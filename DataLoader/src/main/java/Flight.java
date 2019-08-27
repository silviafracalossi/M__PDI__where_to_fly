
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.*;
import java.sql.Time;

/**
 * Class that represent the flight
 */
@Entity
@Table(name = "FLIGHTS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Flight implements Serializable {

    @Id
    @Column(name = "FLIGHT_NUMBER")
    private String flight_number;

    @Id
    @Column(name = "ROUTE_CODE")
    private String route_code;

    @Id
    @Column(name = "DATE")
    private Date date;

    @Column(name = "AIRLINE")
    private String airline;

    @Column(name = "SCHEDULED_DEPARTURE")
    private Time scheduled_departure;

    @Column(name = "DEPARTURE_TIME")
    private Time departure_time;

    @Column(name = "SCHEDULED_ARRIVAL")
    private Time scheduled_arrival;

    @Column(name = "ARRIVAL_TIME")
    private Time arrival_time;


    /**
     * Accesses the flight_number
     * @return flight_number of calling flight
     */
    public String get_flight_number() {
        return flight_number;
    }

    /**
     * Changes the flight_number
     * @param flight_number new flight_number
     */
    public void set_flight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    /**
     * Accesses the route_code
     * @return route_code of calling flight
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
     * Accesses the date
     * @return date of calling flight
     */
    public Date get_date() {
        return date;
    }

    /**
     * Changes the date
     * @param date new date
     */
    public void set_date(Date date) {
        this.date = date;
    }

    /**
     * Accesses the airline
     * @return airline of calling flight
     */
    public String get_airline() {
        return airline;
    }

    /**
     * Changes the airline
     * @param airline new airline
     */
    public void set_airline(String airline) {
        this.airline = airline;
    }

    /**
     * Accesses the scheduled_departure
     * @return scheduled_departure of calling flight
     */
    public Time get_scheduled_departure() {
        return scheduled_departure;
    }

    /**
     * Changes the scheduled_departure
     * @param scheduled_departure new scheduled_departure
     */
    public void set_scheduled_departure(Time scheduled_departure) {
        this.scheduled_departure = scheduled_departure;
    }

    /**
     * Accesses the departure_time
     * @return departure_time of calling flight
     */
    public Time get_departure_time() {
        return departure_time;
    }

    /**
     * Changes the departure_time
     * @param departure_time new departure_time
     */
    public void set_departure_time(Time departure_time) {
        this.departure_time = departure_time;
    }

    /**
     * Accesses the scheduled_arrival
     * @return scheduled_arrival of calling flight
     */
    public Time get_scheduled_arrival() {
        return scheduled_arrival;
    }

    /**
     * Changes the scheduled_arrival
     * @param scheduled_arrival new scheduled_arrival
     */
    public void set_scheduled_arrival(Time scheduled_arrival) {
        this.scheduled_arrival = scheduled_arrival;
    }

    /**
     * Accesses the arrival_time
     * @return arrival_time of calling flight
     */
    public Time get_arrival_time() {
        return arrival_time;
    }

    /**
     * Changes the arrival_time
     * @param arrival_time new arrival_time
     */
    public void set_arrival_time(Time arrival_time) {
        this.arrival_time = arrival_time;
    }

    /**
     * Compares the calling flight and the passed object
     * @param obj object to be compared
     * @return Bool
     */
    @Override
    public boolean equals (Object obj) {
        if (obj instanceof Flight) {
            Flight o = (Flight) obj;
            return (o.flight_number.equals(this.flight_number))
                    && (o.date.equals(this.date))
                    && (o.route_code.equals(this.route_code));
        }
        return false;
    }

    /**
     * Print method
     * @return String of information of the calling user
     */
    @Override
    public String toString() {
        return "Flight number " + this.flight_number + ", route_code = " + this.route_code + ", on " + this.date +
                "with airline +" + this.airline + "; Scheduled departure: " + this.scheduled_departure +
                ", actual one: " + this.departure_time + "; Scheduled arrival " + this.scheduled_arrival +
                ", actual one: " + this.arrival_time + ";";
    }

}
