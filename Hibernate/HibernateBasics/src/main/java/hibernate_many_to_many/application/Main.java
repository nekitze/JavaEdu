package hibernate_many_to_many.application;

import hibernate_many_to_many.entity.Child;
import hibernate_many_to_many.entity.Section;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Child.class)
                .addAnnotatedClass(Section.class)
                .buildSessionFactory();

        Session session = null;
        try {
//            session = sessionFactory.getCurrentSession();
//
//            Section section1 = new Section("Football");
//            Child child1 = new Child("Nikita", 5);
//            Child child2 = new Child("Ivan", 4);
//            Child child3 = new Child("Masha", 6);
//
//            section1.addChild(child1);
//            section1.addChild(child2);
//            section1.addChild(child3);
//
//            session.beginTransaction();
//
//            session.persist(section1);
//
//            session.getTransaction().commit();
//            System.out.println("Done!");
// ***************************************************************
//            session = sessionFactory.getCurrentSession();
//
//            Section section1 = new Section("Volleyball");
//            Section section2 = new Section("Chess");
//            Section section3 = new Section("Math");
//            Child child1 = new Child("Dima", 5);
//
//            child1.addSection(section1);
//            child1.addSection(section2);
//            child1.addSection(section3);
//
//            session.beginTransaction();
//
//            session.persist(child1);
//
//            session.getTransaction().commit();
//            System.out.println("Done!");
// ***************************************************************
//            session = sessionFactory.getCurrentSession();
//
//            session.beginTransaction();
//
//            Section section = session.get(Section.class, 1);
//            System.out.println(section);
//            System.out.println(section.getChildren());
//
//            session.getTransaction().commit();
//            System.out.println("Done!");
// ***************************************************************
//            session = sessionFactory.getCurrentSession();
//
//            session.beginTransaction();
//
//            Child child = session.get(Child.class, 4);
//            System.out.println(child);
//            System.out.println(child.getSections());
//
//            session.getTransaction().commit();
//            System.out.println("Done!");
// ***************************************************************
            session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            Child child = session.get(Child.class, 4);
            session.remove(child);

            session.getTransaction().commit();
            System.out.println("Done!");

        }
        finally {
            session.close();
            sessionFactory.close();
        }
    }
}
