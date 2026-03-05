package realestate;

import realestate.Parser.Parser;
import realestate.Filter_search.PropertySearch;
import java.util.List;
import java.util.Scanner;

public class Realestate {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();
        PropertySearch engine = new PropertySearch();
        
        // Load the data (Ensure database.csv is in your project root)
        List<String[]> allData = parser.loadRawData("database.csv");

        if (allData.isEmpty()) {
            System.out.println("Error: database.csv is missing or empty.");
            return;
        }

        while (true) {
            System.out.println("\n--- GLOBAL PROPERTY SEARCH ---");
            System.out.print("Enter any keyword to search (or type 'exit' to quit): ");
            String query = scanner.nextLine();

            if (query.equalsIgnoreCase("exit")) break;

            // This goes through EVERYTHING
            List<String[]> matches = engine.searchAll(allData, query);
            engine.displayFinalTable(matches);
        }

        System.out.println("Exiting System...");
        scanner.close();
    }
}