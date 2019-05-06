package model.service;

import model.entity.Product;
import model.entity.ProductDegree;
import model.entity.ProductDegreeTime;
import model.entity.ProductOutTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 12/19/16.
 */
@Repository
@Transactional
public class ProductTimeSellManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public boolean enterTime(Product productOut) {
        Product oldPT = getTime(productOut);
        if (oldPT != null){
            try {
                int temp = productOut.getProductOutTimes().size();
                if (oldPT.getProductOutTimes() != null){
                    for (ProductOutTime time : oldPT.getProductOutTimes()) {
                        for (ProductOutTime time1 : productOut.getProductOutTimes()) {
                            if (time.getDate().equals(time1.getDate()) && time.getBatchNumber().equals(time1.getBatchNumber())){
                                time.setTotal(time.getTotal() + time1.getTotal());

                                int tempDegree = time1.getProductDegreeTimes().size();
                                for (ProductDegreeTime productDegreeTime : time.getProductDegreeTimes()) {
                                    for (ProductDegreeTime degreeTime : time1.getProductDegreeTimes()) {
                                        if (productDegreeTime.getDegree() == degreeTime.getDegree()){
                                            productDegreeTime.setTotal(productDegreeTime.getTotal() + degreeTime.getTotal());
                                            tempDegree--;
                                            degreeTime.setDegree(0);
                                        }
                                    }
                                }
                                if (tempDegree != 0){
                                    for (ProductDegreeTime productDegreeTime : time1.getProductDegreeTimes()) {
                                        if (productDegreeTime.getDegree() != 0){
                                            time.getProductDegreeTimes().add(productDegreeTime);
                                        }
                                    }
                                }
                                time1.setDate(new Date(0));
                                temp--;
                            }
                        }
                    }
                    if (temp != 0){
                        for (ProductOutTime time : productOut.getProductOutTimes()) {
                            if (time.getDate() != new Date(0)){
                                oldPT.getProductOutTimes().add(time);
                            }
                        }
                    }
                }else {
                    oldPT.setProductOutTimes(productOut.getProductOutTimes());
                }
                entityManager.persist(oldPT);

            }catch (Exception e){
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    public boolean useTime(Product productOut) {
        Product oldPT = getTime(productOut);
        if (oldPT != null){
            try {
                List<ProductDegree> productDegrees = oldPT.getProductDegrees();
                int temp = productOut.getProductDegrees().size();
                if(!productDegrees.isEmpty()){
                    for (ProductDegree productDegree : productDegrees) {
                        for (ProductDegree degree : productOut.getProductDegrees()) {
                            if(productDegree.getDegree() == degree.getDegree()){
                                if(productDegree.getTotal() >= degree.getTotal()){
                                    productDegree.setTotal(productDegree.getTotal() - degree.getTotal());
                                    oldPT.setTotal(oldPT.getTotal() - degree.getTotal());
                                    temp--;
                                }else {
                                    return false;
                                }
                            }
                        }
                    }
                    if (temp != 0){
                        return false;
                    }
                }else{
                    return false;
                }

                temp = productOut.getProductOutTimes().size();
                for (ProductOutTime time : oldPT.getProductOutTimes()) {
                    for (ProductOutTime time1 : productOut.getProductOutTimes()) {
                        if (time.getDate().equals(time1.getDate()) && time.getBatchNumber().equals(time1.getBatchNumber())){
                            int tempDegree = time1.getProductDegreeTimes().size();
                            for (ProductDegreeTime productDegreeTime : time.getProductDegreeTimes()) {
                                for (ProductDegreeTime degreeTime : time1.getProductDegreeTimes()) {
                                    if (productDegreeTime.getDegree() == degreeTime.getDegree()){
                                        if (productDegreeTime.getTotal() >= degreeTime.getTotal()){
                                            productDegreeTime.setTotal(productDegreeTime.getTotal() - degreeTime.getTotal());
                                            time.setTotal(time.getTotal() - degreeTime.getTotal());
                                            degreeTime.setDegree(0);
                                            tempDegree--;
                                        }else{
                                            return false;
                                        }
                                    }
                                }
                            }
                            if (tempDegree != 0){
                                return false;
                            }else {
                                temp--;
                            }
                        }
                    }
                }
                if (temp != 0){
                    return false;
                }
                entityManager.persist(oldPT);

            }catch (Exception e){
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteTime(Product productOut) {
        Product productOut1 = getTime(productOut);
        if (productOut1 == null){
            return false;
        }
        try {
            int temp = productOut.getProductOutTimes().size();
            for (ProductOutTime productOutTime : productOut1.getProductOutTimes()) {
                for (ProductOutTime productOutTime1 : productOut.getProductOutTimes()) {
                    if(productOutTime1.getDate().equals(productOutTime.getDate())){
                        entityManager.remove(productOutTime);
                        temp--;
                    }
                }
            }

            if (temp != 0){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public Product getTime(Product productOut) {
        try {
            return (Product) entityManager.createQuery("select p from Product p where p.productCode=:x")
                    .setParameter("x", productOut.getProductCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<ProductOutTime> getTimesBetween(Product productOut, Date start, Date end) throws Exception {
        try {
            return entityManager.createQuery("select t from Product p join p.productOutTimes t where p.productCode=:x and t.date between :s1 and :s2")
                    .setParameter("x", productOut.getProductCode())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<ProductOutTime> getAllTimes(Date start, Date end) throws Exception {
        try {
            return entityManager.createQuery("select t from Product p join p.productOutTimes t where t.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
