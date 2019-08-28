
import java.util.*;
import java.io.Serializable;
import org.hibernate.Session;

/**
 * Class that implements the concrete access to mysql database
 */
public class CustomerUserServiceMysql implements Serializable {

    public Session session;

    public CustomerUserServiceMysql (Session session) {
        this.session = session;
    }

    /**
     * Adds a new customer user to the DB
     * @param c - customer user to be inserted
     */
    public void createCustomer (CustomerUser c) {
        try {
            System.out.println("[INFO] Creating " +c);
            session.beginTransaction();
            session.save(c);
            session.getTransaction().commit();
        } catch (Exception e) {
            Boolean found = false;
            List<CustomerUser> all_customer_users = getAllCustomerUsers();
            for (int i = 0; i < all_customer_users.size(); i++) {
                if (all_customer_users.get(i).equals(c)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                System.out.println("[WARN] Inserting customer \""+c.get_nickname()+"\" already present in DB");
            } else {
                System.out.println("[ERROR] Errors while creating new customer");
                e.printStackTrace();
            }
        } finally {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }

    /**
     * Deletes the customer user from the DB
     * @param c - customer user to be deleted
     */
    public void deleteCustomer (CustomerUser c) {
        System.out.println("[INFO] Deleting " +c);
        session.beginTransaction();
        session.delete(c);
        session.getTransaction().commit();
    }

    /**
     * Updates the customer user in the DB
     * @param c - customer user to be updated with new information
     */
    public void updateCustomerUser (CustomerUser c) {
        System.out.println("[INFO] Updating " +c);
        session.beginTransaction();
        session.update(c);
        session.getTransaction().commit();
    }

    // ----- METHODS USED FOR FOREIGN KEY -----

    /**
     * Retrieves all the customer users stored in DB
     * @return list of all customer users
     */
    public List<CustomerUser> getAllCustomerUsers() {
        return session.createQuery("from CustomerUser c").list();
    }



}
