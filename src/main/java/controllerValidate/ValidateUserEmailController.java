package controllerValidate;

import database.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CheckUserEmail", value = "/check-user-email")
public class ValidateUserEmailController extends HttpServlet {
    //    của register
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String username = req.getParameter("username");
        String email = req.getParameter("email");

        UserDAO userDAO = new UserDAO();
        boolean usernameExists = false;
        boolean emailExists = false;

        if (username != null && !username.trim().isEmpty()) {
            usernameExists = userDAO.checkUsernameExist(username.trim());
        }
        if (email != null && !email.trim().isEmpty()) {
            emailExists = userDAO.checkEmailExits(email.trim());
        }

        String jsonResp = "{\"usernameExists\": " + usernameExists + ", \"emailExists\": " + emailExists + "}";
        resp.getWriter().write(jsonResp);
    }
}

