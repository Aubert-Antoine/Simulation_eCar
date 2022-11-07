import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    static final String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7545214";
    static final String USER = "sql7545214";
    static final String PASS = "EtqbWfmqYa";

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("Hello world!");
        Database db = new Database();
        db.connectToDatabase();
//        db.writeInDatabase(db.readCSV("e-cars"), "E_Car");
//        db.writeInDatabase(db.readCSV("chargePoints"), "Charging_Point");
//        db.writeInDatabase(db.readCSV("customer"), "Customer");   //mercha pas car null
        //db.writeInDatabase(db.readCSV("chargingProcess"), "Charging_Process");
        //marche pas car y a un cas de null

        EChargingPoint EP = new EChargingPoint();
//        int i = EP.queryDatabase("SELECT starting_timestamp FROM Charging_Process WHERE Point_id = "+ 526);
//        System.out.println(i);



//        EP.updateDatabase("UPDATE Charging_Point SET number_of_outlets = 1, available_outlets = 1, city = 'P', address = 'P' WHERE point_id = 524");

        ECarsCompany EC = new ECarsCompany();
        EC.printCarModel();
        EC.printNbChargingPoints();
        EC.availableEChargerPoints("Papho", false);


//        db.timeStampConverter();
    }

}