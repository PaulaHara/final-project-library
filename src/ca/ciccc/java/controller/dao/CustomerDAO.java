package ca.ciccc.java.controller.dao;

import ca.ciccc.java.model.Customer;

import java.util.List;

/**
 * @author paula on 25/04/18.
 */
public interface CustomerDAO {
    Customer getCustomer(int id);
    List<Customer> getAllCustomers();
    Customer getCustomerByCustomerID(String customerID);
    boolean insertCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(int id);
}
