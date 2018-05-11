package ca.ciccc.java.view.views;

import ca.ciccc.java.controller.LibraryController;
import ca.ciccc.java.model.Genre;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author paula on 23/04/18.
 */
public class BookView extends Views {
    /**
     * View to create a new book and an author, if it doesn't exist
     */
    @Override
    public void addView(LibraryController controller) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        outputReader.printOutput("Give all the required information about the book:");
        outputReader.printOutputInLine("Title: (don't use spaces, ex: Lord of the Rings -> LordOfTheRings)");
        String title = inputReader.getStringInput();
        outputReader.printOutputInLine("Year Published: ");
        int year = inputReader.getIntInput();
        outputReader.printOutputInLine("Edition: ");
        int edition = inputReader.getIntInput();
        outputReader.printOutputInLine("ISBN: ");
        String isbn = inputReader.getStringInput();

        outputReader.printOutput("Genre: (insert a number)");
        outputReader.printOutput("1) Fiction 2) Non_fiction 3) Sci-Fi 4) Biography 5) History 6) Children");
        int genre = inputReader.getIntInput();

        outputReader.printOutputInLine("Number of Copies: ");
        int numCopies = inputReader.getIntInput();
        outputReader.printOutputInLine("Copies Available: ");
        int available = inputReader.getIntInput();

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

            outputReader.printOutput(controller.addBook(title, firstName, lastName, dateBirth, pseudonym, specialty,
                    year, edition, isbn, genre, numCopies, available));
        }else{
            if(controller.verifyIfBookExist(firstName, lastName, edition)){
                outputReader.printOutput("Error: There is another book equals to this.\n" +
                        "Books with same author and edition are considered equals!");
            }else {
                outputReader.printOutput(controller.addBook(title, firstName, lastName, year, edition, isbn, genre,
                        numCopies, available));
            }
        }
        outputReader.printOutput("");
    }

    /**
     * View to remove a book
     */
    @Override
    public void removeView(LibraryController controller){
        outputReader.printOutput("Give all the required information about the book:");
        outputReader.printOutputInLine("Edition: ");
        int edition = inputReader.getIntInput();
        outputReader.printOutputInLine("Author's first name: ");
        String firstName = inputReader.getStringInput();
        outputReader.printOutputInLine("Author's last name: ");
        String lastName = inputReader.getStringInput();

        outputReader.printOutput(controller.removeBook(edition, firstName, lastName));
        outputReader.printOutput("");
    }
}
