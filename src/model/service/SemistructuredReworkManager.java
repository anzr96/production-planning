package model.service;

import model.entity.Semistructured;
import model.entity.SemistructuredReworkTime;
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
public class SemistructuredReworkManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(SemistructuredReworkManager.class.getName());

    public boolean addRework(Semistructured semistructuredRework) {
        Semistructured semistructuredRework1 = get(semistructuredRework);
        if (semistructuredRework1 != null){
            ArrayList<SemistructuredReworkTime> reworkTimeArrayList = new ArrayList<>();
            for (SemistructuredReworkTime semistructuredReworkTime : semistructuredRework.getSemistructuredReworkTimes()) {
                SemistructuredReworkTime semistructuredReworkTime1;
                try {
                    semistructuredReworkTime1 = (SemistructuredReworkTime) entityManager.createQuery("select p from SemistructuredRework p join p.semistructuredReworkTimes t where p.code=:x and t.date=:y and t.batchNumber=:z")
                            .setParameter("x", semistructuredRework.getSemiCode())
                            .setParameter("y", semistructuredReworkTime.getDate())
                            .setParameter("z", semistructuredReworkTime.getBatchNumber())
                            .getSingleResult();
                    semistructuredReworkTime1.setTotal(semistructuredReworkTime1.getTotal() + semistructuredReworkTime.getTotal());
                    try {
                        entityManager.persist(semistructuredReworkTime1);
                    }catch (Exception e){
                        entityManager.getTransaction().rollback();
                        logger.error(e);
                        return false;
                    }
                }catch (Exception e){
                    reworkTimeArrayList.add(semistructuredReworkTime);
                }
            }
            if (reworkTimeArrayList.size() > 0){
                semistructuredRework1.setSemistructuredReworkTimes(reworkTimeArrayList);
            }
            try {
                entityManager.persist(semistructuredRework1);
            }catch (Exception e){
                entityManager.getTransaction().rollback();
                logger.error(e);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean editTime(Semistructured semistructuredRework) throws Exception {
        for (SemistructuredReworkTime semistructuredReworkTime : semistructuredRework.getSemistructuredReworkTimes()) {
            SemistructuredReworkTime semistructuredReworkTime1;
            try {
                semistructuredReworkTime1 = (SemistructuredReworkTime) entityManager.createQuery("select p from SemistructuredRework p join p.semistructuredReworkTimes t where p.code=:x and t.date=:y and t.batchNumber=:z")
                        .setParameter("x", semistructuredRework.getSemiCode())
                        .setParameter("y", semistructuredReworkTime.getDate())
                        .setParameter("z", semistructuredReworkTime.getBatchNumber())
                        .getSingleResult();
                semistructuredReworkTime1.setTotal(semistructuredReworkTime.getTotal());
            }catch (Exception e){
                return false;
            }
            try {
                entityManager.persist(semistructuredReworkTime1);
            }catch (Exception e){
                entityManager.getTransaction().rollback();
                logger.error(e);
                return false;
            }
        }
        return true;
    }

    public boolean deleteTime(Semistructured semistructuredRework) throws Exception {
        for (SemistructuredReworkTime semistructuredReworkTime : semistructuredRework.getSemistructuredReworkTimes()) {
            SemistructuredReworkTime semistructuredReworkTime1;
            try {
                semistructuredReworkTime1 = (SemistructuredReworkTime) entityManager.createQuery("select p from SemistructuredRework p join p.semistructuredReworkTimes t where p.code=:x and t.date=:y and t.batchNumber=:z")
                        .setParameter("x", semistructuredRework.getSemiCode())
                        .setParameter("y", semistructuredReworkTime.getDate())
                        .setParameter("z", semistructuredReworkTime.getBatchNumber())
                        .getSingleResult();
            }catch (Exception e){
                return false;
            }
            try {
                entityManager.remove(semistructuredReworkTime1);
            }catch (Exception e){
                entityManager.getTransaction().rollback();
                logger.error(e);
                return false;
            }
        }
        return true;
    }

    public Semistructured get(Semistructured semistructuredRework) {
        try {
            return (Semistructured) entityManager.createQuery("select p from Semistructured p where p.semiCode=:x")
                    .setParameter("x", semistructuredRework.getSemiCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public SemistructuredReworkTime getSemiReworkTime(Semistructured semistructuredRework, Date start, Date end) {
        try {
            return (SemistructuredReworkTime) entityManager.createQuery("select t from Semistructured p join p.semistructuredReworkTimes t where p.semiCode=:x and t.date between :s1 and :s2")
                    .setParameter("x", semistructuredRework.getSemiCode())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<SemistructuredReworkTime> getTime(Date start, Date end) {
        try {
            return entityManager.createQuery("select t from Semistructured p join p.semistructuredReworkTimes t where t.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<SemistructuredReworkTime> getBatchTime(String batchNumber, Date start, Date end) {
        try {
            return entityManager.createQuery("select t from Semistructured p join p.semistructuredReworkTimes t where t.batchNumber=:x and t.date between :s1 and :s2")
                    .setParameter("x", batchNumber)
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
