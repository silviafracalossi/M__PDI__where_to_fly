
import java.awt.EventQueue;
import java.util.List;
import org.hibernate.Session;

public class Main {

	public static void main(String[] args) {

		// Creating the connection with the db
		DatabaseConnector db_connector = new DatabaseConnector();
		Session db_session = db_connector.session;


		// Initialize the interface
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartWindow window = new StartWindow(db_session);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Getting all the analytics
		/*AnalyticsServiceMysql asm = new AnalyticsServiceMysql(db_session);
		asm.generateVisualization();*/


	}

}
