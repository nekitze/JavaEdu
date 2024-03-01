package hibernate_test.application;

import hibernate_test.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();

        try {
            Session session = sessionFactory.getCurrentSession();
            Employee employee = new Employee("Adnrey", "Zubov", "SBERTECH", 700);

            session.beginTransaction();
            session.persist(employee);
            session.getTransaction().commit();
        }
        finally {
            sessionFactory.close();
        }
    }
}
