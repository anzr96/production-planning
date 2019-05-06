package model.service;

import model.entity.RawMaterial;
import model.entity.RawMaterialInTimes;
import model.entity.RawMaterialOutTimes;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by amir on 10/7/2016.
 */
@Repository
@Transactional
public class RawMaterialManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(RawMaterialManager.class.getName());

    public boolean registerRawMaterial(RawMaterial rawMaterial) {
        RawMaterial rawMaterial1;
        rawMaterial1 = getRawMaterial(rawMaterial);

        if (rawMaterial1 == null){
            entityManager.persist(rawMaterial);

            return true;
        }
        return false;
    }

    public RawMaterial getRawMaterial(RawMaterial rawMaterial){
        try {
            return (RawMaterial) entityManager.createQuery("select p from RawMaterial p where p.rawCode=:y")
                    .setParameter("y", rawMaterial.getRawCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<RawMaterial> getRawMaterials(){
        try {
            return entityManager.createQuery("select p from RawMaterial p ")
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public boolean enterRawMaterial(RawMaterial rawMaterial){
        RawMaterial rawMaterial1 = getRawMaterial(rawMaterial);
        if (rawMaterial1 != null){
            rawMaterial1.setTotal(rawMaterial.getTotal() + rawMaterial1.getTotal());
            rawMaterial1.setDate(rawMaterial.getDate());
            rawMaterial1.setTotallasttime(rawMaterial.getTotallasttime());
            ArrayList<RawMaterialInTimes> rawMaterialInTimesArrayList = new ArrayList<>() ;
            for (RawMaterialInTimes rawMaterialInTimes : rawMaterial.getRawMaterialInTimes()) {
                RawMaterialInTimes rawMaterialInTimes1 = getSpecific(rawMaterialInTimes);
                if (rawMaterialInTimes1 != null){
                    rawMaterialInTimes1.setTotal(rawMaterialInTimes1.getTotal() + rawMaterialInTimes.getTotal());
                    rawMaterialInTimesArrayList.add(rawMaterialInTimes1);
                }else {
                    rawMaterialInTimesArrayList.add(rawMaterialInTimes);
                }
            }
            rawMaterial1.setRawMaterialInTimes(rawMaterialInTimesArrayList);

            entityManager.persist(rawMaterial1);
            return true;
        }else {
            return registerRawMaterial(rawMaterial);
        }
    }

    public RawMaterialInTimes getSpecific(RawMaterialInTimes rawMaterialInTimes){
        try {
            return (RawMaterialInTimes) entityManager.createQuery("select p from RawMaterialInTimes p where p.date=:x and p.loadNumber=:y and p.bacthNumber=:z")
                    .setParameter("x", rawMaterialInTimes.getDate())
                    .setParameter("y", rawMaterialInTimes.getLoadNumber())
                    .setParameter("z", rawMaterialInTimes.getBatchNumber())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public boolean useRawMaterial(RawMaterial rawMaterial) {
        RawMaterial rawMaterial1 = getRawMaterial(rawMaterial);
        if (rawMaterial1 == null){
            return false;
        }
        if(rawMaterial1.getTotal() >= rawMaterial.getTotal()){
            rawMaterial1.setTotal(rawMaterial1.getTotal() - rawMaterial.getTotal());

            for (RawMaterialOutTimes rawMaterialOutTimes : rawMaterial.getRawMaterialOutTimes()) {
                try {
                    RawMaterialOutTimes rawMaterialOutTimes1 = (RawMaterialOutTimes) entityManager.createQuery("select t from RawMaterial p join p.rawMaterialOutTimes t where p.rawCode=:x and t.batchNumber=:y and t.date=:s1")
                            .setParameter("x", rawMaterial.getRawCode())
                            .setParameter("y", rawMaterialOutTimes.getBatchNumber())
                            .setParameter("s1", rawMaterialOutTimes.getDate())
                            .getSingleResult();
                    rawMaterialOutTimes1.setTotal(rawMaterialOutTimes1.getTotal() + rawMaterialOutTimes.getTotal());
                    entityManager.persist(rawMaterialOutTimes1);

                }catch (Exception e){
                    ArrayList<RawMaterialOutTimes> rawMaterialOutTimesArrayList = new ArrayList<>();
                    rawMaterialOutTimesArrayList.add(rawMaterialOutTimes);
                    rawMaterial1.setRawMaterialOutTimes(rawMaterialOutTimesArrayList);
//                    if (rawMaterial1.getRawMaterialOutTimes() != null){
//                        rawMaterial1.getRawMaterialOutTimes().add(rawMaterialOutTimes);
//                        try {
//                            entityManager.persist(rawMaterial);
//                        }catch (Exception e1){
//                            logger.error(e1);
//                            return false;
//                        }
//                    }else{
//                        return false;
//                    }
                }
            }

            entityManager.persist(rawMaterial1);
            return true;
        }else {
            return false;
        }
    }

    public boolean delete(RawMaterial rawMaterial) {
        rawMaterial = getRawMaterial(rawMaterial);
        try {
            entityManager.remove(rawMaterial);
        }catch (Exception e){
            return false;
        }

        return true;
    }
}
