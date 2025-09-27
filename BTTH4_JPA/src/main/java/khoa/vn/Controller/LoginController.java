package khoa.vn.Controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import khoa.vn.Entity.User;
import khoa.vn.Service.UserService;
import khoa.vn.Service.Impl.UserServiceImpl;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User loggedInUser = (User) session.getAttribute("user");
            redirectByRole(loggedInUser, request, response);
            return;
        }
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.findByUsernameAndPassword(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            redirectByRole(user, request, response);
        } else {
            request.setAttribute("errorMessage", "Invalid username or password!");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }

    private void redirectByRole(User user, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (user.getRoleid() == 1) {
            response.sendRedirect(request.getContextPath() + "/admin/home");
        } else if (user.getRoleid() == 2) {
            response.sendRedirect(request.getContextPath() + "/manager/home");
        } else {
            response.sendRedirect(request.getContextPath() + "/user/home");
        }
    }
}