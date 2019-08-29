
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.*;

/**
 * Class that represent the booking
 */
@Entity
@Table(name = "BOOKING")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Booking implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_USER")
    private CustomerUser customer_user;

    @Id
    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "FLIGHT", referencedColumnName = "ROUTE_CODE"),
            @JoinColumn(name = "FLIGHT", referencedColumnName = "DATE") })
    private Flight flight;

    @Column(name = "PASSENGERS")
    private int passengers;

    /**
     * Accesses the customer_user
     * @return customer_user of calling booking
     */
    public CustomerUser get_customer_user() {
        return customer_user;
    }

    /**
     * Changes the customer_user
     * @param customer_user new customer_user
     */
    public void set_customer_user(CustomerUser customer_user) {
        this.customer_user = customer_user;
    }

    /**
     * Accesses the flight
     * @return flight of calling booking
     */
    public Flight get_flight() {
        return flight;
    }

    /**
     * Changes the flight
     * @param flight new flight
     */
    public void set_flight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Accesses the passengers
     * @return passengers of calling booking
     */
    public int get_passengers() {
        return passengers;
    }

    /**
     * Changes the passengers
     * @param passengers new passengers
     */
    public void set_passengers(int passengers) {
        this.passengers = passengers;
    }

    /**
     * Compares the calling booking and the passed object
     * @param obj object to be compared
     * @return Bool
     */
    @Override
    public boolean equals (Object obj) {
        if (obj instanceof Booking) {
            Booking o = (Booking) obj;
            return (o.customer_user.equals(this.customer_user)
            && o.flight.equals(this.flight));
        }
        return false;
    }

    /**
     * Print method
     * @return String of information of the calling user
     */
    @Override
    public String toString() {
        return "Booking made by " + this.customer_user.get_nickname() + " for flight (" + this.flight + ") for " +
                this.passengers + " passengers;";
    }

}
