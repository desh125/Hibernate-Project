package lk.ijse.orm.hibernate_project.dao.custom;

import lk.ijse.orm.hibernate_project.dao.CrudDAO;
import lk.ijse.orm.hibernate_project.entities.Student;
import org.hibernate.Session;

import java.util.List;

public interface StudentDAO extends CrudDAO<Student, String> {
    void SetSession(Session session);

    List<Student> getAlls();
}