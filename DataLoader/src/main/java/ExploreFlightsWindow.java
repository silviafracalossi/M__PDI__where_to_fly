
import java.awt.*;
import javax.swing.*;
import org.hibernate.Session;
import java.awt.event.*;
import java.util.List;

public class ExploreFlightsWindow {
    private JFrame frame;
    private JTextField txtSomething;
    private AirportController ac;
    private FlightController fc;
    private Session db_session;
    private List<Airport> airports;

    /**
     * Create the application.
     */
    public ExploreFlightsWindow(Session db_session) {
        this.db_session = db_session;
        ac = new AirportController(db_session);
        fc = new FlightController(db_session);
        this.airports = ac.getAllAirports();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 830, 449);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Panel panel = new Panel();
        panel.setBackground(SystemColor.control);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        // TITLE
        JLabel lblWelcomeToWheretofly = new JLabel("Explore Flights");
        lblWelcomeToWheretofly.setBounds(10, 54, 566, 25);
        panel.add(lblWelcomeToWheretofly);
        lblWelcomeToWheretofly.setVerticalAlignment(SwingConstants.TOP);
        lblWelcomeToWheretofly.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblWelcomeToWheretofly.setHorizontalAlignment(SwingConstants.CENTER);


        // ORIGIN AIRPORT LABEL
        JLabel lblClickHereTo_1 = new JLabel("Origin Airport: ");
        lblClickHereTo_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblClickHereTo_1.setBounds(30, 155, 178, 13);
        panel.add(lblClickHereTo_1);

        // ORIGIN AIRPORT COMBO BOX
        String[] airports_in_cb = new String[airports.size()+1];
        airports_in_cb[0] = "Select airport...";
        for (int i = 0; i<airports.size(); i++) {
            airports_in_cb[i+1] = airports.get(i).get_city();
        }
        JComboBox cb=new JComboBox(airports_in_cb);
        cb.setBounds(105, 150, 150, 25);
        panel.add(cb);


        // DESTINATION AIRPORT LABEL
        JLabel destination_label = new JLabel("Destination Airport: ");
        destination_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
        destination_label.setBounds(285, 155, 178, 13);
        panel.add(destination_label);

        // DESTINATION AIRPORT COMBO BOX
        JComboBox dcb=new JComboBox(airports_in_cb);
        dcb.setBounds(395, 150, 150, 25);
        panel.add(dcb);

        // LAST: SEARCH BUTTON
        JButton btnLoginAsAirport = new JButton("Search!");
        btnLoginAsAirport.setBounds(225, 225, 139, 34);
        panel.add(btnLoginAsAirport);
        btnLoginAsAirport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Airport origin_airport = new Airport();
                Airport destination_airport = new Airport();

                if (cb.getSelectedIndex() != 0) {
                    origin_airport = airports.get(cb.getSelectedIndex()-1);
                }

                if (dcb.getSelectedIndex() != 0) {
                    destination_airport = airports.get(dcb.getSelectedIndex()-1);
                }

                List<Flight> flights_list = fc.getFilteredFlights(origin_airport, destination_airport);
                for (int i = 0; i < flights_list.size(); i++) {
                    System.out.println(flights_list.get(i));
                }

            }
        });

        frame.setPreferredSize(new Dimension(600, 350));
        frame.pack();
        frame.setVisible(true);
    }
}
