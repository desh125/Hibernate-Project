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
    private String LastDate;
    private Timestamp OrderDateTime;
    private Student Student;
    private Room Room;
    private String Status;
    private String StudentName;
    private String KeyMoney;

    public Reservation ToEntity() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(this.ReservationId);
        reservation.setLastDate(this.LastDate);
        reservation.setStudent(this.Student);
        reservation.setRoom(this.Room);
        reservation.setStatus(this.Status);
        reservation.setStudentName(this.StudentName);
        reservation.setKeyMoney(this.KeyMoney);
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
        reservation.setKeyMoney(this.KeyMoney);
        return reservation;
    }

}