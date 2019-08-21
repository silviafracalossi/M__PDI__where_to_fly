
import org.apache.spark.api.java.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.ml.feature.LabeledPoint;
import java.io.*;

public class X_AirlinesParser {

	public X_AirlinesParser() {

		String outputPath = "ex_out";
		String airlines_csv = "data/kaggle_airlines.csv";

		String airline_row;
		String[] splitted_airline;

		try {

			// Reading the airlines.csv file
			BufferedReader br = new BufferedReader(new FileReader(airlines_csv));

	  	// Removing the header of the file - IATA_CODE, AIRLINE
			String header = br.readLine();
			System.out.println("-------------------AIRLINE PARSER-------------------");
			System.out.println("Header of file is " +header);

			// Handling each line of the airlines.csv file
			while ((airline_row = br.readLine()) != null) {
				splitted_airline = airline_row.split(",");
				System.out.print("iata code is "+splitted_airline[0]);
				System.out.println("airline name is "+splitted_airline[1]);
			}

		} catch (Exception e) {
			System.out.println("Error with the airlines parsing");
		}

	}
}
