package lk.ijse.orm.hibernate_project.bo.custom.impl;

import lk.ijse.orm.hibernate_project.bo.custom.ReservationBo;
import lk.ijse.orm.hibernate_project.configuration.SessionFactoryConfiguration;
import lk.ijse.orm.hibernate_project.dao.DaoFactory;
import lk.ijse.orm.hibernate_project.dao.custom.ReservationDAO;
import lk.ijse.orm.hibernate_project.dao.custom.RoomDAO;
import lk.ijse.orm.hibernate_project.dto.ReservationDTO;
import lk.ijse.orm.hibernate_project.dto.RoomDTO;
import lk.ijse.orm.hibernate_project.dto.StudentDTO;
import lk.ijse.orm.hibernate_project.entities.Reservation;
import lk.ijse.orm.hibernate_project.entities.Room;
import lk.ijse.orm.hibernate_project.entities.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationBoImpl implements ReservationBo {

    ReservationDAO reservationDao = DaoFactory.getDaoFactory().getDao(DaoFactory.DaoType.RESERVATION);
    RoomDAO roomDao = DaoFactory.getDaoFactory().getDao(DaoFactory.DaoType.ROOM);

    @Override
    public String SaveReservationDetails(ReservationDTO reservationDTO, RoomDTO roomDTO) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            reservationDao.SetSession(session);
            roomDao.SetSession(session);
            String save = reservationDao.Save(reservationDTO.ToEntity());
            roomDao.Update(roomDTO.ToEntity());
            transaction.commit();
            session.close();
            return save;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return "-1";
        }
    }


    @Override
    public ReservationDTO getReservationDetails(String reservation_id) {
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

    public List<ReservationDTO> getAllReservations() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        try {
            reservationDao.SetSession(session);
            List<Reservation> reservations = reservationDao.getAll(); // Implement the 'getAll' method in your ReservationDaoImpl class
            session.close();

            // Convert Reservation entities to ReservationDTOs
            return reservations.stream()
                    .map(Reservation::ToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public boolean UpdateReservationDetails(ReservationDTO reservationDTO, RoomDTO roomDTO) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            reservationDao.SetSession(session);
            roomDao.SetSession(session);
            reservationDao.Update(reservationDTO.ToEntityUpdate());
            roomDao.Update(roomDTO.ToEntity());
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

    @Override
    public StudentDTO GetStudentName(String ID) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        try {
            reservationDao.SetSession(session);
            List<Student> student = reservationDao.GetStudentName(ID);
            Student studentEntity = student.get(0);
            session.close();
            return studentEntity.ToDto();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public RoomDTO GetKeyMoney(String ID) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        try {
            reservationDao.SetSession(session);
            List<Room> room = reservationDao.GetKeyMoney(ID);
            Room roomEntity = room.get(0);
            session.close();
            return roomEntity.ToDto();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}