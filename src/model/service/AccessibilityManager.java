package model.service;

import model.entity.Accessibility;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by amir on 1/24/17.
 */
@Repository
@Transactional
public class AccessibilityManager {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    private static Logger logger = Logger.getLogger(AccessibilityManager.class.getName());

    public boolean add(Accessibility accessibility) throws Exception {
        Accessibility accessibility1 = getAccessibility(accessibility);
        if (accessibility1 == null){
            entityManager.persist(accessibility);

            return true;
        }else {
            return false;
        }
    }

    public boolean edit(Accessibility accessibility) throws Exception {
        Accessibility accessibility1 = getAccessibility(accessibility);
        if (accessibility1 != null){
            accessibility1.setCode(accessibility.getCode());
            accessibility1.setName(accessibility.getName());
            accessibility1.setUrl(accessibility.getUrl());
            entityManager.persist(accessibility1);


            return true;
        }else {
            return false;
        }
    }

    public boolean delete(Accessibility accessibility) throws Exception {
        Accessibility accessibility1 = getAccessibility(accessibility);
        if (accessibility1 != null){
            entityManager.remove(accessibility1);

            return true;
        }else {
            return false;
        }
    }

    public Accessibility getAccessibility(Accessibility accessibility) throws Exception {
        try {
            return (Accessibility) entityManager.createQuery("select p from Accessibility p where p.name=:x or p.code=:y")
                    .setParameter("x", accessibility.getName())
                    .setParameter("y", accessibility.getCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<Accessibility> getAccessibilitys() throws Exception {
        return entityManager.createQuery("select p from Accessibility p").getResultList();
    }
}
