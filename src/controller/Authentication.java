package controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import model.entity.Accessibility;
import model.entity.Role;
import model.entity.User;
import model.service.RoleManager;
import model.service.UserManager;
import org.apache.log4j.Logger;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by amir on 1/24/17.
 */
@Controller
@RequestMapping("/Authentication")
public class Authentication {
    @Autowired
    private UserManager userManager;
    @Autowired
    private RoleManager roleManager;
    private static Logger logger = Logger.getLogger(Authentication.class.getName());

    @RequestMapping("/logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("T")){
                User user = new User();
                try {
                    DecodedJWT jwt = StaticController.verifier.verify(cookie.getValue());
                    List<String> strings = jwt.getAudience();
                    user.setUsername(strings.get(0));
                    user.setConnect(false);
                    try {
                        userManager.setConnect(user);
                    } catch (Exception e) {
                        logger.error("server not respond");
                        response.setStatus(500);
                    }
                } catch (JWTVerificationException exception){
                    logger.info("token failed", exception);
                    response.setStatus(400);
                }
                cookie.setMaxAge(-1);
                cookie.setValue("");
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
        }
        try {
            response.sendRedirect("/login.jsp");
        } catch (IOException e) {
            logger.error("Response Cant send redirect!", e);
            response.setStatus(500);
        }
    }

    @RequestMapping("/login.do")
    public void login(HttpServletRequest request, HttpServletResponse response){
        String username, password;
        boolean rememberMe;
        try {
            username = request.getParameter("username");
            password = request.getParameter("password");
            rememberMe = Boolean.parseBoolean(request.getParameter("rememberMe"));
            if (username == null || username.trim().equals("")){
                response.setStatus(404);
                return;
            }
            if (password == null || password.trim().equals("")){
                response.setStatus(404);
                return;
            }
        }catch (Exception e){
            response.setStatus(404);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        boolean checkLogin;
        checkLogin = userManager.identify(user);
        if (checkLogin){
            user.setConnect(true);
            if (!userManager.setConnect(user)){
                response.setStatus(500);
                return;
            }

            String token = JWTCreate(user, rememberMe, request.getHeader("User-Agent"), request.getRemoteHost());
            if (token == null){
                logger.info("token failed");
                response.setStatus(500);
                return;
            }
            Cookie cookie = new Cookie("T", token);
            cookie.setHttpOnly(true);
            if (rememberMe){
                cookie.setMaxAge(60 * 60 * 24);
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        }else {
            response.setStatus(404);
        }
    }

    private String JWTCreate(User user, boolean rememberMe, String userAgent, String host){
        String token;
        try {
            token = JWT.create()
                    .withAudience(user.getUsername())
                    .withExpiresAt(new Date(new Date().getTime() + 15*60*1000))
                    .withIssuedAt(new Date())
                    .withNotBefore(new Date())
                    .withIssuer("Lastic Pars")
                    .withClaim("r", rememberMe)
                    .withClaim("u", Sha256Hash.toString(userAgent.getBytes(StandardCharsets.UTF_8)))
                    .withClaim("h", Sha256Hash.toString(host.getBytes(StandardCharsets.UTF_8)))
                    .sign(Algorithm.HMAC256("Adm!nstr@t0r1996"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
            return null;
        }
        return token;
    }

    @RequestMapping("/create.do")
    public void create(HttpServletRequest request, HttpServletResponse response) {
        logger.info("create user begin");
        Map<String, String[]> map = request.getParameterMap();
        String username, password, name, P;
        try {
            P = map.get("P")[0];
            username = map.get("username")[0];
            password = map.get("password")[0];
            name = map.get("name")[0];
        }catch (Exception e){
            response.setStatus(400);
            return;
        }
        String PCheck;
        try {
            PCheck = EncoderUtil.getMD5("Adm!nstr@t0r1996");
        } catch (Exception e) {
            logger.error("Encoder Cant get MD5!", e);
            response.setStatus(500);
            return;
        }
        if (!P.trim().equals(PCheck)) {
            response.setStatus(400);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        Role role1 = new Role();
        role1.setCode(0);
        role1.setName("ادمین");
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        role1.setUsers(users);
        try {
            userManager.add(role1);
        } catch (Exception e) {
            logger.error("Server Cant create user", e);
            response.setStatus(500);
        }
        logger.info("create user end");
    }

    @RequestMapping("/checkRole.do")
    public void checkRole(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")){
                User user = new User();
                user.setUsername(cookie.getValue());
                try {
                    user = userManager.getUser(user);
                } catch (Exception e) {
                    logger.error("user not found or server doesnt respond");
                    response.setStatus(500);
                    return;
                }
                Role role;
                try {
                    role = userManager.getUserRole(user);
                }catch (Exception e){
                    logger.error("role not found");
                    response.setStatus(500);
                    return;
                }

                response.setCharacterEncoding("UTF-8");
                response.setContentType("text");
                try {
                    String data = role.getCode() + "";
                    response.getWriter().write(data);
                    return;
                } catch (IOException e) {
                    logger.error("Response Cant write!");
                    response.setStatus(500);
                    return;
                }
            }
        }
    }
}
