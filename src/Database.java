import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class Database implements DatabaseInterface{

    static final String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7557804";
    static final String USER = "sql7557804";
    static final String PASS = "SljGiCuAPm";

    /**
     * connectToDatabase create a connection and then make their 4 tables
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public static void connectToDatabase() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // Open a connection
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
        Statement stmt =  conn.createStatement();

        try {
            String sql ="CREATE TABLE Charging_Point " +
                        "( point_id INTEGER NOT NULL, " +
                        " number_of_outlets INTEGER, " +
                        " available_outlets INTEGER, " +
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
        stmt.close();
        conn.close();
    }//connectToDatabase()




    /**
     * readCSV read the csv file give in param via a scanner
     * @param pNameOfCSVFile the csv file name without '.csv'
     * @return a LinkedList with all lines via String format
     */
    public static LinkedList readCSV(String pNameOfCSVFile){
        /*
        make a linkedList to transfer data to writeInDatabase the aim is to make 1 methode = 1 thing
        LinkedList is usefully because the number of element and type are not defined
         */
        LinkedList CSVLines = new LinkedList();

        try {
            //join path to make an absolute path of data
            Path globalPath = Paths.get(UtilTools.currentDirectory(), "src", "csv", pNameOfCSVFile+".csv");
            Scanner scanner = new Scanner(globalPath);
            String DELIMITER = ",";
            scanner.useDelimiter(DELIMITER);

            scanner.nextLine(); //read the first line useless

            while (scanner.hasNextLine()){CSVLines.add(scanner.nextLine());}

            scanner.close();
            return CSVLines;
        }catch (Exception ex) {ex.printStackTrace();}//catch

        return null;
    }//readCSV




    /**
     * writeInDatabase write in the pNameOfDatabase the content send by the function readCSV
     * There is 4 if because each database have not the same Type in row
     * @param pLines send by readCSV
     * @param pNameOfDatabase the name of the database which is filled
     */
    public static void writeInDatabase(@NotNull LinkedList pLines, String pNameOfDatabase) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {


        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
        Statement stmt =  conn.createStatement();

        String[][] listContent = new String[pLines.size()][];
        int nbOfLine = pLines.size();      //ini here and not in the for because the value change due to the .poll which remove at each call
        String sqlLine = "";


        for (int i = 0; i <nbOfLine; i++){
            listContent[i] = Objects.requireNonNull(pLines.pollFirst()).toString().split(",");

            //fill the good database
            switch (pNameOfDatabase) {
                case "E_Car":
                    sqlLine = "INSERT INTO E_Car VALUES (" + listContent[i][0] + ", '" + listContent[i][1] + "', '" + listContent[i][2] + "', " + UtilTools.dateConverter(listContent[i][3]) + ")";
                    break;
                case "Charging_Point":
                    sqlLine = "INSERT INTO Charging_Point VALUES (" + listContent[i][0] + ", " + listContent[i][1] + ", " + listContent[i][2] + ", '" + listContent[i][3] + "', '" + listContent[i][4] + "')";
                    break;
                case "Customer":
                    sqlLine = "INSERT INTO Customer VALUES (" + listContent[i][0] + ", '" + listContent[i][1] + "', '" + listContent[i][2] + "', " + listContent[i][3] + ",'" + listContent[i][4] + "'," + listContent[i][5] + ", " + UtilTools.dateConverter(listContent[i][6]) + "," + null + ")";
                    break;
                case "Charging_Process":
                    sqlLine = "INSERT INTO Charging_Process VALUES (" + listContent[i][0] + ", " + listContent[i][1] + ", " + listContent[i][2] + "," + UtilTools.timeStampConverter2(listContent[i][3]) + "," + UtilTools.timeStampConverter2(listContent[i][4]) + ")";
                    break;
                default:
                    System.out.println("unknown name of database");
                    break;
            }//switch

            stmt.executeUpdate(sqlLine);
        }//for
        stmt.close();
        conn.close();
    }//writeInDatabase

    public static int GetPreviousCarRegistrationNumber() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
        Statement stmt =  conn.createStatement();

        int number = 0;
        ResultSet outResultSet = null;
        String SQLQModelToModelID = "SELECT MAX(car_registration_number) FROM Customer";

        outResultSet = stmt.executeQuery(SQLQModelToModelID);

        try {
            if(outResultSet.next()){
                String maxRegistrationNumber = outResultSet.getString(1);
                number = UtilTools.stringToInt(maxRegistrationNumber, 3);
            }else {
                System.out.println("outResultSet.next() == false ");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            outResultSet.close();
            stmt.close();
            conn.close();
            return number;
        }
    }


}//class

