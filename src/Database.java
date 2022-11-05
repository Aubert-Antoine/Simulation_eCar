import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class Database implements DatabaseInterface{

    static final boolean debug = true;



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
                        " release_date DATE, " +
                        " PRIMARY KEY (model_id))";
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
                        " total_millimetres INTEGER, " +
                        "PRIMARY KEY ( customer_id )," +
                        "FOREIGN KEY (model_id) REFERENCES E_Car(model_id))";
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
                        "FOREIGN KEY (point_id) REFERENCES Charging_Point(point_id))";
            stmt.executeUpdate(sql);
            System.out.println("Table Charging_Process created");
        } catch (SQLException e) {
            e.printStackTrace();
        }//catch
        conn.close();
    }//connectToDatabase()


    /**
     * readCSV read the csv file give in param via a scanner
     * @param pNameOfCSVFile the csv file name without '.csv'
     * @return a LinkedList with all lines via String format
     */
    public LinkedList readCSV(String pNameOfCSVFile){
        try {
            //join path to make an absolute path of data
            Path globalPath = (Path)Paths.get(currentDirectory(), "src", "csv", pNameOfCSVFile+".csv");
            Scanner scanner = new Scanner(globalPath);
            String DELIMITER = ",";
            scanner.useDelimiter(DELIMITER);

            scanner.nextLine(); //read the first line useless

            LinkedList CSVLines = new LinkedList();
            while (scanner.hasNextLine()){CSVLines.add(scanner.nextLine());}
            scanner.close();
            return CSVLines;
        }catch (Exception ex) {
            ex.printStackTrace();
        }//catch
        return null;
    }//readCSV


    /**
     * currentDirectory return the absolut path of the "Simulation eCar" project.
     * @return
     */
    public String currentDirectory(){
        return System.getProperty("user.dir");
    }//currentDirectory

    /**
     * writeInDatabase write in the pNameOfDatabase the content send by the fonction readCSV
     * @param pLines send by readCSV
     * @param pNameOfDatabase the name of the database wich is filled
     */
    public void writeInDatabase(LinkedList pLines, String pNameOfDatabase) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        // Open a connection
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();


        if(debug) {
            System.out.println("The seize of the LinkedList is : " + pLines.size());
            System.out.println(pLines.pollFirst());
            System.out.println(pLines.pollFirst());
            System.out.println(pLines.pollFirst());
        }

         if(pNameOfDatabase.equals("E_Car")){
             String[] lineContent = pLines.pollFirst().toString().split(",");
             String sqlLine = "INSERT INTO E_Car VALUES (" + lineContent[0] +", '"+lineContent[1] + "', '" + lineContent[2] + "', '" + dateConverter(lineContent[3]) +"')";
             System.out.println(dateConverter(lineContent[3]));
             stmt.executeUpdate(sqlLine);
         }
         else{System.out.println("unknown name of database");}



        conn.close();
    }//writeInDatabase

    /**
     * convert the date dd/mm/yyyy to yyyy-mm-dd
     * https://stackoverflow.com/a/14039210
     * @author Antoine Aubert & stackoverflow
     * @param pDate the date at java.sql.date format -> notice that the result is the same with String format
     * @return
     */
    public java.sql.Date dateConverter(String pDate){
        java.util.Date date = new Date(pDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date format = java.sql.Date.valueOf(formatter.format(date));
        System.out.println(format);
        return format;
    }
}

