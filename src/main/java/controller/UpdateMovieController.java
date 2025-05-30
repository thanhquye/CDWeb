package controller;

import database.CommentDAO;
import database.MovieDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet(name = "updateMovieController", value = "/updateMovie")
public class UpdateMovieController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String Mid = req.getParameter("mid");
        RequestDispatcher requestDispatcher = null;

        System.out.println(Mid);
        MovieDAO movieDAO = new MovieDAO();
        req.setAttribute("movies", movieDAO.getMovieByID(Mid));
        requestDispatcher = req.getRequestDispatcher("updateMovie.jsp");

        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String Fid = req.getParameter("fid");

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String category = req.getParameter("category");
        String date = req.getParameter("releaseDate");
        String director = req.getParameter("director");
        String duration = req.getParameter("duration");
        String country = req.getParameter("country");
        String description = req.getParameter("description");
        String content = req.getParameter("content");
        String published = req.getParameter("isPublished");
        double score = Double.parseDouble(req.getParameter("score"));

        boolean isPublished = false;
        if (published.equalsIgnoreCase("TRUE")) {
            isPublished = true;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate = null;
        try {
            releaseDate = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


//            FilmDAO filmDAO = new FilmDAO();
//            filmDAO.updateFilm(id, name, category, releaseDate, director, duration, country, description, content, isPublished, score);
        resp.sendRedirect("quanliphim");


    }
}
