package model.service;

import model.entity.Machine;
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
public class SectionManager {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    private static Logger logger = Logger.getLogger(SectionManager.class.getName());

    public boolean register(Section section){
        Section section1 = getSection(section);
        if (section1 != null){
            for (MachineGroup machineGroup : section1.getMachineGroups()) {
                for (MachineGroup group : section.getMachineGroups()) {
                    if (machineGroup.getName().equals(group.getName()) || machineGroup.getCode().equals(group.getCode())){
                        group.setCode("NULL");
                        for (Machine machine : machineGroup.getMachines()) {
                            for (Machine machine1 : group.getMachines()) {
                                if (machine.getCode().equals(machine1.getCode())){
                                    machine1.setCode("NULL");
                                    break;
                                }
                            }
                        }
                        for (Machine machine : group.getMachines()) {
                            if (!machine.getCode().equals("NULL")){
                                machineGroup.getMachines().add(machine);
                            }
                        }
                        break;
                    }
                }
            }
            for (MachineGroup machineGroup : section.getMachineGroups()) {
                if (!machineGroup.getCode().equals("NULL")){
                    section1.getMachineGroups().add(machineGroup);
                }
            }
            try {
                entityManager.persist(section1);

            }catch (Exception e){
                logger.error("Cant persist", e);
                return false;
            }
        }else {
            try {
                entityManager.persist(section);

            }catch (Exception e){
                logger.error("Cant persist", e);
                return false;
            }
        }
        return true;
    }

    public boolean editNameCode(Section section){
        Section section1 = getSection(section);
        if (section1 != null){
            if (section.getCode() != null){
                section1.setCode(section.getCode());
            }
            if (section.getName() != null){
                section1.setName(section.getName());
            }
            try {
                entityManager.persist(section1);

            }catch (Exception e){
                logger.error("Cant persist", e);
                return false;
            }
        }else
            return false;
        return true;
    }

    public boolean delete(Section section){
        section = getSection(section);
        if (section != null){
            try {
                entityManager.remove(section);

            }catch (Exception e){
                logger.error("Cant remove", e);
                return false;
            }
        }else
            return false;
        return true;
    }

    public Section getSection(Section section){
        try {
            if (section.getCode() != null){
                return (Section) entityManager.createQuery("select p from Section p where p.code=:x")
                        .setParameter("x", section.getCode())
                        .getSingleResult();
            }else if (section.getName() != null){
                return (Section) entityManager.createQuery("select p from Section p where p.name=:y")
                        .setParameter("y", section.getName())
                        .getSingleResult();
            }else {
                return null;
            }

        }catch (Exception e){
            return null;
        }
    }

    public List<Section> getSections(){
        try {
            return entityManager.createQuery("select p from Section p").getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
