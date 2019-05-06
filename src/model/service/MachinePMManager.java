package model.service;

import model.entity.Machine;
import model.entity.PMTime;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 1/30/17.
 */
@Repository
@Transactional
public class MachinePMManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(MachinePMManager.class.getName());

    public boolean addPM(Machine machinePM) throws Exception {
        Machine machine = get(machinePM);
        ArrayList<PMTime> pmTimes = new ArrayList<>();
        for (PMTime pmTime : machinePM.getPmTimes()) {
            PMTime pmTime1 = getPmTime(machinePM, pmTime.getDate());
            if (pmTime1 == null){
                pmTimes.add(pmTime);
            }else {
                return false;
            }
        }
        machine.setPmTimes(pmTimes);
        return persist(machine, false);
    }

    public boolean editPM(Machine machinePM) {
        for (PMTime pmTime : machinePM.getPmTimes()) {
            PMTime pmTime1 = getPmTime(machinePM, pmTime.getDate());
            if (pmTime1 != null){
                if (pmTime.getStart() != null){
                    pmTime1.setStart(pmTime.getStart());
                }
                if (pmTime.getEnd() != null){
                    pmTime1.setEnd(pmTime.getEnd());
                }
                if (!persist(pmTime1, false))
                    return false;
            }
        }
        return true;
    }

    public boolean deletePM(Machine machinePM){
        for (PMTime pmTime : machinePM.getPmTimes()) {
            PMTime time = getPmTime(machinePM, pmTime.getDate());
            if (time == null){
                entityManager.getTransaction().rollback();
                return false;
            }
            if (!persist(time, true))
                return false;
        }
        return true;
    }

    public Machine get(Machine machinePM) {
        try {
            return (Machine) entityManager.createQuery("select  p from Machine p where p.code=:x")
                    .setParameter("x", machinePM.getCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<PMTime> getPMTimes(Machine machinePM, Date start, Date end){
        try {
            return entityManager.createQuery("select t from Machine p join p.pmTimes t where p.code=:x and t.date between :s1 and :s2")
                    .setParameter("x", machinePM.getCode())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }

    }

    public PMTime getPmTime(Machine machinePM, Date date){
        try {
            return (PMTime) entityManager.createQuery("select t from Machine p join p.pmTimes t where p.code=:x and t.date=:y")
                    .setParameter("x", machinePM.getCode())
                    .setParameter("y", date)
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public Machine getMachineTime(Machine machinePM, Date start, Date end) {
        try {
            return (Machine) entityManager.createQuery("select p from Machine p join p.pmTimes t where p.code=:x and t.date between :s1 and :s2")
                    .setParameter("x", machinePM.getCode())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public boolean persist(Object object, boolean remove){
        try {
            if (remove){
                entityManager.remove(object);
            }else {
                entityManager.persist(object);
            }
            return true;
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            logger.error(e);
            return false;
        }
    }
}
