package ca.ciccc.java.view;

import ca.ciccc.java.controller.LibraryController;
import ca.ciccc.java.view.readers.InputReader;
import ca.ciccc.java.view.readers.OutputReader;
import ca.ciccc.java.view.views.AuthorView;
import ca.ciccc.java.view.views.BookView;
import ca.ciccc.java.view.views.BorrowingView;
import ca.ciccc.java.view.views.CustomerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author paula on 23/04/18.
 */
public class LibraryView {
    private InputReader inputReader = new InputReader();
    private OutputReader outputReader = new OutputReader();
    private AuthorView authorView = new AuthorView();
    private CustomerView customerView = new CustomerView();
    private BookView bookView = new BookView();
    private BorrowingView borrowingView = new BorrowingView();
    private LibraryController controller;

    /**
     * Main view of the Library
     * @param controller
     */
    public void view(LibraryController controller) {
        this.controller = controller;
        int option;
        boolean finish = true;

        while (finish) {
            outputReader.printOutput("Choose an option:" +
                    "\n1) View all books   2) View all customers   3) View all borrowings   4) View all authors" +
                    "\n5) Add a book       6) Borrow a book        7) Return a book         8) Remove a book" +
                    "\n9) Add a customer   10) Remove a customer   11) Inactive a customer" +
                    "\n12) Add an author   13) Remove an author" +
                    "\n14) Exit");
            option = inputReader.getIntInput();

            switch (option){
                case 1:
                    this.printBooks();
                    break;
                case 2:
                    this.controller.printCustomers();
                    break;
                case 3:
                    this.controller.printBorrowings();
                    break;
                case 4:
                    this.controller.printAuthors();
                    break;
                case 5:
                    this.bookView.addView(controller);
                    break;
                case 6:
                    this.borrowingView.addView(controller);
                    break;
                case 7:
                    this.borrowingView.removeView(controller);
                    break;
                case 8:
                    this.bookView.removeView(controller);
                    break;
                case 9:
                    this.customerView.addView(controller);
                    break;
                case 10:
                    this.customerView.removeView(controller);
                    break;
                case 11:
                    this.customerView.inactiveCustomerView(controller);
                    break;
                case 12:
                    this.authorView.addView(controller);
                    break;
                case 13:
                    this.authorView.removeView(controller);
                    break;
                case 14:
                    finish = false;
                    break;
            }
        }
    }

    /**
     * Print books sorted by edition or year
     */
    private void printBooks(){
        outputReader.printOutput("11) Sort by edition\n12) Sort by year");
        int opt = inputReader.getIntInput();

        switch (opt){
            case 11:
                controller.printBooksSortedByEdition();
                break;
            case 12:
                controller.printBooksSortedByYear();
                break;
            default:
                System.out.println("This option don't exist...returning to the main menu.");
                break;
        }
    }

}
