import java.io.*;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    public static String error;
    public static String missingfield;
    public static int count1 = 0;
    public static String[] temp = new String[1000];

    public static int filecount;

    public static String validateshortfield(String[] fields, String csv) {
        boolean sportscheck = false;
        boolean yearcheck = false;
        boolean recordcheck = false;
        boolean champcheck = false;
        int correctyear = Integer.parseInt(csv.replaceAll("[^0-9]", "").trim());
        String correctyear1 = Integer.toString(correctyear);
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].trim().equals("Hokey") || fields[i].trim().equals("Football") || fields[i].trim().equals("Basketball")) {
                sportscheck = true;
            }
            if (fields[i].trim().equals(correctyear1)) {
                yearcheck = true;
            }
            if (fields[i].trim().contains("-")) {
                recordcheck = true;
            }
            if (fields[i].trim().equals("Y") || fields[i].trim().equals("N")) {
                champcheck = true;
            }
        }
        if (!recordcheck) {
            return "Missing Record";
        }
        if (!sportscheck) {
            return "Missing Sport";
        }
        if (!yearcheck) {
            return "Missing Year";
        }
        if (!champcheck) {
            return "Missing Championship Status";

        }
        return "Missing nothing";
    }

    private static boolean validateFields(String[] fields, String csv) {
        String sport = fields[1].trim();
        int year = Integer.parseInt(fields[2].trim());
        int correctyear = Integer.parseInt(csv.replaceAll("[^0-9]", "").trim()); //replaces any character that is not a digit to blank

        if (!(sport.equals("Hokey") || sport.equals("Football") || sport.equals("Basketball"))) {

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

    private static void writeToFile(String filename, String line) throws IOException {
        boolean check = false;
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filename, true))) {
            printWriter.println(line);

            for (int i = 0; i < temp.length; i++) {

                if (filename.equals(temp[i])) {
                    check = true;


                }


            }
            if (!check) {
                temp[count1] = filename;
                count1++;

            }


            //will print to whatever file is input
        }


    }


    private static void writeToFileSyntaxError(String filename, String line, String csv, String exception) throws IOException {
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
    public static void writeSer(Team team){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(team.getSport().trim() + ".csv.ser", true);
            ObjectOutputStream outputStream = new ObjectOutputStream( new BufferedOutputStream(fileOutputStream));
            outputStream.writeObject(team);
            outputStream.close();
            fileOutputStream.close();
        }
        catch (IOException ioException){
            System.out.println(ioException.getMessage());

        }
    }

    /*
    public static void readSer(){
        try {
            String[] hardcodedNames = {"Hokey.csv.ser", "Basketball.csv.ser", "Football.csv.ser"};

            Team[] hokeyTeams;
            Team[] basketballTeams;
            Team[] footballTeams;

            for (int i = 0; i < hardcodedNames.length; i++) {
                FileInputStream fileInputStream = new FileInputStream(hardcodedNames[i]);
                ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(fileInputStream));


                Team[] teamArr = (Team[]) inputStream.readObject();

                inputStream.close();
                fileInputStream.close();

                switch (i) {
                    case 0:
                        hokeyTeams = teamArr;
                        break;
                    case 1:
                        basketballTeams = teamArr;
                        break;
                    case 2:
                        footballTeams = teamArr;
                        break;
                    default:
                        break;
                }
            }
        }
        catch (IOException ioException){
            System.out.println(ioException.getMessage());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
*/

    public static void do_part1() {


        try (BufferedReader br = new BufferedReader(new FileReader("part1 input file names (1).txt"))) {
            int number;
            number = Integer.parseInt(br.readLine());
            String[] s = new String[number];
            for (int i = 0; i < number; i++) {
                s[i] = br.readLine().trim();
            }
            for (int i = 0; i < s.length; i++) {
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
                                    missingfield = validateshortfield(fields, s[i]);
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
                                        writeToFileSyntaxError("syntax_error_file.txt", line, s[i], error);

                                    }
                                }
                            } catch (UnknownSportException e) {
                                System.out.println(e.getMessage());
                                writeToFileSyntaxError("syntax_error_file.txt", line, s[i], error);
                            } catch (TooFewFieldsException e) {
                                System.out.println(e.getMessage());
                                writeToFileSyntaxError("syntax_error_file.txt", line, s[i], "Missing field: " + missingfield);
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
        } catch (IOException e) {
            System.out.println("Error reading/writing file: " + e.getMessage());
        } finally {
            int count = 0;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] != null) {
                    count++;
                    System.out.println(count);
                }
            }
            try {
                PrintWriter pr = new PrintWriter(new FileWriter("part2 input file names.txt", true));
                pr.println(count);
                for (int i = 0; i < temp.length; i++) {
                    if (temp[i] != null) {
                        pr.println(temp[i]);
                        System.out.println(temp[i]);
                    }
                }
                pr.close();

            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }


        }


    }



    public static void do_part2() {
        int number;
        int count=0;
        int count2=0;
        Team[] teams = new Team[0];
        try (BufferedReader br = new BufferedReader(new FileReader("part2 input file names.txt"))) {



            number = Integer.parseInt(br.readLine());
            String[] s = new String[number];
            for (int i = 0; i < number; i++) {
                s[i] = br.readLine().trim();
            }


            for (int i = 0; i < s.length; i++) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(s[i]))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        count++;

                    }
                }
            }
            teams = new Team[count];

            for (int i = 0; i < s.length; i++) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(s[i]))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] fields = line.split(",");
                        teams[count2] =new Team(fields[0],fields[1],fields[2],fields[3],fields[4]);
                        count2++;




                    }


                }
            }
           for (int i =0; i<teams.length;i++){
               writeSer(teams[i]);
           }




        } catch (IOException e) {
            System.out.println("Error reading/writing file: " + e.getMessage());

        }


    }










    public static void do_part3() {
        Team[] hokeyTeams = new Team[10];
        Team[] basketballTeams = new Team[10];
        Team[] footballTeams = new Team[10];
        int currentSportIndex = 0;
        int currentRecordIndex = 0;
        Team[] currentSportArray = hokeyTeams;


        try {
            String[] hardcodedNames = {"Hokey.csv.ser", "Basketball.csv.ser", "Football.csv.ser"};
            int counterH = 0;
            int counterB = 0;
            int counterF = 0;

            for (int i = 0; i < hardcodedNames.length; i++) {
                FileInputStream fileInputStream = new FileInputStream(hardcodedNames[i]);
               ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(fileInputStream));


                        Team teamObj = (Team) inputStream.readObject();
                        switch (i) {
                            case 0:
                                hokeyTeams[counterH] = teamObj;
                                System.out.println("bb");
                                counterH++;
                                break;
                            case 1:
                                basketballTeams[counterB] = teamObj;
                                counterB++;
                                break;
                            case 2:
                                footballTeams[counterF] = teamObj;
                                counterF++;
                                break;
                            default:
                                break;
                        }



            }



            System.out.println("Printing contents of hokey array");
            for (Team element : hokeyTeams) {
                System.out.print(element);
            }
            System.out.println();
            System.out.println(counterH);





            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("----------------------------");
                System.out.println("Main Menu");
                System.out.println("----------------------------");
                System.out.println("v View the selected file: " + hardcodedNames[currentSportIndex] + " (" + currentSportArray.length + " records)");
                System.out.println("s Select a file to view");
                System.out.println("x Exit");
                System.out.println("----------------------------");
                System.out.print("Enter Your Choice: ");
                String choice = scanner.next();

                switch (choice.toLowerCase()) {
                    case "v":
                        //ViewMenu();
                        break;
                    case "s":
                        SubMenu(hokeyTeams,basketballTeams,footballTeams);
                        break;
                    case "x":
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }


        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

    public static void SubMenu(Team[] hokeyTeams, Team[] basketballTeams, Team[] footballTeams) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("------------------------------");
            System.out.println("File Sub-Menu");
            System.out.println("------------------------------");
            System.out.println("1 Hokey.csv.ser (" + hokeyTeams.length + " records)");
            System.out.println("2 Basketball.csv.ser (" + basketballTeams.length + " records)");
            System.out.println("3 Football.csv.ser (" + footballTeams.length + " records)");
            System.out.println("4 Exit");
            System.out.println("------------------------------");
            System.out.print("Enter Your Choice: ");


        }
    }


    public static void main(String[] args) {
        do_part1();
        do_part2();
        do_part3();
    }
}


