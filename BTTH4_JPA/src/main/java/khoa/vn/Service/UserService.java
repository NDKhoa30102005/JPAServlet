package khoa.vn.Service;

import khoa.vn.Entity.User;

public interface UserService {
    User findByUsernameAndPassword(String username, String password);
}


