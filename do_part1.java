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
        String sport = fields[1].trim();
        int year = Integer.parseInt(fields[2].trim());

        if(!(sport.equals("Hokey") || sport.equals("Football") || sport.equals("Basketball")))
            return false;

        if (year <= 2000) {
            return false;
        }


        if (!(fields[3].trim().contains("-"))) {
            return false;
        }


        String flag = fields[4].trim();
        if (!flag.equals("Y") && !flag.equals("N")) {
            return false;
        }

        return true;
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

    for (int y = 0; y<=3;y++)
    {

    try (BufferedReader br = new BufferedReader(new FileReader("part1 input file names (1).txt")))
        {
        String readCsv = br.readLine().trim();
        y++;
        }

    catch (IOException e) {
            System.out.println("Error reading/writing file: " + e.getMessage());
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("readCsv"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");

                try {
                    if (fields.length > 5) {
                        throw new TooManyFieldsException("Error: There are too many fields");
                    }
                    try {
                        if(fields.length<5){
                            throw new TooFewFieldsException("Error: There are too few fields");
                        }
                        // Validate fields
                        boolean isValidFormat = validateFields(fields);
                        if (isValidFormat){
                            throw new UnknownSportException("Error: There is an unknown sport defined");
                        }
                        // Determine the sport field
                        if (fields.length >= 2) {
                            String sport = fields[1].trim();
                            if (isValidFormat) {
                                writeToFile(sport + ".csv", line);
                            } else {
                                writeToFile("syntax_error_file.txt", line);
                            }
                        }
                    }
                    catch(UnknownSportException e){
                        System.out.println(e.getMessage());
                        writeToFile("syntax_error_file.txt", line);
                    }
                    catch(TooFewFieldsException e){
                        System.out.println(e.getMessage());
                        writeToFile("syntax_error_file.txt", line);
                    }
                } catch (TooManyFieldsException e) {
                    System.out.println(e.getMessage());
                    writeToFile("syntax_error_file.txt", line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading/writing file: " + e.getMessage());
        }
    }



    }

    private static void writeToFile(String filename, String line) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filename, true))) {
            printWriter.println(line);
            //will print to whatever file is input
        }
    }
}
