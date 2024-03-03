package edu.nikitazubov.spring.mvc_hibernate_aop.service;

import edu.nikitazubov.spring.mvc_hibernate_aop.dao.EmployeeDAO;
import edu.nikitazubov.spring.mvc_hibernate_aop.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        employeeDAO.saveEmployee(employee);
    }

    @Override
    @Transactional
    public Employee getEmployeeById(Long id) {
        return employeeDAO.getEmployeeById(id);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        employeeDAO.deleteEmployee(id);
    }
}
