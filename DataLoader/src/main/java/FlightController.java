
import org.hibernate.Session;
import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

 /**
  * Class that manages the flight functions and calls the FlightServiceH2 methods
  */
 public class FlightController implements Serializable {

    // Serves database-application communication
    private FlightServiceMysql service;
    private AirlineController ac;
    private RouteController rc;

     public FlightController (Session session) {
         service = new FlightServiceMysql(session);
         ac = new AirlineController(session);
         rc = new RouteController(session);
     }

     public void createMultipleFlights(List<String> flights) {
         for (int i = 0; i < flights.size(); i++) {
             createFlight(flights.get(i));
         }
     }

     // Structure: AIRLINE|FLIGHT_NUMBER|SCHEDULED_DEPARTURE|DEPARTURE_TIME|SCHEDULED_ARRIVAL|ARRIVAL_TIME|ROUTE_CODE|DATE
     public Flight createFlight(String flight_concat){

        // Remove the initial "[" and the final "]" characters
        if (flight_concat.charAt(0) == '[') {
            flight_concat = flight_concat.substring(1, flight_concat.length()-1);
        }

        // Splitting data
        String[] flight_infos = flight_concat.split(",");
        DateFormat time_formatter = new SimpleDateFormat("hhmm");
        SimpleDateFormat day_sdf = new SimpleDateFormat("dd-MM-yyyy");
        Flight flight = new Flight();

        // Creating flight by setting the new information
        try {
            Airline airline = ac.getAirlineByPk(flight_infos[0]);
            Route route = rc.getRouteByPk(flight_infos[6]);

            if (airline.get_iata_code() != null && route.get_route_code() != null) {
                flight.set_airline_iata(airline);
                flight.set_flight_number(flight_infos[1]);
                flight.set_scheduled_departure(new Time(time_formatter.parse(flight_infos[2]).getTime()));
                flight.set_departure_time(new Time(time_formatter.parse(flight_infos[3]).getTime()));
                flight.set_scheduled_arrival(new Time(time_formatter.parse(flight_infos[4]).getTime()));
                flight.set_arrival_time(new Time(time_formatter.parse(flight_infos[5]).getTime()));
                flight.set_route_code(route);
                flight.set_date(day_sdf.parse(flight_infos[7]));
                createFlight(flight);
            }

        } catch (Exception e) {
            System.out.println("[ERROR] Errors creating the flight: " + flight_concat);
        }
        return flight;
     }

     public void createFlight(Flight f) {
         service.createFlight(f);
     }

     // ----- METHODS USED FOR FOREIGN KEY -----

     public List<Flight> getAllFlights() { return service.getAllFlights(); }

     public Flight getFlightByPk(String flight_route, String flight_number, String date) {
         Route route = rc.getRouteByPk(flight_route);
         if (route.get_route_code() != null) {
             return service.getFlightByPk(route, flight_number, date);
         }
         return new Flight();
     }

     public List<Flight> getFilteredFlights (Airport origin_airport, Airport destination_airport) {
         return service.getFilteredFlights(origin_airport, destination_airport);
     }

 }
