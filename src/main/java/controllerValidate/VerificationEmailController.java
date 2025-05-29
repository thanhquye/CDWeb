package controllerValidate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.SendMail;
import verify.VerificationCodeStore;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/resendVerification")
public class VerificationEmailController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String email = user.getEmail();
        String username = user.getUserName();

        if (email == null || email.isEmpty()) {
            response.sendRedirect("login.jsp");
            return;
        }

        Long lastSentTime = (Long) session.getAttribute("lastVerificationEmailTime");
        long now = System.currentTimeMillis();

        if (lastSentTime != null && now - lastSentTime < 60 * 1000) {
            request.setAttribute("resent", false);
            request.setAttribute("reason", "Vui lòng chờ ít nhất 1 phút trước khi gửi lại mã xác minh.");
            request.getRequestDispatcher("GmailVerify.jsp").forward(request, response);
            return;
        }

        String code = UUID.randomUUID().toString();
        VerificationCodeStore.store(email, code);

        boolean sent = SendMail.sendVerificationEmail(email, code, username);
        System.out.println("Gửi email xác minh " + (sent ? "THÀNH CÔNG" : "THẤT BẠI"));

        if (sent) {
            session.setAttribute("lastVerificationEmailTime", now);
        }

        request.setAttribute("resent", sent);
        if (!sent) {
            request.setAttribute("reason", "Gửi email thất bại. Vui lòng thử lại sau.");
        }
        request.getRequestDispatcher("GmailVerify.jsp").forward(request, response);
    }
}