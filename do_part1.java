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
    public static String error;
    public String missingfield;

    public static boolean validateshortfield(String[] fields){
        for (int i =0; i< fields.length;i++){
            for (int j=0; j<fields.length; j++){

            }
        }
        return true;
    }



    private static boolean validateFields(String[] fields, String csv) {
        String sport = fields[1].trim();
        int year = Integer.parseInt(fields[2].trim());
        int correctyear = Integer.parseInt(csv.replaceAll("[^0-9]","").trim()); //replaces any character that is not a digit to blank

        if(!(sport.equals("Hokey") || sport.equals("Football") || sport.equals("Basketball"))) {

            error = "Invalid Sport";
            return false;


        }
        if (year != correctyear) {
            error = "Invalid Year";
            return false;
        }


        if (!(fields[3].trim().contains("-"))) {
            error = "Invalid Record";
            return false;
        }


        String flag = fields[4].trim();
        if (!flag.equals("Y") && !flag.equals("N")) {
            error = "Invalid Champsionship Status";
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


    try (BufferedReader br = new BufferedReader(new FileReader("part1 input file names (1).txt"))) {
        int number;
        number = Integer.parseInt(br.readLine());
        String[] s =new String[number];
        for (int i = 0; i<number; i++){
            s[i] = br.readLine().trim();
        }
        for (int i = 0; i<s.length; i++){
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(s[i]))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] fields = line.split(",");

                    try {
                        if (fields.length > 5) {
                            throw new TooManyFieldsException("Error: There are too many fields");
                        }
                        try {
                            if (fields.length < 5) {
                                int count = 0;
                                for (int j =0; j< fields.length; j++){
                                    System.out.println(fields[j]);
                                    if (fields[j].equals(null)){
                                        System.out.println("aa");
                                        count = j;
                                    }
                                }
                                switch (count){
                                    case 0:{
                                        missingfield = "Team";
                                        break;}
                                    case 1:{
                                        missingfield = "Sport";
                                        break;}
                                    case 2:{
                                        missingfield = "Year";
                                    }
                                    case 3:{
                                        missingfield = "Record";
                                        break;}
                                    case 4:{
                                        missingfield = "Championship Status";
                                    }
                                }
                                throw new TooFewFieldsException("Error: There are too few fields");
                            }
                            // Validate fields
                            boolean isValidFormat = validateFields(fields, s[i]);
                            if (!isValidFormat) {
                                throw new UnknownSportException("Error: There is an unknown sport defined");
                            }
                            // Determine the sport field
                            if (fields.length >= 2) {
                                String sport = fields[1].trim();
                                if (isValidFormat) {
                                    writeToFile(sport + ".csv", line);
                                } else {
                                    writeToFileSyntaxError("syntax_error_file.txt", line, s[i],  error);

                                }
                            }
                        } catch (UnknownSportException e) {
                            System.out.println(e.getMessage());
                            writeToFileSyntaxError("syntax_error_file.txt", line, s[i], error);
                        } catch (TooFewFieldsException e) {
                            System.out.println(e.getMessage());
                            writeToFileSyntaxError("syntax_error_file.txt", line, s[i], "Missing field: " +missingfield);
                        }
                    } catch (TooManyFieldsException e) {
                        System.out.println(e.getMessage());
                        writeToFileSyntaxError("syntax_error_file.txt", line, s[i], "Too Many Fields");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading/writing file: " + e.getMessage());
            }

        }
        }



    catch (IOException e) {
        System.out.println("Error reading/writing file: " + e.getMessage());
    }
    }





    private static void writeToFile(String filename, String line) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filename, true))) {
            printWriter.println(line);


            //will print to whatever file is input
        }
    }

    private static void writeToFileSyntaxError(String filename, String line, String csv, String exception)throws IOException{
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filename, true))) {
            error = exception;
            printWriter.println("syntax error in the file: " + csv);
            printWriter.println("=================================================");
            printWriter.println("Error: " + error);
            printWriter.println("Record: " + line);
            printWriter.println();


            //will print to whatever file is input
        }
    }
}
