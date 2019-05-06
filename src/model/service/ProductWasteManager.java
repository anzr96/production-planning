package model.service;

import model.entity.Product;
import model.entity.ProductWasteTime;
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
public class ProductWasteManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public boolean addTime(Product productWaste){
        Product productWaste1 = get(productWaste);
        if (productWaste1 != null){
            try {
                boolean check = true;
                ArrayList<ProductWasteTime> productWasteTimes = new ArrayList<>();
                for (ProductWasteTime productWasteTime : productWaste.getProductWasteTimes()) {
                    try {
                        ProductWasteTime productWasteTime1 = (ProductWasteTime) entityManager.createQuery("select t from Product p join p.productWasteTimes t where p.productCode=:x and t.batchNumber=:y and t.date=:z and t.shift=:w and t.reason=:q")
                                .setParameter("x", productWaste.getProductCode())
                                .setParameter("y", productWasteTime.getBatchNumber())
                                .setParameter("z", productWasteTime.getDate())
                                .setParameter("w", productWasteTime.getShift())
                                .setParameter("q", productWasteTime.getReason())
                                .getSingleResult();
                        productWasteTime1.setTotal(productWasteTime1.getTotal() + productWasteTime.getTotal());
                        check = false;
                    }catch (Exception e){
                        productWasteTimes.add(productWasteTime);
                    }
                }
                if (check){
                    productWaste1.setProductWasteTimes(productWasteTimes);
                    entityManager.persist(productWaste1);

                    return true;
                }
            }catch (Exception e){
                return false;
            }
        }
        return false;
    }

    public boolean editTime(Product productWaste){
        Product productWaste1 = get(productWaste);
        if (productWaste1 != null){
            try {
                int temp = productWaste.getProductOutTimes().size();
                for (ProductWasteTime productWasteTime : productWaste.getProductWasteTimes()) {
                    for (ProductWasteTime wasteTime : productWaste1.getProductWasteTimes()) {
                        if (productWasteTime.getBatchNumber().equals(wasteTime.getBatchNumber()) && productWasteTime.getDate().equals(wasteTime.getDate())){
                            wasteTime.setTotal(productWasteTime.getTotal());
                            productWasteTime.setBatchNumber("0");
                            temp--;
                        }
                    }
                }
                if (temp == 0){
                    entityManager.persist(productWaste1);

                    return true;
                }
            }catch (Exception e){
                return false;
            }
        }
        return false;
    }

    public boolean deleteTime(Product productWaste){
        Product productWaste1 = get(productWaste);
        if (productWaste1 != null){
            try{
                int temp = productWaste.getProductWasteTimes().size();
                ArrayList<ProductWasteTime> productWasteTimes = new ArrayList<>();
                for (ProductWasteTime productWasteTime : productWaste.getProductWasteTimes()) {
                    for (ProductWasteTime wasteTime : productWaste1.getProductWasteTimes()) {
                        if (productWasteTime.getBatchNumber().equals(wasteTime.getBatchNumber()) && productWasteTime.getDate().equals(wasteTime.getDate())){
                            productWasteTimes.add(wasteTime);
                            temp--;
                        }
                    }
                }
                if (temp == 0){
                    for (ProductWasteTime productWasteTime : productWasteTimes) {
                        entityManager.remove(productWasteTime);
                    }

                    return true;
                }
            }catch (Exception e){
                return false;
            }
        }
        return false;
    }

    public Product get(Product productWaste){
        try {
            return (Product) entityManager.createQuery("select p from Product p where p.code=:x")
                    .setParameter("x", productWaste.getProductCode())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<ProductWasteTime> getProductWasteTime(Product productWaste, Date start, Date end){
        try {
            return entityManager.createQuery("select t from Product p join p.productWasteTimes t where p.productCode=:x and t.date between :s1 and :s2")
                    .setParameter("x", productWaste.getProductCode())
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Product> getTime(Date start, Date end){
        try {
            return entityManager.createQuery("select p from Product p join p.productWasteTimes t where t.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<ProductWasteTime> getAllTimes(Date start, Date end){
        try {
            return entityManager.createQuery("select t from Product p join p.productWasteTimes t where t.date between :s1 and :s2")
                    .setParameter("s1", start, TemporalType.DATE)
                    .setParameter("s2", end, TemporalType.DATE)
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
