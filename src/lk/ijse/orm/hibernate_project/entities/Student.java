package lk.ijse.orm.hibernate_project.entities;

import lk.ijse.orm.hibernate_project.dto.StudentDTO;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "student_id", length = 40)
    private String StudentId;
    @Column(name = "full_name", nullable = false, columnDefinition = "TEXT")
    private String FullName;
    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String Address;
    @Column(name = "contact_number", nullable = false, length = 50)
    private String ContactNumber;
    @Column(name = "dob", nullable = false, length = 50)
    private Date DateOfBirth;
    @Column(name = "gender", nullable = false, length = 50)
    private String Gender;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "Student")
    private List<Reservation> reservationEntities = new ArrayList<>();

    public Student() {
    }

    public Student(String studentId, String fullName, String address, String contactNumber, Date dateOfBirth, String gender, List<Reservation> reservationEntities) {
        StudentId = studentId;
        FullName = fullName;
        Address = address;
        ContactNumber = contactNumber;
        DateOfBirth = dateOfBirth;
        Gender = gender;
        this.reservationEntities = reservationEntities;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public List<Reservation> getReservationEntities() {
        return reservationEntities;
    }

    public void setReservationEntities(List<Reservation> reservationEntities) {
        this.reservationEntities = reservationEntities;
    }

    public StudentDTO ToDto() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(this.StudentId);
        studentDTO.setFullName(this.FullName);
        studentDTO.setAddress(this.Address);
        studentDTO.setContactNumber(this.ContactNumber);
        studentDTO.setDateOfBirth(this.DateOfBirth);
        studentDTO.setGender(this.Gender);
        return studentDTO;
    }
}