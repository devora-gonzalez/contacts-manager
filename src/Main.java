import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

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

        Files.write(dataFile, myData);

        String userName = "Kenneth";
        String userNumber = "23456789";

        Files.write(dataFile, (userName+"|"+userNumber).getBytes(), StandardOpenOption.APPEND);

        System.out.println("Here's all the information in our file");

        List<String> currentFileInfo = Files.readAllLines(dataFile);

        for (String e : currentFileInfo){
          String[] strings = e.split("\\|");
            System.out.printf("%s | %s\n", strings[0], strings[1]);
        }

        Scanner sc = new Scanner (System.in);
        System.out.println("Please enter a name");
        userName = sc.nextLine();
        System.out.println("Enter phone number");
        userNumber = sc.nextLine();
        System.out.println("User added successfully");

        Files.write(dataFile, ("\n" + userName.trim() +"|"+ userNumber.trim()).getBytes(), StandardOpenOption.APPEND);
    }
}
