package controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import model.entity.Accessibility;
import model.entity.Role;
import model.entity.User;
import model.service.UserManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by amir on 7/23/17.
 */
@Controller
public class StaticController {
    private static Logger logger = Logger.getLogger(StaticController.class.getName());
    static public final String stringType = "NVARCHAR2(255)";
    static public final String patternDate = "MM-dd-yyyy";
    public static JWTVerifier verifier;
    @Autowired
    public UserManager userManager;

    @PostConstruct()
    private void start(){
        try {
            verifier = JWT.require(Algorithm.HMAC256("Adm!nstr@t0r1996"))
                    .withIssuer("Lastic Pars")
                    .build();
        } catch (UnsupportedEncodingException e) {
            logger.error("Token exception", e);
        }
        User user = new User();
        user.setUsername("admin");
        user = userManager.getUser(user);
        if (user == null){
            user = new User();
            Role role = new Role();
            role.setCode(0);
            role.setName("ادمین");

            Accessibility accessibility = new Accessibility();
            accessibility.setCode(0);
            accessibility.setName("document");
            accessibility.setUrl("/document");

            ArrayList<Accessibility> accessibilities = new ArrayList<>();
            accessibilities.add(accessibility);
            role.setAccessibilities(accessibilities);

            user.setUsername("admin");
            user.setName("admin");
            user.setPassword("21232f297a57a5a743894a0e4a801fc3");

            ArrayList<User> users = new ArrayList<>();
            users.add(user);

            role.setUsers(users);
            try {
                userManager.add(role);
            }catch (Exception e){
                logger.error("server error", e);
            }

        }
    }

    public static void sendError(HttpServletResponse response,int errorNumber, String errorText){
        response.setCharacterEncoding("utf-8");
        response.setStatus(errorNumber);
        try {
            response.getWriter().write(errorText);
        } catch (IOException e) {
            logger.error("writer cant write", e);
            response.setStatus(500);
        }
    }

    public static void sendData(HttpServletResponse response, String data){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("JSON");
        try {
            response.getWriter().write(data);
        } catch (IOException e) {
            logger.error("writer cant write", e);
            response.setStatus(500);
        }
    }

    public static void checkNULL(JSONObject jsonObject, String name, Object value){
        if (value != null){
            jsonObject.put(name, value);
        }else {
            jsonObject.put(name, " ");
        }
    }
}
