import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import models.Airline;


/**
 * Class that implements the concrete access to H2 database
 */
public class AirlineServiceMysql implements Serializable {

    public static String DB_URL = "jdbc:h2:~/users_database";
    public static String username = "sa";
    public static String password = "";

//    private static final SessionFactory sessionFactory = buildSessionFactory();
//    private static final Connection connection = getConnection();                // variable to establish the connection with db
//    public static Session session = openSession();                               // variable to set up queries

    
//    /**
//     * Creates the SessionFactory using hibernate.cfg.xml configuration file
//     * @return the created sessionFactory
//     */
//    private static SessionFactory buildSessionFactory() {
//        try {
//            Configuration c = new Configuration();
//            c.configure();
//            c.addAnnotatedClass(Airline.class);
//            return c.buildSessionFactory();
//        } catch (Exception ex) {
//            System.out.println("Initial SessionFactory creation failed.");
//            System.err.println(ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
//
//    /**
//     * Establishes the connection with the DB
//     * @return connection variable
//     */
//    private static Connection getConnection() {
//        try {
//            return DriverManager.getConnection(DB_URL, username, password);
//        } catch (SQLException e) {
//            System.out.println("Initial getConnection failed.");
//            System.err.println(e);
//            throw new ExceptionInInitializerError(e);
//        }
//    }
//
//    /**
//     * Opens a new session with the DB
//     * @return new session
//     */
//    private static Session openSession() {
//        return sessionFactory.openSession();
//    }
//
//    /**
//     * Closes the session and the sessionFactory with the DB
//     */
//    public void closeConnection(){
//        if (connection != null) {
//            sessionFactory.close();
//            session.close();
//        }
//    }
}