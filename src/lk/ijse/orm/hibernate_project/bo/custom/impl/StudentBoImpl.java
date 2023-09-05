package lk.ijse.orm.hibernate_project.bo.custom.impl;

import lk.ijse.orm.hibernate_project.bo.custom.StudentBo;
import lk.ijse.orm.hibernate_project.configuration.SessionFactoryConfiguration;
import lk.ijse.orm.hibernate_project.dao.DaoFactory;
import lk.ijse.orm.hibernate_project.dao.custom.StudentDAO;
import lk.ijse.orm.hibernate_project.dto.StudentDTO;
import lk.ijse.orm.hibernate_project.entities.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class StudentBoImpl implements StudentBo {


    StudentDAO studentDao = DaoFactory.getDaoFactory().getDao(DaoFactory.DaoType.STUDENT);

    @Override
    public boolean SaveStudent(StudentDTO studentDTO) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            studentDao.SetSession(session);
            String savedId = studentDao.Save(studentDTO.ToEntity());
            transaction.commit();
            session.close();
            return savedId != null; // Return true if a student is saved, false otherwise
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return false; // Return false in case of an error
        }
    }

    @Override
    public StudentDTO getStudent(String student_id) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        try {
            studentDao.SetSession(session);
            Student student = studentDao.Get(student_id);
            session.close();
            return student.ToDto();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean UpdateStudent(StudentDTO studentDTO) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            studentDao.SetSession(session);
            studentDao.Update(studentDTO.ToEntity());
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return false;
        }
    }

    @Override
    public boolean DeleteStudent(StudentDTO studentDTO) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            studentDao.SetSession(session);
            studentDao.Delete(studentDTO.ToEntity());
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return false;
        }
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<StudentDTO> studentDTOs = new ArrayList<>();
        Session session = SessionFactoryConfiguration.getInstance().getSession();

        try {
            studentDao.SetSession(session);
            List<Student> students = studentDao.getAlls(); // Replace with your actual getAll method in StudentDao

            for (Student student : students) {
                studentDTOs.add(student.ToDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return studentDTOs;
    }

}
