package controller;

import model.entity.*;
import model.entity.Process;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by amir on 12/19/16.
 */
@Controller
@RequestMapping(value = "/document/planing")
public class PlaningController {
    @Autowired
    private ProductManager productManager;
    @Autowired
    private PlaningTimesManager planingTimesManager;
    @Autowired
    private EventController eventController;
    @Autowired
    private ProcessManager processManager;
    @Autowired
    private MachineGroupManager machineGroupManager;
    @Autowired
    private BudgetManager budgetManager;
    @Autowired
    private WorkingCalendarManager workingCalendarManager;
    private static Logger logger = Logger.getLogger(PlaningController.class.getName());

    @RequestMapping(value = "/getData.do" , method = RequestMethod.POST)
    public void getData(HttpServletRequest request, HttpServletResponse response){
        JSONObject planObj;
        try{
            planObj = (JSONObject) new JSONParser().parse(request.getParameter("data"));
        }catch (Exception e){
            response.setStatus(400);
            return;
        }

        int plan, shift;
        Date date;
        try {
            plan = Integer.parseInt((String) planObj.get("plan"));
            date = new Date((Long) planObj.get("date"));
            shift = Integer.parseInt((String) planObj.get("shift"));
        }catch (Exception e){
            logger.error("error", e);
            response.setStatus(400);
            return;
        }

        JSONArray jsonArray;
        switch (plan){
            case 1:{
                jsonArray = getBudget(date, false);
                break;
            }
            case 2:{
                jsonArray = getBudget(date, true);
                break;
            }
            case 3:{
                jsonArray = new JSONArray();
                break;
            }
            case 4:{
                jsonArray = new JSONArray();
                break;
            }
            case 5:{
                jsonArray = getProducts();
                break;
            }
            default:{
                response.setStatus(400);
                return;
            }
        }

        if (jsonArray == null){
            response.setStatus(404);
        }else {
            StaticController.sendData(response, jsonArray.toJSONString());
        }
    }

    private JSONArray getProducts(){
        List<Product> products = productManager.getProducts();
        if (products != null && products.size() > 0){
            JSONArray productsArray = new JSONArray();
            for (Product product : products) {
                JSONObject productObject = new JSONObject();
                productObject.put("code", product.getProductCode());
                productObject.put("name", product.getSize() + "-" + product.getDesign() + "-" + product.getPr());
                productObject.put("total", 0);
                productObject.put("weight", 0);
                productsArray.add(productObject);
            }
            return productsArray;
        }else
            return null;
    }

    private JSONArray getBudget(Date date, boolean remain){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        calendar.set(calendar.get(Calendar.YEAR), 1, 1);
        WorkingCalendar workingCalendar = new WorkingCalendar();
        workingCalendar.setYearValue(calendar.get(Calendar.YEAR));
        Budget budget = budgetManager.get(workingCalendar);
        JSONArray productsArray = new JSONArray();
        for (BudgetProduct budgetProduct : budget.getBudgetProducts()) {
            if (budgetProduct.getRemain() > 0){
                JSONObject productObject = new JSONObject();
                Product product = new Product();
                product.setProductCode(budgetProduct.getCode());
                product = productManager.getProduct(product);
                productObject.put("code", budgetProduct.getCode());
                productObject.put("name", budgetProduct.getName() + "-" + budgetProduct.getDesign() + "-" + budgetProduct.getPr());

                workingCalendar = new WorkingCalendar();
                workingCalendar.setYearValue(calendar.get(Calendar.YEAR));
                workingCalendar = workingCalendarManager.getYear(workingCalendar);

                if (remain){
                    int days = workingCalendarManager.getBetweenDays(workingCalendar, calendar.getTime(), workingCalendar.getEndOfYear()).size();
                    productObject.put("total", Math.ceil(budgetProduct.getRemain() / days));
                    productObject.put("weight", Math.ceil(budgetProduct.getRemain() / days) * product.getWeight());
                }else {
                    productObject.put("total", Math.ceil(budgetProduct.getTotal() / workingCalendar.getWorkingDays().size()));
                    productObject.put("weight", Math.ceil(budgetProduct.getTotal() / workingCalendar.getWorkingDays().size()) * product.getWeight());
                }

                productsArray.add(productObject);
            }
        }
        return productsArray;
    }

    @RequestMapping(value = "/getPlan.do" , method = RequestMethod.POST)
    public void getPlan(HttpServletRequest request, HttpServletResponse response){
        JSONArray productsJSON;
        Date date;
        try{
            productsJSON = (JSONArray) new JSONParser().parse(request.getParameter("products"));
            date = new Date(Long.parseLong(request.getParameter("date")));
        }catch (Exception e){
            response.setStatus(400);
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), 1, 1);

        WorkingCalendar workingCalendar = new WorkingCalendar();
        workingCalendar.setYearValue(calendar.get(Calendar.YEAR));

        WorkingDays workingDays = new WorkingDays();
        workingDays.setDay(calendar.getTime());
        workingDays = workingCalendarManager.getDay(workingCalendar, workingDays);

        HashMap<String, PlaningRaw> planingRawHashMap = new HashMap<>();
        HashMap<String, PlaningSection> planingSectionHashMap = new HashMap<>();
        for (Object o : productsJSON) {
            JSONObject productObj = (JSONObject) o;
            ProcessProduct processProduct = new ProcessProduct();
            processProduct.setCode((String) productObj.get("code"));
            double total = Double.parseDouble(productObj.get("total") + "");
            processProduct = processManager.getProcess(processProduct);
            for (Process process : processProduct.getProcesses()) {
                //List Raw Material Needs
                if (process.getRawInputsRaw() != null){
                    for (ProcessRaw processRaw : process.getRawInputsRaw()) {
                        try {
                            planingRawHashMap.get(processRaw.getRawCode()).setTotal(planingRawHashMap.get(processRaw.getRawCode()).getTotal() + processRaw.getTotal());
                        }catch (Exception e){
                            PlaningRaw planingRaw = new PlaningRaw();
                            planingRaw.setCode(processRaw.getRawCode());
                            planingRaw.setName(processRaw.getRawName());
                            planingRaw.setTotal(processRaw.getTotal() * total);
                            planingRawHashMap.put(processRaw.getRawCode(), planingRaw);
                        }
                    }
                }

                //List Section
                try {
                    PlaningSection planingSection = planingSectionHashMap.get(process.getSectionCode());
                    if (planingSection == null){
                        planingSection = new PlaningSection();
                        planingSection.setCode(process.getSectionCode());
                        planingSection.setName(process.getSectionName());

                        ArrayList<PlaningGroup> planingGroups = new ArrayList<>();
                        createPlaningGroup(process, planingGroups, workingDays, total);

                        planingSection.setPlaningGroupLis(planingGroups);

                        planingSectionHashMap.put(planingSection.getCode(), planingSection);
                    }else {
                        boolean exist = false;
                        if (planingSection.getPlaningGroupLis() != null && planingSection.getPlaningGroupLis().size() > 0){
                            for (PlaningGroup planingGroup : planingSection.getPlaningGroupLis()) {
                                if (planingGroup.getCode().equals(process.getGroupCode())){
                                    exist = true;

                                    double totalCapacity = 0;
                                    MachineGroup machineGroup = getMachineGroup(process);
                                    totalCapacity = getTotalCapacityMachine(machineGroup, workingDays, totalCapacity);

                                    if (planingGroup.getPlaningMachineList() != null && planingGroup.getPlaningMachineList().size() > 0){
                                        for (PlaningMachine planingMachine : planingGroup.getPlaningMachineList()) {
                                            for (Machine machine : machineGroup.getMachines()) {
                                                if (planingMachine.getCode().equals(machine.getCode())){

                                                    planingMachine.setTotal(planingMachine.getTotal() + (((process.getTotalOutput() * total) * (machine.getUph() * workingDays.getShiftHours())) / totalCapacity));

                                                    boolean semiMachineExist = false;
                                                    if (planingMachine.getPlaningSemiMachines() != null && planingMachine.getPlaningSemiMachines().size() > 0){
                                                        for (PlaningSemiMachine planingSemiMachine : planingMachine.getPlaningSemiMachines()) {
                                                            if (planingSemiMachine.getCode().equals(process.getOutputCode())){
                                                                semiMachineExist = true;
                                                                planingSemiMachine.setTotal(planingSemiMachine.getTotal() + (((process.getTotalOutput() * total) * (machine.getUph() * workingDays.getShiftHours())) / totalCapacity));
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (!semiMachineExist){
                                                        setPlaningSemiMachine(process, machine, workingDays, total, totalCapacity, planingMachine);
                                                    }

                                                    machine.setCode(null);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    for (Machine machine : machineGroup.getMachines()) {
                                        if (machine.getCode() != null){
                                            setPlaningMachine(process, machine, planingGroup.getPlaningMachineList(), workingDays, total, totalCapacity);
                                        }
                                    }
                                }
                            }
                        }
                        if (!exist){
                            ArrayList<PlaningGroup> planingGroups = new ArrayList<>();
                            createPlaningGroup(process, planingGroups, workingDays, total);

                            planingSection.setPlaningGroupLis(planingGroups);
                        }
                    }
                }catch (Exception e){
                    logger.error("error", e);
                }
            }
        }

        JSONArray rawArray = new JSONArray();
        for (String s : planingRawHashMap.keySet()) {
            PlaningRaw planingRaw = planingRawHashMap.get(s);
            JSONObject rawObj = new JSONObject();
            rawObj.put("code", planingRaw.getCode());
            rawObj.put("name", planingRaw.getName());
            rawObj.put("total", planingRaw.getTotal());

            rawArray.add(rawObj);
        }

        JSONArray sectionArray = new JSONArray();
        for (String s : planingSectionHashMap.keySet()) {
            PlaningSection planingSection = planingSectionHashMap.get(s);
            JSONObject sectionObj = new JSONObject();
            sectionObj.put("code", planingSection.getCode());
            sectionObj.put("name", planingSection.getName());

            JSONArray groupsArray = new JSONArray();
            for (PlaningGroup planingGroup : planingSection.getPlaningGroupLis()) {
                JSONObject groupObj = new JSONObject();
                groupObj.put("code", planingGroup.getCode());
                groupObj.put("name", planingGroup.getName());

                JSONArray semiArray = new JSONArray();
                for (PlaningSemi planingSemi : planingGroup.getPlaningSemis()) {
                    JSONObject semiObj = new JSONObject();
                    semiObj.put("code", planingSemi.getCode());
                    semiObj.put("name", planingSemi.getName());
                    semiObj.put("total", planingSemi.getTotal());

                    semiArray.add(semiObj);
                }
                groupObj.put("semis", semiArray);

                JSONArray machineArray = new JSONArray();
                for (PlaningMachine planingMachine : planingGroup.getPlaningMachineList()) {
                    JSONObject machineObj = new JSONObject();
                    machineObj.put("code", planingMachine.getCode());
                    machineObj.put("name", planingMachine.getName());
                    machineObj.put("capacity", planingMachine.getCapacity());
                    machineObj.put("total", planingMachine.getTotal());

                    JSONArray semiMachineArray = new JSONArray();
                    for (PlaningSemiMachine planingSemiMachine : planingMachine.getPlaningSemiMachines()) {
                        JSONObject semiMachineObj = new JSONObject();
                        semiMachineObj.put("code", planingSemiMachine.getCode());
                        semiMachineObj.put("name", planingSemiMachine.getName());
                        semiMachineObj.put("priority", planingSemiMachine.getPriority());
                        semiMachineObj.put("total", planingSemiMachine.getTotal());

                        semiMachineArray.add(semiMachineObj);
                    }
                    machineObj.put("semiMachines", semiMachineArray);

                    machineArray.add(machineObj);
                }
                groupObj.put("machines", machineArray);

                groupsArray.add(groupObj);
            }
            sectionObj.put("groups", groupsArray);

            sectionArray.add(sectionObj);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("raw", rawArray);
        jsonObject.put("section", sectionArray);

        StaticController.sendData(response, jsonObject.toJSONString());
    }

    private void setPlaningSemiMachine(Process process, Machine machine, WorkingDays workingDays, double total, double totalCapacity, PlaningMachine planingMachine){
        PlaningSemiMachine planingSemiMachine = new PlaningSemiMachine();
        planingSemiMachine.setCode(process.getOutputCode());
        planingSemiMachine.setName(process.getOutputName());
        planingSemiMachine.setPriority(0);
        planingSemiMachine.setTotal(((process.getTotalOutput() * total) * (machine.getUph() * workingDays.getShiftHours())) / totalCapacity);

        ArrayList<PlaningSemiMachine> planingSemiMachines = new ArrayList<>();
        planingSemiMachines.add(planingSemiMachine);

        planingMachine.setPlaningSemiMachines(planingSemiMachines);
    }

    private void setPlaningMachine(Process process, Machine machine, List<PlaningMachine> planingMachines, WorkingDays workingDays, double total, double totalCapacity){
        PlaningMachine planingMachine = new PlaningMachine();
        planingMachine.setCode(machine.getCode());
        planingMachine.setName(machine.getName());
        planingMachine.setCapacity(machine.getUph() * workingDays.getShiftHours());
        planingMachine.setTotal(((process.getTotalOutput() * total) * (machine.getUph() * workingDays.getShiftHours())) / totalCapacity);

        setPlaningSemiMachine(process, machine, workingDays, total, totalCapacity, planingMachine);

        planingMachines.add(planingMachine);
    }

    private void createPlaningGroup(Process process, List<PlaningGroup> planingGroups, WorkingDays workingDays, double total){
        PlaningGroup planingGroup = new PlaningGroup();
        planingGroup.setCode(process.getGroupCode());
        planingGroup.setName(process.getGroupName());
        planingGroups.add(planingGroup);

        MachineGroup machineGroup = getMachineGroup(process);
        double totalCapacity = 0;
        totalCapacity = getTotalCapacityMachine(machineGroup, workingDays, totalCapacity);

        ArrayList<PlaningMachine> planingMachines = new ArrayList<>();
        for (Machine machine : machineGroup.getMachines()) {
            setPlaningMachine(process, machine, planingMachines, workingDays, total, totalCapacity);
        }

        planingGroup.setPlaningMachineList(planingMachines);

        ArrayList<PlaningSemi> planingSemis = new ArrayList<>();
        PlaningSemi planingSemi = new PlaningSemi();
        planingSemi.setCode(process.getOutputCode());
        planingSemi.setName(process.getOutputName());
        planingSemi.setTotal(process.getTotalOutput() * total);
        planingSemis.add(planingSemi);

        planingGroup.setPlaningSemis(planingSemis);
    }

    private MachineGroup getMachineGroup(Process process){
        Section section = new Section();
        section.setCode(process.getSectionCode());
        section.setName(process.getSectionName());
        MachineGroup machineGroup = new MachineGroup();
        machineGroup.setCode(process.getGroupCode());
        machineGroup.setName(process.getGroupName());
        return machineGroupManager.getMachineGroup(machineGroup, section);
    }

    private double getTotalCapacityMachine(MachineGroup machineGroup, WorkingDays workingDays, Double totalCapacity){
        for (Machine machine : machineGroup.getMachines()) {
            totalCapacity += machine.getUph() * workingDays.getShiftHours();
        }
        return totalCapacity;
    }

    @RequestMapping(value = "/submit.do", method = RequestMethod.POST)
    public void submitPlan(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(request.getParameter("data"));
        }catch (Exception e){
            logger.error("data error", e);
            response.setStatus(400);
            return;
        }

        PlaningTimes planingTimes = new PlaningTimes();
        try {
            planingTimes.setTime(new Date((Long) jsonObject.get("date")));
        }catch (Exception e){
            logger.error("times error", e);
            response.setStatus(400);
            return;
        }

        ArrayList<PlaningShift> planingShifts = new ArrayList<>();
        PlaningShift planingShift = new PlaningShift();
        try {
            planingShift.setShift(Integer.parseInt(jsonObject.get("shift") + ""));
        }catch (Exception e){
            logger.error("shift error", e);
            response.setStatus(400);
            return;
        }

        JSONArray rawArray = (JSONArray) jsonObject.get("raws");
        ArrayList<PlaningRaw> planingRaws = new ArrayList<>();
        if (rawArray != null){
            for (Object o : rawArray) {
                JSONObject rawObj = (JSONObject) o;
                PlaningRaw planingRaw = new PlaningRaw();
                planingRaw.setCode((String) rawObj.get("code"));
                planingRaw.setName((String) rawObj.get("name"));
                planingRaw.setTotal(Double.parseDouble(rawObj.get("total") + ""));

                planingRaws.add(planingRaw);
            }
            planingShift.setPlaningRaws(planingRaws);
        }else {
            StaticController.sendError(response, 404, "اطلاعات مواد اولیه فرستاده نشده است");
            return;
        }

        JSONArray sectionArray = (JSONArray) jsonObject.get("sections");
        ArrayList<PlaningSection> planingSections = new ArrayList<>();
        if (sectionArray != null){
            for (Object o : sectionArray) {
                JSONObject sectionObj = (JSONObject) o;
                PlaningSection planingSection = new PlaningSection();
                planingSection.setCode((String) sectionObj.get("code"));
                planingSection.setName((String) sectionObj.get("name"));

                JSONArray groupsArray = (JSONArray) sectionObj.get("groups");
                ArrayList<PlaningGroup> planingGroups = new ArrayList<>();
                for (Object o1 : groupsArray) {
                    JSONObject groupObj = (JSONObject) o1;
                    PlaningGroup planingGroup = new PlaningGroup();
                    planingGroup.setCode((String) groupObj.get("code"));
                    planingGroup.setName((String) groupObj.get("name"));

                    JSONArray machinesArray = (JSONArray) groupObj.get("machines");
                    ArrayList<PlaningMachine> planingMachines = new ArrayList<>();
                    for (Object o2 : machinesArray) {
                        JSONObject machineObj = (JSONObject) o2;
                        PlaningMachine planingMachine = new PlaningMachine();
                        planingMachine.setCode((String) machineObj.get("code"));
                        planingMachine.setName((String) machineObj.get("name"));
                        planingMachine.setCapacity(Double.parseDouble(machineObj.get("capacity") + ""));
                        planingMachine.setTotal(Double.parseDouble(machineObj.get("total") + ""));

                        JSONArray semiMachineArray = (JSONArray) machineObj.get("semiMachines");
                        ArrayList<PlaningSemiMachine> planingSemiMachines = new ArrayList<>();
                        for (Object o3 : semiMachineArray) {
                            JSONObject semiMacineObj = (JSONObject) o3;
                            PlaningSemiMachine planingSemiMachine = new PlaningSemiMachine();
                            planingSemiMachine.setCode((String) semiMacineObj.get("code"));
                            planingSemiMachine.setName((String) semiMacineObj.get("name"));
                            planingSemiMachine.setPriority(Integer.parseInt(semiMacineObj.get("priority") + ""));
                            planingSemiMachine.setTotal(Double.parseDouble(semiMacineObj.get("total") + ""));

                            planingSemiMachines.add(planingSemiMachine);
                        }
                        planingMachine.setPlaningSemiMachines(planingSemiMachines);

                        planingMachines.add(planingMachine);
                    }
                    planingGroup.setPlaningMachineList(planingMachines);

                    JSONArray semiArray = (JSONArray) groupObj.get("semis");
                    ArrayList<PlaningSemi> planingSemis = new ArrayList<>();
                    for (Object o2 : semiArray) {
                        JSONObject semiObj = (JSONObject) o2;
                        PlaningSemi planingSemi = new PlaningSemi();
                        planingSemi.setCode((String) semiObj.get("code"));
                        planingSemi.setName((String) semiObj.get("name"));
                        planingSemi.setTotal(Double.parseDouble(semiObj.get("total") + ""));

                        planingSemis.add(planingSemi);
                    }
                    planingGroup.setPlaningSemis(planingSemis);

                    planingGroups.add(planingGroup);
                }
                planingSection.setPlaningGroupLis(planingGroups);

                planingSections.add(planingSection);
            }
            planingShift.setPlaningSections(planingSections);
        }else {
            StaticController.sendError(response, 404, "اطلاعات قسمت ها و دستگا ها فرستاده نسده است");
            return;
        }

        JSONArray productArray = (JSONArray) jsonObject.get("products");
        ArrayList<PlaningProduct> planingProducts = new ArrayList<>();
        if (productArray != null){
            for (Object o : productArray) {
                JSONObject productObj = (JSONObject) o;
                PlaningProduct planingProduct = new PlaningProduct();
                planingProduct.setProductCode((String) productObj.get("code"));
                Product product = new Product();
                product.setProductCode(planingProduct.getProductCode());
                product = productManager.getProduct(product);
                planingProduct.setName(product.getSize() + product.getDesign() + product.getPr());
                planingProduct.setTotal(Long.parseLong(productObj.get("total") + ""));

                planingProducts.add(planingProduct);
            }
            planingShift.setPlaningProducts(planingProducts);
        }else {
            StaticController.sendError(response, 404, "اطلاعات محصول ها فرستاده نشده است");
            return;
        }

        planingShifts.add(planingShift);
        planingTimes.setPlaningShifts(planingShifts);

        planingTimesManager.update(planingTimes);
        eventController.addEvent(planingTimes, "add");
    }

    @RequestMapping("/date.do")
    public void date(HttpServletRequest request, HttpServletResponse response){
        WorkingCalendar workingCalendar = new WorkingCalendar();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        PersianCalendar persianCalendar = new PersianCalendar(calendar);
        int thisYear;
        if (persianCalendar.getMonth() > 8){
            calendar.add(Calendar.MONTH, 4);
            thisYear = calendar.get(Calendar.YEAR);
        }else {
            thisYear = calendar.get(Calendar.YEAR);
        }
        workingCalendar.setYearValue(thisYear);
        workingCalendar = workingCalendarManager.getYear(workingCalendar);
        List<PlaningTimes> planingTimes = planingTimesManager.getTimesBetween(workingCalendar.getBeginOfYear(), workingCalendar.getEndOfYear());
        JSONArray dates = new JSONArray();
        for (PlaningTimes planingTime : planingTimes) {
            dates.add(planingTime.getTime().getTime());
        }

        response.setCharacterEncoding("utf-8");
        response.setContentType("json");
        try {
            response.getWriter().write(dates.toJSONString());
        } catch (IOException e) {
            logger.error("writer cant write", e);
            response.setStatus(500);
        }
    }

    @RequestMapping("/sections.do")
    public void sections(HttpServletRequest request, HttpServletResponse response){
        int shift;
        Date date;
        try {
            shift = Integer.parseInt(request.getParameter("shift"));
            date = new Date(Long.parseLong(request.getParameter("date")));
        }catch (Exception e){
            response.setStatus(400);
            return;
        }
        PlaningTimes planingTimes = new PlaningTimes();
        planingTimes.setTime(date);
        planingTimes.setEdited(false);
        planingTimes = planingTimesManager.getTime(planingTimes);
        JSONArray sections = new JSONArray();
        if (planingTimes != null){
            for (PlaningShift planingShift : planingTimes.getPlaningShifts()) {
                if (planingShift.getShift() == shift){
                    if (planingShift.getPlaningRaws() != null && planingShift.getPlaningRaws().size() > 0){
                        sections.add("مواد اولیه");
                    }
                    for (PlaningSection planingSection : planingShift.getPlaningSections()) {
                        sections.add(planingSection.getCode() + " : " + planingSection.getName());
                    }
                }
            }
        }else {
            StaticController.sendError(response, 404, "برنامه ای در شیفت و تاریخ مورد نظر یافت نشد");
            return;
        }

        StaticController.sendData(response, sections.toJSONString());
    }

    @RequestMapping("/plan.do")
    public void plan(HttpServletRequest request, HttpServletResponse response){
        int shift;
        Date date;
        JSONObject dataObj;
        try {
            dataObj = (JSONObject) new JSONParser().parse(request.getParameter("data"));
            shift = Integer.parseInt(dataObj.get("shift") + "");
            date = new Date((Long) dataObj.get("date"));
        }catch (Exception e){
            response.setStatus(400);
            return;
        }
        PlaningTimes planingTimes = new PlaningTimes();
        planingTimes.setTime(date);
        planingTimes.setEdited(false);
        planingTimes = planingTimesManager.getTime(planingTimes);
        if (planingTimes == null){
            StaticController.sendError(response, 404, "برنامه ای در تاریخ مورد نظر یافت نشد");
            return;
        }
        PlaningShift planingShift = null;
        for (PlaningShift pShift : planingTimes.getPlaningShifts()) {
            if (pShift.getShift() == shift){
                planingShift = pShift;
            }
        }
        if (planingShift == null){
            StaticController.sendError(response, 404, "برنامه ای در شیفت مورد نظر یافت نشد");
            return;
        }

        String section = dataObj.get("sections") + "";
        JSONObject datas = new JSONObject();
        switch (section.trim()) {
            case "مواد اولیه":
                if (planingShift.getPlaningRaws() != null && planingShift.getPlaningRaws().size() > 0) {
                    JSONArray rawArray = new JSONArray();
                    for (PlaningRaw planingRaw : planingShift.getPlaningRaws()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("code", planingRaw.getCode());
                        jsonObject.put("name", planingRaw.getName());
                        jsonObject.put("total", planingRaw.getTotal());

                        rawArray.add(jsonObject);
                    }
                    datas.put("raws", rawArray);
                } else {
                    StaticController.sendError(response, 404, "مواد اولیه در تاریخ و شیفت مورد نظر یافت نشد");
                    return;
                }
                break;
            case "محصول ها":
                if (planingShift.getPlaningProducts() != null && planingShift.getPlaningProducts().size() > 0) {
                    JSONArray productArray = new JSONArray();
                    for (PlaningProduct planingProduct : planingShift.getPlaningProducts()) {
                        JSONObject productObj = new JSONObject();
                        productObj.put("code", planingProduct.getProductCode());
                        productObj.put("name", planingProduct.getName());
                        productObj.put("total", planingProduct.getTotal());
                        Product product = new Product();
                        product.setProductCode(planingProduct.getProductCode());
                        product = productManager.getProduct(product);
                        productObj.put("weight", product.getWeight() * planingProduct.getTotal());

                        productArray.add(productObj);
                    }
                    datas.put("products", productArray);
                } else {
                    StaticController.sendError(response, 404, "محصول در تاریخ و شیفت مورد نظر یافت نشد");
                    return;
                }
                break;
            default:
                String code = section.substring(0, section.indexOf(":") - 1);
                String name = section.substring(section.indexOf(":") + 2);
                if (planingShift.getPlaningSections() != null && planingShift.getPlaningSections().size() > 0) {
                    JSONArray sectionArray = new JSONArray();
                    for (PlaningSection planingSection : planingShift.getPlaningSections()) {
                        if (planingSection.getCode().equals(code) || planingSection.getName().equals(name)) {
                            JSONObject sectionObj = new JSONObject();
                            sectionObj.put("code", planingSection.getCode());
                            sectionObj.put("name", planingSection.getName());

                            JSONArray groupArray = new JSONArray();
                            for (PlaningGroup planingGroup : planingSection.getPlaningGroupLis()) {
                                JSONObject groupObj = new JSONObject();
                                groupObj.put("code", planingGroup.getCode());
                                groupObj.put("name", planingGroup.getName());

                                JSONArray machinesArray = new JSONArray();
                                for (PlaningMachine planingMachine : planingGroup.getPlaningMachineList()) {
                                    JSONObject machineObj = new JSONObject();
                                    machineObj.put("code", planingMachine.getCode());
                                    machineObj.put("name", planingMachine.getName());
                                    machineObj.put("total", planingMachine.getTotal());
                                    JSONArray semiMachines = new JSONArray();
                                    for (PlaningSemiMachine planingSemiMachine : planingMachine.getPlaningSemiMachines()) {
                                        JSONObject semiMachineObj = new JSONObject();
                                        semiMachineObj.put("code", planingSemiMachine.getCode());
                                        semiMachineObj.put("name", planingSemiMachine.getName());
                                        semiMachineObj.put("total", planingSemiMachine.getTotal());
                                        semiMachineObj.put("priority", planingSemiMachine.getPriority());

                                        semiMachines.add(semiMachineObj);
                                    }
                                    machineObj.put("semiMachines", semiMachines);

                                    machinesArray.add(machineObj);
                                }

                                JSONArray semiArray = new JSONArray();
                                for (PlaningSemi planingSemi : planingGroup.getPlaningSemis()) {
                                    JSONObject semiObj = new JSONObject();
                                    semiObj.put("code", planingSemi.getCode());
                                    semiObj.put("name", planingSemi.getName());
                                    semiObj.put("total", planingSemi.getTotal());

                                    semiArray.add(semiObj);
                                }

                                groupObj.put("semis", semiArray);
                                groupObj.put("machines", machinesArray);

                                groupArray.add(groupObj);
                            }
                            sectionObj.put("groups", groupArray);

                            sectionArray.add(sectionObj);
                        }
                    }
                    if (datas.get("sections") != null) {
                        JSONArray jsonArray = (JSONArray) datas.get("sections");
                        jsonArray.addAll(sectionArray);
                        datas.put("sections", jsonArray);
                    } else {
                        datas.put("sections", sectionArray);
                    }
                } else {
                    StaticController.sendError(response, 404, "قسمتی در تاریخ و شیفت مورد نظر برنامه ندارد");
                    return;
                }
                break;
        }

        StaticController.sendData(response, datas.toJSONString());
    }
}