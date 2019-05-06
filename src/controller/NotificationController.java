package controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import model.entity.User;
import model.service.UserManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.servlet.http.Cookie;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by amir on 1/24/17.
 */
@Controller
@ServerEndpoint(value = "/notification", configurator = SpringConfigurator.class)
public class NotificationController{
    @Autowired
    private UserManager userManager;
    @Autowired
    private EventController eventController;
    @Autowired
    private MailController mailController;
    private Queue<Session> queue = new ConcurrentLinkedDeque<>();
    public static Map<Session, User> map = new HashMap<>();
    private static Logger logger = Logger.getLogger(NotificationController.class.getName());

    @OnMessage
    public void onMessage(Session session, String msg){
        List<String> strings;
        User user = new User();
        String token;
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(msg);
            token = (String) jsonObject.get("token");
        } catch (ParseException e) {
            logger.error("cant parse json", e);
            return;
        }
        try {
            DecodedJWT jwt = StaticController.verifier.verify(token);
            strings = jwt.getAudience();
        }catch (Exception e){
            logger.error("token exception", e);
            return;
        }
        try {
            String username = strings.get(0);
            user.setUsername(username);
            user = userManager.getUser(user);
            map.put(session, user);
        }catch (Exception e){
            logger.error("user not found", e);
            CloseReason closeReason = new CloseReason(new CloseReason.CloseCode() {
                @Override
                public int getCode() {
                    return 404;
                }
            }, "user not found");
            try {
                session.close(closeReason);
            } catch (IOException e1) {
                logger.error("IO exception", e);
            }
            return;
        }
        try {
            eventController.sendPastNotif(session, user);
        }catch (Exception e){
            logger.error("Event Controller Error", e);
        }
        try {
            mailController.unseened(session, user);
        }catch (Exception e){
            logger.error("Mail Controller Error", e);
        }
    }

    @OnOpen
    public void Open(Session session){
        queue.add(session);
    }

    @OnError
    public void error(Session session, Throwable throwable){
//        try {
//            session.close();
//        } catch (IOException e) {
//            logger.error(e);
//        }
//        queue.remove(session);
        logger.error("Error on session " + session.getId(), throwable);
    }

    @OnClose
    public void closedConnection(Session session){
        try {
            session.close();
        } catch (IOException e) {
            logger.error(e);
        }
        queue.remove(session);
    }

    public void sendMessage(String msg, Session session){
        try {
            if (!session.isOpen()){
                logger.error("Closed Session " + session.getId());
                queue.remove(session);
            }else {
                session.getBasicRemote().sendText(msg);
            }
        }catch (Exception e){
            logger.error("session problem", e);
        }
    }
}
