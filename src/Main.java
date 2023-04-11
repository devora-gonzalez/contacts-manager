import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        String directory = "src/data";
        String filename = "contacts.txt";

        Path dataDirectory = Path.of(directory);
        Path dataFile = Path.of(directory, filename);

        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }

        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
        }

        List<String> myData = Arrays.asList("Luis|1234567", "Jenna|7654321");

//        Files.write(dataFile, myData);

        String userName;
        String userNumber;

        List<String> currentFileInfo;




        boolean userContinue;

        do {
            currentFileInfo = Files.readAllLines(dataFile);

            System.out.println("1. View Contacts"); //display all
            System.out.println("2. Add a new contact"); // add new
            System.out.println("3. Search contact by name"); //search contact
            System.out.println("4. Delete and existing contact by name");
            System.out.println("5. Exit");
            System.out.println();
            System.out.print("Enter an option: ");
            int userChoice = sc.nextInt();
            sc.nextLine(); // clear buffer


            if (userChoice == 1) {
                System.out.println("Here's all the contacts in our file:");
                System.out.printf("%-15s | %-8s | %n", "Name", "Phone Number");
                System.out.println("------------------------------");

                for (String e : currentFileInfo) {
                    String[] strings = e.split("\\|");
                    String fmt = "%-15s | %-12s |%n";
                    System.out.printf(fmt, strings[0], strings[1]);
                }
            } else if (userChoice == 2) {
                System.out.print("Please enter a name: ");
                userName = sc.nextLine();

                System.out.print("Enter phone number: ");
                userNumber = sc.nextLine();

                Files.write(dataFile, List.of(userName.trim() + "|" + userNumber.trim()), StandardOpenOption.APPEND);

                System.out.println("Contact successfully added!!");


            } else if (userChoice == 3) {
                System.out.print("Enter name to search: ");

                String userSearch = sc.nextLine();

                if (currentFileInfo.toString().toUpperCase().contains(userSearch.toUpperCase())) {
                    for (String e : currentFileInfo) {
                        String[] strings = e.split("\\|");
                        if (strings[0].equalsIgnoreCase(userSearch)) {
                            System.out.printf("%s | %s\n", strings[0], strings[1]);
                        }
                    }
                } else {
                    System.out.println("Contact does not exist.");
                }
            } else if (userChoice == 4) {
                System.out.println("Enter a name to delete contact information: ");
                String userDelete = sc.nextLine();

                if (currentFileInfo.toString().toUpperCase().contains(userDelete.toUpperCase())) {
                    for (int i = 0; i < currentFileInfo.size(); i++) {
                        String currentIndex = currentFileInfo.get(i);
                        if (currentIndex.toUpperCase().contains(userDelete.toUpperCase())) {
                            currentFileInfo.remove(i);
                            Files.write(dataFile, currentFileInfo);
                        }
                    }
                    System.out.println("Contact deleted.");
                } else {
                    System.out.println("Contact does not exist.");
                }
            } else if (userChoice == 5) {
                System.out.println("Exiting");
                break;
            } else {
                System.out.println("not valid option");
            }

            System.out.println("Would you like to return to menu? [Y/N]");
            userContinue = sc.next().equalsIgnoreCase("y");
        } while (userContinue);

    }
}
