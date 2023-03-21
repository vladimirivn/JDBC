import java.sql.*;

public class Application {
    public static void main(String[] args) {

        String sql = "SELECT * FROM employee WHERE id = (?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){;
             statement.setInt(1, 5);
             ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2));
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    private static Connection getConnection() {
        final String user = "postgres";
        final String password = "admin";
        final String url = "jdbc:postgresql://localhost:5432/skypro";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
