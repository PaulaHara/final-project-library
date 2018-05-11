package ca.ciccc.java.controller.dao;

import ca.ciccc.java.model.Author;

import java.util.List;

/**
 * @author paula on 25/04/18.
 */
public interface AuthorDAO {
    Author getAuthor(int id);
    List<Author> getAllAuthors();
    Author getAuthorByName(String firstName, String lastName);
    boolean insertAuthor(Author author);
    boolean updateAuthor(Author author);
    boolean deleteAuthor(int id);
}
