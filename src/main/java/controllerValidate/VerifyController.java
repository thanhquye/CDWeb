package controllerValidate;

import database.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import verify.VerificationCodeStore;

import java.io.IOException;

@WebServlet("/verify")
public class VerifyController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String code = request.getParameter("code");

        if (VerificationCodeStore.verify(email, code)) {
            UserDAO userDAO = new UserDAO();
            userDAO.markEmailVerified(email); // cập nhật isVerifyEmail = true
            System.out.println("Verify success. update in DAO success");

            // cập nhật session user
            HttpSession session = request.getSession();
            User updatedUser = userDAO.getUserByEmail(email);
            session.setAttribute("user", updatedUser);

            VerificationCodeStore.remove(email);
            response.sendRedirect(request.getContextPath() + "/home-servlet?action=direct");
        } else {
            response.sendRedirect("GmailVerify.jsp?status=invalid_code");
        }
    }
}