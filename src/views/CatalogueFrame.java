package views;

import javax.swing.*;
import java.awt.*;

import services.ProductService;
import services.CategoryService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CatalogueFrame extends JFrame {
    private final ProductService productService;
    private final CategoryService categoryService;

    // Components for adding a product
    private JTextField productNameField;
    private JTextField productPriceField;
    private JTextArea productListArea;

    // Components for adding a category
    private JTextField categoryNameField;
    private JTextArea categoryListArea;

    public CatalogueFrame(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;

        setTitle("Product and Category Management");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create tabs for Products and Categories
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Products", createProductPanel());
        tabbedPane.addTab("Categories", createCategoryPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        productNameField = new JTextField();
        productPriceField = new JTextField();
        productListArea = new JTextArea();
        productListArea.setEditable(false);

        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        panel.add(new JLabel("Product Name:"));
        panel.add(productNameField);
        panel.add(new JLabel("Product Price:"));
        panel.add(productPriceField);
        panel.add(addButton);
        panel.add(new JScrollPane(productListArea));

        return panel;
    }

    private JPanel createCategoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        categoryNameField = new JTextField();
        categoryListArea = new JTextArea();
        categoryListArea.setEditable(false);

        JButton addButton = new JButton("Add Category");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCategory();
            }
        });

        panel.add(new JLabel("Category Name:"));
        panel.add(categoryNameField);
        panel.add(addButton);
        panel.add(new JScrollPane(categoryListArea));

        return panel;
    }

    private void addProduct() {
        String name = productNameField.getText();
        String priceText = productPriceField.getText();

        if (name.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and price.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            entities.Product product = new entities.Product();
            product.setDesignation(name);
            product.setPrix(price);

            this.productService.add(product);
            updateProductList();
            productNameField.setText("");
            productPriceField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price. Please enter a numeric value.");
        }
    }

    private void addCategory() {
        String name = categoryNameField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a category name.");
            return;
        }

        entities.Category category = new entities.Category();
        category.setDescription(name);
        this.categoryService.add(category);
        updateCategoryList();
        categoryNameField.setText("");
    }

    private void updateProductList() {
        List<entities.Product> products = productService.getAll();
        StringBuilder productList = new StringBuilder();
        for (entities.Product product : products) {
            productList.append(product.getDesignation()).append(" - $").append(product.getPrice()).append("\n");
        }
        productListArea.setText(productList.toString());
    }

    private void updateCategoryList() {
        List<entities.Category> categories = categoryService.getAll();
        StringBuilder categoryList = new StringBuilder();
        for (entities.Category category : categories) {
            categoryList.append(category.getDescription()).append("\n");
        }
        categoryListArea.setText(categoryList.toString());
    }

//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("catalogue");
//        EntityManager em = emf.createEntityManager();
//
//        CategoryService categoryService = new CategoryService(em);
//        ProductService productService = new ProductService(em);
//
//        CatalogueFrame frame = new CatalogueFrame(productService, categoryService);
//        frame.setVisible(true);
//    }
}

