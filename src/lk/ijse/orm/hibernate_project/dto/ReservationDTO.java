package lk.ijse.orm.hibernate_project.dto;

import lk.ijse.orm.hibernate_project.entities.Reservation;
import lk.ijse.orm.hibernate_project.entities.Room;
import lk.ijse.orm.hibernate_project.entities.Student;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDTO {

    private String ReservationId;
    private Date LastDate;
    private Date OrderDateTime;
    private Student Student;
    private Room Room;
    private String Status;
    private String StudentName;

    public Reservation ToEntity() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(this.ReservationId);
        reservation.setLastDate(this.LastDate);
        reservation.setStudent(this.Student);
        reservation.setRoom(this.Room);
        reservation.setStatus(this.Status);
        reservation.setStudentName(this.StudentName);
        return reservation;
    }

    public Reservation ToEntityUpdate() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(this.ReservationId);
        reservation.setLastDate(this.LastDate);
        reservation.setOrderDateTime(this.OrderDateTime);
        reservation.setStudent(this.Student);
        reservation.setRoom(this.Room);
        reservation.setStatus(this.Status);
        reservation.setStudentName(this.StudentName);
        return reservation;
    }

}