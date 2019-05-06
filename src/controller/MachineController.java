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
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by amir on 11/25/16.
 */
@Controller
@RequestMapping("/document/machine")
public class MachineController {
    @Autowired
    private MachineManager machineManager;
    @Autowired
    private ProductManager productManager;
    @Autowired
    private SemistructuredManager semistructuredManager;
    @Autowired
    private SectionManager sectionManager;
    @Autowired
    private MachineGroupManager machineGroupManager;
    private static Logger logger = Logger.getLogger(MachineController.class.getName());

    @RequestMapping("/register.do")
    public void register(HttpServletRequest req, HttpServletResponse resp){
        String data = req.getParameter("data");
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(data);
        } catch (org.json.simple.parser.ParseException e) {
            logger.error("JSON Cant parse", e);
            resp.setStatus(400);
            return;
        }
        Section section = new Section();
        try {
            section.setCode((String) jsonObject.get("code"));
            section.setName((String) jsonObject.get("name"));
            if (section.getCode().equals("") || section.getName().equals("")){
                resp.setStatus(400);
                return;
            }
        }catch (Exception e){
            resp.setStatus(400);
            return;
        }

        JSONArray groupsArray = (JSONArray) jsonObject.get("groups");
        for (Object o : groupsArray) {
            JSONObject groupsObject = (JSONObject) o;

            MachineGroup machineGroup = new MachineGroup();
            try {
                machineGroup.setCode((String) groupsObject.get("code"));
                machineGroup.setName((String) groupsObject.get("name"));
                if (machineGroup.getCode().equals("") || machineGroup.getName().equals("")){
                    resp.setStatus(400);
                    return;
                }
            }catch (Exception e){
                resp.setStatus(400);
                return;
            }

            JSONArray machinesArray = (JSONArray) groupsObject.get("machines");
            for (Object o1 : machinesArray) {
                JSONObject MachinesObject = (JSONObject) o1;
                Machine machine = new Machine();
                machine.setCode((String) MachinesObject.get("code"));
                machine.setName((String) MachinesObject.get("name"));
                if (machine.getCode().equals("") || machine.getName().equals("")){
                    resp.setStatus(400);
                    return;
                }
                machine.setCapacity(Double.valueOf((String) MachinesObject.get("capacity")) );
                machine.setUph(Double.valueOf((String)  MachinesObject.get("uph")) );
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
                try {
                    machine.setBirthday(simpleDateFormat.parse((String) MachinesObject.get("d")));
                } catch (ParseException e) {
                    logger.error("Cant parse", e);
                }
                machine.setCountry((String) MachinesObject.get("country"));
                machine.setDescription((String) MachinesObject.get("des"));
                if (MachinesObject.get("udf") != null){
                    JSONArray udfArray = (JSONArray) MachinesObject.get("udf");
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
                                    udf.setDate(simpleDateFormat.parse((String) udfObject.get("value")));
                                } catch (ParseException e) {
                                    resp.setStatus(400);
                                    return;
                                }
                                break;
                            }
                            case "file":{
                                udf.setBytes(Base64.getDecoder().decode((String) udfObject.get("value")));
                                break;
                            }
                            default:{
                                resp.setStatus(400);
                                return;
                            }
                        }
                        if (udf.getName().equals("")){
                            resp.setStatus(400);
                            return;
                        }
                        udfArrayList.add(udf);
                    }
                    machine.setUdfs(udfArrayList);
                }
                machineGroup.getMachines().add(machine);
            }

            JSONArray productsArray = (JSONArray) groupsObject.get("products");
            for (Object o1 : productsArray) {
                JSONObject productsObject = (JSONObject) o1;

                Product product = new Product();
                product.setProductCode((String) productsObject.get("code"));
                product.setSize((String) productsObject.get("size"));
                product.setDesign((String) productsObject.get("design"));
                product.setPr((String) productsObject.get("pr"));
                if (product.getProductCode().equals("") || product.getSize().equals("") || product.getDesign().equals("") || product.getPr().equals("")){
                    resp.setStatus(400);
                    return;
                }
                product.setWeight(Double.valueOf((String) productsObject.get("weight")));
                product.setDescription((String) productsObject.get("des"));
                if (productsObject.get("udf") != null){
                    JSONArray udfArray = (JSONArray) productsObject.get("udf");
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
                                    resp.setStatus(400);
                                    return;
                                }
                                break;
                            }
                            case "file":{
                                udf.setBytes(Base64.getDecoder().decode((String) udfObject.get("value")));
                                break;
                            }
                            default:{
                                resp.setStatus(400);
                                return;
                            }
                        }
                        udfArrayList.add(udf);
                    }
                    product.setUdfs(udfArrayList);
                }

                machineGroup.getMachineProducts().add(product);
            }

            JSONArray semisArray = (JSONArray) groupsObject.get("semistructureds");
            for (Object o1 : semisArray) {
                JSONObject semiObject = (JSONObject) o1;

                Semistructured semistructured = new Semistructured();
                semistructured.setSemiCode((String) semiObject.get("code"));
                semistructured.setSemiName((String) semiObject.get("name"));
                if (semistructured.getSemiCode().equals("") || semistructured.getSemiName().equals("")){
                    resp.setStatus(400);
                    return;
                }
                Units units = new Units();
                units.setUnitName((String) semiObject.get("unit"));
                semistructured.setUnit(units);
                semistructured.setAgeMin(Double.valueOf((String) semiObject.get("ageMin")));
                semistructured.setAgeMax(Double.valueOf((String) semiObject.get("ageMax")));
                semistructured.setNeedMin(Double.valueOf((String) semiObject.get("needMin")));
                semistructured.setNeedMax(Double.valueOf((String) semiObject.get("needMax")));
                semistructured.setDescription((String) semiObject.get("des"));
                semistructured.setSemiGroups(machineGroup.getName());
                if (semiObject.get("udf") != null){
                    JSONArray udfArray = (JSONArray) semiObject.get("udf");
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
                                    resp.setStatus(400);
                                    return;
                                }
                                break;
                            }
                            case "file":{
                                udf.setBytes(Base64.getDecoder().decode((String) udfObject.get("value")));
                                break;
                            }
                            default:{
                                resp.setStatus(400);
                                return;
                            }
                        }
                        udfArrayList.add(udf);
                    }
                    semistructured.setUdfs(udfArrayList);
                }
                machineGroup.getMachineSemis().add(semistructured);
            }

            section.getMachineGroups().add(machineGroup);
        }
        sectionManager.register(section);
    }

    @RequestMapping("/ajaxData.do")
    public void ajaxData(HttpServletRequest request, HttpServletResponse response){
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

        Machine machine = new Machine();
        machine.setCode((String) jsonObject.get("machine"));

        machine = machineManager.getMachine(section, machineGroup, machine);
        if (machine == null){
            StaticController.sendError(response, 404, "دستگاه مورد نظر یافت نشد");
            return;
        }

        jsonObject = new JSONObject();
        StaticController.checkNULL(jsonObject, "sc", section.getCode());
        StaticController.checkNULL(jsonObject, "sn", section.getName());
        StaticController.checkNULL(jsonObject, "gc", machineGroup.getCode());
        StaticController.checkNULL(jsonObject, "gn", machineGroup.getName());
        StaticController.checkNULL(jsonObject, "mc", machine.getCode());
        StaticController.checkNULL(jsonObject, "mn", machine.getName());
        StaticController.checkNULL(jsonObject, "uph", machine.getUph());
        StaticController.checkNULL(jsonObject, "capacity", machine.getCapacity());
        StaticController.checkNULL(jsonObject, "birthday", machine.getBirthday().getTime());
        StaticController.checkNULL(jsonObject, "country", machine.getCountry());
        StaticController.checkNULL(jsonObject, "des", machine.getDescription());

        JSONArray jsonArray = new JSONArray();
        for (UDF udf : machine.getUdfs()) {
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

    @RequestMapping("/edit.do")
    public void edit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Machine machine){

    }

    @RequestMapping("/delete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response){

    }

    @RequestMapping("/listGroup.do")
    public void listMachineGroup(HttpServletRequest request, HttpServletResponse response){
        String code;
        try {
            code = request.getParameter("code");
            if (code.trim().equals("") || code == null){
                response.setStatus(400);
                return;
            }
        }catch (Exception e){
            response.setStatus(400);
            return;
        }
        Section section = new Section();
        section.setCode(code);
        section = sectionManager.getSection(section);

        JSONArray groupsArray = new JSONArray();
        if (section.getMachineGroups() != null){
            for (MachineGroup machineGroup : section.getMachineGroups()) {
                JSONObject groupObject = new JSONObject();
                groupObject.put("code", machineGroup.getCode());
                groupObject.put("name", machineGroup.getName());

                groupsArray.add(groupObject);
            }
        }else {
            StaticController.sendError(response, 404, "گروهی در قسمت مورد نظر یافت نشد");
        }

        StaticController.sendData(response, groupsArray.toJSONString());
    }

    @RequestMapping("/listSection.do")
    public void listMachineSection(HttpServletRequest request, HttpServletResponse response){
        List<Section> sections = sectionManager.getSections();
        JSONArray sectionArray = new JSONArray();
        for (Section section : sections) {
            JSONObject sectionObject = new JSONObject();
            sectionObject.put("code", section.getCode());
            sectionObject.put("name", section.getName());

            sectionArray.add(sectionObject);
        }

        StaticController.sendData(response, sectionArray.toJSONString());
    }

    @RequestMapping("/listProductMachine.do")
    public void listProductMachine(HttpServletRequest request, HttpServletResponse response){
        String groupCode, sectionCode;
        try {
            groupCode = request.getParameter("groupCode");
            sectionCode = request.getParameter("sectionCode");
        }catch (Exception e){
            response.setStatus(400);
            return;
        }

        Section section = new Section();
        section.setCode(sectionCode);

        MachineGroup machineGroup = new MachineGroup();
        machineGroup.setCode(groupCode);

        machineGroup = machineGroupManager.getMachineGroup(machineGroup, section);

        if (machineGroup == null){
            response.setStatus(400);
            return;
        }

        if (machineGroup.getMachineSemis() != null && machineGroup.getMachineSemis().size() > 0){
            JSONArray semiArray = new JSONArray();
            for (Semistructured semistructured : machineGroup.getMachineSemis()) {
                JSONObject semiObject = new JSONObject();

                semiObject.put("code", semistructured.getSemiCode());
                semiObject.put("name", semistructured.getSemiName());

                semiArray.add(semiObject);
            }

            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            try {
                response.getWriter().write(semiArray.toJSONString());
            } catch (IOException e) {
                logger.error("Cant write", e);
                response.setStatus(500);
            }
        }else if (machineGroup.getMachineProducts() != null && machineGroup.getMachineProducts().size() > 0){
            JSONArray productArray = new JSONArray();
            for (Product machineProduct : machineGroup.getMachineProducts()) {
                JSONObject productObject = new JSONObject();

                productObject.put("code", machineProduct.getProductCode());
                productObject.put("name", machineProduct.getSize() + machineProduct.getDesign() + machineProduct.getPr());

                productArray.add(productObject);
            }

            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            try {
                response.getWriter().write(productArray.toJSONString());
            } catch (IOException e) {
                logger.error("Cant write", e);
                response.setStatus(500);
            }
        }else {
            response.setStatus(404);
        }
    }

    @RequestMapping("/listMachines.do")
    public void listMachines(HttpServletRequest request, HttpServletResponse response){
        JSONArray jsonArray;
        try {
            String data = request.getParameter("groups");
            jsonArray = (JSONArray) new JSONParser().parse(data);
        }catch (Exception e){
            response.setStatus(400);
            return;
        }


        JSONArray sectionArray = new JSONArray();
        for (Object o : jsonArray) {
            JSONObject sectionObject = (JSONObject) o;
            Section section = new Section();
            section.setCode((String) sectionObject.get("code"));
            section = sectionManager.getSection(section);

            if (section == null){
                StaticController.sendError(response, 404, "قسمت مورد نظر یافت نشد");
                return;
            }

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

                if (machineGroup == null){
                    StaticController.sendError(response, 404, "گروه مورد نظر یافت نشد");
                    return;
                }

                JSONObject groupObj = new JSONObject();
                groupObj.put("code", machineGroup.getCode());
                groupObj.put("name", machineGroup.getName());

                if (machineGroup.getMachines() != null && machineGroup.getMachines().size() > 0){
                    JSONArray machineArray = new JSONArray();
                    for (Machine machine : machineGroup.getMachines()) {
                        JSONObject machineObj = new JSONObject();

                        machineObj.put("code", machine.getCode());
                        machineObj.put("name", machine.getName());

                        machineArray.add(machineObj);
                    }
                    groupObj.put("machines", machineArray);
                    groupsArr.add(groupObj);
                } else {
                    StaticController.sendError(response, 404, "دستگاهی در گروه (" + machineGroup.getCode() + " : " + machineGroup.getName() + ") یافت نشد");
                }

            }
            sectionObj.put("groups", groupsArr);
            sectionArray.add(sectionObj);
        }

        StaticController.sendData(response, sectionArray.toJSONString());
    }
}
