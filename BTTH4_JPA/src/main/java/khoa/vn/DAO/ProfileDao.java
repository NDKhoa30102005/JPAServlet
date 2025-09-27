package khoa.vn.DAO;

import java.util.List;

import khoa.vn.Entity.Profile;
import khoa.vn.Entity.User;

public interface ProfileDao {
    void insert(Profile profile);
    void update(Profile profile);
    void delete(int id) throws Exception;
    Profile findById(int id);
    List<Profile> findAll();
    Profile findByUser(User user);
}
