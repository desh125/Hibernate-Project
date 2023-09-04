package lk.ijse.orm.hibernate_project.dao;

import lk.ijse.orm.hibernate_project.dao.custom.impl.ReservationDaoImpl;
import lk.ijse.orm.hibernate_project.dao.custom.impl.StudentDaoImpl;

public class DaoFactory {

    private static DaoFactory daoFactory;

    private DaoFactory() {
    }

    public static DaoFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DaoFactory() : daoFactory;
    }

    public <SuperDAO> SuperDAO getDao(DaoType daoType) {
        switch (daoType) {
            case STUDENT:
                return (SuperDAO) new StudentDaoImpl();
            case ROOM:
                return (SuperDAO) new RoomDaoImpl();
            case RESERVATION:
                return (SuperDAO) new ReservationDaoImpl();
            default:
                return null;
        }
    }

    public enum DaoType {
        STUDENT, ROOM, RESERVATION
    }

}