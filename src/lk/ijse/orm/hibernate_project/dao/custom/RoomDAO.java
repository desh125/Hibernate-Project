package lk.ijse.orm.hibernate_project.dao.custom;

import lk.ijse.orm.hibernate_project.dao.CrudDAO;
import lk.ijse.orm.hibernate_project.entities.Room;
import org.hibernate.Session;

public interface RoomDAO extends CrudDAO<Room, Integer> {
    void SetSession(Session session);
}