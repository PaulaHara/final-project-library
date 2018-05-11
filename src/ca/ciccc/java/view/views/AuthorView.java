package ca.ciccc.java.view.views;

import ca.ciccc.java.controller.LibraryController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author paula on 23/04/18.
 */
public class AuthorView extends Views {

    @Override
    public void addView(LibraryController controller) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        outputReader.printOutput("Give author's name: ");
        outputReader.printOutputInLine("First Name: ");
        String firstName = inputReader.getStringInput();
        outputReader.printOutputInLine("Last Name: ");
        String lastName = inputReader.getStringInput();

        if(!controller.verifyIfAuthorExist(firstName, lastName)){
            outputReader.printOutput("Author not registered. Give all the requested information:");
            outputReader.printOutputInLine("Date of Birth: (MM/dd/yyyy)");
            LocalDate dateBirth = LocalDate.parse(inputReader.getStringInput(), formatter);
            outputReader.printOutputInLine("Pseudonym: ");
            String pseudonym = inputReader.getStringInput();
            outputReader.printOutput("Specialty: (insert a number)");
            outputReader.printOutput("1) Fiction 2) Non_fiction 3) Sci-Fi 4) Biography 5) History 6) Children");
            int specialty = inputReader.getIntInput();

            outputReader.printOutput(controller.addAuthor(firstName, lastName, dateBirth, pseudonym, specialty));
        }else{
            this.outputReader.printOutput("Error: There is an Author registered with same name!");
        }
        outputReader.printOutput("");
    }

    @Override
    public void removeView(LibraryController controller) {
        outputReader.printOutput("Give author's name: ");
        outputReader.printOutputInLine("First Name: ");
        String firstName = inputReader.getStringInput();
        outputReader.printOutputInLine("Last Name: ");
        String lastName = inputReader.getStringInput();

        outputReader.printOutput(controller.removeAuthor(firstName, lastName));
        outputReader.printOutput("");
    }
}
