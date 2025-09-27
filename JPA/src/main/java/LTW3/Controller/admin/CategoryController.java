package LTW3.Controller.admin;

import java.io.File;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import LTW3.Entity.Category;
import LTW3.Entity.User;
import LTW3.Service.CategoryService;
import LTW3.Service.Impl.CategoryServiceImpl;
import LTW3.Util.Constant;
import LTW3.Util.UploadUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

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
	        response.sendRedirect(request.getContextPath() + "/admin-category"); // Sau khi thêm, chuyển hướng về trang danh sách
	        return;

	    } else if (url.contains("update")) {
	        update(request, response);
	        response.sendRedirect(request.getContextPath() + "/admin-category"); // Sau khi cập nhật, chuyển hướng về trang danh sách
	        return;

	    } else if (url.contains("delete")) {
	        delete(request, response);
	        response.sendRedirect(request.getContextPath() + "/admin-category"); // Sau khi xóa, chuyển hướng về trang danh sách
	        return;

	    } else if (url.contains("reset")) {
	        request.setAttribute("category", new Category());
	    }

	    findAll(request, response);
	    request.getRequestDispatcher("/views/cate/list.jsp").forward(request, response); // Không cần gọi forward() sau redirect
	}


	private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String categoryCode = request.getParameter("categoryCode");
		String categoryName = request.getParameter("categoryName");

		// Lấy user từ session
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// Tạo category
		Category category = new Category();
		category.setCategoryCode(categoryCode);
		category.setCategoryName(categoryName);
		category.setUser(user);

		// Kiểm tra file upload
		Part filePart = request.getPart("images");
		if (filePart != null && filePart.getSize() > 0) {
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString()
					.replaceAll("[^a-zA-Z0-9.\\-_]", "_");

			File uploadDir = new File(Constant.DIR);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			// Tạo đường dẫn để ghi file
			String filePath = Constant.DIR + File.separator + fileName;
			try {
				filePart.write(filePath);
				category.setImages(fileName); // Gán tên file nếu ghi thành công
			} catch (IOException e) {
				System.err.println("Không thể ghi file ảnh, nhưng vẫn tiếp tục thêm category.");
				e.printStackTrace();
				category.setImages(null); // Không có ảnh
			}
		} else {
			// Không có ảnh upload
			category.setImages(null);
		}

		// Lưu vào DB
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
				list = categoryService.findAll(); // Admin và User thấy tất cả
			} else {
				list = categoryService.findByUser(user); // Manager chỉ thấy của mình
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

	        // Lấy thông tin cũ từ DB
	        Category oldCategory = categoryService.findById(category.getCategoryId());
	        if (oldCategory == null) {
	            request.setAttribute("error", "Không tìm thấy danh mục để cập nhật!");
	            return;
	        }

	        HttpSession session = request.getSession();
	        User user = (User) session.getAttribute("user");

	        // Kiểm tra quyền cập nhật
	        if (user == null || oldCategory.getUser().getId() != user.getId()) {
	            request.setAttribute("error", "Bạn không có quyền cập nhật danh mục này!");
	            return;
	        }

	        // Giữ nguyên người tạo cũ
	        category.setUser(oldCategory.getUser());

	        // Xử lý ảnh (nếu có thay đổi)
	        if (request.getPart("images").getSize() == 0) {
	            category.setImages(oldCategory.getImages());
	        } else {
	            // Xóa ảnh cũ nếu có
	            if (oldCategory.getImages() != null) {
	                File file = new File(Constant.DIR + "\\category\\" + oldCategory.getImages());
	                if (file.exists()) {
	                    file.delete();
	                }
	            }

	            // Upload ảnh mới
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
