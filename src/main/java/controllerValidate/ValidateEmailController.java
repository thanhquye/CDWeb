package controllerValidate;

import database.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ValidateEmail", value = "/validate-email")
public class ValidateEmailController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        boolean isValid = false;


        // Kiểm tra định dạng email
        if (email != null && email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            // Kiểm tra email có tồn tại trong hệ thống hay không
            UserDAO userDAO = new UserDAO();
            isValid = userDAO.isEmailExists(email); // Hàm kiểm tra trong database
        }

        // Trả về JSON với boolean thực
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.write("{\"isValid\": " + isValid + "}"); // Không dùng dấu ngoặc kép cho boolean
    }

}
