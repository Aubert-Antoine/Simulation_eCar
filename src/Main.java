import java.sql.SQLException;

public class Main {

    static final String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7545214";
    static final String USER = "sql7545214";
    static final String PASS = "EtqbWfmqYa";

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("Hello world!");
        Database db = new Database();
        db.connectToDatabase();
        db.writeInDatabase(db.readCSV("e-cars"), "E_Car");
        db.writeInDatabase(db.readCSV("chargePoints"), "Charging_Point");
        db.writeInDatabase(db.readCSV("customer"), "Customer");
        db.writeInDatabase(db.readCSV("chargingProcess"), "Charging_Process");
    }
}