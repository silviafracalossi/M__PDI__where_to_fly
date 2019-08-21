import java.io.Serializable;
import models.Airline;

/**
 * Class that manages the airline functions and calls the AirlineServiceH2 methods
 */
public class AirlineController implements Serializable {

	private AirlineServiceMysql as_mysql = new AirlineServiceMysql();

    public void printaAirline(String airline_concat){
    	String[] airline_infos = airline_concat.split(",");

		Airline airline = new Airline();
		airline.set_iata_code(airline_infos[0]);
		airline.set_name(airline_infos[1]);

		System.out.println(airline);

    }
}
