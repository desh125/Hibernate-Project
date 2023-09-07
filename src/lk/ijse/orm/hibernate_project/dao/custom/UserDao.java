package lk.ijse.orm.hibernate_project.dao.custom;

import lk.ijse.orm.hibernate_project.dao.CrudDAO;
import lk.ijse.orm.hibernate_project.entities.User;

public interface UserDao extends CrudDAO<User, String> {

    String getUser(String id);

    String getPassword(String id);

    boolean updateUser_Pw(String newUserName, String newPw);
}