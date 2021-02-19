
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.*;

/**
 * Class that represent the route
 */
@Entity
@Table(name = "ROUTES")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Route implements Serializable {

    @Id
    @Column(name = "ROUTE_CODE")
    private String route_code;

    @ManyToOne
    @JoinColumn(name = "ORIGIN_AIRPORT")
    private Airport origin_airport;

    @ManyToOne
    @JoinColumn(name = "DESTINATION_AIRPORT")
    private Airport destination_airport;


    /**
     * Accesses the route_code
     * @return route_code of calling route
     */
    public String get_route_code() {
        return route_code;
    }

    /**
     * Changes the route_code
     * @param route_code new route_code
     */
    public void set_route_code(String route_code) {
        this.route_code = route_code;
    }

    /**
     * Accesses the origin_airport
     * @return origin_airport of calling route
     */
    public Airport get_origin_airport() {
        return origin_airport;
    }

    /**
     * Changes the origin_airport
     * @param origin_airport new origin_airport
     */
    public void set_origin_airport(Airport origin_airport) {
        this.origin_airport = origin_airport;
    }

    /**
     * Accesses the destination_airport
     * @return destination_airport of calling route
     */
    public Airport get_destination_airport() {
        return destination_airport;
    }

    /**
     * Changes the destination_airport
     * @param destination_airport new destination_airport
     */
    public void set_destination_airport(Airport destination_airport) {
        this.destination_airport = destination_airport;
    }

    /**
     * Compares the calling route and the passed object
     * @param obj object to be compared
     * @return Bool
     */
    @Override
    public boolean equals (Object obj) {
        if (obj instanceof Route) {
            Route o = (Route) obj;
            return (o.route_code.equals(this.route_code));
        }
        return false;
    }

    /**
     * Print method
     * @return String of information of the calling user
     */
    @Override
    public String toString() {
        return "Route: ROUTE code = " + this.route_code + ", from airport (" + this.origin_airport.get_iata_code() + ") to ("
                + this.destination_airport.get_iata_code() + ");";
    }

}
