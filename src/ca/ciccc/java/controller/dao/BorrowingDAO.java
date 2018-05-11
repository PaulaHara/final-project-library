package ca.ciccc.java.controller.dao;

import ca.ciccc.java.model.Borrowing;

import java.util.HashMap;
import java.util.List;

/**
 * @author paula on 25/04/18.
 */
public interface BorrowingDAO {
    Borrowing getBorrowing(int id);
    HashMap<String, Borrowing> getAllBorrowings();
    Borrowing getBorrowingByCustomer(int customer_id);
    boolean insertBorrowing(Borrowing borrowing);
    boolean updateBorrowing(Borrowing borrowing);
    boolean deleteBorrowing(int id);
}
