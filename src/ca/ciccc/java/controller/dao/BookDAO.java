package ca.ciccc.java.controller.dao;

import ca.ciccc.java.model.Book;

import java.util.List;
import java.util.Set;

/**
 * @author paula on 25/04/18.
 */
public interface BookDAO {
    Book getBook(int id);
    Set<Book> getAllBooks();
    Book getBookByAuthorAndEdition(int authorId, int edition);
    boolean insertBook(Book book);
    boolean updateBook(Book book);
    boolean deleteBook(int id);
    List<Book> getBorrowedBooks(int borrowingId);
}
