package lk.ijse.orm.hibernate_project.dao.custom.impl;

import lk.ijse.orm.hibernate_project.dao.custom.ReservationDAO;
import lk.ijse.orm.hibernate_project.entities.Reservation;
import lk.ijse.orm.hibernate_project.entities.Room;
import lk.ijse.orm.hibernate_project.entities.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ReservationDaoImpl implements ReservationDAO {
    private Session session;

    public ReservationDaoImpl() {
    }

    @Override
    public String Save(Reservation reservationEntity) {
        return (String) session.save(reservationEntity);
    }

    @Override
    public Reservation Get(String reservation_id) {
        return session.get(Reservation.class, reservation_id);
    }

    @Override
    public void Update(Reservation reservationEntity) {
        session.update(reservationEntity);
    }

    @Override
    public void Delete(Reservation reservationEntity) {
        session.delete(reservationEntity);
    }

    @Override
    public void SetSession(Session session) {
        this.session = session;
    }

    @Override
    public List<Student> GetStudentName(String ID) {
        String hql = "SELECT s FROM Student s WHERE s.StudentId = :studentId";
        Query query = session.createQuery(hql);
        query.setParameter("studentId", ID);
        List<Student> list = query.list();
        return list;
    }

    @Override
    public List<Room> GetKeyMoney(String ID) {
        String hql = "SELECT s FROM Room s WHERE s.RoomTypeId = :roomtypeid";
        Query query = session.createQuery(hql);
        query.setParameter("roomtypeid", ID);
        List<Room> list = query.list();
        return list;
    }
}