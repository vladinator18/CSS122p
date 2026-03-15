package realestate.Report;

import realestate.Presenter.Property;
import java.util.List;
import java.awt.*;
import javax.swing.*;

public class ReportGenerator extends JPanel {

    // Helper to collect text instead of printing to console
    private StringBuilder reportBuffer;

    // ─── Main Report ────────────────────────────────────────────────────────────
    public void generateFullReport(List<Property> properties) {
        reportBuffer = new StringBuilder(); // Reset buffer
        
        printHeader();
        printTable(properties);
        printSummary(properties);
        printFooter();
        
        showReportPopup("Full Property Status Report");
    }

    // ─── Block Report ───────────────────────────────────────────────────────────
    public void generateBlockReport(List<Property> properties) {
        reportBuffer = new StringBuilder();
        String[] blocks = {"A", "B", "C", "D", "E"};

        reportBuffer.append("=".repeat(80)).append("\n");
        reportBuffer.append(String.format("%50s\n", "REPORT PER BLOCK"));
        reportBuffer.append("=".repeat(80)).append("\n");

        for (String block : blocks) {
            reportBuffer.append("\n  BLOCK ").append(block).append("\n");
            reportBuffer.append("  ").append("-".repeat(76)).append("\n");
            reportBuffer.append(String.format("  %-10s %-8s %-12s %-14s %-10s\n",
                    "UNIT CODE", "LOT", "AREA (SQM)", "PRICE", "STATUS"));
            reportBuffer.append("  ").append("-".repeat(76)).append("\n");

            boolean hasLots = false;
            for (Property p : properties) {
                if (p.getBlock().equalsIgnoreCase(block)) {
                    reportBuffer.append(String.format("  %-10s %-8d %-12d %-14.2f %-10s\n",
                            p.getUnitCode(), p.getLot(), p.getArea(), p.getPrice(), p.getStatus()));
                    hasLots = true;
                }
            }
            if (!hasLots) reportBuffer.append("  No lots found for Block ").append(block).append(".\n");
        }
        
        reportBuffer.append("\n").append("=".repeat(80)).append("\n");
        reportBuffer.append(String.format("%50s\n", "END OF BLOCK REPORT"));
        reportBuffer.append("=".repeat(80)).append("\n");

        showReportPopup("Block Report");
    }

    // ─── Popup Logic ────────────────────────────────────────────────────────────
    private void showReportPopup(String title) {
        JTextArea textArea = new JTextArea(reportBuffer.toString());
        textArea.setEditable(false);
        // CRITICAL: Monospaced font keeps the table columns aligned
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, 500));

        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.PLAIN_MESSAGE);
    }

    // ─── Refactored Helper Methods ──────────────────────────────────────────────
    private void printHeader() {
        reportBuffer.append("=".repeat(80)).append("\n");
        reportBuffer.append(String.format("%55s\n", "REAL ESTATE SALES & MANAGEMENT SYSTEM"));
        reportBuffer.append(String.format("%50s\n", "FULL PROPERTY STATUS REPORT"));
        reportBuffer.append("=".repeat(80)).append("\n");
        reportBuffer.append(String.format("%-10s %-8s %-8s %-12s %-14s %-10s\n",
                "UNIT CODE", "BLOCK", "LOT", "AREA (SQM)", "PRICE", "STATUS"));
        reportBuffer.append("-".repeat(80)).append("\n");
    }

    private void printTable(List<Property> properties) {
        for (Property p : properties) {
            reportBuffer.append(String.format("%-10s %-8s %-8d %-12d %-14.2f %-10s\n",
                    p.getUnitCode(), p.getBlock(), p.getLot(), p.getArea(), p.getPrice(), p.getStatus()));
        }
        reportBuffer.append("-".repeat(80)).append("\n");
    }

    private void printSummary(List<Property> properties) {
        int total = properties.size();
        int available = 0, sold = 0, reserved = 0;
        double totalRevenue = 0;

        for (Property p : properties) {
            switch (p.getStatus().toLowerCase()) {
                case "available": available++; break;
                case "sold": sold++; totalRevenue += p.getPrice(); break;
                case "reserved": reserved++; break;
            }
        }

        reportBuffer.append("\n  SUMMARY\n");
        reportBuffer.append("  ").append("-".repeat(35)).append("\n");
        reportBuffer.append(String.format("  %-20s %d\n", "Total Lots:", total));
        reportBuffer.append(String.format("  %-20s %d\n", "Available:", available));
        reportBuffer.append(String.format("  %-20s %d\n", "Reserved:", reserved));
        reportBuffer.append(String.format("  %-20s %d\n", "Sold:", sold));
        reportBuffer.append(String.format("  %-20s %.2f\n", "Total Revenue:", totalRevenue));
        reportBuffer.append("  ").append("-".repeat(35)).append("\n");
    }

    private void printFooter() {
        reportBuffer.append("=".repeat(80)).append("\n");
        reportBuffer.append(String.format("%45s\n", "END OF REPORT"));
        reportBuffer.append("=".repeat(80)).append("\n");
    }
}