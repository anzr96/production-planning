package model.service;

import model.entity.WorkingCalendar;
import model.entity.WorkingDays;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class WorkingCalendarManager {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(WorkingCalendarManager.class.getName());

    public boolean register(WorkingCalendar workingCalendar){
        if (get(workingCalendar) == null){
            try {
                entityManager.persist(workingCalendar);
                return true;
            }catch (Exception e){
                logger.error("error while persist", e);
                return false;
            }
        }else {
            return false;
        }
    }

    public boolean delete(WorkingCalendar workingCalendar){
        workingCalendar = get(workingCalendar);
        if (workingCalendar != null){
            try {
                entityManager.remove(workingCalendar);
                return true;
            }catch (Exception e){
                logger.error("error while remove", e);
                return false;
            }
        }
        return false;
    }

    public boolean edit(WorkingCalendar workingCalendar){
        WorkingCalendar workingCalendar1 = get(workingCalendar);
        if (workingCalendar1 != null){
            workingCalendar1.setBeginOfYear(workingCalendar.getBeginOfYear());
            workingCalendar1.setEndOfYear(workingCalendar.getEndOfYear());
            try {
                entityManager.persist(workingCalendar1);
                return true;
            }catch (Exception e){
                logger.error("error while persist", e);
                return false;
            }
        }
        return false;
    }

    public boolean addDays(WorkingCalendar workingCalendar){
        WorkingCalendar workingCalendar1 = get(workingCalendar);
        if (workingCalendar1 != null){
            for (WorkingDays workingDays : workingCalendar1.getWorkingDays()) {
                try {
                    entityManager.remove(workingDays);
                }catch (Exception e){
                    logger.error("cant remove", e);
                }
            }
            workingCalendar1.setWorkingDays(workingCalendar.getWorkingDays());
            try {
                entityManager.persist(workingCalendar1);
                return true;
            }catch (Exception e){
                logger.error("error while persist", e);
                return false;
            }
        }
        return false;
    }

    public boolean editDays(WorkingCalendar workingCalendar){
        return false;
    }

    public boolean deleteDays(WorkingCalendar workingCalendar){
        WorkingCalendar workingCalendar1 = get(workingCalendar);
        if (workingCalendar1 != null){
            List<WorkingDays> workingDays = getInDays(workingCalendar);
            ArrayList<WorkingDays> workingDaysArrayList = new ArrayList<>();
            if (workingDays != null){
                for (WorkingDays workingDay : workingDays) {
                    for (WorkingDays days : workingCalendar.getWorkingDays()) {
                        if (checkEqualDate(workingDay.getDay(), days.getDay())){
                            workingDaysArrayList.add(workingDay);
                        }
                    }
                }
            }
            if (workingDaysArrayList.size() > 0){
                try {
                    for (WorkingDays days : workingDaysArrayList) {
                        entityManager.remove(days);
                    }
                    return true;
                }catch (Exception e){
                    logger.error("error while remove", e);
                    return false;
                }
            }
        }
        return false;
    }

    public WorkingCalendar get(WorkingCalendar workingCalendar){
        try {
            return (WorkingCalendar) entityManager.createQuery("select p from WorkingCalendar p where p.beginOfYear=:x or p.endOfYear=:y")
                    .setParameter("x", workingCalendar.getBeginOfYear(), TemporalType.DATE)
                    .setParameter("y", workingCalendar.getEndOfYear(), TemporalType.DATE)
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public WorkingCalendar getYear(WorkingCalendar workingCalendar){
        try {
            return (WorkingCalendar) entityManager.createQuery("select p from WorkingCalendar p where p.yearValue=:x")
                    .setParameter("x", workingCalendar.getYearValue())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<WorkingDays> getDays(WorkingCalendar workingCalendar){
        try {
            if (workingCalendar.getBeginOfYear() != null){
                return entityManager.createQuery("select t from WorkingCalendar p join p.workingDays t where p.beginOfYear=:x")
                        .setParameter("x", workingCalendar.getBeginOfYear())
                        .getResultList();
            }else if (workingCalendar.getEndOfYear() != null){
                return entityManager.createQuery("select t from WorkingCalendar p join p.workingDays t where p.endOfYear=:x")
                        .setParameter("x", workingCalendar.getEndOfYear())
                        .getResultList();
            }else {
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    public List<WorkingDays> getBetweenDays(WorkingCalendar workingCalendar, Date start, Date end){
        try {
            if (workingCalendar.getBeginOfYear() != null){
                return entityManager.createQuery("select t from WorkingCalendar p join p.workingDays t where p.beginOfYear=:x and t.day between :t1 and :t2")
                        .setParameter("x", workingCalendar.getBeginOfYear())
                        .setParameter("t1", start, TemporalType.DATE)
                        .setParameter("t2", end, TemporalType.DATE)
                        .getResultList();
            }else if (workingCalendar.getEndOfYear() != null){
                return entityManager.createQuery("select t from WorkingCalendar p join p.workingDays t where p.endOfYear=:x and t.day between :t1 and :t2")
                        .setParameter("x", workingCalendar.getEndOfYear())
                        .setParameter("t1", start, TemporalType.DATE)
                        .setParameter("t2", end, TemporalType.DATE)
                        .getResultList();
            }else {
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    public List<WorkingDays> getInDays(WorkingCalendar workingCalendar){
        try {
            ArrayList<Date> dates = new ArrayList<>();
            for (WorkingDays workingDays : workingCalendar.getWorkingDays()) {
                dates.add(workingDays.getDay());
            }
            return entityManager.createQuery("select t from WorkingCalendar p join p.workingDays t where p.beginOfYear=:x and t.day in :y")
                    .setParameter("x", workingCalendar.getBeginOfYear())
                    .setParameter("y", dates)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public WorkingDays getDay(WorkingCalendar workingCalendar, WorkingDays workingDays){
        try {
            return (WorkingDays) entityManager.createQuery("select t from WorkingCalendar p join p.workingDays t where p.yearValue=:x and t.day=:y")
                    .setParameter("x", workingCalendar.getYearValue())
                    .setParameter("y", workingDays.getDay())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<WorkingCalendar> getAll(){
        try {
            return entityManager.createQuery("select p from WorkingCalendar p").getResultList();
        }catch (Exception e){
            return null;
        }
    }

    private boolean checkEqualDate(Date date1, Date date2){
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(date1);
        calendar2.setTime(date2);

        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

}
