package hibernate_one_to_many_bi.application;

import hibernate_one_to_many_bi.entity.Department;
import hibernate_one_to_many_bi.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Department.class)
                .buildSessionFactory();

        Session session = null;
        try {
//            session = sessionFactory.getCurrentSession();
//
//            Department department = new Department("SALES"
//                    , 800, 1500);
//            Employee employee1 = new Employee("Nikita", "Zubov"
//                    , 800);
//            Employee employee2 = new Employee("Elena", "Ivanova"
//                    , 1500);
//            Employee employee3 = new Employee("Anton", "Sidorov"
//                    , 1200);
//
//            department.addEmployeeToDepartment(employee1);
//            department.addEmployeeToDepartment(employee2);
//            department.addEmployeeToDepartment(employee3);
//
//            session.beginTransaction();
//
//            session.persist(department);
//
//            session.getTransaction().commit();
//
//            System.out.println("Done!");
// 888888888888888888888888888888888888888
            session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            System.out.println("Get department");
            Department department = session.get(Department.class, 3);

            System.out.println("Show department");
            System.out.println(department);

            department.getEmployees().get(0);

            session.getTransaction().commit();

            System.out.println("Show employees of department");
            System.out.println(department.getEmployees());

            System.out.println("Done!");
// 888888888888888888888888888888888888888
//            session = sessionFactory.getCurrentSession();
//
//            session.beginTransaction();
//
//            Employee employee = session.get(Employee.class, 1);
//
//            System.out.println(employee);
//            System.out.println(employee.getDepartment());
//
//            session.getTransaction().commit();
//
//            System.out.println("Done!");
// 888888888888888888888888888888888888888
//            session = sessionFactory.getCurrentSession();
//
//            session.beginTransaction();
//
//            Employee employee = session.get(Employee.class, 1);
//            session.remove(employee);
//
//            session.getTransaction().commit();
//
//            System.out.println("Done!");
        }
        finally {
            session.close();
            sessionFactory.close();
        }
    }
}
