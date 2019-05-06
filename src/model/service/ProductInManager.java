package model.service;

import model.entity.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 11/25/16.
 */
@Repository
@Transactional
public class ProductInManager {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public boolean enterTime(Product productIn){
        Product oldPT = getTime(productIn);
        if (oldPT != null){
            try {
                List<ProductDegree> productDegrees = oldPT.getProductDegrees();
                int tempProductDegrees = productIn.getProductDegrees().size();
                if (productDegrees != null && !productDegrees.isEmpty()){
                    for(int i = 0; i < productDegrees.size(); i++){
                        for (ProductDegree productDegree : productIn.getProductDegrees()) {
                            if(productDegrees.get(i).getDegree() == productDegree.getDegree()){
                                productDegrees.get(i).setTotal(productDegrees.get(i).getTotal() + productDegree.getTotal());
                                productDegree.setTotal(0);
                                tempProductDegrees--;
                            }
                        }
                    }
                    if (tempProductDegrees != 0){
                        for (ProductDegree productDegree : productIn.getProductDegrees()) {
                            if (productDegree.getTotal() != 0){
                                productDegrees.add(productDegree);
                            }
                        }
                    }
                    oldPT.setProductDegrees(productDegrees);
                }else {
                    oldPT.setProductDegrees(productIn.getProductDegrees());
                }
                oldPT.setTotal(oldPT.getTotal() + productIn.getTotal());

                int tempProductIn = productIn.getProductInTimes().size();
                if (oldPT.getProductInTimes() != null){
                    for (ProductInTime productInTime : oldPT.getProductInTimes()) {
                        for (ProductInTime productInTime1 : productIn.getProductInTimes()) {
                            if (productInTime.getDate().equals(productInTime1.getDate()) && productInTime.getBatchNumber().equals(productInTime1.getBatchNumber())){
                                productInTime.setTotal(productInTime.getTotal() + productInTime1.getTotal());
                                productInTime.setRemain(productInTime.getRemain() + productInTime1.getTotal());
                                int tempDegree = productInTime1.getProductDegreeTimes().size();
                                for (ProductDegreeTime productDegreeTime : productInTime.getProductDegreeTimes()) {
                                    for (ProductDegreeTime degreeTime : productInTime1.getProductDegreeTimes()) {
                                        if (productDegreeTime.getDegree() == degreeTime.getDegree()){
                                            productDegreeTime.setTotal(productDegreeTime.getTotal() + degreeTime.getTotal());
                                            productDegreeTime.setRemain(productDegreeTime.getRemain() + degreeTime.getTotal());
                                            tempDegree--;
                                            degreeTime.setDegree(0);
                                        }
                                    }
                                }
                                if (tempDegree != 0){
                                    for (ProductDegreeTime productDegreeTime : productInTime1.getProductDegreeTimes()) {
                                        if (productDegreeTime.getDegree() != 0){
                                            productDegreeTime.setRemain(productDegreeTime.getTotal());
                                            productInTime.getProductDegreeTimes().add(productDegreeTime);
                                        }
                                    }
                                }
                                productInTime1.setDate(new Date(0));
                                tempProductIn--;
                            }
                        }
                    }
                    if (tempProductIn != 0){
                        for (ProductInTime productInTime : productIn.getProductInTimes()) {
                            if (productInTime.getDate() != new Date(0)){
                                for (ProductDegreeTime productDegreeTime : productInTime.getProductDegreeTimes()) {
                                    productDegreeTime.setRemain(productDegreeTime.getTotal());
                                }
                                productInTime.setRemain(productInTime.getTotal());
                                oldPT.getProductInTimes().add(productInTime);
                            }
                        }
                    }
                }else {
                    for (ProductInTime productInTime : productIn.getProductInTimes()) {
                        for (ProductDegreeTime productDegreeTime : productInTime.getProductDegreeTimes()) {
                            productDegreeTime.setRemain(productDegreeTime.getTotal());
                        }
                        productInTime.setRemain(productInTime.getTotal());
                    }
                    oldPT.setProductInTimes(productIn.getProductInTimes());
                }
                entityManager.persist(oldPT);

                return true;
            }catch (Exception e){
                return false;
            }

        }else {
            return false;
        }
    }

    public boolean useTime(Product productIn){
        Product oldPT = getTime(productIn);
        if (oldPT != null){
            try {
                int temp = productIn.getProductOutTimes().size();
                for (ProductInTime productInTime : oldPT.getProductInTimes()) {
                    for (ProductOutTime productOutTime : productIn.getProductOutTimes()) {
                        if (productInTime.getDate().equals(productOutTime.getDate())){
                            int tempDegree = productOutTime.getProductDegreeTimes().size();
                            for (ProductDegreeTime productDegreeTime : productInTime.getProductDegreeTimes()) {
                                for (ProductDegreeTime degreeTime : productOutTime.getProductDegreeTimes()) {
                                    if (productDegreeTime.getDegree() == degreeTime.getDegree()){
                                        if (productDegreeTime.getRemain() >= degreeTime.getTotal()){
                                            productDegreeTime.setRemain(productDegreeTime.getRemain() - degreeTime.getTotal());
                                            productInTime.setRemain(productInTime.getRemain() - degreeTime.getTotal());
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

                return true;
            }catch (Exception e){
                return false;
            }
        }else {
            return false;
        }
    }

    public boolean deleteTime(Product productIn){
        Product productIn1 = getTime(productIn);
        int temp = productIn.getProductInTimes().size();
        for (ProductInTime productInTime : productIn1.getProductInTimes()) {
            for (ProductInTime productInTime1 : productIn.getProductInTimes()) {
                if(productInTime1.getDate().equals(productInTime.getDate()) && productInTime1.getBatchNumber().equals(productInTime.getBatchNumber())){
                    entityManager.remove(productInTime);
                    temp--;
                }
            }
        }

        if (temp != 0) {
            return false;
        }
        return true;
    }

    public Product getTime(Product productIn){
        try {
            return (Product) entityManager.createQuery("select p from Product p where p.productCode=:x")
                    .setParameter("x", productIn.getProductCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<ProductInTime> getTimeRemain(Product productIn){
        try {
            return entityManager.createQuery("select t from Product p join p.productInTimes t where p.productCode=:x and not t.remain=0")
                    .setParameter("x", productIn.getProductCode())
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<ProductInTime> getTimesBetween(Product productIn, Date start, Date end){
        try {
            return entityManager.createQuery("select distinct t from Product p join p.productInTimes t where p.productCode=:x and t.date between :s1 and :s2")
                    .setParameter("x", productIn.getProductCode())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Product> getBatchNumberTimes(String batchNumber, Date start, Date end){
        try {
            return entityManager.createQuery("select p from Product p join p.productInTimes t where t.batchNumber=:x and t.date between :s1 and :s2")
                    .setParameter("x", batchNumber)
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public ProductInTime getBatchNumber(Product productIn){
        try {
            Date end = new Date(productIn.getProductInTimes().get(0).getDate().getTime() + 24*60*60*1000);
            return (ProductInTime) entityManager.createQuery("select t from Product p join p.productInTimes t where p.productCode=:x and t.batchNumber=:y and t.date between :s1 and :s2")
                    .setParameter("s1", productIn.getProductInTimes().get(0).getDate())
                    .setParameter("s2", end)
                    .setParameter("x", productIn.getProductCode())
                    .setParameter("y", productIn.getProductInTimes().get(0).getBatchNumber())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<ProductInTime> getAllTimes(Date start, Date end){
        try {
            return entityManager.createQuery("select t from Product p join p.productInTimes t where t.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
