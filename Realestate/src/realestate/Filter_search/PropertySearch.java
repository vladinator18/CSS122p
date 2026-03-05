package realestate.Filter_search;

import java.util.ArrayList;
import java.util.List;

public class PropertySearch {

    public List<String[]> findByCriteria(List<String[]> data, String query, int columnIndex) {
        List<String[]> filteredResults = new ArrayList<>();
        
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            // "Fuzzy" search: checks if the cell contains the query text
            if (row.length > columnIndex && 
                row[columnIndex].toLowerCase().contains(query.toLowerCase())) {
                filteredResults.add(row);
            }
        }
        return filteredResults;
    }

    public void displayResults(List<String[]> results) {
        if (results.isEmpty()) {
            System.out.println("\n[!] No matching properties found.");
            return;
        }

        System.out.printf("\n%-12s | %-8s | %-12s | %-10s%n", "UNIT CODE", "BLOCK", "PRICE", "STATUS");
        System.out.println("------------------------------------------------------------");
        for (String[] row : results) {
            System.out.printf("%-12s | %-8s | %-12s | %-10s%n", row[0], row[1], row[4], row[5]);
        }
    }
}