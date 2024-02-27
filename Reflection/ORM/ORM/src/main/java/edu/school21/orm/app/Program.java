package edu.school21.orm.app;

import edu.school21.orm.models.User;
import edu.school21.orm.manager.OrmManager;

public class Program {
    public static void main(String[] args) {
        System.out.println("\nСоздаем таблицы для сущностей:");
        System.out.println("========================");

        OrmManager ormManager = new OrmManager();

        System.out.println("\nСоздаем пользователей:");
        System.out.println("========================");

        User user1 = new User(1L, "John", "Locke", 48);
        User user2 = new User(2L, "Alan", "Oconnor", 25);
        User user3 = new User(3L, "Abdullah", "Brown", 33);
        User user4 = new User(4L, "Hari", "Brock", 51);
        User user5 = new User(5L, "Darius", "Douglas", 19);

        ormManager.save(user1);
        ormManager.save(user2);
        ormManager.save(user3);
        ormManager.save(user4);
        ormManager.save(user5);

        System.out.println("\nОбновляем пользователей:");
        System.out.println("========================");

        ormManager.update(new User(1L, "firstName0", "lastName0", 555));
        ormManager.update(new User(5L, "firstName3", "lastName5", 111));

        System.out.println("\nНаходим пользователей:");
        System.out.println("========================");

        System.out.println(ormManager.findById(1L, User.class));
        System.out.println(ormManager.findById(2L, User.class));
        System.out.println(ormManager.findById(3L, User.class));
        System.out.println(ormManager.findById(4L, User.class));
        System.out.println(ormManager.findById(5L, User.class));
    }
}
