
 import org.hibernate.cfg.Configuration;
 import java.util.*;
 import org.hibernate.*;
 import org.hibernate.cfg.*;
 import java.io.Serializable;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.SQLException;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.boot.MetadataSources;
 import org.hibernate.boot.registry.StandardServiceRegistry;
 import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
 import org.hibernate.exception.ConstraintViolationException;

 /**
    * Class that implements the concrete access to mysql database
    */
   public class AirportServiceMysql implements Serializable {

     public Session session;

     public AirportServiceMysql (Session session) {
         this.session = session;
     }

       /**
        * Adds a new airport to the DB
        * @param a - airport to be inserted
        */
       public void createAirport (Airport a) {
           try {
               System.out.println("[INFO] Creating " +a);
               session.beginTransaction();
               session.save(a);
               session.getTransaction().commit();
           } catch (Exception e) {
         	  Boolean found = false;
         	  List<Airport> all_airports = getAllAirports();
         	  for (int i = 0; i < all_airports.size(); i++) {
         		  if (all_airports.get(i).equals(a)) {
         			  found = true;
         			  break;
         		  }
         		}
         	  if (found) {
         		  System.out.println("[WARN] Inserting airport \""+a.get_iata_code()+"\" already present in DB");
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
        * Deletes the airport from the DB
        * @param a - airport to be deleted
        */
       public void deleteAirport (Airport a) {
           System.out.println("[INFO] Deleting " +a);
           session.beginTransaction();
           session.delete(a);
           session.getTransaction().commit();
       }

       /**
        * Updates the airport in the DB
        * @param a - airport to be updated with new information
        */
       public void updateAirport (Airport a) {
           System.out.println("[INFO] Updating " +a);
           session.beginTransaction();
           session.update(a);
           session.getTransaction().commit();
       }

        // ----- METHODS USED FOR FOREIGN KEY -----

        /**
        * Retrieves all the airports stored in DB
        * @return list of all airports
        */
        public List<Airport> getAllAirports() {
         return session.createQuery("from Airport a").list();
        }

        public Airport getAirportByPk (String iata_code) {
            List<Airport> airports = session.createQuery("from Airport a where a.iata_code LIKE :iata_parameter")
                    .setParameter("iata_parameter", "%"+iata_code+"%")
                    .list();
            return (airports.size() > 0) ? airports.get(0) : new Airport();
        }

        public Airport getAirportByCityState (String city, String state) {
            List<Airport> airports = session.createQuery(
                    "from Airport a where replace(lower(a.city), ' ', '') LIKE :city_parameter and a.state LIKE :state_parameter")
                    .setParameter("city_parameter", "%"+city.toLowerCase().replaceAll("\\s+", "")+"%")
                    .setParameter("state_parameter", "%"+state+"%")
                    .list();
            return (airports.size() > 0) ? airports.get(0) : new Airport();
        }


 }
