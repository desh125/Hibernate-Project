package lk.ijse.orm.hibernate_project.configuration;

import lk.ijse.orm.hibernate_project.entities.Reservation;
import lk.ijse.orm.hibernate_project.entities.Room;
import lk.ijse.orm.hibernate_project.entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class SessionFactoryConfiguration {

    private static SessionFactoryConfiguration factoryConfig;
    private final SessionFactory sessionFactory;

    private SessionFactoryConfiguration() {
        Properties properties = loadHibernateProperties();

        sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(Reservation.class)
                .addProperties(properties)
                .buildSessionFactory();
    }

    public static SessionFactoryConfiguration getInstance() {
        return (factoryConfig == null) ? factoryConfig = new SessionFactoryConfiguration() : factoryConfig;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    private Properties loadHibernateProperties() {
        Properties properties = new Properties();

        try {
            properties.load(SessionFactoryConfiguration.class.getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            // Handle the exception (e.g., log or throw a custom exception)
            e.printStackTrace();
        }

        return properties;
    }
}

