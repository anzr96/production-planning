package controller;

import model.entity.Machine;
import model.entity.PMTime;
import model.service.MachineManager;
import model.service.MachinePMManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 2/7/17.
 */
@Controller
@RequestMapping("/document/pm")
public class PMController {
    @Autowired
    private MachinePMManager machinePMManager;
    @Autowired
    private MachineManager machineManager;
    private static Logger logger = Logger.getLogger(PMController.class.getName());

    @RequestMapping("/add.do")
    public void add(HttpServletRequest request, HttpServletResponse response){
        String d = request.getParameter("date");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String code = request.getParameter("code");

        if (d != null && start != null && end != null && code != null && !d.trim().equals("") && !start.trim().equals("") && !end.trim().equals("") && !code.trim().equals("")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
            Date date;
            try {
                date = simpleDateFormat.parse(d);
            }catch (Exception e){
                response.setStatus(400);
                return;
            }

            Machine machine = new Machine();
            machine.setCode(code);
            try {
//                machine = machineManager.getMachine(machine);
            } catch (Exception e) {
                response.setStatus(404);
                return;
            }

            Machine machinePM = new Machine();
            machinePM.setCode(machine.getCode());
            machinePM.setName(machine.getName());

            ArrayList<PMTime> pmTimes = new ArrayList<>();
            PMTime pmTime = new PMTime();
            pmTime.setDate(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int hour, minute;
            try {
                hour = Integer.parseInt(start.substring(0,2));
                minute = Integer.parseInt(start.substring(3,5));
            }catch (Exception e){
                response.setStatus(400);
                return;
            }
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hour, minute);

            pmTime.setStart(calendar.getTime());

            calendar.setTime(date);
            try {
                hour = Integer.parseInt(end.substring(0,2));
                minute = Integer.parseInt(end.substring(3,5));
            }catch (Exception e){
                response.setStatus(400);
                return;
            }
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hour, minute);

            pmTime.setEnd(calendar.getTime());

            pmTimes.add(pmTime);
            machinePM.setPmTimes(pmTimes);

            try {
                machinePMManager.addPM(machinePM);
            } catch (Exception e) {
                logger.error("server not respond", e);
                response.setStatus(500);
            }
        }else {
            response.setStatus(400);
        }
    }

    @RequestMapping("/get.do")
    public void get(HttpServletRequest request, HttpServletResponse response){
        String start = request.getParameter("start");
        String end = request.getParameter("end");

        if (start != null && end != null && !start.trim().equals("") && !end.trim().equals("")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StaticController.patternDate);
            Date startDate;
            Date endDate;
            try {
                startDate = simpleDateFormat.parse(start);
                endDate = simpleDateFormat.parse(end);
            }catch (Exception e){
                response.setStatus(400);
                return;
            }

            List<Machine> machines;
            try {
                machines = machineManager.getAll();
            } catch (Exception e) {
                logger.error("server not respond", e);
                response.setStatus(500);
                return;
            }

            JSONArray jsonArray = new JSONArray();
            for (Machine machinePM : machines) {
                List<PMTime> pmTimes = machinePMManager.getPMTimes(machinePM, startDate, endDate);
                for (PMTime pmTime : pmTimes) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", machinePM.getCode());
                    jsonObject.put("name", machinePM.getName());
                    jsonObject.put("date", simpleDateFormat.format(pmTime.getDate()));

                    jsonArray.add(jsonObject);
                }
            }

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
    }

    @RequestMapping("/data.do")
    public void data(HttpServletRequest request, HttpServletResponse response){
        List<Machine> machines;
        try {
            machines = machineManager.getMachines();
        } catch (Exception e) {
            logger.error("server not respond", e);
            response.setStatus(500);
            return;
        }

        JSONArray jsonArray = new JSONArray();
        for (Machine machine : machines) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", machine.getCode());
            jsonObject.put("name", machine.getName());

            jsonArray.add(jsonObject);
        }

        response.setContentType("text/JSON");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(jsonArray.toJSONString());
        } catch (IOException e) {
            logger.error("response Cant write", e);
            response.setStatus(500);
        }
    }
}
