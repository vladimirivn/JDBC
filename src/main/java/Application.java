import java.sql.*;

public class Application {
    public static void main(String[] args) {

        String sql = "SELECT first_name, last_name, gender, city_name " +
                     "FROM employee " +
                     "LEFT JOIN city USING(city_id) " +
                     "WHERE id = (?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){;
             statement.setInt(1, 4);
             ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String firstName = "First name: " + resultSet.getString(1);
                String lastName = "Last name: " + resultSet.getString(2);
                String gender = "Gender: " + resultSet.getString(3);
                String city = "City: " + resultSet.getString(4);

                System.out.println(firstName);
                System.out.println(lastName);
                System.out.println(gender);
                System.out.println(city);
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
