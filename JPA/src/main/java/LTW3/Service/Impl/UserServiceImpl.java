package LTW3.Service.Impl;

import LTW3.Entity.User;
import LTW3.Service.UserService;
import LTW3.Dao.UserDao;
import LTW3.Dao.Impl.UserDaoImpl;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }
}
