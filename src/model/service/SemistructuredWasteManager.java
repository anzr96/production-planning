package model.service;

import model.entity.Semistructured;
import model.entity.SemistructuredReworkTime;
import model.entity.SemistructuredWasteTime;
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
public class SemistructuredWasteManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(SemistructuredWasteManager.class.getName());

    public boolean addWaste(Semistructured semistructuredWaste)  {
        Semistructured semistructuredWaste1 = get(semistructuredWaste);
        if (semistructuredWaste1 != null){
            ArrayList<SemistructuredWasteTime> wasteTimes = new ArrayList<>();
            for (SemistructuredWasteTime semistructuredWasteTime : semistructuredWaste.getSemistructuredWasteTimes()) {
                try {
                    SemistructuredWasteTime semistructuredWasteTime1 = (SemistructuredWasteTime) entityManager.createQuery("select t from Semistructured p join p.semistructuredWasteTimes t where p.semiCode=:x and t.batchNumber=:y and t.date=:z")
                            .setParameter("x", semistructuredWaste.getSemiCode())
                            .setParameter("y", semistructuredWasteTime.getBatchNumber())
                            .setParameter("z", semistructuredWasteTime.getDate())
                            .getSingleResult();
                    semistructuredWasteTime1.setTotal(semistructuredWasteTime1.getTotal() + semistructuredWasteTime.getTotal());
                    if (!persist(semistructuredWasteTime1, false))
                        return false;
                }catch (Exception e){
                    wasteTimes.add(semistructuredWasteTime);
                }
            }
            if (wasteTimes.size() > 0){
                semistructuredWaste1.setSemistructuredWasteTimes(wasteTimes);
                return persist(semistructuredWaste1, false);
            }
        }
        return false;
    }

    public boolean editWaste(Semistructured semistructuredWaste)  {
        for (SemistructuredWasteTime semistructuredWasteTime : semistructuredWaste.getSemistructuredWasteTimes()) {
            SemistructuredWasteTime semistructuredWasteTime1;
            try {
                semistructuredWasteTime1 = (SemistructuredWasteTime) entityManager.createQuery("select p from SemistructuredWaste p join p.semistructuredWasteTimes where p.code=:x and t.batchNumber=:y and t.date=:z")
                        .setParameter("x", semistructuredWaste.getSemiCode())
                        .setParameter("y", semistructuredWasteTime.getBatchNumber())
                        .setParameter("z", semistructuredWasteTime.getDate())
                        .getSingleResult();
                semistructuredWasteTime1.setTotal(semistructuredWasteTime.getTotal());
            }catch (Exception e){
                return false;
            }
            if (!persist(semistructuredWasteTime1, false)){
                return false;
            }
        }
        return true;
    }

    public boolean deleteWaste(Semistructured semistructuredWaste)  {
        for (SemistructuredWasteTime semistructuredWasteTime : semistructuredWaste.getSemistructuredWasteTimes()) {
            SemistructuredWasteTime semistructuredWasteTime1;
            try {
                semistructuredWasteTime1 = (SemistructuredWasteTime) entityManager.createQuery("select p from SemistructuredWaste p join p.semistructuredWasteTimes where p.code=:x and t.batchNumber=:y and t.date=:z")
                        .setParameter("x", semistructuredWaste.getSemiCode())
                        .setParameter("y", semistructuredWasteTime.getBatchNumber())
                        .setParameter("z", semistructuredWasteTime.getDate())
                        .getSingleResult();
            }catch (Exception e){
                return false;
            }
            if (!persist(semistructuredWasteTime1, true)){
                return false;
            }
        }
        return true;
    }

    public Semistructured get(Semistructured semistructuredWaste)  {
        try {
            return (Semistructured) entityManager.createQuery("select p from Semistructured p where p.semiCode=:x")
                    .setParameter("x", semistructuredWaste.getSemiCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }

    }

    public List<SemistructuredWasteTime> getSemiTime(Semistructured semistructuredWaste, Date start, Date end)  {
        try {
            return entityManager.createQuery("select t from Semistructured p join p.semistructuredWasteTimes t where p.semiCode=:x and t.date between :s1 and :s2")
                    .setParameter("x", semistructuredWaste.getSemiCode())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }

    }

    public List<Semistructured> getBatchTime(String batchNumber, Date start, Date end)  {
        try {
            return entityManager.createQuery("select p from Semistructured p join p.semistructuredWasteTimes t where t.batchNumber=:x and t.date between :s1 and :s2")
                    .setParameter("x", batchNumber)
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }

    }

    public List<SemistructuredWasteTime> getAllTimes(Date start, Date end) {
        try {
            return entityManager.createQuery("select t from Semistructured p join p.semistructuredWasteTimes t where t.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    private boolean persist(Object object, boolean remove){
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
