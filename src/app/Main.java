package app;

import controllers.CategoryController;
import controllers.ProductController;
import services.CategoryService;
import services.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import views.CatalogueFrame;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("catalogue");
        EntityManager em = emf.createEntityManager();

        ProductService productService = new ProductService(em);
        ProductController productController = new ProductController(productService);

        CategoryService categoryService = new CategoryService(em);
        CategoryController categoryController = new CategoryController(categoryService);

        CatalogueFrame frame = new CatalogueFrame(productController,categoryController);
        frame.setVisible(true);
    }
}
