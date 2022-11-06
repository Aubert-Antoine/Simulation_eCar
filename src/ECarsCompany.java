import java.sql.*;
import java.util.Date;

public class ECarsCompany {


    private String model;
    private String chargingSpeed;
    private java.util.Date releaseDate;

    /**
     * Print tous les model de voiture et ses attributs dans l'ordre alphabetique vis a vis du nom dui model.
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void printCarModel()throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        ResultSet outResultSet = null;
        String pQuery = "SELECT * FROM E_Car ORDER BY model ASC";

        try {
            outResultSet = stmt.executeQuery(pQuery);
            System.out.println("Query : "+ pQuery +"  ->  done..");


            while (outResultSet.next()){

                int column1 = outResultSet.getInt(1);
                String column2 = outResultSet.getString(2);
                String column3 = outResultSet.getString(3);
                Date column4 = outResultSet.getDate(4);

                System.out.println(column1 +"   "+ column2+"   "+ column3+"    "+  column4 );
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        outResultSet.close();
        conn.close();
    }//printCarModel


    public void printNbChargingPoints()throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        ResultSet outResultSet = null;
//        String pQuery = "SELECT SUM(number_of_outlets) AS city FROM Charging_Point";
//        String pSubQuery = "SELECT SUM(number_of_outlets) FROM Charging_Point WHERE city = 'Papho'";

        String[] city = {"Papho", "Limassol", "Larnaca", "Nicosia"};

        try {

            for (String town : city) {
                outResultSet = stmt.executeQuery("SELECT SUM(number_of_outlets) FROM Charging_Point WHERE city = '"+ town +"'");
                System.out.println("Query : "+ "SELECT SUM(number_of_outlets) FROM Charging_Point WHERE city = '"+ town +"'" +"  ->  done..");

                while (outResultSet.next()){

                    int column1 = outResultSet.getInt(1);
                    System.out.println(column1);
                }

            }


        } catch (Exception e){
            e.printStackTrace();
        }
        outResultSet.close();
        conn.close();
    }//printCarModel


    public void availableEChargerPoints(String city, boolean availableOutlets){

    }


}
