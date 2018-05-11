package ca.ciccc.java.model;

import java.util.Comparator;

/**
 * @author paula on 24/04/18.
 */
public class BookSortByYearComparator implements Comparator<Book> {

    @Override
    public int compare(Book book, Book book2) {
        if(book.getYearPublished() > book2.getYearPublished()){
            return 1;
        }else if(book.getYearPublished() == book2.getYearPublished()){
            return 0;
        }
        return -1;
    }
}
