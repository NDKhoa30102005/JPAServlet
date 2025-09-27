package khoa.vn.DAO.Impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import khoa.vn.Configs.JPA_Config;
import khoa.vn.DAO.ProfileDao;
import khoa.vn.Entity.Profile;
import khoa.vn.Entity.User;

public class ProfileDaoImpl implements ProfileDao {

    @Override
    public void insert(Profile profile) {
        EntityManager em = JPA_Config.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            if (profile.getId() != 0) {
                em.merge(profile);
            } else {
                em.persist(profile);
            }
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Profile profile) {
        EntityManager em = JPA_Config.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(profile);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        EntityManager em = JPA_Config.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Profile profile = em.find(Profile.class, id);
            if (profile != null) {
                em.remove(profile);
            } else {
                throw new Exception("Không tìm thấy profile");
            }
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Profile findById(int id) {
        EntityManager em = JPA_Config.getEntityManager();
        try {
            return em.find(Profile.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Profile> findAll() {
        EntityManager em = JPA_Config.getEntityManager();
        try {
            TypedQuery<Profile> query = em.createQuery("SELECT p FROM Profile p", Profile.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Profile findByUser(User user) {
        EntityManager em = JPA_Config.getEntityManager();
        try {
            TypedQuery<Profile> query = em.createQuery(
                "SELECT p FROM Profile p WHERE p.user = :user", Profile.class);
            query.setParameter("user", user);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }
}
