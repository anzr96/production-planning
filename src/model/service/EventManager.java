package model.service;

import model.entity.Event;
import model.entity.EventProperties;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 1/24/17.
 */
@Repository
@Transactional
public class EventManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(EventManager.class.getName());

    public boolean add(Event event){
        try {
            entityManager.persist(event);
            return true;
        }catch (Exception e){
            logger.error("cant persist", e);
            return false;
        }
    }

    public boolean delete(Event event){
        event = getEvent(event);
        if (event == null)
            return false;
        try {
            entityManager.remove(event);
            return true;
        }catch (Exception e){
            logger.error("cant persist", e);
            return false;
        }
    }

    public boolean edit(Event oldEvent, Event newEvent){
        oldEvent = getEvent(oldEvent);
        if (oldEvent == null){
            return false;
        }
        oldEvent.setEditedDate(newEvent.getDate());
        oldEvent.setEdited(true);
        oldEvent.setDescription(newEvent.getDescription());
        oldEvent.setKind(newEvent.getKind());
        oldEvent.setTitle(newEvent.getTitle());
        try {
            entityManager.persist(oldEvent);
            return true;
        }catch (Exception e){
            logger.error("cant persist", e);
            return false;
        }
    }

    public boolean deleteUser(Event event){
        EventProperties eventProperties = getEventUser(event);
        if (eventProperties == null){
            return false;
        }
        eventProperties.setDelete(true);
        try {
            entityManager.persist(eventProperties);
            return true;
        }catch (Exception e){
            logger.error("cant persist", e);
            return false;
        }
    }

    public boolean seenUser(Event event){
        EventProperties eventProperties = getEventUser(event);
        if (eventProperties == null){
            return false;
        }
        eventProperties.setSeen(true);
        try {
            entityManager.persist(eventProperties);
            return true;
        }catch (Exception e){
            logger.error("cant persist", e);
            return false;
        }
    }

    public boolean favoriteUser(Event event){
        EventProperties eventProperties = getEventUser(event);
        if (eventProperties == null){
            return false;
        }
        eventProperties.setFavorite(true);
        try {
            entityManager.persist(eventProperties);
            return true;
        }catch (Exception e){
            logger.error("cant persist", e);
            return false;
        }
    }

    public boolean addProperties(Event event){
        if (event.getPropertiesList() != null && event.getPropertiesList().size() > 0){
            try {
                entityManager.persist(event);
                return true;
            }catch (Exception e){
                logger.error("cant persist data", e);
                return false;
            }
        }else {
            return false;
        }
    }

    public EventProperties getEventUser(Event event){
        try {
            return (EventProperties) entityManager.createQuery("select t from Event p join p.propertiesList t where p.id=:x and t.username=:y")
                    .setParameter("x", event.getId())
                    .setParameter("y", event.getPropertiesList().get(0).getUsername())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public Event getEvent(Event event){
        try {
            return (Event) entityManager.createQuery("select p from Event p where p.id=:x")
                    .setParameter("x", event.getId())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<Event> getEventBetween(Date start, Date end){
        try {
            return entityManager.createQuery("select p from Event p where p.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.TIMESTAMP)
                    .setParameter("s2", end, TemporalType.TIMESTAMP)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Event> getEventKind(Event event) {
        try {
            return entityManager.createQuery("select p from Event p where p.kind=:x")
                    .setParameter("x", event.getKind())
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Event> getEventAccess(Event event) {
        try {
            return entityManager.createQuery("select p from Event p where p.access=:x")
                    .setParameter("x", event.getAccess())
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Event> getEventAccessBetween(Event event, Date start, Date end) {
        try {
            return entityManager.createQuery("select p from Event p where p.access=:x and p.date between :s1 and :s2")
                    .setParameter("x", event.getAccess())
                    .setParameter("s1", start, TemporalType.TIMESTAMP)
                    .setParameter("s2", start, TemporalType.TIMESTAMP)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Event> getEventKindBetween(Event event, Date start, Date end) {
        try {
            return entityManager.createQuery("select p from Event p where p.kind=:x and p.date between :s1 and :s2")
                    .setParameter("x", event.getKind())
                    .setParameter("s1", start, TemporalType.TIMESTAMP)
                    .setParameter("s2", start, TemporalType.TIMESTAMP)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Event> getEventKindAccessBetween(Event event, Date start, Date end) {
        try {
            return entityManager.createQuery("select p from Event p where p.access=:x and p.kind=:y and p.date between :s1 and :s2")
                    .setParameter("x", event.getAccess())
                    .setParameter("y", event.getKind())
                    .setParameter("s1", start, TemporalType.TIMESTAMP)
                    .setParameter("s2", start, TemporalType.TIMESTAMP)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Event> getLast(List<String> accesses, List<String> kinds) {
        try {
            return entityManager.createQuery("select p from Event p where p.access in :x and p.kind in :y order by p.date desc ")
                    .setParameter("x", accesses)
                    .setParameter("y", kinds)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public Event getID(long id) {
        try {
            return (Event) entityManager.createQuery("select p from Event p where p.id=:x")
                    .setParameter("x", id)
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }
}
