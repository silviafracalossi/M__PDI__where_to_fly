
import org.hibernate.Session;
import java.io.Serializable;
import java.util.*;

/**
 * Class that manages the booking functions and calls the BookingServiceH2 methods
 */
public class BookingController implements Serializable {

    // Serves database-application communication
    private BookingServiceMysql service;
    private FlightController fc;
    private CustomerUserController cu;

    public BookingController (Session session) {
        service = new BookingServiceMysql(session);
        fc = new FlightController(session);
        cu = new CustomerUserController(session);
    }

    public Booking createBooking(String flight_info, String customer_nickname, int passengers){

        String[] flight_info_split = flight_info.split(",");
        Flight flight = fc.getFlightByPk(flight_info_split[0], flight_info_split[1], flight_info_split[2]);
        CustomerUser customer = cu.getCustomerUserByPk(customer_nickname);

        if (customer.get_nickname() != null && flight.get_flight_number() != null) {
            Booking booking = new Booking();
            booking.set_flight(flight);
            booking.set_customer_user(customer);
            booking.set_passengers(passengers);
            return createBooking(booking);
        } else {
            System.out.println("No flight or customer found while booking");
            return new Booking();
        }
    }

    public Booking createBooking(Booking b) {
        service.createCustomer(b);
        return b;
    }

    // ----- METHODS USED FOR FOREIGN KEY -----

    public List<Booking> getAllBookings () { return service.getAllBookings(); }


}
