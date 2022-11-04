import java.sql.*;

public class DBUtil {
    static final String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7542187";
    static final String USER = "sql7542187";
    static final String PASS = "FwRe9s8Cf1";
    static final String QUERY = "SELECT id, first, last, age FROM Employees";

    static final String QUERYINSERT = "INSERT INTO Employees VALUES (101, 18, 'Maria', 'TErzi');";


    public static void main(String[] args)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        // Open a connection
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", Age: " + rs.getInt("age"));
                System.out.print(", First: " + rs.getString("first"));
                System.out.println(", Last: " + rs.getString("last"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            //Assume a valid connection object conn

            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();

            String SQL = "INSERT INTO Employees  " +
                    "VALUES (106, 20, 'Rita', 'Tez')";
            stmt.executeUpdate(SQL);
            //Submit a malformed SQL statement that breaks
            String SQLINSERT = "INSERTED IN Employees  " +
                    "VALUES (107, 22, 'Sita', 'Singh')";
            stmt.executeUpdate(SQLINSERT);
            // If there is no error.
            conn.commit();
        }catch(SQLException se){
            // If there is any error.
            conn.rollback();
        }
    }//main()
}//DBUtil