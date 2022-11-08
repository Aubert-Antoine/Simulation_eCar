import java.sql.*;

public class EChargingPoint {

    private int point_id;
    private int number_of_outlets;
    private int availableOutlets;
    private String city;
    private String address;



    /**
     * Print the time of start of charging for a specific car
     * @param pCustomer
     * @param pPointID
     */
    public void startCharging(Customer pCustomer, int pPointID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //Print the beginning of charging -> query
        String SQLQBeginningCharging = "SELECT starting_timestamp FROM Charging_Process WHERE point_id = "+pPointID;
        System.out.println(queryDatabase(SQLQBeginningCharging));

        //Update the database => remove 1 slot of available outlet -> update
        String SQLUAvailableOutlets = "UPDATE Charging_Point SET available_outlets = available_outlets-1 WHERE point_id = "+pPointID;
        updateDatabase(SQLUAvailableOutlets);

        // insert a new record => change the starting_timestamp -> Update
        String SQLUNewRecord = "";
        updateDatabase(SQLUNewRecord);
    }//startCharging

    /**
     * make a request to the mysql database and return the result
     * @param pQuery
     * @return outResultSet a ResultSet type
     */
    public int queryDatabase(String pQuery) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Statement stmt = Database.connectDatabase();


        int pID = 0;
        try {
            ResultSet outResultSet;
            outResultSet = stmt.executeQuery(pQuery);
            System.out.println("Query : "+ pQuery +"  ->  done..");

            pID = outResultSet.getInt(0);
            System.out.println("outResultSet : " + pID);
            outResultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();


        }//catch
        return pID;
    }//queryDatabase(.)

    /**
     * Update the database
     * @param pUpdate
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void updateDatabase(String pUpdate) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Statement stmt = Database.connectDatabase();


        try {
            stmt.executeUpdate(pUpdate);
            System.out.println("Query : "+ pUpdate +"  ->  done..");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//updateDatabase

//    public void finishCharging(Customer customer) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        //check if a customer has his car at a charging point : The date of starting_timestamp > fully_charge_timestamp => is charging -> Queries
//        String SQLQStartChargingTimeCar = "";
//        String SQLQEndChargingTimeCar = "";
//
////        SQLQStartChargingTimeCar>SQLQEndChargingTimeCar = charge
//        if(date1.compareTo(date2) > 0){
//            System.out.println("The car is charging");
//        }
//
//        //replace null value to the current dateTime in field fully-charge_timestamp
//        String SQLUReplaceValues = "";
//        updateDatabase(SQLUReplaceValues);
//
//        //Update the database => add 1 slot of available outlet -> update
//        String SQLUAvailableOutlets = "UPDATE Charging_Point SET available_outlets = available_outlets+1 WHERE point_id = "+pPointID;
//        //On  a pas moyen d'avoir acces aux id depuis customer
//        updateDatabase(SQLUAvailableOutlets);
//    }//finishCharging
}
