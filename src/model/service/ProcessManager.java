package model.service;

import controller.ReportController;
import model.entity.Process;
import model.entity.ProcessProduct;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Created by amir on 7/3/17.
 */
@Repository
@Transactional
public class ProcessManager {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(ProcessManager.class.getName());

    public boolean addProcess(ProcessProduct processProduct){
        ProcessProduct processProduct1 = getProcess(processProduct);
        if (processProduct1 == null){
            try {
                entityManager.persist(processProduct);

            }catch (Exception e){
                logger.error("Cant persist", e);
                return false;
            }
            return true;
        }else {
            for (Process process : processProduct.getProcesses()) {
                boolean check = false;
                for (Process process1 : processProduct1.getProcesses()) {
                    if (process1.getSectionCode().equals(process.getSectionCode()) && process1.getGroupCode().equals(process.getGroupCode())){
                        check = true;
                        break;
                    }
                }
                if (!check){
                    processProduct1.getProcesses().add(process);
                }
            }
            try {
                entityManager.persist(processProduct1);

            }catch (Exception e){
                logger.error("Cant persist", e);
                return false;
            }
            return true;
        }
    }

    public ProcessProduct getProcess(ProcessProduct processProduct){
        try {
            return  (ProcessProduct) entityManager.createQuery("select p from ProcessProduct p where p.code=:x or p.name=:y")
                    .setParameter("x", processProduct.getCode())
                    .setParameter("y", processProduct.getName())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public boolean deleteProcess(ProcessProduct processProduct){
        processProduct = getProcess(processProduct);
        if (processProduct != null){
            try {
                entityManager.remove(processProduct);

            }catch (Exception e){
                logger.error("Cant remove", e);
                return false;
            }
        }else {
            return false;
        }
        return true;
    }

    public boolean editProcess(ProcessProduct processProduct){
        ProcessProduct processProduct1 = processProduct;
        if (deleteProcess(processProduct1)){
            try {
                entityManager.persist(processProduct);

            }catch (Exception e){
                logger.error("Cant persist", e);
                return false;
            }
        }else {
            return false;
        }
        return true;
    }
}
