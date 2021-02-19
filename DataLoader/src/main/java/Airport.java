
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.*;

/**
 * Class that represent the airport
 */
@Entity
@Table(name = "AIRPORTS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Airport implements Serializable {

    @Id
    @Column(name = "IATA_CODE")
    private String iata_code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "LAT")
    private String lat;

    @Column(name = "LNG")
    private String lng;


    /**
     * Accesses the iata_code
     * @return iata_code of calling airport
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
     * @return name of calling airport
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
     * Accesses the city
     * @return city of calling airport
     */
    public String get_city() {
        return city;
    }

    /**
     * Changes the city
     * @param city new city
     */
    public void set_city(String city) {
        this.city = city;
    }

    /**
     * Accesses the state
     * @return state of calling airport
     */
    public String get_state() {
        return state;
    }

    /**
     * Changes the state
     * @param state new state
     */
    public void set_state(String state) {
        this.state = state;
    }

    /**
     * Accesses the country
     * @return country of calling airport
     */
    public String get_country() {
        return country;
    }

    /**
     * Changes the country
     * @param country new country
     */
    public void set_country(String country) {
        this.country = country;
    }

    /**
     * Accesses the latitude
     * @return latitude of calling airport
     */
    public String get_lat() {
        return lat;
    }

    /**
     * Changes the latitude
     * @param lat new latitude
     */
    public void set_lat(String lat) {
        this.lat = lat;
    }

    /**
     * Accesses the longitude
     * @return longitude of calling airport
     */
    public String get_lng() {
        return lng;
    }

    /**
     * Changes the longitude
     * @param lng new longitude
     */
    public void set_lng(String lng) {
        this.lng = lng;
    }

    /**
     * Compares the calling airport and the passed object
     * @param obj object to be compared
     * @return Bool
     */
    @Override
    public boolean equals (Object obj) {
        if (obj instanceof Airport) {
            Airport o = (Airport) obj;
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
        return "Airport: IATA code = " + this.iata_code + ", name = " + this.name + ", in " + this.city +
                ", " + this.state + " (" + this.country + "), located LAT = " + this.lat + ", LNG = " + this.lng + ";";
    }

}
