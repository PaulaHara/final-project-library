package ca.ciccc.java.model;

/**
 * @author paula on 20/04/18.
 */
public class Book implements Comparable<Book> {
    private int id;
    private String title;
    private int authorId;
    private int yearPublished;
    private int edition;
    private String isbn;
    private Genre genre;
    private int numberOfCopies;
    private int copiesAvailable;

    public Book(){}

    public Book(String title, int authorId, int yearPublished, int edition, String isbn,
                Genre genre, int numberOfCopies, int copiesAvailable){
        this.setTitle(title);
        this.setAuthor(authorId);
        this.setYearPublished(yearPublished);
        this.setEdition(edition);
        this.setIsbn(isbn);
        this.setGenre(genre);
        this.setNumberOfCopies(numberOfCopies);
        this.setCopiesAvailable(copiesAvailable);
    }

    public Book(Integer id, String title, int authorId, int yearPublished, int edition, String isbn,
                Genre genre, int numberOfCopies, int copiesAvailable){
        this.setId(id);
        this.setTitle(title);
        this.setAuthor(authorId);
        this.setYearPublished(yearPublished);
        this.setEdition(edition);
        this.setIsbn(isbn);
        this.setGenre(genre);
        this.setNumberOfCopies(numberOfCopies);
        this.setCopiesAvailable(copiesAvailable);
    }

    /**
     * Primary key
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Book title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set book title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Id of the author
     * @return
     */
    public int getAuthor() {
        return authorId;
    }

    /**
     *
     * @param authorId
     */
    public void setAuthor(int authorId) {
        this.authorId = authorId;
    }

    /**
     * Get the year that the book was published
     * @return yearPublished
     */
    public int getYearPublished() {
        return yearPublished;
    }

    /**
     * Sete the year published
     * @param yearPublished
     */
    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    /**
     * Get the edition
     * @return
     */
    public int getEdition() {
        return edition;
    }

    /**
     * Set the edition
     * @param edition
     */
    public void setEdition(int edition) {
        this.edition = edition;
    }

    /**
     * Get the ISBN
     * @return isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Set the ISBN
     * @param isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Get the genre of the book
     * @return
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Set the genre of the book
     * @param genre
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * Get the number of the copies of the book
     * @return
     */
    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    /**
     * Set the number of copies of the book
     * @param numberOfCopies
     */
    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    /**
     * Get the number of available copies of the book
     * @return
     */
    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    /**
     * Set the number of available copies of the book
     * @param copiesAvailable
     */
    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    /**
     * Two books are equals if they had the same author and the same edition
     * @param o
     * @return true if equals, false if not
     */
    @Override
    public boolean equals(Object o) {
        if(o != null && o instanceof Book){
            Book book = (Book) o;
            if(o == this || this.getAuthor() == book.getAuthor() && this.getEdition() == book.getEdition()){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result + this.getAuthor() + this.getEdition());
        return result;
    }

    /**
     *
     * @param book
     * @return <ul>
     *           <li>1 -> this.edition > book.edition</li>
     *           <li>0 -> this.edition == book.edition</li>
     *           <li>-1 -> this.edition < book.edition</li>
     *         </ul>
     */
    @Override
    public int compareTo(Book book) {
        if(this.getEdition() > book.getEdition()){
            return 1;
        } else if(this.getEdition() == book.getEdition()){
            return 0;
        }
        return -1;
    }

    /**
     * Return all the information about this book and the id
     * @return
     */
    public String toStringWithId() {
        return String.format("%5s %15s %15s %20s %10s %10s %15s %15s %15s", getId(), getTitle(), getAuthor(),
                getYearPublished(), getEdition(), getIsbn(), getGenre().getDescription(), getNumberOfCopies(),
                getCopiesAvailable());
    }

    /**
     * Return all the information about this book
     * @return string
     */
    @Override
    public String toString() {
        return String.format("%15s %15s %20s %10s %10s %15s %15s %15s",
                getTitle(), getAuthor(), getYearPublished(), getEdition(), getIsbn(), getGenre().getDescription(),
                getNumberOfCopies(), getCopiesAvailable());
    }
}