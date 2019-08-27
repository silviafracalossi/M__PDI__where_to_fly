
import org.hibernate.Session;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		System.out.println("------Execution started------");

		// Object of useful classes
		List<String> content;
		DataFileReader dfr = new DataFileReader();
		DatabaseConnector db_connector = new DatabaseConnector();
		Session db_session = db_connector.session;

		// Airlines loader
		AirlineController airline_controller = new AirlineController(db_session);
		content = dfr.ParseFile("airlines", true);
		airline_controller.createMultipleAirlines(content);

		// Airports loader
		AirportController airport_controller = new AirportController(db_session);
		content = dfr.ParseFile("airports", true);
		airport_controller.createMultipleAirports(content);

		//Routes loader
		RouteController route_controller = new RouteController(db_session);
		content = dfr.ParseFile("routes", false);
		route_controller.createMultipleRoutes(content);

		// Flights loader
		FlightController flight_controller = new FlightController(db_session);
		content = dfr.ParseFile("flights", false);
		flight_controller.createMultipleFlights(content);

		 // Fares loader
		 FareController fare_controller = new FareController(db_session);
		 content = dfr.ParseFile("fares", false);
		 fare_controller.createMultipleFares(content);

		System.out.println("------Execution completed------");
		System.exit(0);

		// TODO DOCUMENTAZIONE
        //  scrivere un po di documentazione
        //  sed ':a;N;$!ba;s/\n(/ (/g' transportation_airfare_not_parsed.csv > transportation_airfare.csv

	}

}
