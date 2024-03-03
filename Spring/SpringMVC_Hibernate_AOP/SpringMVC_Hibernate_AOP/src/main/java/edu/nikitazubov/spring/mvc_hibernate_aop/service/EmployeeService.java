package edu.nikitazubov.spring.mvc_hibernate_aop.service;

import edu.nikitazubov.spring.mvc_hibernate_aop.entity.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> getAllEmployees();
    public void saveEmployee(Employee employee);
    public Employee getEmployeeById(Long id);
    public void deleteEmployee(Long id);
}
