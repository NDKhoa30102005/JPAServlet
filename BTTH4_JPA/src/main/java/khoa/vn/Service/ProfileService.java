package khoa.vn.Service;

import java.util.List;
import khoa.vn.Entity.Profile;
import khoa.vn.Entity.User;

public interface ProfileService {
    void insert(Profile profile);

    void update(Profile profile);

    void delete(int profileId) throws Exception;

    Profile findById(int profileId);

    List<Profile> findAll();

}
