package ca.ciccc.java.model;

/**
 * @author paula on 20/04/18.
 */
public enum Genre {
    FICTION("Fiction"), NON_FICTION("Non-fiction"), SCI_FI("Sci-Fi"), BIOGRAPHY("Biography"), HISTORY("History"), CHILDREN("Children");

    private String description;

    /**
     * Constructor for DressCode
     * @param description
     */
    Genre(String description){
        this.description = description;
    }

    /**
     * description of the enumeration
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the new description for enumeration
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Information about Genre
     * @return string
     */
    @Override
    public String toString() {
        return "Genre [Fiction, Non Fiction, Sci-Fi, Biography, History, Children]";
    }
}
