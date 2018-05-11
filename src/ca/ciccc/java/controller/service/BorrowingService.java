package ca.ciccc.java.controller.service;

import ca.ciccc.java.db.ConnectionFactory;
import ca.ciccc.java.model.Borrowing;
import ca.ciccc.java.controller.dao.BorrowingDAOI;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

/**
 * @author paula on 26/04/18.
 */
public class BorrowingService {
    private final BorrowingDAOI borrowingDAOI;

    public BorrowingService(){
        borrowingDAOI = new BorrowingDAOI(ConnectionFactory.getConnection());
    }

    public BorrowingService(Connection connection){
        borrowingDAOI = new BorrowingDAOI(connection);
    }

    public String createBorrowing(Borrowing borrowing) {
        borrowingDAOI.insertBorrowing(borrowing);

        String msg = "";
        for(Integer bookId : borrowing.getBooksId()){
            msg += borrowBook(bookId, getBorrowingByCustomer_id(borrowing.getCustomerId()).getId());
        }
        return msg;
    }

    public Borrowing getBorrowingById(int id) {
        return borrowingDAOI.getBorrowing(id);
    }

    public HashMap<String, Borrowing> getAllBorrowings() {
        return borrowingDAOI.getAllBorrowings();
    }

    public void updateBorrowing(Borrowing borrowing) {
        borrowingDAOI.updateBorrowing(borrowing);
    }

    public void deleteBorrowing(int id) {
        borrowingDAOI.deleteBorrowing(id);
    }

    public Borrowing getBorrowingByCustomer_id(int customerId){
        return borrowingDAOI.getBorrowingByCustomer(customerId);
    }

    public List<Integer> getBooksBorrowed(int borrowingId){
        return borrowingDAOI.getBorrowedBooks(borrowingId);
    }

    public String borrowBook(int bookId, int borrowingId){
        Borrowing borrowing = getBorrowingById(borrowingId);

        if(borrowing != null && borrowingDAOI.getBorrowedBooks(borrowingId).size() < 5) {
            if(borrowingDAOI.isBookBorrowed(bookId, borrowingId)){
                return "Error: This customer is already borrowing the book with id "+bookId+".\n";
            }
            borrowingDAOI.borrowBook(bookId, borrowingId);
            return "Book borrowed successfully!\n";
        }else{
            return "Error: Book (id: "+bookId+") not borrowed!\n" +
                    "This customer already has 5 books borrowed. Each customer can't take more than 5 books.\n";
        }
    }

    public void returnBook(int bookId, int borrowingId){
        borrowingDAOI.returnBook(bookId, borrowingId);
    }
}
