
import org.hibernate.Session;
import java.io.Serializable;
import java.util.*;

/**
 * Class that manages the customer functions and calls the CustomerUserServiceH2 methods
 */
public class CustomerUserController implements Serializable {

    // Serves database-application communication
    private CustomerUserServiceMysql service;

    public CustomerUserController (Session session) {
        service = new CustomerUserServiceMysql(session);
    }

    public CustomerUser createCustomerUser(String customer_information){

        String[] customer_splitted = customer_information.split(",");

        CustomerUser customer = new CustomerUser();
        customer.set_nickname(customer_splitted[0]);
        customer.set_firstname(customer_splitted[1]);
        customer.set_lastname(customer_splitted[2]);

        return createCustomerUser(customer);
    }

    public CustomerUser createCustomerUser(CustomerUser c) {
        service.createCustomer(c);
        return c;
    }

    // ----- METHODS USED FOR FOREIGN KEY -----

    public List<CustomerUser> getAllCustomerUsers () { return service.getAllCustomerUsers(); }


}
