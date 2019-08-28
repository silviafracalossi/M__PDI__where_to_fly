
import org.apache.spark.sql.*;
import java.io.Serializable;
import org.apache.spark.api.java.JavaSparkContext;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.regexp_replace;

public class AirfareETL implements Serializable {

    public AirfareETL () {

        // CSV source path and output path definition
        String airfare_csv = "../data/transportation_airfare.csv";
        // String airfare_csv = "../data/airfare_short.csv";
        String outputAirfarePath = "../parsed_data/fares";

        // Create a Spark Session object and set the name of the application
        SparkSession ss = SparkSession.builder().appName("Airfares Filtering Data").getOrCreate();

        // Create a Java Spark Context from the Spark Session
        JavaSparkContext sc = new JavaSparkContext(ss.sparkContext());

        // Loading data into a dataset
        Dataset<Row> airfare_df = ss.read()
                .format("com.databricks.spark.csv")
                .option("header", "true")
                .load(airfare_csv);

        // Removing useless columns
        // Except year, quarter, fare, Geocoded_City1, Geocoded_City2
        String[] flight_cols_to_drop = {"tbl", "citymarketid_1", "citymarketid_2", "nsmiles", "passengers", "carrier_lg",
                "large_ms", "fare_lg", "carrier_low", "lf_ms", "fare_low", "table_1_flag", "tbl6pk", "city1", "city2"};
        for (String colName : flight_cols_to_drop) {
            airfare_df = airfare_df.drop(colName);
        }

        // Removing the coordinates and extra useless data from the dataset
        airfare_df = airfare_df.withColumn("Geocoded_City1", regexp_replace(col("Geocoded_City1"), ", \\([0-9-., ]*\\)", ""));
        airfare_df = airfare_df.withColumn("Geocoded_City2", regexp_replace(col("Geocoded_City2"), ", \\([0-9-., ]*\\)", ""));
        airfare_df = airfare_df.withColumn("Geocoded_City1", regexp_replace(col("Geocoded_City1"), "\\([0-9-., ]*\\)", ""));
        airfare_df = airfare_df.withColumn("Geocoded_City2", regexp_replace(col("Geocoded_City2"), "\\([0-9-., ]*\\)", ""));
        airfare_df = airfare_df.withColumn("Geocoded_City1", regexp_replace(col("Geocoded_City1"), " ", ""));
        airfare_df = airfare_df.withColumn("Geocoded_City2", regexp_replace(col("Geocoded_City2"), " ", ""));

        // Stores data into file
        airfare_df.javaRDD().saveAsTextFile(outputAirfarePath);

        // Close the Spark Context object
        sc.close();
    }
}
