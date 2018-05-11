package ca.ciccc.java.controller.dao;

import ca.ciccc.java.Exceptions.InvalidCustomerIDException;
import ca.ciccc.java.db.ConnectionFactory;
import ca.ciccc.java.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author paula on 25/04/18.
 */
public class CustomerDAOI implements CustomerDAO {
    private Connection connection;
    private final String SQL_CREATE_CUSTOMER = "INSERT INTO customer (first_name, last_name, date_of_birth, customer_id, " +
            "is_active) VALUES (?, ?, ?, ?, ?)";
    private final String SQL_GET_CUSTOMER_BY_ID = "SELECT * FROM customer WHERE id=?";
    private final String SQL_GET_CUSTOMER_BY_CUSTOMER_ID = "SELECT * FROM customer WHERE customer_id=?";
    private final String SQL_GET_ALL_CUSTOMERS = "SELECT * FROM customer";
    private final String SQL_UPDATE_CUSTOMER = "UPDATE customer SET first_name=?, last_name=?, date_of_birth=?, " +
            "customer_id=?, is_active=? WHERE id=?";
    private final String SQL_DELETE_CUSTOMER = "DELETE FROM customer WHERE id=?";

    public CustomerDAOI(Connection connection){
        if(connection == null){
            this.connection = ConnectionFactory.getConnection();
        }else {
            this.connection = connection;
        }
    }

    @Override
    public Customer getCustomer(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_CUSTOMER_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return extractCustomerFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_GET_ALL_CUSTOMERS);
            List customers = new ArrayList();
            while (rs.next()){
                Customer customer = extractCustomerFromResultSet(rs);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer getCustomerByCustomerID(String customerID) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_CUSTOMER_BY_CUSTOMER_ID);
            ps.setString(1, customerID.toLowerCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return extractCustomerFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertCustomer(Customer customer) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_CUSTOMER);
            ps.setString(1, customer.getFirstName().toLowerCase());
            ps.setString(2, customer.getLastName().toLowerCase());
            ps.setDate(3, Date.valueOf(customer.getDateOfBirth()));
            ps.setString(4, customer.getCustomerID().toLowerCase());
            ps.setBoolean(5, customer.isActive());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_CUSTOMER);
            ps.setString(1, customer.getFirstName().toLowerCase());
            ps.setString(2, customer.getLastName().toLowerCase());
            ps.setDate(3, Date.valueOf(customer.getDateOfBirth()));
            ps.setString(4, customer.getCustomerID().toLowerCase());
            ps.setBoolean(5, customer.isActive());
            ps.setInt(6, customer.getId());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE_CUSTOMER);
            ps.setInt(1, id);
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private Customer extractCustomerFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();

        try {
            customer.setId(rs.getInt("id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
            customer.setCustomerID(rs.getString("customer_id"));
            customer.setActive(rs.getBoolean("is_active"));
        } catch (InvalidCustomerIDException e) {
            e.printStackTrace();
        }

        return customer;
    }
}
