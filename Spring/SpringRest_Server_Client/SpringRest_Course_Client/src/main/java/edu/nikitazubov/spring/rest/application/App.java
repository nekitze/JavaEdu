package edu.nikitazubov.spring.rest.application;

import edu.nikitazubov.spring.rest.configuration.MyConfig;
import edu.nikitazubov.spring.rest.entity.Employee;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication"
                , Communication.class);

        List<Employee> allEmployees = communication.getAllEmployees();
        System.out.println(allEmployees);

        Employee employee = communication.getEmployee(4L);
        System.out.println(employee);

        Employee newEmployee = new Employee("Sveta", "Sokolova"
                , "HR", 900);
        newEmployee.setId(8L);
        communication.saveEmployee(newEmployee);

//        communication.deleteEmployee(9L);
    }
}
