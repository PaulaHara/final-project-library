package ca.ciccc.test;

import ca.ciccc.java.Exceptions.InvalidCustomerIDException;
import ca.ciccc.java.controller.service.AuthorService;
import ca.ciccc.java.controller.service.BookService;
import ca.ciccc.java.controller.service.BorrowingService;
import ca.ciccc.java.controller.service.CustomerService;
import ca.ciccc.java.db.ConnectionFactory;
import ca.ciccc.java.model.*;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author paula on 26/04/18.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BorrowingTest {
    private static BookService bookService;
    private static AuthorService authorService;
    private static BorrowingService borrowingService;
    private static CustomerService customerService;
    private static Author author;
    private static Customer customer1;
    private static Customer customer2;
    private static Book book1;
    private static Book book2;
    private static Book book3;
    private static Book book4;
    private static Book book5;
    private static Book book6;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Before any test, the customerService must be initialized
     */
    @BeforeClass
    public static void openConnection() throws InvalidCustomerIDException {
        ConnectionFactory.disconnectFromDB();

        DBTest.createTables();
        bookService = new BookService(DBTest.getConnection());
        authorService = new AuthorService(DBTest.getConnection());
        borrowingService = new BorrowingService(DBTest.getConnection());
        customerService = new CustomerService(DBTest.getConnection());

        createAuthor();
        createBook();
        createCustomer();
    }

    @AfterClass
    public static void closeConnection(){
        deleteAllData();
        DBTest.disconnectFromDB();
    }

    @Test
    public void a_addBorrowingTest(){
        HashMap<String, Borrowing> borrowings = borrowingService.getAllBorrowings();
        Assert.assertEquals(0, borrowings.size());

        Set<Integer> booksId = new HashSet<>();
        booksId.add(book1.getId());
        booksId.add(book2.getId());
        booksId.add(book3.getId());
        booksId.add(book4.getId());
        booksId.add(book5.getId());
        borrowingService.createBorrowing(new Borrowing(customer1.getId(), booksId, false, LocalDate.now(), null));

        booksId = new HashSet<>();
        booksId.add(book1.getId());
        booksId.add(book2.getId());
        booksId.add(book3.getId());
        booksId.add(book4.getId());
        borrowingService.createBorrowing(new Borrowing(customer2.getId(), booksId, false, LocalDate.now(), null));

        borrowings = borrowingService.getAllBorrowings();
        Assert.assertEquals(2, borrowings.size());
    }

    /**
     * Test borrow another book to a customer
     */
    @Test
    public void b_borrowBookTest(){
        Borrowing borrowing = borrowingService.getBorrowingByCustomer_id(customer2.getId());
        Assert.assertEquals(4, borrowingService.getBooksBorrowed(borrowing.getId()).size());

        borrowingService.borrowBook(book6.getId(), borrowing.getId());

        borrowing = borrowingService.getBorrowingByCustomer_id(customer2.getId());
        Assert.assertEquals(5, borrowingService.getBooksBorrowed(borrowing.getId()).size());
    }

    /**
     * Test borrow another book to a customer that already has 5 books borrowed
     */
    @Test
    public void c_borrowBookFailTest(){
        Borrowing borrowing = borrowingService.getBorrowingByCustomer_id(customer1.getId());
        Assert.assertEquals(5, borrowingService.getBooksBorrowed(borrowing.getId()).size());

        String result = borrowingService.borrowBook(book6.getId(), borrowing.getId());

        Assert.assertTrue(result.contains("Error:") &&
                result.contains("Each customer can't take more than 5 books"));

        borrowing = borrowingService.getBorrowingByCustomer_id(customer1.getId());
        Assert.assertEquals(5, borrowingService.getBooksBorrowed(borrowing.getId()).size());
    }

    /**
     * Test return book
     */
    @Test
    public void d_returnBookTest(){
        Borrowing borrowing = borrowingService.getBorrowingByCustomer_id(customer1.getId());
        Assert.assertEquals(5, borrowingService.getBooksBorrowed(borrowing.getId()).size());

        borrowingService.returnBook(book2.getId(), borrowing.getId());

        borrowing = borrowingService.getBorrowingByCustomer_id(customer1.getId());
        Assert.assertEquals(4, borrowingService.getBooksBorrowed(borrowing.getId()).size());
    }

    /**
     * Test update borrowing -> remove all books
     */
    @Test
    public void e_updateBorrowingTest(){
        Borrowing borrowing = borrowingService.getBorrowingByCustomer_id(customer1.getId());
        Assert.assertNull(borrowing.getReturnDate());

        borrowing.setReturnDate(LocalDate.now());
        borrowingService.updateBorrowing(borrowing);

        borrowing = borrowingService.getBorrowingByCustomer_id(customer1.getId());
        Assert.assertNotNull(borrowing.getReturnDate());
        Assert.assertEquals(LocalDate.now(), borrowing.getReturnDate());
    }

    /**
     * Test delete borrowing
     */
    @Test
    public void f_deleteBorrowingTest(){
        Borrowing borrowing = borrowingService.getBorrowingByCustomer_id(customer2.getId());
        Assert.assertEquals(5, borrowingService.getBooksBorrowed(borrowing.getId()).size());

        int borrowingId = borrowing.getId();

        borrowingService.deleteBorrowing(borrowingId);

        borrowing = borrowingService.getBorrowingByCustomer_id(customer2.getId());
        Assert.assertNull(borrowing);
        Assert.assertEquals(0, borrowingService.getBooksBorrowed(borrowingId).size());
    }

    private static void createAuthor(){
        authorService.createAuthor(new Author("Joanne", "Rowling",
                LocalDate.parse("07/31/1965", formatter), "J.K.Rowling", Genre.FICTION));
        author = authorService.getAuthorByName("Joanne", "Rowling");
    }

    private static void createCustomer() throws InvalidCustomerIDException {
        customerService.createCustomer(new Customer("Bruno", "Machado",
                LocalDate.parse("02/24/1989", formatter),"BR289", true));
        customer1 = customerService.getCustomerByCustomerID("BR289");

        customerService.createCustomer(new Customer("Maria", "Cordeiro",
                LocalDate.parse("09/02/1970", formatter),"MC970", true));
        customer2 = customerService.getCustomerByCustomerID("MC970");
    }

    private static void createBook(){
        bookService.createBook(new Book("Livro 1", author.getId(), 1990, 23,
                "ABC123", Genre.SCI_FI, 23, 5));
        bookService.createBook(new Book("Livro 2", author.getId(), 1991, 2,
                "ABC456", Genre.BIOGRAPHY, 15, 5));
        bookService.createBook(new Book("Livro 3", author.getId(), 1992, 3,
                "ABC789", Genre.CHILDREN, 28, 5));
        bookService.createBook(new Book("Livro 4", author.getId(), 1993, 13,
                "DEF123", Genre.FICTION, 8, 5));
        bookService.createBook(new Book("Livro 5", author.getId(), 1994, 24,
                "DEF456", Genre.HISTORY, 3, 1));
        bookService.createBook(new Book("Livro 6", author.getId(), 1995, 5,
                "GHI789", Genre.NON_FICTION, 12, 5));
        book1 = bookService.getBookByAuthorAndEdition(author.getId(), 23);
        book2 = bookService.getBookByAuthorAndEdition(author.getId(), 2);
        book3 = bookService.getBookByAuthorAndEdition(author.getId(), 3);
        book4 = bookService.getBookByAuthorAndEdition(author.getId(), 13);
        book5 = bookService.getBookByAuthorAndEdition(author.getId(), 24);
        book6 = bookService.getBookByAuthorAndEdition(author.getId(), 5);
    }

    private static void deleteAllData(){
        HashMap<String, Borrowing> borrowingList = borrowingService.getAllBorrowings();
        for(Borrowing borrowing : borrowingList.values()){
            borrowingService.deleteBorrowing(borrowing.getId());
        }

        Set<Book> books = bookService.getAllBooks();
        for(Book book : books){
            bookService.deleteBook(book.getId());
        }

        List<Author> authors = authorService.getAllAuthors();
        for(Author author : authors){
            authorService.deleteAuthor(author.getId());
        }

        List<Customer> customers = customerService.getAllCustomers();
        for(Customer customer : customers){
            customerService.deleteCustomer(customer.getId());
        }
    }
}
