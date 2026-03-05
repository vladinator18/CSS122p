package realestate.Parser;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<String[]> loadRawData(String filePath) {
        List<String[]> data = new ArrayList<>();
        
        // This 'try' block replaces the UnsupportedOperationException
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            // readAll() converts the CSV rows into a List of String arrays
            data = reader.readAll(); 
        } catch (Exception e) {
            System.err.println("Error: Could not read " + filePath);
            e.printStackTrace();
        }
        
        return data;
    }
}