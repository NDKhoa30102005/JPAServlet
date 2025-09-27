package LTW3.Dao;

import java.util.List;

import LTW3.Entity.Category;
import LTW3.Entity.User;

public interface CategoryDao {

	int count();

	List<Category> findByCategoryname(String catname);

	List<Category> findAll(int page, int pagesize);

	List<Category> findAll();

	Category findById(int categoryId);

	void delete(int cateid) throws Exception;

	void update(Category category);

	void insert(Category category);

	List<Category> findByUser(User user);

}
