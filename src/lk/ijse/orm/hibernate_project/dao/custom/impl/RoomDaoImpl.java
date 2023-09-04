package lk.ijse.orm.hibernate_project.dao.custom.impl;

import lk.ijse.orm.hibernate_project.dao.custom.RoomDAO;
import lk.ijse.orm.hibernate_project.entities.Room;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RoomDaoImpl implements RoomDAO {

    private Session session;

    public RoomDaoImpl() {
    }

    @Override
    public String Save(Room roomEntity) {
        return (String) session.save(roomEntity);
    }

    @Override
    public Room Get(String id) {
        return session.get(Room.class, id);
    }

    @Override
    public void Update(Room roomEntity) {
        session.update(roomEntity);
    }

    @Override
    public void Delete(Room roomEntity) {
        session.delete(roomEntity);
    }

    @Override
    public void SetSession(Session session) {
        this.session = session;
    }

    @Override
    public List<String> getRoomType() {
        String hql = "SELECT r.Type FROM Room r";
        Query query = session.createQuery(hql);
        List list = query.list();
        return list;
    }

    @Override
    public List<String> getRoomTypeID() {
        String hql = "SELECT r.RoomTypeId FROM Room r";
        Query query = session.createQuery(hql);
        List list = query.list();
        return list;
    }
}