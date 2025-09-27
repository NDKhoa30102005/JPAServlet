package LTW3.Service;

import LTW3.Entity.User;

public interface UserService {
    User findByUsernameAndPassword(String username, String password);
}

