
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
   public class AirlineServiceMysql implements Serializable {

       public Session session;

     public AirlineServiceMysql (Session session) {
         this.session = session;
     }

       /**
        * Adds a new airline to the DB
        * @param a - airline to be inserted
        */
       public void createAirline (Airline a) {
           try {
               System.out.println("[INFO] Creating " +a);
               session.beginTransaction();
               session.save(a);
               session.getTransaction().commit();
           } catch (Exception e) {
         	  Boolean found = false;
         	  List<Airline> all_airlines = getAllAirlines();
         	  for (int i = 0; i < all_airlines.size(); i++) {
         		  if (all_airlines.get(i).equals(a)) {
         			  found = true;
         			  break;
         		  }
         		}
         	  if (found) {
         		  System.out.println("[WARN] Inserting airline \""+a.get_iata_code()+"\" already present in DB");
         	  } else {
         		  System.out.println("[ERROR] Errors while creating new airline");
                   e.printStackTrace();
         	  }
           } finally {
               if (session.getTransaction().isActive()) {
                   session.getTransaction().rollback();
               }
           }
       }

       /**
        * Deletes the airline from the DB
        * @param a - airline to be deleted
        */
       public void deleteAirline (Airline a) {
           System.out.println("[INFO] Deleting " +a);
           session.beginTransaction();
           session.delete(a);
           session.getTransaction().commit();
       }

       /**
        * Updates the airline in the DB
        * @param a - airline to be updated with new information
        */
       public void updateAirline (Airline a) {
           System.out.println("[INFO] Updating " +a);
           session.beginTransaction();
           session.update(a);
           session.getTransaction().commit();
       }

        // ----- METHODS USED FOR FOREIGN KEY -----

        /**
        * Retrieves all the airlines stored in DB
        * @return list of all airlines
        */
        public List<Airline> getAllAirlines() {
         return session.createQuery("from Airline a").list();
        }

        /**
        * Retrieves airline with given pk
        * @param iata_code pk of airline
        * @return airline with "iata_code" as pk
        */
        public Airline getAirlineByPk(String iata_code) {
            List<Airline> airlines = session.createQuery("from Airline a where a.iata_code LIKE :parameter")
                .setParameter("parameter", "%"+iata_code+"%")
                .list();
            return (airlines.size() > 0) ? airlines.get(0) : new Airline();
        }


 }
