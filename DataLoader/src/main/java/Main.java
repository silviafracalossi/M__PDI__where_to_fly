
import org.hibernate.Session;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		// Creating the connection with the db
		DatabaseConnector db_connector = new DatabaseConnector();
		Session db_session = db_connector.session;

		// If triggered, loads on the db the data
		if (true) {
			DataLoader dl = new DataLoader(db_session);
		}


		System.exit(0);

		// TODO DOCUMENTAZIONE
        //  scrivere un po di documentazione
        //  sed ':a;N;$!ba;s/\n(/ (/g' transportation_airfare_not_parsed.csv > transportation_airfare.csv

	}

}
