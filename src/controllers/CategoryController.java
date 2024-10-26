package controllers;

import entities.Category;
import services.CategoryService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        this.categoryService = new CategoryService(em);
    }

    public void addCategory(String nom, String description) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }

        Category category = new Category();
        category.setNom(nom);
        category.setDescription(description);

        categoryService.add(category);
    }


    public void deleteCategory(Long id) {
        Category category = categoryService.get(id).orElseThrow(() -> new IllegalArgumentException("Category not found."));
        categoryService.delete(category);
    }

    public List<Category> loadCategories() {
        return categoryService.getAll();
    }
}
