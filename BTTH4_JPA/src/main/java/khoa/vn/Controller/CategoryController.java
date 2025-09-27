package khoa.vn.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import khoa.vn.Util.Constant;

import org.apache.commons.beanutils.BeanUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import khoa.vn.Entity.Category;
import khoa.vn.Entity.User;
import khoa.vn.Service.CategoryService;
import khoa.vn.Service.Impl.CategoryServiceImpl;
import khoa.vn.Util.UploadUtils;

@WebServlet(urlPatterns = { "/admin-category", "/admin-category/create", "/admin-category/update",
		"/admin-category/edit", "/admin-category/delete", "/admin-category/reset" })
@MultipartConfig
(
	fileSizeThreshold = 1024 * 1024, 
		    maxFileSize = 1024 * 1024 * 10, 
		    maxRequestSize = 1024 * 1024 * 50 
)

public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryService categoryService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURL().toString();
		Category category = null;

		if (url.contains("create")) {
			req.getRequestDispatcher("/views/cate/add.jsp").forward(req, resp);
			return;

		} else if (url.contains("delete")) {
			delete(req, resp);
			category = new Category();
			req.setAttribute("category", category);

		} else if (url.contains("edit")) {
			edit(req, resp); // <-- gọi method xử lý logic
			req.getRequestDispatcher("/views/cate/edit.jsp").forward(req, resp);
			return;

		} else if (url.contains("reset")) {
			category = new Category();
			req.setAttribute("category", category);
		}

		findAll(req, resp);
		req.setAttribute("tag", "cate");
		req.getRequestDispatcher("/views/cate/list.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String url = request.getRequestURL().toString();

	    if (url.contains("create")) {
	        insert(request, response);
	        response.sendRedirect(request.getContextPath() + "/admin-category");
	        return;

	    } else if (url.contains("update")) {
	        update(request, response);
	        response.sendRedirect(request.getContextPath() + "/admin-category");
	        return;

	    } else if (url.contains("delete")) {
	        delete(request, response);
	        response.sendRedirect(request.getContextPath() + "/admin-category");
	        return;

	    } else if (url.contains("reset")) {
	        request.setAttribute("category", new Category());
	    }

	    findAll(request, response);
	    request.getRequestDispatcher("/views/cate/list.jsp").forward(request, response); 
	}


	private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String categoryCode = request.getParameter("categoryCode");
		String categoryName = request.getParameter("categoryName");

		// Lấy user từ session
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Category category = new Category();
		category.setCategoryCode(categoryCode);
		category.setCategoryName(categoryName);
		category.setUser(user);

		Part filePart = request.getPart("images");
		if (filePart != null && filePart.getSize() > 0) {
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString()
					.replaceAll("[^a-zA-Z0-9.\\-_]", "_");

			File uploadDir = new File(Constant.DIR);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			String filePath = Constant.DIR + File.separator + fileName;
			try {
				filePart.write(filePath);
				category.setImages(fileName); 
			} catch (IOException e) {
				System.err.println("Không thể ghi file ảnh, nhưng vẫn tiếp tục thêm category.");
				e.printStackTrace();
				category.setImages(null);
			}
		} else {
			category.setImages(null);
		}

		categoryService.insert(category);

		// Chuyển về trang danh sách
		response.sendRedirect(request.getContextPath() + "/admin-category");
	}

	protected void findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			List<Category> list;

			if (user.getRoleid() == 1 || user.getRoleid() == 5) {
				list = categoryService.findAll(); 
			} else {
				list = categoryService.findByUser(user); 
			}

			request.setAttribute("categorys", list);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Lỗi hiển thị danh sách: " + e.getMessage());
		}
	}

	protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String categoryId = request.getParameter("categoryId");
			Category category = categoryService.findById(Integer.parseInt(categoryId));

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			if (category.getUser().getId() == user.getId()) {
				request.setAttribute("category", category);
			} else {
				request.setAttribute("error", "Bạn không có quyền chỉnh sửa mục này!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Lỗi edit: " + e.getMessage());
		}
	}

	protected void delete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        String categoryId = request.getParameter("categoryId");
	        Category category = categoryService.findById(Integer.parseInt(categoryId));

	        if (category == null) {
	            request.setAttribute("error", "Không tìm thấy danh mục cần xóa!");
	            return;
	        }

	        HttpSession session = request.getSession();
	        User user = (User) session.getAttribute("user");

	        if (user != null && category.getUser().getId() == user.getId()) {
	            categoryService.delete(category.getCategoryId());
	            request.setAttribute("message", "Đã xóa thành công");
	        } else {
	            request.setAttribute("error", "Bạn không có quyền xóa danh mục này!");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Lỗi delete: " + e.getMessage());
	    }
	}


	protected void update(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        request.setCharacterEncoding("UTF-8");
	        response.setCharacterEncoding("UTF-8");

	        Category category = new Category();
	        BeanUtils.populate(category, request.getParameterMap());

	        Category oldCategory = categoryService.findById(category.getCategoryId());
	        if (oldCategory == null) {
	            request.setAttribute("error", "Không tìm thấy danh mục để cập nhật!");
	            return;
	        }

	        HttpSession session = request.getSession();
	        User user = (User) session.getAttribute("user");

	        if (user == null || oldCategory.getUser().getId() != user.getId()) {
	            request.setAttribute("error", "Bạn không có quyền cập nhật danh mục này!");
	            return;
	        }

	        category.setUser(oldCategory.getUser());

	        if (request.getPart("images").getSize() == 0) {
	            category.setImages(oldCategory.getImages());
	        } else {
	            if (oldCategory.getImages() != null) {
	                File file = new File(Constant.DIR + "\\category\\" + oldCategory.getImages());
	                if (file.exists()) {
	                    file.delete();
	                }
	            }

	            String fileName = category.getCategoryCode() + System.currentTimeMillis();
	            category.setImages(UploadUtils.processUpload("images", request, Constant.DIR + "\\category", fileName));
	        }

	        // Cập nhật vào DB
	        categoryService.update(category);
	        request.setAttribute("category", category);
	        request.setAttribute("message", "Cập nhật thành công");

	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Lỗi update: " + e.getMessage());
	    }
	}

}
