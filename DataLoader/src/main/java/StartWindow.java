
import java.awt.*;
import javax.swing.*;

import org.hibernate.Session;

import java.awt.event.*;

public class StartWindow {
	private JFrame frame;
	private JTextField txtSomething;
	private Session db_session;
	
	/**
	 * Create the application.
	 */
	public StartWindow(Session db_session) {
		this.db_session = db_session;
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
		
		JLabel lblNewLabel = new JLabel("Insert Airport IATA code:");
		lblNewLabel.setBounds(86, 280, 153, 20);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblNewLabel);
		
		txtSomething = new JTextField();
		txtSomething.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtSomething.setBounds(251, 280, 76, 20);
		txtSomething.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(txtSomething);
		txtSomething.setColumns(10);
				
		JLabel lblWelcomeToWheretofly = new JLabel("Welcome to WhereToFLy");
		lblWelcomeToWheretofly.setBounds(10, 54, 566, 25);
		panel.add(lblWelcomeToWheretofly);
		lblWelcomeToWheretofly.setVerticalAlignment(SwingConstants.TOP);
		lblWelcomeToWheretofly.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWelcomeToWheretofly.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Explore button
		JButton btnExploreFlights = new JButton("Explore Flights");
		btnExploreFlights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Explore!");
			}
		});
		btnExploreFlights.setBounds(364, 194, 139, 34);
		panel.add(btnExploreFlights);
		
		// LOGIN BUTTON
		JButton btnLoginAsAirport = new JButton("Login as Airport");
		btnLoginAsAirport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String iata_code = txtSomething.getText();
				if (!iata_code.contentEquals("")) {
					System.out.println(iata_code);
					// frame.setVisible(false);
				}
			}
		});
		btnLoginAsAirport.setBounds(364, 270, 139, 34);
		panel.add(btnLoginAsAirport);
		
		// SHOW ANALYTICS BUTTON
		JButton btnShowAnalytics = new JButton("Show Analytics");
		btnShowAnalytics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnalyticsServiceMysql asm = new AnalyticsServiceMysql(db_session);
				asm.displayAnalytics();
			}
		});
		btnShowAnalytics.setBounds(364, 124, 139, 34);
		panel.add(btnShowAnalytics);
		
		JLabel lblClickHereTo = new JLabel("Click here to see Data Analisys:");
		lblClickHereTo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblClickHereTo.setBounds(124, 134, 193, 13);
		panel.add(lblClickHereTo);
		
		JLabel lblClickHereTo_1 = new JLabel("Click here to look for a flight:");
		lblClickHereTo_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblClickHereTo_1.setBounds(124, 205, 178, 13);
		panel.add(lblClickHereTo_1);
		
		frame.setPreferredSize(new Dimension(600, 400));
		frame.pack();
		frame.setVisible(true);
	}
}
