package services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;

public class CategoryService implements Repository<entities.Category, Long> {
    private final EntityManager em;

    // Constructor to initialize the EntityManager
    public CategoryService(EntityManager em) {
        this.em = em;
    }

    // Retrieve a category using its ID
    @Override
    public Optional<entities.Category> get(Long id) {
        return Optional.ofNullable(em.find(entities.Category.class, id));
    }

    // Retrieve all categories
    @Override
    public List<entities.Category> getAll() {
        Query query = em.createNamedQuery("Category.getAll");
        return query.getResultList();
    }

    // Save a category (create or update)
    @Override
    public Optional<entities.Category> save(entities.Category cat) {
        if (cat.getId() == null) {
            em.persist(cat);
        } else {
            cat = em.merge(cat);
        }
        return Optional.of(cat);
    }

    // Delete a category
    @Override
    public void delete(entities.Category cat) {
        // Use find to ensure the category is managed
        entities.Category managedCategory = em.find(entities.Category.class, cat.getId());
        if (managedCategory != null) {
            em.remove(managedCategory);
        }
    }

    // Method for adding a new category
    public Optional<entities.Category> add(entities.Category cat) {
        if (cat == null) {
            return Optional.empty();
        }
        em.persist(cat);
        return Optional.of(cat);
    }

    // Method for updating an existing category
    public Optional<entities.Category> update(entities.Category cat) {
        if (cat == null || cat.getId() == null) {
            return Optional.empty();
        }
        return Optional.of(em.merge(cat));
    }
}
