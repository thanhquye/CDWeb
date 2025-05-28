package controller;

import database.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.SendMail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet(name = "ForgotPass", value = "/forgot-pass")
public class ForgotPassController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Lấy email từ client
        String email = req.getParameter("email");
        String message = "";
        boolean isSuccess = false;

        // 1. Kiểm tra email có hợp lệ / tồn tại trong hệ thống không)
        if (email == null || email.trim().isEmpty()) {
            message = "Email không được để trống.";
        } else {
            // 2. Sinh mật khẩu mới
            String newPassword = generateRandomPassword(8);

            // 3.  Cập nhật mật khẩu mới vào database cho người dùng tương ứng
            UserDAO userDAO = new UserDAO();
            userDAO.updatePasswordByEmail(email, newPassword);

            // 4. Gửi email chứa mật khẩu mới cho người dùng
            boolean emailSent = SendMail.sendNewPasswordEmail(email, newPassword);
            if (emailSent) {
                isSuccess = true;
                message = "Mật khẩu mới đã được gửi tới email của bạn.";
            } else {
                message = "Gửi email thất bại, vui lòng thử lại sau!";
            }
        }

        // Trả về JSON cho AJAX
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String jsonResp = "{\"status\": \"" + (isSuccess ? "success" : "error") + "\","
                + "\"message\": \"" + message + "\"}";
        out.write(jsonResp);
    }

    // Hàm sinh mật khẩu ngẫu nhiên với độ dài cho trước
    private String generateRandomPassword(int length) {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            int index = rnd.nextInt(charSet.length());
            password.append(charSet.charAt(index));
        }
        return password.toString();
    }
}