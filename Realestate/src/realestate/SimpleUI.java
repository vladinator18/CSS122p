package realestate;

import realestate.Parser.Parser;
import realestate.Presenter.Property;
import realestate.Presenter.PropertyData;
import realestate.Report.ReportGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class SimpleUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterCombo;
    private JTextField filterField;
    private List<Property> allProperties;
    private PropertyData propertyData;
    private ReportGenerator repGen;

    public SimpleUI() {
        // Set up main frame
        setTitle("Real Estate Property System");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        // Initialize Data Managers
        allProperties = new ArrayList<>();
        propertyData = new PropertyData();
        Parser parser = new Parser();
        
        // Load real.csv database data
        // Note: Working directory when ran from IDE/Terminal might vary, relying on "database.csv"
        List<String[]> rawData = parser.loadRawData("database.csv");
        if (rawData != null && !rawData.isEmpty()) {
            for (String[] row : rawData) {
                try {
                    Property p = new Property.Builder()
                        .setUnitCode(row[0])
                        .setBlock(row[1])
                        .setLot(Integer.parseInt(row[2]))
                        .setArea(Integer.parseInt(row[3]))
                        .setPrice(Double.parseDouble(row[4]))
                        .setStatus(row[5])
                        .build();
                    allProperties.add(p);
                } catch (Exception e) {
                    System.out.println("Skipping invalid row during UI load: " + String.join(",", row));
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Warning: 'database.csv' is missing or empty. Please ensure the file is present in the working directory.",
                "Data Load Warning",
                JOptionPane.WARNING_MESSAGE);
        }

        // Layout Setup
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top Control Panel (Filters)
        JPanel controlPanel = new JPanel(new GridLayout(2,1,0,0));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Search & Filter"));
        
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        
        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] filters = {"All Properties", "Block", "Lot Size", "Status", "Price"};
        filterCombo = new JComboBox<>(filters);
        filterCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        
        filterField = new JTextField(15);
        filterField.setFont(new Font("Arial", Font.PLAIN, 14));
//        filterField.setEnabled(false); // Disabled initially for "All Properties"
        
        JButton applyBtn = new JButton("Apply Filter");
        applyBtn.setFont(new Font("Arial", Font.BOLD, 14));
        applyBtn.setBackground(new Color(70, 130, 180));
        applyBtn.setForeground(Color.BLACK);
        applyBtn.setFocusPainted(false);

        
        JButton reportBtn = new JButton("Full Report");
        reportBtn.setFont(new Font("Arial", Font.BOLD, 14));
        reportBtn.setBackground(new Color(60, 179, 113));
        reportBtn.setForeground(Color.BLACK);
        reportBtn.setFocusPainted(false);

        JButton blockReportBtn = new JButton("Block Report");
        blockReportBtn.setFont(new Font("Arial", Font.BOLD, 14));
        blockReportBtn.setBackground(new Color(60, 179, 113));
        blockReportBtn.setForeground(Color.BLACK);
        blockReportBtn.setFocusPainted(false);
        
        
        JButton buyBtn = new JButton("Buy Apartment");
        buyBtn.setFont(new Font("Arial", Font.BOLD, 14));
        buyBtn.setBackground(new Color(60, 179, 113));
        buyBtn.setForeground(Color.BLACK);
        buyBtn.setFocusPainted(false);
        
        JButton resBtn = new JButton("Reserve Apartment");
        resBtn.setFont(new Font("Arial", Font.BOLD, 14));
        resBtn.setBackground(new Color(60, 179, 113));
        resBtn.setForeground(Color.BLACK);
        resBtn.setFocusPainted(false);

        reportBtn.addActionListener(e -> {
            ReportGenerator rg = new ReportGenerator();
            rg.generateFullReport(allProperties);
            JOptionPane.showMessageDialog(this, "Full report printed to console.", "Report Generated", JOptionPane.INFORMATION_MESSAGE);
        });

        blockReportBtn.addActionListener(e -> {
            ReportGenerator rg = new ReportGenerator();
            rg.generateBlockReport(allProperties);
            JOptionPane.showMessageDialog(this, "Block report printed to console.", "Report Generated", JOptionPane.INFORMATION_MESSAGE);
        });
        
        row1.add(filterLabel);
        row1.add(filterCombo);
        row1.add(filterField);
        row1.add(applyBtn);
        row2.add(reportBtn);
        row2.add(blockReportBtn);
        row2.add(buyBtn);
        row2.add(resBtn);
        
        controlPanel.add(row1);
        controlPanel.add(row2);
        
        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // Center Panel (JTable)
        String[] columnNames = {"UNIT CODE", "BLOCK LETTER", "LOT NO.", "AREA (sqm)", "PRICE", "STATUS"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table cells are read-only
            }
        };
        
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        // Center align table contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Initial Table Populatation
        updateTable(allProperties);

        // Action Listeners
        filterCombo.addActionListener(e -> {
            String selected = (String) filterCombo.getSelectedItem();
//            if ("All Properties".equals(selected)) {
//                filterField.setText("");
//                filterField.setEnabled(false);
//                updateTable(allProperties); // Instantly show all properties when switching back
//            } else {
                updateTable(allProperties);
                filterField.setEnabled(true);
                filterField.requestFocus();
//            } to enable for all selected cases for java combo box
        });

        applyBtn.addActionListener(e -> applyFilter());
        // Allow user to hit Enter directly in the text field to apply search
        filterField.addActionListener(e -> applyFilter());

        add(mainPanel);
    }

    private void applyFilter() {
        filterCombo.setSelectedItem("All Properties");
        String filterType = (String) filterCombo.getSelectedItem();
        String filterValue = filterField.getText().trim();
        List<Property> result;

//        if ("All Properties".equals(filterType)) {
//            updateTable(allProperties);
//            return;
//        }

        if (filterValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a value to filter by.", 
                "Empty Filter", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if ("All Properties".equals(filterType)) {
                result = propertyData.displayLots(allProperties, filterValue);
            }
            else if ("Block".equals(filterType)) {
                result = propertyData.filterByBlock(allProperties, filterValue);
            } else if ("Lot Size".equals(filterType)) {
                int size = Integer.parseInt(filterValue);
                result = propertyData.filterBySize(allProperties, size);
            } else if ("Status".equals(filterType)) {
                result = propertyData.filterByStatus(allProperties, filterValue);
            }
            else if ("Price".equals(filterType)) {
                int price = Integer.parseInt(filterValue);
                result = propertyData.filterByPrice(allProperties,price);
            }else {
                result = allProperties;
            }
            updateTable(result);
            
            if (result.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No matching properties found.", 
                    "No Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid number for 'Lot Size'.", 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "An unexpected error occurred while filtering.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<Property> properties) {
        tableModel.setRowCount(0); // Clear rows
        for (Property p : properties) {
            Object[] row = {
                p.getUnitCode(),
                p.getBlock(),
                p.getLot(),
                String.format(p.getArea()+"sqm"),
                String.format("₱%,.2f",p.getPrice()), // Format currency nicer
                p.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Set System Look & Feel for nicer overall OS-matching layout
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            
            SimpleUI ui = new SimpleUI();
            ui.setVisible(true);
        });
    }
}
