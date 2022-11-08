import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class ECarsCompany {

    private String companyName;
    private ElectricCar ElecCar;
    private Map<String, Integer> numberofmodel = new HashMap<String, Integer>();
    private Customer customer;

    public ECarsCompany(String pCompanyName, ElectricCar pElectricCar, Map<String, Integer> pNbCarOwnByCompany, Customer pCustomer) {
        super();
        this.companyName = pCompanyName;
        this.ElecCar = pElectricCar;
        this.numberofmodel.putAll(pNbCarOwnByCompany);
        this.customer = pCustomer;
    }

    /**
     * Print tous les model de voiture et ses attributs dans l'ordre alphabetique vis a vis du nom dui model.
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void printCarModel() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        ResultSet outResultSet = null;
        String pQuery = "SELECT * FROM E_Car ORDER BY model ASC";

        try {
            outResultSet = stmt.executeQuery(pQuery);
            System.out.println("Query : " + pQuery + "  ->  done..");


            while (outResultSet.next()) {

                int column1 = outResultSet.getInt(1);
                String column2 = outResultSet.getString(2);
                String column3 = outResultSet.getString(3);
                Date column4 = outResultSet.getDate(4);

                System.out.println(column1 + "   " + column2 + "   " + column3 + "    " + column4);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        outResultSet.close();
        conn.close();
    }//printCarModel


    public void printNbChargingPoints() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        ResultSet outResultSet = null;
//        String pQuery = "SELECT SUM(number_of_outlets) AS city FROM Charging_Point";
//        String pSubQuery = "SELECT SUM(number_of_outlets) FROM Charging_Point WHERE city = 'Papho'";

        String[] city = {"Papho", "Limassol", "Larnaca", "Nicosia"};

        try {

            for (String town : city) {
                outResultSet = stmt.executeQuery("SELECT SUM(number_of_outlets) FROM Charging_Point WHERE city = '" + town + "'");
                System.out.println("Query : " + "SELECT SUM(number_of_outlets) FROM Charging_Point WHERE city = '" + town + "'" + "  ->  done..");

                while (outResultSet.next()) {

                    int column1 = outResultSet.getInt(1);
                    System.out.println(column1);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        outResultSet.close();
        conn.close();
    }//printCarModel


    public static void availableEChargerPoints(String city, boolean availableOutlets) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //print for the city all outlets if false & only free outlets if true with address

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

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


    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("Hello world!");

        Database db = new Database();
        EChargingPoint EP = new EChargingPoint();
        ElectricCar EC = new ElectricCar();
        Customer C = new Customer(1111, "NaN", EC, "NaN");

        Map<String, Integer> hm = new HashMap<String, Integer>();
        hm.put("model1", 3);
        hm.put("model2", 4);
        ECarsCompany ECC = new ECarsCompany("UCYC", EC, hm, C);


//        db.connectToDatabase();
//        db.writeInDatabase(db.readCSV("e-cars"), "E_Car");
//        db.writeInDatabase(db.readCSV("chargePoints"), "Charging_Point");
//        db.writeInDatabase(db.readCSV("customer"), "Customer");   //mercha pas car null
        //db.writeInDatabase(db.readCSV("chargingProcess"), "Charging_Process");
        //marche pas car y a un cas de null


//        ECC.welcomeMessage();
//        chatBox(EP,EC,ECC);
        ECC.Start_charging();
    }//main()

    public static void chatBox() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n\n\n");
        System.out.println("1. Show more info about the company.");
        System.out.println("2. Find a charging point");
        System.out.println("3. Login");
        System.out.println("4. Register");
        System.out.println("5. Exit");

        System.out.println("\nPick a number : ");
        int numberSelected;
        numberSelected = sc.nextInt();

        while (numberSelected < 1 || numberSelected > 5) {
            System.out.println("\n Pick a number : ");
            numberSelected = sc.nextInt();
        }//while
        sc.close();

        if (numberSelected == 1) {
            option1();
        } else if (numberSelected == 2) {
            option2();
        } else if (numberSelected == 3) {
            option3();
        } else if (numberSelected == 4) {
            option4();
        } else if (numberSelected == 5) {
            option5();
        } else {
            System.out.println("bug -- nb selected out of range 1-5");
        }
    }//chatBox

    public static void option1() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ECC.printCarModel();
        ECC.printNbChargingPoints();
        chatBox();
    }//op1

    public static void option2() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a city in the list : 'Papho', 'Larnaca', 'Limassol', 'Nicosia'");
        String inputCity = sc.nextLine();

        while (!inputCity.equals("Papho") || !inputCity.equals("Larnaca") || !inputCity.equals("Limassol") || !inputCity.equals("Nicosia")) {
            System.out.println("Enter a city in the list <!> : 'Papho', 'Larnaca', 'Limassol', 'Nicosia'");
            inputCity = sc.nextLine();
        }

        System.out.println("Show only free outlets ? y - n ");
        String inputFree = sc.nextLine();
        while (!inputFree.equals("y") || !inputFree.equals("n")) {
            System.out.println("Show only free outlets ? y - n ");
            inputFree = sc.nextLine();
        }
        if (inputFree.equals("y")) availableEChargerPoints(inputCity, true);
        else if (inputFree.equals("n")) availableEChargerPoints(inputCity, true);
        else System.out.println("bug -- lettre not accepted");
        sc.close();
        chatBox();
    }//op2

    public static void option3() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner(System.in);
        int customerID;
        boolean isUserExist = false;
        System.out.println("Entre your ID :");

        try {
            customerID = sc.nextInt();
            sc.close();

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
            Statement stmt = conn.createStatement();

            String SQLQisInBase = String.format("SELECT customer_id FROM Customer WHERE EXISTS(SELECT customer_id FROM Customer WHERE customer_id = %s )", customerID);
            ResultSet outResultSet = stmt.executeQuery(SQLQisInBase);
            String outStringResult = outResultSet.getString(1);
            System.out.println(outStringResult);
            //if () isUserExist = true;
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
//            option3(EP,EC,ECC);
        }
        sc.close();
        if (!isUserExist) {
            System.out.println("USER unknown");
            chatBox(EP, EC, ECC);
        } else {
            subChatBox();
        }
    }

    public static void option4() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give your ID number : ");
        String customerID = scanner.nextLine();


        //check if ID exist
        String SQLQid = String.format("SELECT customer_id FROM Customer WHERE customer_id = %s ", ID);

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        ResultSet outResultSet = null;

        String fullName;
        String carModel;
        String carColor;
        int carBatteryLevel;
        String carDateService;
        int carMillimetre;
        int modelID;

        try {
            outResultSet = stmt.executeQuery(SQLQid);
            System.out.println("Query : "+ SQLQid +"  ->  done..");

            if(outResultSet.getInt(0) != 0 ){ // si = 0 -> SQL null
                System.out.println("USER already exist");
                chatBox();
            }else {
                System.out.println("Enter your full name : ");
                fullName = scanner.nextLine();

                System.out.println("Enter your car model : ");
                carModel = scanner.nextLine();

                //check if the model exist      <!>
                String SQLQModel = "";
                outResultSet = stmt.executeQuery(SQLQModel);

//                if (outResultSet.getInt(1) == 0 ){ // <!> param 1 ?  // == 0 => retour de sql null
//                    System.out.println("The model is unknown");
//                    System.out.println("Enter your car model");
//                    carModel = scanner.nextLine();
            }else{
                System.out.println("Enter car color : ");
                carColor = scanner.nextLine();
                System.out.println("Enter car battery level : ");
                carBatteryLevel = scanner.nextInt();
                System.out.println("Enter car last service : ");
                carDateService = scanner.nextLine();
                System.out.println("Enter car millimetres : ");
                carMillimetre = scanner.nextInt();

                modelID = modelTomodelId(carModel);

                String SQLInsertInBase = String.format("INSERT INTO Customer(customer_id, full_name, car_registration_number," +
                        " model_id, car_color, car_battery_level, car_last_service, total_millimetres) VALUES (%e,'%s','%s',%e,'%s',%e,%e,%e)",
                        customerID, fullName,GetPreviousCarRegistrationNumber()+1,modelID,carColor,carBatteryLevel,carDateService,carMillimetre );

                scanner.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        outResultSet.close();
        conn.close();
        chatBox();
    }//option4

    public static void option5() {
        System.out.println("Quit()");
        return;
    }


    public static void subChatBox() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("a. Show car status");
        System.out.println("b. Start charging");
        System.out.println("c. Complete charging process");
        System.out.println("d. Logout");

        Scanner sc = new Scanner(System.in);

        System.out.println("\nPick a letter : ");
        String lettreSelected;
        lettreSelected = sc.nextLine();

        if (lettreSelected.equals("a")) {
            Show_car_status();
        } else if (lettreSelected.equals("b")) {
            Start_charging();
        } else if (lettreSelected.equals("c")) {
            Complete_charging_process();
        } else if (lettreSelected.equals("d")) {
            Logout();
        }
        sc.close();
    }//subChatBox

    public static void Show_car_status() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("battery level : ");
        ElectricCar.getBatteryBalance();
        System.out.println("total millimetres : ");
        ElectricCar.getCarTravelDistance();
        System.out.println("last service date : ");
        ElectricCar.getLastService();
        subChatBox();
    }

    //marche pas
    public static void Start_charging() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the charging’s point id.");
        String resultLine = scanner.nextLine();
        scanner.close();
        // chek is the id is valid ?

        String SQLQ = String.format("SELECT available_outlets FROM Charging_Point WHERE point_id = '%s' and available_outlets > 0", resultLine);


        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        ResultSet outResultSet = null;

        try {
            outResultSet = stmt.executeQuery(SQLQ);
            if(outResultSet.getInt(0)>0) System.out.println("");

        } catch (SQLException e) {
            e.printStackTrace();
        }//catch

        conn.close();
        outResultSet.close();
        return resultInt;

        System.out.println(queryDatabaseInt(SQLQ));


    }// Start_charging

    public static void Complete_charging_process(int pCustomerID) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        ResultSet outResultSet = null;
        String SQLQ = String.format("SELECT starting_timestamp, fully_charge_timestamp FROM Charging_Process WHERE customer_id = '%s'",pCustomerID);

        try {
            outResultSet = stmt.executeQuery(SQLQ);
            System.out.println(outResultSet.getString(0) +"   "+ (outResultSet.getString(1)));

        } catch (SQLException e) {
            e.printStackTrace();
        }//catch

        conn.close();
        outResultSet.close();
        subChatBox();
    }

    public static void Logout() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        chatBox();
    }

    /**
     * return the model_id for a given model
     * @param pModel
     * @return
     */
    public static int modelTomodelId(String pModel) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        ResultSet outResultSet = null;
        String SQLQModelToModelID = String.format("SELECT model_id FROM E_Car WHERE model = '%s'",pModel);
        outResultSet = stmt.executeQuery(SQLQModelToModelID);
        int modelID = outResultSet.getInt(0);
        
        outResultSet.close();
        conn.close();
        return modelID;
    }

    public static int GetPreviousCarRegistrationNumber() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        ResultSet outResultSet = null;
        String SQLQModelToModelID = "SELECT MAX(car_registration_number) FROM Customer";
        outResultSet = stmt.executeQuery(SQLQModelToModelID);
        String maxRegistrationNumber = outResultSet.getString(0);
        String registrationNumber = maxRegistrationNumber.substring(3);
        int number = parseInt(registrationNumber);

        outResultSet.close();
        conn.close();
        return number;
    }
}//class
