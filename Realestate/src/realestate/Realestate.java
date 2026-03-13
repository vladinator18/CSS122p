package realestate;

import realestate.Parser.Parser;
import realestate.Filter_search.PropertySearch;
import realestate.Presenter.Property;
import realestate.Presenter.PropertyData;  
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Realestate {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            Parser parser = new Parser();
            PropertyData presenter = new PropertyData();
            List<String[]> allData = parser.loadRawData("database.csv");
            if (allData.isEmpty()) {
                System.out.println("Error: database.csv is missing or empty.");
                return;
            }

            //Convert CSV rows into Property objects
            List<Property> properties = new ArrayList<>();
            for (String[] row : allData) {
                try {
                    Property p = new Property.Builder()
                        .setUnitCode(row[0])
                        .setBlock(row[1])
                        .setLot(Integer.parseInt(row[2]))
                        .setArea(Integer.parseInt(row[3]))
                        .setPrice(Double.parseDouble(row[4]))
                        .setStatus(row[5])
                        .build();
                    properties.add(p);
                } catch (Exception e) {
                    System.out.println("Skipping invalid row: " + String.join(",", row));
                }
            }

            //Main menu loop
            while (true) {
                System.out.println("\n--- REAL ESTATE PROPERTY SYSTEM ---");
                System.out.println("1. Display all properties");
                System.out.println("2. Filter by block");
                System.out.println("3. Filter by size");
                System.out.println("4. Filter by status");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                String choice = scanner.nextLine();
                List<Property> result = new ArrayList<>();

                switch (choice) {
                    case "1":
                        presenter.displayLots(properties); // <-- uses 'properties' here
                        break;
                    case "2":
                        System.out.print("Enter block (e.g. A, B): ");
                        String block = scanner.nextLine();
                        result = presenter.filterByBlock(properties, block);
                        presenter.displayLots(result);
                        break;
                    case "3":
                        System.out.print("Enter lot size: ");
                        int size = Integer.parseInt(scanner.nextLine());
                        result = presenter.filterBySize(properties, size);
                        presenter.displayLots(result);
                        break;
                    case "4":
                        System.out.print("Enter status (Available/Sold/Reserved): ");
                        String status = scanner.nextLine();
                        result = presenter.filterByStatus(properties, status);
                        presenter.displayLots(result);
                        break;
                    case "5":
                        System.out.println("Exiting system...");
                        return;
                    default:
                        System.out.println("Invalid choice, try again.");
                }
            }

        }
    }
}
    
    
