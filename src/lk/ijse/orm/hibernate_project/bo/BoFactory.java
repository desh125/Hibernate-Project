package lk.ijse.orm.hibernate_project.bo;

import lk.ijse.orm.hibernate_project.bo.custom.impl.ReservationBoImpl;
import lk.ijse.orm.hibernate_project.bo.custom.impl.RoomBoImpl;
import lk.ijse.orm.hibernate_project.bo.custom.impl.StudentBoImpl;
import lk.ijse.orm.hibernate_project.bo.custom.impl.UserBoImpl;

public class BoFactory {

    private static BoFactory boFactory;
    private static StudentBoImpl studentBoImpl;
    private static ReservationBoImpl reservationBoImpl;
    private static RoomBoImpl roomBoImpl;
    private static UserBoImpl userBoImpl;

    private BoFactory() {
    }

    public static BoFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BoFactory() : boFactory;
    }

    public <SuperBo> SuperBo getBo(BoType boType) {
        switch (boType) {
            case STUDENT:
                return (SuperBo) ((studentBoImpl == null) ? studentBoImpl = new StudentBoImpl() : studentBoImpl);
            case ROOM:
                return (SuperBo) ((roomBoImpl == null) ? roomBoImpl = new RoomBoImpl() : roomBoImpl);
            case RESERVATION:
                return (SuperBo) ((reservationBoImpl == null) ? reservationBoImpl = new ReservationBoImpl() : reservationBoImpl);
            case USER:
                return (SuperBo) ((userBoImpl == null) ? userBoImpl = new UserBoImpl() : userBoImpl);
            default:
                return null;
        }
    }

    public enum BoType {
        STUDENT, ROOM, RESERVATION, USER
    }

}