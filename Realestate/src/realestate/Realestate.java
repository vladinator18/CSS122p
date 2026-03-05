package realestate;

import realestate.Parser.Parser;
import realestate.Filter_search.PropertySearch;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Realestate {

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Parser csvParser = new Parser();
            PropertySearch searchEngine = new PropertySearch();
            
            List<String[]> allData = csvParser.loadRawData("database.csv");
            
            if (allData.isEmpty()) {
                System.out.println("Error: Database empty or file not found.");
                return;
            }
            
            // --- PREPARE "AUTOFILL" SUGGESTIONS ---
            // This gets unique values from the 'Status' column (Index 5)
            Set<String> statusOptions = allData.stream()
                    .skip(1) // skip headers
                    .map(row -> row[5].trim())
                    .collect(Collectors.toSet());
            
            boolean running = true;
            while (running) {
                System.out.println("\n========================================");
                System.out.println("   REAL ESTATE SYSTEM (Type 'exit' to quit)");
                System.out.println("   Suggestions: " + statusOptions);
                System.out.println("========================================");
                
                System.out.print("Search by Status: ");
                String userQuery = input.nextLine();
                
                if (userQuery.equalsIgnoreCase("exit")) {
                    running = false;
                    System.out.println("Goodbye!");
                } else {
                    List<String[]> results = searchEngine.findByCriteria(allData, userQuery, 5);
                    searchEngine.displayResults(results);
                }
            }
        }
    }
}