import java.sql.SQLException;

public class Main {

    static final String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7542187";
    static final String USER = "sql7542187";
    static final String PASS = "FwRe9s8Cf1";

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("Hello world!");
        Database db = new Database();
        //db.connectToDatabase();
        db.readCSV("e-cars", "c");
    }
}