package ca.ciccc.java.controller.dao;

import ca.ciccc.java.db.ConnectionFactory;
import ca.ciccc.java.model.Author;
import ca.ciccc.java.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author paula on 25/04/18.
 */
public class AuthorDAOI implements AuthorDAO {
    private final Connection connection;
    private final String SQL_CREATE_AUTHOR = "INSERT INTO author (first_name, last_name, date_of_birth, pseudonym, " +
            "specialty) VALUES (?, ?, ?, ?, ?)";
    private final String SQL_GET_AUTHOR_BY_ID = "SELECT * FROM author WHERE id=?";
    private final String SQL_GET_AUTHOR_BY_NAME = "SELECT * FROM author WHERE first_name=? AND last_name=?";
    private final String SQL_GET_ALL_AUTHORS = "SELECT * FROM author";
    private final String SQL_UPDATE_AUTHOR = "UPDATE author SET first_name=?, last_name=?, date_of_birth=?, " +
            "pseudonym=?, specialty=? WHERE id=?";
    private final String SQL_DELETE_AUTHOR = "DELETE FROM author WHERE id=?";

    public AuthorDAOI(Connection connection){
        this.connection = connection;
    }

    @Override
    public Author getAuthor(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_AUTHOR_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return extractAuthorFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Author> getAllAuthors() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_GET_ALL_AUTHORS);
            List authors = new ArrayList();
            while (rs.next()){
                Author author = extractAuthorFromResultSet(rs);
                authors.add(author);
            }
            return authors;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Author getAuthorByName(String firstName, String lastName) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_AUTHOR_BY_NAME);
            ps.setString(1, firstName.toLowerCase());
            ps.setString(2, lastName.toLowerCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return extractAuthorFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertAuthor(Author author) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_AUTHOR);
            ps.setString(1, author.getFirstName().toLowerCase());
            ps.setString(2, author.getLastName().toLowerCase());
            ps.setDate(3, Date.valueOf(author.getDateOfBirth()));
            ps.setString(4, author.getPseudonym().toLowerCase());
            ps.setString(5, author.getSpecialty().getDescription());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAuthor(Author author) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_AUTHOR);
            ps.setString(1, author.getFirstName().toLowerCase());
            ps.setString(2, author.getLastName().toLowerCase());
            ps.setDate(3, Date.valueOf(author.getDateOfBirth()));
            ps.setString(4, author.getPseudonym().toLowerCase());
            ps.setString(5, author.getSpecialty().getDescription());
            ps.setInt(6, author.getId());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAuthor(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE_AUTHOR);
            ps.setInt(1, id);
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private Author extractAuthorFromResultSet(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setId(rs.getInt("id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));
        author.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
        author.setPseudonym(rs.getString("pseudonym"));
        author.setSpecialty(Genre.valueOf(rs.getString("specialty").replace("-", "_").toUpperCase()));
        return author;
    }
}
