
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.Function;
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
import scala.Tuple2;

public class SparkDriverLRegression {

	public static void main(String[] args) {

		String input_data = "../data/final_flight_training.txt";
		String prediction_data = "../data/final_flight_prediction.txt";
		String outpath = "../ex_out/prediction";

		// Create a Spark Session object and set the name of the application
		// We use some Spark SQL transformation in this program
		SparkSession ss = SparkSession.builder().appName("MLlib - logistic regression").getOrCreate();

		// Create a Java Spark Context from the Spark Session
		// When a Spark Session has already been defined this method
		// is used to create the Java Spark Context
		JavaSparkContext sc = new JavaSparkContext(ss.sparkContext());

		// *************************
		// Training step
		// *************************

		// ------------- use ANC_SEA route -------------
		// Map each airport to tuple (flight data, flight data split)
		JavaRDD<String> flights_data = sc.textFile(input_data);
		JavaPairRDD<String, String[]> flightsRDD = flights_data.mapToPair(a -> new Tuple2<>(a, a.split(",")));

		// Creates filter function, which removes the flights with route different from ANC_SEA
		Function<Tuple2<String, String[]>, Boolean> filterFunction = a -> (a._2[6].equals("ANC_SEA"));

		// apply the filter on wordsRDD
		JavaPairRDD<String, String[]> rddf = flightsRDD.filter(filterFunction);

		// converting back the JavaPairRDD in a JavaRDD
		JavaRDD<String> flights_after_filter = rddf.map(x -> x._1);

		System.out.println("Saved. Now training");

		// Map each input record/data point of the input file to a LabeledPoint

		// File structure: [AS,98,0005,2354,0430,0408,ANC_SEA,1-1-2015]
		JavaRDD<LabeledPoint> flightsTrainingRDD = flights_after_filter.map(record ->
		{
			// Removing brakets and preparing data
			record = record.replace("[", "");
			record = record.replace("]", "");
			String[] fields = record.split(",");
			String[] date = fields[7].split("-");

			// 2 means late, 5 means in time
			double classLabel = (Integer.parseInt(fields[3]) > Integer.parseInt(fields[2])) ? 2.0 : 5.0;

			double[] attributesValues = new double[6];
			attributesValues[0] = Double.parseDouble(fields[1]);						// Flight number
			attributesValues[1] = Double.parseDouble(date[0]);							// Day of January
			attributesValues[2] = Double.parseDouble(fields[2].substring(0,2));			// Hour of departure time
			attributesValues[3] = Double.parseDouble(fields[2].substring(2,4));			// Minutes of departure time
			attributesValues[4] = Double.parseDouble(fields[4].substring(0,2));			// Hour of arrival time
			attributesValues[5] = Double.parseDouble(fields[4].substring(2,4));			// Minutes of arrival time

			// Create a dense vector based on the content of attributesValues
			Vector attrValues = Vectors.dense(attributesValues);

			// Return a LabeledPoint based on the content of the current line
			return new LabeledPoint(classLabel, attrValues);
		});

		// Prepare training data.
		// We use LabeledPoint, which is a JavaBean.
		// We use Spark SQL to convert RDDs of JavaBeans
		// into Dataset<Row>. The columns of the Dataset are label
		// and features
		Dataset<Row> flightsTraining = ss.createDataFrame(flightsTrainingRDD, LabeledPoint.class).cache();

		// Create a LogisticRegression object.
		// LogisticRegression is an Estimator that is used to
		// create a classification model based on logistic regression.
		LogisticRegression flights_lr = new LogisticRegression();

		// We can set the values of the parameters of the
		// Logistic Regression algorithm using the setter methods.
		// There is one set method for each parameter
		// For example, we are setting the number of maximum iterations to 10
		// and the regularization parameter. to 0.0.1
		flights_lr.setMaxIter(10);
		flights_lr.setRegParam(0.01);

		// Define the pipeline that is used to create the logistic regression
		// model on the training data.
		// In this case the pipeline contains one single stage/step (the model
		// generation step).
		Pipeline flights_pipeline = new Pipeline().setStages(new PipelineStage[] {flights_lr});

		// Execute the pipeline on the training data to build the
		// classification model
		PipelineModel flights_model = flights_pipeline.fit(flightsTraining);

		// Now, the classification model can be used to predict the class label
		// of new unlabeled data

		// *************************
		// Prediction  step
		// *************************

		// Read unlabeled data
		// For the unlabeled data only the predictive attributes are available
		// The class label is not available and must be predicted by applying
		// the classification model inferred during the previous phase

		// ------------- use ANC_SEA route -------------
		JavaRDD<String> flightsUnlabeledData = sc.textFile(prediction_data);

		JavaPairRDD<String, String[]> flights_unlabeled_RDD = flightsUnlabeledData.mapToPair(a -> new Tuple2<>(a, a.split(",")));

		// Creates filter function, which removes the flights with route different from ANC_SEA
		Function<Tuple2<String, String[]>, Boolean> filterFunction_unl = a -> (a._2[6].equals("ANC_SEA"));

		// apply the filter on wordsRDD
		JavaPairRDD<String, String[]> rddf_unlabeled = flights_unlabeled_RDD.filter(filterFunction_unl);

		// converting back the JavaPairRDD in a JavaRDD
		JavaRDD<String> flights_unlabeled_after_filter = rddf_unlabeled.map(x -> x._1);

		// Map each unlabeled input record/data point of the input file to a LabeledPoint
		JavaRDD<LabeledPoint> flightsUnlabeledRDD = flights_unlabeled_after_filter.map(record ->
		{

			// Removing brakets and preparing data
			record = record.replace("[", "");
			record = record.replace("]", "");
			String[] fields = record.split(",");
			String[] date = fields[7].split("-");

			double[] attributesValues = new double[6];
			attributesValues[0] = Double.parseDouble(fields[1]);						// Flight number
			attributesValues[1] = Double.parseDouble(date[0]);							// Day of January
			attributesValues[2] = Double.parseDouble(fields[2].substring(0,2));			// Hour of departure time
			attributesValues[3] = Double.parseDouble(fields[2].substring(2,4));			// Minutes of departure time
			attributesValues[4] = Double.parseDouble(fields[4].substring(0,2));			// Hour of arrival time
			attributesValues[5] = Double.parseDouble(fields[4].substring(2,4));			// Minutes of arrival time

			// Create a dense vector based on the content of attributesValues
			Vector attrValues = Vectors.dense(attributesValues);

			// The class label in unknown.
			// To create a LabeledPoint a class label value must be specified also for the
			// unlabeled data. I set it to -1 (an invalid value). The specified value
			// does not impact on the prediction because the label column is not
			// used to perform the prediction
			double classLabel = -1;

			// Return a new LabeledPoint
			return new LabeledPoint(classLabel, attrValues);
		});

		// Create the DataFrame based on the new test data
		Dataset<Row> flightsUnlabeled = ss.createDataFrame(flightsUnlabeledRDD, LabeledPoint.class);
		Dataset<Row> flights_predictions = flights_model.transform(flightsUnlabeled);

		// Select only the features (i.e., the value of the predictive attributes)
		// and the predicted class for each record
		Dataset<Row> flights_predictionsDF=flights_predictions.select("features", "prediction");

		// Save the result in an HDFS file
		JavaRDD<Row> flights_predictionsRDD = flights_predictionsDF.javaRDD();

		// Saving the content in a file
		flights_predictionsRDD.saveAsTextFile(outpath);

		// Close the Spark Context object
		sc.close();

		// Show the results of the prediction
		ShowPrediction sp = new ShowPrediction();
	}
}
