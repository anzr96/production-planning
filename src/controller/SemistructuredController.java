package controller;

import model.entity.*;
import model.service.*;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by amir on 11/20/16.
 */
@Controller
@RequestMapping("/document/semistructured")
public class SemistructuredController {
    @Autowired
    private RawMaterialManager rawMaterialManager;
    @Autowired
    private SemistructuredManager semistructuredManager;
    @Autowired
    private SectionManager sectionManager;
    @Autowired
    private MachineGroupManager machineGroupManager;
    @Autowired
    private EventController eventController;
    private static Logger logger = Logger.getLogger(SemistructuredController.class.getName());

    @RequestMapping(value = "/enter.do")
    public void enter(HttpServletRequest request, HttpServletResponse response){
        JSONObject semiObj;
        try {
            semiObj = (JSONObject) new JSONParser().parse(request.getParameter("data"));
        }catch (Exception e){
            response.setStatus(400);
            return;
        }
        SemistructuredInTimes semistructuredInTimes = new SemistructuredInTimes();

        Section section = new Section();
        section.setCode((String) semiObj.get("section"));

        MachineGroup machineGroup = new MachineGroup();
        machineGroup.setCode((String) semiObj.get("groupM"));

        Semistructured semistructured = new Semistructured();
        semistructured.setSemiCode((String) semiObj.get("semi"));

        semistructured = semistructuredManager.getSemistructured(section, machineGroup, semistructured);
        if (semistructured == null){
            response.setStatus(404);
            return;
        }

        JSONArray jsonArray = (JSONArray) semiObj.get("machines");
        if (jsonArray == null || jsonArray.size() == 0){
            response.setStatus(400);
            return;
        }
        Set<Long> machineIds = new HashSet<>();
        for (Object o : jsonArray) {
            machineIds.add(Long.parseLong((String) o));
        }

        semistructuredInTimes.setMachineId(machineIds);

        if (semiObj.get("date") == null || semiObj.get("total") == null || semiObj.get("batch") == null || semiObj.get("shift") == null){
            response.setStatus(400);
            return;
        }
        semistructuredInTimes.setDate(new Date(Long.valueOf((String) semiObj.get("date"))));
        semistructuredInTimes.setTotal(Double.valueOf((String) semiObj.get("total")));
        semistructuredInTimes.setBatchNumber((String) semiObj.get("batch"));
        semistructuredInTimes.setShift(Integer.parseInt((String) semiObj.get("shift")));

        semistructuredInTimes.setGroup((String) semiObj.get("group"));
        semistructuredInTimes.setDescription((String) semiObj.get("des"));

        if (semiObj.get("operators") != null){
            JSONArray operators = (JSONArray) semiObj.get("operators");
            Set<Operator> operatorArrayList = new HashSet<>();
            for (Object o2 : operators) {
                JSONObject operatorObject = (JSONObject) o2;
                Operator operator = new Operator();
                operator.setCode((String) operatorObject.get("code"));
                operator.setName((String) operatorObject.get("name"));
                operator.setJob((String) operatorObject.get("job"));
                operator.setDescription((String) operatorObject.get("des"));

                operatorArrayList.add(operator);
            }
            semistructuredInTimes.setOperatorSet(operatorArrayList);
        }

        JSONArray rawArray = (JSONArray) semiObj.get("raws");
        if (rawArray != null && rawArray.size() > 0){
            Set<String> rawSet = new HashSet<>();
            for (Object o : rawArray) {
                JSONObject rawObj = (JSONObject) o;
                rawSet.add((String) rawObj.get("serial"));
            }
            semistructuredInTimes.setMaterials(rawSet);
        }


        JSONArray semiArray = (JSONArray) semiObj.get("raws");
        if (semiArray != null && semiArray.size() > 0){
            Set<String> semiSet = new HashSet<>();
            for (Object o : semiArray) {
                JSONObject semiObject = (JSONObject) o;
                semiSet.add((String) semiObject.get("serial"));
            }
            semistructuredInTimes.setSemis(semiSet);
        }

        if (semiObj.get("udf") != null){
            JSONArray udfArray = (JSONArray) semiObj.get("udf");
            ArrayList<UDF> udfArrayList = new ArrayList<>();
            for (Object o2 : udfArray) {
                JSONObject udfObject = (JSONObject) o2;
                UDF udf = new UDF();
                udf.setName((String) udfObject.get("name"));
                udf.setType((String) udfObject.get("type"));
                switch (udf.getType()){
                    case "text":{
                        udf.setString((String) udfObject.get("value"));
                        break;
                    }
                    case "number":{
                        udf.setNumber(Double.parseDouble((String) udfObject.get("value")));
                        break;
                    }
                    case "date":{
                        try {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
                            udf.setDate(simpleDateFormat.parse((String) udfObject.get("value")));
                        } catch (ParseException e) {
                            response.setStatus(400);
                            return;
                        }
                        break;
                    }
                    case "file":{
                        udf.setBytes(Base64.getDecoder().decode((String) udfObject.get("value")));
                        break;
                    }
                    default:{
                        response.setStatus(400);
                        return;
                    }
                }
                udfArrayList.add(udf);
            }
            semistructuredInTimes.setUdfs(udfArrayList);
        }

        ArrayList<SemistructuredInTimes> semistructuredInTimesArrayList = new ArrayList<>();
        semistructuredInTimesArrayList.add(semistructuredInTimes);

        semistructured.setSemistructuredInTimes(semistructuredInTimesArrayList);
        semistructuredManager.enterSemistructured(section, machineGroup, semistructured);
    }

    @RequestMapping(value = "/ajaxIdentity.do")
    public void identity(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(request.getParameter("data"));
            if (jsonObject == null){
                response.setStatus(400);
                return;
            }
        }catch (Exception e){
            response.setStatus(400);
            return;
        }

        Section section = new Section();
        section.setCode((String) jsonObject.get("sc"));
        section = sectionManager.getSection(section);

        MachineGroup machineGroup = new MachineGroup();
        machineGroup.setCode((String) jsonObject.get("gc"));
        machineGroup = machineGroupManager.getMachineGroup(machineGroup, section);

        Semistructured semistructured = new Semistructured();
        semistructured.setSemiCode((String) jsonObject.get("oc"));

        semistructured = semistructuredManager.getSemistructured(section, machineGroup, semistructured);
        if (semistructured == null){
            StaticController.sendError(response, 404, "نیمه ساخته مورد نظر یافت نشد");
            return;
        }

        jsonObject = new JSONObject();
        jsonObject.put("sc", section.getCode());
        jsonObject.put("sn", section.getName());
        jsonObject.put("gc", machineGroup.getCode());
        jsonObject.put("gn", machineGroup.getName());
        jsonObject.put("oc", semistructured.getSemiCode());
        jsonObject.put("on", semistructured.getSemiName());
        jsonObject.put("unit", semistructured.getUnit().getUnitName());
        jsonObject.put("ageMin", semistructured.getAgeMin());
        jsonObject.put("ageMax", semistructured.getAgeMax());
        jsonObject.put("min", semistructured.getNeedMin());
        jsonObject.put("max", semistructured.getNeedMax());
        jsonObject.put("des", semistructured.getDescription());

        JSONArray jsonArray = new JSONArray();
        for (UDF udf : semistructured.getUdfs()) {
            JSONObject udfObj = new JSONObject();
            udfObj.put("name", udf.getName());
            udfObj.put("type", udf.getType());
            try {
                switch (udf.getType()){
                    case "text":{
                        udfObj.put("value", udf.getString());
                        break;
                    }
                    case "number":{
                        udfObj.put("value", udf.getNumber());
                        break;
                    }
                    case "date":{
                        udfObj.put("value", udf.getDate());
                        break;
                    }
                    case "file":{
                        udfObj.put("value", Base64.getEncoder().encode(udf.getBytes()));
                        break;
                    }
                }
            }catch (Exception e){
                udfObj.put("value", null);
            }
            jsonArray.add(udfObj);
        }
        jsonObject.put("udfs", jsonArray);

        StaticController.sendData(response, jsonObject.toJSONString());
    }

    @RequestMapping("/getSection.do")
    public void section(HttpServletRequest req, HttpServletResponse resp){
        List<Section> sections = sectionManager.getSections();
        JSONArray sectionArray = new JSONArray();
        if (sections != null){
            for (Section section : sections) {
                JSONObject sectionObject = new JSONObject();
                sectionObject.put("code", section.getCode());
                sectionObject.put("name", section.getName());

                sectionArray.add(sectionObject);
            }
        }

        resp.setContentType("text/json");
        resp.setCharacterEncoding("utf-8");
        try {
            resp.getWriter().write(sectionArray.toJSONString());
        } catch (IOException e) {
            logger.error("cant write", e);
            resp.setStatus(500);
        }
    }

    @RequestMapping("/getGroup.do")
    public void group(HttpServletRequest req, HttpServletResponse resp){
        JSONArray jsonArray;
        try {
            String data = req.getParameter("sections");
            jsonArray = (JSONArray) new JSONParser().parse(data);
        }catch (Exception e){
            resp.setStatus(400);
            return;
        }

        JSONArray sectionArray = new JSONArray();
        for (Object o : jsonArray) {
            String str = (String) o;
            Section section = new Section();
            section.setCode(str);
            section = sectionManager.getSection(section);

            if (section != null){
                List<MachineGroup> machineGroups = machineGroupManager.getMachineGroups(section);
                JSONObject sectionObj = new JSONObject();
                sectionObj.put("code", section.getCode());
                sectionObj.put("name", section.getName());
                JSONArray groupsArray = new JSONArray();
                if (machineGroups != null){
                    for (MachineGroup machineGroup : machineGroups) {
                        JSONObject groupObj = new JSONObject();
                        groupObj.put("code", machineGroup.getCode());
                        groupObj.put("name", machineGroup.getName());
                        groupsArray.add(groupObj);
                    }
                }
                sectionObj.put("groups", groupsArray);
                sectionArray.add(sectionObj);
            }
        }

        resp.setContentType("text/json");
        resp.setCharacterEncoding("utf-8");
        try {
            resp.getWriter().write(sectionArray.toJSONString());
        } catch (IOException e) {
            logger.error("cant write", e);
            resp.setStatus(500);
        }
    }

    @RequestMapping("/getSemi.do")
    public void semi(HttpServletRequest req, HttpServletResponse resp){
        JSONArray jsonArray;
        try {
            String data = req.getParameter("groups");
            jsonArray = (JSONArray) new JSONParser().parse(data);
        }catch (Exception e){
            resp.setStatus(400);
            return;
        }


        JSONArray sectionArray = new JSONArray();
        for (Object o : jsonArray) {
            JSONObject sectionObject = (JSONObject) o;
            Section section = new Section();
            section.setCode((String) sectionObject.get("code"));
            section = sectionManager.getSection(section);

            JSONObject sectionObj = new JSONObject();
            sectionObj.put("code", section.getCode());
            sectionObj.put("name", section.getName());

            JSONArray groupsArray = (JSONArray) sectionObject.get("options");
            JSONArray groupsArr = new JSONArray();
            for (Object o1 : groupsArray) {
                JSONObject groupObejct = (JSONObject) o1;
                MachineGroup machineGroup = new MachineGroup();
                machineGroup.setCode((String) groupObejct.get("code"));
                machineGroup = machineGroupManager.getMachineGroup(machineGroup, section);

                JSONObject groupObj = new JSONObject();
                groupObj.put("code", machineGroup.getCode());
                groupObj.put("name", machineGroup.getName());

                List<Semistructured> semistructureds = machineGroup.getMachineSemis();
                JSONArray semiArray = new JSONArray();
                if (semistructureds != null){
                    for (Semistructured semistructured : semistructureds) {
                        JSONObject semiObject = new JSONObject();
                        semiObject.put("code", semistructured.getSemiCode());
                        semiObject.put("name", semistructured.getSemiName());
                        semiObject.put("total", semistructured.getTotal());
                        semiObject.put("unit", semistructured.getUnit().getUnitName());
                        if (semistructured.getTotal() < semistructured.getNeedMin()){
                            semiObject.put("color", "#d50000");
                        }else if (semistructured.getTotal() > semistructured.getNeedMax()){
                            semiObject.put("color", "#45bf34");
                        }

                        semiArray.add(semiObject);
                    }
                }
                groupObj.put("semis", semiArray);
                groupsArr.add(groupObj);
            }
            sectionObj.put("groups", groupsArr);
            sectionArray.add(sectionObj);
        }

        resp.setContentType("text/json");
        resp.setCharacterEncoding("utf-8");
        try {
            resp.getWriter().write(sectionArray.toJSONString());
        } catch (IOException e) {
            logger.error("cant write", e);
            resp.setStatus(500);
        }
    }

    @RequestMapping("/ajaxData.do")
    public void ajaxData(HttpServletRequest req, HttpServletResponse resp){
        try {
            if (req.getParameter("rawCode") != null && !req.getParameter("rawCode").trim().equals("")){
                RawMaterial rawMaterial = new RawMaterial();
                rawMaterial.setRawCode(req.getParameter("rawCode"));
                rawMaterial = rawMaterialManager.getRawMaterial(rawMaterial);
                Element root = new Element("root");
                Document document = new Document(root);

                Element code = new Element("rawCode");
                code.addContent(rawMaterial.getRawCode());

                Element name = new Element("rawName");
                name.addContent(rawMaterial.getRawName());

                root.addContent(code);
                root.addContent(name);

                resp.setContentType("text/xml");
                resp.getWriter().write(new XMLOutputter().outputString(document));
            }else {
                resp.setStatus(403);
            }
        }catch (Exception e){
            e.printStackTrace();
            resp.setStatus(404);
        }
    }

    @RequestMapping("/ajaxData2.do")
    public void ajaxData2(HttpServletRequest req, HttpServletResponse resp){
        try {
            if (req.getParameter("semiCode") != null && !req.getParameter("semiCode").trim().equals("")){
                Semistructured semistructured = new Semistructured();
                semistructured.setSemiCode(req.getParameter("semiCode"));
//                semistructured = semistructuredManager.getSemistructured(semistructured);

                Element root = new Element("root");
                Document document = new Document(root);

                Element code = new Element("semiCode");
                code.addContent(semistructured.getSemiCode());

                Element name = new Element("semiName");
                name.addContent(semistructured.getSemiName());

                root.addContent(code);
                root.addContent(name);

                resp.setContentType("text/xml");
                resp.getWriter().write(new XMLOutputter().outputString(document));
            }else {
                resp.setStatus(403);
            }
        }catch (Exception e){
            e.printStackTrace();
            resp.setStatus(404);
        }
    }

    @RequestMapping("/raw.do")
    public void ajaxRaw(HttpServletRequest req, HttpServletResponse resp){
        try {
            List<RawMaterial> rawMaterials = rawMaterialManager.getRawMaterials();

            Element root = new Element("root");
            Document document = new Document(root);

            for (RawMaterial rawMaterial : rawMaterials) {
                Element node = new Element("node");

                Element code = new Element("code");
                code.addContent(rawMaterial.getRawCode());
                Element name = new Element("name");
                name.addContent(rawMaterial.getRawName());

                node.addContent(code);
                node.addContent(name);

                root.addContent(node);
            }

            resp.setContentType("text/xml");
            resp.getWriter().write(new XMLOutputter().outputString(document));
        }catch (Exception e){
            e.printStackTrace();
            resp.setStatus(500);
        }
    }

    private boolean checkingNullAndDuplicate(String[] strings){
        Set<String> temp = new HashSet<String>();
        for(int i = 0; i < strings.length; i++){
            String string = strings[i];
            temp.add(string);
            if(strings[i] == null || strings[i].trim().equals("")){
                return false;
            }
        }

        Set<String> duplicate = new HashSet<String>();

        for (String s : temp) {
            if(!duplicate.contains(s)){
                duplicate.add(s);
            }else{
                return false;
            }
        }

        return true;
    }
}
