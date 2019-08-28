
import org.hibernate.Session;
import java.io.Serializable;
import java.util.*;

/**
 * Class that manages the airport functions and calls the AirportUserServiceH2 methods
 */
public class AirportUserController implements Serializable {

    // Serves database-application communication
    private AirportUserServiceMysql service;
    private AirportController ac;

    public AirportUserController (Session session) {
        service = new AirportUserServiceMysql(session);
        ac = new AirportController(session);
    }

    public AirportUser createAirportUser(String airport_iata_code){

        Airport airport = ac.getAirportByPk(airport_iata_code);

        if (airport.get_iata_code() != null) {
            AirportUser airport_user = new AirportUser();
            airport_user.set_airport_iata_code(airport);
            return createAirportUser(airport_user);
        }

        return new AirportUser();
    }

    public AirportUser createAirportUser(AirportUser a) {
        service.createAirport(a);
        return a;
    }

    // ----- METHODS USED FOR FOREIGN KEY -----

    public List<AirportUser> getAllAirportUsers () { return service.getAllAirportUsers(); }


}
