
import org.hibernate.Session;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		// Creating the connection with the db
		DatabaseConnector db_connector = new DatabaseConnector();
		Session db_session = db_connector.session;

		// Loads on the db the data
		// DataLoader dl = new DataLoader(db_session);

		// AirportUser handling objects
		/*AirportUserController airport_user_controller = new AirportUserController(db_session);
		AirportUser as = airport_user_controller.createAirportUser("ABE");
		System.out.println(as);

		// CustomerUser handling objects*/
		/*CustomerUserController customer_user_controller = new CustomerUserController(db_session);
		CustomerUser customer = customer_user_controller.createCustomerUser("nickname,name,lastname");
		System.out.println(customer);*/

		// Booking handling objects - TODO fix reference to flight table
		BookingController booking_user_controller = new BookingController(db_session);
		Booking booking = booking_user_controller.createBooking("ABE_ATL,1084,2015-01-13", "nickname", 3);
		System.out.println(booking);

		System.exit(0);
	}

}
