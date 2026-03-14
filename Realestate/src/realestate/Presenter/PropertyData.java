package realestate.Presenter;

import java.util.ArrayList;
import java.util.List;

public class PropertyData {

    // Display all properties
    public List<Property> displayLots(List<Property> properties) {
        System.out.printf("%-10s %-6s %-6s %-8s %-10s %-10s\n",
                "UNITCODE", "BLOCK", "LOT", "AREA", "PRICE", "STATUS");
        System.out.println("------------------------------------------------------");
        for (Property p : properties) {
            p.display();
        }
    }

    // Filter by lot size
    public List<Property> filterBySize(List<Property> properties, int size) {
        List<Property> result = new ArrayList<>();
        for (Property p : properties) {
            if (p.getArea() == size) {
                result.add(p);
            }
        }
        return result;
    }

    // Filter by block number
    public List<Property> filterByBlock(List<Property> properties, String block) {
        List<Property> result = new ArrayList<>();
        for (Property p : properties) {
            if (p.getBlock().equalsIgnoreCase(block)) {
                result.add(p);
            }
        }
        return result;
    }

    // Filter by status
    public List<Property> filterByStatus(List<Property> properties, String status) {
        List<Property> result = new ArrayList<>();
        for (Property p : properties) {
            if (p.getStatus().equalsIgnoreCase(status)) {
                result.add(p);
            }
        }
        return result;
    }
    public List<Property> filterByPrice(List<Property> properties, int price) {
        List<Property> result = new ArrayList<>();
        for (Property p : properties) {
            if (p.getPrice()==price) {
                result.add(p);
            }
        }
        return result;
    }
}