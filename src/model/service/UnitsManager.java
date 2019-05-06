package model.service;

import model.entity.Units;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by amir on 11/20/16.
 */
@Repository
@Transactional
public class UnitsManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public boolean enter(Units units) throws Exception {
        List<Units> unitses = getUnits();
        for (Units unitse : unitses) {
            if (unitse.getUnitName().equals(units.getUnitName())){
                return true;
            }
        }
        entityManager.persist(units);

        return true;
    }

    public boolean delete(Units units) throws Exception {
        entityManager.remove(units);

        return true;
    }

    public List<Units> getUnits() throws Exception {
        List<Units> unitses = entityManager.createQuery("select p from Units p").getResultList();
        return unitses;
    }

    public Units getUnit(Units units) throws Exception {
        units = (Units) entityManager.createQuery("select p from Units p where p.unitName=:x").setParameter("x", units.getUnitName()).getSingleResult();
        return units;
    }
}
