package lk.ijse.orm.hibernate_project.dao;

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
                return (SuperDAO) new ();
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