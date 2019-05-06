package controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import model.service.UserManager;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by amir on 1/25/17.
 */
@WebFilter(urlPatterns = "/login.jsp")
public class FilterFirstPage implements javax.servlet.Filter {
    private static Logger logger = Logger.getLogger(FilterFirstPage.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Cookie[] cookies = ((HttpServletRequest)servletRequest).getCookies();
        if (cookies != null){
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
                ((HttpServletResponse) servletResponse).sendRedirect("/document/MainPage.jsp");
            }else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
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
    @Override
    public void destroy() {

    }
}
