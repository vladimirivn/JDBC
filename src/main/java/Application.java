import java.sql.*;

public class Application {
    public static void main(String[] args) {

        String sql = "SELECT first_name, last_name, gender, city_name " +
                     "FROM employee " +
                     "LEFT JOIN city USING(city_id) " +
                     "WHERE id = (?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){;
             statement.setInt(1, 5);
             ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String firstName = "Имя: " + resultSet.getString(1);
                String lastName = "Фамилия: " + resultSet.getString(2);
                String gender = "Пол: " + resultSet.getString(3);
                String city ="Город: ";
                if (resultSet.getString(4) == null) {
                    city += "БОМЖ";
                } else {
                    city += resultSet.getString(4);
                }

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
