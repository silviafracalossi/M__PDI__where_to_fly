
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
   public class FareServiceMysql implements Serializable {

     public Session session;

     public FareServiceMysql (Session session) {
         this.session = session;
     }

       /**
        * Adds a new fare to the DB
        * @param f - fare to be inserted
        */
       public void createFare (Fare f) {
           try {
               System.out.println("[INFO] Creating " +f);
               session.beginTransaction();
               session.save(f);
               session.getTransaction().commit();
           } catch (Exception e) {
         	  Boolean found = false;
         	  List<Fare> all_fares = getAllFares();
         	  for (int i = 0; i < all_fares.size(); i++) {
         		  if (all_fares.get(i).equals(f)) {
         			  found = true;
         			  break;
         		  }
         		}
         	  if (found) {
         		  System.out.println("[WARN] Inserting fare \""+f.get_route().get_route_code()+"\" already present in DB");
         	  } else {
         		  System.out.println("[ERROR] Errors while creating new fare");
                   e.printStackTrace();
         	  }
           } finally {
               if (session.getTransaction().isActive()) {
                   session.getTransaction().rollback();
               }
           }
       }

       /**
        * Deletes the fare from the DB
        * @param f - fare to be deleted
        */
       public void deleteFare (Fare f) {
           System.out.println("[INFO] Deleting " +f);
           session.beginTransaction();
           session.delete(f);
           session.getTransaction().commit();
       }

       /**
        * Updates the fare in the DB
        * @param f - fare to be updated with new information
        */
       public void updateFare (Fare f) {
           System.out.println("[INFO] Updating " +f);
           session.beginTransaction();
           session.update(f);
           session.getTransaction().commit();
       }

       // ----- METHODS USED FOR FOREIGN KEY -----

       /**
        * Retrieves all the fares stored in DB
        * @return list of all fares
        */
       public List<Fare> getAllFares() {
           return session.createQuery("from Fare f").list();
       }


 }
