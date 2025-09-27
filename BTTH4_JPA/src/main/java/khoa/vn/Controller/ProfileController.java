package khoa.vn.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import khoa.vn.Entity.Profile;
import khoa.vn.Entity.User;
import khoa.vn.Service.ProfileService;
import khoa.vn.Service.Impl.ProfileServiceImpl;
import khoa.vn.Util.Constant;
import khoa.vn.Util.UploadUtils;

@WebServlet(urlPatterns = { "/profile", "/profile/edit", "/profile/update" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, 
    maxFileSize = 1024 * 1024 * 10, 
    maxRequestSize = 1024 * 1024 * 50
)
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProfileService profileService = new ProfileServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURL().toString();

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Profile profile = profileService.findById(user.getId());
        req.setAttribute("profile", profile != null ? profile : new Profile());

        if (url.contains("edit")) {
            req.getRequestDispatcher("/views/profile/edit.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/views/profile/view.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURL().toString();

        if (url.contains("update")) {
            updateProfile(req, resp);
        } else {
            doGet(req, resp);
        }
    }

    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");

            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            Profile profile = profileService.findById(user.getId());
            if (profile == null) {
                profile = new Profile();
                profile.setUser(user);
            }

            BeanUtils.populate(profile, req.getParameterMap());

            Part filePart = req.getPart("file");
            if (filePart != null && filePart.getSize() > 0) {
                // Xóa ảnh cũ nếu có
                if (profile.getImages() != null) {
                    File oldFile = new File(Constant.DIR + "\\profile\\" + profile.getImages());
                    if (oldFile.exists()) oldFile.delete();
                }

                String fileName = "profile_" + user.getId() + "_" + System.currentTimeMillis();
                profile.setImages(UploadUtils.processUpload("file", req, Constant.DIR + "\\profile", fileName));
            }

            profileService.update(profile);

            req.setAttribute("profile", profile);
            req.setAttribute("message", "Cập nhật profile thành công!");
            req.getRequestDispatcher("/views/profile/edit.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi cập nhật profile: " + e.getMessage());
            req.getRequestDispatcher("/views/profile/edit.jsp").forward(req, resp);
        }
    }
}
