package ca.ciccc.java.controller.dao;

import ca.ciccc.java.model.Borrowing;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author paula on 25/04/18.
 */
public class BorrowingDAOI implements BorrowingDAO {
    private final Connection connection;
    private final String SQL_CREATE_BORROWING = "INSERT INTO borrowing (customer_id, is_finished, borrowed_date, " +
            "return_date) VALUES (?, ?, ?, ?)";
    private final String SQL_GET_BORROWING_BY_ID = "SELECT * FROM borrowing WHERE id=?";
    private final String SQL_GET_BORROWING_BY_CUSTOMER = "SELECT * FROM borrowing WHERE customer_id=?";
    private final String SQL_GET_ALL_BORROWINGS = "SELECT * FROM borrowing";
    private final String SQL_UPDATE_BORROWING = "UPDATE borrowing SET customer_id=?, is_finished=?, borrowed_date=?, " +
            "return_date=? WHERE id=?";
    private final String SQL_DELETE_BORROWING = "DELETE FROM borrowing WHERE id=?";
    private final String SQL_CREATE_BOOK_BORROWING = "INSERT INTO book_borrowing (book_id, borrowing_id) VALUES (?, ?)";
    private final String SQL_DELETE_BOOK_BORROWING = "DELETE FROM book_borrowing WHERE book_id=? and borrowing_id=?";
    private final String SQL_GET_BORROWED_BOOKS = "SELECT * FROM book_borrowing WHERE borrowing_id=?";
    private final String SQL_GET_BOOK_BORROWED = "SELECT * FROM book_borrowing WHERE book_id=? and borrowing_id=?";

    public BorrowingDAOI(Connection connection){
        this.connection = connection;
    }

    @Override
    public Borrowing getBorrowing(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_BORROWING_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return extractBorrowingFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<String, Borrowing> getAllBorrowings() {
        try {
            CustomerDAOI customerDAOI = new CustomerDAOI(connection);

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_GET_ALL_BORROWINGS);
            HashMap<String, Borrowing> borrowings = new HashMap<>();
            while (rs.next()){
                Borrowing borrowing = extractBorrowingFromResultSet(rs);
                borrowings.put(customerDAOI.getCustomer(borrowing.getCustomerId()).getCustomerID(), borrowing);
            }
            return borrowings;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Borrowing getBorrowingByCustomer(int customerId) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_BORROWING_BY_CUSTOMER);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return extractBorrowingFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertBorrowing(Borrowing borrowing) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_BORROWING);
            ps.setInt(1, borrowing.getCustomerId());
            ps.setBoolean(2, borrowing.isFinished());
            ps.setDate(3, Date.valueOf(borrowing.getBorrowedDate()));
            ps.setDate(4, borrowing.getReturnDate() == null ? null : Date.valueOf(borrowing.getReturnDate()));
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateBorrowing(Borrowing borrowing) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_BORROWING);
            ps.setInt(1, borrowing.getCustomerId());
            ps.setBoolean(2, borrowing.isFinished());
            ps.setDate(3, Date.valueOf(borrowing.getBorrowedDate()));
            ps.setDate(4, borrowing.getReturnDate() == null ? null : Date.valueOf(borrowing.getReturnDate()));
            ps.setInt(5, borrowing.getId());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBorrowing(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BORROWING);
            ps.setInt(1, id);
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Integer> getBorrowedBooks(int borrowingId) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_BORROWED_BOOKS);
            ps.setInt(1, borrowingId);
            ResultSet rs = ps.executeQuery();

            List<Integer> booksId = new ArrayList();
            while(rs.next()){
                booksId.add(rs.getInt("book_id"));
            }
            return booksId;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean borrowBook(int bookId, int borrowingId){
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_BOOK_BORROWING);
            ps.setInt(1, bookId);
            ps.setInt(2, borrowingId);
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean returnBook(int bookId, int borrowingId){
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BOOK_BORROWING);
            ps.setInt(1, bookId);
            ps.setInt(2, borrowingId);
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean isBookBorrowed(int bookId, int borrowingId){
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_BOOK_BORROWED);
            ps.setInt(1, bookId);
            ps.setInt(2, borrowingId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private Borrowing extractBorrowingFromResultSet(ResultSet rs) throws SQLException {
        Borrowing borrowing = new Borrowing();
        borrowing.setId(rs.getInt("id"));
        borrowing.setCustomerId(rs.getInt("customer_id"));
        borrowing.setFinished(rs.getBoolean("is_finished"));
        borrowing.setBorrowedDate(rs.getDate("borrowed_date").toLocalDate());
        borrowing.setReturnDate(rs.getDate("return_date") == null ? null : rs.getDate("return_date").toLocalDate());
        return borrowing;
    }
}
