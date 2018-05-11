package ca.ciccc.java.model;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author paula on 20/04/18.
 */
public class Borrowing {
    private int id;
    private int customerId;
    private Set<Integer> booksId;
    private boolean finished; // When the user return the books, the borrowing is finished.
    private LocalDate borrowedDate;
    private LocalDate returnDate;

    public Borrowing(){
    }

    public Borrowing(int customerId, boolean finished, LocalDate borrowedDate, LocalDate returnDate){
        this.setCustomerId(customerId);
        this.setFinished(finished);
        this.setBorrowedDate(borrowedDate);
        this.setReturnDate(returnDate);
    }

    public Borrowing(int id, int customerId, boolean finished, LocalDate borrowedDate, LocalDate returnDate){
        this.setId(id);
        this.setCustomerId(customerId);
        this.setFinished(finished);
        this.setBorrowedDate(borrowedDate);
        this.setReturnDate(returnDate);
    }

    public Borrowing(int customerId, Set<Integer> booksId, boolean finished, LocalDate borrowedDate, LocalDate returnDate){
        this.setId(id);
        this.setCustomerId(customerId);
        this.setBooksId(booksId);
        this.setFinished(finished);
        this.setBorrowedDate(borrowedDate);
        this.setReturnDate(returnDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Set<Integer> getBooksId() {
        return booksId;
    }

    public void setBooksId(Set<Integer> booksId) {
        this.booksId = booksId;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return String.format("%15s %20s %20s",
                isFinished(), getBorrowedDate(), getReturnDate());
    }
}
