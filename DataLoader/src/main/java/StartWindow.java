
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
				frame.setVisible(false);
				ExploreFlightsWindow sf = new ExploreFlightsWindow(db_session);
			}
		});
		btnExploreFlights.setBounds(364, 194, 139, 34);
		panel.add(btnExploreFlights);
		
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
		
		frame.setPreferredSize(new Dimension(600, 350));
		frame.pack();
		frame.setVisible(true);
	}
}
