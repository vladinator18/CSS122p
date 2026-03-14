package realestate.Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<String[]> loadRawData(String filePath) {
        List<String[]> data = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLineHeader = true;
            while ((line = reader.readLine()) != null) {
                if (firstLineHeader) {
                    firstLineHeader = false;
                    continue; // Skip header row
                }
                // Split by commas not inside quotes
                String[] rawRow = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                String[] parsedRow = new String[rawRow.length];
                
                for (int i = 0; i < rawRow.length; i++) {
                    String val = rawRow[i].trim();
                    // Strip the quotes and remove commas from inside strings (like prices)
                    if (val.startsWith("\"") && val.endsWith("\"")) {
                        val = val.substring(1, val.length() - 1).replace(",", "");
                    }
                    parsedRow[i] = val;
                }
                data.add(parsedRow);
            }
        } catch (Exception e) {
            System.err.println("Error: Could not read " + filePath);
            e.printStackTrace();
        }
        
        return data;
    }
}