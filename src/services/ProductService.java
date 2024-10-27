package services;

import entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final EntityManager em;

    public ProductService(EntityManager em) {
        this.em = em;
    }

    public Optional<Product> get(Long id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }

    public List<Product> getAll() {
        Query query = em.createNamedQuery("Produit.getAll");
        return query.getResultList();
    }

    public Optional<Product> save(Product product) {
        if (product.getId() == null) {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
        } else {
            em.getTransaction().begin();
            em.merge(product);
            em.getTransaction().commit();
        }
        return Optional.of(product);
    }

    public void delete(Product product) {
        em.getTransaction().begin();
        if (em.contains(product)) {
            em.remove(product);
        } else {
            em.remove(em.merge(product));
        }
        em.getTransaction().commit();
    }
}
