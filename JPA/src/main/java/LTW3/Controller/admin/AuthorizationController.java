package LTW3.Controller.admin;


import java.io.IOException;

import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import LTW3.Entity.User;
import LTW3.Entity.Category;
import LTW3.Service.CategoryService;
import LTW3.Service.Impl.CategoryServiceImpl;


@WebServlet(urlPatterns = { "/admin/home", "/manager/home", "/user/home" })
public class AuthorizationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURL().toString();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        // Kiểm tra nếu người dùng đã đăng nhập
        if (user != null) {
            CategoryService categoryService = new CategoryServiceImpl();
            List<Category> listCategory;

            if (user.getRoleid() == 1 || user.getRoleid() == 3) {
                listCategory = categoryService.findAll();  // Admin và User thấy tất cả
            } else {
                listCategory = categoryService.findByUser(user);  // Manager chỉ thấy của mình
            }

            req.setAttribute("categorys", listCategory);

            // Kiểm tra URL và điều hướng đến trang JSP tương ứng
            if (url.contains("admin")) {
                if (user.getRoleid() == 1) {
                    req.getRequestDispatcher("/views/admin/home.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/login");
                }
            } else if (url.contains("manager")) {
                if (user.getRoleid() == 2) {
                    req.getRequestDispatcher("/views/manager/home.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/login");
                }
            } else if (url.contains("user")) {
                if (user.getRoleid() == 5) {  
                    req.getRequestDispatcher("/views/user/home.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/login");
                }
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}

