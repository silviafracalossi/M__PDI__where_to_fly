/*
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

public class Z_SparkDriver {

	public void logistic_regression() {
		String inputFileTraining ="data/old/trainingData.txt";
		String inputFileTest = "data/old/unlabeledData.txt";
		String outputPath = "ex_out";

		// inputFileTraining=args[0];
		// inputFileTest=args[1];
		// outputPath=args[2];

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

		// Read training data from a text file
		// Each line has the format: class-label,list of three numerical attribute values
		// E.g., 		1.0,5.8,0.51.7
		JavaRDD<String> trainingData=sc.textFile(inputFileTraining);

		// Map each input record/data point of the input file to a LabeledPoint
		JavaRDD<LabeledPoint> trainingRDD=trainingData.map(record ->
			{
				String[] fields = record.split(",");

				// Fields of 0 contains the id of the class
				double classLabel = Double.parseDouble(fields[0]);

				// The other three cells of fields contain the (numerical) values of the
				// three predictive attributes
				// Create an array of doubles containing those three values
				double[] attributesValues = new double[3];

				attributesValues[0] = Double.parseDouble(fields[1]);
				attributesValues[1] = Double.parseDouble(fields[2]);
				attributesValues[2] = Double.parseDouble(fields[3]);

				// Create a dense vector based on the content of attributesValues
				Vector attrValues= Vectors.dense(attributesValues);

				// Return a LabeledPoint based on the content of the current line
				return new LabeledPoint(classLabel, attrValues);
			});

		// Prepare training data.
		// We use LabeledPoint, which is a JavaBean.
		// We use Spark SQL to convert RDDs of JavaBeans
		// into Dataset<Row>. The columns of the Dataset are label
		// and features
		Dataset<Row> training = ss.createDataFrame(trainingRDD, LabeledPoint.class).cache();

		// Create a LogisticRegression object.
		// LogisticRegression is an Estimator that is used to
		// create a classification model based on logistic regression.
		LogisticRegression lr = new LogisticRegression();

		// We can set the values of the parameters of the
		// Logistic Regression algorithm using the setter methods.
		// There is one set method for each parameter
		// For example, we are setting the number of maximum iterations to 10
		// and the regularization parameter. to 0.0.1
		lr.setMaxIter(10);
		lr.setRegParam(0.01);


		// Define the pipeline that is used to create the logistic regression
		// model on the training data.
		// In this case the pipeline contains one single stage/step (the model
		// generation step).
		Pipeline pipeline = new Pipeline()
				  .setStages(new PipelineStage[] {lr});

		// Execute the pipeline on the training data to build the
		// classification model
		PipelineModel model = pipeline.fit(training);

		// Now, the classification model can be used to predict the class label
		// of new unlabeled data



		// *************************
		// Prediction  step
		// *************************

		// Read unlabeled data
		// For the unlabeled data only the predictive attributes are available
		// The class label is not available and must be predicted by applying
		// the classification model inferred during the previous phase
		JavaRDD<String> unlabeledData=sc.textFile(inputFileTest);

		// Map each unlabeled input record/data point of the input file to a LabeledPoint
		JavaRDD<LabeledPoint> unlabeledRDD=unlabeledData.map(record ->
		{
			String[] fields = record.split(",");


			// The last three cells of fields contain the (numerical) values of the
			// three predictive attributes
			// Create an array of doubles containing those three values
			double[] attributesValues = new double[3];

			attributesValues[0] = Double.parseDouble(fields[1]);
			attributesValues[1] = Double.parseDouble(fields[2]);
			attributesValues[2] = Double.parseDouble(fields[3]);

			// Create a dense vector based in the content of attributesValues
			Vector attrValues= Vectors.dense(attributesValues);

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
		Dataset<Row> unlabeled = ss.createDataFrame(unlabeledRDD, LabeledPoint.class);

		// Make predictions on test documents using the Transformer.transform() method.
		// The transform will only use the 'features' columns

		// The returned DataFrame has the following schema (attributes)
		// - features: vector (values of the attributes)
		// - label: double (value of the class label)
		// - rawPrediction: vector (nullable = true)
		// - probability: vector (The i-th cell contains the probability that the
		//                        current record belongs to the i-th class
		// - prediction: double (the predicted class label)

		Dataset<Row> predictions = model.transform(unlabeled);

		// Select only the features (i.e., the value of the predictive attributes)
		// and the predicted class for each record
		Dataset<Row> predictionsDF=predictions.select("features", "prediction");

		// Save the result in an HDFS file
		JavaRDD<Row> predictionsRDD = predictionsDF.javaRDD();
		predictionsRDD.saveAsTextFile(outputPath);

		// Close the Spark Context object
		sc.close();
	}

 }
*/