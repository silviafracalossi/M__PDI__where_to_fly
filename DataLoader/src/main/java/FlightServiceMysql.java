
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
   public class FlightServiceMysql implements Serializable {

       public Session session;

     public FlightServiceMysql (Session session) {
         this.session = session;
     }

       /**
        * Adds a new flight to the DB
        * @param f - flight to be inserted
        */
       public void createFlight (Flight f) {
           try {
               System.out.println("[INFO] Creating " +f);
               session.beginTransaction();
               session.save(f);
               session.getTransaction().commit();
           } catch (Exception e) {
         	  Boolean found = false;
         	  List<Flight> all_flights = getAllFlights();
         	  for (int i = 0; i < all_flights.size(); i++) {
         		  if (all_flights.get(i).equals(f)) {
         			  found = true;
         			  break;
         		  }
         		}
         	  if (found) {
         		  System.out.println("[WARN] Inserting flight \""+f.get_flight_number()+"\" already present in DB");
         	  } else {
         		  System.out.println("[ERROR] Errors while creating new flight");
                   e.printStackTrace();
         	  }
           } finally {
               if (session.getTransaction().isActive()) {
                   session.getTransaction().rollback();
               }
           }
       }

       /**
        * Deletes the flight from the DB
        * @param f - flight to be deleted
        */
       public void deleteFlight (Flight f) {
           System.out.println("[INFO] Deleting " +f);
           session.beginTransaction();
           session.delete(f);
           session.getTransaction().commit();
       }

       /**
        * Updates the flight in the DB
        * @param f - flight to be updated with new information
        */
       public void updateFlight (Flight f) {
           System.out.println("[INFO] Updating " +f);
           session.beginTransaction();
           session.update(f);
           session.getTransaction().commit();
       }

       // ----- METHODS USED FOR FOREIGN KEY -----

       /**
        * Retrieves all the flights stored in DB
        * @return list of all flights
        */
       public List<Flight> getAllFlights() {
           return session.createQuery("from Flight f ").list();
       }

        /**
        * Retrieves all the flights stored in DB
        * @param route route of flight
        * @param flight_number number of flight
        * @param date date of the flight
        * @return flight with asked pk
        */
        public Flight getFlightByPk(Route route, String flight_number, String date) {
            String[] splitted_date = date.split("-");
             List<Flight> flights = session.createQuery(
                     "from Flight f " +
                             "where f.route_code = :route_param " +
                             "and flight_number LIKE :number_param " +
                             "and day(date) = :day_param " +
                             "and month(date) = :month_param " +
                             "and year(date) = :year_param ")
                     .setParameter("route_param", route)
                     .setParameter("number_param", "%"+flight_number+"%")
                     .setParameter("day_param", Integer.parseInt(splitted_date[2]))
                     .setParameter("month_param", Integer.parseInt(splitted_date[1]))
                     .setParameter("year_param", Integer.parseInt(splitted_date[0]))
                     .list();
             return (flights.size() > 0) ? flights.get(0) : new Flight();
        }

         public List<Flight> getFilteredFlights(Airport origin_airport, Airport destination_airport) {
             String query = "from Flight f ";
             Boolean first_where = true;

             if (origin_airport.get_iata_code() != null) {
                 if (first_where) {
                     query += " where ";
                     first_where = false;
                 }
                 query += "f.route_code.origin_airport LIKE :origin_param";
             }

             if (destination_airport.get_iata_code() != null) {
                 if (first_where) {
                     query += " where ";
                     first_where = false;
                 } else {
                     query += " and ";
                 }
                 query += "f.route_code.destination_airport LIKE :destination_param";
             }

             Query final_query = session.createQuery(query);
             if (origin_airport.get_iata_code() != null) {
                final_query.setParameter("origin_param", origin_airport);
             }
             if (destination_airport.get_iata_code() != null) {
                 final_query.setParameter("destination_param", destination_airport);
             }

             return final_query.setMaxResults(15).list();
         }


 }
