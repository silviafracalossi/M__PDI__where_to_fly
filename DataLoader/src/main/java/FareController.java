
import org.hibernate.Session;
import java.io.Serializable;
import java.util.*;

 /**
  * Class that manages the fare functions and calls the FareServiceH2 methods
  */
 public class FareController implements Serializable {

    // Serves database-application communication
    private FareServiceMysql service;
    private RouteController rc;
    private AirportController ac;

     public FareController (Session session) {
         this.service = new FareServiceMysql(session);
         rc = new RouteController(session);
         ac = new AirportController(session);
     }

     public void createMultipleFares(List<String> fares) {
         for (int i = 0; i < fares.size(); i++) {
             createFare(fares.get(i));
         }
     }

     // 
     public void createFare(String fare_concat){

        // Remove the initial "[" and the final "]" characters
        if (fare_concat.charAt(0) == '[') {
            fare_concat = fare_concat.substring(1, fare_concat.length()-1);
        }

        // Splitting the input
        String[] fare_infos = fare_concat.split(",");

        // Creating multiple fares since one fare may represent multiple cities
        Fare fare;
        Route route;
        Airport origin_iata_code;
        String[] origin_cities = fare_infos[3].split("/");
        Airport destination_iata_code;
        String[] destination_cities = fare_infos[5].split("/");

        // Looping over origin-destination combinations
        for (int i = 0; i<origin_cities.length; i++) {
            origin_iata_code = ac.getAirportByCityState(origin_cities[i], fare_infos[4]);
            for (int j = 0; j<destination_cities.length; j++) {
                destination_iata_code = ac.getAirportByCityState(destination_cities[j], fare_infos[6]);
                if (origin_iata_code.get_iata_code() != null && destination_iata_code.get_iata_code() != null) {
                    System.out.println(origin_iata_code.get_iata_code() + "_" + destination_iata_code.get_iata_code());
                    route = rc.getRouteByPk(origin_iata_code.get_iata_code() + "_" + destination_iata_code.get_iata_code());
                    if (route.get_route_code() != null) {
                        fare = new Fare();
                        fare.set_route_code(route);
                        fare.set_year(Integer.parseInt(fare_infos[0]));
                        fare.set_quarter(Integer.parseInt(fare_infos[1]));
                        fare.set_price(Float.parseFloat(fare_infos[2]));

                        System.out.println("Inserting " +fare);
                        createFare(fare);
                    } else {
                        System.out.println("[WARN] Fare not inserted since there is no route correspondence");
                    }
                } else {
                    System.out.println("[WARN] Fare not inserted since there is no airport correspondence");
                }
            }
        }
     }

     public void createFare(Fare f) {
         service.createFare(f);
     }
 }
