package ca.ciccc.java.model;

import java.util.*;

/**
 * @author paula on 20/04/18.
 */
public class Library {
    private String libraryName;
    private Set<Book> books = new HashSet<Book>();
    private List<Customer> customers = new ArrayList<>();
    private HashMap<String, Borrowing> borrowings = new HashMap<>();

    /**
     * Constructor for Library
     * @param libraryName
     * @param books
     * @param customers
     * @param borrowings
     */
    public Library(String libraryName, Set<Book> books, List<Customer> customers,
                   HashMap<String, Borrowing> borrowings){
        this.setLibraryName(libraryName);
        this.setBooks(books);
        this.setCustomers(customers);
        this.setBorrowings(borrowings);
    }

    /**
     * Get the name of the library
     * @return libraryName
     */
    public String getLibraryName() {
        return libraryName;
    }

    /**
     * Set the name of the library
     * @param libraryName
     */
    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    /**
     * Get all books of library
     * @return books
     */
    public Set<Book> getBooks() {
        return books;
    }

    /**
     * Set books of library
     * @param books
     */
    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    /**
     * Get all the customer of library
     * @return customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Set the customer of library
     * @param customers
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Get all the information about the books borrowed
     * @return
     */
    public HashMap<String, Borrowing> getBorrowings() {
        return borrowings;
    }

    /**
     * Set the informartion about the book borrowed
     * @param borrowings
     */
    public void setBorrowings(HashMap<String, Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    /**
     * Return all the information about this library
     * @return string
     */
    @Override
    public String toString() {
        return "Library [Name: "+getLibraryName()+", Has "+getBooks().size()+" books, " +
                "Has "+getCustomers().size()+" customers, Borrowed for "+getBorrowings().size()+" different customers]";
    }
}
