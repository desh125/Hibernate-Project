package lk.ijse.orm.hibernate_project.dto;

import lk.ijse.orm.hibernate_project.entities.Reservation;
import lk.ijse.orm.hibernate_project.entities.Student;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentDTO {

    private String StudentId;
    private String FullName;
    private String Address;
    private String ContactNumber;
    private String DateOfBirth;
    private String Gender;
    private List<Reservation> reservationEntities = new ArrayList<>();

    public Student ToEntity() {
        Student student = new Student();
        student.setStudentId(this.StudentId);
        student.setFullName(this.FullName);
        student.setAddress(this.Address);
        student.setContactNumber(this.ContactNumber);
        student.setDateOfBirth(this.DateOfBirth);
        student.setGender(this.Gender);
        student.setReservationEntities(this.reservationEntities);
        return student;
    }

}