
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.*;

/**
 * Class that represent the airline
 */
@Entity
@Table(name = "AIRLINES")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Airline implements Serializable {

    @Id
    @Column(name = "IATA_CODE")
    private String iata_code;

    @Column(name = "NAME")
    private String name;


    /**
     * Accesses the iata_code
     * @return iata_code of calling airline
     */
    public String get_iata_code() {
        return iata_code;
    }

    /**
     * Changes the iata_code
     * @param iata_code new iata_code
     */
    public void set_iata_code(String iata_code) {
        this.iata_code = iata_code;
    }

    /**
     * Accesses the name
     * @return name of calling airline
     */
    public String get_name() {
        return name;
    }

    /**
     * Changes the name
     * @param name new name
     */
    public void set_name(String name) {
        this.name = name;
    }

    /**
     * Compares the calling airline and the passed object
     * @param obj object to be compared
     * @return Bool
     */
    @Override
    public boolean equals (Object obj) {
        if (obj instanceof Airline) {
            Airline o = (Airline) obj;
            return (o.iata_code.equals(this.iata_code));
        }
        return false;
    }

    /**
     * Print method
     * @return String of information of the calling user
     */
    @Override
    public String toString() {
        return "Airline: IATA code = " + this.iata_code + ", name = " + this.name + ";";
    }

}
