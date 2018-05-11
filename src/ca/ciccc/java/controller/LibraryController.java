package ca.ciccc.java.controller;

import ca.ciccc.java.Exceptions.InvalidCustomerIDException;
import ca.ciccc.java.controller.service.AuthorService;
import ca.ciccc.java.db.ConnectionFactory;
import ca.ciccc.java.model.*;
import ca.ciccc.java.controller.service.BookService;
import ca.ciccc.java.controller.service.BorrowingService;
import ca.ciccc.java.controller.service.CustomerService;
import ca.ciccc.java.view.LibraryView;
import ca.ciccc.java.view.readers.OutputReader;

import java.time.LocalDate;
import java.util.*;

/**
 * @author paula on 23/04/18.
 */
public class LibraryController {
    private static LibraryController controller;
    private static LibraryView libraryView;
    private static BookService bookService;
    private static AuthorService authorService;
    private static CustomerService customerService;
    private static BorrowingService borrowingService;
    private Library library;

    private OutputReader outputReader = new OutputReader();

    public static void main(String[] args) {
        ConnectionFactory.createTables();

        controller = new LibraryController();
        libraryView = new LibraryView();
        bookService = new BookService();
        authorService = new AuthorService();
        customerService = new CustomerService();
        borrowingService = new BorrowingService();

        controller.createLibrary("Paula's Library", bookService.getAllBooks(),
                customerService.getAllCustomers(), borrowingService.getAllBorrowings());

        controller.view();

        ConnectionFactory.disconnectFromDB();
    }

    /**
     * Initialize a Library
     * @param libraryName
     * @param books
     * @param customers
     * @param borrowings
     */
    public void createLibrary(String libraryName, Set<Book> books, List<Customer> customers,
                              HashMap<String, Borrowing> borrowings){
        this.library = new Library(libraryName, books, customers, borrowings);
    }

    public void updateLibrary(Set<Book> books, List<Customer> customers,
                              HashMap<String, Borrowing> borrowings){
        this.library.setBooks(books);
        this.library.setCustomers(customers);
        this.library.setBorrowings(borrowings);
    }

    /**
     * View for Library
     */
    public void view(){
        libraryView.view(controller);
    }

    /**
     * Create author and book
     * @param title
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param pseudonym
     * @param specialtyCode
     * @param yearPublished
     * @param edition
     * @param isbn
     * @param genreCode
     * @param numberOfCopies
     * @param copiesAvailable
     */
    public String addBook(String title, String firstName, String lastName, LocalDate dateOfBirth, String pseudonym,
                        int specialtyCode, int yearPublished, int edition, String isbn,
                        int genreCode, int numberOfCopies, int copiesAvailable){
        this.addAuthor(firstName, lastName, dateOfBirth, pseudonym, specialtyCode);
        Author author = authorService.getAuthorByName(firstName, lastName);

        bookService.createBook(new Book(title, author.getId(), yearPublished, edition, isbn,
                getGenre(genreCode), numberOfCopies, copiesAvailable));

        this.updateLibrary(bookService.getAllBooks(), library.getCustomers(), library.getBorrowings());

        return "Book add successfully";
    }

    /**
     * Search author and create book
     * @param title
     * @param firstName
     * @param lastName
     * @param yearPublished
     * @param edition
     * @param isbn
     * @param genreCode
     * @param numberOfCopies
     * @param copiesAvailable
     */
    public String addBook(String title, String firstName, String lastName, int yearPublished, int edition, String isbn,
                        int genreCode, int numberOfCopies, int copiesAvailable){
        Author author = authorService.getAuthorByName(firstName, lastName);
        bookService.createBook(new Book(title, author.getId(), yearPublished, edition, isbn,
                getGenre(genreCode), numberOfCopies, copiesAvailable));

        this.updateLibrary(bookService.getAllBooks(), library.getCustomers(), library.getBorrowings());

        return "Book add successfully";
    }

    /**
     * code: 1) Fiction 2) Non_fiction 3) Sci-Fi 4) Biography 5) History 6) Children
     *
     * @param genreCode
     * @return
     */
    private Genre getGenre(int genreCode) {
        switch(genreCode){
            case 1:
                return Genre.FICTION;
            case 2:
                return Genre.NON_FICTION;
            case 3:
                return Genre.SCI_FI;
            case 4:
                return Genre.BIOGRAPHY;
            case 5:
                return Genre.HISTORY;
            case 6:
                return Genre.CHILDREN;
            default:
                return Genre.FICTION;
        }
    }

    /**
     * Find book by title and author's name, then delete if it is found
     * @param edition
     * @param authorFirstName
     * @param authorLastName
     */
    public String removeBook(int edition, String authorFirstName, String authorLastName){
        Author author = authorService.getAuthorByName(authorFirstName, authorLastName);
        Book book = bookService.getBookByAuthorAndEdition(author.getId(), edition);
        if(book != null){
            bookService.deleteBook(book.getId());

            this.updateLibrary(bookService.getAllBooks(), library.getCustomers(), library.getBorrowings());

            return "Book removed successfully!";
        }else{
            return "Book not found! It may already be deleted.";
        }
    }

    public boolean verifyIfBookExist(String firstName, String lastName, int edition){
        Author author = authorService.getAuthorByName(firstName, lastName);
        Book book = bookService.getBookByAuthorAndEdition(author.getId(), edition);
        return book != null ? true : false;
    }

    /**
     * Create new Author
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param pseudonym
     * @param specialtyCode
     */
    public String addAuthor(String firstName, String lastName, LocalDate dateOfBirth, String pseudonym,
                             int specialtyCode){
        authorService.createAuthor(new Author(firstName, lastName, dateOfBirth, pseudonym, getGenre(specialtyCode)));

        return "Author add successfully";
    }

    /**
     * Remove a customer
     * @param firstName
     * @param lastName
     */
    public String removeAuthor(String firstName, String lastName){
        Author author = this.authorService.getAuthorByName(firstName, lastName);
        if(author != null) {
            this.authorService.deleteAuthor(author.getId());
            return "Author removed.";
        }
        return "Author not found! It may already be deleted.";
    }

    /**
     * Verify if an author already exist int the DB
     * @param firstName
     * @param lastName
     * @return true if exist, false if not
     */
    public boolean verifyIfAuthorExist(String firstName, String lastName){
        Author author = authorService.getAuthorByName(firstName, lastName);
        return author != null ? true : false;
    }

    /**
     * Create a new Customer
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param customerID
     * @param active
     */
    public String addCustomer(String firstName, String lastName, LocalDate dateOfBirth, String customerID,
                            boolean active){
        Customer customer;
        try {
            customer = new Customer(firstName, lastName, dateOfBirth, customerID, active);
        } catch (InvalidCustomerIDException e) {
            return "Error adding Customer: "+e.getMessage();
        }
        customerService.createCustomer(customer);

        this.updateLibrary(library.getBooks(), customerService.getAllCustomers(), library.getBorrowings());

        return "Customer add successfully!";
    }

    /**
     * Remove a customer
     * @param customerID
     */
    public String removeCustomer(String customerID){
        Customer customer = this.customerService.getCustomerByCustomerID(customerID);
        if(customer != null) {
            this.customerService.deleteCustomer(customer.getId());

            this.updateLibrary(library.getBooks(), customerService.getAllCustomers(), library.getBorrowings());

            return "Customer removed.";
        }
        return "Customer not found! It may already be deleted.";
    }

    /**
     * Verify if a customer already exist int the DB
     * @param customerID
     * @return true if exist, false if not
     */
    public boolean verifyIfCustomerExist(String customerID){
        Customer customer = this.customerService.getCustomerByCustomerID(customerID);
        return customer != null ? true : false;
    }

    /**
     * Activate a customer if he borrowed a book and was inactivated
     * @param customerID
     */
    public String activeCustomer(String customerID){
        Customer customer = customerService.getCustomerByCustomerID(customerID);
        if(customer != null){
            if(!customer.isActive()){
                customer.setActive(true);
                customerService.updateCustomer(customer);

                this.updateLibrary(library.getBooks(), customerService.getAllCustomers(), library.getBorrowings());
                return "Customer was activated.";
            }
        }
        return "Customer not found!";
    }

    /**
     * Inactivate a customer if there's no books borrowed or if borrowing finished
     * @param customerID
     */
    public String inactiveCustomer(String customerID){
        Customer customer = customerService.getCustomerByCustomerID(customerID);
        if(customer != null){
            Borrowing borrowing = borrowingService.getBorrowingByCustomer_id(customer.getId());
            if(borrowing == null || (borrowing != null && borrowing.isFinished())){
                customer.setActive(false);
                customerService.updateCustomer(customer);

                this.updateLibrary(library.getBooks(), customerService.getAllCustomers(), library.getBorrowings());

                return "Customer was inactivated.";
            }
            return "This customer can't be inactivated, he/she has books borrowed.";
        }
        return "Customer not found!";
    }

    /**
     * Update an existing borrowing or create a new one if it doesn't exist
     * @param customerID
     * @param bookId
     * @return String with result of the operation
     */
    public String borrowBook(String customerID, int bookId){
        Customer customer = customerService.getCustomerByCustomerID(customerID);
        Book book = bookService.getBookById(bookId);
        Borrowing borrowing = borrowingService.getBorrowingByCustomer_id(customer.getId());

        String msg;

        if(book == null) {
            return "Error: There is no book with id "+bookId+".";
        }

        if(book.getCopiesAvailable() == 0){
            return "Error: The book "+book.getTitle()+" doesn't have any copy available to be borrowed.";
        }

        if(borrowing != null){
            if(bookService.getBorrowedBooks(borrowing.getId()).size() == 5){
                return "Error: Customer "+customer.getFirstName()+", ID "+customerID+", has 5 books borrowed.\n" +
                        "Each customer can't take more than 5 books.";
            }
            msg = borrowingService.borrowBook(book.getId(), borrowing.getId());

            book.setCopiesAvailable(book.getCopiesAvailable() - 1);
            bookService.updateBook(book);

            borrowing = this.setBorrowingData(borrowing, customer.getId());
            borrowingService.updateBorrowing(borrowing);
        }else {
            Set<Integer> booksId = new HashSet<>();
            booksId.add(bookId);

            borrowing = this.setBorrowingData(new Borrowing(), customer.getId());
            borrowing.setBooksId(booksId);

            msg = borrowingService.createBorrowing(borrowing);
        }

        // Activate customer, if he was inactivated before
        this.activeCustomer(customerID);

        this.updateLibrary(library.getBooks(), library.getCustomers(), borrowingService.getAllBorrowings());

        return msg+"\n"+customer.getFirstName()+" has "
                +borrowingService.getBooksBorrowed(library.getBorrowings().get(customer.getCustomerID()).getId()).size()
                +" book(s) borrowed.";
    }

    /**
     * Set borrowing with the data passed
     * @param borrowing
     * @param customerId
     * @return borrowing
     */
    private Borrowing setBorrowingData(Borrowing borrowing, int customerId){
        borrowing.setCustomerId(customerId);
        borrowing.setBorrowedDate(LocalDate.now());
        borrowing.setFinished(false);
        return borrowing;
    }

    /**
     * Return a book using customerID and bookId
     * @param customerID
     * @param bookId
     * @return String with the result of the operation
     */
    public String returnBook(String customerID, int bookId) {
        Customer customer = customerService.getCustomerByCustomerID(customerID);
        Book book = bookService.getBookById(bookId);
        Borrowing borrowing = borrowingService.getBorrowingByCustomer_id(customer.getId());

        if(customer == null){
            return "Can't return book!\nCustomer with ID "+customerID+" don't exist.";
        }else if(book == null){
            return "Can't return book!\nBook with id "+bookId+" don't exist.";
        }else if(borrowing == null){
            return "Can't return book!\nBorrowing for customer "+customer.getFirstName()+" don't exist.";
        }

        borrowingService.returnBook(bookId, borrowing.getId());

        // If there is no book borrowed for a customer, borrowing has finished
        if (borrowingService.getBooksBorrowed(borrowing.getId()).size() == 0) {
            borrowing.setFinished(true);
        }
        borrowing.setReturnDate(LocalDate.now());
        borrowingService.updateBorrowing(borrowing);

        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        bookService.updateBook(book);

        this.updateLibrary(library.getBooks(), library.getCustomers(), borrowingService.getAllBorrowings());

        return "Book returned.\nCustomer "+customer.getFirstName()+" "+customer.getLastName()+" has" +
                borrowing.getBooksId().size()+" book(s) borrowed.";
    }

    /**
     * Print books borrowed showing ids
     */
    public void printBooksBorrowed(String customerID){
        if(library.getBorrowings().get(customerID) != null) {
            List<Book> books = bookService.getBorrowedBooks(library.getBorrowings().get(customerID).getId());

            String formatString = "%5s %15s %15s %20s %10s %10s %15s %15s %15s";
            String[] objects = {"ID", "Title", "Author", "Year Published",
                    "Edition", "ISBN", "Genre", "Num. Copies", "Available"};
            this.printBooks(books, formatString, objects);
        }else{
            Customer customer = customerService.getCustomerByCustomerID(customerID);
            String errorMsg;
            if(customer == null){
                errorMsg = "Customer with ID "+customerID+" doesn't exist.";
            }else{
                errorMsg = "Customer with ID "+customerID+" don't have any book borrowed.";
            }
            outputReader.printOutput(errorMsg);
        }
    }

    /**
     * Print books sorted by edition
     */
    public void printBooksSortedByEdition(){
        //List<Book> sortedBookList = new ArrayList(bookService.getAllBooks());
        List<Book> sortedBookList = new ArrayList(library.getBooks());

        Collections.sort(sortedBookList);
        String formatString = "%15s %15s %20s %10s %10s %15s %15s %15s";
        String[] objects = {"Title", "Author", "Year Published",
                "Edition", "ISBN", "Genre", "Num. Copies", "Available"};
        this.printBooks(sortedBookList, formatString, objects);
    }

    /**
     * Print books sorted by published year
     */
    public void printBooksSortedByYear(){
        //List<Book> sortedBookList = new ArrayList(bookService.getAllBooks());
        List<Book> sortedBookList = new ArrayList(library.getBooks());

        Collections.sort(sortedBookList, new BookSortByYearComparator());
        String formatString = "%15s %15s %20s %10s %10s %15s %15s %15s";
        String[] objects = {"Title", "Author", "Year Published",
                "Edition", "ISBN", "Genre", "Num. Copies", "Available"};
        this.printBooks(sortedBookList, formatString, objects);
    }

    /**
     * Receive a sorted book array and print
     * @param sortedBookList
     */
    private void printBooks(List<Book> sortedBookList, String formatString, String[] objects){
        System.out.printf(formatString, objects);
        outputReader.printOutput("");

        for(Book book : sortedBookList){
            outputReader.printOutput(book.toString());
        }
        outputReader.printOutput("");
    }

    /**
     * Print books showing ids
     */
    public void printBooksWithIds(){
        //List<Book> sortedBookList = new ArrayList(bookService.getAllBooks());
        List<Book> books = new ArrayList(library.getBooks());

        String formatString = "%5s %15s %15s %20s %10s %10s %15s %15s %15s";
        String[] objects = {"ID", "Title", "Author", "Year Published",
                "Edition", "ISBN", "Genre", "Num. Copies", "Available"};

        System.out.printf(formatString, objects);
        outputReader.printOutput("");

        for(Book book : books){
            outputReader.printOutput(book.toStringWithId());
        }
        outputReader.printOutput("");
    }

    /**
     * Print all customers
     */
    public void printCustomers(){
        System.out.printf("%20s %20s %15s %20s %15s", "First Name", "Last Name", "Date of Birth",
                "Customer ID", "Active");
        outputReader.printOutput("");

        for(Customer customer : library.getCustomers()){
            outputReader.printOutput(customer.toString());
        }
        outputReader.printOutput("");
    }

    /**
     * Print all customers
     */
    public void printBorrowings(){
        System.out.printf("%20s %15s %20s %20s", "Costumer ID", "Finished",
                "Borrowed Date", "Return Date");
        outputReader.printOutput("");

        for(String customerID : library.getBorrowings().keySet()){
            outputReader.printOutput(String.format("%20s", customerID) +
                    library.getBorrowings().get(customerID).toString());
        }
        outputReader.printOutput("");
    }

    /**
     * Print all authors
     */
    public void printAuthors(){
        System.out.printf("%20s %20s %20s %15s %15s", "First Name", "Last Name", "Date of Birth",
                "Pseudonym", "Specialty");
        outputReader.printOutput("");

        for(Author author : authorService.getAllAuthors()){
            outputReader.printOutput(author.toString());
        }
        outputReader.printOutput("");
    }
}
