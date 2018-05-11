package ca.ciccc.test;

import ca.ciccc.java.controller.service.AuthorService;
import ca.ciccc.java.db.ConnectionFactory;
import ca.ciccc.java.model.Author;
import ca.ciccc.java.model.Genre;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author paula on 26/04/18.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorTest {
    private static AuthorService authorService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Before any test, the authorService must be initialized
     */
    @BeforeClass
    public static void openConnection(){
        ConnectionFactory.disconnectFromDB();

        DBTest.createTables();
        authorService = new AuthorService(DBTest.getConnection());
    }

    @AfterClass
    public static void closeConnection(){
        deleteAllAuthors();
        DBTest.disconnectFromDB();
    }

    /**
     * Test the creation of an author and the getAllAuthors method
     */
    @Test
    public void a_addAuthorAndGetAllAuthorsTest() {
        List<Author> authorList = this.authorService.getAllAuthors();
        Assert.assertEquals(0, authorList.size());

        this.authorService.createAuthor(new Author("Bruno", "Machado",
                LocalDate.parse("02/24/1989", formatter),"BM", Genre.CHILDREN));

        this.authorService.createAuthor(new Author("Roberta", "Carvalho",
                LocalDate.parse("11/12/1992", formatter),"Berta", Genre.NON_FICTION));

        authorList = this.authorService.getAllAuthors();
        Assert.assertEquals(2, authorList.size());
    }

    /**
     * Testing update and search with AuthorID
     */
    @Test
    public void b_updateCustomerAndSearchByNameTest() {
        Author author = this.authorService.getAuthorByName("RoberTa", "CarVaLho");
        Assert.assertNotNull(author);

        //*********** Updating Customer *************
        author.setPseudonym("Robertinha");

        this.authorService.updateAuthor(author);
        //*******************************************

        author = this.authorService.getAuthorByName("Roberta", "Carvalho");

        Assert.assertNotNull(author);
        Assert.assertEquals("robertinha", author.getPseudonym());
    }

    /**
     * Testing update and search with AuthorID
     */
    @Test
    public void c_searchByNameAndByIdTest() {
        Author author1 = this.authorService.getAuthorByName("Bruno", "Machado");

        Assert.assertNotNull(author1);
        Assert.assertEquals("bm", author1.getPseudonym());

        Author author2 = this.authorService.getAuthorById(author1.getId());

        Assert.assertNotNull(author2);
        // Should be exactly the same author
        Assert.assertEquals(author1, author2);
    }

    /**
     * Deleting Customer test
     */
    @Test
    public void d_deleteAuthorTest(){
        List<Author> authorList = this.authorService.getAllAuthors();
        Assert.assertEquals(2, authorList.size());

        this.authorService.deleteAuthor(authorList.get(0).getId());

        authorList = this.authorService.getAllAuthors();
        Assert.assertEquals(1, authorList.size());
    }

    /**
     * Cleaning customers table
     */
    private static void deleteAllAuthors(){
        List<Author> authorList = authorService.getAllAuthors();

        for(Author author : authorList) {
            authorService.deleteAuthor(author.getId());
        }
    }
}
