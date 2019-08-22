# Path to this folder
# d:
# cd \Uni\repositories\PDI\pdi2019-project\SparkComponent

# Remove folders of out files
rm -rf ex_out

# Compiles the project
mvn package

# Runs application
spark-submit --class Main --deploy-mode client --master local target/FlightsProject-1.0.0.jar 
