package lk.ijse.orm.hibernate_project.configuration;

import lk.ijse.orm.hibernate_project.entities.Reservation;
import lk.ijse.orm.hibernate_project.entities.Room;
import lk.ijse.orm.hibernate_project.entities.Student;
import lk.ijse.orm.hibernate_project.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class SessionFactoryConfiguration {
    private static SessionFactoryConfiguration sessionFactoryConfig;
    private final SessionFactory sessionFactory;

    private SessionFactoryConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Room.class);
        configuration.addAnnotatedClass(Reservation.class);
        configuration.addAnnotatedClass(User.class);

        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/hibernate.properties"));
        } catch (IOException e) {
            System.out.println("File not found: hibernate.properties");
        }
        configuration.setProperties(properties);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactoryConfiguration getInstance() {
        return (sessionFactoryConfig == null) ?
                sessionFactoryConfig = new SessionFactoryConfiguration() :
                sessionFactoryConfig;
    }

    public final Session getSession() {
        return sessionFactory.openSession();
    }
}
