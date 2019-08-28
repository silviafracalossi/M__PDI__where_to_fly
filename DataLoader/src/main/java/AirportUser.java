
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.*;

/**
 * Class that represent the airport user
 */
@Entity
@Table(name = "AIRPORTUSER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AirportUser implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "AIRPORT_IATA_CODE")
    private Airport airport_iata_code;

    /**
     * Accesses the airport_iata_code
     * @return airport_iata_code of calling airport_user
     */
    public Airport get_airport_iata_code() {
        return airport_iata_code;
    }

    /**
     * Changes the airport_iata_code
     * @param airport_iata_code new airport_iata_code
     */
    public void set_airport_iata_code(Airport airport_iata_code) {
        this.airport_iata_code = airport_iata_code;
    }

    /**
     * Compares the calling airport_user and the passed object
     * @param obj object to be compared
     * @return Bool
     */
    @Override
    public boolean equals (Object obj) {
        if (obj instanceof AirportUser) {
            AirportUser o = (AirportUser) obj;
            return (o.airport_iata_code.equals(this.airport_iata_code));
        }
        return false;
    }

    /**
     * Print method
     * @return String of information of the calling user
     */
    @Override
    public String toString() {
        return "AirportUser: airport IATA code = " + this.airport_iata_code.get_iata_code() + ";";
    }

}
