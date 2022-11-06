import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
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
     * writeInDatabase write in the pNameOfDatabase the content send by the function readCSV
     * There is 4 if because each database have not the same Type in row
     * @param pLines send by readCSV
     * @param pNameOfDatabase the name of the database which is filled
     */
    public void writeInDatabase(@NotNull LinkedList pLines, String pNameOfDatabase) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        // Open a connection
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        String[][] listContent = new String[pLines.size()][];
        int nbOfLine = pLines.size();      //ini here and not in the for because the value change due to the .poll which remove at each call
        String sqlLine = "";
        if(debug) System.out.println(nbOfLine + " le nb de ligne");
        for (int i = 0; i <nbOfLine; i++){
            listContent[i] = pLines.pollFirst().toString().split(",");
            if(debug) System.out.println(listContent[i]);
            if(pNameOfDatabase.equals("E_Car")){
                sqlLine = "INSERT INTO E_Car VALUES (" + listContent[i][0] +", '"+listContent[i][1] + "', '" + listContent[i][2] + "', '" + dateConverter(listContent[i][3]) +"')";
            } else if (pNameOfDatabase.equals("Charging_Point")) {
                sqlLine = "INSERT INTO Charging_Point VALUES (" + listContent[i][0] +", "+listContent[i][1] + ", " + listContent[i][2] + ", '" + listContent[i][3] +"', '"+ listContent[i][4] +"')";
            } else if (pNameOfDatabase.equals("Customer")) {
                sqlLine = "INSERT INTO Customer VALUES (" + listContent[i][0] +", '"+listContent[i][1] + "', '" + listContent[i][2] + "', " + listContent[i][3] +",'" + listContent[i][4] +"'," + listContent[i][5] + ", " + dateConverter(listContent[i][6]) + "," + /*listContent[i][7]*/ 1 + ")";
            } else if (pNameOfDatabase.equals("Charging_Process")) {
                sqlLine = "INSERT INTO Charging_Process VALUES (" + listContent[i][0] +", "+listContent[i][1] + ", " + listContent[i][2] + "," + /*timeStampConverter(listContent[i][3])*/ '23/10/2022 21:05:69' + ", '" + "23/10/2022/21:05:69"/*timeStampConverter(listContent[i][4])*/ + "')";
            } else{System.out.println("unknown name of database");}
            stmt.executeUpdate(sqlLine);
        }//for
        conn.close();
    }//writeInDatabase

    /**
     * convert the date dd/mm/yyyy to yyyy-mm-dd
     * https://stackoverflow.com/a/14039210
     * @author Antoine Aubert & stackoverflow
     * @param pDate the date at java.sql.date format -> notice that the result is the same with String format
     * @return
     */
    public String dateConverter(String pDate){
        if(debug) System.out.println(pDate + " est ce egal a 'null' : "+pDate.contains("null"));
        if(pDate.contains("null")){
            if(debug) System.out.println("null catch");
            return null;
        }else {
            java.util.Date date = new Date(pDate);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String format = formatter.format(date);

            if (debug) System.out.println(format);

            return "'"+format+"'"; // fix the bug due to null don't need quote but values need inside sql requests
        }
    }//dateConverter()


    public String timeStampConverter(String pTimeStamp){
        if(pTimeStamp.contains("null")){
            System.out.println("content null ? "+pTimeStamp.contains("null"));
            return null;
        }else{
            return "'"+"23/10/2022.21:05:69"+"'";
//            return "'"+pTimeStamp+"'"; //add quote for sql request
        }

    }//timeStampConverter
}//class

