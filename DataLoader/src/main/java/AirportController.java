import org.hibernate.Session;

import java.io.Serializable;
import java.util.*;

 /**
  * Class that manages the airport functions and calls the AirportServiceH2 methods
  */
 public class AirportController implements Serializable {

    // Serves database-application communication
 	private AirportServiceMysql service;

 	public AirportController (Session session) {
 	    service = new AirportServiceMysql(session);
    }

     public void createMultipleAirports(List<String> airports) {
         for (int i = 0; i < airports.size(); i++) {
             createAirport(airports.get(i));
         }
     }

     public void createAirport(String airport_concat){
     	String[] airport_infos = airport_concat.split(",");

        Airport airport = new Airport();
        airport.set_iata_code(airport_infos[0]);
        airport.set_name(airport_infos[1]);
        airport.set_city(airport_infos[2]);
        airport.set_state(airport_infos[3]);
        airport.set_country(airport_infos[4]);
        airport.set_lat(airport_infos[5]);
        airport.set_lng(airport_infos[6]);
        createAirport(airport);

     }

     public void createAirport(Airport a) {
         service.createAirport(a);
     }

     // ----- METHODS USED FOR FOREIGN KEY -----

     public List<Airport> getAllAirports () { return service.getAllAirports(); }

     public Airport getAirportByPk (String iata_code) {
 	    return service.getAirportByPk(iata_code);
     }

     public Airport getAirportByCityState (String city, String state) {
         return service.getAirportByCityState(city, state);
     }

 }
