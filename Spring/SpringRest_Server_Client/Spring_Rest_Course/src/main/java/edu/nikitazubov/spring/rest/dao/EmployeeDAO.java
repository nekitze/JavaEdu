package edu.nikitazubov.spring.rest.dao;

import edu.nikitazubov.spring.rest.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    public List<Employee> getAllEmployees();
    public Employee saveEmployee(Employee employee);
    public Employee getEmployeeById(Long id);
    public void deleteEmployee(Long id);
}
