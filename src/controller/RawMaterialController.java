package controller;

import model.entity.*;
import model.service.PlaningTimesManager;
import model.service.RawMaterialManager;
import org.apache.log4j.Logger;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by amir on 11/19/16.
 */
@Controller
@RequestMapping("/document/rawmaterial")
public class RawMaterialController {
    @Autowired
    private PlaningTimesManager planingTimesManager;
    @Autowired
    private RawMaterialManager rawMaterialManager;
    @Autowired
    private EventController eventController;
    private static Logger logger = Logger.getLogger(RawMaterialController.class.getName());

    @RequestMapping(value = "/register.do")
    public void register(HttpServletRequest req, HttpServletResponse resp, @ModelAttribute RawMaterial rawMaterial){
        try {
            rawMaterialManager.registerRawMaterial(rawMaterial);
        }catch (Exception e){
            logger.error("cant persist", e);
            resp.setStatus(400);
        }
    }

    @RequestMapping(value = "/entery.do")
    public void enter(HttpServletRequest req, HttpServletResponse resp){
        String code, batchNumber, loadNumber;
        double total;
        Date time;
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(req.getParameter("data"));
            code = (String) jsonObject.get("code");
            batchNumber = (String) jsonObject.get("batchNumber");
            loadNumber = (String) jsonObject.get("loadNumber");
            time = new Date((long) jsonObject.get("date"));
            total = Double.parseDouble(jsonObject.get("total") + "");
        }catch (Exception e){
            resp.setStatus(400);
            return;
        }

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawCode(code);
        rawMaterial.setTotal(total);
        rawMaterial.setTotallasttime(total);
        rawMaterial.setDate(time);

        RawMaterialInTimes rawMaterialInTimes = new RawMaterialInTimes();
        rawMaterialInTimes.setBatchNumber(batchNumber);
        rawMaterialInTimes.setLoadNumber(loadNumber);
        rawMaterialInTimes.setTotal(total);
        rawMaterialInTimes.setDate(time);

        ArrayList<RawMaterialInTimes> rawMaterialInTimes1 = new ArrayList<>();
        rawMaterialInTimes1.add(rawMaterialInTimes);

        rawMaterial.setRawMaterialInTimes(rawMaterialInTimes1);

        RawMaterial rawMaterial1 = new RawMaterial();
        rawMaterial1.setRawCode(rawMaterial.getRawCode());
        try {
            rawMaterial1 = rawMaterialManager.getRawMaterial(rawMaterial1);
        } catch (Exception e) {
            resp.setStatus(404);
            return;
        }
        rawMaterial.setRawName(rawMaterial1.getRawName());

        eventController.addEvent(rawMaterial, "add");

        try {
            rawMaterialManager.enterRawMaterial(rawMaterial);
        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @RequestMapping("/outData.do")
    public void outData(HttpServletRequest req, HttpServletResponse resp){
        Map<String, String[]> map = req.getParameterMap();
        String date;
        int shift;
        try {
            date = map.get("date")[0];
            shift = Integer.parseInt(map.get("shift")[0]);
        }catch (Exception e){
            resp.setStatus(403);
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
        Date date1;
        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            logger.error("Cant parse date", e);
            resp.setStatus(500);
            return;
        }

        PlaningTimes planingTimes = new PlaningTimes();
        planingTimes.setTime(date1);
        planingTimes.setEdited(false);

        try {
            planingTimes = planingTimesManager.getTime(planingTimes);
        } catch (Exception e) {
            logger.error("database problem", e);
            resp.setStatus(500);
            return;
        }

        List<RawMaterial> rawMaterials;
        try {
            rawMaterials = rawMaterialManager.getRawMaterials();
        } catch (Exception e) {
            logger.error("database problem", e);
            resp.setStatus(500);
            return;
        }

        JSONArray jsonArray = new JSONArray();

        if (planingTimes.getPlaningShifts() != null){
            for (PlaningShift planingShift : planingTimes.getPlaningShifts()) {
                if (planingShift.getShift() == shift){
                    for (PlaningRaw planingRaw : planingShift.getPlaningRaws()) {
                        for (RawMaterial rawMaterial : rawMaterials) {
                            if (rawMaterial.getRawCode().equals(planingRaw.getCode())){
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("code", rawMaterial.getRawCode());
                                jsonObject.put("name", rawMaterial.getRawName());
                                jsonObject.put("total", rawMaterial.getTotal());
                                jsonObject.put("plan", planingRaw.getTotal());

                                jsonArray.add(jsonObject);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/JSON");
        try {
            resp.getWriter().write(jsonArray.toJSONString());
        } catch (IOException e) {
            logger.error("resp writer", e);
            resp.setStatus(500);
        }
    }

    @RequestMapping("/ajaxData.do")
    public void ajaxData(HttpServletRequest req, HttpServletResponse resp){
        List<RawMaterial> rawMaterials = rawMaterialManager.getRawMaterials();
        JSONArray jsonArray = new JSONArray();
        for (RawMaterial rawMaterial : rawMaterials) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", rawMaterial.getRawCode());
            jsonObject.put("name", rawMaterial.getRawName());
            jsonArray.add(jsonObject);
        }

        StaticController.sendData(resp, jsonArray.toJSONString());
    }

    @RequestMapping("/ajaxIdentity.do")
    public void ajaxIdentity(HttpServletRequest req, HttpServletResponse resp){
        JSONObject dataObj;
        try {
            dataObj = (JSONObject) new JSONParser().parse(req.getParameter("data"));
        }catch (Exception e){
            resp.setStatus(400);
            return;
        }

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setRawCode((String) dataObj.get("code"));

        rawMaterial = rawMaterialManager.getRawMaterial(rawMaterial);

        if (rawMaterial == null){
            StaticController.sendError(resp, 404, "ماده اولیه مورد نظر یافت نشد");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", rawMaterial.getRawCode());
        jsonObject.put("name", rawMaterial.getRawName());
        jsonObject.put("company", rawMaterial.getCompanyName());
        if (rawMaterial.getInneroutter() == 1){
            jsonObject.put("inout", "خارجی");
        }else if (rawMaterial.getInneroutter() == 0){
            jsonObject.put("inout", "داخلی");
        }
        try {
            jsonObject.put("tlo", rawMaterial.getDate().getTime());
        }catch (Exception e){

        }
        try {
            jsonObject.put("op", rawMaterial.getOrderpoint());
        }catch (Exception e){

        }
        try {
            jsonObject.put("total", rawMaterial.getTotallasttime());
        }catch (Exception e){

        }

        StaticController.sendData(resp, jsonObject.toJSONString());
    }

    @RequestMapping("/stock.do")
    public void stock(HttpServletRequest req, HttpServletResponse resp){
        List<RawMaterial> rawMaterials = rawMaterialManager.getRawMaterials();

        JSONArray rawmaterialArray = new JSONArray();
        for (RawMaterial rawMaterial : rawMaterials) {
            JSONObject rawObj = new JSONObject();

            rawObj.put("code", rawMaterial.getRawCode());
            rawObj.put("name", rawMaterial.getRawName());
            rawObj.put("total", rawMaterial.getTotal());
            if (rawMaterial.getTotal() < rawMaterial.getOrderpoint()){
                rawObj.put("color", "#d50000");
            }

            rawmaterialArray.add(rawObj);
        }

        resp.setContentType("text/json");
        resp.setCharacterEncoding("utf-8");
        try {
            resp.getWriter().write(rawmaterialArray.toJSONString());
        } catch (IOException e) {
            logger.error("cant write", e);
            resp.setStatus(500);
        }
    }
}
