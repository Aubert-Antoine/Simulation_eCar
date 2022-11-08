import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {

    private final int ID;
    private final String fullName;
    private ElectricCar eCar;
    private String carRegistrationNumber;


    public Customer(int pID, String pFullName, ElectricCar pECar, String pCarRegistrationNumber){
        this.ID = pID;
        this.fullName = pFullName;
        this.eCar = pECar;
        this.carRegistrationNumber = pCarRegistrationNumber;
    }
    public void upDateDatabase(Customer pCustomer) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
        Statement stmt = conn.createStatement();

        String pUpdate = String.format("UPDATE Customer SET full_name = %s , car_registration_number = %s , model_id = %s , car_color = %s , car_battery_level = %s , car_last_service = %s , total_millimetres = %s WHERE customer_id = %s"
                , this.fullName, this.carRegistrationNumber, this.eCar.getModel_ID(), this.eCar.getCarColor(), this.eCar.getBatteryBalance(), this.eCar.getLastService(), this.eCar.getCarTravelDistance(), pCustomer.ID);

        try {
            stmt.executeUpdate(pUpdate);
            System.out.println("Query : "+ pUpdate +"  ->  done..");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();
    }

    public ElectricCar geteCar() {
        return eCar;
    }
}
