
import org.apache.spark.api.java.*;
import org.apache.spark.sql.SparkSession;


public class AirlineETL {

	public AirlineETL () {

		// Handles the interations between ETL and Database Service Class
		AirlineController controller = new AirlineController();

		// CSV source path and output path definition
		String airline_header = "IATA_CODE,AIRLINE";
		String airlines_csv = "data/kaggle_airlines.csv";
		String outputPath = "ex_out";


		// Create a Spark Session object and set the name of the application
		SparkSession ss = SparkSession.builder().appName("Airlines Filtering Data").getOrCreate();

		// Create a Java Spark Context from the Spark Session
		JavaSparkContext sc = new JavaSparkContext(ss.sparkContext());

		// Getting data from airlines file
		// Structure of airlines: IATA code, name
		JavaRDD<String> airlines_data = sc.textFile(airlines_csv);

		// Creating instances of Airline
		airlines_data.foreach((airline) -> {
			if (airline.equals(airline_header)) {
				controller.printaAirline(airline);
			}
		});

		// Saving the content in a file
		airlines_data.saveAsTextFile(outputPath);

		// Close the Spark Context object
		sc.close();

	}
}
