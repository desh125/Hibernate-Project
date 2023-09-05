package lk.ijse.orm.hibernate_project.dao.custom.impl;

import lk.ijse.orm.hibernate_project.dao.custom.StudentDAO;
import lk.ijse.orm.hibernate_project.entities.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDaoImpl implements StudentDAO {

    private Session session;

    public StudentDaoImpl() {
    }

    @Override
    public String Save(Student studentEntity) {
        return (String) session.save(studentEntity);
    }

    @Override
    public Student Get(String id) {
        return session.get(Student.class, id);
    }

    public List<Student> getAlls() {
        Query<Student> query = session.createQuery("FROM Student", Student.class);
        return query.list();
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