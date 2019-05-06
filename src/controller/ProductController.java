package controller;

import model.entity.*;
import model.entity.Process;
import model.service.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
 * Created by amir on 11/20/16.
 */
@Controller
@RequestMapping("/document/product")
public class ProductController {
    @Autowired
    private RawMaterialManager rawMaterialManager;
    @Autowired
    private ProductManager productManager;
    @Autowired
    private ProductInManager productInManager;
    @Autowired
    private ProductWasteManager productWasteManager;
    @Autowired
    private ProductTimeSellManager productTimeSellManager;
    @Autowired
    private SemistructuredManager semistructuredManager;
    @Autowired
    private BudgetManager budgetManager;
    @Autowired
    private EventController eventController;
    @Autowired
    private ProcessManager processManager;

    private static Logger logger = Logger.getLogger(ProductController.class.getName());

    @RequestMapping(value = "/entery.do")
    public void enter(HttpServletRequest req, HttpServletResponse resp){
        String code, batchNumber;
        double total;
        int degree;
        Date date;
        try {
            JSONObject dataObj = (JSONObject) new JSONParser().parse(req.getParameter("data"));
            code = (String) dataObj.get("code");
            batchNumber = (String) dataObj.get("batchNumber");
            date = new Date((Long) dataObj.get("date"));
            total = (Double) dataObj.get("total");
            degree = (Integer) dataObj.get("degree");
        }catch (Exception e){
            resp.setStatus(400);
            return;
        }

        Product product = new Product();
        product.setProductCode(code);
        try {
            product = productManager.getProduct(product);
        } catch (Exception e) {
            resp.setStatus(400);
            return;
        }

        ProductDegree productDegree = new ProductDegree();
        ProductDegreeTime productDegreeTime = new ProductDegreeTime();
        ProductInTime productInTime = new ProductInTime();

        //product degrees set
        productDegree.setDegree(degree);
        productDegreeTime.setDegree(degree);
        productDegree.setTotal(total);
        productDegreeTime.setTotal(total);

        productInTime.setDate(date);
        productInTime.setTotal(total);
        productInTime.setBatchNumber(batchNumber);

        ArrayList<ProductDegree> productDegrees = new ArrayList<>();
        ArrayList<ProductDegreeTime> productDegreeTimes = new ArrayList<>();
        ArrayList<ProductInTime> productInTimes = new ArrayList<>();

        productDegrees.add(productDegree);
        productDegreeTimes.add(productDegreeTime);
        product.setProductDegrees(productDegrees);
        product.setTotal(total);
        productInTime.setProductDegreeTimes(productDegreeTimes);

        productInTimes.add(productInTime);

        product.setProductInTimes(productInTimes);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        PersianCalendar persianCalendar = new PersianCalendar(calendar);
        if (persianCalendar.getMonth() > 8){
            calendar.add(Calendar.MONTH, 4);
        }
        calendar.set(calendar.get(Calendar.YEAR), 0, 0, 0, 0, 0);
        Budget budget = new Budget();
        WorkingCalendar workingCalendar = new WorkingCalendar();
        workingCalendar.setYearValue(calendar.get(Calendar.YEAR));
        BudgetProduct budgetProduct = new BudgetProduct();
        budgetProduct.setRemain(product.getTotal());
        budgetProduct.setCode(product.getProductCode());
        ArrayList<BudgetProduct> budgetProducts = new ArrayList<>();
        budgetProducts.add(budgetProduct);
        budget.setBudgetProducts(budgetProducts);
        persianCalendar.setPersianCalendar(calendar);

        eventController.addEvent(product, "add");

        try {
            budgetManager.addProduct(workingCalendar, budget);
        } catch (Exception e) {
            StaticController.sendError(resp, 404,  "لطفا بودجه سال " + persianCalendar.getYear() + " را ثبت کنید. ");
            return;
        }

        productInManager.enterTime(product);
    }

    @RequestMapping(value = "/ajaxIdentity.do")
    public void identity(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(request.getParameter("data"));
        }catch (Exception e){
            response.setStatus(400);
            return;
        }
        Product product = new Product();
        product.setProductCode((String) jsonObject.get("code"));
        product = productManager.getProduct(product);

        jsonObject = new JSONObject();
        try{
            jsonObject.put("code", product.getProductCode());
            jsonObject.put("size", product.getSize());
            jsonObject.put("design", product.getDesign());
            jsonObject.put("pr", product.getPr());
        }catch (Exception e){
            response.setStatus(400);
            return;
        }

        ProcessProduct processProduct = new ProcessProduct();
        processProduct.setCode(product.getProductCode());
        processProduct = processManager.getProcess(processProduct);

        ArrayList<Process> processes = new ArrayList<>();
        ArrayList<ProcessRaw> processRaws = new ArrayList<>();

        if (processProduct.getProcesses() != null){
            for (Process process : processProduct.getProcesses()) {
                for (ProcessRaw processRaw : process.getRawInputsRaw()) {
                    putProcessRaw(processRaws, processRaw);
                }

                putProcess(processes, process);
            }
        }

        JSONArray rawArray = new JSONArray();
        for (ProcessRaw processRaw : processRaws) {
            JSONObject rawObj = new JSONObject();
            rawObj.put("code", processRaw.getRawCode());
            rawObj.put("name", processRaw.getRawName());
            rawObj.put("total", processRaw.getTotal());

            rawArray.add(rawObj);
        }
        jsonObject.put("raws", rawArray);

        JSONArray semiArray = new JSONArray();
        for (Process process : processes) {
            JSONObject semiObj = new JSONObject();
            semiObj.put("sc", process.getSectionCode());
            semiObj.put("sn", process.getSectionName());
            semiObj.put("gc", process.getGroupCode());
            semiObj.put("gn", process.getGroupName());
            semiObj.put("oc", process.getOutputCode());
            semiObj.put("on", process.getOutputName());
            semiObj.put("total", process.getTotalOutput());

            semiArray.add(semiObj);
        }
        jsonObject.put("semis", semiArray);

        StaticController.sendData(response, jsonObject.toJSONString());
    }

    private void putProcessRaw(ArrayList<ProcessRaw> processRaws, ProcessRaw processRaw){
        for (ProcessRaw raw : processRaws) {
            if (raw.getRawCode().equals(processRaw.getRawCode())){
                raw.setTotal(raw.getTotal() + processRaw.getTotal());
                return;
            }
        }
        processRaws.add(processRaw);
    }

    private void putProcess(ArrayList<Process> processes, Process process){
        for (Process process1 : processes) {
            if (process1.getSectionCode().equals(process.getSectionCode()) && process1.getGroupCode().equals(process.getGroupCode()) && process1.getOutputCode().equals(process.getOutputCode())){
                process1.setTotalOutput(process1.getTotalOutput() + process.getTotalOutput());
                return;
            }
        }
        processes.add(process);
    }

    @RequestMapping(value = "/out.do")
    public void out(HttpServletRequest req, HttpServletResponse resp){
        String data = req.getParameter("data");
        JSONArray productsArray;
        try {
            productsArray = (JSONArray) new JSONParser().parse(data);
        } catch (org.json.simple.parser.ParseException e) {
            resp.setStatus(400);
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);

        for (Object o : productsArray) {
            JSONObject productObject = (JSONObject) o;
            Product product = new Product();
            product.setProductCode((String) productObject.get("code"));

            ProductOutTime productOutTime = new ProductOutTime();
            try {
                productOutTime.setDate(simpleDateFormat.parse((String) productObject.get("date")));
            } catch (ParseException e) {
                logger.error("Cant parse", e);
                resp.setStatus(400);
                return;
            }
            productOutTime.setBatchNumber((String) productObject.get("batch"));

            ArrayList<ProductDegreeTime> productDegreeTimes = new ArrayList<>();

            Product eventProduct = new Product();
            eventProduct.setProductCode(product.getProductCode());

            eventProduct = productManager.getProduct(eventProduct);

            JSONArray degreeArray = (JSONArray) productObject.get("degrees");
            for (Object o1 : degreeArray) {
                JSONObject degreeObject = (JSONObject) o1;

                ProductDegreeTime productDegreeTime = new ProductDegreeTime();
                productDegreeTime.setDegree(Integer.parseInt((String) degreeObject.get("code")));
                productDegreeTime.setTotal(Double.parseDouble((String) degreeObject.get("value")));

                ProductDegree productDegree = new ProductDegree();
                productDegree.setDegree(productDegreeTime.getDegree());
                productDegree.setTotal(productDegreeTime.getTotal());
                ArrayList<ProductDegree> productDegrees = new ArrayList<>();
                productDegrees.add(productDegree);
                eventProduct.setProductDegrees(productDegrees);
                eventController.addEvent(eventProduct, "use");

                productDegreeTimes.add(productDegreeTime);
            }

            productOutTime.setProductDegreeTimes(productDegreeTimes);

            ArrayList<ProductOutTime> productOutTimes = new ArrayList<>();
            productOutTimes.add(productOutTime);
            product.setProductOutTimes(productOutTimes);

            if (productInManager.useTime(product)){
                productTimeSellManager.enterTime(product);
            }
        }

    }

    @RequestMapping("/isolate.do")
    public void isolate(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(request.getParameter("data"));
        }catch (Exception e){
            response.setStatus(400);
            return;
        }

        String code, batchNumber, reason, description;
        Date date;
        int shift;
        double total;
        try {
            code = (String) jsonObject.get("code");
            date = new Date((Long) jsonObject.get("date"));
            total = (Double) jsonObject.get("total");
            batchNumber = (String) jsonObject.get("batch");
            reason = (String) jsonObject.get("reason");
            description = (String) jsonObject.get("des");
            shift = (Integer) jsonObject.get("shift");
        }catch (Exception e){
            response.setStatus(400);
            return;
        }

        if (code != null && reason != null && batchNumber != null && !code.trim().equals("") && !batchNumber.trim().equals("")){
            Product product = new Product();
            product.setProductCode(code);
            product = productManager.getProduct(product);

            Product productWaste = new Product();
            productWaste.setProductCode(product.getProductCode());
            productWaste.setSize(product.getSize());
            productWaste.setDesign(product.getDesign());
            productWaste.setPr(product.getPr());

            ArrayList<ProductWasteTime> productWasteTimes = new ArrayList<>();

            ProductWasteTime productWasteTime = new ProductWasteTime();
            productWasteTime.setBatchNumber(batchNumber);
            productWasteTime.setTotal(total);
            productWasteTime.setDate(date);
            productWasteTime.setReason(reason);
            productWasteTime.setShift(shift);
            if (description != null){
                productWasteTime.setDescription(description);
            }

            productWasteTimes.add(productWasteTime);
            productWaste.setProductWasteTimes(productWasteTimes);

            try {
                productWasteManager.addTime(productWaste);
                product.setTotal(total);
                eventController.addEvent(product, "isolate");
            } catch (Exception e) {
                response.setStatus(400);
            }
        }else{
            response.setStatus(400);
        }
    }

    @RequestMapping("/data.do")
    public void data(HttpServletRequest req, HttpServletResponse resp){
        List<Product> products = productManager.getProducts();
        if (products != null){
            JSONArray jsonArray = new JSONArray();
            for (Product product : products) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", product.getProductCode());
                jsonObject.put("name", product.getSize() + product.getDesign()  + product.getPr());
                jsonArray.add(jsonObject);
            }
            StaticController.sendData(resp, jsonArray.toJSONString());
        }else {
            StaticController.sendError(resp, 404, "محصول ثبت نشده است");
        }
    }

    @RequestMapping("/ajaxDate.do")
    public void ajaxDate(HttpServletRequest req, HttpServletResponse resp){
        if(req.getParameter("productCode") != null && !req.getParameter("productCode").trim().equals("")) {
            Product productIn = new Product();
            productIn.setProductCode(req.getParameter("productCode"));
            List<ProductInTime> productInTimes;

            productInTimes = productInManager.getTimeRemain(productIn);
            if (productInTimes == null){
                resp.setStatus(400);
                return;
            }

            JSONArray jsonArray = new JSONArray();
            JSONObject dateJSON = new JSONObject();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
            for (ProductInTime productInTime : productInTimes) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("date", simpleDateFormat.format(productInTime.getDate()));

                jsonArray.add(jsonObject);
            }

            try {
                productIn = productManager.getProduct(productIn);
            }catch (Exception e){
                logger.error("Server does not work well!", e);
                resp.setStatus(500);
                return;
            }
            dateJSON.put("total", productIn.getTotal());
            dateJSON.put("array", jsonArray);

            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/JSON");
            try {
                resp.getWriter().write(dateJSON.toJSONString());
            } catch (IOException e) {
                logger.error("Response can not write data!", e);
                resp.setStatus(500);
            }
        }
    }

    @RequestMapping("/ajaxDegree.do")
    public void ajaxDegree(HttpServletRequest req, HttpServletResponse resp){
        Map<String, String[]> map = req.getParameterMap();
        String productCode, d, batch;
        try {
            productCode = map.get("productCode")[0];
            d = map.get("date")[0];
            batch = map.get("batchNumber")[0];
        }catch (Exception e){
            logger.error("Data does not send properly!");
            resp.setStatus(400);
            return;
        }

        if (!productCode.trim().equals("") && !d.trim().equals("") && !batch.trim().equals("")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
            Date date;
            try {
                date = simpleDateFormat.parse(d);
            } catch (ParseException e) {
                logger.error("Data does not send properly!");
                resp.setStatus(403);
                return;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, 24);
            List<Product> productIns;
            try {
                productIns = productInManager.getBatchNumberTimes(batch, date, calendar.getTime());
            } catch (Exception e) {
                logger.error("Data dows not find!", e);
                resp.setStatus(404);
                return;
            }

            boolean check = false;
            JSONArray jsonArray = new JSONArray();
            for (Product productIn : productIns) {
                if (productIn.getProductCode().equals(productCode)){
                    for (ProductInTime productInTime : productIn.getProductInTimes()) {
                        for (ProductDegreeTime productDegreeInTime : productInTime.getProductDegreeTimes()) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("code", productDegreeInTime.getDegree());
                            jsonObject.put("total", productDegreeInTime.getRemain());
                            jsonArray.add(jsonObject);
                        }
                        break;
                    }
                    check = true;
                    break;
                }
            }

            if (check){
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("text/JSON");
                try {
                    resp.getWriter().write(jsonArray.toJSONString());
                } catch (IOException e) {
                    logger.error("Response does not write!", e);
                    resp.setStatus(500);
                }
            }else {
                logger.error("Data does not find!");
                resp.setStatus(404);
            }
        }else {
            logger.error("Data does not send properly!");
            resp.setStatus(400);
        }
    }

    @RequestMapping("/ajaxBatchNumber.do")
    public void ajaxBatchNumber(HttpServletRequest req, HttpServletResponse resp){
        if(req.getParameter("productCode") != null && !req.getParameter("productCode").trim().equals("") && req.getParameter("date") != null && !req.getParameter("date").trim().equals("")){
            Product productIn = new Product();
            productIn.setProductCode(req.getParameter("productCode"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
            Date date;
            try {
                date = simpleDateFormat.parse(req.getParameter("date"));
            } catch (ParseException e) {
                logger.error("Data does not send properly!", e);
                resp.setStatus(400);
                return;
            }
            List<ProductInTime> productInTimes;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, 24);
            try {
                productInTimes = productInManager.getTimesBetween(productIn, date, calendar.getTime());
            } catch (Exception e) {
                logger.error("Data does not find!");
                resp.setStatus(404);
                return;
            }

            JSONArray jsonArray = new JSONArray();
            for (ProductInTime productInTime : productInTimes) {
                if (productInTime.getRemain() != 0){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("batchNumber", productInTime.getBatchNumber());

                    jsonArray.add(jsonObject);
                }
            }

            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/JSON");
            try {
                resp.getWriter().write(jsonArray.toJSONString());
            } catch (IOException e) {
                logger.error("Response does not write!");
                resp.setStatus(500);
            }
        }else{
            logger.error("Data does not send properly!");
            resp.setStatus(400);
        }
    }

    @RequestMapping("/ajaxLoad.do")
    public void ajaxLoad(HttpServletRequest req, HttpServletResponse resp){
        JSONArray jsonArray = new JSONArray();
        List<Product> products = productManager.getProducts();
        if (products != null){
            for (Product product : products) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", product.getProductCode());
                jsonObject.put("size", product.getSize());
                jsonObject.put("design", product.getDesign());
                jsonObject.put("pr", product.getPr());

                jsonArray.add(jsonObject);
            }
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/JSON");
        try {
            resp.getWriter().write(jsonArray.toJSONString());
        } catch (IOException e) {
            logger.error("Response can not write data", e);
        }
    }

    @RequestMapping("/getData.do")
    public void getData(HttpServletRequest req, HttpServletResponse resp){
        String kind;
        try {
            kind = req.getParameter("kind");
            if (kind == null || kind.trim().equals("")){
                resp.setStatus(400);
                return;
            }
        }catch (Exception e){
            resp.setStatus(400);
            return;
        }

        JSONArray jsonArray;
        switch (kind) {
            case "stock":
                jsonArray = getStock();
                break;
            case "in": {
                Date start, end;
                try {
                    start = new Date(Long.parseLong(req.getParameter("start")));
                    end = new Date(Long.parseLong(req.getParameter("end")));
                } catch (Exception e) {
                    resp.setStatus(400);
                    return;
                }
                jsonArray = getIn(start, end);
                break;
            }
            case "out": {
                Date start, end;
                try {
                    start = new Date(Long.parseLong(req.getParameter("start")));
                    end = new Date(Long.parseLong(req.getParameter("end")));
                } catch (Exception e) {
                    resp.setStatus(400);
                    return;
                }
                jsonArray = getOut(start, end);
                break;
            }
            case "isolate": {
                Date start, end;
                try {
                    start = new Date(Long.parseLong(req.getParameter("start")));
                    end = new Date(Long.parseLong(req.getParameter("end")));
                } catch (Exception e) {
                    resp.setStatus(400);
                    return;
                }
                jsonArray = getIsolate(start, end);
                break;
            }
            default:
                resp.setStatus(400);
                return;
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/JSON");
        try {
            resp.getWriter().write(jsonArray.toJSONString());
        } catch (IOException e) {
            logger.error("Response can not write data", e);
        }
    }

    private JSONArray getStock(){
        JSONArray jsonArray = new JSONArray();
        List<Product> products = productManager.getProducts();
        if (products != null){
            for (Product product : products) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", product.getProductCode());
                jsonObject.put("size", product.getSize());
                jsonObject.put("design", product.getDesign());
                jsonObject.put("pr", product.getPr());
                jsonObject.put("total", product.getTotal());
                jsonObject.put("tweight", product.getTotal() * product.getWeight());

                jsonArray.add(jsonObject);
            }
        }

        return jsonArray;
    }

    private JSONArray getIn(Date start, Date end){
        List<Product> products = productManager.getProducts();
        JSONArray jsonArray = new JSONArray();

        for (Product product : products) {
            List<ProductInTime> productInTimes = productInManager.getTimesBetween(product, start, end);
            for (ProductInTime productInTime : productInTimes) {
                JSONObject jsonObject = new JSONObject();

            }
        }

        return null;
    }

    private JSONArray getOut(Date start, Date end){
        return null;
    }

    private JSONArray getIsolate(Date start, Date end){
        return null;
    }

    private Product checkProductTime(ArrayList<Product> productIns, Product productIn){
        for (Product time : productIns) {
            if(time.getProductCode().equals(productIn.getProductCode())){
                return time;
            }
        }
        return productIn;
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
