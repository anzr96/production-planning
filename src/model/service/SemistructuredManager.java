package model.service;

import model.entity.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amir on 11/9/16.
 */
@Repository
@Transactional
public class SemistructuredManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(SemistructuredManager.class.getName());

    public boolean registerSemistructured(Semistructured semistructured) throws Exception {
        entityManager.persist(semistructured);
        return true;
    }

    public Semistructured getSemistructured(Section section, MachineGroup machineGroup, Semistructured semistructured) {
        try {
            return (Semistructured) entityManager.createQuery("select w from Section p join p.machineGroups t join t.machineSemis w where (p.code=:x1 or p.name=:x2) and (t.code=:y1 or t.name=:y2) and (w.semiCode=:z1 or w.semiName=:z2)")
                    .setParameter("x1", section.getCode())
                    .setParameter("x2", section.getName())
                    .setParameter("y1", machineGroup.getCode())
                    .setParameter("y2", machineGroup.getName())
                    .setParameter("z1", semistructured.getSemiCode())
                    .setParameter("z2", semistructured.getSemiName())
                    .getSingleResult();
        }catch (Exception e){
            logger.error(e);
            return null;
        }
    }

    public List<Semistructured> getSemistructureds(Section section, MachineGroup machineGroup) {
        try {
            return entityManager.createQuery("select w from Section p join p.machineGroups t join t.machineSemis w where (p.code=:x1 or p.name=:x2) and (t.code=:y1 or t.name=:y2)")
                    .setParameter("x1", section.getCode())
                    .setParameter("x2", section.getName())
                    .setParameter("y1", machineGroup.getCode())
                    .setParameter("y2", machineGroup.getName())
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public boolean enterSemistructured(Section section, MachineGroup machineGroup, Semistructured semistructured) {
        Semistructured semistructured1 = getSemistructured(section, machineGroup, semistructured);
        if (semistructured1 != null){
            semistructured1.setTotal(semistructured.getTotal() + semistructured1.getTotal());

            ArrayList<SemistructuredInTimes> semistructuredInTimesArrayList = new ArrayList<>();
            for (SemistructuredInTimes semistructuredInTimes : semistructured.getSemistructuredInTimes()) {
                SemistructuredInTimes semistructuredInTimes1 = getSemistructuredInTimes(semistructured, semistructuredInTimes);
                if (semistructuredInTimes1 == null){
                    semistructuredInTimesArrayList.add(semistructuredInTimes);
                }else {
                    semistructuredInTimes1.setTotal(semistructuredInTimes1.getTotal() + semistructuredInTimes.getTotal());
                }

                try {
                    entityManager.persist(semistructuredInTimes1);
                }catch (Exception e){
                    logger.error(e);
                    return false;
                }
            }
            if (semistructuredInTimesArrayList.size() > 0){
                semistructured1.setSemistructuredInTimes(semistructuredInTimesArrayList);
            }
            try {
                entityManager.persist(semistructured1);
            }catch (Exception e){
                entityManager.getTransaction().rollback();
                logger.error(e);
                return false;
            }

            return true;
        }
        return false;
    }

    public boolean useSemistructured(Section section, MachineGroup machineGroup, Semistructured semistructured) throws Exception {
        Semistructured semistructured1 = getSemistructured(section, machineGroup, semistructured);
        if (semistructured1.getTotal() >= semistructured.getTotal()){
            semistructured1.setTotal(semistructured1.getTotal() - semistructured.getTotal());

            ArrayList<SemistructuredOutTimes> semistructuredOutTimesArrayList = new ArrayList<>();
            for (SemistructuredOutTimes semistructuredOutTimes : semistructured.getSemistructuredOutTimes()) {
                SemistructuredOutTimes semistructuredOutTimes1 = getSemistructuredOutTimes(semistructured, semistructuredOutTimes);
                if (semistructuredOutTimes1 == null){
                    semistructuredOutTimesArrayList.add(semistructuredOutTimes);
                }
                semistructuredOutTimes1.setTotal(semistructuredOutTimes1.getTotal() + semistructuredOutTimes.getTotal());
                try {
                    entityManager.persist(semistructuredOutTimes1);
                }catch (Exception e){
                    entityManager.getTransaction().rollback();
                    logger.error(e);
                    return false;
                }
            }
            if (semistructuredOutTimesArrayList.size() > 0){
                semistructured1.setSemistructuredOutTimes(semistructuredOutTimesArrayList);
            }
            try {
                entityManager.persist(semistructured1);
            }catch (Exception e){
                entityManager.getTransaction().rollback();
                logger.error(e);
                return false;
            }

            return true;
        }else {
            return false;
        }
    }

    public boolean delete(Section section, MachineGroup machineGroup, Semistructured semistructured) throws Exception {
        semistructured = getSemistructured(section, machineGroup, semistructured);
        if (semistructured != null){
            entityManager.remove(semistructured);

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

    public SemistructuredOutTimes getSemistructuredOutTimes(Semistructured semistructured, SemistructuredOutTimes semistructuredOutTimes){
        try {
            return (SemistructuredOutTimes) entityManager.createQuery("select t from Semistructured p join p.semistructuredOutTimes t where p.semiCode=:x and t.batchNumber=:y and t.date=:z")
                    .setParameter("x", semistructured.getSemiCode())
                    .setParameter("y", semistructuredOutTimes.getBatchNumber())
                    .setParameter("z", semistructuredOutTimes.getDate(), TemporalType.TIMESTAMP)
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }
}
