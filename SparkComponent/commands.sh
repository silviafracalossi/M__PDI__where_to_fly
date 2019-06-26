# first complie with
mvn package

# Then remove folders of the previous run  (if it's not the first time)
rm -rf ex_out

# Run application
spark-submit  --class SparkDriverLRegression --deploy-mode client --master local target/FlightsProject-1.0.0.jar "ex_data/trainingData.txt"  "ex_data/unlabeledData.txt" ex_out
