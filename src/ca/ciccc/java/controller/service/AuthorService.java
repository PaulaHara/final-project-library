package ca.ciccc.java.controller.service;

import ca.ciccc.java.controller.dao.AuthorDAOI;
import ca.ciccc.java.db.ConnectionFactory;
import ca.ciccc.java.model.Author;

import java.sql.Connection;
import java.util.List;

/**
 * @author paula on 26/04/18.
 */
public class AuthorService {
    private final AuthorDAOI authorDAOI;

    public AuthorService(){
        authorDAOI = new AuthorDAOI(ConnectionFactory.getConnection());
    }

    // To be used by tests
    public AuthorService(Connection connection){
        authorDAOI = new AuthorDAOI(connection);
    }

    public void createAuthor(Author author) {
        authorDAOI.insertAuthor(author);
    }

    public Author getAuthorById(int id) {
        return authorDAOI.getAuthor(id);
    }

    public List<Author> getAllAuthors() {
        return authorDAOI.getAllAuthors();
    }

    public void updateAuthor(Author author) {
        authorDAOI.updateAuthor(author);
    }

    public void deleteAuthor(int id) {
        authorDAOI.deleteAuthor(id);
    }

    public Author getAuthorByName(String firstName, String lastName){
        return authorDAOI.getAuthorByName(firstName, lastName);
    }
}
