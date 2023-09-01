package lk.ijse.orm.hibernate_project.dao.custom;

import lk.ijse.orm.hibernate_project.dao.CrudDAO;
import lk.ijse.orm.hibernate_project.entities.Student;
import org.hibernate.Session;

public interface StudentDAO extends CrudDAO<Student, Integer> {
    void SetSession(Session session);
}