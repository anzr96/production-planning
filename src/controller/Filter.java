package controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import model.entity.Accessibility;
import model.entity.Role;
import model.entity.User;
import model.service.UserManager;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by amir on 12/5/16.
 */
@WebFilter(urlPatterns = "/document/*")
public class Filter implements javax.servlet.Filter {
    @Autowired
    private UserManager userManager;

    private static Logger logger = Logger.getLogger(Filter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Cookie[] cookies = ((HttpServletRequest)servletRequest).getCookies();
        DecodedJWT jwt = checkCookies(cookies);
        String username;
        try {
            String userAgent = jwt.getClaim("u").asString();
            String host = jwt.getClaim("h").asString();
            if (userAgent.equals(Sha256Hash.toString(((HttpServletRequest) servletRequest).getHeader("User-Agent").getBytes(StandardCharsets.UTF_8)))
                    && host.equals(Sha256Hash.toString(servletRequest.getRemoteHost().getBytes(StandardCharsets.UTF_8)))){
                username = jwt.getAudience().get(0);
            }else {
                username = null;
            }
        }catch (Exception e){
            username = null;
        }

        if (username != null){
            User user = new User();
            user.setUsername(username);

            String tokenString = JWTCreate(user, jwt.getClaim("r").asBoolean(), ((HttpServletRequest) servletRequest).getHeader("User-Agent"), servletRequest.getRemoteHost());
            if (tokenString == null){
                logger.info("token null");
                logOut(((HttpServletResponse) servletResponse), 500);
                return;
            }

            Cookie cookie2 = new Cookie("T", tokenString);
            cookie2.setHttpOnly(true);
            if (jwt.getClaim("r").asBoolean()){
                cookie2.setMaxAge(60*60*24);
            }

            cookie2.setPath("/");
            ((HttpServletResponse) servletResponse).addCookie(cookie2);

            if (!user.getUsername().equals("admin")){
                if (!checkAccessibility(user, ((HttpServletRequest) servletRequest).getServletPath())){
                    ((HttpServletResponse) servletResponse).setStatus(403);
                    ((HttpServletResponse) servletResponse).sendRedirect("/403.jsp");
                    return;
                }
            }

            servletRequest.setAttribute("user", user);
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            logOut(((HttpServletResponse) servletResponse), 400);
        }
    }

    private DecodedJWT checkCookies(Cookie[] cookies){
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("T")){
                String token = cookie.getValue();
                if (token != null && !token.trim().equals("")){
                    try {
                        DecodedJWT jwt = StaticController.verifier.verify(token);
                        return jwt;
                    } catch (JWTVerificationException exception){
                        logger.info("token failed", exception);
                        return null;
                    }
                }
                break;
            }
        }
        return null;
    }

    private String JWTCreate(User user, boolean rememberMe, String userAgent, String host){
        String token;
        try {
            if (rememberMe){
                token = JWT.create()
                        .withAudience(user.getUsername())
                        .withIssuedAt(new Date())
                        .withNotBefore(new Date())
                        .withIssuer("Lastic Pars")
                        .withClaim("r", true)
                        .withClaim("u", Sha256Hash.toString(userAgent.getBytes(StandardCharsets.UTF_8)))
                        .withClaim("h", Sha256Hash.toString(host.getBytes(StandardCharsets.UTF_8)))
                        .sign(Algorithm.HMAC256("Adm!nstr@t0r1996"));
            }else {
                token = JWT.create()
                        .withAudience(user.getUsername())
                        .withExpiresAt(new Date(new Date().getTime() + 15*60*1000))
                        .withIssuedAt(new Date())
                        .withNotBefore(new Date())
                        .withIssuer("Lastic Pars")
                        .withClaim("r", false)
                        .withClaim("u", Sha256Hash.toString(userAgent.getBytes(StandardCharsets.UTF_8)))
                        .withClaim("h", Sha256Hash.toString(host.getBytes(StandardCharsets.UTF_8)))
                        .sign(Algorithm.HMAC256("Adm!nstr@t0r1996"));
            }
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
            return null;
        }
        return token;
    }

    private void logOut(HttpServletResponse response, int status){
        Cookie cookie = new Cookie("T", "");
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setStatus(status);

        try {
            response.sendRedirect("/login.jsp");
        } catch (IOException e) {
            logger.error("response send redirect error", e);
        }
    }

    private boolean checkAccessibility(User user, String urlPattern){
        Role role = userManager.getUserRole(user);
        if (role != null){
            for (Accessibility accessibility : role.getAccessibilities()) {
                if (urlPattern.contains(accessibility.getUrl())){
                    return true;
                }
            }
        }
        if (role == null){
            logger.info("null role");
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
