package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controllers.CategoryController;
import entities.Category;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CategoryView extends JFrame {
    private final JTextField categoryField;
    private final JTextField descriptionField;
    private JButton addButton;
    private final JTable categoryTable;
    private final DefaultTableModel tableModel;
    private final CategoryController categoryController;

    public CategoryView() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("catalogue");
        categoryController = new CategoryController(emf);

        setTitle("Category Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        categoryField = new JTextField(15);
        descriptionField = new JTextField(15);
        addButton = new JButton("Add Category");

        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(addButton);

        String[] columnNames = {"ID", "Category", "Description", "Action"};
        tableModel = new DefaultTableModel(columnNames, 0);
        categoryTable = new JTable(tableModel);
        categoryTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(categoryTable);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String categoryName = categoryField.getText().trim();
            String description = descriptionField.getText().trim();

            try {
                categoryController.addCategory(categoryName, description);
                loadCategories();
                categoryField.setText("");
                descriptionField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        categoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = categoryTable.rowAtPoint(evt.getPoint());
                int column = categoryTable.columnAtPoint(evt.getPoint());

                if (column == 3) {
                    Long id = (Long) tableModel.getValueAt(row, 0);
                    deleteCategory(id);
                }
            }
        });

        loadCategories();
    }

    private void loadCategories() {
        tableModel.setRowCount(0);
        List<Category> categories = categoryController.loadCategories();

        for (Category category : categories) {
            Object[] rowData = {category.getId(), category.getNom(), category.getDescription(), "Delete"};
            tableModel.addRow(rowData);
        }
    }

    private void deleteCategory(Long id) {
        try {
            categoryController.deleteCategory(id);
            loadCategories();
            JOptionPane.showMessageDialog(this, "Category deleted.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CategoryView frame = new CategoryView();
            frame.setVisible(true);
        });
    }
}
//jsp - servlet