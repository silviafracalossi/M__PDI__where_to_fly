import org.hibernate.Session;

import java.io.Serializable;
import java.util.*;

 /**
  * Class that manages the airline functions and calls the AirlineServiceH2 methods
  */
 public class AirlineController implements Serializable {

    // Serves database-application communication
    private AirlineServiceMysql service;

     public AirlineController (Session session) {
         service = new AirlineServiceMysql(session);
     }


     public void createMultipleAirlines(List<String> airlines) {
         for (int i = 0; i < airlines.size(); i++) {
             createAirline(airlines.get(i));
         }
     }

     public void createAirline(String airline_concat){
     	String[] airline_infos = airline_concat.split(",");

 		Airline airline = new Airline();
 		airline.set_iata_code(airline_infos[0]);
 		airline.set_name(airline_infos[1]);

 		createAirline(airline);
     }

     public void createAirline(Airline a) {
         service.createAirline(a);
     }

     // ----- METHODS USED FOR FOREIGN KEY -----

     public List<Airline> getAllAirlines() { return service.getAllAirlines(); }

     public Airline getAirlineByPk(String iata_code) {
         return service.getAirlineByPk(iata_code);
     }

 }
