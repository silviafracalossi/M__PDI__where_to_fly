rmdir ex_out /s /q
mvn package
spark-submit --class Main --deploy-mode client --master local target/FlightsProject-1.0.0.jar
