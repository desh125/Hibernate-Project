package lk.ijse.orm.hibernate_project.dto;

import lk.ijse.orm.hibernate_project.entities.Reservation;
import lk.ijse.orm.hibernate_project.entities.Room;
import lk.ijse.orm.hibernate_project.entities.Student;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDTO {

    private String ReservationId;
    private Timestamp OrderDateTime;
    private Student Student;
    private Room Room;
    private String Status;

    public Reservation ToEntity() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(this.ReservationId);
        reservation.setOrderDateTime(this.OrderDateTime);
        reservation.setStudent(this.Student);
        reservation.setRoom(this.Room);
        reservation.setStatus(this.Status);
        return reservation;
    }

}