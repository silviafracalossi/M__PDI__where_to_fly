
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
   public class RouteServiceMysql implements Serializable {

     public Session session;

     public RouteServiceMysql (Session session) {
         this.session = session;
     }

       /**
        * Adds a new route to the DB
        * @param r - route to be inserted
        */
       public void createRoute (Route r) {
           try {
               System.out.println("[INFO] Creating " +r);
               session.beginTransaction();
               session.save(r);
               session.getTransaction().commit();
           } catch (Exception e) {
         	  Boolean found = false;
         	  List<Route> all_routes = getAllRoutes();
         	  for (int i = 0; i < all_routes.size(); i++) {
         		  if (all_routes.get(i).equals(r)) {
         			  found = true;
         			  break;
         		  }
         		}
         	  if (found) {
         		  System.out.println("[WARN] Inserting route \""+r.get_route_code()+"\" already present in DB");
         	  } else {
         		  System.out.println("[ERROR] Errors while creating new route");
                   e.printStackTrace();
         	  }
           } finally {
               if (session.getTransaction().isActive()) {
                   session.getTransaction().rollback();
               }
           }
       }

       /**
        * Deletes the route from the DB
        * @param r - route to be deleted
        */
       public void deleteRoute (Route r) {
           System.out.println("[INFO] Deleting " +r);
           session.beginTransaction();
           session.delete(r);
           session.getTransaction().commit();
       }

       /**
        * Updates the route in the DB
        * @param r - route to be updated with new information
        */
       public void updateRoute (Route r) {
           System.out.println("[INFO] Updating " +r);
           session.beginTransaction();
           session.update(r);
           session.getTransaction().commit();
       }

       /**
        * Retrieves all the routes stored in DB
        * @return list of all routes
        */
       public List<Route> getAllRoutes() {
           return session.createQuery("from Route r").list();
       }


 }
