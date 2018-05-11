package ca.ciccc.test;

import ca.ciccc.java.Exceptions.InvalidCustomerIDException;
import ca.ciccc.java.controller.service.CustomerService;
import ca.ciccc.java.db.ConnectionFactory;
import ca.ciccc.java.model.Customer;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author paula on 26/04/18.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerTest {
    private static CustomerService customerService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Before any test, the customerService must be initialized
     */
    @BeforeClass
    public static void openConnection(){
        ConnectionFactory.disconnectFromDB();

        DBTest.createTables();
        customerService = new CustomerService(DBTest.getConnection());
    }

    @AfterClass
    public static void closeConnection(){
        deleteAllCustomers();
        DBTest.disconnectFromDB();
    }

    /**
     * Test the creation of a customer and the getAllCustomers method
     * @throws InvalidCustomerIDException
     */
    @Test
    public void a_addCustomerTest() throws InvalidCustomerIDException {
        List<Customer> customerList = this.customerService.getAllCustomers();
        Assert.assertEquals(0, customerList.size());

        this.customerService.createCustomer(new Customer("Bruno", "Machado",
                LocalDate.parse("02/24/1989", formatter),"BR289", true));

        this.customerService.createCustomer(new Customer("Maria", "Cordeiro",
                LocalDate.parse("09/02/1970", formatter),"MC970", true));

        customerList = this.customerService.getAllCustomers();
        Assert.assertEquals(2, customerList.size());
    }

    /**
     * Test creation of a customer with invalid customerID, should throw an exception
     * @throws InvalidCustomerIDException
     */
    @Test(expected = InvalidCustomerIDException.class)
    public void b_addCustomerFailTest() throws InvalidCustomerIDException {
        List<Customer> customerList = this.customerService.getAllCustomers();
        Assert.assertEquals(2, customerList.size());

        this.customerService.createCustomer(new Customer("Joao", "Silva",
                LocalDate.parse("03/19/1980", formatter),"B1DS9", true));

        customerList = this.customerService.getAllCustomers();
        Assert.assertEquals(2, customerList.size());
    }

    /**
     * Testing update and search with CustomerID
     * @throws InvalidCustomerIDException
     */
    @Test
    public void c_updateCustomerTest() throws InvalidCustomerIDException {
        //************* Initial Asserts *************
        Customer customer = this.customerService.getCustomerByCustomerID("MC970");

        Assert.assertTrue(customer.getFirstName().equalsIgnoreCase("Maria"));
        Assert.assertTrue(customer.isActive());

        //*********** Updating Customer *************
        customer.setFirstName("Ana Maria");
        customer.setActive(false);

        this.customerService.updateCustomer(customer);

        //************** Final Asserts **************
        customer = this.customerService.getCustomerByCustomerID("MC970");

        Assert.assertTrue(customer.getFirstName().equalsIgnoreCase("Ana Maria"));
        Assert.assertFalse(customer.isActive());
    }

    /**
     * Deleting Customer test
     * @throws InvalidCustomerIDException
     */
    @Test
    public void d_deleteCustomerTest(){
        List<Customer> customerList = this.customerService.getAllCustomers();
        Assert.assertEquals(2, customerList.size());

        this.customerService.deleteCustomer(customerList.get(0).getId());

        customerList = this.customerService.getAllCustomers();
        Assert.assertEquals(1, customerList.size());
    }

    /**
     * Cleaning customers table
     */
    private static void deleteAllCustomers(){
        List<Customer> customerList = customerService.getAllCustomers();

        for(Customer customer : customerList) {
            customerService.deleteCustomer(customer.getId());
        }

    }
}
