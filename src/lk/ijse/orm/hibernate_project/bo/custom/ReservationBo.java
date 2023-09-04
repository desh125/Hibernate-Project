package lk.ijse.orm.hibernate_project.bo.custom;

import lk.ijse.orm.hibernate_project.bo.SuperBo;
import lk.ijse.orm.hibernate_project.dto.ReservationDTO;

public interface ReservationBo extends SuperBo {
    int SaveReservationDetails(ReservationDTO reservationDTO);

    ReservationDTO getReservationDetails(int reservation_id);

    boolean UpdateReservationDetails(ReservationDTO reservationDTO);

    boolean DeleteReservationDetails(ReservationDTO reservationDTO);
}