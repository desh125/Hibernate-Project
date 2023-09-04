package lk.ijse.orm.hibernate_project.bo.custom.impl;

import lk.ijse.orm.hibernate_project.bo.custom.RoomBo;
import lk.ijse.orm.hibernate_project.configuration.SessionFactoryConfiguration;
import lk.ijse.orm.hibernate_project.dao.DaoFactory;
import lk.ijse.orm.hibernate_project.dao.custom.RoomDAO;
import lk.ijse.orm.hibernate_project.dto.RoomDTO;
import lk.ijse.orm.hibernate_project.entities.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RoomBoImpl implements RoomBo {

    RoomDAO roomDao = DaoFactory.getDaoFactory().getDao(DaoFactory.DaoType.ROOM);

    @Override
    public String SaveRoom(RoomDTO roomDTO) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            roomDao.SetSession(session);
            String save = roomDao.Save(roomDTO.ToEntity());
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
    public RoomDTO getRoom(String room_type_id) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        try {
            roomDao.SetSession(session);
            Room roomEntity = roomDao.Get(room_type_id);
            session.close();
            return roomEntity.ToDto();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean UpdateRoom(RoomDTO roomDTO) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            roomDao.SetSession(session);
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
    public boolean DeleteRoom(RoomDTO roomDTO) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            roomDao.SetSession(session);
            roomDao.Delete(roomDTO.ToEntity());
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
    public List<String> getAllRoomType() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        try {
            roomDao.SetSession(session);
            List<String> type = roomDao.getRoomType();
            session.close();
            return type;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<String> getAllRoomTypeID() {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        try {
            roomDao.SetSession(session);
            List<String> typeId = roomDao.getRoomTypeID();
            session.close();
            return typeId;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}