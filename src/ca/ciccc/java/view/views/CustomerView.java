package ca.ciccc.java.view.views;

import ca.ciccc.java.Exceptions.InvalidCustomerIDException;
import ca.ciccc.java.controller.LibraryController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author paula on 23/04/18.
 */
public class CustomerView extends Views {
    /**
     * View to create a new customer
     */
    @Override
    public void addView(LibraryController controller) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        outputReader.printOutput("Give a unique Customer ID with 2 uppercase letters and 3 digits " +
                "(ex: AB123, A12B3, 123AB,...):");
        outputReader.printOutputInLine("Customer ID: ");
        String customerID = inputReader.getStringInput();

        if(controller.verifyIfCustomerExist(customerID)){
            this.outputReader.printOutput("Error: There is a customer registered with same Customer ID!");
        }else{
            outputReader.printOutput("Give all the requested information:");
            outputReader.printOutputInLine("First Name: ");
            String firstName = inputReader.getStringInput();
            outputReader.printOutputInLine("Last Name: ");
            String lastName = inputReader.getStringInput();
            outputReader.printOutputInLine("Date of Birth: (MM/dd/yyyy)");
            LocalDate dateBirth = LocalDate.parse(inputReader.getStringInput(), formatter);

            outputReader.printOutput(controller.addCustomer(firstName, lastName, dateBirth, customerID, true));
        }
        outputReader.printOutput("");
    }

    /**
     * View to remove a customer
     * @param controller
     */
    @Override
    public void removeView(LibraryController controller) {
        outputReader.printOutputInLine("Give the customer ID:");
        String customerID = inputReader.getStringInput();

        outputReader.printOutput(controller.removeCustomer(customerID));
        outputReader.printOutput("");
    }

    /**
     * View to inactivate a customer
     * @param controller
     */
    public void inactiveCustomerView(LibraryController controller) {
        outputReader.printOutputInLine("Give the customer ID:");
        String customerID = inputReader.getStringInput();

        outputReader.printOutput(controller.inactiveCustomer(customerID));
        outputReader.printOutput("");
    }
}
