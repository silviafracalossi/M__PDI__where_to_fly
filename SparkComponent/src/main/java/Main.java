
public class Main {

	// ------------Files paths - useless for now-------------
	// String inputFileTraining = "data/old/trainingData.txt";
	// String inputFileTest = "data/old/unlabeledData.txt";
	// String outputPath = "ex_out";
	// String outputFile = outputPath+"/output.csv";
	// String airfare_csv = "data/transportation_airfare.csv";
	// String airlines_csv = "data/kaggle_airlines.csv";
	// String airports_csv = "data/kaggle_airports.csv";
	// String flights_csv = "data/kaggle_flights.csv";
	// -------------------------------------------------------

	public static void main(String[] args) {
		AirlineETL airline_etl = new AirlineETL();
		// X_AirlinesParser ap = new X_AirlinesParser();
		X_ShowGeneratedHtml sgh = new X_ShowGeneratedHtml();
	}

}
