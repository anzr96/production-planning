package model.service;

import model.entity.Role;
import model.entity.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by amir on 1/24/17.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UserManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    private static Logger logger = Logger.getLogger(UserManager.class.getName());

    public boolean add(Role role){
        try {
            entityManager.persist(role);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean edit(User user){
        try {
            User user1 = getUser(user);
            if (user1 != null){
                user1.setPassword(user.getPassword());
                entityManager.persist(user1);

                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            logger.error(e);
            return false;
        }
    }

    public boolean editRole(Role role){
        try {
            User user = getUser(role.getUsers().get(0));
            role = getRole(role);
            role.getUsers().add(user);
            entityManager.persist(role);
            return true;
        }catch (Exception e){
            logger.error(e);
            return false;
        }
    }

    public boolean delete(User user){
        try {
            User user1 = getUser(user);
            if (user1 != null){
                entityManager.remove(user1);

                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            logger.error(e);
            return false;
        }
    }

    public Role getRole(Role role){
        try {
            return (Role) entityManager.createQuery("select p from Role p where p.code=:x")
                    .setParameter("x", role.getCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public User getUser(User user){
        try {
            return (User) entityManager.createQuery("select p from User p where p.username=:x")
                    .setParameter("x", user.getUsername())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<User> getUsers(){
        try {
            return entityManager.createQuery("select p from User p").getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public boolean setKeys(User user){
        try {
            User user1 = getUser(user);
            if (user1 != null){
                user1.setPublicKey(user.getPublicKey());
                entityManager.persist(user1);

                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            logger.error(e);
            return false;
        }
    }

    public boolean setDate(User user){
        User user1 = getUser(user);
        if (user1 != null){
            user1.setDate(user.getDate());
            try {
                entityManager.persist(user1);
            }catch (Exception e){
                logger.error(e);
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    public boolean identify(User user){
        try {
            user = (User) entityManager.createQuery("select p from User p where p.username=:x and p.password=:y")
                    .setParameter("x", user.getUsername())
                    .setParameter("y", user.getPassword())
                    .getSingleResult();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean setConnect(User user){
        User user1 = getUser(user);
        if (user1 != null){
            user1.setConnect(user.isConnect());
            try {
                entityManager.persist(user1);
            }catch (Exception e){
                logger.error(e);
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    public boolean setDateAndKeys(User user){
        try {
            User user1 = getUser(user);
            if (user1 != null){
                user1.setDate(user.getDate());
                user1.setPublicKey(user.getPublicKey());
                entityManager.persist(user1);

                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            logger.error(e);
            return false;
        }
    }

    public Role getUserRole(User user){
        try {
            List<Role> roles =  entityManager.createQuery("select p from Role p").getResultList();
            for (Role role : roles) {
                for (User user1 : role.getUsers()) {
                    if (user1.getUsername().equals(user.getUsername())){
                        return role;
                    }
                }
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
}
