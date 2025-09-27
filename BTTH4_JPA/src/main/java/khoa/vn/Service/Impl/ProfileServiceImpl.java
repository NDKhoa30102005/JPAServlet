package khoa.vn.Service.Impl;

import java.util.List;

import khoa.vn.DAO.ProfileDao;
import khoa.vn.DAO.Impl.ProfileDaoImpl;
import khoa.vn.Entity.Profile;
import khoa.vn.Entity.User;
import khoa.vn.Service.ProfileService;

public class ProfileServiceImpl implements ProfileService {
    private ProfileDao profileDao;

    public ProfileServiceImpl() {
        this.profileDao = new ProfileDaoImpl(); // Gọi DAO Impl
    }

    @Override
    public void insert(Profile profile) {
        profileDao.insert(profile);
    }

    @Override
    public void update(Profile profile) {
        profileDao.update(profile);
    }

    @Override
    public void delete(int profileId) throws Exception {
        profileDao.delete(profileId);
    }

    @Override
    public Profile findById(int profileId) {
        return profileDao.findById(profileId);
    }

    @Override
    public List<Profile> findAll() {
        return profileDao.findAll();
    }

}
