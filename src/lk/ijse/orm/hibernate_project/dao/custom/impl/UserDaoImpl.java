package lk.ijse.orm.hibernate_project.dao.custom.impl;

import lk.ijse.orm.hibernate_project.configuration.SessionFactoryConfiguration;
import lk.ijse.orm.hibernate_project.dao.custom.UserDao;
import lk.ijse.orm.hibernate_project.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoImpl implements UserDao {

    private Session session;

    @Override
    public String Save(User reservationEntity) {
        return (String) session.save(reservationEntity);
    }

    @Override
    public User Get(String reservation_id) {
        return session.get(User.class, reservation_id);
    }

    @Override
    public void Update(User reservationEntity) {
        session.update(reservationEntity);
    }

    @Override
    public void Delete(User reservationEntity) {
        session.delete(reservationEntity);
    }

    @Override
    public String getUser(String id) {
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            User user = session.get(User.class, id);
            transaction.commit();
            return user.getName();
        } catch (Exception ex) {
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public String getPassword(String id) {
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            User user = session.get(User.class, id);
            transaction.commit();
            return user.getPassword();
        } catch (Exception ex) {
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean updateUser_Pw(String newUserName, String newPw) {
        session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            User user = session.get(User.class, "1");
            user.setName(newUserName);
            user.setPassword(newPw);
            session.update(user);

            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    // Implement other methods from the CrudDAO<User, String> interface if needed
}
