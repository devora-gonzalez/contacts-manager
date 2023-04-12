package contacts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class Contact {

    private String name;

    private String number;

    Scanner scanner = new Scanner(System.in);

    public Contact() {
    }

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    //check if directory/file exists

    public void fileCheck() throws IOException {
        String directory = "src/contacts/data";
        String filename = "contacts.txt";

        Path dataDirectory = Path.of(directory);
        Path dataFile = Path.of(directory, filename);

        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }

        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
        }

    }

    // read file

    public Path path() {
        return Path.of("src/contacts/data", "contacts.txt");
    }

    public List<String> fileData() throws IOException {
        return Files.readAllLines(this.path());
    }


    //displayOptions

    public int options(){

        System.out.println("\n1. View Contacts");
        System.out.println("2. Add a new contact");
        System.out.println("3. Search contact by name");
        System.out.println("4. Delete and existing contact by name");
        System.out.println("5. Exit");
        System.out.println();

        System.out.print("Enter an option: ");
        int userChoose = scanner.nextInt();
        System.out.println();

        return userChoose;
    }


    //displayAll

    public void displayAll() throws IOException {
        scanner.nextLine();
        List<String> data = this.fileData();

        data.sort(null);

        for (String e : data) {
            String[] strings = e.split("\\|");
            String fmt = "%-20s | %-12s |%n";
            String formatedNum;
            if (strings[1].length() >= 10) {
                formatedNum = strings[1].replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
            } else {
                formatedNum = strings[1].replaceFirst("(\\d{3})(\\d+)", "$1-$2");
            }

            System.out.printf(fmt, strings[0], formatedNum);
        }
    }

    //add

    public void addContact() throws IOException {
        scanner.nextLine();

        List<String> currentFileInfo = this.fileData();

        System.out.print("Enter first and last name: ");
        this.name = scanner.nextLine();

        if (currentFileInfo.toString().toUpperCase().contains(this.name.toUpperCase())) {
            System.out.println("This contact already exists, do you want to overwrite it? [Y/N]");
            boolean userConfirm = scanner.nextLine().equalsIgnoreCase("y");

            if (userConfirm) {
                System.out.print("Enter phone number: ");
                this.number = scanner.nextLine();
                for (int i = 0; i < currentFileInfo.size(); i++) {
                    String currentIndex = currentFileInfo.get(i);
                    if (currentIndex.toUpperCase().contains(this.name.toUpperCase())) {
                        currentFileInfo.set(i, this.name + "|" + this.number);
                        Files.write(this.path(), currentFileInfo);
                    }
                }
                System.out.println("Contact successfully updated!");

            } else {

                System.out.print("Enter phone number: ");
                this.number = scanner.nextLine();

                Files.write(this.path(), List.of(this.name.trim() + "|" + this.number.trim()), StandardOpenOption.APPEND);
            }

            System.out.println("Contact successfully added!");
        } else {
            System.out.print("Enter phone number: ");
            this.number = scanner.nextLine();

            Files.write(this.path(), List.of(this.name.trim() + "|" + this.number.trim()), StandardOpenOption.APPEND);

            System.out.println("Contact successfully added!");
        }
    }


    //search

    public void search() throws IOException {
        scanner.nextLine();

        List<String> currentFileInfo = this.fileData();


        System.out.print("Enter name to search: ");

        String userSearch = scanner.nextLine();

        if (currentFileInfo.toString().toUpperCase().contains(userSearch.toUpperCase())) {
            for (int i = 0; i < currentFileInfo.size(); i++) {
                String currentIndex = currentFileInfo.get(i);
                if (currentIndex.toUpperCase().contains(userSearch.toUpperCase())) {
                    String[] strings = currentIndex.split("\\|");
                    String fmt = "%-10s | %-12s |%n";
                    String formattedNum = strings[1];
                    if (strings[1].length() >= 10) {
                        formattedNum = strings[1].replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
                    } else {
                        formattedNum = strings[1].replaceFirst("(\\d{3})(\\d+)", "$1-$2");
                    }
                    System.out.printf(fmt, strings[0], formattedNum);
                }
            }
        } else {
            System.out.println("Contact does not exist.");
        }

    }

    //destroy

    public void destroy() throws IOException {
        scanner.nextLine();

        List<String> currentFileInfo = this.fileData();

        System.out.println("Enter a name to delete contact information: ");
        String userDelete = scanner.nextLine();

        if (currentFileInfo.toString().toUpperCase().contains(userDelete.toUpperCase())) {
            for (int i = 0; i < currentFileInfo.size(); i++) {
                String currentIndex = currentFileInfo.get(i);
                if (currentIndex.toUpperCase().contains(userDelete.toUpperCase())) {
                    currentFileInfo.remove(i);
                    Files.write(this.path(), currentFileInfo);
                }
            }
            System.out.println("Contact deleted.");
        } else {
            System.out.println("Contact does not exist.");
        }

    }
}
