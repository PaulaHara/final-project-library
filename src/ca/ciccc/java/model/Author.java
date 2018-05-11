package ca.ciccc.java.model;

import java.time.LocalDate;

/**
 * @author paula on 20/04/18.
 */
public class Author extends Person {
    private String pseudonym;
    private Genre specialty;

    /**
     * Empty constructor
     */
    public Author() {
    }

    /**
     * Constructor with all information
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param pseudonym
     * @param specialty
     */
    public Author(Integer id, String firstName, String lastName, LocalDate dateOfBirth, String pseudonym, Genre specialty) {
        super(id, firstName, lastName, dateOfBirth);
        this.setPseudonym(pseudonym);
        this.setSpecialty(specialty);
    }

    /**
     * Constructor without id
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param pseudonym
     * @param specialty
     */
    public Author(String firstName, String lastName, LocalDate dateOfBirth, String pseudonym, Genre specialty) {
        super(firstName, lastName, dateOfBirth);
        this.setPseudonym(pseudonym);
        this.setSpecialty(specialty);
    }

    /**
     * Get the pseudonym
     * @return pseudonym
     */
    public String getPseudonym() {
        return pseudonym;
    }

    /**
     * Get the pseudonym
     * @param pseudonym
     */
    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    /**
     * Get the specialty
     * @return
     */
    public Genre getSpecialty() {
        return specialty;
    }

    /**
     * Set the specialty
     * @param specialty
     */
    public void setSpecialty(Genre specialty) {
        this.specialty = specialty;
    }

    /**
     * Two authors are equals if they had the same name and date of birth
     * @param o
     * @return true if equals, false if not
     */
    @Override
    public boolean equals(Object o) {
        if(o != null && o instanceof Author){
            Author author = (Author) o;
            if(o == this || this.getFirstName().equalsIgnoreCase(author.getFirstName())
                    && this.getLastName().equalsIgnoreCase(author.getLastName())
                    && this.getDateOfBirth().equals(author.getDateOfBirth())){
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
        result = (prime * result + this.getFirstName().hashCode() + this.getLastName().hashCode());
        result = (prime * result + getDateOfBirth().hashCode());
        return result;
    }

    /**
     * Return all the information about this library
     * @return string
     */
    @Override
    public String toString() {
        return String.format("%20s %20s %20s %15s %15s",
                getFirstName(), getLastName(), getDateOfBirth(), getPseudonym(), getSpecialty().getDescription());
    }
}
