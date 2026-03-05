package realestate.Filter_search;

import java.util.ArrayList;
import java.util.List;

public class PropertySearch {

    /**
     * Scans every single column in the CSV for the search term.
     */
    public List<String[]> searchAll(List<String[]> data, String query) {
        List<String[]> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase().trim();

        // Start at 1 to skip the header row
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            for (String cell : row) {
                // If the term is found in ANY column of this row
                if (cell.toLowerCase().contains(lowerQuery)) {
                    results.add(row);
                    break; // Stop looking at this row and move to the next one
                }
            }
        }
        return results;
    }

    public void displayFinalTable(List<String[]> results) {
        if (results.isEmpty()) {
            System.out.println("\n[!] No matches found for your search.");
            return;
        }

        System.out.println("\n" + "=".repeat(90));
        System.out.printf("%-12s | %-8s | %-8s | %-12s | %-12s | %-10s%n", 
                          "UNIT CODE", "BLOCK", "LOT", "AREA", "PRICE", "STATUS");
        System.out.println("-".repeat(90));

        for (String[] row : results) {
            System.out.printf("%-12s | %-8s | %-8s | %-12s | %-12s | %-10s%n",
                              row[0], row[1], row[2], row[3], row[4], row[5]);
        }
        System.out.println("=".repeat(90));
    }
}