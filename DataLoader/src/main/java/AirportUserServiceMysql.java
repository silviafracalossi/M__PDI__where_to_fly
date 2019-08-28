
import java.util.*;
import java.io.Serializable;
import org.hibernate.Session;

/**
 * Class that implements the concrete access to mysql database
 */
public class AirportUserServiceMysql implements Serializable {

    public Session session;

    public AirportUserServiceMysql (Session session) {
        this.session = session;
    }

    /**
     * Adds a new airport user to the DB
     * @param a - airport user to be inserted
     */
    public void createAirport (AirportUser a) {
        try {
            System.out.println("[INFO] Creating " +a);
            session.beginTransaction();
            session.save(a);
            session.getTransaction().commit();
        } catch (Exception e) {
            Boolean found = false;
            List<AirportUser> all_airport_users = getAllAirportUsers();
            for (int i = 0; i < all_airport_users.size(); i++) {
                if (all_airport_users.get(i).equals(a)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                System.out.println("[WARN] Inserting airport \""+a.get_airport_iata_code().get_iata_code()+"\" already present in DB");
            } else {
                System.out.println("[ERROR] Errors while creating new airport");
                e.printStackTrace();
            }
        } finally {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }

    /**
     * Deletes the airport user from the DB
     * @param a - airport user to be deleted
     */
    public void deleteAirport (AirportUser a) {
        System.out.println("[INFO] Deleting " +a);
        session.beginTransaction();
        session.delete(a);
        session.getTransaction().commit();
    }

    /**
     * Updates the airport user in the DB
     * @param a - airport user to be updated with new information
     */
    public void updateAirportUser (AirportUser a) {
        System.out.println("[INFO] Updating " +a);
        session.beginTransaction();
        session.update(a);
        session.getTransaction().commit();
    }

    // ----- METHODS USED FOR FOREIGN KEY -----

    /**
     * Retrieves all the airport users stored in DB
     * @return list of all airport users
     */
    public List<AirportUser> getAllAirportUsers() {
        return session.createQuery("from AirportUser a").list();
    }



}
