package LTW3.Controller.admin;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import LTW3.Entity.User;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/home", "/manager/home", "/user/home"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Bạn có thể để trống nếu không cần cấu hình thêm
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String url = req.getRequestURI();
        if (url.endsWith("/login")) {
            chain.doFilter(request, response);
            return;
        }

        // Kiểm tra quyền truy cập theo URL và Role
        if (user != null) {
            // Xử lý quyền truy cập cho các role
            if (url.contains("/admin/home") && user.getRoleid() == 1) {
                chain.doFilter(request, response);  // Nếu là admin, cho phép truy cập
            } else if (url.contains("/manager/home") && user.getRoleid() == 2) {
                chain.doFilter(request, response);  // Nếu là manager, cho phép truy cập
            } else if (url.contains("/user/home") && user.getRoleid() == 5) {
                chain.doFilter(request, response);  // Nếu là user, cho phép truy cập
            } else {
                resp.sendRedirect(req.getContextPath() + "/login");  // Nếu không có quyền, chuyển về login
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");  // Nếu chưa đăng nhập, chuyển về login
        }
    }

    @Override
    public void destroy() {
        // Có thể để trống nếu không cần giải phóng tài nguyên
    }
}
