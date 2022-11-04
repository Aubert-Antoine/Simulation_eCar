import java.sql.SQLException;

public interface DatabaseInterface {
    void connectToDatabase() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException;
}
