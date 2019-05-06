package controller;

import model.entity.Budget;
import model.entity.BudgetProduct;
import model.entity.Product;
import model.entity.WorkingCalendar;
import model.service.BudgetManager;
import model.service.ProductManager;
import model.service.WorkingCalendarManager;
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
 * Created by amir on 1/31/17.
 */
@Controller
@RequestMapping("/document/budget")
public class BudgetController {
    @Autowired
    private BudgetManager budgetManager;
    @Autowired
    private ProductManager productManager;
    @Autowired
    private WorkingCalendarManager workingCalendarManager;
    private static Logger logger = Logger.getLogger(BudgetController.class.getName());

    @RequestMapping("/register.do")
    public void register(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(request.getParameter("data"));
            if (jsonObject == null){
                logger.info("here");
                response.setStatus(400);
                return;
            }
        }catch (Exception e){
            logger.info("here");
            response.setStatus(400);
            return;
        }

        String p, w;
        int date;
        boolean equal;

        try {
            w = (String) jsonObject.get("weight");
            date = Integer.parseInt(jsonObject.get("date") + "");
            p = (String) jsonObject.get("price");
            equal = (boolean) jsonObject.get("equal");
        }catch (Exception e){
            logger.info("here", e);
            response.setStatus(400);
            return;
        }

        double price;
        try {
            String tempPrice = "";
            for (int i = 0; i < p.length(); i++){
                int temp;
                try {
                    temp = Integer.parseInt(String.valueOf(p.charAt(i)));
                    tempPrice += temp;
                }catch (Exception e){

                }
            }
            price = Double.parseDouble(tempPrice);
        }catch (Exception e){
            logger.info("here");
            response.setStatus(400);
            return;
        }

        WorkingCalendar workingCalendar = new WorkingCalendar();
        workingCalendar.setYearValue(date);

        Budget budget = new Budget();
        budget.setPrice(price);
        budget.setWeight(Double.parseDouble(w));

        ArrayList<BudgetProduct> budgetProducts = new ArrayList<>();

        double totalPrice = 0, totalWeight = 0;
        JSONArray productsArray = (JSONArray) jsonObject.get("products");
        try {
            for (Object o : productsArray) {
                JSONObject productObj = (JSONObject) o;
                Product product = new Product();
                product.setProductCode((String) productObj.get("code"));
                product = productManager.getProduct(product);

                BudgetProduct budgetProduct = new BudgetProduct();
                budgetProduct.setCode(product.getProductCode());
                budgetProduct.setName(product.getSize());
                budgetProduct.setDesign(product.getDesign());
                budgetProduct.setPr(product.getPr());
                budgetProduct.setTotal(Double.parseDouble("" + productObj.get("total")));
                budgetProduct.setRemain(budgetProduct.getTotal());
                budgetProduct.setPrice(Double.parseDouble("" + productObj.get("cost")));

                totalPrice += budgetProduct.getTotal() * budgetProduct.getPrice();
                totalWeight += budgetProduct.getTotal() * product.getWeight();

                budgetProducts.add(budgetProduct);
            }
        }catch (Exception e){
            logger.error("error", e);
            response.setStatus(400);
            return;
        }
        budget.setBudgetProducts(budgetProducts);

        workingCalendar.setBudget(budget);

        if (equal){
            if (totalPrice != price || totalWeight != Double.parseDouble(w)){
                StaticController.sendError(response, 400, "nep");
                return;
            }
            if (totalWeight != Double.parseDouble(w)){
                StaticController.sendError(response, 400, "new");
                return;
            }
        }


        if (!budgetManager.register(workingCalendar)){
            logger.info("here");
            response.setStatus(400);
        }
    }

    @RequestMapping("/show.do")
    public void show(HttpServletRequest request, HttpServletResponse response){
        String d = request.getParameter("date");
        if (d != null && !d.trim().equals("")){
            WorkingCalendar workingCalendar = new WorkingCalendar();
            workingCalendar.setYearValue(Integer.parseInt(d));
            Budget budget = budgetManager.get(workingCalendar);
            if (budget == null){
                response.setStatus(404);
                return;
            }

            double totalWeight = 0, totalPrice = 0;
            try {
                List<Product> products = productManager.getProducts();
                for (Product product : products) {
                    for (BudgetProduct budgetProduct : budget.getBudgetProducts()) {
                        if (budgetProduct.getCode().equals(product.getProductCode())){
                            totalWeight += budgetProduct.getRemain() * product.getWeight();
                            totalPrice += budgetProduct.getRemain() * budgetProduct.getPrice();
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("server not respond!", e);
                response.setStatus(500);
                return;
            }
            totalPrice = budget.getPrice() - totalPrice;
            totalWeight = budget.getWeight() - totalWeight;

            JSONArray jsonArray = new JSONArray();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("price", budget.getPrice());
            jsonObject.put("weight", budget.getWeight());
            jsonObject.put("remainPrice", totalPrice);
            jsonObject.put("remainWeight", totalWeight);

            for (BudgetProduct budgetProduct : budget.getBudgetProducts()) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("code", budgetProduct.getCode());
                jsonObject1.put("size", budgetProduct.getName());
                jsonObject1.put("design", budgetProduct.getDesign());
                jsonObject1.put("pr", budgetProduct.getPr());
                jsonObject1.put("total", budgetProduct.getTotal());
                jsonObject1.put("price", budgetProduct.getPrice());
                jsonObject1.put("remain", budgetProduct.getRemain());

                jsonArray.add(jsonObject1);
            }
            jsonObject.put("data", jsonArray);

            StaticController.sendData(response, jsonObject.toJSONString());
        }else {
            response.setStatus(400);
        }
    }

    @RequestMapping("/year.do")
    public void checkYear(HttpServletRequest request, HttpServletResponse response){
        JSONArray jsonArray = new JSONArray();
        List<WorkingCalendar> workingCalendars = workingCalendarManager.getAll();
        for (WorkingCalendar workingCalendar : workingCalendars) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(workingCalendar.getYearValue(), 1, 1, 0, 0, 0);
            if (workingCalendar.getBudget() == null){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("date", calendar.getTime().getTime());

                jsonArray.add(jsonObject);
            }
        }

        StaticController.sendData(response, jsonArray.toJSONString());
    }
}
