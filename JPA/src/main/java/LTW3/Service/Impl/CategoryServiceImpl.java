package LTW3.Service.Impl;

import java.util.List;

import LTW3.Dao.CategoryDao;
import LTW3.Dao.Impl.CategoryDaoImpl;
import LTW3.Entity.Category;
import LTW3.Entity.User;
import LTW3.Service.CategoryService;

public class CategoryServiceImpl  implements CategoryService {

	CategoryDao categoryDao = new CategoryDaoImpl();
	@Override
	public void insert(Category category) {
		categoryDao.insert(category);
		
	}
	@Override
	public void update(Category category) {
		categoryDao.insert(category);
		
	}
	@Override
	public void delete(int cateid) throws Exception {
		categoryDao.delete(cateid);
		
	}
	
	@Override
	public Category findById (int cateid) {
		return categoryDao.findById(cateid);
	}
	
	@Override
	public List<Category> findAll(){
		return categoryDao.findAll();
	}
	
	@Override
	public List<Category> findByCategoryname(String catname){
		return categoryDao.findByCategoryname(catname);
	}
	@Override
	public List<Category> finAll(int page,int pagesize){
		return categoryDao.findAll(page, pagesize);
	}
	@Override
	public int count() {
		return categoryDao.count();
	}
	@Override
	public List<Category> findByUser(User user) {
	    return categoryDao.findByUser(user);
	}

	
	

}
