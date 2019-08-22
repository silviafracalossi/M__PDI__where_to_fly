rmdir ex_out /s /q
mvn package
spark-submit --class Main --deploy-mode client --master local target/FlightsProject-1.0.0.jar

REM D:\Uni\repositories\PDI\hibernate-release-5.4.4.Final\project\hibernate-core\src\main\java\org\hibernate\cfg\
