package lk.ijse.orm.hibernate_project.dao.custom;

import lk.ijse.orm.hibernate_project.dao.CrudDAO;
import lk.ijse.orm.hibernate_project.entities.Reservation;
import org.hibernate.Session;

public interface ReservationDAO extends CrudDAO<Reservation, Integer> {
    void SetSession(Session session);
}