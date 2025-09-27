package LTW3.Dao.Impl;

import LTW3.Configs.JPA_Config;
import LTW3.Dao.UserDao;
import LTW3.Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UserDaoImpl implements UserDao {

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        EntityManager em = JPA_Config.getEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
        	 e.printStackTrace();
            return null; // Nếu không tìm thấy tài khoản hoặc mật khẩu sai
        }
    }
}
