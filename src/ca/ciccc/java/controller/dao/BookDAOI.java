package ca.ciccc.java.controller.dao;

import ca.ciccc.java.db.ConnectionFactory;
import ca.ciccc.java.model.Book;
import ca.ciccc.java.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author paula on 25/04/18.
 */
public class BookDAOI implements BookDAO {
    private Connection connection;
    private final String SQL_CREATE_BOOK = "INSERT INTO book (title, author_id, year_published, edition, " +
            "isbn, genre, number_copies, copies_available) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_GET_BOOK_BY_ID = "SELECT * FROM book WHERE id=?";
    private final String SQL_GET_BOOK_BY_AUTHOR_AND_EDITION = "SELECT * FROM book WHERE author_id=? and edition=?";
    private final String SQL_GET_ALL_BOOKS = "SELECT * FROM book";
    private final String SQL_GET_BORROWED_BOOKS = "SELECT * FROM book_borrowing WHERE borrowing_id=?";
    private final String SQL_UPDATE_BOOK = "UPDATE book SET title=?, author_id=?, year_published=?, edition=?, " +
            "isbn=?, genre=?, number_copies=?, copies_available=? WHERE id=?";
    private final String SQL_DELETE_BOOK = "DELETE FROM book WHERE id=?";

    public BookDAOI(Connection connection){
        if(connection == null){
            this.connection = ConnectionFactory.getConnection();
        }else {
            this.connection = connection;
        }
    }

    @Override
    public Book getBook(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_BOOK_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return extractBookFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Book> getAllBooks() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_GET_ALL_BOOKS);
            Set books = new HashSet();
            while (rs.next()){
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
            return books;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getBookByAuthorAndEdition(int authorId, int edition){
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_BOOK_BY_AUTHOR_AND_EDITION);
            ps.setInt(1, authorId);
            ps.setInt(2, edition);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return extractBookFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertBook(Book book) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_BOOK);
            ps.setString(1, book.getTitle().toLowerCase());
            ps.setInt(2, book.getAuthor());
            ps.setInt(3, book.getYearPublished());
            ps.setInt(4, book.getEdition());
            ps.setString(5, book.getIsbn().toLowerCase());
            ps.setString(6, book.getGenre().getDescription());
            ps.setInt(7, book.getNumberOfCopies());
            ps.setInt(8, book.getCopiesAvailable());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateBook(Book book) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_BOOK);
            ps.setString(1, book.getTitle().toLowerCase());
            ps.setInt(2, book.getAuthor());
            ps.setInt(3, book.getYearPublished());
            ps.setInt(4, book.getEdition());
            ps.setString(5, book.getIsbn().toLowerCase());
            ps.setString(6, book.getGenre().getDescription());
            ps.setInt(7, book.getNumberOfCopies());
            ps.setInt(8, book.getCopiesAvailable());
            ps.setInt(9, book.getId());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBook(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BOOK);
            ps.setInt(1, id);
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Book> getBorrowedBooks(int borrowingId) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_BORROWED_BOOKS);
            ps.setInt(1, borrowingId);
            ResultSet rs = ps.executeQuery();

            ArrayList<Book> books = new ArrayList();
            while(rs.next()){
                Book book = this.getBook(rs.getInt("book_id"));
                books.add(book);
            }
            return books;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title").toLowerCase());
        book.setAuthor(rs.getInt("author_id"));
        book.setYearPublished(rs.getInt("year_published"));
        book.setEdition(rs.getInt("edition"));
        book.setIsbn(rs.getString("isbn"));
        book.setGenre(Genre.valueOf(rs.getString("genre").replace("-", "_").toUpperCase()));
        book.setNumberOfCopies(rs.getInt("number_copies"));
        book.setCopiesAvailable(rs.getInt("copies_available"));
        return book;
    }
}
