package controller;

import model.entity.AttachFile;
import model.entity.Mail;
import model.entity.Role;
import model.entity.User;
import model.service.MailManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by amir on 7/21/17.
 */
@Controller
@RequestMapping("/document/mail")
public class MailController {
    @Autowired
    private MailManager mailManager;
    @Autowired
    private NotificationController notificationController;
    @Autowired
    private UserController userController;
    private static Logger logger = Logger.getLogger(MailController.class.getName());

    @RequestMapping(value = "/add.do")
    public void addMail(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        JSONArray receivers = (JSONArray) jsonObject.get("receiver");
        String subject = (String) jsonObject.get("subject");
        String description = (String) jsonObject.get("description");
        String attach = (String) jsonObject.get("attach");
        byte[] attachFile;
        try {
            attachFile = Base64.getDecoder().decode((String) jsonObject.get("attachFile"));
        }catch (Exception e){
            attachFile = null;
        }

        Role role = userController.getUser(request);
        User user = role.getUsers().get(0);
        Date date = new Date();

        if (receivers != null){
            for (Object o : receivers) {
                String receiver = (String) o;
                Mail mail = new Mail();
                try {
                    mail.setSender(user.getUsername());
                    mail.setReceiver(receiver);
                    mail.setSentDate(date);
                    if (subject != null){
                        mail.setSubject(subject);
                    }
                    if (description != null){
                        mail.setDescription(description);
                    }
                    if (attach != null){
                        mail.setAttach(attach);
                    }
                    if (attachFile != null){
                        AttachFile file = new AttachFile();
                        file.setBytes(attachFile);
                        ArrayList<AttachFile> attachFiles = new ArrayList<>();
                        attachFiles.add(file);
                        mail.setAttachFiles(attachFiles);
                    }
                }catch (Exception e){
                    response.setStatus(400);
                    response.setContentType("text");
                    response.setCharacterEncoding("utf-8");
                    try {
                        response.getWriter().write("یکی از فیلد ها خالی است");
                    } catch (IOException e1) {
                        logger.error("Cant write", e);
                    }
                }
                if (!mailManager.addMail(mail)){
                    logger.error("Cant persist");
                    response.setStatus(400);
                }else {
                    sendMail(mail);
                }
            }
        }else {
            logger.error("receiver null");
            response.setStatus(400);
        }
    }

    private void sendMail(Mail mail){
        for (Session session : NotificationController.map.keySet()) {
            User user = NotificationController.map.get(session);
            if (mail.getReceiver().equals(user.getUsername())){
                ArrayList<Mail> mailList = new ArrayList<>();
                mailList.add(mail);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("mail", toJSON(mailList));
                notificationController.sendMessage(jsonObject.toJSONString(), session);
            }
        }
    }

    public void unseened(Session session, User user){
        try{
            if (user != null){
                Mail mail = new Mail();
                mail.setReceiver(user.getUsername());
                List<Mail> mailList = mailManager.getUnseened(mail);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("mail", toJSON(mailList));
                notificationController.sendMessage(jsonObject.toJSONString(), session);
            }
        }catch (Exception e){
            logger.error(e);
        }
    }

    @RequestMapping("/getInbox.do")
    public void getMail(HttpServletRequest request, HttpServletResponse response){
        Role role = userController.getUser(request);
        User user = role.getUsers().get(0);
        Mail mail = new Mail();
        mail.setReceiver(user.getUsername());
        List<Mail> mailList = mailManager.getMails(mail);

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            response.getWriter().write(toJSON(mailList).toJSONString());
        } catch (IOException e) {
            logger.error("Cant parse", e );
        }
    }

    private JSONArray toJSON(List<Mail> mailList){
        JSONArray mailArray = new JSONArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (Mail mail1 : mailList) {
            if (!mail1.isSeen()){
                JSONObject mailObject = new JSONObject();
                mailObject.put("id", mail1.getId());
                mailObject.put("s", mail1.getSender());
                mailObject.put("r", mail1.getReceiver());
                mailObject.put("sb", mail1.getSubject());
                mailObject.put("ds", mail1.getDescription());
                mailObject.put("a", mail1.getAttach());
                JSONArray attachArray = new JSONArray();
                int i = 0;
                for (AttachFile attachFile : mail1.getAttachFiles()) {
                    JSONObject attachObject = new JSONObject();
                    attachObject.put(i, attachFile.getBytes());
                    i++;

                    attachArray.add(attachObject);
                }
                mailObject.put("af", attachArray);
                mailObject.put("d", simpleDateFormat.format(mail1.getSentDate()));

                mailArray.add(mailObject);
            }
        }

        return mailArray;
    }

    @RequestMapping("/seen.do")
    public void seenMail(HttpServletRequest request, HttpServletResponse response){
        Mail mail = new Mail();
        try {
            String id = request.getParameter("id");
            mail.setId(Long.parseLong(id));
            User user = (User) request.getAttribute("user");
            String status = mailManager.setSeen(mail, user.getUsername());
            switch (status){
                case "server error":{
                    response.setStatus(500);
                    break;
                }
                case "mail error":{
                    response.setStatus(404);
                    break;
                }
                case "receiver error":{
                    response.setStatus(400);
                    break;
                }
                default:{
                    response.getWriter().write(id);
                }
            }
        }catch (Exception e){
            response.setStatus(400);
        }
    }
}
