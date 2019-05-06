package controller;

import model.entity.Discontinued;
import model.entity.DiscontinuedGroup;
import model.entity.DiscontinuedTime;
import model.entity.Machine;
import model.service.DiscontinuedManager;
import model.service.MachineManager;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by amir on 12/24/16.
 */
@Controller
@RequestMapping("/document/discontinued")
public class DiscontinuedController {
    @Autowired
    private MachineManager machineManager;
    @Autowired
    private DiscontinuedManager discontinuedManager;
    @Autowired
    private EventController eventController;

    @RequestMapping("/add.do")
    public void register(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String[]> map = request.getParameterMap();
            String groupKind;
            if (map.get("groupKind").length > 1){
                if (map.get("groupKind")[1].equals("")){
                    groupKind = map.get("groupKind")[0];
                }else {
                    groupKind = map.get("groupKind")[1];
                }
            }else {
                groupKind = map.get("groupKind")[0];
            }

            String kind;
            if (map.get("kind").length > 1){
                if (map.get("kind")[1].equals("")){
                    kind = map.get("kind")[0];
                }else {
                    kind = map.get("kind")[1];
                }
            }else {
                kind = map.get("kind")[0];
            }

            double disTime = Double.parseDouble(map.get("disTime")[0]);
            double repTime = Double.parseDouble(map.get("repTime")[0]);
            String place;
            try{
                place = map.get("place")[0];
            }catch (Exception e){
                place = "";
            }
            String explain = map.get("explain")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate + " HH:mm");
            String tempDate = map.get("date")[0] + " " + map.get("hour")[0] + ":" + map.get("min")[0];
            Date date = simpleDateFormat.parse(tempDate);

            DiscontinuedGroup discontinuedGroup = new DiscontinuedGroup();
            discontinuedGroup.setName(groupKind);

            Discontinued discontinued = new Discontinued();
            discontinued.setName(kind);

            DiscontinuedTime discontinuedTime = new DiscontinuedTime();
            discontinuedTime.setDate(date);
            discontinuedTime.setExplain(explain);
            discontinuedTime.setTimeOfDiscontinued(disTime);
            discontinuedTime.setTimeOfRepair(repTime);
            if (place != null && !place.trim().equals("")){
                discontinuedTime.setPlace(place);
            }else {
                discontinuedTime.setPlace("");
            }

            ArrayList<DiscontinuedTime> discontinuedTimes = new ArrayList<>();
            discontinuedTimes.add(discontinuedTime);

            discontinued.setDiscontinuedTimes(discontinuedTimes);

            ArrayList<Discontinued> discontinueds = new ArrayList<>();
            discontinueds.add(discontinued);

            discontinuedGroup.setDiscontinueds(discontinueds);

            try{
                if (discontinuedManager.addDiscontinued(discontinuedGroup)){
                    eventController.addEvent(discontinuedGroup, "discontinued");
                    response.setStatus(200);
                }else
                    response.setStatus(500);
            }catch (Exception e){
                e.printStackTrace();
                response.setStatus(500);
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(400);
        }
    }

    @RequestMapping("/disGroup.do")
    public void discontinuedGroup(HttpServletRequest request, HttpServletResponse response){
        try {
            List<DiscontinuedGroup> discontinuedGroups = discontinuedManager.getDiscontinuedGroups();

            Element root = new Element("root");
            Document document = new Document(root);

            for (DiscontinuedGroup discontinuedGroup : discontinuedGroups) {
                Element name = new Element("name");
                name.addContent(discontinuedGroup.getName());
                root.addContent(name);
            }

            response.setContentType("text/XML");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new XMLOutputter().outputString(document));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(404);
        }
    }

    @RequestMapping("/disKind.do")
    public void discontinuedKindSingle(HttpServletRequest request, HttpServletResponse response){
        String disGroup = request.getParameter("data");
        if (disGroup != null && !disGroup.trim().equals("")) {
            DiscontinuedGroup discontinuedGroup = new DiscontinuedGroup();
            discontinuedGroup.setName(disGroup);

            try {
                discontinuedGroup = discontinuedManager.getDiscontinuedGroup(discontinuedGroup);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(404);
                return;
            }

            Element root = new Element("root");
            Document document = new Document(root);

            for (Discontinued discontinued : discontinuedGroup.getDiscontinueds()) {
                Element name = new Element("name");
                name.addContent(discontinued.getName());
                root.addContent(name);
            }

            response.setContentType("text/XML");
            response.setCharacterEncoding("UTF-8");
            try {
                response.getWriter().write(new XMLOutputter().outputString(document));
            } catch (IOException e) {
                e.printStackTrace();
                response.setStatus(500);
            }
        }else {
            response.setStatus(400);
        }
    }

    @RequestMapping("/disPlace.do")
    public void discontinuedPlace(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Machine> machines = machineManager.getMachines();
            Element root = new Element("root");
            Document document = new Document(root);

            for (Machine machine : machines) {
                Element machineElement = new Element("machine");
                Element code = new Element("code");
                Element name = new Element("name");

                code.addContent(machine.getCode());
                name.addContent(machine.getName());
                machineElement.addContent(code);
                machineElement.addContent(name);
                root.addContent(machineElement);
            }
            response.setContentType("text/XML");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new XMLOutputter().outputString(document));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(404);
        }
    }

    @RequestMapping("/disKindMultiple.do")
    public void discontinuedKindMultiple(HttpServletRequest request, HttpServletResponse response){
        String[] disGroup = request.getParameter("data").split(",");

        Element root = new Element("root");
        Document document = new Document(root);

        for (String s : disGroup) {
            if (s != null && !s.trim().equals("")) {

                DiscontinuedGroup discontinuedGroup = new DiscontinuedGroup();
                discontinuedGroup.setName(s);

                try {
                    discontinuedGroup = discontinuedManager.getDiscontinuedGroup(discontinuedGroup);
                } catch (Exception e) {
                    response.setStatus(400);
                    return;
                }
                Element discontinuedElement = new Element("discontinued");
                discontinuedElement.setAttribute("label", discontinuedGroup.getName());

                for (Discontinued discontinued : discontinuedGroup.getDiscontinueds()) {
                    Element name = new Element("name");
                    name.addContent(discontinued.getName());
                    discontinuedElement.addContent(name);
                }

                root.addContent(discontinuedElement);
            }else {
                response.setStatus(400);
                return;
            }
        }

        response.setContentType("text/XML");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(new XMLOutputter().outputString(document));
        } catch (IOException e) {
            response.setStatus(500);
        }
    }

    @RequestMapping("/show.do")
    public void discontinuedShow(HttpServletRequest request, HttpServletResponse response){
        Map<String, String[]> map = request.getParameterMap();

        String[] disGroup;
        String[] disKind;
        String start, end, table = "", line = "", bar = "";
        Date startDate, endDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);

        try {
            disGroup = map.get("disGroup");
            disKind = map.get("kind");
            start = map.get("start")[0];
            end = map.get("end")[0];
        }catch (Exception e){
            response.setStatus(400);
            return;
        }
        try {
            table = map.get("table")[0];
        }catch (Exception e){
        }
        try {
            line = map.get("line")[0];
        }catch (Exception e){
        }
        try {
            bar = map.get("bar")[0];
        }catch (Exception e){
        }

        try {
            startDate = simpleDateFormat.parse(start);
            endDate = simpleDateFormat.parse(end);
        } catch (ParseException e) {
            response.setStatus(500);
            return;
        }

        JSONObject jsonObject = new JSONObject();

        if (table != null && !table.trim().equals("")){
            JSONArray jsonArray = new JSONArray();
            jsonObject.put("table", jsonArray);
        }

        Map<Date, Double> dates = new HashMap<>();
        Map<String, Double> kinds = new HashMap<>();

        for (String s : disGroup) {
            if (s != null && !s.trim().equals("")){
                s =s.trim();
                DiscontinuedGroup discontinuedGroup = new DiscontinuedGroup();
                discontinuedGroup.setName(s);
                try {
                    discontinuedGroup = discontinuedManager.getDiscontinuedGroup(discontinuedGroup);
                } catch (Exception e) {
                    response.setStatus(404);
                    return;
                }
                for (Discontinued discontinued : discontinuedGroup.getDiscontinueds()) {
                    for (String s1 : disKind) {
                        if (s1.equals(discontinued.getName())){
                            DiscontinuedGroup discontinuedGroup1 = new DiscontinuedGroup();
                            discontinuedGroup1.setName(discontinuedGroup.getName());
                            Discontinued discontinued1 = new Discontinued();
                            discontinued1.setName(discontinued.getName());
                            ArrayList<Discontinued> discontinueds = new ArrayList<>();
                            discontinueds.add(discontinued1);
                            discontinuedGroup1.setDiscontinueds(discontinueds);
                            try {
                                List<DiscontinuedTime> discontinuedTimes = discontinuedManager.getDiscontinuedTimes(discontinuedGroup1, startDate, endDate);
                                discontinued1.setDiscontinuedTimes(discontinuedTimes);
                            } catch (Exception e) {
                                response.setStatus(500);
                                return;
                            }
                            if (table != null && !table.trim().equals("")){
                                JSONArray temp = table(discontinuedGroup1);
                                for (Object o : temp) {
                                    JSONObject jsonObject1 = (JSONObject) o;
                                    ((JSONArray) jsonObject.get("table")).add(jsonObject1);
                                }
                            }
                            if (line != null && !line.trim().equals("")){
                                for (DiscontinuedTime discontinuedTime : discontinued.getDiscontinuedTimes()) {
                                    if (dates.containsKey(discontinuedTime.getDate())){
                                        dates.put(discontinuedTime.getDate(), dates.get(discontinuedTime.getDate()) + discontinuedTime.getTimeOfDiscontinued());
                                    }else {
                                        dates.put(discontinuedTime.getDate(), discontinuedTime.getTimeOfDiscontinued());
                                    }
                                }
                            }
                            if (bar != null && !bar.trim().equals("")){
                                double total = 0.0;
                                String name;
                                for (Discontinued discontinued2 : discontinuedGroup1.getDiscontinueds()) {
                                    for (DiscontinuedTime discontinuedTime : discontinued2.getDiscontinuedTimes()) {
                                        total += discontinuedTime.getTimeOfDiscontinued();
                                    }
                                }
                                name = discontinuedGroup1.getDiscontinueds().get(0).getName();
                                kinds.put(name, total);
                            }
                        }
                    }
                }
            }else {
                response.setStatus(400);
                return;
            }
        }

        if (line != null && !line.trim().equals("")){
            JSONArray lineJSON = new JSONArray();
            for (Date date : dates.keySet()) {
                JSONObject tempLine = new JSONObject();
                tempLine.put("date", date.getTime());
                tempLine.put("value", dates.get(date));
                lineJSON.add(tempLine);
            }
            jsonObject.put("line", lineJSON);
        }
        if (bar != null && !bar.trim().equals("")){
            JSONArray barJSON = new JSONArray();
            for (String s : kinds.keySet()) {
                JSONObject tempBar = new JSONObject();
                tempBar.put("name", s);
                tempBar.put("value", kinds.get(s));
                barJSON.add(tempBar);
            }
            jsonObject.put("bar", barJSON);
        }

        response.setContentType("text/JSON");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(jsonObject.toJSONString());
        } catch (IOException e) {
            response.setStatus(500);
        }

    }

    private JSONArray table(DiscontinuedGroup discontinuedGroup){
        Discontinued discontinued = discontinuedGroup.getDiscontinueds().get(0);
        List<DiscontinuedTime> discontinuedTimes = discontinued.getDiscontinuedTimes();

        JSONArray tableJSON = new JSONArray();

        for (DiscontinuedTime discontinuedTime : discontinuedTimes) {
            JSONObject tempTable = new JSONObject();

            tempTable.put("group", discontinuedGroup.getName());
            tempTable.put("kind", discontinued.getName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate + " HH:mm");
            tempTable.put("date", simpleDateFormat.format(discontinuedTime.getDate()));
            tempTable.put("discontinuedTime", discontinuedTime.getTimeOfDiscontinued());
            tempTable.put("repairTime", discontinuedTime.getTimeOfRepair());
            tempTable.put("explain", discontinuedTime.getExplain());

            if (discontinuedTime.getPlace() != null && !discontinuedTime.getPlace().equals("")){
                Machine machine = new Machine();
                machine.setCode(discontinuedTime.getPlace());
                try {
//                    machine = machineManager.getMachine(machine);
                    tempTable.put("place", machine.getName());
                } catch (Exception e) {
                    tempTable.put("place", "");
                }
            }else {
                tempTable.put("place", "");
            }

            tableJSON.add(tempTable);
        }
        return tableJSON;
    }
}
