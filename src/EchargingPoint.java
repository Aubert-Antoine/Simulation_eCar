import java.sql.*;

public class EchargingPoint {

    private int point_id;
    private int number_of_outlets;
    private int availableOutlets;
    private String city;
    private String address;

//    public EchargingPoint(){
//        this.point_id = 0;
//        this.number_of_outlets =0;
//        this.availableOutlets = 0;
//        this.city = "";
//        this.address = "";
//    }
//
//    public EchargingPoint(int pPoint_id, int pNumber_of_outlets, int pAvailableOutlets, String pCity, String pAddress){
//        this.point_id = pPoint_id;
//        this.number_of_outlets = pNumber_of_outlets;
//        this.availableOutlets = pAvailableOutlets;
//        this.city = pCity;
//        this.address = pAddress;
//    }

    public int getAvailableOutlets() {
        return availableOutlets;
    }

    /**
     * Print the time of start of charging for a specific car
     * @param pCustomer
     * @param pPointID
     */
    public void startCharging(Customer pCustomer, int pPointID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {       // pPointID = point_id ?
        String SQLQBeginningCharging = "SELECT starting_timestamp FROM Charging_Process WHERE point_id = "+pPointID;
        System.out.println(queryDatabase(SQLQBeginningCharging));
    }

    /**
     * make a request to the mysql database and return the result
     * @param pQuery
     * @return outResultSet a ResultSet type
     */
    public int queryDatabase(String pQuery) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();
        ResultSet outResultSet = null;
        String[] outResultString;
        int pID = 0;
        try {
            outResultSet = stmt.executeQuery(pQuery);
            System.out.println("Query : "+ pQuery +"  ->  done..");

            pID = outResultSet.getInt(0);
            System.out.println("outResultSet : " + pID);

        } catch (SQLException e) {
            e.printStackTrace();


        }//catch
        conn.close();
        outResultSet.close();
        return pID;
    }//queryDatabase(.)

    public void updateDatabase(String pUpdate) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        try {
            stmt.executeUpdate(pUpdate);
            System.out.println("Query : "+ pUpdate +"  ->  done..");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();
    }

    public void finishCharging(Customer customer){

    }
}
