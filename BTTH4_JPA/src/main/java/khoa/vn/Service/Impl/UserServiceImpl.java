package khoa.vn.Service.Impl;

import khoa.vn.DAO.UserDao;
import khoa.vn.DAO.Impl.UserDaoImpl;
import khoa.vn.Entity.User;
import khoa.vn.Service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }
   
}
