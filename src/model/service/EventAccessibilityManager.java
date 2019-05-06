package model.service;

import model.entity.EventAccessibility;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by amir on 2/6/17.
 */
@Repository
@Transactional
public class EventAccessibilityManager {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    private Logger logger = Logger.getLogger(EventAccessibilityManager.class.getName());

    public boolean add(EventAccessibility eventAccessibility) throws Exception {
        EventAccessibility eventAccessibility1 = get(eventAccessibility);
        if (eventAccessibility1 == null){
            entityManager.persist(eventAccessibility);

            return true;
        }
        return false;
    }

    public boolean delete(EventAccessibility eventAccessibility) throws Exception {
        EventAccessibility eventAccessibility1 = get(eventAccessibility);
        if (eventAccessibility1 != null){
            entityManager.remove(eventAccessibility1);

            return true;
        }
        return false;
    }

    public EventAccessibility get(EventAccessibility eventAccessibility) throws Exception {
        try {
            return (EventAccessibility) entityManager.createQuery("select p from EventAccessibility p where p.code=:x")
                    .setParameter("x", eventAccessibility.getCode())
                    .getSingleResult();
        }catch (Exception e){
            logger.error("not found", e);
        }
        return null;
    }

    public List<EventAccessibility> getAll() throws Exception {
        return entityManager.createQuery("select p from EventAccessibility p")
                .getResultList();
    }
}
