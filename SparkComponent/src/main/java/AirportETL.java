
import org.apache.spark.api.java.*;
import org.apache.spark.sql.SparkSession;

public class AirportETL {

	public AirportETL () {

		// CSV source path and output path definition
		String airport_header = "IATA_CODE,AIRPORT,CITY,STATE,COUNTRY,LATITUDE,LONGITUDE";
		String airports_csv = "../data/kaggle_airports.csv";
		String outputPath = "../parsed_data/airports";

		// Create a Spark Session object and set the name of the application
		SparkSession ss = SparkSession.builder().appName("Airports Filtering Data").getOrCreate();

		// Create a Java Spark Context from the Spark Session
		JavaSparkContext sc = new JavaSparkContext(ss.sparkContext());

		// Getting data from airports file
		JavaRDD<String> airports_data = sc.textFile(airports_csv);

		// Saving the content in a file
		airports_data.saveAsTextFile(outputPath);

		// Close the Spark Context object
		sc.close();

	}
}
