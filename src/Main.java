import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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

        String userName;
        String userNumber;

        List<String> currentFileInfo;


        boolean userContinue = true;

        do {
            currentFileInfo = Files.readAllLines(dataFile);
            // sorted contacts by alphabetical (lexicographical) order
            currentFileInfo.sort(null);

            System.out.println("\n1. View Contacts");
            System.out.println("2. Add a new contact");
            System.out.println("3. Search contact by name");
            System.out.println("4. Delete and existing contact by name");
            System.out.println("5. Exit");
            System.out.println();

            System.out.print("Enter an option: ");
            int userChoice = sc.nextInt();
            System.out.println();
            sc.nextLine(); // clear buffer


            if (userChoice == 1) {

                System.out.println("Here's all the contacts in our file:");
                System.out.printf("%-20s | %-8s | %n", "Name", "Phone Number");
                System.out.println("---------------------------------------");

                for (String e : currentFileInfo) {
                    String[] strings = e.split("\\|");
                    String fmt = "%-20s | %-12s |%n";
                    String formatedNum = strings[1];
                    if (strings[1].length() >= 10) {
                        formatedNum = strings[1].replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
                    } else {
                        formatedNum = strings[1].replaceFirst("(\\d{3})(\\d+)", "$1-$2");
                    }

                    System.out.printf(fmt, strings[0], formatedNum);
                }
            } else if (userChoice == 2) {

                System.out.print("Enter first and last name: ");
                userName = sc.nextLine();

                if (currentFileInfo.toString().toUpperCase().contains(userName.toUpperCase())) {
                    System.out.println("This contact already exists, do you want to overwrite it? [Y/N]");
                    boolean userConfirm = sc.nextLine().equalsIgnoreCase("y");

                    if (userConfirm) {
                        System.out.print("Enter phone number: ");
                        userNumber = sc.nextLine();
                        for (int i = 0; i < currentFileInfo.size(); i++) {
                            String currentIndex = currentFileInfo.get(i);
                            if (currentIndex.toUpperCase().contains(userName.toUpperCase())) {
                                currentFileInfo.set(i, userName + "|" + userNumber);
                                Files.write(dataFile, currentFileInfo);
                            }
                        }
                        System.out.println("Contact successfully updated!");

                    } else {

                        System.out.print("Enter phone number: ");
                        userNumber = sc.nextLine();

                        Files.write(dataFile, List.of(userName.trim() + "|" + userNumber.trim()), StandardOpenOption.APPEND);
                    }

                    System.out.println("Contact successfully added!");
                } else {
                    System.out.print("Enter phone number: ");
                    userNumber = sc.nextLine();

                    Files.write(dataFile, List.of(userName.trim() + "|" + userNumber.trim()), StandardOpenOption.APPEND);

                    System.out.println("Contact successfully added!");
                }

            } else if (userChoice == 3) {

                System.out.print("Enter name to search: ");

                String userSearch = sc.nextLine();

                if (currentFileInfo.toString().toUpperCase().contains(userSearch.toUpperCase())) {
                    for (int i = 0; i < currentFileInfo.size(); i++) {
                        String currentIndex = currentFileInfo.get(i);
                        if (currentIndex.toUpperCase().contains(userSearch.toUpperCase())) {
                            String[] strings = currentIndex.split("\\|");
                            String fmt = "%-10s | %-12s |%n";
                            String formatedNum = strings[1];
                            if (strings[1].length() >= 10) {
                                formatedNum = strings[1].replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
                            } else {
                                formatedNum = strings[1].replaceFirst("(\\d{3})(\\d+)", "$1-$2");
                            }
                            System.out.printf(fmt, strings[0], formatedNum);
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
                System.out.println("Closing contacts manager");
                userContinue = false;

            } else {
                System.out.println("not valid option");
            }
        } while (userContinue);
    }
}
