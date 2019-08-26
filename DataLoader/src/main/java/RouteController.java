
import org.hibernate.Session;
import java.io.Serializable;
import java.util.*;

/**
 * Class that manages the route functions and calls the RouteServiceH2 methods
 */
public class RouteController implements Serializable {

    // Serves database-application communication
    private RouteServiceMysql service;

    public RouteController (Session session) {
        service = new RouteServiceMysql(session);
    }


    public void createMultipleRoutes(List<String> routes) {
        for (int i = 0; i < routes.size(); i++) {
            createRoute(routes.get(i));
        }
    }

    // Data structure in file: AIRLINE|ORIGIN_AIRPORT|DESTINATION_AIRPORT|ROUTE_CODE
    public void createRoute(String route_concat){

        // Remove the initial "[" and the final "]" characters
        if (route_concat.charAt(0) == '[') {
            route_concat = route_concat.substring(1, route_concat.length()-1);
        }

        String[] route_infos = route_concat.split(",");
        System.out.println(route_concat);

         Route route = new Route();
         route.set_airline(route_infos[0]);
         route.set_origin_airport(route_infos[1]);
         route.set_destination_airport(route_infos[2]);
         route.set_route_code(route_infos[3]);
         createRoute(route);
    }

    public void createRoute(Route r) {
        service.createRoute(r);
    }
}
