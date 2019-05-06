package model.service;

import model.entity.Semistructured;
import model.entity.SemistructuredOutTimes;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 1/30/17.
 */
@Repository
@Transactional
public class SemistructuredOutManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(SemistructuredOutManager.class.getName());

    public boolean deleteTime(Semistructured semistructuredOut) {
        for (SemistructuredOutTimes semistructuredOutTimes : semistructuredOut.getSemistructuredOutTimes()) {
            try {
                entityManager.remove(getSpecific(semistructuredOut, semistructuredOutTimes));
            }catch (Exception e){
                entityManager.getTransaction().rollback();
                logger.error(e);
                return false;
            }
        }
        return true;
    }

    public Semistructured get(Semistructured semistructuredOut) {
        try {
            return (Semistructured) entityManager.createQuery("select p from Semistructured p where p.semiCode=:x")
                    .setParameter("x", semistructuredOut.getSemiCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public SemistructuredOutTimes getSpecific(Semistructured semistructured, SemistructuredOutTimes semistructuredOutTimes){
        try {
            String query = "select t from Semistructured p join p.semistructuredOutTimes t where p.semiCode=:x ";
            if (semistructuredOutTimes.getBatchNumber() != null && !semistructuredOutTimes.getBatchNumber().trim().equals("")){
                query += "and t.batchNumber=:y ";
            }
            if (semistructuredOutTimes.getDate() != null){
                query += "and t.date=:t1";
            }
            Query query1 = entityManager.createQuery(query);
            query1.setParameter("x", semistructured.getSemiCode());
            if (semistructuredOutTimes.getBatchNumber() != null && !semistructuredOutTimes.getBatchNumber().trim().equals("")){
                query1.setParameter("y", semistructuredOutTimes.getBatchNumber());
            }
            if (semistructuredOutTimes.getDate() != null){
                query1.setParameter("t1", semistructuredOutTimes.getDate());
            }
            return (SemistructuredOutTimes) query1.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<Semistructured> getTime(Date start, Date end) {
        try {
            return entityManager.createQuery("select p from Semistructured p join p.semistructuredOutTimes t where t.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Semistructured> getBatchTime(String batchNumber, Date start, Date end) {
        try {
            return entityManager.createQuery("select p from Semistructured p join p.semistructuredOutTimes t where t.batchNumber=:x and t.date between :s1 and :s2")
                    .setParameter("x", batchNumber)
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<SemistructuredOutTimes> getAllTimes(Date start, Date end) {
        try {
            return entityManager.createQuery("select p from Semistructured p join p.semistructuredOutTimes t where t.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
