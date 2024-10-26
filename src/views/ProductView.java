package views;

import entities.Product;
import entities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import services.ProductService;
import services.CategoryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductView extends JFrame {
    private ProductService productService;
    private CategoryService categoryService;
    private DefaultTableModel tableModel;
    private JTable productTable;
    private JTextField designationField, priceField, quantityField, sdrField;
    private JComboBox<Category> categoryComboBox;
    private JButton addButton;

    public ProductView(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;

        setTitle("Product Management");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new String[]{"ID", "Designation", "Price", "Quantity", "SDR", "Category", "Actions"}, 0);
        productTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        designationField = new JTextField(10);
        priceField = new JTextField(10);
        quantityField = new JTextField(10);
        sdrField = new JTextField(10);
        categoryComboBox = new JComboBox<>(categoryService.getAll().toArray(new Category[0]));

        addButton = new JButton("Add Product");

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Designation:"));
        inputPanel.add(designationField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("SDR:"));
        inputPanel.add(sdrField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryComboBox);
        inputPanel.add(addButton);

        addButton.addActionListener(e -> addProduct());

        setLayout(new BorderLayout());
        add(tableScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        loadProducts();
    }

    private void loadProducts() {
        List<Product> products = productService.getAll();
        tableModel.setRowCount(0);
        for (Product product : products) {
            tableModel.addRow(new Object[]{
                    product.getId(),
                    product.getDesignation(),
                    product.getPrix(),
                    product.getQuantite(),
                    product.getSdr(),
//                    product.getCategory().getNom(),
                    new JButton("Delete")
            });
        }
    }

    private void addProduct() {
        try {
            String designation = designationField.getText().trim();
            Double price = Double.parseDouble(priceField.getText().trim());
            Long quantity = Long.parseLong(quantityField.getText().trim());
            Long sdr = Long.parseLong(sdrField.getText().trim());
            Category category = (Category) categoryComboBox.getSelectedItem();

            Product product = new Product(designation, null, price, quantity, sdr, category);
            productService.add(product);

            loadProducts();
            clearInputs();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearInputs() {
        designationField.setText("");
        priceField.setText("");
        quantityField.setText("");
        sdrField.setText("");
        categoryComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("catalogue");
        EntityManager em = emf.createEntityManager();

        ProductService productService = new ProductService(em);
        CategoryService categoryService = new CategoryService(em);

        SwingUtilities.invokeLater(() -> new ProductView(productService, categoryService).setVisible(true));
    }
}
