/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package realestate.Report;

import realestate.Presenter.Property;
import java.util.List;
import javax.swing.*;
public class ReportGenerator extends JPanel {

    // ─── Main Report ────────────────────────────────────────────────────────────
    public void generateFullReport(List<Property> properties) {
        printHeader();
        printTable(properties);
        printSummary(properties);
        printFooter();
    }

    // ─── Header ─────────────────────────────────────────────────────────────────
    private void printHeader() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("              REAL ESTATE SALES & MANAGEMENT SYSTEM");
        System.out.println("                     FULL PROPERTY STATUS REPORT");
        System.out.println("=".repeat(80));
        System.out.printf("%-10s %-8s %-8s %-12s %-14s %-10s%n",
                "UNIT CODE", "BLOCK", "LOT", "AREA (SQM)", "PRICE", "STATUS");
        System.out.println("-".repeat(80));
    }

    // ─── Table ──────────────────────────────────────────────────────────────────
    private void printTable(List<Property> properties) {
        for (Property p : properties) {
            System.out.printf("%-10s %-8s %-8d %-12d %-14.2f %-10s%n",
                    p.getUnitCode(),
                    p.getBlock(),
                    p.getLot(),
                    p.getArea(),
                    p.getPrice(),
                    p.getStatus());
        }
        System.out.println("-".repeat(80));
    }

    // ─── Summary ────────────────────────────────────────────────────────────────
    private void printSummary(List<Property> properties) {
        int total = properties.size();
        int available = 0, sold = 0, reserved = 0;
        double totalRevenue = 0;

        for (Property p : properties) {
            switch (p.getStatus().toLowerCase()) {
                case "available": available++; break;
                case "sold":
                    sold++;
                    totalRevenue += p.getPrice();
                    break;
                case "reserved": reserved++; break;
            }
        }

        System.out.println("\n  SUMMARY");
        System.out.println("  " + "-".repeat(35));
        System.out.printf("  %-20s %d%n",  "Total Lots:",     total);
        System.out.printf("  %-20s %d%n",  "Available:",      available);
        System.out.printf("  %-20s %d%n",  "Reserved:",       reserved);
        System.out.printf("  %-20s %d%n",  "Sold:",           sold);
        System.out.printf("  %-20s %.2f%n","Total Revenue:",  totalRevenue);
        System.out.println("  " + "-".repeat(35));
    }

    // ─── Footer ─────────────────────────────────────────────────────────────────
    private void printFooter() {
        System.out.println("=".repeat(80));
        System.out.println("                         END OF REPORT");
        System.out.println("=".repeat(80) + "\n");
    }

    // ─── Block Report ───────────────────────────────────────────────────────────
    public void generateBlockReport(List<Property> properties) {
        String[] blocks = {"A", "B", "C", "D", "E"};

        System.out.println("\n" + "=".repeat(80));
        System.out.println("                   REPORT PER BLOCK");
        System.out.println("=".repeat(80));

        for (String block : blocks) {
            System.out.println("\n  BLOCK " + block);
            System.out.println("  " + "-".repeat(76));
            System.out.printf("  %-10s %-8s %-12s %-14s %-10s%n",
                    "UNIT CODE", "LOT", "AREA (SQM)", "PRICE", "STATUS");
            System.out.println("  " + "-".repeat(76));

            boolean hasLots = false;
            for (Property p : properties) {
                if (p.getBlock().equalsIgnoreCase(block)) {
                    System.out.printf("  %-10s %-8d %-12d %-14.2f %-10s%n",
                            p.getUnitCode(),
                            p.getLot(),
                            p.getArea(),
                            p.getPrice(),
                            p.getStatus());
                    hasLots = true;
                }
            }

            if (!hasLots) {
                System.out.println("  No lots found for Block " + block + ".");
            }
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("                    END OF BLOCK REPORT");
        System.out.println("=".repeat(80) + "\n");
        
        
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Set System Look & Feel for nicer overall OS-matching layout
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            ReportGenerator rg = new ReportGenerator();
            rg.setVisible(true);
        });
    }
    
}