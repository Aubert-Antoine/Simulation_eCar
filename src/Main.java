import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static final String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7545214";
    static final String USER = "sql7545214";
    static final String PASS = "EtqbWfmqYa";

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("Hello world!");

        Database db = new Database();
        EChargingPoint EP = new EChargingPoint();
        ElectricCar EC = new ElectricCar();
        Customer C  = new Customer(1111, "NaN", EC, "NaN" );

        Map< String,Integer> hm = new HashMap< String,Integer>();
        hm.put("model1", 3);
        hm.put("model2", 4);
        ECarsCompany ECC = new ECarsCompany("UCYC", EC, hm, C);


//        db.connectToDatabase();
//        db.writeInDatabase(db.readCSV("e-cars"), "E_Car");
//        db.writeInDatabase(db.readCSV("chargePoints"), "Charging_Point");
//        db.writeInDatabase(db.readCSV("customer"), "Customer");   //mercha pas car null
        //db.writeInDatabase(db.readCSV("chargingProcess"), "Charging_Process");
        //marche pas car y a un cas de null


        ECC.welcomeMessage();
        chatBox(EP,EC,ECC);
    }//main()

    public static void chatBox(EChargingPoint EP, ElectricCar EC, ECarsCompany ECC) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

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

        while (numberSelected<1 || numberSelected>5){
            System.out.println("\n Pick a number : ");
            numberSelected = sc.nextInt();
        }//while
        sc.close();

        if(numberSelected == 1 ){
            option1(EP,EC,ECC);
        } else if (numberSelected == 2) {
            option2(EP,EC,ECC);
        } else if (numberSelected == 3) {
            option3(EP,EC,ECC);
//        } else if (numberSelected == 4) {
//            option4(EP,EC,ECC);
//        } else if (numberSelected == 5) {
//            option5(EP,EC,ECC);
        }else {
            System.out.println("bug -- nb selected out of range 1-5");
        }
    }//chatBox

    public static void option1(EChargingPoint EP, ElectricCar EC, ECarsCompany ECC) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ECC.printCarModel();
        ECC.printNbChargingPoints();
        chatBox(EP,EC,ECC);
    }//op1

    public static void option2(EChargingPoint EP, ElectricCar EC, ECarsCompany ECC) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a city in the list : 'Papho', 'Larnaca', 'Limassol', 'Nicosia'");
        String inputCity = sc.nextLine();

        while(!inputCity.equals("Papho") || !inputCity.equals("Larnaca") || !inputCity.equals("Limassol") || !inputCity.equals("Nicosia")){
            System.out.println("Enter a city in the list <!> : 'Papho', 'Larnaca', 'Limassol', 'Nicosia'");
            inputCity = sc.nextLine();
        }

        System.out.println("Show only free outlets ? y - n ");
        String inputFree = sc.nextLine();
        while (!inputFree.equals("y") || !inputFree.equals("n")){
            System.out.println("Show only free outlets ? y - n ");
            inputFree = sc.nextLine();
        }
        if(inputFree.equals("y")) ECC.availableEChargerPoints(inputCity, true);
        else if (inputFree.equals("n"))  ECC.availableEChargerPoints(inputCity, true);
        else System.out.println("bug -- lettre not accepted");
        sc.close();
        chatBox(EP,EC,ECC);
    }//op2

    public static void option3(EChargingPoint EP, ElectricCar EC, ECarsCompany ECC) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
            if () isUserExist = true;
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
            subChatBox(EP,EC,ECC);
        }
    }
    public static void option4(EChargingPoint EP, ElectricCar EC, ECarsCompany ECC){

    }
    public static void option5(EChargingPoint EP, ElectricCar EC, ECarsCompany ECC){

    }



    public static void subChatBox(EChargingPoint EP, ElectricCar EC, ECarsCompany ECC){
        System.out.println("a. Show car status");
        System.out.println("b. Start charging");
        System.out.println("c. Complete charging process");
        System.out.println("d. Logout");

        Scanner sc = new Scanner(System.in);

        System.out.println("\nPick a letter : ");
        String lettreSelected;
        lettreSelected = sc.nextLine();

        if(lettreSelected.equals("a")){
            subOption1();
        }else if (lettreSelected.equals("b")) {
            subOption2();
        }
        else if (lettreSelected.equals("b")) {
            subOption3();
        }else if (lettreSelected.equals("b")) {
            subOption4();
        }
        sc.close();
    }//subChatBox

    public static void subOption1(){
        System.out.println("battery level : ");
        ECC.getCustomer().geteCar().getBatteryBalance();
        System.out.println("total millimetres : ");
        ECC.getCustomer().geteCar().getCarTravelDistance();
        System.out.println("last service date : ");
        ECC.getCustomer().geteCar().getLastService();
        subChatBox();
    }
    public static void subOption2(){

    }
    public static void subOption3(){

    }
    public static void subOption4(){

    }

}//class