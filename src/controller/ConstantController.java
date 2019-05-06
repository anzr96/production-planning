package controller;

import model.entity.Constant;
import model.service.ConstantManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amir on 2/11/17.
 */
@Controller
@RequestMapping("/document/constant")
public class ConstantController {
    @Autowired
    private ConstantManager constantManager;
    private static Logger logger = Logger.getLogger(ConstantController.class.getName());

    @RequestMapping("/register.do")
    public void register(HttpServletRequest request, HttpServletResponse response){
        String d = request.getParameter("d");
        String working = request.getParameter("w");
        String shift = request.getParameter("s");

        if (d != null && working != null && shift != null && !d.trim().equals("") && !working.trim().equals("") && !shift.trim().equals("")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            Date date;
            try {
                date = simpleDateFormat.parse(d);
            } catch (ParseException e) {
                logger.error("Cant parse", e);
                response.setStatus(400);
                return;
            }

            Constant constant = new Constant();
            constant.setDate(date);
            try {
                constant.setWorkingDatesNumber(Integer.parseInt(working));
                constant.setShiftHour(Integer.parseInt(shift));
            }catch (Exception e){
                logger.error("Cant parse", e);
                response.setStatus(400);
                return;
            }

            try {
                constantManager.register(constant);
            } catch (Exception e) {
                logger.error("server Cant write", e);
                response.setStatus(400);
            }
        }else {
            logger.error("data doesnt correct");
            response.setStatus(400);
        }
    }
}
