package controllers;

import entities.Category;
import services.CategoryService;

import java.util.List;

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<Category> getAllCategories() {
        return categoryService.getAll();
    }

    public void addCategory(String name,String description) {
        Category category = new Category();
        category.setNom(name);
        category.setDescription(description);
        categoryService.save(category);
    }

    public void deleteCategory(Long id) {
        categoryService.get(id).ifPresent(categoryService::delete);
    }
}
