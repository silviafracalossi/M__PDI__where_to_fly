
public class Main {

	public static void main(String[] args) {

	  // Starts the ETL processes
		AirlineETL airline_etl = new AirlineETL();
		AirportETL airport_etl = new AirportETL();
		FlightETL flight_etl = new FlightETL();
		AirfareETL airfare_etl = new AirfareETL();
	}

}
