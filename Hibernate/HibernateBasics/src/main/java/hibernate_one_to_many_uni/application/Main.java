package hibernate_one_to_many_uni.application;

import hibernate_one_to_many_uni.entity.Department;
import hibernate_one_to_many_uni.entity.Employee;
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
//            Department department = new Department("HR"
//                    , 500, 1500);
//            Employee employee1 = new Employee("Nikita", "Zubov"
//                    , 1000);
//            Employee employee2 = new Employee("Tanya", "Ivanova"
//                    , 500);
//
//            department.addEmployeeToDepartment(employee1);
//            department.addEmployeeToDepartment(employee2);
//
//            session.beginTransaction();
//
//            session.persist(department);
//
//            session.getTransaction().commit();
//
//            System.out.println("Done!");
// 888888888888888888888888888888888888888
//            session = sessionFactory.getCurrentSession();
//
//            session.beginTransaction();
//
//            Department department = session.get(Department.class, 1);
//            System.out.println(department);
//            System.out.println(department.getEmployees());
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
//
//            System.out.println(employee);
//
//            session.getTransaction().commit();
//
//            System.out.println("Done!");
// 888888888888888888888888888888888888888
            session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            Department department1 = session.get(Department.class, 1);
            Department department2 = session.get(Department.class, 2);
            session.remove(department1);
            session.remove(department2);

            session.getTransaction().commit();

            System.out.println("Done!");
        }
        finally {
            session.close();
            sessionFactory.close();
        }
    }
}
