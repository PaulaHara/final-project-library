package ca.ciccc.java.controller.service;

import ca.ciccc.java.controller.dao.CustomerDAOI;
import ca.ciccc.java.db.ConnectionFactory;
import ca.ciccc.java.model.Book;
import ca.ciccc.java.controller.dao.BookDAOI;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author paula on 26/04/18.
 */
public class BookService {
    private BookDAOI bookDAOI;

    public BookService(){
        bookDAOI = new BookDAOI(ConnectionFactory.getConnection());
    }

    // To be used by tests
    public BookService(Connection connection){
        bookDAOI = new BookDAOI(connection);
    }

    public void createBook(Book book) {
        bookDAOI.insertBook(book);
    }

    public Book getBookById(int id) {
        return bookDAOI.getBook(id);
    }

    public Set<Book> getAllBooks() {
        return bookDAOI.getAllBooks();
    }

    public void updateBook(Book book) {
        bookDAOI.updateBook(book);
    }

    public void deleteBook(int id) {
        bookDAOI.deleteBook(id);
    }

    public Book getBookByAuthorAndEdition(int authorId, int edition){
        return bookDAOI.getBookByAuthorAndEdition(authorId, edition);
    }

    public ArrayList<Book> getBorrowedBooks(int borrowingId){
        return bookDAOI.getBorrowedBooks(borrowingId);
    }
}
