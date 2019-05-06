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
 * Created by amir on 12/25/16.
 */
@Controller
@RequestMapping("/document/reports")
public class ReportController {
    @Autowired
    private ProductManager productManager;
    @Autowired
    private ProductInManager productInManager;
    @Autowired
    private ProductWasteManager productWasteManager;
    @Autowired
    private ProductTimeSellManager productTimeSellManager;
    @Autowired
    private RawMaterialManager rawMaterialManager;
    @Autowired
    private RawMaterialInManager rawMaterialInManager;
    @Autowired
    private RawMaterialOutManager rawMaterialOutManager;
    @Autowired
    private SemistructuredManager semistructuredManager;
    @Autowired
    private SemistructuredInManager semistructuredInManager;
    @Autowired
    private SemistructuredWasteManager semistructuredWasteManager;
    @Autowired
    private MachineManager machineManager;
    @Autowired
    private SectionManager sectionManager;
    private static Logger logger = Logger.getLogger(ReportController.class.getName());
    private int status = 404;

    @RequestMapping("/getData.do")
    public void getData(HttpServletRequest request, HttpServletResponse response){
        status = 404;
        Map<String, String[]> map = request.getParameterMap();

        String data = map.get("data")[0];
        String data2 = map.get("data2")[0];
        String[] data3 = map.get("data3");
        String start = map.get("start")[0];
        String end = map.get("end")[0];
        String viewMode = map.get("viewMode")[0];

        JSONArray retobj;

        switch (data){
            case "product":{
                retobj = product(data2, data3, start, end, viewMode);
                break;
            }
            case "rawmaterial":{
                retobj = rawMaterial(data2, data3, start, end, viewMode);
                break;
            }
            case "semistructured":{
                retobj = semistructured(data2, data3, start, end, viewMode);
                break;
            }
            case "machine":{
                retobj = machine(data2, data3, start, end, viewMode);
                break;
            }
            default:{
                retobj = null;
            }
        }


        if (retobj != null && retobj.size() > 0){
            response.setContentType("text/JSON");
            response.setCharacterEncoding("UTF-8");
            try {
                response.getWriter().write(retobj.toJSONString());
            } catch (IOException e) {
                logger.error("response Cant write", e);
                response.setStatus(500);
            }
        }else {
            response.setStatus(status);
        }
    }

    public JSONArray product(String data, String[] data3, String start, String end, String viewMode){
        if (data.equals("products")){
            boolean all = false;
            for (String s : data3) {
                if (s.equals("all")){
                    all= true;
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
            Date startDate, endDate;
            try {
                startDate = simpleDateFormat.parse(start);
                endDate = simpleDateFormat.parse(end);
            } catch (ParseException e) {
                status = 400;
                return null;
            }

            JSONArray json = new JSONArray();
            if (all){
                List<ProductInTime> productInTimes;
                try {
                    productInTimes = productInManager.getAllTimes(startDate, endDate);
                } catch (Exception e) {
                    status = 404;
                    return null;
                }

                Map<String, Double> map = getDoubleDate(startDate ,endDate, viewMode);

                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat();
                switch (viewMode){
                    case "month":{
                        simpleDateFormat1.applyPattern("MMMMM");
                        break;
                    }
                    case "year":{
                        simpleDateFormat1.applyPattern("yyyy");
                        break;
                    }
                    default:{
                        simpleDateFormat1.applyPattern(StaticController.patternDate);
                    }
                }

                for (ProductInTime productInTime : productInTimes) {
                    map.put(simpleDateFormat1.format(productInTime.getDate()),
                            map.get(simpleDateFormat1.format(productInTime.getDate()))
                                    + productInTime.getTotal());
                }

                return getJSON(map);
            }else {
                ArrayList<Product> productIns = new ArrayList<>();
                for (String s : data3) {
                    Product productIn = new Product();
                    productIn.setProductCode(s);
                    productIns.add(productIn);
                }
                for (Product productIn : productIns) {

                    List<ProductInTime> productInTimes;
                    try {
                        productInTimes = productInManager.getTimesBetween(productIn, startDate, endDate);
                    } catch (Exception e) {
                        status = 404;
                        return null;
                    }

                    if (productInTimes == null){
                        status = 404;
                        return null;
                    }else {
                        for (ProductInTime productInTime : productInTimes) {
                            boolean jsonCheck = false;
                            for (Object o : json) {
                                JSONObject jsonObject = (JSONObject) o;
                                if (jsonObject.get("date").equals(productInTime.getDate().getTime())){
                                    double total = Double.parseDouble(jsonObject.get(productIn.getProductCode()) + "");
                                    total += productInTime.getTotal();
                                    jsonObject.replace(productIn.getProductCode(), total);
                                    jsonCheck = true;
                                    break;
                                }
                            }
                            if (!jsonCheck){
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("date", productInTime.getDate().getTime());
                                for (Product in : productIns) {
                                    jsonObject.put(in.getProductCode(), 0);
                                }
                                jsonObject.replace(productIn.getProductCode(), productInTime.getTotal());
                                json.add(jsonObject);
                            }
                        }
                    }
                }
                return json;
            }
        }else if (data.equals("sells")){
            boolean all = false;
            for (String s : data3) {
                if (s.equals("all")){
                    all= true;
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
            Date startDate, endDate;
            try {
                startDate = simpleDateFormat.parse(start);
                endDate = simpleDateFormat.parse(end);
            } catch (ParseException e) {
                status = 400;
                return null;
            }

            JSONArray json = new JSONArray();
            if (all){
                List<ProductOutTime> productOutTimes;
                try {
                    productOutTimes = productTimeSellManager.getAllTimes(startDate, endDate);
                } catch (Exception e) {
                    status = 404;
                    return null;
                }

                Map<String, Double> map = getDoubleDate(startDate ,endDate, viewMode);

                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat();
                switch (viewMode){
                    case "month":{
                        simpleDateFormat1.applyPattern("MMMMM");
                        break;
                    }
                    case "year":{
                        simpleDateFormat1.applyPattern("yyyy");
                        break;
                    }
                    default:{
                        simpleDateFormat1.applyPattern(StaticController.patternDate);
                    }
                }

                for (ProductOutTime productOutTime : productOutTimes) {
                    map.put(simpleDateFormat1.format(productOutTime.getDate()),
                            map.get(simpleDateFormat1.format(productOutTime.getDate()))
                                    + productOutTime.getTotal());
                }

                return getJSON(map);
            }else {
                ArrayList<Product> productOuts = new ArrayList<>();
                for (String s : data3) {
                    Product productOut = new Product();
                    productOut.setProductCode(s);
                    productOuts.add(productOut);
                }
                for (Product productOut : productOuts) {
                    List<ProductOutTime> productOutTimes;
                    try {
                        productOutTimes = productTimeSellManager.getTimesBetween(productOut, startDate, endDate);
                    } catch (Exception e) {
                        status = 404;
                        return null;
                    }
                    if (productOutTimes == null){
                        status = 404;
                        return null;
                    }else {
                        for (ProductOutTime productOutTime : productOutTimes) {
                            boolean jsonCheck = false;
                            for (Object o : json) {
                                JSONObject jsonObject = (JSONObject) o;
                                if (jsonObject.get("date").equals(productOutTime.getDate().getTime())){
                                    double total = Double.parseDouble(jsonObject.get(productOut.getProductCode()) + "");
                                    total += productOutTime.getTotal();
                                    jsonObject.replace(productOut.getProductCode(), total);
                                    jsonCheck = true;
                                    break;
                                }
                            }
                            if (!jsonCheck){
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("date", productOutTime.getDate().getTime());
                                for (Product out : productOuts) {
                                    jsonObject.put(out.getProductCode(), 0);
                                }
                                jsonObject.replace(productOut.getProductCode(), productOutTime.getTotal());
                                json.add(jsonObject);
                            }
                        }
                    }
                }
                return json;
            }
        }else if (data.equals("isolated")){
            boolean all = false;
            for (String s : data3) {
                if (s.equals("all")){
                    all= true;
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
            Date startDate, endDate;
            try {
                startDate = simpleDateFormat.parse(start);
                endDate = simpleDateFormat.parse(end);
            } catch (ParseException e) {
                status = 400;
                return null;
            }

            JSONArray json = new JSONArray();
            if (all){
                List<ProductWasteTime> productWasteTimes;
                try {
                    productWasteTimes = productWasteManager.getAllTimes(startDate, endDate);
                } catch (Exception e) {
                    status = 404;
                    return null;
                }

                Map<String , Double> map = getDoubleDate(startDate, endDate, viewMode);

                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat();
                switch (viewMode){
                    case "month":{
                        simpleDateFormat1.applyPattern("MMMMM");
                        break;
                    }
                    case "year":{
                        simpleDateFormat1.applyPattern("yyyy");
                        break;
                    }
                    default:{
                        simpleDateFormat1.applyPattern(StaticController.patternDate);
                    }
                }

                for (ProductWasteTime productWasteTime : productWasteTimes) {
                    map.put(simpleDateFormat1.format(productWasteTime.getDate()), map.get(simpleDateFormat1.format(productWasteTime.getDate())) + productWasteTime.getTotal());
                }

                return getJSON(map);
            }else {
                ArrayList<Product> productWastes = new ArrayList<>();
                for (String s : data3) {
                    Product productWaste = new Product();
                    productWaste.setProductCode(s);
                    productWastes.add(productWaste);
                }
                for (Product productWaste : productWastes) {
                    List<ProductWasteTime> productWasteTimes;
                    try {
                        productWasteTimes = productWasteManager.getProductWasteTime(productWaste, startDate, endDate);
                    } catch (Exception e) {
                        status = 404;
                        return null;
                    }

                    if (productWasteTimes == null){
                        status = 404;
                        return null;
                    }else {
                        for (ProductWasteTime productWasteTime : productWasteTimes) {
                            boolean jsonCheck = false;
                            for (Object o : json) {
                                JSONObject jsonObject = (JSONObject) o;
                                if (jsonObject.get("date").equals(productWasteTime.getDate().getTime())){
                                    double total = Double.parseDouble(jsonObject.get(productWaste.getProductCode()) + "");
                                    total += productWasteTime.getTotal();
                                    jsonObject.replace(productWaste.getProductCode(), total);
                                    jsonCheck = true;
                                    break;
                                }
                            }
                            if (!jsonCheck){
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("date", productWasteTime.getDate().getTime());
                                for (Product waste : productWastes) {
                                    jsonObject.put(waste.getProductCode(), 0);
                                }
                                jsonObject.replace(productWaste.getProductCode(), productWasteTime.getTotal());
                                json.add(jsonObject);
                            }
                        }
                    }
                }
                return json;
            }
        }else {
            return null;
        }
    }

    public JSONArray rawMaterial(String data, String[] data3, String start, String end, String viewMode){
        if (data.equals("buy")){
            boolean all = false;
            for (String s : data3) {
                if (s.equals("all")){
                    all= true;
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
            Date startDate, endDate;
            try {
                startDate = simpleDateFormat.parse(start);
                endDate = simpleDateFormat.parse(end);
            } catch (ParseException e) {
                logger.error("Cant parse dates", e);
                return null;
            }

            JSONArray json = new JSONArray();
            if (all){
                List<RawMaterialInTimes> rawMaterialInTimesList;
                try {
                    rawMaterialInTimesList = rawMaterialInManager.getAllTimes(startDate, endDate);
                } catch (Exception e) {
                    status = 404;
                    return null;
                }

                Map<String, Double> map = getDoubleDate(startDate, endDate, viewMode);

                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat();
                switch (viewMode){
                    case "month":{
                        simpleDateFormat1.applyPattern("MMMMM");
                        break;
                    }
                    case "year":{
                        simpleDateFormat1.applyPattern("yyyy");
                        break;
                    }
                    default:{
                        simpleDateFormat1.applyPattern(StaticController.patternDate);
                    }
                }

                for (RawMaterialInTimes rawMaterialInTimes : rawMaterialInTimesList) {
                    map.put(simpleDateFormat1.format(rawMaterialInTimes.getDate()), map.get(simpleDateFormat1.format(rawMaterialInTimes.getDate())) + rawMaterialInTimes.getTotal());
                }

                return getJSON(map);
            }else {
                ArrayList<RawMaterial> rawMaterialIns = new ArrayList<>();
                for (String s : data3) {
                    RawMaterial rawMaterialIn = new RawMaterial();
                    rawMaterialIn.setRawCode(s);
                    rawMaterialIns.add(rawMaterialIn);
                }
                for (RawMaterial rawMaterialIn : rawMaterialIns) {

                    List<RawMaterialInTimes> rawMaterialInTimesList;
                    try{
                        rawMaterialInTimesList = rawMaterialInManager.getTimes(rawMaterialIn, startDate, endDate);
                    }catch (Exception e){
                        return null;
                    }

                    if (rawMaterialInTimesList == null){
                        status = 404;
                    }else {
                        for (RawMaterialInTimes rawMaterialInTimes : rawMaterialInTimesList) {
                            boolean jsonCheck = false;
                            for (Object o : json) {
                                JSONObject jsonObject = (JSONObject) o;
                                if (jsonObject.get("date").equals(rawMaterialInTimes.getDate().getTime())){
                                    double total = Double.parseDouble(jsonObject.get(rawMaterialIn.getRawCode()) + "");
                                    total += rawMaterialInTimes.getTotal();
                                    jsonObject.replace(rawMaterialIn.getRawCode(), total);
                                    jsonCheck = true;
                                    break;
                                }
                            }
                            if (!jsonCheck){
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("date", rawMaterialInTimes.getDate().getTime());
                                for (RawMaterial materialIn : rawMaterialIns) {
                                    jsonObject.put(materialIn.getRawCode(), 0);
                                }
                                jsonObject.replace(rawMaterialIn.getRawCode(), rawMaterialInTimes.getTotal());
                                json.add(jsonObject);
                            }
                        }
                    }
                }
                return json;
            }
        }else if (data.equals("use")){
            boolean all = false;
            for (String s : data3) {
                if (s.equals("all")){
                    all= true;
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
            Date startDate, endDate;
            try {
                startDate = simpleDateFormat.parse(start);
                endDate = simpleDateFormat.parse(end);
            } catch (ParseException e) {
                logger.error("Cant parse dates", e);
                return null;
            }

            JSONArray json = new JSONArray();
            if (all){
                List<RawMaterialOutTimes> rawMaterialOutTimes;
                try {
                    rawMaterialOutTimes = rawMaterialOutManager.getAllTimes(startDate, endDate);
                } catch (Exception e) {
                    status = 404;
                    return null;
                }

                Map<String, Double> map = getDoubleDate(startDate, endDate, viewMode);

                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat();
                switch (viewMode){
                    case "month":{
                        simpleDateFormat1.applyPattern("MMMMM");
                        break;
                    }
                    case "year":{
                        simpleDateFormat1.applyPattern("yyyy");
                        break;
                    }
                    default:{
                        simpleDateFormat1.applyPattern(StaticController.patternDate);
                    }
                }

                for (RawMaterialOutTimes rawMaterialOutTime : rawMaterialOutTimes) {
                    map.put(simpleDateFormat1.format(rawMaterialOutTime.getDate()), map.get(simpleDateFormat1.format(rawMaterialOutTime.getDate()) + rawMaterialOutTime.getTotal()));
                }

                return getJSON(map);
            }else {
                ArrayList<RawMaterial> rawMaterialOuts = new ArrayList<>();
                for (String s : data3) {
                    RawMaterial rawMaterialOut = new RawMaterial();
                    rawMaterialOut.setRawCode(s);
                    rawMaterialOuts.add(rawMaterialOut);
                }
                for (RawMaterial rawMaterialOut : rawMaterialOuts) {
                    List<RawMaterialOutTimes> rawMaterialOutTimes;
                    try {
                        rawMaterialOutTimes = rawMaterialOutManager.getTimes(rawMaterialOut, startDate, endDate);
                    } catch (Exception e) {
                        status = 404;
                        return null;
                    }

                    if (rawMaterialOutTimes == null){
                        status = 404;

                    }else {
                        for (RawMaterialOutTimes rawMaterialOutTime : rawMaterialOutTimes) {
                            boolean jsonCheck = false;
                            for (Object o : json) {
                                JSONObject jsonObject = (JSONObject) o;
                                if (jsonObject.get("date").equals(rawMaterialOutTime.getDate().getTime())){
                                    double total = Double.parseDouble(jsonObject.get(rawMaterialOut.getRawCode()) + "");
                                    total += rawMaterialOutTime.getTotal();
                                    jsonObject.replace(rawMaterialOut.getRawCode(), total);
                                    jsonCheck = true;
                                    break;
                                }
                            }
                            if (!jsonCheck){
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("date", rawMaterialOutTime.getDate().getTime());
                                for (RawMaterial materialOut : rawMaterialOuts) {
                                    jsonObject.put(materialOut.getRawCode(), 0);
                                }
                                jsonObject.replace(rawMaterialOut.getRawCode(), rawMaterialOutTime.getTotal());
                                json.add(jsonObject);
                            }
                        }
                    }
                }
                return json;
            }
        }else {
            return null;
        }
    }

    public JSONArray semistructured(String data, String[] data3, String start, String end, String viewMode){
        if (data.equals("products")){
            boolean all = false;
            for (String s : data3) {
                if (s.equals("all")){
                    all= true;
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
            Date startDate, endDate;
            try {
                startDate = simpleDateFormat.parse(start);
                endDate = simpleDateFormat.parse(end);
            } catch (ParseException e) {
                status = 400;
                return null;
            }

            JSONArray json = new JSONArray();
            if (all){
                List<SemistructuredInTimes> semistructuredInTimes;
                try {
                    semistructuredInTimes = semistructuredInManager.getAlltimes(startDate, endDate);
                } catch (Exception e) {
                    status = 404;
                    return null;
                }

                Map<String, Double> map = getDoubleDate(startDate, endDate, viewMode);

                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat();
                switch (viewMode){
                    case "month":{
                        simpleDateFormat1.applyPattern("MMMMM");
                        break;
                    }
                    case "year":{
                        simpleDateFormat1.applyPattern("yyyy");
                        break;
                    }
                    default:{
                        simpleDateFormat1.applyPattern(StaticController.patternDate);
                    }
                }

                for (SemistructuredInTimes semistructuredInTime : semistructuredInTimes) {
                    map.put(simpleDateFormat1.format(semistructuredInTime.getDate()),
                            map.get(simpleDateFormat1.format(semistructuredInTime.getDate()))
                                    + semistructuredInTime.getTotal());
                }

                return getJSON(map);
            }else {
                ArrayList<Semistructured> semistructuredIns = new ArrayList<>();
                for (String s : data3) {
                    Semistructured semistructuredIn = new Semistructured();
                    semistructuredIn.setSemiCode(s);
                    semistructuredIns.add(semistructuredIn);
                }
                for (Semistructured semistructuredIn : semistructuredIns) {
                    List<SemistructuredInTimes> semistructuredInTimes;
                    try {
                        semistructuredInTimes = semistructuredInManager.getSemiTime(semistructuredIn, startDate, endDate);
                    } catch (Exception e) {
                        status = 404;
                        return null;
                    }

                    if (semistructuredInTimes == null){
                        status = 404;
                        return null;
                    }else {
                        for (SemistructuredInTimes semistructuredInTime : semistructuredInTimes) {
                            boolean jsonCheck = false;
                            for (Object o : json) {
                                JSONObject jsonObject = (JSONObject) o;
                                if (jsonObject.get("date").equals(semistructuredInTime.getDate().getTime())){
                                    double total = Double.parseDouble(jsonObject.get(semistructuredIn.getSemiCode()) + "");
                                    total += semistructuredInTime.getTotal();
                                    jsonObject.replace(semistructuredIn.getSemiCode(), total);
                                    jsonCheck = true;
                                    break;
                                }
                            }
                            if (!jsonCheck){
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("date", semistructuredInTime.getDate().getTime());
                                for (Semistructured in : semistructuredIns) {
                                    jsonObject.put(in.getSemiCode(), 0);
                                }
                                jsonObject.replace(semistructuredIn.getSemiCode(), semistructuredInTime.getTotal());
                                json.add(jsonObject);
                            }
                        }
                    }
                }
                return json;
            }
        }else if (data.equals("wastes")){
            boolean all = false;
            for (String s : data3) {
                if (s.equals("all")){
                    all= true;
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
            Date startDate, endDate;
            try {
                startDate = simpleDateFormat.parse(start);
                endDate = simpleDateFormat.parse(end);
            } catch (ParseException e) {
                logger.error("Cant parse dates", e);
                return null;
            }

            JSONArray json = new JSONArray();
            if (all){
                List<SemistructuredWasteTime> semistructuredWasteTimes;
                try {
                    semistructuredWasteTimes = semistructuredWasteManager.getAllTimes(startDate, endDate);
                } catch (Exception e) {
                    status = 404;
                    return null;
                }

                Map<String, Double> map = getDoubleDate(startDate, endDate, viewMode);

                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat();
                switch (viewMode){
                    case "month":{
                        simpleDateFormat1.applyPattern("MMMMM");
                        break;
                    }
                    case "year":{
                        simpleDateFormat1.applyPattern("yyyy");
                        break;
                    }
                    default:{
                        simpleDateFormat1.applyPattern(StaticController.patternDate);
                    }
                }

                for (SemistructuredWasteTime semistructuredWasteTime : semistructuredWasteTimes) {
                    map.put(simpleDateFormat1.format(semistructuredWasteTime.getDate()),
                            map.get(simpleDateFormat1.format(semistructuredWasteTime.getDate()))
                                    + semistructuredWasteTime.getTotal());
                }

                return getJSON(map);
            }else {
                ArrayList<Semistructured> semistructuredWastes = new ArrayList<>();
                for (String s : data3) {
                    Semistructured semistructuredWaste = new Semistructured();
                    semistructuredWaste.setSemiCode(s);
                    semistructuredWastes.add(semistructuredWaste);
                }
                for (Semistructured semistructuredWaste : semistructuredWastes) {
                    List<SemistructuredWasteTime> semistructuredWasteTimes;
                    try {
                        semistructuredWasteTimes = semistructuredWasteManager.getSemiTime(semistructuredWaste, startDate, endDate);
                    } catch (Exception e) {
                        status = 404;
                        return null;
                    }

                    if (semistructuredWasteTimes == null){
                        status = 404;
                        return null;
                    }else {
                        for (SemistructuredWasteTime semistructuredWasteTime : semistructuredWasteTimes) {
                            boolean jsonCheck = false;
                            for (Object o : json) {
                                JSONObject jsonObject = (JSONObject) o;
                                if (jsonObject.get("date").equals(semistructuredWasteTime.getDate().getTime())){
                                    double total = Double.parseDouble(jsonObject.get(semistructuredWaste.getSemiCode()) + "");
                                    total += semistructuredWasteTime.getTotal();
                                    jsonObject.replace(semistructuredWaste.getSemiCode(), total);
                                    jsonCheck = true;
                                    break;
                                }
                            }
                            if (!jsonCheck){
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("date", semistructuredWasteTime.getDate().getTime());
                                for (Semistructured waste : semistructuredWastes) {
                                    jsonObject.put(waste.getSemiCode(), 0);
                                }
                                jsonObject.replace(semistructuredWaste.getSemiCode(), semistructuredWasteTime.getTotal());
                                json.add(jsonObject);
                            }
                        }
                    }

                }
                return json;
            }
        }else {
            return null;
        }
    }

    public JSONArray machine(String data, String[] data3, String start, String end, String viewMode){

        return null;
    }

    @RequestMapping("/list.do")
    public void list(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        if (data != null && !data.trim().equals("")){
            JSONArray jsonArray = new JSONArray();
            switch (data){
                case "product":{
                    try {
                        List<Product> products = productManager.getProducts();
                        for (Product product : products) {
                            jsonArray.add(product.getProductCode());
                        }
                    } catch (Exception e) {
                        logger.error(e);
                        response.setStatus(500);
                        return;
                    }
                    break;
                }
                case "semistructured":{
                    try {
                        List<Section> sections = sectionManager.getSections();
                        for (Section section : sections) {
                            List<MachineGroup> machineGroups = section.getMachineGroups();
                            for (MachineGroup machineGroup : machineGroups) {
                                List<Semistructured> semistructureds = machineGroup.getMachineSemis();
                                for (Semistructured semistructured : semistructureds) {
                                    jsonArray.add(semistructured.getSemiCode());
                                }
                            }
                        }
                    }catch (Exception e){
                        logger.error(e);
                        response.setStatus(500);
                        return;
                    }
                    break;
                }
                case "rawmaterial":{
                    try {
                        List<RawMaterial> rawMaterials = rawMaterialManager.getRawMaterials();
                        for (RawMaterial rawMaterial : rawMaterials) {
                            jsonArray.add(rawMaterial.getRawCode());
                        }
                    }catch (Exception e){
                        logger.error(e);
                        response.setStatus(500);
                        return;
                    }
                    break;
                }
                case "machine":{
                    try {
                        List<Machine> machines = machineManager.getMachines();
                        for (Machine machine : machines) {
                            jsonArray.add(machine.getCode());
                        }
                    }catch (Exception e){
                        logger.error(e);
                        response.setStatus(500);
                        return;
                    }
                    break;
                }
                default:{
                    jsonArray = null;
                }
            }
            if (jsonArray != null){
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/JSON");
                try {
                    response.getWriter().write(jsonArray.toJSONString());
                } catch (IOException e) {
                    logger.error("response Cant write", e);
                    response.setStatus(500);
                }
            }else {
                response.setStatus(400);
            }
        }else {
            response.setStatus(400);
        }
    }

    private Map<String, Double> getDoubleDate(Date startDate, Date endDate, String viewMode){
        Map<String, Double> map = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        Calendar startCalender = Calendar.getInstance();
        Calendar endCalender = Calendar.getInstance();
        startCalender.setTime(startDate);
        endCalender.setTime(endDate);

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat();
        switch (viewMode){
            case "month":{
                simpleDateFormat1.applyPattern("MMMMM");
                break;
            }
            case "year":{
                simpleDateFormat1.applyPattern("yyyy");
                break;
            }
            default:{
                simpleDateFormat1.applyPattern(StaticController.patternDate);
            }
        }

        while (calendar.before(endCalender)){
            map.put(simpleDateFormat1.format(calendar.getTime()), 0.0);

            switch (viewMode){
                case "month":{
                    calendar.add(Calendar.MONTH, 1);
                    break;
                }
                case "year":{
                    calendar.add(Calendar.YEAR, 1);
                    break;
                }
                default:{
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        }

        return map;
    }

    private JSONArray getJSON(Map<String, Double> map){
        JSONArray jsonArray = new JSONArray();
        for (String s : map.keySet()) {
            if (map.get(s) > 0.0){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("date", s);
                jsonObject.put("total", map.get(s));

                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }
}
