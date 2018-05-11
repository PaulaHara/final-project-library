package ca.ciccc.java.model;

import java.time.LocalDate;

/**
 * @author paula on 20/04/18.
 */
public class Person {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    /**
     * Empty constructor
     */
    public Person(){
    }

    /**
     * Constructor with all information
     * @param id
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     */
    public Person(Integer id, String firstName, String lastName, LocalDate dateOfBirth){
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setDateOfBirth(dateOfBirth);
    }

    /**
     * Constructor without id
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     */
    public Person(String firstName, String lastName, LocalDate dateOfBirth){
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setDateOfBirth(dateOfBirth);
    }

    /**
     * Get the id
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the id
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get last name
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get date of birth
     * @return dateOfBirth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Set the date of birth
     * @param dateOfBirth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Return all the information about this person
     * @return string
     */
    @Override
    public String toString() {
        return "Person [First Name: "+getFirstName()+", Last Name: "+getLastName()+", Date of Birth: "+getDateOfBirth()+"]";
    }
}
