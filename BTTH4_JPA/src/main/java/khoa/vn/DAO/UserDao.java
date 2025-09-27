package khoa.vn.DAO;

import khoa.vn.Entity.User;

public interface UserDao {
	User findByUsernameAndPassword(String username, String password);
	}

