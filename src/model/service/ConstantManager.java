package model.service;

import model.entity.Constant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TemporalType;
import java.util.List;

/**
 * Created by amir on 2/11/17.
 */
@Repository
@Transactional
public class ConstantManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public boolean register(Constant constant) {
        try {
            get(constant);
        }catch (Exception e){
            entityManager.persist(constant);
            return true;
        }
        return false;
    }

    public boolean edit(Constant constant) {
        Constant constant1 = get(constant);
        if (constant1 != null){
            constant1.setShiftHour(constant.getShiftHour());
            constant1.setWorkingDatesNumber(constant.getWorkingDatesNumber());
            try {
                entityManager.persist(constant1);
                return true;
            }catch (Exception e){
                return false;
            }
        }
        return false;
    }

    public boolean delete(Constant constant) {
        Constant constant1 = get(constant);
        if (constant1 != null){
            try {
                entityManager.remove(constant1);
                return true;
            }catch (Exception e){
                return false;
            }
        }
        return false;
    }

    public Constant get(Constant constant) {
        try {
            return (Constant) entityManager.createQuery("select p from Constant p where p.date=:x")
                    .setParameter("x", constant.getDate(), TemporalType.DATE)
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<Constant> getAll(Constant constant) {
        try {
            return entityManager.createQuery("select p from Constant p")
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
