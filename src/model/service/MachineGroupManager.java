package model.service;

import model.entity.MachineGroup;
import model.entity.Section;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by amir on 7/6/17.
 */
@Repository
@Transactional
public class MachineGroupManager {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    private static Logger logger = Logger.getLogger(MachineGroupManager.class.getName());

    public MachineGroup getMachineGroup(MachineGroup machineGroup, Section section){
        try {
            return (MachineGroup) entityManager.createQuery("select t from Section p join p.machineGroups t where (p.code=:x1 or p.name=:x2) and (t.code=:y1 or t.name=:y2)")
                    .setParameter("x1", section.getCode())
                    .setParameter("x2", section.getName())
                    .setParameter("y1", machineGroup.getCode())
                    .setParameter("y2", machineGroup.getName())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<MachineGroup> getMachineGroups(Section section){
        try {
            return entityManager.createQuery("select t from Section p join p.machineGroups t where p.code=:x1 or p.name=:x2")
                    .setParameter("x1", section.getCode())
                    .setParameter("x2", section.getName())
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public boolean edit(MachineGroup machineGroup, Section section){
        MachineGroup machineGroup1 = getMachineGroup(machineGroup, section);
        if (machineGroup1 != null){
            if (machineGroup.getCode() != null){
                machineGroup1.setCode(machineGroup.getCode());
            }
            if (machineGroup.getName() != null){
                machineGroup1.setName(machineGroup.getName());
            }
            try {
                entityManager.persist(machineGroup1);

            }catch (Exception e){
                logger.error("Cant persist", e);
                return false;
            }
        }else
            return false;
        return true;
    }

    public boolean delete(MachineGroup machineGroup, Section section){
        machineGroup = getMachineGroup(machineGroup, section);
        if (machineGroup != null){
            try {
                entityManager.remove(machineGroup);

            }catch (Exception e){
                logger.error("Cant persist", e);
                return false;
            }
        }else
            return false;
        return true;
    }


}
