
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
  
  /**
   * Class that implements the concrete access to mysql database
   */
  public class AirlineServiceMysql implements Serializable {

      public static String DB_URL = "jdbc:mysql://localhost/wheretofly";
      public static String username = "silvia";
      public static String password = "aPassword!";

      private static final SessionFactory sessionFactory = buildSessionFactory();
      private static final Connection connection = getConnection();                // variable to establish the connection with db
      public static Session session = openSession();                               // variable to set up queries


      /**
       * Creates the SessionFactory using hibernate.cfg.xml configuration file
       * @return the created sessionFactory
       */
      private static SessionFactory buildSessionFactory() {
          try {
              Configuration c = new Configuration();
              c.configure();
              c.addAnnotatedClass(Airline.class);
              return c.buildSessionFactory();
          } catch (Exception ex) {
              System.out.println("Initial SessionFactory creation failed.");
              System.err.println(ex);
              throw new ExceptionInInitializerError(ex);
          }
      }

      /**
       * Establishes the connection with the DB
       * @return connection variable
       */
      private static Connection getConnection() {
          try {
              return DriverManager.getConnection(DB_URL, username, password);
          } catch (SQLException e) {
              System.out.println("Initial getConnection failed.");
              System.err.println(e);
              throw new ExceptionInInitializerError(e);
          }
      }

      /**
       * Opens a new session with the DB
       * @return new session
       */
      private static Session openSession() {
          return sessionFactory.openSession();
      }

      /**
       * Closes the session and the sessionFactory with the DB
       */
      public void closeConnection(){
          if (connection != null) {
              sessionFactory.close();
              session.close();
          }
      }
      
    //--------------------------------Methods for Airlines-------------------------------------

      /**
       * Adds a new airline to the DB
       * @param a - airline to be inserted
       */
      public void createAirline (Airline a) {
          try {
              session.beginTransaction();
              session.save(a);
              session.getTransaction().commit();
          } catch (HibernateException e) {
        	  Boolean found = false;
        	  List<Airline> all_airlines = getAllAirlines();
        	  for (int i = 0; i < all_airlines.size(); i++) {
        		  if (all_airlines.get(i).equals(a)) {
        			  found = true;
        			  System.out.println("found");
        			  break;
        		  }
        		}
        	  if (found) {
        		  System.out.println("Airline "+a.get_iata_code()+" already present in DB");
        	  } else {
        		  System.out.println("Errors while creating new user");
                  e.printStackTrace();
        	  }
             
          }
      }

      /**
       * Deletes the airline from the DB
       * @param a - airline to be deleted
       */
      public void deleteAirline (Airline a) {
          session.beginTransaction();
          session.delete(a);
          session.getTransaction().commit();
      }

      /**
       * Updates the airline in the DB
       * @param a - airline to be updated with new information
       */
      public void updateAirline (Airline a) {
          session.beginTransaction();
          session.update(a);
          session.getTransaction().commit();
      }
      
      /**
       * Retrieves all the airlines stored in DB
       * @return list of all airlines
       */
      public List<Airline> getAllAirlines() {
          return session.createQuery("from Airline a").list();
      }


}
