package model.service;

import model.entity.Budget;
import model.entity.BudgetMonth;
import model.entity.BudgetProduct;
import model.entity.WorkingCalendar;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 1/30/17.
 */
@Repository
@Transactional
public class BudgetManager {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(BudgetManager.class.getName());

    public boolean register(WorkingCalendar workingCalendar) {
        try {
            WorkingCalendar workingCalendar1 = (WorkingCalendar) entityManager.createQuery("select p from WorkingCalendar p where p.yearValue=:x")
                    .setParameter("x", workingCalendar.getYearValue())
                    .getSingleResult();
            if (workingCalendar1.getBudget() != null){
                workingCalendar1.getBudget().setPrice(workingCalendar.getBudget().getPrice());
                workingCalendar1.getBudget().setWeight(workingCalendar.getBudget().getWeight());
                for (BudgetProduct budgetProduct : workingCalendar1.getBudget().getBudgetProducts()) {
                    for (BudgetProduct product : workingCalendar.getBudget().getBudgetProducts()) {
                        if (budgetProduct.getCode().equals(product.getCode())){
                            budgetProduct.setPrice(product.getPrice());
                            budgetProduct.setRemain(product.getTotal() - (budgetProduct.getTotal() - budgetProduct.getRemain()));
                            budgetProduct.setTotal(product.getTotal());
                            for (BudgetMonth budgetMonth : budgetProduct.getBudgetMonths()) {
                                for (BudgetMonth month : product.getBudgetMonths()) {
                                    if (budgetMonth.getDate().equals(month.getDate())){
                                        budgetMonth.setRemainTotal(month.getTotal() - (budgetMonth.getTotal() - budgetMonth.getRemainTotal()));
                                        budgetMonth.setRemainWeight(month.getWeight() - (budgetMonth.getWeight() - budgetMonth.getRemainWeight()));
                                        budgetMonth.setTotal(month.getTotal());
                                        budgetMonth.setWeight(month.getWeight());
                                        budgetMonth.setPrice(month.getPrice());
                                        month.setDate(null);
                                    }
                                }
                            }
                            for (BudgetMonth budgetMonth : product.getBudgetMonths()) {
                                if (budgetMonth.getDate() != null){
                                    budgetProduct.getBudgetMonths().add(budgetMonth);
                                }
                            }
                            product.setCode(null);
                        }
                    }
                }
                for (BudgetProduct budgetProduct : workingCalendar.getBudget().getBudgetProducts()) {
                    if (budgetProduct.getCode() != null){
                        workingCalendar1.getBudget().getBudgetProducts().add(budgetProduct);
                    }
                }
            }else {
                workingCalendar1.setBudget(workingCalendar.getBudget());
            }

            try {
                entityManager.persist(workingCalendar1);
                return true;
            }catch (Exception e){
                logger.error(e);
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public Budget get(WorkingCalendar workingCalendar) {
        try{
            return (Budget) entityManager.createQuery("select t from WorkingCalendar p join p.budget t where p.yearValue=:x")
                    .setParameter("x", workingCalendar.getYearValue())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<Budget> getAll() {
        try {
            return entityManager.createQuery("select p from Budget p")
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public boolean addProduct(WorkingCalendar workingCalendar, Budget budget) throws Exception {
        Budget budget1 = get(workingCalendar);
        if (budget1 != null){
            int temp = budget.getBudgetProducts().size();
            if (budget1.getBudgetProducts() != null){
                for (BudgetProduct budgetProduct : budget.getBudgetProducts()) {
                    for (BudgetProduct product : budget1.getBudgetProducts()) {
                        if (budgetProduct.getCode().equals(product.getCode())){
                            for (BudgetMonth budgetMonth : budgetProduct.getBudgetMonths()) {
                                for (BudgetMonth month : product.getBudgetMonths()) {
                                    if (budgetMonth.getDate().equals(month.getDate())){
                                        month.setRemainTotal(month.getRemainTotal() + budgetMonth.getRemainTotal());
                                        month.setRemainWeight(month.getRemainWeight() + budgetMonth.getRemainWeight());
                                        product.setRemain(product.getRemain() + budgetProduct.getRemain());
                                        budgetProduct.setCode("0");
                                        temp--;
                                    }
                                }
                            }
                        }
                    }
                }
                if (temp > 0){
                    for (BudgetProduct budgetProduct : budget.getBudgetProducts()) {
                        if (!budgetProduct.getCode().equals("0")){
                            budget1.getBudgetProducts().add(budgetProduct);
                        }
                    }
                }
            }else {
                budget1.setBudgetProducts(budget.getBudgetProducts());
            }
            try{
                entityManager.persist(budget1);

                return true;
            }catch (Exception e){
                return false;
            }
        }
        return false;
    }

    public boolean deleteProduct(WorkingCalendar workingCalendar, Budget budget) throws Exception {
        Budget budget1 = get(workingCalendar);
        if (budget1 != null){
            ArrayList<BudgetProduct> budgetProducts = new ArrayList<>();
            int temp = budget.getBudgetProducts().size();
            for (BudgetProduct budgetProduct : budget.getBudgetProducts()) {
                for (BudgetProduct product : budget1.getBudgetProducts()) {
                    if (budgetProduct.getCode().equals(product.getCode())){
                        budgetProducts.add(product);
                        temp--;
                    }
                }
            }
            if (temp > 0){
                return false;
            }else {
                for (BudgetProduct budgetProduct : budgetProducts) {
                    entityManager.remove(budgetProduct);

                }
                return true;
            }
        }
        return false;
    }
}
