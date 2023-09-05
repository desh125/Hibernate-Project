package lk.ijse.orm.hibernate_project.bo.custom;

import lk.ijse.orm.hibernate_project.bo.SuperBo;
import lk.ijse.orm.hibernate_project.dto.StudentDTO;

import java.util.List;

public interface StudentBo extends SuperBo {
    boolean SaveStudent(StudentDTO studentDto);

    StudentDTO getStudent(String student_id);

    List<StudentDTO> getAllStudents();

    boolean UpdateStudent(StudentDTO studentDTO);

    boolean DeleteStudent(StudentDTO studentDTO);
}