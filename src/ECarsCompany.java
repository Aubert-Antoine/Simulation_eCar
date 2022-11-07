import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ECarsCompany {

    private String companyName;
    private ElectricCar ElecCar;
    private Map<String, Integer> numberofmodel= new HashMap<String, Integer>();
    private Customer customer;

    public ECarsCompany(String pCompanyName, ElectricCar pElectricCar, Map<String, Integer> pNbCarOwnByCompany, Customer pCustomer){
        super();
        this.companyName = pCompanyName;
        this.ElecCar = pElectricCar;
        this.numberofmodel.putAll(pNbCarOwnByCompany);
        this.customer = pCustomer;
    }

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


    public void availableEChargerPoints(String city, boolean availableOutlets) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //print for the city all outlets if false & only free outlets if true with address

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        String SQLQ;
        ResultSet outResultSet;


        if(availableOutlets){
            SQLQ = String.format("SELECT address FROM Charging_Point WHERE city = '%s' and available_outlets > 0",city);
        } else {
            SQLQ = String.format("SELECT address FROM Charging_Point WHERE city = '%s' ",city);
        }

        try {
            outResultSet = stmt.executeQuery(SQLQ);
            System.out.println("\n Query : "+ SQLQ +"  ->  done..");
            while (outResultSet.next()){
                System.out.println(outResultSet.getString(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        }//catch
    }//availableEChargerPoints()

    public String getCompanyName() {
        return companyName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<String, Integer> getNumberofmodel() {
        return numberofmodel;
    }

    public void setElecCar(ElectricCar elecCar) {
        ElecCar = elecCar;
    }

    public void setNumberofmodel(Map<String, Integer> numberofmodel) {
        this.numberofmodel = numberofmodel;
    }

    public void welcomeMessage() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println(this.companyName);
        System.out.println(this.numberofmodel.toString());
        printNbChargingPoints();
    }
}
