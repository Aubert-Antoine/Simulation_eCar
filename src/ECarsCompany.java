import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class ECarsCompany {

    private static String companyName = "Company name";
    private static ElectricCar ElecCar;
    private static Map<String, Integer> numberofmodel = new HashMap<String, Integer>();
    private static Customer customer;

    public ECarsCompany(String pCompanyName, ElectricCar pElectricCar, Map<String, Integer> pNbCarOwnByCompany, Customer pCustomer) {
        super();
        this.companyName = pCompanyName;
        this.ElecCar = pElectricCar;
        this.numberofmodel.putAll(pNbCarOwnByCompany);
        this.customer = pCustomer;
    }
    public ECarsCompany() {
        super();
        this.companyName = "pCompanyName";
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("Hello world!");
//
//        Database.connectToDatabase();
//        Database.writeInDatabase(Database.readCSV("e-cars"), "E_Car");
//        Database.writeInDatabase(Database.readCSV("chargePoints"), "Charging_Point");
//        Database.writeInDatabase(Database.readCSV("customer"), "Customer");
//        Database.writeInDatabase(Database.readCSV("chargingProcess"), "Charging_Process");

        welcomeMessage();
        chatBox();
    }//main()

    public static void welcomeMessage() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println(companyName);
        System.out.println("The number of car is : "+getCarNumber());
        printNbChargingPoints();
    }


    public static void chatBox() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("\n\n");
        System.out.println("1. Show more info about the company.");
        System.out.println("2. Find a charging point");
        System.out.println("3. Login");
        System.out.println("4. Register");
        System.out.println("5. Exit");

        int nbSelected = (int) UtilTools.scanner(true, 1,5);

        if (nbSelected == 1) {
            option1();
        } else if (nbSelected == 2) {
            option2();
        } else if (nbSelected == 3) {
            option3();
        } else if (nbSelected == 4) {
            option4();
        } else if (nbSelected == 5) {
            option5();
        } else {
            System.out.println("bug -- nb selected out of range 1-5");
        }
    }//chatBox


    public static void option1() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        printCarModel();
        printNbChargingPoints();
        chatBox();
    }//option1

    public static void option2() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("Enter a city in the list : 'Papho', 'Larnaca', 'Limassol', 'Nicosia'");
        String inputCity = (String) UtilTools.scanner(false,0,0);

        if(inputCity.equals("Papho") || inputCity.equals("Larnaca") || inputCity.equals("Limassol") || inputCity.equals("Nicosia")){
            System.out.println("Show only free outlets ? y - n ");
            String inputFree = (String) UtilTools.scanner(false,0,0);
            if(inputFree.equals("y")){
                availableEChargerPoints(inputCity, true);
                chatBox();
            } else if (inputFree.equals("n")) {
                availableEChargerPoints(inputCity, false);
                chatBox();
            }else{
                option2();
            }
        }else{
            option2();
        }
    }//option2

    public static Object option3() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
        Statement stmt =  conn.createStatement();

        int customerID = 0;
        boolean isUserExist = false;
        System.out.println("Entre your ID :");

        try {

            customerID = (int) UtilTools.scanner(true,0,999999);

            String SQLQisInBase = String.format("SELECT customer_id FROM Customer WHERE EXISTS(SELECT customer_id FROM Customer WHERE customer_id = %s )", customerID);
            ResultSet outResultSet = stmt.executeQuery(SQLQisInBase);

            if (!outResultSet.next()) {
                System.out.println("Unknown ID");
                chatBox();
                conn.close();
                return null;
            } else {
                subChatBox(customerID);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn.close();
        return customerID;
    }

    public static void option4() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
        Statement stmt =  conn.createStatement();

        System.out.println("Give your ID number : ");
        String customerID = (String) UtilTools.scanner(false,0,0);

        //check if ID exist
        String SQLQid = String.format("SELECT customer_id FROM Customer WHERE customer_id = %s ", customerID);


        ResultSet outResultSet = null;
        String fullName, carModel, carColor, carDateService;
        int carBatteryLevel, carMillimetre, modelID;

        try {
            outResultSet = stmt.executeQuery(SQLQid);
            System.out.println("Query : "+ SQLQid +"  ->  done..");

            if(outResultSet.getInt(0) != 0 ){ // si = 0 -> SQL null
                System.out.println("USER already exist");
                chatBox();
            }else {


                System.out.println("Enter your full name : ");
                fullName = (String) UtilTools.scanner(false,0,0);

                System.out.println("Enter your car model : ");
                carModel = (String) UtilTools.scanner(false,0,0);

                //check if the model exist      <!>
                String SQLQModel = "SELECT model_id FROM E_Car WHERE model = '" + carModel+"'";
                outResultSet = stmt.executeQuery(SQLQModel);

                if (!outResultSet.next()) {
                    System.out.println("The model is unknown");
                    option4();
                } else {


                    System.out.println("Enter car color : ");
                    carColor = (String) UtilTools.scanner(false,0,0);
                    System.out.println("Enter car battery level : ");
                    carBatteryLevel = (int) UtilTools.scanner(true,0,100);
                    System.out.println("Enter car last service : ");
                    carDateService = (String) UtilTools.scanner(false,0,0);
                    System.out.println("Enter car millimetres : ");
                    carMillimetre = (int) UtilTools.scanner(true,0,99999999);

                    modelID = modelToModelId(carModel);

                    String SQLInsertInBase = String.format("INSERT INTO Customer(customer_id, full_name, car_registration_number," +
                                    " model_id, car_color, car_battery_level, car_last_service, total_millimetres) VALUES (%e,'%s','%s',%e,'%s',%e,%e,%e)",
                            customerID, fullName, Database.GetPreviousCarRegistrationNumber() + 1, modelID, carColor, carBatteryLevel, carDateService, carMillimetre);
                    stmt.executeUpdate(SQLInsertInBase);
                }//else
            }//else
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            conn.close();
        }

        outResultSet.close();
        chatBox();
    }//option4

    public static void option5() {
        System.out.println("Quit()");
        return;
    }//option5


    public static void subChatBox(int pID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("a. Show car status");
        System.out.println("b. Start charging");
        System.out.println("c. Complete charging process");
        System.out.println("d. Logout");

        String lettreSelected = (String) UtilTools.scanner(false,0,0);


        if (lettreSelected.equals("a")) {
            Show_car_status(pID);
        } else if (lettreSelected.equals("b")) {
            Start_charging(pID);
        } else if (lettreSelected.equals("c")) {
            Complete_charging_process(pID);
        } else if (lettreSelected.equals("d")) {
            Logout();
        }
    }//subChatBox


    public static void Show_car_status(int pID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("battery level : ");
        System.out.println(ElectricCar.getBatteryBalance());
        System.out.println("total millimetres : ");
        System.out.println(ElectricCar.getCarTravelDistance());
        System.out.println("last service date : ");
        System.out.println(ElectricCar.getLastService());
        subChatBox(pID);
    }

    public static void Start_charging(int pID) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
        Statement stmt =  conn.createStatement();

        ResultSet outResultSet = null;

        System.out.println("Enter the charging’s point id.");
        int intID = (int) UtilTools.scanner(true,0,999999);

        // check is the id is valid
        String SQL = "SELECT count(*) FROM Charging_Point WHERE point_id = "+intID;
        outResultSet = stmt.executeQuery(SQL);
        if (outResultSet.next() == false) {
            System.out.println("This point ID is not in database");
            subChatBox(pID);
        }

        String SQLQ = String.format("SELECT available_outlets FROM Charging_Point WHERE point_id = %e and available_outlets > 0", intID);
        outResultSet = stmt.executeQuery(SQLQ);
        outResultSet.next();
        if (outResultSet.getInt(2)==0) {
            System.out.println("Their is not available outlets");
            subChatBox(pID);
        }
        conn.close();
        EChargingPoint.startCharging(pID,intID);
    }// Start_charging

    public static void Complete_charging_process(int pCustomerID) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
        Statement stmt =  conn.createStatement();


        ResultSet outResultSet = null;
        String SQLQ = String.format("SELECT starting_timestamp, fully_charge_timestamp FROM Charging_Process WHERE customer_id = '%s'",pCustomerID);

        try {
            outResultSet = stmt.executeQuery(SQLQ);
            System.out.println(outResultSet.getString(0) +"   "+ (outResultSet.getString(1)));

        } catch (SQLException e) {
            e.printStackTrace();
        }//catch

        outResultSet.close();
        conn.close();
        subChatBox(pCustomerID);
    }

    public static void Logout() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        chatBox();
    }



    public static void availableEChargerPoints(String city, boolean availableOutlets) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //print for the city all outlets if false & only free outlets if true with address
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
        Statement stmt =  conn.createStatement();

        String SQLQ;
        ResultSet outResultSet;


        if (availableOutlets) {
            SQLQ = String.format("SELECT address FROM Charging_Point WHERE city = '%s' and available_outlets > 0", city);
        } else {
            SQLQ = String.format("SELECT address FROM Charging_Point WHERE city = '%s' ", city);
        }

        try {
            outResultSet = stmt.executeQuery(SQLQ);
            System.out.println("\n Query : " + SQLQ + "  ->  done..");
            while (outResultSet.next()) {
                System.out.println(outResultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            conn.close();
        }
    }//availableEChargerPoints()




    /*
    Other Methode
     */

    /**
     * return the model_id for a given model
     * @param pModel
     * @return
     */
    public static int modelToModelId(String pModel) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
        Statement stmt =  conn.createStatement();

        ResultSet outResultSet = null;
        String SQLQModelToModelID = String.format("SELECT model_id FROM E_Car WHERE model = '%s'",pModel);
        outResultSet = stmt.executeQuery(SQLQModelToModelID);
        int modelID = outResultSet.getInt(0);

        outResultSet.close();
        conn.close();
        return modelID;
    }

    /**
     * Print tous les model de voiture et ses attributs dans l'ordre alphabetique vis a vis du nom dui model.
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void printCarModel() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
        Statement stmt =  conn.createStatement();



        String pQuery = "SELECT * FROM E_Car ORDER BY model ASC";

        try {
            ResultSet outResultSet = null;
            outResultSet = stmt.executeQuery(pQuery);
            System.out.println("Query : " + pQuery + "  ->  done..");


            while (outResultSet.next()) {

                int column1 = outResultSet.getInt(1);
                String column2 = outResultSet.getString(2);
                String column3 = outResultSet.getString(3);
                Date column4 = outResultSet.getDate(4);

                System.out.println(column1 + "   " + column2 + "   " + column3 + "    " + column4);
            }
            outResultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            conn.close();
        }
    }//printCarModel


    public static void printNbChargingPoints() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
        Statement stmt =  conn.createStatement();

        ResultSet outResultSet = null;
        String[] city = {"Papho", "Limassol", "Larnaca", "Nicosia"};

        try {
            for (String town : city) {
                outResultSet = stmt.executeQuery("SELECT SUM(number_of_outlets) FROM Charging_Point WHERE city = '" + town + "'");
                System.out.println("Query : " + "SELECT SUM(number_of_outlets) FROM Charging_Point WHERE city = '" + town + "'" + "  ->  done..");

                while (outResultSet.next()) {

                    int column1 = outResultSet.getInt(1);
                    System.out.println(column1);
                }//while
            }//for
        } catch (Exception e) {
            e.printStackTrace();
        }
        outResultSet.close();
        conn.close();
    }//printCarModel

    public static int getCarNumber() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER, Database.PASS);
        Statement stmt =  conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT count(*) FROM E_Car");
        rs.next();
        conn.close();
        return rs.getInt(1);
    }

}//class
