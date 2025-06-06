package controller;

import database.JDBCUtil;
import database.MovieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MovieMediaLink;
import model.User;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "quanliphim", value = "/quanliphim")
public class MovieManagerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("admin");
    if(user == null ){
        resp.sendRedirect("404.jsp");
    }else {
        MovieDAO movieDAO = new MovieDAO();
        req.setAttribute("movieList", movieDAO.getAllMovieBySTT());
        req.setAttribute("showAll", true);
        req.getRequestDispatcher("quanliphim.jsp").forward(req,resp);

    }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String category = req.getParameter("category");
        String releaseDate = req.getParameter("releaseDate");
        String director = req.getParameter("director");
        String duration = req.getParameter("duration");
        String country = req.getParameter("country");
        String description = req.getParameter("description");
        String linkMovieTrailer = req.getParameter("linkMovieTrailer");
        String linkMovieImage = req.getParameter("linkMovieImage");
        String content = req.getParameter("content");
        String published = req.getParameter("isPublished");
        double score = Double.parseDouble(req.getParameter("score"));

        boolean isPublished = false;
        if (published.equalsIgnoreCase("true")){
            isPublished = true;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date sqlDate = null;
        try {
            java.util.Date utilDate = dateFormat.parse(releaseDate);
            sqlDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        MovieDAO movieDAO = new MovieDAO();
        movieDAO.addMovie(name, category, sqlDate, director, duration, country, description, content, isPublished, score);
        movieDAO.addMovieLink(linkMovieTrailer, linkMovieImage);
        resp.sendRedirect("quanliphim");

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String idMovie = req.getParameter("movieID");
        try{

            Connection connection = JDBCUtil.getConnection();
            String sqlDelete = "delete from movie where movieID = ?";
            PreparedStatement pr = connection.prepareStatement(sqlDelete);
            pr.setString(1, idMovie);
            int resultSet = pr.executeUpdate();

            System.out.println(resultSet);
            System.out.println("DELETE" + idMovie);
            JSONObject jsonObject = new JSONObject();
            if(resultSet > 0){
                jsonObject.put("status", 200);
                jsonObject.put("message", "Đã xóa thành công");
                resp.setContentType("application/json");
                resp.getWriter().write(jsonObject.toString());
            }
            else {
                jsonObject.put("status", 500);
                jsonObject.put("message", "Xóa phim thất bại. Vui lòng thử lại");
                resp.setContentType("application/json");
                resp.getWriter().write(jsonObject.toString());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
