package lk.ijse.orm.hibernate_project.bo.custom.impl;

import lk.ijse.orm.hibernate_project.bo.custom.UserBo;
import lk.ijse.orm.hibernate_project.dao.DaoFactory;
import lk.ijse.orm.hibernate_project.dao.custom.UserDao;

public class UserBoImpl implements UserBo {

    UserDao userDao = DaoFactory.getDaoFactory().getDao(DaoFactory.DaoType.USER);

    @Override
    public String getUser(String id) {
        return userDao.getUser(id);
    }

    @Override
    public String getPassword(String id) {
        return userDao.getPassword(id);
    }

    @Override
    public boolean updateUser_Pw(String newUserName, String newPw) {
        return userDao.updateUser_Pw(newUserName, newPw);
    }
}
