package lk.ijse.orm.hibernate_project.bo.custom;

import lk.ijse.orm.hibernate_project.bo.SuperBo;
import lk.ijse.orm.hibernate_project.dto.ReservationDTO;
import lk.ijse.orm.hibernate_project.dto.RoomDTO;
import lk.ijse.orm.hibernate_project.dto.StudentDTO;

public interface ReservationBo extends SuperBo {
    String SaveReservationDetails(ReservationDTO reservationDTO);

    ReservationDTO getReservationDetails(String reservation_id);

    boolean UpdateReservationDetails(ReservationDTO reservationDTO);

    boolean DeleteReservationDetails(ReservationDTO reservationDTO);

    StudentDTO GetStudentName(String ID);

    RoomDTO GetKeyMoney(String ID);
}