package dao.impl;

import dao.EmployeeDao;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    private final Connection connection;

    public EmployeeDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Employee employee) {

        String sql = "INSERT INTO employee(first_name, last_name, gender, age, city_id) " +
                "VALUES ((?), (?), (?), (?), (?))";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employee.getFirst_name());
            statement.setString(2, employee.getLast_name());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.setLong(5, employee.getCiti_id());
//            statement.executeQuery();
            int x = statement.executeUpdate();

            if (x > 0) {
                System.out.println("Запись добавлена");
            }
            else {
                System.out.println("Запись не добавлена");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee readById(int id) {

        Employee employee = new Employee();

        String sql = "SELECT * FROM employee WHERE id = (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                employee.setId(resultSet.getInt("id"));
                employee.setFirst_name(resultSet.getString("first_name"));
                employee.setLast_name(resultSet.getString("last_name"));
                employee.setGender(resultSet.getString("gender"));
                employee.setAge(resultSet.getInt("age"));
                employee.setCiti_id(resultSet.getInt("city_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public List<Employee> readAll() {

        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                int city_id = resultSet.getInt("city_id");
                employees.add(new Employee(id, first_name, last_name, gender, age, city_id));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public void updateById(int id, Employee employee) {
        String sql = "UPDATE employee SET first_name = (?), last_name = (?), gender = (?), age = (?), city_id = (?) WHERE id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.getFirst_name());
            statement.setString(2, employee.getLast_name());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.setInt(5, employee.getCiti_id());
            statement.setInt(6, id);

            int x = statement.executeUpdate();

            if (x > 0) {
                System.out.println("Запись обновлена");
            }
            else {
                System.out.println("Запись не обновлена");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM employee WHERE id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int x = statement.executeUpdate();

            if (x > 0) {
                System.out.println("Запись уалена");
            }
            else {
                System.out.println("Запись не удалена");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
