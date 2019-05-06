package controller;

import model.entity.*;
import model.service.*;
import org.apache.log4j.Logger;
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
 * Created by amir on 2/17/17.
 */
@Controller
@RequestMapping("/document/elasticity")
public class ElasticityController {
    @Autowired
    private RawMaterialManager rawMaterialManager;
    @Autowired
    private ProductManager productManager;
    @Autowired
    private SemistructuredManager semistructuredManager;
    @Autowired
    private MachineManager machineManager;
    @Autowired
    private ConstantManager constantManager;
    private static Logger logger = Logger.getLogger(ElasticityController.class.getName());

    @RequestMapping("/ajaxGet.do")
    public void ajaxGet(HttpServletRequest request, HttpServletResponse response){
        Map<String, String[]> map = request.getParameterMap();
        String[] code = map.get("code");
        String[] percent = map.get("percent");
        double tp = 0;
        for (String s : percent) {
            if (Double.parseDouble(s) < 0){
                response.setStatus(400);
                return;
            }
            tp += Double.parseDouble(s);
        }
        if (tp > 100){
            response.setStatus(400);
            return;
        }
        int shift;
        try {
            shift = Integer.parseInt(map.get("shift")[0]);
        }catch (Exception e){
            response.setStatus(400);
            return;
        }

        if (code != null && percent != null){
//            JSONObject jsonObject = calculateTotal(code, percent);
//            JSONObject jsonObject1 = calculateMachine(jsonObject, shift);

//            response.setContentType("text/JSON");
//            response.setCharacterEncoding("UTF-8");
//            try {
//                response.getWriter().write(jsonObject1.toJSONString());
//            } catch (IOException e) {
//                response.setStatus(500);
//            }
        }else {
            response.setStatus(400);
        }
    }

//    private JSONObject calculateTotal(String[] code, String[] percent){
//        ArrayList<Product> products = new ArrayList<>();
//
//        for (int i = 0; i < code.length; i++){
//            Product p = new Product();
//            p.setProductCode(code[i].trim());
//            try {
//                p = productManager.getProduct(p);
//            } catch (Exception e) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("error", 400);
//                return jsonObject;
//            }
//            p.setTotal(Double.parseDouble(percent[i]) / 100);
//            products.add(p);
//        }
//
////        List<Semistructured> semistructuredList;
////        try {
////            semistructuredList = semistructuredManager.getSemistructureds();
////        } catch (Exception e) {
////            logger.error("server error", e);
////            JSONObject jsonObject = new JSONObject();
////            jsonObject.put("error", 500);
////            return jsonObject;
////        }
////        for (Semistructured semistructured : semistructuredList) {
////            semistructured.setTotal(0);
////        }
//        List<RawMaterial> raws;
//        try {
//            raws = rawMaterialManager.getRawMaterials();
//        }catch (Exception e){
//            logger.error("server error", e);
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("error", 500);
//            return jsonObject;
//        }
//        for (RawMaterial raw : raws) {
//            raw.setTotallasttime(0);
//        }
//
////        for (Product product : products) {
////            if (product.getSemis() != null){
////                for (Semi semi : product.getSemis()) {
////                    for (Semistructured semistructured : semistructuredList) {
////                        if (semistructured.getSemiCode().equals(semi.getSemiCode())){
////                            semistructured.setTotal(semistructured.getTotal() + semi.getTotal() * product.getTotal());
////                            break;
////                        }
////                    }
////                }
////            }
////
////            if (product.getRaws() != null){
////                for (Raw raw : product.getRaws()) {
////                    for (RawMaterial rawMaterial : raws) {
////                        if (rawMaterial.getRawCode().equals(raw.getRawCode())){
////                            rawMaterial.setTotallasttime(rawMaterial.getTotallasttime() + raw.getWeight() * product.getTotal());
////                            break;
////                        }
////                    }
////                }
////            }
////        }
//
////        List<Semistructured> semistruct;
////        try {
////            semistruct = semistructuredManager.getSemistructureds();
////        } catch (Exception e) {
////            logger.error("server not respond", e);
////            JSONObject jsonObject = new JSONObject();
////            jsonObject.put("error", 500);
////            return jsonObject;
////        }
////        for (Semistructured semistructured : semistruct) {
////            for (Semistructured semistructured1 : semistructuredList) {
////                if (semistructured.getSemiCode().equals(semistructured1.getSemiCode())){
////                    semistructured.setTotal(semistructured1.getTotal());
////                    break;
////                }
////            }
////        }
//
//        while (true){
////            for (Semistructured semistructured : semistructuredList) {
////                if (semistructured.getSemis() != null){
////                    for (Semi semi : semistructured.getSemis()) {
////                        for (Semistructured semistructured1 : semistruct) {
////                            if (semistructured1.getSemiCode().equals(semi.getSemiCode())){
////                                semistructured1.setTotal(semistructured1.getTotal() + semi.getTotal() * semistructured.getTotal());
////                                for (Semistructured semistructured2 : semistructuredList) {
////                                    if (semistructured2.getSemiCode().equals(semistructured1.getSemiCode())){
////                                        semistructured2.setTotal(semistructured2.getTotal() + semi.getTotal() * semistructured.getTotal());
////                                        break;
////                                    }
////                                }
////                                break;
////                            }
////                        }
////                    }
////                }
////
////                if (semistructured.getRaws() != null){
////                    for (Raw raw : semistructured.getRaws()) {
////                        for (RawMaterial rawMaterial : raws) {
////                            if (rawMaterial.getRawCode().equals(raw.getRawCode())){
////                                rawMaterial.setTotallasttime(rawMaterial.getTotallasttime() + raw.getWeight() * semistructured.getTotal());
////                                break;
////                            }
////                        }
////                    }
////                }
////                semistructured.setTotal(0);
////            }
//            boolean bool = false;
////            for (Semistructured semistructured : semistructuredList) {
////                if (semistructured.getTotal() > 0){
////                    bool = true;
////                }
////            }
//            if (!bool){
//                break;
//            }
//        }
//
//        JSONArray jsonArray = new JSONArray();
//        List<RawMaterial> rawMaterialList;
//        try {
//            rawMaterialList = rawMaterialManager.getRawMaterials();
//        } catch (Exception e) {
//            logger.error("server not respond", e);
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("error", 500);
//            return jsonObject;
//        }
//
//        long min = Long.MAX_VALUE;
//        for (RawMaterial rawMaterial : raws) {
//            rawMaterial.setTotallasttime(Math.floor(rawMaterial.getTotal() / rawMaterial.getTotallasttime()));
//            if (rawMaterial.getTotallasttime() < min){
//                min = (long) rawMaterial.getTotallasttime();
//            }
//        }
//
//        for (Product product : products) {
//            product.setTotal(Math.floor((product.getTotal() / 100) * min));
//        }
//
//        JSONArray jsonArray2 = new JSONArray();
//        for (Semistructured semistructured : semistruct) {
//            JSONObject jsonObject = new JSONObject();
//
//            jsonObject.put("c", semistructured.getSemiCode());
//            jsonObject.put("n", semistructured.getSemiName());
//            jsonObject.put("t", semistructured.getTotal());
//
//            jsonArray2.add(jsonObject);
//        }
//
//        JSONArray jsonArray1 = new JSONArray();
//        for (Product product : products) {
//            JSONObject jsonObject = new JSONObject();
//
//            jsonObject.put("c", product.getProductCode());
//            jsonObject.put("n", product.getSize() + " " + product.getDesign() + " " + product.getPr());
//            jsonObject.put("t", product.getTotal());
//
//            jsonArray1.add(jsonObject);
//        }
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("r", jsonArray);
//        jsonObject.put("s", jsonArray2);
//        jsonObject.put("p", jsonArray1);
//
//        return jsonObject;
//    }

    private JSONObject calculateMachine(JSONObject jsonData, int shift){
        ArrayList<Semistructured> semistruct = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) jsonData.get("s");
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            Semistructured semistructured = new Semistructured();
            semistructured.setSemiCode((String) jsonObject.get("c"));
            semistructured.setTotal((Double) jsonObject.get("t"));
            semistruct.add(semistructured);
        }
        jsonArray = (JSONArray) jsonData.get("p");
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            Product product = new Product();
            product.setProductCode((String) jsonObject.get("c"));
            product.setTotal((Double) jsonObject.get("t"));
            products.add(product);
        }

        List<Machine> machines;
        try {
            machines = machineManager.getMachines();
        } catch (Exception e) {
            logger.error("server not respnod", e);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", 500);
            return jsonObject;
        }

        ArrayList<Machine> machines1 = new ArrayList<>();
        HashMap<String, Integer> integerHashMap = new HashMap<>();

        for (Machine machine : machines) {
//            if (integerHashMap.get(machine.getCenter()) != null){
//                integerHashMap.replace(machine.getCenter(), integerHashMap.get(machine.getCenter()) + 1);
//            }else {
//                integerHashMap.put(machine.getCenter(), 1);
//            }
        }

        for (Machine machine : machines) {
//            if (machine.getMachineSemis() != null){
//                for (MachineSemi machineSemi : machine.getMachineSemis()) {
//                    for (Semistructured semistructured : semistruct) {
//                        if (semistructured.getSemiCode().equals(machineSemi.getSemiCode())){
//                            Machine machine1 = new Machine();
////                            machine1.setCenter(machine.getCenter());
//                            machine1.setCapacity(semistructured.getTotal());
//                            boolean bool = true;
//                            for (Machine machine2 : machines1) {
////                                if (machine2.getCenter().equals(machine1.getCenter())){
////                                    machine2.setCapacity(machine2.getCapacity() + machine1.getCapacity());
////                                    bool = false;
////                                }
//                            }
//                            if (bool){
//                                machines1.add(machine1);
//                            }
//                        }
//                    }
//                }
//            }else if (machine.getMachineProducts() != null){
//                for (MachineProduct machineProduct : machine.getMachineProducts()) {
//                    for (Product product : products) {
//                        if (product.getProductCode().equals(machineProduct.getProductCode())){
//                            Machine machine1 = new Machine();
////                            machine1.setCenter(machine.getCenter());
//                            machine1.setCapacity(product.getTotal());
//                            boolean bool = true;
//                            for (Machine machine2 : machines1) {
////                                if (machine2.getCenter().equals(machine1.getCenter())){
////                                    machine2.setCapacity(machine2.getCapacity() + machine1.getCapacity());
////                                    bool = false;
////                                }
//                            }
//                            if (bool){
//                                machines1.add(machine1);
//                            }
//                        }
//                    }
//                }
//            }
        }

        JSONArray jsonArray1 = new JSONArray();
        for (Machine machine : machines) {
            for (Machine machine1 : machines1) {
//                if (machine1.getCenter().equals(machine.getCenter())){
//                    machine1.setUph(machine1.getUph() + machine.getUph());
//                }
            }
        }

        Date date1;
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy");
        try {
            date1 = simpleDateFormat1.parse(year);
        } catch (ParseException e) {
            logger.error("Cant parse");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", 500);
            return jsonObject;
        }

        Constant constant = new Constant();
        constant.setDate(date1);
        try {
            constant = constantManager.get(constant);
        }catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", 404);
            return jsonObject;
        }

        double min = Double.MAX_VALUE;
        for (Machine machine : machines1) {
            for (Machine machine1 : machines) {
//                if (machine.getCenter().equals(machine1.getCenter())){
//                    machine.setCapacity(machine.getCapacity() / machine1.getCapacity());
//                    break;
//                }
            }
            JSONObject jsonObject = new JSONObject();

//            jsonObject.put("c", machine.getCenter());
            jsonObject.put("cp", machine.getUph() * constant.getShiftHour() * shift);
            jsonObject.put("cn", machine.getCapacity());
            double d = machine.getCapacity() / (machine.getUph() * constant.getShiftHour() * shift);
            if (min > d){
                min = d;
            }
            jsonObject.put("d", d);

            jsonArray1.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m", jsonArray1);
        jsonObject.put("p", jsonData.get("p"));
        jsonObject.put("d", min);

        return jsonObject;
    }
}
