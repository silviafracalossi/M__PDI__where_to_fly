
import org.hibernate.Session;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		// Creating the connection with the db
		DatabaseConnector db_connector = new DatabaseConnector();
		Session db_session = db_connector.session;

		// Loads on the db the data
		// DataLoader dl = new DataLoader(db_session);

		FlightController fc = new FlightController(db_session);
		Flight flight = fc.createFlight("[UA,414,0625,0627,0754,0742,LAX_SFO,27-11-2015]");
		System.out.println(flight);

		// AirportUser handling objects
		/*AirportUserController airport_user_controller = new AirportUserController(db_session);
		AirportUser as = airport_user_controller.createAirportUser("ABE");
		System.out.println(as);

		// CustomerUser handling objects*/
		/*CustomerUserController customer_user_controller = new CustomerUserController(db_session);
		CustomerUser customer = customer_user_controller.createCustomerUser("nickname,name,lastname");
		System.out.println(customer);*/

		System.exit(0);
	}

}
