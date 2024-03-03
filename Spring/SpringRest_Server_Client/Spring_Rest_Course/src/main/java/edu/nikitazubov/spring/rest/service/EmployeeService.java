package edu.nikitazubov.spring.rest.service;

import edu.nikitazubov.spring.rest.entity.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> getAllEmployees();
    public Employee saveEmployee(Employee employee);
    public Employee getEmployeeById(Long id);
    public void deleteEmployee(Long id);
}
