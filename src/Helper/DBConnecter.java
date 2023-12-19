package Helper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class is for the database connection. It opens and closes the connection with the SQL Database
 */
public class DBConnecter {
        // code from wgu resource video
        //defining database variables using the code provided by WGU
        private static final String protocol = "jdbc";
        private static final String vendor = ":mysql:";
        private static final String location = "//localhost/";
        private static final String databaseName = "client_schedule";
        private static final String jdbcUrl = protocol + vendor + location + databaseName;// "?connectionTimeZone = UTC"; // LOCAL
        private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
        private static final String userName = "sqlUser"; // Username
        private static String password = "Passw0rd!"; // Password
        public static Connection connection;  // Connection Interface

    /**
     * This method establishes and open the connection to the SQL database.
     *
     * @return Connection object to the database.
     */
        public static Connection getConnection()
        {
            try {
                Class.forName(driver); // Locate Driver
                connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
                System.out.println("Connection successful!");
            }
            catch(Exception e)
            {
                System.out.println("Error:" + e.getMessage());
            }

            return connection;
        }

    /**
     * This method closes the connection to the SQL database.
     */
    public static void closeConnection() {
            try {
                connection.close();
                System.out.println("Connection closed!");
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("Error: Failed to connect to database");
            }
        }
    }
