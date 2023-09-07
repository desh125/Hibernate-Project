package lk.ijse.orm.hibernate_project.dao.custom;

import lk.ijse.orm.hibernate_project.dao.CrudDAO;
import lk.ijse.orm.hibernate_project.entities.Reservation;
import lk.ijse.orm.hibernate_project.entities.Room;
import lk.ijse.orm.hibernate_project.entities.Student;
import org.hibernate.Session;

import java.util.List;

public interface ReservationDAO extends CrudDAO<Reservation, String> {
    void SetSession(Session session);

    List<Student> GetStudentName(String ID);

    List<Room> GetKeyMoney(String ID);

    List<Reservation> getAll();

    List<Reservation> getUnpaidReservationsByStudentId(String studentId) throws Exception;

    List<Reservation> getUnpaidReservations();


}