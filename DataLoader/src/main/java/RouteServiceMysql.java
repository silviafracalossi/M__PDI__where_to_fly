
 import java.util.*;
 import java.io.Serializable;
 import org.hibernate.Session;

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

        // ----- METHODS USED FOR FOREIGN KEY -----

        /**
        * Retrieves all the routes stored in DB
        * @param route_code pk of route
        * @return route with "route_code" as pk
        */
        public Route getRouteByPk(String route_code) {
            List<Route> routes = session.createQuery("from Route r where r.route_code LIKE :parameter")
                    .setParameter("parameter", "%"+route_code+"%")
                    .list();

            return (routes.size() > 0) ? routes.get(0) : new Route();
        }

         /**
          * Retrieves all the routes stored in DB
          * @return list of all routes
          */
         public List<Route> getAllRoutes() {
             return session.createQuery("from Route r").list();
         }

        /**
        * Retrieves all the routes stored in DB, along with airport information
        * @return list of all routes with airports infos
        */
        public List<Route> getAllCompleteRoutes() {
         return session.createQuery("from Route r").list();
     }


 }
