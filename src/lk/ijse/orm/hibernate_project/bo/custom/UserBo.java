package lk.ijse.orm.hibernate_project.bo.custom;

import lk.ijse.orm.hibernate_project.bo.SuperBo;


public interface UserBo extends SuperBo {
    String getUser(String id);

    String getPassword(String id);

    boolean updateUser_Pw(String newUserName, String newPw);
}
