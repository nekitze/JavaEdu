package hibernate_test.application;

import hibernate_test.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main3 {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

//            List<Employee> employeeList = session.createQuery("from Employee")
//                    .getResultList();

            List<Employee> employeeList = session.createQuery("from Employee " +
                    "where name = 'Nikita' and salary > 499")
                    .getResultList();

            for (Employee e : employeeList) {
                System.out.println(e);
            }

            session.getTransaction().commit();

            System.out.println("Done!");
        }
        finally {
            sessionFactory.close();
        }
    }
}
