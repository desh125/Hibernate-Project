package lk.ijse.orm.hibernate_project.configuration;

import lk.ijse.orm.hibernate_project.entities.Reservation;
import lk.ijse.orm.hibernate_project.entities.Room;
import lk.ijse.orm.hibernate_project.entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryConfiguration {
    private static SessionFactoryConfiguration factoryConfig;
    private final SessionFactory sessionFactory;

    private SessionFactoryConfiguration() {
        sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(Reservation.class)
                .buildSessionFactory();

    }

    public static SessionFactoryConfiguration getInstance() {
        return (factoryConfig == null) ? factoryConfig = new SessionFactoryConfiguration() : factoryConfig;
    }

    public Session getSession() {

     /*   Properties properties = new Properties();

        try {
            properties.load(SessionFactoryConfiguration.class.getResourceAsStream("hibernate.properties"));
        } catch (Exception e) {}
*/
        return sessionFactory.openSession();
    }

}
