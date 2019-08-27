
import org.apache.spark.sql.*;
import java.io.Serializable;
import org.apache.spark.api.java.JavaSparkContext;
import static org.apache.spark.sql.functions.*;

public class FlightETL implements Serializable {

    public FlightETL () {

        // CSV source path and output path definition
        // String flights_csv = "../data/kaggle_flights.csv";
        String flights_csv = "../data/flights_short.csv";
        String outputFlightPath = "../parsed_data/flights";
        String outputRoutePath = "../parsed_data/routes";

        // Create a Spark Session object and set the name of the application
        SparkSession ss = SparkSession.builder().appName("Flights Filtering Data").getOrCreate();

        // Create a Java Spark Context from the Spark Session
        JavaSparkContext sc = new JavaSparkContext(ss.sparkContext());

        // --------- Elaborating ROUTES entity ---------

        // Loading data into a dataset
        Dataset<Row> route_df = ss.read()
                .format("com.databricks.spark.csv")
                .option("header", "true")
                .load(flights_csv);

        // Removing useless columns except:
        // airline, origin_airport, destination_airport
        String[] route_cols_to_drop = {"YEAR", "MONTH", "DAY", "DAY_OF_WEEK", "FLIGHT_NUMBER", "TAIL_NUMBER",
                "SCHEDULED_DEPARTURE", "DEPARTURE_TIME", "DEPARTURE_DELAY", "TAXI_OUT", "WHEELS_OFF", "SCHEDULED_TIME",
                "ELAPSED_TIME", "AIR_TIME", "DISTANCE", "WHEELS_ON", "TAXI_IN", "SCHEDULED_ARRIVAL", "ARRIVAL_TIME",
                "ARRIVAL_DELAY", "DIVERTED", "CANCELLED", "CANCELLATION_REASON", "AIR_SYSTEM_DELAY", "SECURITY_DELAY",
                "AIRLINE_DELAY", "LATE_AIRCRAFT_DELAY", "WEATHER_DELAY", "AIRLINE"};
        for (String colName : route_cols_to_drop) {
            route_df = route_df.drop(colName);
        }

        // add column to ds
        route_df = route_df.withColumn("ROUTE_CODE", concat(
                route_df.col("ORIGIN_AIRPORT"),
                lit("_"),
                route_df.col("DESTINATION_AIRPORT")));

        // drops duplicates
        route_df = route_df.dropDuplicates("ROUTE_CODE");

        // stores data into file
        route_df.javaRDD().saveAsTextFile(outputRoutePath);


        // --------- Elaborating FLIGTHS entity ---------

        // Loading data into a dataset
        Dataset<Row> flight_df = ss.read()
                .format("com.databricks.spark.csv")
                .option("header", "true")
                .load(flights_csv);

        // Removing useless columns except:
        // route_code information, date information, arrival_time, scheduled_arrival, departure_time, scheduled_departure
        String[] flight_cols_to_drop = {"DAY_OF_WEEK", "TAIL_NUMBER", "DEPARTURE_DELAY", "TAXI_OUT", "WHEELS_OFF",
                "SCHEDULED_TIME", "ELAPSED_TIME", "AIR_TIME", "DISTANCE", "WHEELS_ON", "TAXI_IN", "ARRIVAL_DELAY",
                "DIVERTED", "CANCELLED", "CANCELLATION_REASON", "AIR_SYSTEM_DELAY", "SECURITY_DELAY", "AIRLINE_DELAY",
                "LATE_AIRCRAFT_DELAY", "WEATHER_DELAY"};
        for (String colName : flight_cols_to_drop) {
            flight_df = flight_df.drop(colName);
        }

        // Add route code to df
        flight_df = flight_df.withColumn("ROUTE_CODE", concat(
                flight_df.col("ORIGIN_AIRPORT"),
                lit("_"),
                flight_df.col("DESTINATION_AIRPORT")));

        // Add date to df
        flight_df = flight_df.withColumn("DATE", concat(
                flight_df.col("DAY"),
                lit("-"),
                flight_df.col("MONTH"),
                lit("-"),
                flight_df.col("YEAR")));

        // Columns to drop after transformation of them into unique field
        String[] flight_other_cols_to_drop = {"ORIGIN_AIRPORT", "DESTINATION_AIRPORT", "DAY", "MONTH", "YEAR"};
        for (String colName : flight_other_cols_to_drop) {
            flight_df = flight_df.drop(colName);
        }

        // remove rows with nulls in the departure and arrival times
        flight_df = flight_df.filter(flight_df.col("DEPARTURE_TIME").isNotNull());
        flight_df = flight_df.filter(flight_df.col("SCHEDULED_DEPARTURE").isNotNull());
        flight_df = flight_df.filter(flight_df.col("ARRIVAL_TIME").isNotNull());
        flight_df = flight_df.filter(flight_df.col("SCHEDULED_ARRIVAL").isNotNull());

        // stores data into file
        flight_df.javaRDD().saveAsTextFile(outputFlightPath);

        // Close the Spark Context object
        sc.close();

    }
}