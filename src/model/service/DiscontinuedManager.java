package model.service;

import model.entity.Discontinued;
import model.entity.DiscontinuedGroup;
import model.entity.DiscontinuedTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 1/22/17.
 */
@Repository("discontinuedManager")
@Transactional
public class DiscontinuedManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public boolean register(DiscontinuedGroup discontinuedGroup) {
        try {
            entityManager.persist(discontinuedGroup);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean addDiscontinued(DiscontinuedGroup discontinuedGroup) {
        DiscontinuedGroup discontinuedGroup1;
        try {
            discontinuedGroup1 = getDiscontinuedGroup(discontinuedGroup);
        }catch (Exception e){
            discontinuedGroup1 = null;
        }

        if (discontinuedGroup1 != null){
            int temp = discontinuedGroup.getDiscontinueds().size();
            for (Discontinued discontinued : discontinuedGroup.getDiscontinueds()) {
                for (Discontinued discontinued1 : discontinuedGroup1.getDiscontinueds()) {
                    if (discontinued.getName().equals(discontinued1.getName())){
                        for (DiscontinuedTime discontinuedTime : discontinued.getDiscontinuedTimes()) {
                            discontinued1.getDiscontinuedTimes().add(discontinuedTime);
                        }
                        temp--;
                        discontinued.setName("0");
                    }
                }
            }
            if (temp > 0){
                for (Discontinued discontinued : discontinuedGroup.getDiscontinueds()) {
                    if (!discontinued.getName().equals("0")){
                        discontinuedGroup1.getDiscontinueds().add(discontinued);
                    }
                }
            }
            try {
                entityManager.persist(discontinuedGroup1);
            }catch (Exception e){
                return false;
            }

            return true;
        }else {
            return register(discontinuedGroup);
        }
    }

    public boolean addDiscontinuedTime(DiscontinuedGroup discontinuedGroup) {
        DiscontinuedGroup discontinuedGroup1 = getDiscontinuedGroup(discontinuedGroup);
        if (discontinuedGroup1 != null){
            int temp = discontinuedGroup.getDiscontinueds().size();
            for (Discontinued discontinued : discontinuedGroup.getDiscontinueds()) {
                for (Discontinued discontinued1 : discontinuedGroup1.getDiscontinueds()) {
                    if (discontinued1.getName().equals(discontinued.getName())){
                        discontinued1.getDiscontinuedTimes().addAll(discontinued.getDiscontinuedTimes());
                        discontinued.setName("");
                        temp--;
                    }
                }
            }
            if (temp != 0){
                for (Discontinued discontinued : discontinuedGroup.getDiscontinueds()) {
                    if (!discontinued.getName().equals("")){
                        addDiscontinued(discontinuedGroup);
                    }
                }
            }
            try {
                entityManager.persist(discontinuedGroup1);
            }catch (Exception e){
                return false;
            }

            return true;
        }else {
            return register(discontinuedGroup1);
        }
    }

    public boolean updateDiscontinued(DiscontinuedGroup discontinuedGroup, String name) {
        DiscontinuedGroup discontinuedGroup1 = getDiscontinuedGroup(discontinuedGroup);
        if (discontinuedGroup1 != null){
            int temp = discontinuedGroup.getDiscontinueds().size();
            for (Discontinued discontinued : discontinuedGroup.getDiscontinueds()) {
                for (Discontinued discontinued1 : discontinuedGroup1.getDiscontinueds()) {
                    if (discontinued.getName().equals(discontinued1.getName())){
                        discontinued1.setName(name);
                        temp--;
                        discontinued.setName("");
                    }
                }
            }
            if (temp != 0){
                for (Discontinued discontinued : discontinuedGroup.getDiscontinueds()) {
                    if (!discontinued.getName().equals("")){
                        return false;
                    }
                }
            }
            try{
                entityManager.persist(discontinuedGroup1);
            }catch (Exception e){
                return false;
            }

            return true;
        }else {
            return false;
        }
    }

    public boolean updateDiscontiinuedGroup(DiscontinuedGroup discontinuedGroup, String name) {
        DiscontinuedGroup discontinuedGroup1 = getDiscontinuedGroup(discontinuedGroup);
        if (discontinuedGroup1 != null){
            discontinuedGroup1.setName(name);
            try {
                entityManager.persist(discontinuedGroup1);
            }catch (Exception e){
                return false;
            }

            return true;
        }else
            return false;
    }

    public boolean updateDiscontinuedTime(DiscontinuedGroup discontinuedGroup) {
        DiscontinuedGroup discontinuedGroup1 = getDiscontinuedGroup(discontinuedGroup);
        if (discontinuedGroup1 != null){
            for (Discontinued discontinued : discontinuedGroup.getDiscontinueds()) {
                for (Discontinued discontinued1 : discontinuedGroup1.getDiscontinueds()) {
                    if (discontinued.getName().equals(discontinued1.getName())){
                        for (DiscontinuedTime discontinuedTime : discontinued.getDiscontinuedTimes()) {
                            for (DiscontinuedTime time : discontinued1.getDiscontinuedTimes()) {
                                if (discontinuedTime.getDate().equals(time.getDate())){
                                    if (!discontinuedTime.getExplain().trim().equals("")){
                                        time.setExplain(discontinuedTime.getExplain());
                                    }
                                    if (discontinuedTime.getTimeOfDiscontinued() > 0){
                                        time.setTimeOfDiscontinued(discontinuedTime.getTimeOfDiscontinued());
                                    }
                                    if (discontinuedTime.getTimeOfRepair() > 0){
                                        time.setTimeOfRepair(discontinuedTime.getTimeOfRepair());
                                    }
                                    if (!discontinuedTime.getPlace().trim().equals("")){
                                        time.setPlace(discontinuedTime.getPlace());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            try {
                entityManager.persist(discontinuedGroup1);
            }catch (Exception e){
                return false;
            }

            return true;
        }else {
            return false;
        }
    }

    public boolean deleteDiscontinued(DiscontinuedGroup discontinuedGroup) {
        DiscontinuedGroup discontinuedGroup1 = getDiscontinuedGroup(discontinuedGroup);
        if (discontinuedGroup != null){
            for (Discontinued discontinued : discontinuedGroup.getDiscontinueds()) {
                for (Discontinued discontinued1 : discontinuedGroup1.getDiscontinueds()) {
                    if (discontinued1.getName().equals(discontinued.getName())){
                        try{
                            entityManager.remove(discontinued1);
                        }catch (Exception e){
                            return false;
                        }

                        discontinuedGroup.getDiscontinueds().remove(discontinued);
                    }
                }
            }
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteDiscontiinuedGroup(DiscontinuedGroup discontinuedGroup) {
        discontinuedGroup = getDiscontinuedGroup(discontinuedGroup);
        if (discontinuedGroup != null){
            try {
                entityManager.remove(discontinuedGroup);
            }catch (Exception e){
                return false;
            }

            return true;
        }else {
            return false;
        }
    }

    public boolean deleteDiscontinuedTime(DiscontinuedGroup discontinuedGroup){
        DiscontinuedGroup discontinuedGroup1 = getDiscontinuedGroup(discontinuedGroup);
        if (discontinuedGroup1 != null){
            for (Discontinued discontinued : discontinuedGroup.getDiscontinueds()) {
                for (Discontinued discontinued1 : discontinuedGroup1.getDiscontinueds()) {
                    if (discontinued.getName().equals(discontinued1.getName())){
                        for (DiscontinuedTime discontinuedTime : discontinued.getDiscontinuedTimes()) {
                            for (DiscontinuedTime time : discontinued1.getDiscontinuedTimes()) {
                                if (time.getDate().equals(discontinuedTime.getDate())){
                                    try {
                                        entityManager.remove(time);
                                    }catch (Exception e){
                                        return false;
                                    }

                                    discontinued.getDiscontinuedTimes().remove(discontinuedTime);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }else {
            return false;
        }
    }

    public DiscontinuedGroup getDiscontinuedGroup(DiscontinuedGroup discontinuedGroup) {
        try {
            return  (DiscontinuedGroup) entityManager.createQuery("select p from DiscontinuedGroup p where p.name=:x")
                    .setParameter("x", discontinuedGroup.getName())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<DiscontinuedGroup> getDiscontinuedGroups() {
        try {
            return entityManager.createQuery("select p from DiscontinuedGroup p")
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<DiscontinuedTime> getDiscontinuedTimes(DiscontinuedGroup discontinuedGroup, Date start, Date end) {
        Discontinued discontinued = discontinuedGroup.getDiscontinueds().get(0);
        try {
            return entityManager.createQuery("select distinct t from DiscontinuedGroup p join p.discontinueds w join w.discontinuedTimes t where p.name=:x and w.name=:y and t.date between :s1 and :s2")
                    .setParameter("x", discontinuedGroup.getName())
                    .setParameter("y", discontinued.getName())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE).getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public Discontinued getDiscontinued(Discontinued discontinued) {
        try {
            return (Discontinued) entityManager.createQuery("select p from Discontinued p where p.name=:x")
                    .setParameter("x", discontinued.getName())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }
}
