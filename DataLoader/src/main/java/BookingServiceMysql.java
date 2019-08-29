
import java.util.*;
import java.io.Serializable;
import org.hibernate.Session;

/**
 * Class that implements the concrete access to mysql database
 */
public class BookingServiceMysql implements Serializable {

    public Session session;

    public BookingServiceMysql (Session session) {
        this.session = session;
    }

    /**
     * Adds a new booking to the DB
     * @param b - booking to be inserted
     */
    public void createCustomer (Booking b) {
        try {
            System.out.println("[INFO] Creating " +b);
            session.beginTransaction();
            session.save(b);
            session.getTransaction().commit();
        } catch (Exception e) {
            Boolean found = false;
            List<Booking> all_bookings = getAllBookings();
            for (int i = 0; i < all_bookings.size(); i++) {
                if (all_bookings.get(i).equals(b)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                System.out.println("[WARN] Inserting booking by \""+b.get_customer_user().get_nickname()+"\" already present in DB");
            } else {
                System.out.println("[ERROR] Errors while creating new booking");
                e.printStackTrace();
            }
        } finally {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }

    /**
     * Deletes the booking from the DB
     * @param b - booking to be deleted
     */
    public void deleteCustomer (Booking b) {
        System.out.println("[INFO] Deleting " +b);
        session.beginTransaction();
        session.delete(b);
        session.getTransaction().commit();
    }

    /**
     * Updates the booking in the DB
     * @param b - booking to be updated with new information
     */
    public void updateBooking (Booking b) {
        System.out.println("[INFO] Updating " +b);
        session.beginTransaction();
        session.update(b);
        session.getTransaction().commit();
    }

    // ----- METHODS USED FOR FOREIGN KEY -----

    /**
     * Retrieves all the bookings stored in DB
     * @return list of all bookings
     */
    public List<Booking> getAllBookings() {
        return session.createQuery("from Booking b").list();
    }



}
