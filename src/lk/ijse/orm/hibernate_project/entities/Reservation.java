package lk.ijse.orm.hibernate_project.entities;

import lk.ijse.orm.hibernate_project.dto.ReservationDTO;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @Column(name = "reservation_id", length = 50)
    private String ReservationId;

    @Column(name = "date_")
    private Date OrderDateTime;
    @ManyToOne
    @JoinColumn(name = "student_id",
            referencedColumnName = "student_id",
            insertable = true,
            updatable = true)
    private Student Student;
    @ManyToOne
    @JoinColumn(name = "room_type_id",
            referencedColumnName = "room_type_id",
            insertable = true,
            updatable = true)
    private Room Room;
    @Column(name = "status", length = 50)
    private String Status;
    @Column(name = "exp", nullable = false, length = 50)
    private Date LastDate;
    @Column(name = "student_name", nullable = false, length = 50)
    private String StudentName;


    public Reservation() {
    }

    public Reservation(String reservationId, Date orderDateTime, Student student, Room room, String status, Date lastDate, String studentName) {
        ReservationId = reservationId;
        OrderDateTime = orderDateTime;
        Student = student;
        Room = room;
        Status = status;
        LastDate = lastDate;
        StudentName = studentName;

    }

    public String getReservationId() {
        return ReservationId;
    }

    public void setReservationId(String reservationId) {
        ReservationId = reservationId;
    }

    public Date getOrderDateTime() {
        return OrderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
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

    public Date getLastDate() {
        return LastDate;
    }

    public void setLastDate(Date lastDate) {
        LastDate = lastDate;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
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
        reservationDTO.setLastDate(this.LastDate);
        reservationDTO.setStudent(this.Student);
        reservationDTO.setRoom(this.Room);
        reservationDTO.setStatus(this.Status);
        return reservationDTO;
    }

}