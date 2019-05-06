package model.service;

import model.entity.Accessibility;
import model.entity.EventAccessibility;
import model.entity.Role;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amir on 1/24/17.
 */
@Repository
@Transactional
public class RoleManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    private static Logger logger = Logger.getLogger(RoleManager.class.getName());

    public boolean addAccess(Role role) throws Exception {
        Role role1 = getRole(role);
        if (role1 != null){
            int temp = role.getAccessibilities().size();
            for (Accessibility accessibility : role1.getAccessibilities()) {
                for (Accessibility accessibility1 : role.getAccessibilities()) {
                    if (accessibility.getName().equals(accessibility1.getName())){
                        accessibility1.setName("");
                        temp--;
                    }
                }
            }
            if (temp > 0){
                for (Accessibility accessibility : role.getAccessibilities()) {
                    if (!accessibility.getName().equals("")){
                        role1.getAccessibilities().add(accessibility);
                    }
                }
            }
            entityManager.persist(role1);

            return true;
        }else {
            entityManager.persist(role);

            return true;
        }
    }

    public boolean deleteAccess(Role role) throws Exception {
        Role role1 = getRole(role);
        if (role1 != null){
            int temp = role.getAccessibilities().size();
            for (Accessibility accessibility : role1.getAccessibilities()) {
                for (Accessibility accessibility1 : role.getAccessibilities()) {
                    if (accessibility.getName().equals(accessibility1.getName())){
                        role1.getAccessibilities().remove(accessibility);
                        temp--;
                    }
                }
            }
            if (temp > 0){
                return false;
            }
            entityManager.persist(role1);

            return true;
        }else {
            return false;
        }
    }

    public boolean add(Role role) throws Exception {
        Role role1 = getRole(role);
        if (role1 == null){
            Role role2 = new Role();
            role2.setCode(role.getCode());
            role2.setName(role.getName());
            ArrayList<Accessibility> accessibilityArrayList = new ArrayList<>();
            for (Accessibility accessibility : role.getAccessibilities()) {
                accessibility = (Accessibility) entityManager.createQuery("select p from Accessibility p where p.code=:y")
                        .setParameter("y", accessibility.getCode())
                        .getSingleResult();
                accessibilityArrayList.add(accessibility);
            }
            if (role.getEventAccessibilities() != null){
                ArrayList<EventAccessibility> eventAccessibilities = new ArrayList<>();
                for (EventAccessibility eventAccessibility : role.getEventAccessibilities()) {
                    eventAccessibility = (EventAccessibility) entityManager.createQuery("select p from EventAccessibility p where p.code=:x")
                            .setParameter("x", eventAccessibility.getCode())
                            .getSingleResult();
                    eventAccessibilities.add(eventAccessibility);
                }
                role2.setEventAccessibilities(eventAccessibilities);
            }
            role2.setAccessibilities(accessibilityArrayList);
            entityManager.persist(role2);

            return true;
        }else {
            ArrayList<Accessibility> accessibilityArrayList = new ArrayList<>();
            ArrayList<EventAccessibility> eventAccessibilities = new ArrayList<>();

            for (Accessibility accessibility : role.getAccessibilities()) {
                logger.info("access before : " + accessibility.getCode());
                accessibility = (Accessibility) entityManager.createQuery("select p from Accessibility p where p.code=:y")
                        .setParameter("y", accessibility.getCode())
                        .getSingleResult();
                logger.info("access : " + accessibility.getCode());
                accessibilityArrayList.add(accessibility);
            }
            if (role.getEventAccessibilities() != null){
                for (EventAccessibility eventAccessibility : role.getEventAccessibilities()) {
                    eventAccessibility = (EventAccessibility) entityManager.createQuery("select p from EventAccessibility p where p.code=:x")
                            .setParameter("x", eventAccessibility.getCode())
                            .getSingleResult();
                    logger.info("event : " + eventAccessibility.getCode());
                    eventAccessibilities.add(eventAccessibility);
                }
                role1.setEventAccessibilities(eventAccessibilities);
            }else {
                role1.setEventAccessibilities(null);
            }
            role1.setAccessibilities(accessibilityArrayList);
            entityManager.persist(role1);

            return true;
        }
    }

    public boolean edit(Role role) throws Exception {
        Role role1 = getRole(role);
        if (role1 != null){
            role1.setCode(role.getCode());
            role.setName(role.getName());
            entityManager.persist(role1);

            return true;
        }else {
            return false;
        }
    }

    public boolean delete(Role role) throws Exception {
        Role role1 = getRole(role);
        if (role1 != null){
            entityManager.remove(role1);

            return true;
        }else {
            return false;
        }
    }

    public Role getRole(Role role) {
        try {
            return (Role) entityManager.createQuery("select p from Role p where p.code=:x")
                    .setParameter("x", role.getCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }

    }

    public List<Role> getRoles(){
        try {
            return entityManager.createQuery("select p from Role p").getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
