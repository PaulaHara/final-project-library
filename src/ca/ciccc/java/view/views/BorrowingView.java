package ca.ciccc.java.view.views;

import ca.ciccc.java.controller.LibraryController;

/**
 * @author paula on 23/04/18.
 */
public class BorrowingView extends Views {

    /**
     * View to borrow a book (add book in borrowing or create a new borrowing)
     * @param controller
     */
    @Override
    public void addView(LibraryController controller) {
        outputReader.printOutput("Insert customer ID:");
        String customerID = inputReader.getStringInput().toUpperCase();
        outputReader.printOutput("Choose a book: (insert the id of the book)");
        controller.printBooks();
        int bookId = inputReader.getIntInput();

        outputReader.printOutput(controller.borrowBook(customerID, bookId));
        outputReader.printOutput("");
    }

    /**
     * View to return a book (remove a book of borrowing)
     * @param controller
     */
    @Override
    public void removeView(LibraryController controller) {
        outputReader.printOutput("Insert customer id:");
        String customerID = inputReader.getStringInput().toUpperCase();

        if(controller.customerIsBorrowingBooks(customerID)) {
            outputReader.printOutput("Choose a book: (insert the id of the book)");
            controller.printBooksBorrowed(customerID);
            int bookId = inputReader.getIntInput();

            outputReader.printOutput(controller.returnBook(customerID, bookId));
        }else{
            outputReader.printOutput("This customer is not borrowing any book!");
        }
        outputReader.printOutput("");
    }
}
