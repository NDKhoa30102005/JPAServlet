package LTW3.Controller.admin;

import java.io.IOException;
import LTW3.Entity.User;
import LTW3.Service.UserService;
import LTW3.Service.Impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/login", "/logout" })
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            User loggedInUser = (User) session.getAttribute("user");
            if (loggedInUser.getRoleid() == 1) {
                response.sendRedirect(request.getContextPath() + "/admin/home");
            } else if (loggedInUser.getRoleid() == 2) {
                response.sendRedirect(request.getContextPath() + "/manager/home");
            } else {
                response.sendRedirect(request.getContextPath() + "/user/home");
            }
            return; 
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.findByUsernameAndPassword(username, password);

        if (user != null) {
        	 System.out.println("ROLEID = " + user.getRoleid());
            session.setAttribute("user", user);

            if (user.getRoleid() == 1) {
                response.sendRedirect(request.getContextPath() + "/admin/home");
            } else if (user.getRoleid() == 2) {
                response.sendRedirect(request.getContextPath() + "/manager/home");
            } else {
                response.sendRedirect(request.getContextPath() + "/user/home");
            }
        } else {
           
            request.setAttribute("errorMessage", "Invalid username or password!");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();  // Xóa session

        response.sendRedirect(request.getContextPath() + "/login");  // Redirect về login
    }
}
