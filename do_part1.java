import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;

public class do_part1 {

    private static boolean validateFields(String[] fields) {
        if (fields[1].trim()== "Hokey"||fields[1].trim()== "Football"||fields[1].trim()== "Basketball")
            return true;
        else
            return false;
    }
    //need to fix this to sort through input txt
    String inputFile0 = "games2001.csv";
    String inputFile1 = "games2011.csv";
    String inputFile2 = "games2019.csv";
    String outputFile0 = "Hokey.csv";
    String outputFile1 = "Football.csv";
    String outputFile2 = "Basketball.csv";
    String outputFile3 = "syntax_error_file.txt";

    public do_part1() {

        try (BufferedReader br = new BufferedReader(new FileReader("games2001.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                // Validate fields
                boolean isValidFormat = validateFields(fields);

                String sport = fields[1].trim();

                if (isValidFormat) {
                    writeToFile(sport + ".csv", line);
                } else {
                    writeToFile("syntax_error_file.txt", line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading/writing file: " + e.getMessage());
        }


    }

    private static void writeToFile(String filename, String line) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filename, true))) {
            printWriter.println(line);
            //will print to whatever file is input
        }
    }
}
