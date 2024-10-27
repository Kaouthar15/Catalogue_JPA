package controllers;

import entities.Product;
import services.ProductService;

import java.util.List;

public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    public void addProduct(Product product) {
        productService.save(product);
    }

    public void deleteProduct(Long productId) {
        productService.get(productId).ifPresent(productService::delete);
    }
}
