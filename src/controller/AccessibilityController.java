package controller;

import model.entity.Accessibility;
import model.service.AccessibilityManager;
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
 * Created by amir on 2/5/17.
 */
@Controller
@RequestMapping("/document/access")
public class AccessibilityController {
    @Autowired
    private AccessibilityManager accessibilityManager;
    private static Logger logger = Logger.getLogger(AccessibilityController.class.getName());

    @RequestMapping("/add.do")
    public void add(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        if (code != null && !code.trim().equals("") && name != null && !name.trim().equals("") && address != null && !address.trim().equals("")){
            Accessibility accessibility= new Accessibility();
            accessibility.setCode(Integer.parseInt(code));
            accessibility.setName(name);
            accessibility.setUrl(address);

            try {
                accessibilityManager.add(accessibility);
            } catch (Exception e) {
                logger.error("server not respond", e);
                response.setStatus(500);
            }
        }
    }

    @RequestMapping("/get.do")
    public void get(HttpServletRequest request, HttpServletResponse response){
        List<Accessibility> accessibilities;
        try {
            accessibilities = accessibilityManager.getAccessibilitys();
        } catch (Exception e) {
            logger.error("server not respond", e);
            response.setStatus(500);
            return;
        }
        JSONArray jsonArray = new JSONArray();
        for (Accessibility accessibility : accessibilities) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", accessibility.getCode());
            jsonObject.put("name", accessibility.getName());
            jsonObject.put("address", accessibility.getUrl());

            jsonArray.add(jsonObject);
        }

        response.setContentType("text/JSON");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(jsonArray.toJSONString());
        } catch (IOException e) {
            logger.error("response Cant write", e);
            response.setStatus(500);
        }
    }
}
