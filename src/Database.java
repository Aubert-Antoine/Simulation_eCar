import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database implements DatabaseInterface{


    /**
     * connectToDatabase create a connection and then make their 4 tables
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public void connectToDatabase() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // Open a connection
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        try {
            String sql ="CREATE TABLE Charging_Point " +
                        "( point_id INTEGER NOT NULL, " +
                        " number_of_outlets INTEGER, " +
                        " available_outlets VARCHAR(255), " +
                        " city VARCHAR(255), " +
                        " address VARCHAR(255), " +
                        " PRIMARY KEY (point_id)) ";
            stmt.executeUpdate(sql);
            System.out.println("Table Charging_Point created");
        } catch (SQLException e) {
            e.printStackTrace();
        }//catch

        try {
            String sql ="CREATE TABLE E_Car " +
                        "(model_id INTEGER not NULL, " +
                        " model VARCHAR(255), " +
                        " charging_speed VARCHAR(255), " +
                        " release_date DATE, "+
                        "PRIMARY KEY ( model_id )";
            stmt.executeUpdate(sql);
            System.out.println("Table E_Car created");
        } catch (SQLException e) {
            e.printStackTrace();
        }//catch

        try {
            String sql ="CREATE TABLE Customer " +
                        "(customer_id INTEGER not NULL, " +
                        " full_name VARCHAR(255), " +
                        " car_registration_number VARCHAR(255), " +
                        " model_id INTEGER, " +
                        " car_color VARCHAR(255), " +
                        " car_battery_level INTEGER, " +
                        " car_last_service DATE, " +
                        " total_millimetres INTEGER, "+
                        "PRIMARY KEY ( customer_id ),"+
                        "FOREIGN KEY (model_id) REFERENCES E_Car(model_id)";
            stmt.executeUpdate(sql);
            System.out.println("Table Customer created");
        } catch (SQLException e) {
            e.printStackTrace();
        }//catch

        try {
            String sql ="CREATE TABLE Charging_Process " +
                        "(process_id INTEGER not NULL, " +
                        " customer_id INTEGER, " +
                        " point_id INTEGER, " +
                        " starting_timestamp TIMESTAMP, " +
                        " fully_charge_timestamp TIMESTAMP, " +
                        "PRIMARY KEY ( process_id, customer_id, point_id ),"+
                        "FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),"+
                        "FOREIGN KEY (point_id) REFERENCES Charging_Point(point_id)";
            stmt.executeUpdate(sql);
            System.out.println("Table Charging_Process created");
        } catch (SQLException e) {
            e.printStackTrace();
        }//catch
        conn.close();
    }//connectToDatabase()
}
