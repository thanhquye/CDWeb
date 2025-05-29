package controller;

import database.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "register", value = "/register")
public class RegisterController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Go in register servlet");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String userName = req.getParameter("userName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String retypePassword = req.getParameter("retypePassword");

        UserDAO userDAO = new UserDAO();

        // Kiểm tra email đã tồn tại
        if (userDAO.checkEmailExits(email)) {
            req.setAttribute("status", "email_exists");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            System.out.println("gmail exist");
            return;
        }

        // Kiểm tra username đã tồn tại
        if (userDAO.checkUsernameExist(userName)) {
            req.setAttribute("status", "username_exists");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            System.out.println("username exist");
            return;
        }

        // Kiểm tra mật khẩu
        if (!password.equals(retypePassword) || password.length() < 6) {
            req.setAttribute("status", "password_error");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        boolean check = userDAO.registerUser(userName, email, password);
        System.out.println("Register success");

        if (check) {
            System.out.println("Register success 2");
            resp.sendRedirect(req.getContextPath() + "/home-servlet?action=direct");
            return;
        } else {
            System.out.println("Register failed");
            req.setAttribute("status", "failed");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }

        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

}
