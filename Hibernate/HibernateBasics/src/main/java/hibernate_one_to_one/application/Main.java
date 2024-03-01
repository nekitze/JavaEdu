package hibernate_one_to_one.application;

import hibernate_one_to_one.entity.Detail;
import hibernate_one_to_one.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Detail.class)
                .buildSessionFactory();

        Session session = null;
        try {
//            Session session = sessionFactory.getCurrentSession();
//            Employee employee = new Employee("Nikita", "Zubov"
//                    , "IT", 500);
//            Detail detail = new Detail("Moscow"
//                    , "314566", "nikitazubov@outlook.com");
//            employee.setEmployeeDetail(detail);
//
//            session.beginTransaction();
//            session.persist(employee);
//            session.getTransaction().commit();

//            Session session = sessionFactory.getCurrentSession();
//            Employee employee = new Employee("Ivan", "Ivanov"
//                    , "SALES", 200);
//            Detail detail = new Detail("Moscow"
//                    , "5555", "IvanIvanov@outlook.com");
//            employee.setEmployeeDetail(detail);
//
//            session.beginTransaction();
//            session.persist(employee);
//            session.getTransaction().commit();

            session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            Employee employee = session.get(Employee.class, 2);
            session.remove(employee);

            session.getTransaction().commit();

        }
        finally {
            session.close();
            sessionFactory.close();
        }
    }
}
