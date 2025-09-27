package khoa.vn.Controller;

import java.io.IOException;

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
import khoa.vn.Entity.User;

@WebFilter(urlPatterns = {"/admin/home", "/manager/home", "/user/home"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
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

        if (user != null) {
            if (url.contains("/admin/home") && user.getRoleid() == 1) {
                chain.doFilter(request, response);
            } else if (url.contains("/manager/home") && user.getRoleid() == 2) {
                chain.doFilter(request, response);
            } else if (url.contains("/user/home") && user.getRoleid() == 3) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect(req.getContextPath() + "/login"); 
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
    }
}
