package ca.ciccc.java.model;

import ca.ciccc.java.Exceptions.InvalidCustomerIDException;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author paula on 20/04/18.
 */
public class Customer extends Person {
    private String customerID; // - e.g. AB123 (2 uppercase letters, 3 digits)
    private boolean active;

    public Customer(){
    }

    public Customer(Integer id, String firstName, String lastName, LocalDate dateOfBirth, String customerID, boolean active) throws InvalidCustomerIDException {
        super(id, firstName, lastName, dateOfBirth);
        this.setCustomerID(customerID);
        this.setActive(active);
    }

    public Customer(String firstName, String lastName, LocalDate dateOfBirth, String customerID, boolean active) throws InvalidCustomerIDException {
        super(firstName, lastName, dateOfBirth);
        this.setCustomerID(customerID);
        this.setActive(active);
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) throws InvalidCustomerIDException {
        int countLetters = countMatches(customerID, "[A-Za-z]");
        int countNumbers = countMatches(customerID, "[0-9]");
        customerID = customerID.toUpperCase();

        if(customerID.length() != 5 || countLetters != 2 || countNumbers != 3){
            throw new InvalidCustomerIDException("Customer ID need to have 2 letters and 3 numbers. Ex: AB123, 456DC, A12D5,...");
        }

        this.customerID = customerID;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private int countMatches(String customerID, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(customerID);
        int count = 0;
        while (m.find()) {
            count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return String.format("%20s %20s %15s %20s %15s",
                getFirstName(), getLastName(), getDateOfBirth(), getCustomerID(), isActive());
    }
}
