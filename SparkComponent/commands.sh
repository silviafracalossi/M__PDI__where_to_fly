# Path to this file
# d:
# cd \Uni\repositories\Programming Data Infrastructure\pdi2019-project\SparkComponent

# Remove folders of out files
# OS:
# rm -rf ex_out
# WINDOWS:
rmdir ex_out

# Compiles the project
mvn package

# Runs application
spark-submit  --class SparkDriver --deploy-mode client --master local target/FlightsProject-1.0.0.jar
