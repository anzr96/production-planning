package controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import model.entity.Role;
import model.entity.User;
import model.service.UserManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by amir on 1/24/17.
 */
@Controller
@RequestMapping("/document/user")
public class UserController {
    @Autowired
    private UserManager userManager;
    private static Logger logger = Logger.getLogger(UserController.class.getName());

    @RequestMapping("/create.do")
    public void create(HttpServletRequest request, HttpServletResponse response){
        Map<String, String[]> map = request.getParameterMap();
        String username, password, name, role;
        try {
            username = map.get("username")[0];
            password = map.get("password")[0];
            name = map.get("name")[0];
            role = map.get("role")[0];
        }catch (Exception e){
            response.setStatus(400);
            return;
        }

        User user = new User();
        user.setUsername(username);
        try {
            password = EncoderUtil.getMD5(password);
        } catch (Exception e) {
            logger.error(e);
        }
        user.setPassword(password);
        user.setName(name);
        Role role1 = new Role();
        role1.setCode(Integer.parseInt(role));
        role1 = userManager.getRole(role1);
        if (role1 != null){
            role1.getUsers().add(user);
        }else {
            response.setStatus(404);
            return;
        }

        if (!userManager.add(role1)){
            logger.error("user already exist");
            response.setStatus(400);
            try {
                response.getWriter().write("نام کاربری موجود است");
            } catch (IOException e) {
                logger.error("cant write", e);
                response.setStatus(500);
            }
        }
    }

    @RequestMapping("/change.do")
    public void change(HttpServletRequest request, HttpServletResponse response){
        Map<String, String[]> map = request.getParameterMap();
        String password, newpassword, repeatpassword;
        try {
            password = map.get("password")[0];
            newpassword = map.get("newpassword")[0];
            repeatpassword = map.get("repeatpassword")[0];
        }catch (Exception e){
            response.setStatus(403);
            return;
        }

        if (password != null && !password.trim().equals("") && newpassword != null && !newpassword.trim().equals("") && repeatpassword != null && !repeatpassword.trim().equals("")){
            if (newpassword.equals(repeatpassword)){
                User user = (User) request.getAttribute("user");
                try {
                    if (user.getPassword().equals(password)){
                        user.setPassword(newpassword);
                        try {
                            userManager.edit(user);
                        }catch (Exception e){
                            response.setStatus(500);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(404);
                }
            }
        }else {
            response.setStatus(403);
        }
    }

    @RequestMapping("/data.do")
    public void data(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getAttribute("user");
        if (user.getUsername() != null && !user.getUsername().equals("")){
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", user.getName());
                jsonObject.put("role", userManager.getUserRole(user).getName());
                jsonObject.put("username", user.getUsername());
                response.setContentType("text/JSON");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonObject.toJSONString());
                return;
            } catch (Exception e) {
                logger.error(e);
                response.setStatus(404);
                return;
            }
        }
        response.setStatus(404);
    }

    @RequestMapping("/get.do")
    public void get(HttpServletRequest request, HttpServletResponse response){
        List<User> users = userManager.getUsers();

        JSONArray userArray = new JSONArray();
        for (User user : users) {
            JSONObject userObject = new JSONObject();

            userObject.put("n", user.getName());
            userObject.put("u", user.getUsername());

            userArray.add(userObject);
        }

        response.setContentType("text/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(userArray.toJSONString());
        } catch (IOException e) {
            logger.error("Cant write", e);
            response.setStatus(500);
        }
    }

    @RequestMapping("/delete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response){
        String password = request.getParameter("password");
        if (password != null && !password.trim().equals("")){
            User user = (User) request.getAttribute("user");
            if (user.getPassword().equals(password)){
                try {
                    userManager.delete(user);
                } catch (Exception e) {
                    logger.error("server not respond", e);
                    response.setStatus(500);
                }
            }else {
                response.setStatus(400);
            }
        }else {
            response.setStatus(400);
        }
    }

    public Role getUser(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        User user = new User();
        Role role;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("T")){
                String token = cookie.getValue();
                if (token != null && !token.equals("")){
                    try {
                        JWTVerifier verifier = null; //Reusable verifier instance
                        try {
                            verifier = JWT.require(Algorithm.HMAC256("Adm!nstr@t0r1996"))
                                    .withIssuer("Lastic Pars")
                                    .build();
                        } catch (UnsupportedEncodingException e) {
                            logger.error(e);
                            return null;
                        }
                        DecodedJWT jwt = verifier.verify(token);
                        List<String> strings = jwt.getAudience();
                        user.setUsername(strings.get(0));
                        try {
                            role = userManager.getUserRole(user);
                            user = userManager.getUser(user);
                            ArrayList<User> users= new ArrayList<>();
                            users.add(user);
                            Role role1 = new Role();
                            role1.setCode(role.getCode());
                            role1.setName(role.getName());
                            role1.setUsers(users);
                            role1.setAccessibilities(role.getAccessibilities());
                            role1.setEventAccessibilities(role.getEventAccessibilities());
                            return role1;
                        } catch (Exception e) {
                            logger.error("null", e);
                        }
                    } catch (JWTVerificationException exception){
                        logger.info("token failed", exception);
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
