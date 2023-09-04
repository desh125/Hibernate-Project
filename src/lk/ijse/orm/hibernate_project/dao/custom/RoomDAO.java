package lk.ijse.orm.hibernate_project.dao.custom;

import lk.ijse.orm.hibernate_project.dao.CrudDAO;
import lk.ijse.orm.hibernate_project.entities.Room;
import org.hibernate.Session;

import java.util.List;

public interface RoomDAO extends CrudDAO<Room, Integer> {
    void SetSession(Session session);

    List<String> getRoomType();

    List<String> getRoomTypeID();
}