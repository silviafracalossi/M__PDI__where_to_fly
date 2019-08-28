
import org.hibernate.Session;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		// Creating the connection with the db
		DatabaseConnector db_connector = new DatabaseConnector();
		Session db_session = db_connector.session;

		// If triggered, loads on the db the data
		if (false) {
			DataLoader dl = new DataLoader(db_session);
		}


		System.exit(0);
	}

}
