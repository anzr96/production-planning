package controller;

import model.entity.*;
import model.service.EventAccessibilityManager;
import model.service.EventManager;
import model.service.UserManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by amir on 2/1/17.
 */
@Controller
@RequestMapping("/document/event")
public class EventController {
    @Autowired
    private EventManager eventManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private NotificationController notificationController;
    @Autowired
    private EventAccessibilityManager eventAccessibilityManager;
    @Autowired
    private UserController userController;
    private static Logger logger = Logger.getLogger(EventController.class.getName());

    public void addEvent(Object object, String kind){

        switch (object.getClass().getName()){
            case "model.entity.Product":{
                product((Product) object, kind);
                break;
            }
            case "model.entity.RawMaterial":{
                rawMaterial((RawMaterial) object, kind);
                break;
            }
            case "model.entity.Semistructured":{
                semistructured((Semistructured) object, kind);
                break;
            }
            case "model.entity.Machine":{
                machine((Machine) object, kind);
                break;
            }
            case "model.entity.Budget":{
                budget((Budget) object, kind);
                break;
            }
            case "model.entity.PlaningTimes":{
                plan((PlaningTimes) object, kind);
                break;
            }
            case "model.entity.DiscontinuedGroup":{
                discontinued((DiscontinuedGroup) object, kind);
                break;
            }
            default:{
                logger.error("class not defined");
            }
        }
    }

    private void product(Product product, String kind){
        Event event = new Event();
        event.setDate(new Date());
        event.setKind(kind);
        event.setAccess("product");

        boolean check = true;

        switch (kind){
            case "add":{
                event.setTitle("تحویل محصول به انبار");
                event.setDescription(product.getTotal() + "محصول " + product.getSize() + " " + product.getDesign() + " "
                 + product.getPr() + " با کد " + product.getProductCode() + " با درجه " + product.getProductDegrees().get(0).getDegree() +  "به انبار تحویل داده شد");
                break;
            }
            case "use":{
                event.setTitle("ارسال محصول");
                event.setDescription(product.getProductDegrees().get(0).getTotal() + "محصول " + product.getSize() + " " + product.getDesign() + " "
                        + product.getPr() + " با کد " + product.getProductCode() + " با درجه " + product.getProductDegrees().get(0).getDegree() + " از انبار کسر گردید");
                break;
            }
            case "isolate":{
                event.setTitle("تحویل محصول به ضایعات");
                event.setDescription(product.getTotal() + "محصول " + product.getSize() + " " + product.getDesign() + " "
                        + product.getPr() + " با کد " + product.getProductCode() + " تبدیل به ضایعات شدند");
                break;
            }
            default:{
                logger.error("kind not defined");
                check = false;
            }
        }
        if (check){
            try {
                eventManager.add(event);
                sendNotif(event);
            } catch (Exception e) {
                logger.error("Server does not respond", e);
            }
        }
    }

    private void rawMaterial(RawMaterial rawMaterial, String kind){
        Event event = new Event();
        event.setDate(new Date());
        event.setKind(kind);
        event.setAccess("rawmaterial");

        switch (kind) {
            case "add": {
                event.setTitle("تحویل مواد جدید به انبار");
                event.setDescription(rawMaterial.getTotal() + "ماده اولیه " + rawMaterial.getRawName() + " با کد " + rawMaterial.getRawCode() + "به انبار تحویل داده شد");
                break;
            }
            case "use": {
                event.setTitle("تحویل مواد به تولید");
                event.setDescription(rawMaterial.getTotal() + "ماده اولیه " + rawMaterial.getRawName() + " با کد " + rawMaterial.getRawCode() + " به تولید تحویل داده شد");
                break;
            }
            default: {
                logger.error("kind not defined");
                return;
            }
        }
        try {
            eventManager.add(event);
        } catch (Exception e) {
            logger.error("Server does not respond", e);
        }
        try {
            sendNotif(event);
        }catch (Exception e){
            logger.error("websocket isnt available", e);
        }
    }

    private void semistructured(Semistructured semistructured, String kind){
        Event event = new Event();
        event.setDate(new Date());
        event.setKind(kind);
        event.setAccess("semistructured");

        switch (kind){
            case "add":{
                event.setTitle("تحویل "+ semistructured.getSemiGroups() + " به تولید");
                event.setDescription(semistructured.getTotal() + " " + semistructured.getSemiGroups() + " " + semistructured.getSemiName() + " با کد " + semistructured.getSemiCode() + "به تولید تحویل داده شد");
                break;
            }
            case "use":{
                event.setTitle("استفاده از " + semistructured.getSemiGroups());
                event.setDescription(semistructured.getTotal() + " " + semistructured.getSemiGroups() + " " + semistructured.getSemiName() + " با کد " + semistructured.getSemiCode() + " استفاده شد");
                break;
            }
            default:{
                logger.error("kind not defined");
            }
        }
        try {
            eventManager.add(event);
            sendNotif(event);
        } catch (Exception e) {
            logger.error("Server does not respond");
        }
    }

    private void machine(Machine machine, String kind){
        Event event = new Event();
        event.setDate(new Date());
        event.setKind(kind);
        event.setAccess("machine");

        switch (kind){
            case "stop":{
                event.setTitle("متوقف شدن دستگاه");
                event.setDescription("دستگاه " + machine.getName() + " با کد " + machine.getCode() + " متوقف شد");
                break;
            }
            case "run":{
                event.setTitle("راه اندازی دستگاه");
                event.setDescription("دستگاه " + machine.getName() + " با کد " + machine.getCode() + " راه اندازی شد");
                break;
            }
            default:{
                logger.error("kind not defined");
            }
        }
        try {
            eventManager.add(event);
            sendNotif(event);
        } catch (Exception e) {
            logger.error("Server does not respond");
        }
    }

    private void budget(Budget budget, String kind){
        Event event = new Event();
        event.setDate(new Date());
        event.setKind(kind);
        event.setAccess("budget");

        boolean check = true;
        switch (kind){
            case "add":{
                event.setTitle("بودجه سال ثبت شد");
                event.setUrl("/document/budgetShow.jsp");
                event.setDescription("بودجه سال جدید با مبلغ " + budget.getPrice() + " و مقدار تولید " + budget.getWeight() + " کیلوگرم ثبت شد");
                break;
            }
            case "edit":{
                event.setTitle("بودجه ویرایش شد");
                event.setUrl("/document/budgetShow.jsp");
                event.setDescription("بودجه ویرایش شد و مبلغ جدید به " + budget.getPrice() + " و مقدار تولید جدید به " + budget.getWeight() + " تغییر پیدا کرد.");
                break;
            }
            default:{
                logger.error("kind not defined");
                check = false;
            }
        }
        if (check){
            try {
                eventManager.add(event);
                sendNotif(event);
            } catch (Exception e) {
                logger.error("Server does not respond", e);
            }
        }
    }

    private void plan(PlaningTimes planingTimes, String kind){
        Event event = new Event();
        event.setDate(new Date());
        event.setKind(kind);
        event.setAccess("plan");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
        boolean check = true;
        switch (kind){
            case "add":{
                event.setTitle("برنامه تولید روزانه اضافه شد");
                event.setUrl("/document/planingShow.jsp");
                event.setDescription("برنامه تولید تاریخ " + simpleDateFormat.format(planingTimes.getTime()) + " در شیفت " + planingTimes.getPlaningShifts().get(0).getShift() + " اضافه شد");
                break;
            }
            case "edit":{
                event.setTitle("برنامه تولید روزانه ویرایش شد");
                event.setUrl("/document/planingShow.jsp");
                event.setDescription("برنامه تولید تاریخ " + simpleDateFormat.format(planingTimes.getTime()) + " در شیفت " + planingTimes.getPlaningShifts().get(0).getShift() + " ویرایش شد");
                break;
            }
            default:{
                logger.error("kind not defined");
                check = false;
            }
        }
        if (check){
            try {
                eventManager.add(event);
                sendNotif(event);
            } catch (Exception e) {
                logger.error("Server does not respond", e);
            }
        }
    }

    private void discontinued(DiscontinuedGroup discontinuedGroup, String kind){
        Event event = new Event();
        event.setDate(new Date());
        event.setKind(kind);
        event.setAccess("discontinued");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate + " HH:mm");
        Discontinued discontinued = discontinuedGroup.getDiscontinueds().get(0);
        DiscontinuedTime discontinuedTime = discontinued.getDiscontinuedTimes().get(0);
        switch (kind){
            case "discontinued":{
                event.setTitle("توقف رخ داده است");
                event.setUrl("/document/discontinuedShow.jsp");
                event.setDescription("توقف در گروه توقفات " + discontinuedGroup.getName() + " با نام " + discontinued.getName() + " در تاریخ " + simpleDateFormat.format(discontinuedTime.getDate()) + " به مدت " + discontinuedTime.getTimeOfDiscontinued() + " دقیقه رخ داده است");
                break;
            }
            case "discontinuedRun":{
                event.setTitle("توقف رفع شد");
                event.setUrl("/document/discontinuedShow.jsp");
                event.setDescription("توقف در گروه توقفات " + discontinuedGroup.getName() + " با نام " + discontinued.getName() + " در تاریخ " + simpleDateFormat.format(discontinuedTime.getDate()) + " در مدت " + discontinuedTime.getTimeOfRepair() + " رفع شد");
                break;
            }
            default:{
                logger.error("kind not defined");
            }
        }
        try {
            eventManager.add(event);
            sendNotif(event);
        } catch (Exception e) {
            logger.error("Server does not respond");
        }
    }

    @RequestMapping(value = "/get.do", method = RequestMethod.GET)
    public String getSpecificNotification(HttpServletRequest request, HttpServletResponse response){
        long t = Long.parseLong(request.getParameter("t"));
        if (t != 0){
            LinkedList<Event> linkedList = new LinkedList<>();
            Event event;
            try {
                event = eventManager.getID(t);
            } catch (Exception e) {
                logger.error("bad request, Cant find the id", e);
                return "document/MainPage";
            }
            linkedList.add(event);
            try {
                List<Event> events = eventManager.getEventKindAccessBetween(event, new Date(new Date().getTime() - 1000*60*60*5), new Date());
                for (Event event1 : events) {
                    if (event1.getId() != event.getId()){
                        linkedList.add(event1);
                    }
                }
            } catch (Exception e) {

            }
            request.setAttribute("event", linkedList);
            return "document/notificationView";
        }else {
            return "document/MainPage";
        }
    }

    @RequestMapping(value = "/getEvent.do")
    public void getNotification(HttpServletRequest request, HttpServletResponse response){
        List<String> accesses = null, kinds = null;
        Role role = userController.getUser(request);
        User user = role.getUsers().get(0);
        if (user != null){
            if (user.getUsername().equals("admin")){
                List<EventAccessibility> eventAccessibilities = null;
                try {
                    eventAccessibilities = eventAccessibilityManager.getAll();
                } catch (Exception e) {
                    logger.error(e);
                }
                accesses = new ArrayList<>();
                kinds = new ArrayList<>();
                for (EventAccessibility eventAccessibility : eventAccessibilities) {
                    accesses.add(eventAccessibility.getAccess());
                    kinds.add(eventAccessibility.getKind());
                }
            }else if (role.getEventAccessibilities() != null){
                accesses = new ArrayList<>();
                kinds = new ArrayList<>();
                for (EventAccessibility eventAccessibility : role.getEventAccessibilities()) {
                    accesses.add(eventAccessibility.getAccess());
                    kinds.add(eventAccessibility.getKind());
                }
            }
            List<Event> events;
            if (accesses.size() > 0 && kinds.size() > 0){
                events = eventManager.getLast(accesses, kinds);
            }else {
                events = null;
            }
            if (events == null){
                events = new ArrayList<>();
            }
            JSONArray jsonArray = new JSONArray();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate + " HH:mm");
            for (Event event : events) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", event.getId());
                jsonObject.put("date", simpleDateFormat.format(event.getDate()));
                jsonObject.put("title", event.getTitle());
                jsonObject.put("kind", event.getKind());
                jsonObject.put("description", event.getDescription());
                jsonObject.put("seen", false);

                jsonArray.add(jsonObject);
            }
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            try {
                response.getWriter().write(jsonArray.toJSONString());
            } catch (IOException e) {
                logger.error("writer doesnt write", e);
            }
            return;
        }
        response.setStatus(403);
    }

    @RequestMapping("/delete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response){
        Role role = userController.getUser(request);
        User user = role.getUsers().get(0);
        if (user != null){
            if (role.getCode() == 0){
                response.setContentType("text");
                response.setCharacterEncoding("UTF-8");
                try {
                    response.getWriter().write("{admin:true}");
                } catch (IOException e) {
                    logger.error(e);
                    response.setStatus(500);
                }
                return;
            }
            String[] data = request.getParameterValues("data");
            for (String s : data) {
                long id = Long.parseLong(s);
                Event event = new Event();
                event.setId(id);
                EventProperties eventProperties = new EventProperties();
                eventProperties.setUsername(user.getUsername());
                ArrayList<EventProperties> eventProperties1 = new ArrayList<>();
                eventProperties1.add(eventProperties);
                event.setPropertiesList(eventProperties1);
                try {
                    eventManager.deleteUser(event);
                } catch (Exception e) {
                    logger.error("server not respond");
                }
            }
        }
    }

    @RequestMapping("/adminDelete.do")
    public void adminDelete(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        ArrayList<Long> longs = new ArrayList<>();
        Role role = userController.getUser(request);
        User user = role.getUsers().get(0);
        if (user != null && role.getCode() == 0){
            String[] data = request.getParameterValues("data");
            for (String s : data) {
                long id = Long.parseLong(s);
                Event event = new Event();
                event.setId(id);
                try {
                    eventManager.delete(event);
                } catch (Exception e) {
                    logger.error("server not respond");
                    longs.add(id);
                }
            }
            if (longs.size() > 0){
                response.setContentType("text");
                response.setCharacterEncoding("utf-8");
                try {
                    response.getWriter().write(String.valueOf(longs));
                } catch (IOException e) {
                    logger.error("Cant write", e);
                }
            }
        }else {
            response.setStatus(400);
        }
    }

    private boolean checkProperties(Event event, User user, int code){
        boolean check = false;
        if (event.getPropertiesList() != null){
            if (event.getPropertiesList() != null && event.getPropertiesList().size() > 0){
                for (EventProperties eventProperties : event.getPropertiesList()) {
                    try {
                        if (eventProperties.getUsername().equals(user.getUsername())){
                            check = true;
                            break;
                        }
                    }catch (Exception e){
                        logger.warn(e);
                    }
                }
            }
            if (!check){
                EventProperties eventProperties = new EventProperties();
                eventProperties.setUsername(user.getUsername());
                eventProperties.setAccessCode(code);
                ArrayList<EventProperties> eventPropertiesArrayList = new ArrayList<>();
                eventPropertiesArrayList.add(eventProperties);
                event.setId(event.getId());
                event.setPropertiesList(eventPropertiesArrayList);

                return eventManager.addProperties(event);
            }
        }else {
            EventProperties eventProperties = new EventProperties();
            eventProperties.setUsername(user.getUsername());
            eventProperties.setAccessCode(code);
            ArrayList<EventProperties> eventPropertiesArrayList = new ArrayList<>();
            eventPropertiesArrayList.add(eventProperties);
            event.setPropertiesList(eventPropertiesArrayList);

            return eventManager.addProperties(event);
        }
        return false;
    }

    public void sendNotif(Event event){
        for (Session session: NotificationController.map.keySet()) {
            JSONArray jsonArray = new JSONArray();
            User user = NotificationController.map.get(session);
            Role role = userManager.getUserRole(user);
            if (!user.getUsername().equals("admin")){
                for (EventAccessibility eventAccessibility : role.getEventAccessibilities()) {
                    if (event.getKind().equals(eventAccessibility.getKind()) && event.getAccess().equals(eventAccessibility.getAccess())){
                        if (checkProperties(event, user, eventAccessibility.getCode())){
                            jsonArray.add(getLast(event, false));
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("alert", jsonArray);
                            notificationController.sendMessage(jsonObject.toJSONString(), session);
                        }
                        break;
                    }
                }
            }else {
                if (checkProperties(event, user, 0)){
                    jsonArray.add(getLast(event, false));
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("alert", jsonArray);
                    notificationController.sendMessage(jsonObject.toJSONString(), session);
                }
                break;
            }
        }
    }

    public void sendPastNotif(Session session, User user){
        Role role = userManager.getUserRole(user);

        List<Event> events;

        events = eventManager.getEventBetween(new Date(new Date().getTime() - /*user.getCustomization().getTimeNotification()*/5*60*60*1000), new Date());

        JSONArray jsonArray = new JSONArray();
        if (events != null){
            for (Event event : events) {
                if (user.getUsername().equals("admin")){
                    checkProperties(event, user, 0);
                    for (EventProperties eventProperties : event.getPropertiesList()) {
                        try {
                            if (eventProperties.getUsername().equals(user.getUsername())){
                                jsonArray.add(getLast(event, eventProperties.isSeen()));
                            }
                        }catch (Exception e){
                            logger.warn("eventProperties", e);
                        }

                    }
                }
                if (role.getEventAccessibilities() != null){
                    for (EventAccessibility eventAccessibility : role.getEventAccessibilities()) {
                        if (event.getKind().equals(eventAccessibility.getKind()) && event.getAccess().equals(eventAccessibility.getAccess())){
                            checkProperties(event, user, eventAccessibility.getCode());
                            for (EventProperties eventProperties : event.getPropertiesList()) {
                                if (eventProperties.getUsername().equals(user.getUsername())){
                                    jsonArray.add(getLast(event, eventProperties.isSeen()));
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }else {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("alert", jsonArray);
        notificationController.sendMessage(jsonObject.toJSONString(), session);
    }

    private JSONArray getLast10(User user){
        Role role = userManager.getUserRole(user);
        List<String> accesses = null, kinds = null;
        if (user.getUsername().equals("admin")){
            List<EventAccessibility> eventAccessibilities = null;
            try {
                eventAccessibilities = eventAccessibilityManager.getAll();
            } catch (Exception e) {
                logger.error(e);
            }
            accesses = new ArrayList<>();
            kinds = new ArrayList<>();
            for (EventAccessibility eventAccessibility : eventAccessibilities) {
                accesses.add(eventAccessibility.getAccess());
                kinds.add(eventAccessibility.getKind());
            }

        }else if (role.getEventAccessibilities() != null){
            accesses = new ArrayList<>();
            kinds = new ArrayList<>();
            for (EventAccessibility eventAccessibility : role.getEventAccessibilities()) {
                accesses.add(eventAccessibility.getAccess());
                kinds.add(eventAccessibility.getKind());
            }
        }
        List<Event> events;
        try {
            events = eventManager.getLast(accesses, kinds);
        } catch (Exception e) {
            logger.error("server not respond", e);
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate + " HH:mm");
        for (Event event : events) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", event.getId());
            jsonObject.put("date", simpleDateFormat.format(event.getDate()));
            jsonObject.put("title", event.getTitle());
            jsonObject.put("kind", event.getKind());
            jsonObject.put("description", event.getDescription());
            jsonObject.put("seen", false);

            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    private JSONObject getLast(Event event, boolean seen){
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate + " HH:mm");

        jsonObject.put("id", event.getId());
        jsonObject.put("date", simpleDateFormat.format(event.getDate()));
        jsonObject.put("title", event.getTitle());
        jsonObject.put("kind", event.getKind());
        jsonObject.put("description", event.getDescription());
        jsonObject.put("seen", seen);

        if (event.getKind().equals("add") && event.getAccess().equals("product")){
            jsonObject.put("name", "add_product");
        }else if (event.getKind().equals("use") && event.getAccess().equals("product")){
            jsonObject.put("name", "use_product");
        }else if (event.getKind().equals("add") && event.getAccess().equals("rawmaterial")){
            jsonObject.put("name", "add_rawmaterial");
        }else if (event.getKind().equals("use") && event.getAccess().equals("rawmaterial")){
            jsonObject.put("name", "use_rawmaterial");
        }else if (event.getKind().equals("discontinued") && event.getAccess().equals("discontinued")){
            jsonObject.put("name", "discontinued");
        }
        return jsonObject;
    }

    @RequestMapping("/seen.do")
    public void seen(HttpServletRequest request, HttpServletResponse response){
        JSONArray jsonArray;
        try{
            jsonArray = (JSONArray) new JSONParser().parse(request.getParameter("data"));
            if (jsonArray == null){
                response.setStatus(400);
                return;
            }
        }catch (Exception e){
            response.setStatus(400);
            return;
        }

        for (Object o : jsonArray) {
            int id = Integer.parseInt(o + "");
            Event event = new Event();
            event.setId(id);

            User user = (User) request.getAttribute("user");
            List<EventProperties> eventProperties = new ArrayList<>();
            EventProperties eventProperties1 = new EventProperties();
            eventProperties1.setUsername(user.getUsername());
            eventProperties.add(eventProperties1);
            event.setPropertiesList(eventProperties);

            if (!eventManager.seenUser(event)){
                response.setStatus(500);
            }
        }
    }
}
