package lk.ijse.orm.hibernate_project.entities;

import lk.ijse.orm.hibernate_project.dto.ReservationDTO;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @Column(name = "reservation_id", length = 50)
    private String ReservationId;
    @CreationTimestamp
    @Column(name = "date_")
    private Timestamp OrderDateTime;
    @ManyToOne
    @JoinColumn(name = "student_id",
            referencedColumnName = "student_id",
            insertable = false,
            updatable = false)
    private Student Student;
    @ManyToOne
    @JoinColumn(name = "room_type_id",
            referencedColumnName = "room_type_id",
            insertable = false,
            updatable = false)
    private Room Room;
    @Column(name = "status", length = 50)
    private String Status;

    public Reservation() {
    }

    public Reservation(String reservationId, Timestamp orderDateTime, Student student, Room room, String status) {
        ReservationId = reservationId;
        OrderDateTime = orderDateTime;
        Student = student;
        Room = room;
        Status = status;
    }

    public String getReservationId() {
        return ReservationId;
    }

    public void setReservationId(String reservationId) {
        ReservationId = reservationId;
    }

    public Timestamp getOrderDateTime() {
        return OrderDateTime;
    }

    public void setOrderDateTime(Timestamp orderDateTime) {
        OrderDateTime = orderDateTime;
    }

    public Student getStudent() {
        return Student;
    }

    public void setStudent(Student student) {
        Student = student;
    }

    public Room getRoom() {
        return Room;
    }

    public void setRoom(Room room) {
        Room = room;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "ReservationEntity{" +
                "ReservationId='" + ReservationId + '\'' +
                ", OrderDateTime=" + OrderDateTime +
                ", Student=" + Student +
                ", Room=" + Room +
                ", Status='" + Status + '\'' +
                '}';
    }

    public ReservationDTO ToDto() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationId(this.ReservationId);
        reservationDTO.setOrderDateTime(this.OrderDateTime);
        reservationDTO.setStudent(this.Student);
        reservationDTO.setRoom(this.Room);
        reservationDTO.setStatus(this.Status);
        return reservationDTO;
    }

}