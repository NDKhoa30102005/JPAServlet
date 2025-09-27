package khoa.vn.Service;

import java.util.List;

import khoa.vn.Entity.Category;
import khoa.vn.Entity.User;

public interface CategoryService {

	int count();

	List<Category> finAll(int page, int pagesize);

	List<Category> findByCategoryname(String catname);

	List<Category> findAll();

	Category findById(int cateid);

	void delete(int cateid) throws Exception;

	void update(Category category);

	void insert(Category category);

	List<Category> findByUser(User user);

	
	
}
