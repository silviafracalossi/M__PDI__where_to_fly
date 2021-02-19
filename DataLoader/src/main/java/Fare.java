
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.*;

/**
 * Class that represent the fares
 */
@Entity
@Table(name = "FARES")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Fare implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "ROUTE")
    private Route route;

    @Id
    @Column(name = "YEAR")
    private int year;

    @Id
    @Column(name = "QUARTER")
    private int quarter;

    @Column(name = "PRICE")
    private float price;


    /**
     * Accesses the route_code
     * @return route_code of calling fares
     */
    public Route get_route() {
        return route;
    }

    /**
     * Changes the route_code
     * @param route new route_code
     */
    public void set_route_code(Route route) {
        this.route = route;
    }

    /**
     * Accesses the year
     * @return year of calling fares
     */
    public int get_year() {
        return year;
    }

    /**
     * Changes the year
     * @param year new year
     */
    public void set_year(int year) { this.year = year; }

    /**
     * Accesses the quarter
     * @return quarter of calling fares
     */
    public int get_quarter() {
        return quarter;
    }

    /**
     * Changes the quarter
     * @param quarter new quarter
     */
    public void set_quarter(int quarter) {
        this.quarter = quarter;
    }

    /**
     * Accesses the price
     * @return price of calling fares
     */
    public float get_price() {
        return price;
    }

    /**
     * Changes the price
     * @param price new price
     */
    public void set_price(float price) {
        this.price = price;
    }

    /**
     * Compares the calling fares and the passed object
     * @param obj object to be compared
     * @return Bool
     */
    @Override
    public boolean equals (Object obj) {
        if (obj instanceof Fare) {
            Fare o = (Fare) obj;
            return (
              o.route.equals(this.route)
              && o.year==this.year
              && o.quarter==this.quarter);
        }
        return false;
    }

    /**
     * Print method
     * @return String of information of the calling user
     */
    @Override
    public String toString() {
        return "Fare on route " + this.route.get_route_code() + ", in quarter " + this.quarter +
         " of " + this.year + ", is " + this.price + "$;";
    }

}
