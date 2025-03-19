package controller;

import database.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Register", value = "/register")
public class RegisterController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String userName = req.getParameter("userName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");




        UserDAO userDAO = new UserDAO();
        boolean check = userDAO.registerUser(userName, email, password);


        if (check) {
            req.setAttribute("status", "success");
        } else {
            req.setAttribute("status", "failed");
        }
        req.getRequestDispatcher("register.jsp").forward(req,resp);
        System.out.println(req.getAttribute("status"));

    }
}
