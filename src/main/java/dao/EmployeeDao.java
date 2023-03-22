package dao;

import model.Employee;

import java.util.List;

public interface EmployeeDao {

    void create(Employee employee);
    Employee readById(int id);
    List<Employee> readAll();
    void updateById(int id, Employee employee);
    void deleteById(int id);

}
