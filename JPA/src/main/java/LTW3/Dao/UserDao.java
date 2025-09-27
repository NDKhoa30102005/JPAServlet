package LTW3.Dao;

import LTW3.Entity.User;

public interface UserDao {
	User findByUsernameAndPassword(String username, String password);
	}

