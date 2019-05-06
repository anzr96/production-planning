package model.service;

import model.entity.PlaningShift;
import model.entity.PlaningTimes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TemporalType;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 11/8/16.
 */
@Repository
@Transactional
public class PlaningTimesManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public boolean newTime(PlaningTimes planingTimes) {
        try {
            entityManager.persist(planingTimes);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean delete(PlaningTimes planingTimes) {
        planingTimes = getTime(planingTimes);
        if (planingTimes == null){
            return false;
        }
        try {
            entityManager.remove(planingTimes);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean update(PlaningTimes planingTimes){
        PlaningTimes planingTimes1 = getTime(planingTimes);
        if (planingTimes1 != null) {
            int temp = planingTimes.getPlaningShifts().size();
            for (PlaningShift planingShift : planingTimes.getPlaningShifts()) {
                for (PlaningShift shift : planingTimes1.getPlaningShifts()) {
                    if (shift.getShift() == planingShift.getShift()){
                        shift.setPlaningProducts(planingShift.getPlaningProducts());
                        shift.setPlaningRaws(planingShift.getPlaningRaws());
                        shift.setPlaningSections(planingShift.getPlaningSections());
                        temp--;
                        planingShift.setShift(0);
                    }
                }
            }
            if (temp != 0){
                for (PlaningShift planingShift : planingTimes.getPlaningShifts()) {
                    if (planingShift.getShift() != 0){
                        planingTimes1.getPlaningShifts().add(planingShift);
                    }
                }
            }
            try {
                entityManager.persist(planingTimes1);
                return true;
            }catch (Exception e){
                return false;
            }
        }else {
            return newTime(planingTimes);
        }
    }

    public PlaningTimes getTime(PlaningTimes planingTimes) {
        try {
            return (PlaningTimes) entityManager.createQuery("select p from PlaningTimes p where p.time=:x and p.edited=:y")
                    .setParameter("x", planingTimes.getTime())
                    .setParameter("y", planingTimes.isEdited())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<PlaningTimes> getTimesBetween(Date begin, Date end) {
        try {
            return entityManager.createQuery("select p from PlaningTimes p where p.time between :t1 and :t2")
                    .setParameter("t1", begin, TemporalType.DATE)
                    .setParameter("t2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<PlaningTimes> getTimes() {
        try {
            return entityManager.createQuery("select p from PlaningTimes p")
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
