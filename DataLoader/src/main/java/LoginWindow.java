
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LoginWindow {
	private JFrame frame;
	private JTextField txtSomething;
	
	/**
	 * Create the application.
	 */
	public LoginWindow() {
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
		
		JLabel lblNewLabel = new JLabel("Insert nickname or Airport IATA code");
		lblNewLabel.setBounds(46, 131, 264, 20);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblNewLabel);
		
		txtSomething = new JTextField();
		txtSomething.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtSomething.setBounds(332, 131, 214, 20);
		txtSomething.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(txtSomething);
		txtSomething.setColumns(10);
		
		JRadioButton rdbtnAirport = new JRadioButton("Airport");
		rdbtnAirport.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnAirport.setBounds(255, 197, 105, 21);
		panel.add(rdbtnAirport);
		
		JRadioButton radioButton = new JRadioButton("Customer");
		radioButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		radioButton.setBounds(373, 197, 105, 21);
		panel.add(radioButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(radioButton);
		group.add(rdbtnAirport);
		
		
		JLabel lblWelcomeToWheretofly = new JLabel("Welcome to WhereToFLy");
		lblWelcomeToWheretofly.setBounds(10, 38, 566, 25);
		panel.add(lblWelcomeToWheretofly);
		lblWelcomeToWheretofly.setVerticalAlignment(SwingConstants.TOP);
		lblWelcomeToWheretofly.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWelcomeToWheretofly.setHorizontalAlignment(SwingConstants.CENTER);
		
		Button button = new Button("Login");
		button.setFont(null);
		button.setBounds(226, 267, 128, 34);
		panel.add(button);
		
		Label label = new Label("Logging in as:");
		label.setBounds(129, 193, 120, 21);
		panel.add(label);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		frame.setPreferredSize(new Dimension(600, 400));
		frame.pack();
		frame.setVisible(true);
	}
}
