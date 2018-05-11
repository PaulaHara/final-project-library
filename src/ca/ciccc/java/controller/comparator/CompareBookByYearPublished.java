package ca.ciccc.java.controller.comparator;

import ca.ciccc.java.model.Book;

import java.util.Comparator;

/**
 * @author paula on 20/04/18.
 */
public class CompareBookByYearPublished implements Comparator<Book> {

    @Override
    public int compare(Book book, Book otherBook) {
        if(book.getYearPublished() > otherBook.getYearPublished()){
            return 1;
        } else if(book.getYearPublished() == otherBook.getYearPublished()){
            return 0;
        }
        return -1;
    }
}