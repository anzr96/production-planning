package model.service;

import model.entity.RawMaterial;
import model.entity.RawMaterialInTimes;
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
public class RawMaterialInManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(RawMaterialInManager.class.getName());

    public boolean edit(RawMaterial rawMaterialIn, RawMaterial materialIn) {
        RawMaterialInTimes rawMaterialInTimes = getRawMaterialIn(rawMaterialIn);
        if (rawMaterialInTimes == null){
            return false;
        }
        RawMaterialInTimes materialInTimes;
        try {
            materialInTimes  = materialIn.getRawMaterialInTimes().get(0);
        }catch (Exception e){
            logger.error("array empty", e);
            return false;
        }

        try {
            if (materialInTimes.getBatchNumber() != null && !materialInTimes.getBatchNumber().trim().equals("")){
                rawMaterialInTimes.setBatchNumber(materialInTimes.getBatchNumber());
            }
            if (materialInTimes.getLoadNumber() != null && !materialInTimes.getLoadNumber().trim().equals("")){
                rawMaterialInTimes.setLoadNumber(materialIn.getRawMaterialInTimes().get(0).getLoadNumber());
            }
            if (materialInTimes.getDate() != null){
                rawMaterialInTimes.setDate(materialIn.getRawMaterialInTimes().get(0).getDate());
            }
            if (materialInTimes.getTotal() > 0){
                rawMaterialInTimes.setTotal(materialIn.getRawMaterialInTimes().get(0).getTotal());
            }
        }catch (Exception e){
            return false;
        }

        try {
            entityManager.persist(rawMaterialInTimes);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public RawMaterialInTimes getRawMaterialIn(RawMaterial rawMaterial){
        try {
            RawMaterialInTimes rawMaterialInTimes = rawMaterial.getRawMaterialInTimes().get(0);
            return (RawMaterialInTimes) entityManager.createQuery("select t from RawMaterial p join p.rawMaterialInTimes t where p.rawCode=:x and t.batchNumber=:y and t.loadNumber=:z and t.date=:w")
                    .setParameter("x", rawMaterial.getRawCode())
                    .setParameter("y", rawMaterialInTimes.getBatchNumber())
                    .setParameter("z", rawMaterialInTimes.getLoadNumber())
                    .setParameter("w", rawMaterialInTimes.getDate())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public RawMaterial getRawMaterial(RawMaterial rawMaterialIn)  {
        try {
            return (RawMaterial) entityManager.createQuery("select p from RawMaterial p where p.rawCode=:x")
                    .setParameter("x", rawMaterialIn.getRawCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<RawMaterialInTimes> getTimes(RawMaterial rawMaterialIn, Date start, Date end) {
        try{
            return entityManager.createQuery("select distinct t from RawMaterial p join p.rawMaterialInTimes t where p.rawCode=:x and t.date between :s1 and :s2")
                    .setParameter("x", rawMaterialIn.getRawCode())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<RawMaterial> getRawsTimes(List<RawMaterial> rawMaterialIns, Date start, Date end) {
        ArrayList<String> strings = new ArrayList<>();
        for (RawMaterial rawMaterialIn : rawMaterialIns) {
            strings.add(rawMaterialIn.getRawCode());
        }
        try {
            return entityManager.createQuery("select p from RawMaterial p join p.rawMaterialInTimes t where p.rawCode IN :x and t.date between :s1 and :s2")
                    .setParameter("x", strings.toArray())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<RawMaterialInTimes> getAllTimes(Date start, Date end) {
        try {
            return entityManager.createQuery("select t from RawMaterial p join p.rawMaterialInTimes t where t.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
