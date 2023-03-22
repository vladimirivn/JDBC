import dao.EmployeeDao;
import dao.impl.EmployeeDaoImpl;
import model.Employee;

import java.sql.*;

public class Application {

    public static final String USER = "postgres";
    public static final String PASSWORD = "admin";
    public static final String URL = "jdbc:postgresql://localhost:5432/skypro";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            task1(connection);
            task2(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void task1(Connection connection) throws SQLException {

        String sql = "SELECT first_name, last_name, gender, city_name " +
                "FROM employee " +
                "LEFT JOIN city USING(city_id) " +
                "WHERE id = (?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, 3);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String firstName = "Имя: " + resultSet.getString(1);
            String lastName = "Фамилия: " + resultSet.getString(2);
            String gender = "Пол: " + resultSet.getString(3);
            String city = "Город: ";
            if (resultSet.getString(4) == null) {
                city += "БОМЖ";
            } else {
                city += resultSet.getString(4);
            }
            System.out.println("-------------------------------- Задание 1 -----------------------------------------");
            System.out.println(firstName);
            System.out.println(lastName);
            System.out.println(gender);
            System.out.println(city);
        }

    }

    public static void task2(Connection connection) {

        EmployeeDao employeeDao = new EmployeeDaoImpl(connection);

        Employee employee = new Employee("Vladimir", "Ivanenko", "Male", 49, 1);
        Employee updateEmployee = new Employee("Ivan", "Popov", "Male", 49, 4);

        System.out.println("-------------------------------- Задание 2 -----------------------------------------");
        System.out.println("----------- добавляем ---------------------");
        employeeDao.create(employee);
        System.out.println("-----------читаем по id -------------------");
        System.out.println(employeeDao.readById(4));
        System.out.println("-----------обновляем по id -------------------");
        employeeDao.updateById(15,updateEmployee);
        System.out.println("------------ удаляем по id ------------------");
        employeeDao.deleteById(15);
        System.out.println("-------------считываем все -----------------");
        employeeDao.readAll().forEach(System.out::println);

    }

}
