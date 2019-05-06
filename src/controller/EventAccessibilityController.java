package controller;

import model.entity.EventAccessibility;
import model.service.EventAccessibilityManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by amir on 2/6/17.
 */
@Controller
@RequestMapping("/document/eventAccessibility")
public class EventAccessibilityController {
    @Autowired
    private EventAccessibilityManager eventAccessibilityManager;
    private static Logger logger = Logger.getLogger(EventAccessibilityController.class.getName());

    @RequestMapping("/add.do")
    public void add(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String kind = request.getParameter("kind");
        String access = request.getParameter("access");
        if (code != null && name != null && kind != null && access != null && !code.trim().equals("") && !name.trim().equals("") && !kind.trim().equals("") && !access.trim().equals("")){
            EventAccessibility eventAccessibility = new EventAccessibility();
            eventAccessibility.setCode(Integer.parseInt(code));
            eventAccessibility.setName(name);
            eventAccessibility.setKind(kind);
            eventAccessibility.setAccess(access);

            try {
                eventAccessibilityManager.add(eventAccessibility);
            } catch (Exception e) {
                logger.error("server not respond", e);
                response.setStatus(500);
            }
        }else {
            response.setStatus(400);
        }
    }

    @RequestMapping("/get.do")
    public void get(HttpServletRequest request, HttpServletResponse response){
        List<EventAccessibility> eventAccessibilities;
        try {
            eventAccessibilities = eventAccessibilityManager.getAll();
        } catch (Exception e) {
            logger.error("server not respond", e);
            response.setStatus(500);
            return;
        }

        JSONArray jsonArray = new JSONArray();
        for (EventAccessibility eventAccessibility : eventAccessibilities) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", eventAccessibility.getCode());
            jsonObject.put("name", eventAccessibility.getName());

            jsonArray.add(jsonObject);
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/JSON");
        try {
            response.getWriter().write(jsonArray.toJSONString());
        } catch (IOException e) {
            logger.error("response Cant write", e);
            response.setStatus(500);
        }
    }
}
