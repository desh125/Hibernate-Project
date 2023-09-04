package lk.ijse.orm.hibernate_project.bo.custom.impl;

import lk.ijse.orm.hibernate_project.bo.custom.ReservationBo;
import lk.ijse.orm.hibernate_project.configuration.SessionFactoryConfiguration;
import lk.ijse.orm.hibernate_project.dao.DaoFactory;
import lk.ijse.orm.hibernate_project.dao.custom.ReservationDAO;
import lk.ijse.orm.hibernate_project.dto.ReservationDTO;
import lk.ijse.orm.hibernate_project.entities.Reservation;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ReservationBoImpl implements ReservationBo {

    ReservationDAO reservationDao = DaoFactory.getDaoFactory().getDao(DaoFactory.DaoType.RESERVATION);

    @Override
    public int SaveReservationDetails(ReservationDTO reservationDTO) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            reservationDao.SetSession(session);
            int save = reservationDao.Save(reservationDTO.ToEntity());
            transaction.commit();
            session.close();
            return save;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return 0;
        }
    }

    @Override
    public ReservationDTO getReservationDetails(int reservation_id) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        try {
            reservationDao.SetSession(session);
            Reservation reservation = reservationDao.Get(reservation_id);
            session.close();
            return reservation.ToDto();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean UpdateReservationDetails(ReservationDTO reservationDTO) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            reservationDao.SetSession(session);
            reservationDao.Update(reservationDTO.ToEntity());
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return false;
        }
    }

    @Override
    public boolean DeleteReservationDetails(ReservationDTO reservationDTO) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            reservationDao.SetSession(session);
            reservationDao.Delete(reservationDTO.ToEntity());
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return false;
        }
    }
}