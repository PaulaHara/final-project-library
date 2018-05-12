package ca.ciccc.test;

import ca.ciccc.java.controller.service.AuthorService;
import ca.ciccc.java.controller.service.BookService;
import ca.ciccc.java.db.ConnectionFactory;
import ca.ciccc.java.model.Author;
import ca.ciccc.java.model.Book;
import ca.ciccc.java.model.Genre;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * @author paula on 26/04/18.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookTest {
    private static BookService bookService;
    private static AuthorService authorService;
    private static Author author;
    private static int bookId;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Before any test, the customerService must be initialized
     */
    @BeforeClass
    public static void openConnection(){
        ConnectionFactory.disconnectFromDB();

        DBTest.createTables();
        bookService = new BookService(DBTest.getConnection());
        authorService = new AuthorService(DBTest.getConnection());

        createAuthor();
    }

    @AfterClass
    public static void closeConnection(){
        deleteAllBooks();
        DBTest.disconnectFromDB();
    }

    @Test
    public void a_addBookAndGetAllBooksTest(){
        Set<Book> bookList = bookService.getAllBooks();
        Assert.assertEquals(0, bookList.size());

        bookService.createBook(new Book("Livro teste", author.getId(), 1990, 23,
                "ABC123", Genre.SCI_FI, 23, 5));

        bookService.createBook(new Book("New Book", author.getId(), 1992, 3,
                "ABC456", Genre.BIOGRAPHY, 2, 2));

        bookList = bookService.getAllBooks();
        Assert.assertEquals(2, bookList.size());
    }

    /**
     * Test search using author and edition, and test the update of a book
     */
    @Test
    public void b_SearchByTitleAndAuthorAndUpdateBookTest(){
        Book book = bookService.getBookByAuthorAndEdition(author.getId(), 3);

        Set<Book> bookList = bookService.getAllBooks();
        Assert.assertEquals(2, bookList.size());

        bookId = bookList.iterator().next().getId();

        book.setTitle("New Book Updated");
        bookService.updateBook(book);

        // Size can't change
        bookList = bookService.getAllBooks();
        Assert.assertEquals(2, bookList.size());
    }

    /**
     * Test get book by Id and test get book by author and edition
     */
    @Test
    public void c_getBookByIdAndGetBookByTitleAndAuthorTest(){
        Book book1 = bookService.getBookByAuthorAndEdition(author.getId(), 23);
        Assert.assertNotNull(book1);
        Assert.assertEquals("livro teste", book1.getTitle().toLowerCase());

        Book book2 = bookService.getBookById(book1.getId());

        Assert.assertNotNull(book2);
        // Should be exactly the same book
        Assert.assertEquals(book1, book2);
    }

    /**
     * Test get of all books
     */
    @Test
    public void d_deleteBookTest(){
        Set<Book> bookList = bookService.getAllBooks();
        Assert.assertEquals(2, bookList.size());

        bookService.deleteBook(bookId);

        bookList = bookService.getAllBooks();
        Assert.assertEquals(1, bookList.size());
    }

    private static void createAuthor(){
        authorService.createAuthor(new Author("Joanne", "Rowling",
                LocalDate.parse("07/31/1965", formatter), "J.K.Rowling", Genre.FICTION));
        author = authorService.getAuthorByName("Joanne", "Rowling");
    }

    private static void deleteAllBooks(){
        Set<Book> bookList = bookService.getAllBooks();

        for(Book book : bookList){
            bookService.deleteBook(book.getId());
        }
    }
}
