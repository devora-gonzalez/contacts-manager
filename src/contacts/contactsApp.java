package contacts;

import java.io.IOException;

public class contactsApp {

    public static void main(String[] args) throws IOException {
        Contact contact = new Contact();
        contact.fileCheck();
        boolean userBool = true;
        do {
            int menuChoice = contact.options();
            switch (menuChoice) {
                case 1 -> contact.displayAll();
                case 2 -> contact.addContact();
                case 3 -> contact.search();
                case 4 -> contact.destroy();
                case 5 -> {
                    userBool = false;
                    System.out.println("Closing contacts manager");
                }
                default -> System.out.println("Invalid Option");
            }
        } while (userBool);
    }
}
