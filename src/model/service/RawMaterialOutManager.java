package model.service;

import model.entity.RawMaterial;
import model.entity.RawMaterialOutTimes;
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
 * Created by amir on 1/27/17.
 */
@Repository
@Transactional
public class RawMaterialOutManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(RawMaterialOutManager.class.getName());

    public boolean add(RawMaterial rawMaterialOut) {

        return true;
    }

    public boolean edit(RawMaterial rawMaterialOut, RawMaterial materialOut) throws Exception {
        RawMaterialOutTimes rawMaterialOutTimes = rawMaterialOut.getRawMaterialOutTimes().get(0);
        try {
            rawMaterialOutTimes = (RawMaterialOutTimes) entityManager.createQuery("select t from RawMaterial p join p.rawMaterialOutTimes t where p.rawCode=:x and t.batchNumber=:y and t.date between :s1 and :s2")
                    .setParameter("x", rawMaterialOut.getRawCode())
                    .setParameter("y", rawMaterialOutTimes.getBatchNumber())
                    .setParameter("s1", rawMaterialOutTimes.getDate())
                    .setParameter("s2", rawMaterialOutTimes.getDate())
                    .getSingleResult();
        }catch (Exception e){
            return false;
        }

        try {
            rawMaterialOutTimes.setDate(materialOut.getRawMaterialOutTimes().get(0).getDate());


        }catch (Exception e){
            logger.error(e);
        }
        try {
            rawMaterialOutTimes.setTotal(materialOut.getRawMaterialOutTimes().get(0).getTotal());
        }catch (Exception e){
            logger.error(e);
        }
        try {
            rawMaterialOutTimes.setBatchNumber(materialOut.getRawMaterialOutTimes().get(0).getBatchNumber());
        }catch (Exception e){
            logger.error(e);
        }

        try {
            entityManager.persist(rawMaterialOutTimes);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public RawMaterial get(RawMaterial rawMaterialOut) {
        try {
            return (RawMaterial) entityManager.createQuery("select p from RawMaterial p where p.rawCode=:x")
                    .setParameter("x", rawMaterialOut.getRawCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<RawMaterialOutTimes> getTimes(RawMaterial rawMaterialOut, Date start, Date end) {
        try {
            return  entityManager.createQuery("select t from RawMaterial p join p.rawMaterialOutTimes t where p.rawCode=:x and t.date between :s1 and :s2")
                    .setParameter("x", rawMaterialOut.getRawCode())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<RawMaterial> getRawsTimes(List<RawMaterial> rawMaterialOut, Date start, Date end) {
        ArrayList<String> strings = new ArrayList<>();
        for (RawMaterial materialOut : rawMaterialOut) {
            strings.add(materialOut.getRawCode());
        }
        try {
            return entityManager.createQuery("select p from RawMaterial p join p.rawMaterialOutTimes t where p.rawCode IN :x and t.date between :s1 and :s2")
                    .setParameter("x", strings.toArray())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<RawMaterialOutTimes> getAllTimes(Date start, Date end) {
        try {
            return entityManager.createQuery("select p from RawMaterial p join p.rawMaterialOutTimes t where t.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;

        }
    }
}
