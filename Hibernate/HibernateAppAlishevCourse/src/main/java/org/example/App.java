package org.example;




import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.postgresql.util.OSUtil;

import javax.management.PersistentMBean;
import javax.management.loading.PrivateClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        //передаем класс, который помечен @Entity
        //автоматически считывает информацию из фалйа с названием hibernate.properties
        Configuration configuration = new Configuration().addAnnotatedClass(Item.class)
                .addAnnotatedClass(Person.class);
        //создаем сессию для работы с хибер
        SessionFactory sessionFactory = configuration.buildSessionFactory();

    try(sessionFactory) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Person person = session.get(Person.class,1);
        System.out.println("сессия завершилась, был вызван метод close ");
        session.getTransaction().commit();

        //отрываем сессию еще раз
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        System.out.println("внутри второй транзакции");
        person = (Person) session.merge(person);
        Hibernate.initialize(person.getItems());
        session.getTransaction().commit();
        System.out.println("Вне второй сессии");
        System.out.println(person.getItems());

    }
    }
}
