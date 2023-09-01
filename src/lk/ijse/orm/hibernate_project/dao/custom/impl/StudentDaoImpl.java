package lk.ijse.orm.hibernate_project.dao.custom.impl;

import lk.ijse.orm.hibernate_project.dao.custom.StudentDAO;
import lk.ijse.orm.hibernate_project.entities.Student;
import org.hibernate.Session;

public class StudentDaoImpl implements StudentDAO {

    private Session session;

    public StudentDaoImpl() {
    }

    @Override
    public Integer Save(Student studentEntity) {
        return (int) session.save(studentEntity);
    }

    @Override
    public Student Get(Integer id) {
        return session.get(Student.class, id);
    }

    @Override
    public void Update(Student studentEntity) {
        session.update(studentEntity);
    }

    @Override
    public void Delete(Student studentEntity) {
        session.delete(studentEntity);
    }

    @Override
    public void SetSession(Session session) {
        this.session = session;
    }
}