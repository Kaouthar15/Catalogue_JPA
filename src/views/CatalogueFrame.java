package views;

import controllers.ProductController;
import controllers.CategoryController;
import entities.Product;
import entities.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CatalogueFrame extends JFrame {
    private ProductController productController;
    private CategoryController categoryController;

    // Panels
    private JPanel productPanel;
    private JPanel categoryPanel;

    // Components for Product Panel
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private JTextField txtDesignation, txtPrice, txtQuantity, txtSdr;
    private JComboBox<Category> categoryComboBox;

    // Components for Category Panel
    private JTable categoryTable;
    private DefaultTableModel categoryTableModel;
    private JTextField txtCategoryName, txtCategoryDescription;

    public CatalogueFrame(ProductController productController, CategoryController categoryController) {
        this.productController = productController;
        this.categoryController = categoryController;
        initUI();
        loadProducts();
        loadCategories();
    }

    // Initialize the User Interface
    private void initUI() {
        setTitle("Product and Category Management");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create Button Panel for switching between views
        JPanel buttonPanel = new JPanel();
        JButton btnProducts = new JButton("Products");
        JButton btnCategories = new JButton("Categories");

        btnProducts.addActionListener(e -> showProductPanel());
        btnCategories.addActionListener(e -> showCategoryPanel());

        buttonPanel.add(btnProducts);
        buttonPanel.add(btnCategories);

        add(buttonPanel, BorderLayout.NORTH);

        // Initialize Product Panel
        productPanel = new JPanel(new BorderLayout());
        initProductPanel();

        // Initialize Category Panel
        categoryPanel = new JPanel(new BorderLayout());
        initCategoryPanel();

        // Add Product Panel as default visible
        add(productPanel, BorderLayout.CENTER);
    }

    // Initialize the Product Panel
    private void initProductPanel() {
        // Create Table for Products
        productTableModel = new DefaultTableModel(new String[]{"ID", "Designation", "Price", "Quantity", "SDR", "Category"}, 0);
        productTable = new JTable(productTableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);

        // Add Panel for Product Details
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Designation:"));
        txtDesignation = new JTextField(10);
        inputPanel.add(txtDesignation);

        inputPanel.add(new JLabel("Price:"));
        txtPrice = new JTextField(5);
        inputPanel.add(txtPrice);

        inputPanel.add(new JLabel("Quantity:"));
        txtQuantity = new JTextField(5);
        inputPanel.add(txtQuantity);

        inputPanel.add(new JLabel("SDR:"));
        txtSdr = new JTextField(5);
        inputPanel.add(txtSdr);

        inputPanel.add(new JLabel("Category:"));
        categoryComboBox = new JComboBox<>();
        inputPanel.add(categoryComboBox);

        JButton btnAdd = new JButton("Add Product");
        btnAdd.addActionListener(e -> addProduct());
        inputPanel.add(btnAdd);

        // Panel for Delete button
        JPanel deletePanel = new JPanel();
        JButton btnDelete = new JButton("Delete Product");
        btnDelete.addActionListener(e -> deleteProduct());
        deletePanel.add(btnDelete);


        productPanel.add(inputPanel, BorderLayout.NORTH);
        productPanel.add(scrollPane, BorderLayout.CENTER);
        productPanel.add(deletePanel, BorderLayout.SOUTH);
    }

    // Initialize the Category Panel
    private void initCategoryPanel() {
        // Create Table for Categories
        categoryTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Description"}, 0);
        categoryTable = new JTable(categoryTableModel);
        JScrollPane scrollPane = new JScrollPane(categoryTable);

        // Add Panel for Category Details
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Category Name:"));
        txtCategoryName = new JTextField(10);
        inputPanel.add(txtCategoryName);

        inputPanel.add(new JLabel("Description:"));
        txtCategoryDescription = new JTextField(10);
        inputPanel.add(txtCategoryDescription);

        JButton btnAddCategory = new JButton("Add Category");
        btnAddCategory.addActionListener(e -> addCategory());
        inputPanel.add(btnAddCategory);

        // Panel for Delete button
        JPanel deletePanel = new JPanel();
        JButton btnDeleteCategory = new JButton("Delete Category");
        btnDeleteCategory.addActionListener(e -> deleteCategory());
        deletePanel.add(btnDeleteCategory);

        categoryPanel.add(inputPanel, BorderLayout.NORTH);
        categoryPanel.add(scrollPane, BorderLayout.CENTER);
        categoryPanel.add(deletePanel, BorderLayout.SOUTH);
    }

    // Load Products from the database into the table
    private void loadProducts() {
        productTableModel.setRowCount(0);
        List<Product> products = productController.getAllProducts();
        for (Product product : products) {
            productTableModel.addRow(new Object[]{
                    product.getId(), product.getDesignation(), product.getPrix(), product.getQuantite(), product.getSdr(), product.getCategory() != null ? product.getCategory().getNom() : "N/A"
            });
        }
    }

    // Load Categories into the JComboBox
    private void loadCategories() {
        List<Category> categories = categoryController.getAllCategories();
        categoryComboBox.removeAllItems();
        for (Category category : categories) {
            categoryComboBox.addItem(category);
            categoryTableModel.addRow(new Object[]{
                    category.getId(), category.getNom(), category.getDescription()
            });
        }
    }

    // Show the Product Panel
    private void showProductPanel() {
        categoryPanel.setVisible(false);
        productPanel.setVisible(true);
        add(productPanel, BorderLayout.CENTER);
        validate();
        repaint();
    }

    // Show the Category Panel
    private void showCategoryPanel() {
        productPanel.setVisible(false);
        categoryPanel.setVisible(true);
        add(categoryPanel, BorderLayout.CENTER);
        validate();
        repaint();
    }

    // Add a new product
    private void addProduct() {
        String designation = txtDesignation.getText();
        double price;
        Long quantity;
        Long sdr;

        try {
            price = Double.parseDouble(txtPrice.getText());
            quantity = Long.parseLong(txtQuantity.getText());
            sdr = Long.parseLong(txtSdr.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for price, quantity, and SDR.");
            return;
        }

        Category selectedCategory = (Category) categoryComboBox.getSelectedItem();
        if (selectedCategory == null) {
            JOptionPane.showMessageDialog(this, "Please select a category.");
            return;
        }

        Product product = new Product();
        product.setDesignation(designation);
        product.setPrix(price);
        product.setQuantite(quantity);
        product.setSdr(sdr);
        product.setCategory(selectedCategory);

        productController.addProduct(product);
        loadProducts();
        txtDesignation.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        txtSdr.setText("");
        categoryComboBox.setSelectedIndex(0);
    }

    // Add a new category
    private void addCategory() {
        String name = txtCategoryName.getText();
        String description = txtCategoryDescription.getText();

        if (name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both category name and description.");
            return;
        }

        categoryController.addCategory(name, description);
        loadCategories();
        txtCategoryName.setText("");
        txtCategoryDescription.setText("");
    }

    // Delete the selected product
    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.");
            return;
        }

        Long productId = (Long) productTableModel.getValueAt(selectedRow, 0);
        productController.deleteProduct(productId);
        loadProducts();
    }

    // Delete the selected category
    private void deleteCategory() {
        int selectedRow = categoryTable.getSelectedRow(); // Assuming you have a JTable for categories
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a category to delete.");
            return;
        }

        Long categoryId = (Long) categoryTableModel.getValueAt(selectedRow, 0); // Get category ID
        categoryController.deleteCategory(categoryId); // Call the delete method
        loadCategories(); // Refresh the table to reflect changes
    }
}
