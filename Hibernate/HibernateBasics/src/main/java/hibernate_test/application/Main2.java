package hibernate_test.application;

import hibernate_test.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main2 {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();

        try {
            Session session = sessionFactory.getCurrentSession();
            Employee employee = new Employee("Oleg", "Sidorov", "HR", 300);

            session.beginTransaction();
            session.persist(employee);

            Long myId = employee.getId();
            Employee employeeFromDB = session.get(Employee.class, myId);

            session.getTransaction().commit();

            System.out.println(employeeFromDB);

            System.out.println("Done!");
        }
        finally {
            sessionFactory.close();
        }
    }
}
