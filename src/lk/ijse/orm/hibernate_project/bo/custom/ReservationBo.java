package lk.ijse.orm.hibernate_project.bo.custom;

import lk.ijse.orm.hibernate_project.bo.SuperBo;
import lk.ijse.orm.hibernate_project.dto.ReservationDTO;
import lk.ijse.orm.hibernate_project.dto.RoomDTO;
import lk.ijse.orm.hibernate_project.dto.StudentDTO;

import java.util.List;

public interface ReservationBo extends SuperBo {
    String SaveReservationDetails(ReservationDTO reservationDTO, RoomDTO roomDTO);

    ReservationDTO getReservationDetails(String reservation_id);

    boolean UpdateReservationDetails(ReservationDTO reservationDTO, RoomDTO roomDTO);

    boolean DeleteReservationDetails(ReservationDTO reservationDTO, RoomDTO roomDTO);

    StudentDTO GetStudentName(String ID);

    RoomDTO GetKeyMoney(String ID);

    List<ReservationDTO> getReservationsByStatus(String status);

    List<ReservationDTO> getAllReservations();

    List<ReservationDTO> getUnpaidReservationsByStudentId(String studentId) throws Exception;

    List<ReservationDTO> getUnpaidStudentsWithJoinQuery();


}