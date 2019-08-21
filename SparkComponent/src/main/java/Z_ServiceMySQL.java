// import java.util.*;
// import org.hibernate.*;
// import org.hibernate.cfg.*;
// import java.io.Serializable;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
//
//
// /**
//  * Class that implements the concrete access to H2 database
//  */
// public class UserServiceH2 implements Serializable {
//
//     public static String DB_URL = "jdbc:h2:~/users_database";
//     public static String username = "sa";
//     public static String password = "";
//
//     private static final SessionFactory sessionFactory = buildSessionFactory();
//     private static final Connection connection = getConnection();                // variable to establish the connection with db
//     public static Session session = openSession();                               // variable to set up queries
//
//
//     /**
//      * Creates the SessionFactory using hibernate.cfg.xml configuration file
//      * @return the created sessionFactory
//      */
//     private static SessionFactory buildSessionFactory() {
//         try {
//             Configuration c = new Configuration();
//             c.configure();
//             c.addAnnotatedClass(User.class);
//             return c.buildSessionFactory();
//         } catch (Exception ex) {
//             System.out.println("Initial SessionFactory creation failed.");
//             System.err.println(ex);
//             throw new ExceptionInInitializerError(ex);
//         }
//     }
//
//     /**
//      * Establishes the connection with the DB
//      * @return connection variable
//      */
//     private static Connection getConnection() {
//         try {
//             return DriverManager.getConnection(DB_URL, username, password);
//         } catch (SQLException e) {
//             System.out.println("Initial getConnection failed.");
//             System.err.println(e);
//             throw new ExceptionInInitializerError(e);
//         }
//     }
//
//     /**
//      * Opens a new session witht the DB
//      * @return new session
//      */
//     private static Session openSession() {
//         return sessionFactory.openSession();
//     }
//
//     /**
//      * Closes the session and the sessionFactory with the DB
//      */
//     public void closeConnection(){
//         if (connection != null) {
//             sessionFactory.close();
//             session.close();
//         }
//     }
//
//     //--------------------------------Methods for all types of user-------------------------------------
//
//     /**
//      * Retrieves the average membership length in years given all users
//      * @return average membership length
//      */
//     public int getAvgMembership() {
//         String query = "select cast(avg(datediff('YEAR', u.dateOfJoining, now())) as int) from User u";
//         List<Integer> result = session.createQuery(query).list();
//         return result.get(0);
//     }
//
//     /**
//      * Retrieves all the users stored in DB
//      * @return list of all users
//      */
//     public List<User> getAllUsers() {
//         return session.createQuery("from User u").list();
//     }
//
//     /**
//      * Retrieves the last three users based on the date of join
//      * @return list of last three users
//      */
//     public List<User> getLastThreeUsers() {
//         //session.Query.
//         return session.createQuery("from User u order by u.dateOfJoining desc").setMaxResults(3).list();
//     }
//
//     //--------------------------------Methods for user-------------------------------------
//
//     /**
//      * Adds a new plain user to the DB
//      * @param u user to be inserted
//      */
//     public void createUser(User u) {
//         try {
//             session.beginTransaction();
//             session.save(u);
//             session.getTransaction().commit();
//         } catch (HibernateException e) {
//             System.out.println("Errors while creating new user");
//             e.printStackTrace();
//         }
//     }
//
//     /**
//      * Deletes the plain user from the DB
//      * @param u user to be deleted
//      */
//     public void deleteUser (User u) {
//         session.beginTransaction();
//         session.delete(u);
//         session.getTransaction().commit();
//     }
//
//     /**
//      * Updates the plain user in the DB
//      * @param u user to be updated with new information
//      */
//     public void updateUser (User u) {
//         session.beginTransaction();
//         session.update(u);
//         session.getTransaction().commit();
//     }
//
// }
