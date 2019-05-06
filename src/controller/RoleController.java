package controller;

import model.entity.Accessibility;
import model.entity.EventAccessibility;
import model.entity.Role;
import model.service.RoleManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by amir on 2/5/17.
 */
@Controller
@RequestMapping("/document/role")
public class RoleController {
    @Autowired
    private RoleManager roleManager;
    private static Logger logger = Logger.getLogger(RoleController.class.getName());

    @RequestMapping("/add.do")
    public void add(HttpServletRequest request, HttpServletResponse response){
        Map<String, String[]> map = request.getParameterMap();
        String code = map.get("code")[0];
        String name = map.get("name")[0];
        String[] codeAccess = request.getParameterValues("accessCode[]");
        String[] codeEvent = request.getParameterValues("eventCode[]");

        if (code != null && !code.trim().equals("") && name != null && !name.trim().equals("") && codeAccess != null){
            Role role = new Role();
            role.setCode(Integer.parseInt(code));
            role.setName(name);

            ArrayList<Accessibility> accessibilityArrayList = new ArrayList<>();
            for (String codeAcces : codeAccess) {
                Accessibility accessibility = new Accessibility();
                accessibility.setCode(Integer.parseInt(codeAcces));

                accessibilityArrayList.add(accessibility);
            }

            if (codeEvent != null){
                ArrayList<EventAccessibility> eventAccessibilities = new ArrayList<>();
                for (String s : codeEvent) {
                    EventAccessibility eventAccessibility = new EventAccessibility();
                    eventAccessibility.setCode(Integer.parseInt(s));

                    eventAccessibilities.add(eventAccessibility);
                }
                if (eventAccessibilities.size() > 0){
                    role.setEventAccessibilities(eventAccessibilities);
                }
            }

            if (accessibilityArrayList.size() > 0){
                role.setAccessibilities(accessibilityArrayList);
                try {
                    roleManager.add(role);
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

    @RequestMapping("/get.do")
    public void get(HttpServletRequest request, HttpServletResponse response){
        List<Role> roles;
        try {
            roles = roleManager.getRoles();
        } catch (Exception e) {
            logger.error("server not respond", e);
            response.setStatus(500);
            return;
        }
        JSONArray jsonArray = new JSONArray();
        for (Role role : roles) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",role.getCode());
            jsonObject.put("name", role.getName());

            jsonArray.add(jsonObject);
        }
        if (jsonArray.size() == 0){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","0");
            jsonObject.put("name", "ادمین");

            jsonArray.add(jsonObject);
        }

        response.setContentType("text/JSON");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(jsonArray.toJSONString());
        } catch (IOException e) {
            logger.error("Response Cant write", e);
            response.setStatus(500);
        }
    }
}
