
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

public class AirportETL {

	public AirportETL () {

		// CSV source path and output path definition
		String airports_csv = "../data/kaggle_airports.csv";
		String outputPath = "../parsed_data/airports";

		// Create a Spark Session object and set the name of the application
		SparkSession ss = SparkSession.builder().appName("Airports Filtering Data").getOrCreate();

		// Create a Java Spark Context from the Spark Session
		JavaSparkContext sc = new JavaSparkContext(ss.sparkContext());

		// Getting data from airports file
		JavaRDD<String> airports_data = sc.textFile(airports_csv);

		// Map each airport to tuple (airport data, number of String that composes it)
		JavaPairRDD<String, Integer> airportRDD = airports_data.mapToPair(a -> new Tuple2<>(a, a.split(",").length));

		// Creates filter function, which removes the airports with incomplete information (aka that have not 7 fields)
		Function<Tuple2<String, Integer>, Boolean> filterFunction = a -> (a._2 == 7);

		// apply the filter on wordsRDD
		JavaPairRDD<String, Integer> rddf = airportRDD.filter(filterFunction);

		// converting back the JavaPairRDD in a JavaRDD
		JavaRDD<String> airports_to_be_saved = rddf.map(x -> x._1);

		// Saving the content in a file
		airports_to_be_saved.saveAsTextFile(outputPath);

		// Close the Spark Context object
		sc.close();

	}
}
