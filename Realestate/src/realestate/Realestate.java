package realestate;

// 1. Make sure this import is exactly like this
import com.opencsv.CSVReader; 
import java.io.StringReader;
import java.util.Arrays;

public class Realestate {
    public static void main(String[] args) {
        String csvData = "ID, Name, Role\n1, Gemini, Assistant";

        // 2. Change 'CSVReader' to 'com.opencsv.CSVReader' here 
        // This stops Java from looking in your 'realestate' package
        try (com.opencsv.CSVReader reader = new com.opencsv.CSVReader(new StringReader(csvData))) {
            String[] nextLine;
            System.out.println("--- OpenCSV Test Started ---");
            
            while ((nextLine = reader.readNext()) != null) {
                System.out.println("Row found: " + Arrays.toString(nextLine));
            }
            System.out.println("--- Success: OpenCSV is working! ---");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
