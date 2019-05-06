package model.service;

import model.entity.Product;
import model.entity.ProductDegree;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by amir on 10/28/2016.
 */
@Repository
@Transactional
public class ProductManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public boolean registerProduct(Product product) throws Exception {
        entityManager.persist(product);

        return true;
    }

    public boolean deleteProduct(Product product) throws Exception {
        Product product1 = getProduct(product);
        entityManager.remove(product1);

        return true;
    }

    public Product getProduct(Product product){
        try {
            if (product.getProductCode() != null){
                return (Product) entityManager.createQuery("select p from Product p where p.productCode=:x").setParameter("x", product.getProductCode()).getSingleResult();
            }else {
                return (Product) entityManager.createQuery("select p from Product p where (p.size1=:y1 and p.design=:y2 and p.pr=:y3)")
                        .setParameter("y1", product.getSize())
                        .setParameter("y2", product.getDesign())
                        .setParameter("y3", product.getPr())
                        .getSingleResult();
            }
        }catch (Exception e){
            return null;
        }
    }

    public List<Product> getProducts(){
        try {
            return entityManager.createQuery("select p from Product p")
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
