
import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {

		// Creating the connection with the db
		//DatabaseConnector db_connector = new DatabaseConnector();
		//Session db_session = db_connector.session;

		// Initialize the interface
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
