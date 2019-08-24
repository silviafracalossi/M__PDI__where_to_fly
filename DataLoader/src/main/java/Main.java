import org.hibernate.Session;

import java.util.ArrayList;
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
		content = dfr.ParseFile("airlines");
		airline_controller.createMultipleAirlines(content);

		// Airports loader
		AirportController airport_controller = new AirportController(db_session);
		content = dfr.ParseFile("airports");
		airport_controller.createMultipleAirports(content);

		System.out.println("------Execution completed------");

		// TODO trovare un modo per filtrare già gli aeroporti che non hanno LAT e LNG
		// TODO scrivere un po di documentazione

	}

}
