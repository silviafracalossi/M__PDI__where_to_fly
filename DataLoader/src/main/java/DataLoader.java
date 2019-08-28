
import org.hibernate.Session;
import java.util.List;

public class DataLoader {

	public DataLoader (Session db_session) {

		System.out.println("------Execution started------");

		// Object of useful classes
		List<String> content;
		DataFileReader dfr = new DataFileReader();

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
	}

}
