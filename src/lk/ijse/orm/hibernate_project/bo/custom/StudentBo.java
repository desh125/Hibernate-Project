package lk.ijse.orm.hibernate_project.bo.custom;

import lk.ijse.orm.hibernate_project.bo.SuperBo;
import lk.ijse.orm.hibernate_project.dto.StudentDTO;

public interface StudentBo extends SuperBo {
    String SaveStudent(StudentDTO studentDto);

    StudentDTO getStudent(String student_id);

    boolean UpdateStudent(StudentDTO studentDTO);

    boolean DeleteStudent(StudentDTO studentDTO);
}