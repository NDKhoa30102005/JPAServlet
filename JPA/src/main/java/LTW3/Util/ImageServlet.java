package LTW3.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/images/category/*")
public class ImageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = request.getPathInfo().substring(1); // bỏ dấu /
        File file = new File(Constant.DIR + File.separator + filename);

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404
            return;
        }

        response.setContentType(getServletContext().getMimeType(file.getName()));
        response.setContentLengthLong(file.length());

        Files.copy(file.toPath(), response.getOutputStream());
    }
}
