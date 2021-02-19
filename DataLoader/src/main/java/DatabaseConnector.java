import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static String DB_URL = "jdbc:mysql://localhost/wheretofly";
    public static String username = "silvia";
    public static String password = "aPassword!";

    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final Connection connection = getConnection();                // variable to establish the connection with db
    public static Session session = openSession();                               // variable to set up queries


    /**
     * Creates the SessionFactory using hibernate.cfg.xml configuration file
     * @return the created sessionFactory
     */
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration c = new Configuration();
            c.configure();
            c.addAnnotatedClass(Airline.class);
            c.addAnnotatedClass(Airport.class);
            return c.buildSessionFactory();
        } catch (Exception ex) {
            System.out.println("Initial SessionFactory creation failed.");
            System.err.println(ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Establishes the connection with the DB
     * @return connection variable
     */
    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, username, password);
        } catch (SQLException e) {
            System.out.println("Initial getConnection failed.");
            System.err.println(e);
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Opens a new session with the DB
     * @return new session
     */
    private static Session openSession() {
        return sessionFactory.openSession();
    }

    /**
     * Closes the session and the sessionFactory with the DB
     */
    public void closeConnection(){
        if (connection != null) {
            sessionFactory.close();
            session.close();
        }
    }
}
