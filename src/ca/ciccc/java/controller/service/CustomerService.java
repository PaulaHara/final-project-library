package ca.ciccc.java.controller.service;

import ca.ciccc.java.db.ConnectionFactory;
import ca.ciccc.java.model.Customer;
import ca.ciccc.java.controller.dao.CustomerDAOI;

import java.sql.Connection;
import java.util.List;

/**
 * @author paula on 26/04/18.
 */
public class CustomerService {
    private final CustomerDAOI customerDAOI;

    public CustomerService(){
        customerDAOI = new CustomerDAOI(ConnectionFactory.getConnection());
    }

    // To be used by tests
    public CustomerService(Connection connection){
        customerDAOI = new CustomerDAOI(connection);
    }

    public void createCustomer(Customer customer) {
        customerDAOI.insertCustomer(customer);
    }

    public Customer getCustomerById(int id) {
        return customerDAOI.getCustomer(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDAOI.getAllCustomers();
    }

    public void updateCustomer(Customer customer) {
        customerDAOI.updateCustomer(customer);
    }

    public void deleteCustomer(int id) {
        customerDAOI.deleteCustomer(id);
    }

    public Customer getCustomerByCustomerID(String customerID){
        return customerDAOI.getCustomerByCustomerID(customerID);
    }
}
