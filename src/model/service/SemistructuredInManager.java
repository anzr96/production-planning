package model.service;

import model.entity.Semistructured;
import model.entity.SemistructuredInTimes;
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
public class SemistructuredInManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(SemistructuredInManager.class.getName());

    public boolean deleteTime(Semistructured semistructuredIn) throws Exception {
        boolean check = true;
        ArrayList<SemistructuredInTimes> times = new ArrayList<>();
        for (SemistructuredInTimes semistructuredInTimes : semistructuredIn.getSemistructuredInTimes()) {
            try {
                times.add(getSemistructuredInTimes(semistructuredIn, semistructuredInTimes));
            }catch (Exception e){
                check = false;
            }
        }
        if (check){
            try {
                entityManager.createQuery("delete from SemistructuredInTimes p where p in :x")
                        .setParameter("x", times)
                        .executeUpdate();
            }catch (Exception e){
                logger.error(e);
                return false;
            }
            return true;
        }
        return false;
    }

    public SemistructuredInTimes getSemistructuredInTimes(Semistructured semistructured, SemistructuredInTimes semistructuredInTimes){
        try {
            return (SemistructuredInTimes) entityManager.createQuery("select t from Semistructured p join p.semistructuredInTimes t where p.semiCode=:x and t.batchNumber=:y and t.date=:z")
                    .setParameter("x", semistructured.getSemiCode())
                    .setParameter("y", semistructuredInTimes.getBatchNumber())
                    .setParameter("z", semistructuredInTimes.getDate(), TemporalType.TIMESTAMP)
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public Semistructured get(Semistructured semistructuredIn) {
        try {
            return (Semistructured) entityManager.createQuery("select p from Semistructured p where p.semiCode=:x")
                    .setParameter("x", semistructuredIn.getSemiCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<SemistructuredInTimes> getSemiTime(Semistructured semistructuredIn, Date start, Date end) {
        try {
            return entityManager.createQuery("select t from Semistructured p join p.semistructuredInTimes t where p.semiCode=:x and t.date between :s1 and :s2")
                    .setParameter("x", semistructuredIn.getSemiCode())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Semistructured> getTime(Date start, Date end) {
        try {
            return entityManager.createQuery("select p from Semistructured p join p.semistructuredInTimes t where t.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Semistructured> getBatchTime(String batchNumber, Date start, Date end) {
        try {
            return entityManager.createQuery("select p from Semistructured p join p.semistructuredInTimes t where t.batchNumber=:x and t.date between :s1 and :s2")
                    .setParameter("x", batchNumber)
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<SemistructuredInTimes> getAlltimes(Date start, Date end) {
        try {
            return entityManager.createQuery("select t from Semistructured p join p.semistructuredInTimes t where t.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
