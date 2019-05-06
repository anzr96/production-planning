package controller;

import model.entity.WorkingCalendar;
import model.entity.WorkingDays;
import model.service.WorkingCalendarManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.awt.geom.AreaOp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/document/workingCalendar")
public class WorkingCalendarController {
    @Autowired
    private WorkingCalendarManager workingCalendarManager;
    private static Logger logger = Logger.getLogger(WorkingCalendarController.class.getName());

    @RequestMapping("/register.do")
    public void register(HttpServletRequest request, HttpServletResponse response){
        long begin = 0, end = 0;
        int shift = 0;
        ArrayList<Long> workingDates = new ArrayList<>();
        JSONArray editedDates = null;
        try {
            begin = Long.parseLong(request.getParameter("begin"));
            end = Long.parseLong(request.getParameter("end"));
            try {
                shift = Integer.parseInt(request.getParameter("shift"));
            }catch (Exception e){
                shift = 8;
            }
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(request.getParameter("dates"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("wd");
            for (Object o : jsonArray) {
                workingDates.add((Long) o);
            }
            editedDates = (JSONArray) jsonObject.get("ed");
        }catch (Exception e){
            logger.error("error", e);
            response.setStatus(400);
            return;
        }

        if (begin != 0 && end != 0 && shift != 0 && workingDates.size() > 0){
            WorkingCalendar workingCalendar = new WorkingCalendar();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(end));

            workingCalendar.setYearValue(calendar.get(Calendar.YEAR));

            workingCalendar.setBeginOfYear(calendar.getTime());

            calendar.setTime(new Date(end));
            workingCalendar.setEndOfYear(calendar.getTime());

            ArrayList<WorkingDays> workingDaysArrayList = new ArrayList<>();
            for (long date : workingDates) {
                WorkingDays workingDays = new WorkingDays();
                workingDays.setDay(new Date(date));
                workingDays.setShiftHours(shift);
                workingDaysArrayList.add(workingDays);
            }
            for (Object editedDate : editedDates) {
                JSONObject jsonObject = (JSONObject) editedDate;
                WorkingDays workingDays = new WorkingDays();
                workingDays.setDay(new Date((Long) jsonObject.get("unix")));
                workingDays.setEdited(true);
                try {
                    workingDays.setReason((String) jsonObject.get("reason"));
                    workingDays.setDescription((String) jsonObject.get("des"));
                }catch (Exception e){

                }
                workingDays.setShiftHours(shift);
                workingDaysArrayList.add(workingDays);
            }
            workingCalendar.setWorkingDays(workingDaysArrayList);

            WorkingCalendar workingCalendar1 = workingCalendarManager.get(workingCalendar);
            if (workingCalendar1 == null){
                if (!workingCalendarManager.register(workingCalendar)){
                    response.setStatus(400);
                }
            }else {
                if (!workingCalendarManager.addDays(workingCalendar)){
                    response.setStatus(400);
                }
            }

        }else {
            response.setStatus(400);
        }
    }

    @RequestMapping("/get.do")
    public void getWorkingDates(HttpServletRequest request, HttpServletResponse response){
        Date begin, end;
        try {
            begin = new Date(Long.parseLong(request.getParameter("begin")));
        }catch (Exception e){
            begin = null;
        }
        try {
            end = new Date(Long.parseLong(request.getParameter("end")));
        }catch (Exception e){
            end = null;
        }
        WorkingCalendar workingCalendar = new WorkingCalendar();
        if (begin != null){
            workingCalendar.setBeginOfYear(begin);
        }
        if (end != null){
            workingCalendar.setEndOfYear(end);
        }

        if (begin == null && end == null){
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
            if (workingCalendar == null){
                try {
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("تقویم کاری تعریف نشده است");
                    response.setStatus(404);
                } catch (IOException e) {
                    response.setStatus(500);
                    logger.error("writer cant write", e);
                }
                return;
            }
        }else {
            workingCalendar = workingCalendarManager.get(workingCalendar);
        }

        JSONArray workingDates = new JSONArray();
        JSONArray editedDates = new JSONArray();
        if (workingCalendar != null){
            if (workingCalendar.getWorkingDays() != null && workingCalendar.getWorkingDays().size() > 0){
                for (WorkingDays workingDays : workingCalendar.getWorkingDays()) {
                    if (workingDays.isEdited()){
                        JSONObject edObj = new JSONObject();
                        edObj.put("unix", workingDays.getDay().getTime());
                        edObj.put("reason", workingDays.getReason());
                        edObj.put("des", workingDays.getDescription());
                        editedDates.add(edObj);
                    }else {
                        workingDates.add(workingDays.getDay().getTime());
                    }
                }
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("wd", workingDates);
        jsonObject.put("ed", editedDates);
        response.setContentType("text/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(jsonObject.toJSONString());
        } catch (IOException e) {
            logger.error("writer cant write", e);
            response.setStatus(500);
        }
    }

}
