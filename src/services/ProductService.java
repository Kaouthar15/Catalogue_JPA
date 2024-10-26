package services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

public class ProductService implements Repository<entities.Product, Long> {
    private final EntityManager em;

//    initialisation du constructeur

    public ProductService(EntityManager em) {
        this.em = em;
    }

//    méthode pour récupérer la catégorie en utilisant ID
    @Override
    public Optional<entities.Product> get(Long id) {
        return Optional.of(em.find(entities.Product.class, id));
    }

//    méthode pour récupérer toutes les enregistrements
    @Override
    public List<entities.Product> getAll() {
        Query query = em.createNamedQuery("Produit.getAll");
        return query.getResultList();
    }

//    méthode pour enregistrer un produit
    @Override
    public Optional<entities.Product> save(entities.Product product) {
        if (product.getId() == null) {
            em.persist(product);
        } else {
            product = em.merge(product);
        }
        return Optional.of(product);
    }

//    méthode pour supprimer un produit
    @Override
    public void delete(entities.Product product) {
        if (em.contains(product)) {
            em.remove(product);
        }
    }

//  méthode pour faire la mise à jour
    public Optional<entities.Product> update(entities.Product product) {
        return Optional.of(em.merge(product));
    }


//  méthode pour ajouter un produit
    public Optional<entities.Product> add(entities.Product product) {
        if (product == null) {
            return Optional.empty();
        }
        em.persist(product);
        return Optional.of(product);
    }
}