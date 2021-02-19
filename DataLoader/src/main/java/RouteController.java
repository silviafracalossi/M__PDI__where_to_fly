
import org.hibernate.Session;
import java.io.Serializable;
import java.util.*;

/**
 * Class that manages the route functions and calls the RouteServiceH2 methods
 */
public class RouteController implements Serializable {

    // Serves database-application communication
    private RouteServiceMysql service;
    private AirportController ac;

    public RouteController (Session session) {
        ac = new AirportController(session);
        service = new RouteServiceMysql(session);
    }

    public void createMultipleRoutes(List<String> routes) {
        for (int i = 0; i < routes.size(); i++) {
            createRoute(routes.get(i));
        }
    }

    // Data structure in file: ORIGIN_AIRPORT|DESTINATION_AIRPORT|ROUTE_CODE
    public void createRoute(String route_concat){

        // Remove the initial "[" and the final "]" characters
        if (route_concat.charAt(0) == '[') {
            route_concat = route_concat.substring(1, route_concat.length()-1);
        }

        // Splitting route
        String[] route_infos = route_concat.split(",");
        Airport origin_airport = ac.getAirportByPk(route_infos[0]);
        Airport destination_airport = ac.getAirportByPk(route_infos[1]);

        // Checking if there is a correspondence
        if (origin_airport.get_iata_code() != null && destination_airport.get_iata_code() != null) {
            Route route = new Route();
            route.set_origin_airport(origin_airport);
            route.set_destination_airport(destination_airport);
            route.set_route_code(route_infos[2]);
            createRoute(route);
        } else {
            System.out.println("[WARN] No airport correspondence while creating route");
        }

    }

    public void createRoute(Route r) {
        service.createRoute(r);
    }

    // ----- METHODS USED FOR FOREIGN KEY -----

    public List<Route> getAllRoutes() { return service.getAllRoutes(); }

    public Route getRouteByPk(String route_code) {
        return service.getRouteByPk(route_code);
    }

}
