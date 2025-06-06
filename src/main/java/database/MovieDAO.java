package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements DAOInterface<Movie> {
    int newMovieID = 0;

    @Override
    public ArrayList<Movie> selectAll() {
        return null;
    }

    @Override
    public ArrayList<Movie> selectById(Movie object) {
        return null;
    }

    @Override
    public int insert(Movie obj) {
        return 0;
    }

    @Override
    public int insertAll(ArrayList<Movie> arrayList) {
        return 0;
    }

    @Override
    public int delete(Movie obj) {
        return 0;
    }

    @Override
    public int deleteAll(ArrayList<Movie> arrayList) {
        return 0;
    }

    @Override
    public int update(Movie obj) {
        return 0;
    }

    public List<MovieMediaLink> getAllMovie() {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT  * FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID " ;
        try {
            List<MovieMediaLink> list = new ArrayList<>();
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                MovieMediaLink movie = new MovieMediaLink();
                movie.setMovieID(rs.getString("movieID"));
                movie.setMovieName(rs.getString("movieName"));
                movie.setMovieCategory(rs.getString("movieCategory"));
                movie.setReleaseDate(rs.getString("releaseDate"));
                movie.setDirector(rs.getString("director"));
                movie.setDuration(rs.getString("duration"));
                movie.setCountry(rs.getString("country"));
                movie.setMovieDescription(rs.getString("movieDescription"));
                movie.setMovieContent(rs.getString("movieContent"));
                movie.setIsPublished(rs.getInt("isPublished"));
                movie.setMovieScore(rs.getDouble("movieScore"));
                movie.setLinkMovieTrailer(rs.getString("linkMovieTrailer"));
                movie.setLinkMovieImage(rs.getString("linkMovieImage"));
                list.add(movie);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<MovieMediaLink> getAllMovieBySTT() {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT  * FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID " ;
        int i =1;
        try {
            List<MovieMediaLink> list = new ArrayList<>();
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                MovieMediaLink movie = new MovieMediaLink();
                movie.setStt(i++);
                movie.setMovieID(rs.getString("movieID"));
                movie.setMovieName(rs.getString("movieName"));
                movie.setMovieCategory(rs.getString("movieCategory"));
                movie.setReleaseDate(rs.getString("releaseDate"));
                movie.setDirector(rs.getString("director"));
                movie.setDuration(rs.getString("duration"));
                movie.setCountry(rs.getString("country"));
                movie.setMovieDescription(rs.getString("movieDescription"));
                movie.setMovieContent(rs.getString("movieContent"));
                movie.setIsPublished(rs.getInt("isPublished"));
                movie.setMovieScore(rs.getDouble("movieScore"));
                movie.setLinkMovieTrailer(rs.getString("linkMovieTrailer"));
                movie.setLinkMovieImage(rs.getString("linkMovieImage"));
                list.add(movie);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MovieMediaLink> getNewestFilms(int num) {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT  * FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID\n" +
                     "ORDER BY releaseDate desc LIMIT ?;" ;
        try {
            List<MovieMediaLink> list = new ArrayList<>();
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setInt(1, num);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                MovieMediaLink movie = new MovieMediaLink();
                movie.setMovieID(rs.getString("movieID"));
                movie.setMovieName(rs.getString("movieName"));
                movie.setMovieCategory(rs.getString("movieCategory"));
                movie.setReleaseDate(rs.getString("releaseDate"));
                movie.setDirector(rs.getString("director"));
                movie.setDuration(rs.getString("duration"));
                movie.setCountry(rs.getString("country"));
                movie.setMovieDescription(rs.getString("movieDescription"));
                movie.setMovieContent(rs.getString("movieContent"));
                movie.setIsPublished(rs.getInt("isPublished"));
                movie.setMovieScore(rs.getDouble("movieScore"));
                movie.setLinkMovieTrailer(rs.getString("linkMovieTrailer"));
                movie.setLinkMovieImage(rs.getString("linkMovieImage"));
                list.add(movie);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<MovieMediaLink> getPublishedMoive(int isPublished, int numMovie) {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT * FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID\n" +
                "WHERE m.isPublished = ? LIMIT ?" ;

        try {
            List<MovieMediaLink> list = new ArrayList<>();
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, isPublished);
            s.setInt(2, numMovie);
            ResultSet rs = s.executeQuery();
            while(rs.next()) {
                MovieMediaLink movie = new MovieMediaLink();
                movie.setMovieID(rs.getString("movieID"));
                movie.setMovieName(rs.getString("movieName"));
                movie.setMovieCategory(rs.getString("movieCategory"));
                movie.setReleaseDate(rs.getString("releaseDate"));
                movie.setDirector(rs.getString("director"));
                movie.setDuration(rs.getString("duration"));
                movie.setCountry(rs.getString("country"));
                movie.setMovieDescription(rs.getString("movieDescription"));
                movie.setMovieContent(rs.getString("movieContent"));
                movie.setIsPublished(rs.getInt("isPublished"));
                movie.setMovieScore(rs.getDouble("movieScore"));
                movie.setLinkMovieTrailer(rs.getString("linkMovieTrailer"));
                movie.setLinkMovieImage(rs.getString("linkMovieImage"));
                list.add(movie);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<String> getAllMovieCatelogy() {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT DISTINCT m.movieCategory FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID" ;
        try {
            List<String> list = new ArrayList<>();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                String category = rs.getString("movieCategory");
                list.add(category);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<String> getAllMovieCountry() {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT DISTINCT m.country FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID" ;
        try {
            List<String> list = new ArrayList<>();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                String country = rs.getString("country");
                list.add(country);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<String> getAllMovieYear() {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT DISTINCT YEAR(m.releaseDate) FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID" ;
        try {
            List<String> list = new ArrayList<>();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                String releaseDate = rs.getString("YEAR(m.releaseDate)");
                list.add(releaseDate);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<MovieMediaLink> getMovieForCinemaAndShowtime(String cinemaID, String date) {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT m.*, mml.linkMovieTrailer, mml.linkMovieImage FROM showtime st JOIN contain c ON st.showtimeID = c.showtimeID \n" +
                "\t\t\t\t\t\t\t\t\tJOIN cinema ci ON c.cinemaID = ci.cinemaID\n" +
                "\t\t\t\t\t\t\t\t\tJOIN movie m ON st.movieID = m.movieID\t \n" +
                "\t\t\t\t\t\t\t\t\tJOIN moviemedialink mml ON mml.movieID = m.movieID\t\n" +
                "WHERE ci.cinemaID = ? AND st.showDate = ?" ;
        try {
            List<MovieMediaLink> list = new ArrayList<>();
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, cinemaID);
            s.setString(2, date);
            ResultSet rs = s.executeQuery();
            while(rs.next()) {
                MovieMediaLink movie = new MovieMediaLink();
                movie.setMovieID(rs.getString("movieID"));
                movie.setMovieName(rs.getString("movieName"));
                movie.setMovieCategory(rs.getString("movieCategory"));
                movie.setReleaseDate(rs.getString("releaseDate"));
                movie.setDirector(rs.getString("director"));
                movie.setDuration(rs.getString("duration"));
                movie.setCountry(rs.getString("country"));
                movie.setMovieDescription(rs.getString("movieDescription"));
                movie.setMovieContent(rs.getString("movieContent"));
                movie.setIsPublished(rs.getInt("isPublished"));
                movie.setMovieScore(rs.getDouble("movieScore"));
                movie.setLinkMovieTrailer(rs.getString("linkMovieTrailer"));
                movie.setLinkMovieImage(rs.getString("linkMovieImage"));
                list.add(movie);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public MovieMediaLink getMovieByID(String mid) {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT * FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID\n" +
                "WHERE m.movieID = ?" ;

        try {
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, mid);
            ResultSet rs = s.executeQuery();
            MovieMediaLink movie = new MovieMediaLink();
            while(rs.next()) {
                movie.setMovieID(rs.getString("movieID"));
                movie.setMovieName(rs.getString("movieName"));
                movie.setMovieCategory(rs.getString("movieCategory"));
                movie.setReleaseDate(rs.getString("releaseDate"));
                movie.setDirector(rs.getString("director"));
                movie.setDuration(rs.getString("duration"));
                movie.setCountry(rs.getString("country"));
                movie.setMovieDescription(rs.getString("movieDescription"));
                movie.setMovieContent(rs.getString("movieContent"));
                movie.setIsPublished(rs.getInt("isPublished"));
                movie.setMovieScore(rs.getDouble("movieScore"));
                movie.setLinkMovieTrailer(rs.getString("linkMovieTrailer"));
                movie.setLinkMovieImage(rs.getString("linkMovieImage"));
            }
            return movie;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<MovieMediaLink> getMovieByCategory(String category) {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT * FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID WHERE m.movieCategory LIKE ?";
        try {
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1,  category );
            ResultSet rs = s.executeQuery();
            List<MovieMediaLink> list = new ArrayList<>();
            while(rs.next()) {
                MovieMediaLink movie = new MovieMediaLink();
                movie.setMovieID(rs.getString("movieID"));
                movie.setMovieName(rs.getString("movieName"));
                movie.setMovieCategory(rs.getString("movieCategory"));
                movie.setReleaseDate(rs.getString("releaseDate"));
                movie.setDirector(rs.getString("director"));
                movie.setDuration(rs.getString("duration"));
                movie.setCountry(rs.getString("country"));
                movie.setMovieDescription(rs.getString("movieDescription"));
                movie.setMovieContent(rs.getString("movieContent"));
                movie.setIsPublished(rs.getInt("isPublished"));
                movie.setMovieScore(rs.getDouble("movieScore"));
                movie.setLinkMovieTrailer(rs.getString("linkMovieTrailer"));
                movie.setLinkMovieImage(rs.getString("linkMovieImage"));
                list.add(movie);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<MovieMediaLink> getMovieByCountry(String country) {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT * FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID WHERE m.country LIKE ?";
        try {
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1,  country );
            ResultSet rs = s.executeQuery();
            List<MovieMediaLink> list = new ArrayList<>();
            while(rs.next()) {
                MovieMediaLink movie = new MovieMediaLink();
                movie.setMovieID(rs.getString("movieID"));
                movie.setMovieName(rs.getString("movieName"));
                movie.setMovieCategory(rs.getString("movieCategory"));
                movie.setReleaseDate(rs.getString("releaseDate"));
                movie.setDirector(rs.getString("director"));
                movie.setDuration(rs.getString("duration"));
                movie.setCountry(rs.getString("country"));
                movie.setMovieDescription(rs.getString("movieDescription"));
                movie.setMovieContent(rs.getString("movieContent"));
                movie.setIsPublished(rs.getInt("isPublished"));
                movie.setMovieScore(rs.getDouble("movieScore"));
                movie.setLinkMovieTrailer(rs.getString("linkMovieTrailer"));
                movie.setLinkMovieImage(rs.getString("linkMovieImage"));
                list.add(movie);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<MovieMediaLink> getMovieByYear(int year) {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT * FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID WHERE YEAR(m.releaseDate) = ?";
        try {
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1,  year );
            ResultSet rs = s.executeQuery();
            List<MovieMediaLink> list = new ArrayList<>();
            while(rs.next()) {
                MovieMediaLink movie = new MovieMediaLink();
                movie.setMovieID(rs.getString("movieID"));
                movie.setMovieName(rs.getString("movieName"));
                movie.setMovieCategory(rs.getString("movieCategory"));
                movie.setReleaseDate(rs.getString("releaseDate"));
                movie.setDirector(rs.getString("director"));
                movie.setDuration(rs.getString("duration"));
                movie.setCountry(rs.getString("country"));
                movie.setMovieDescription(rs.getString("movieDescription"));
                movie.setMovieContent(rs.getString("movieContent"));
                movie.setIsPublished(rs.getInt("isPublished"));
                movie.setMovieScore(rs.getDouble("movieScore"));
                movie.setLinkMovieTrailer(rs.getString("linkMovieTrailer"));
                movie.setLinkMovieImage(rs.getString("linkMovieImage"));
                list.add(movie);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<MovieMediaLink> getMovieByName(String name) {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT * FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID WHERE m.movieName LIKE ?";
        try {
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1,  name );
            ResultSet rs = s.executeQuery();
            List<MovieMediaLink> list = new ArrayList<>();
            while(rs.next()) {
                MovieMediaLink movie = new MovieMediaLink();
                movie.setMovieID(rs.getString("movieID"));
                movie.setMovieName(rs.getString("movieName"));
                movie.setMovieCategory(rs.getString("movieCategory"));
                movie.setReleaseDate(rs.getString("releaseDate"));
                movie.setDirector(rs.getString("director"));
                movie.setDuration(rs.getString("duration"));
                movie.setCountry(rs.getString("country"));
                movie.setMovieDescription(rs.getString("movieDescription"));
                movie.setMovieContent(rs.getString("movieContent"));
                movie.setIsPublished(rs.getInt("isPublished"));
                movie.setMovieScore(rs.getDouble("movieScore"));
                movie.setLinkMovieTrailer(rs.getString("linkMovieTrailer"));
                movie.setLinkMovieImage(rs.getString("linkMovieImage"));
                list.add(movie);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<MovieMediaLink> getMostPopularMoive (int numMovie) {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT * FROM movie m JOIN moviemedialink mml ON m.movieID = mml.movieID WHERE m.movieID IN\n" +
                " ( SELECT m.movieID FROM movie m JOIN showtime st ON m.movieID = st.movieID  \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t JOIN ticket t ON t.showtimeID = st.showtimeID \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t JOIN transactionticket tt ON tt.ticketID = t.ticketID\n" +
                "\tGROUP BY m.movieID\n" +
                "\tHAVING COUNT(m.movieID) >= ALL (SELECT COUNT(m.movieID) AS c FROM movie m JOIN showtime st ON m.movieID = st.movieID  \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  JOIN ticket t ON t.showtimeID = st.showtimeID \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  JOIN transactionticket tt ON tt.ticketID = t.ticketID\n" +
                "\t\t\t\t\t\t\t\t\t\t\t  GROUP BY m.movieID, m.movieName ) ) LIMIT ?" ;

        try {
            List<MovieMediaLink> list = new ArrayList<>();
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, numMovie);
            ResultSet rs = s.executeQuery();
            while(rs.next()) {
                MovieMediaLink movie = new MovieMediaLink();
                movie.setMovieID(rs.getString("movieID"));
                movie.setMovieName(rs.getString("movieName"));
                movie.setMovieCategory(rs.getString("movieCategory"));
                movie.setReleaseDate(rs.getString("releaseDate"));
                movie.setDirector(rs.getString("director"));
                movie.setDuration(rs.getString("duration"));
                movie.setCountry(rs.getString("country"));
                movie.setMovieDescription(rs.getString("movieDescription"));
                movie.setMovieContent(rs.getString("movieContent"));
                movie.setIsPublished(rs.getInt("isPublished"));
                movie.setMovieScore(rs.getDouble("movieScore"));
                movie.setLinkMovieTrailer(rs.getString("linkMovieTrailer"));
                movie.setLinkMovieImage(rs.getString("linkMovieImage"));
                list.add(movie);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean addMovie(String movieName, String movieCategory, Date releaseDate, String director, String duration,
                           String country, String movieDescription, String movieContent, boolean isPublished, double movieScore) {
        Connection connection = null;
        MovieMediaLink movie = new MovieMediaLink();
        try {
            connection = JDBCUtil.getConnection();
            String maxIdQuery = "select movieID from movie";
            PreparedStatement idPr = connection.prepareStatement(maxIdQuery);
            ResultSet rsId = idPr.executeQuery();
            while (rsId.next()) {
                String id = rsId.getString("movieID").substring(2);
                int intID = Integer.parseInt(id);
                if (intID > newMovieID) {
                    newMovieID = intID;

                }
            }
            System.out.println(newMovieID);
            try {
                String query = "insert into movie values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
                PreparedStatement prInsert = connection.prepareStatement(query);
                prInsert.setString(1, "Mv" + (newMovieID+1));
                prInsert.setString(2, movieName);
                prInsert.setString(3, movieCategory);
                java.sql.Date sqlReleaseDate = new java.sql.Date(releaseDate.getTime());
                prInsert.setDate(4, sqlReleaseDate);
                prInsert.setString(5, director);
                prInsert.setString(6, duration);
                prInsert.setString(7, country);
                prInsert.setString(8, movieDescription);
                prInsert.setString(9, movieContent);
                prInsert.setBoolean(10, isPublished);
                prInsert.setDouble(11, movieScore);
                int rs = prInsert.executeUpdate();
                if (rs >0){
                    return true;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.closeConnection(connection);
        }
    }

    public boolean addMovieLink( String linkMovieTrailer, String linkMovieImage) {
        Connection connection = null;
        MovieMediaLink movie = new MovieMediaLink();
        try {
            connection = JDBCUtil.getConnection();
            String query = "insert into moviemedialink values(?,?,?,?)";
            PreparedStatement preInsert = connection.prepareStatement(query);
            preInsert.setString(1, "Mv" + (newMovieID+1));
            preInsert.setString(2, "mml" + (newMovieID+1));
            preInsert.setString(3, linkMovieTrailer);
            preInsert.setString(4, linkMovieImage);
            int rs = preInsert.executeUpdate();
            if (rs >0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
        return false;
    }

    public boolean updateMovie(String movieID, String movieName, String movieCategory, Date releaseDate, String director, String duration,
                              String country, String movieDescription, String movieContent, boolean isPublished, double movieScore){
        Connection connection = null;

        try {
            connection = JDBCUtil.getConnection();
            String query = "update movie set movieName = ?, movieCategory = ?, releaseDate = ?,  director = ?, duration = ?, country = ?, movieDescription = ?,  movieContent = ?,  isPublished = ?,  movieScore = ? where movieID = ? ";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, movieName);
            pr.setString(2, movieCategory);
            pr.setDate(3, new java.sql.Date(releaseDate.getTime()));
            pr.setString(4, director);
            pr.setString(5, duration);
            pr.setString(6, country);
            pr.setString(7, movieDescription);
            pr.setString(8, movieContent);
            pr.setBoolean(9, isPublished);
            pr.setDouble(10, movieScore);
            pr.setString(11, movieID);
            int rs = pr.executeUpdate();
            if (rs >0){
                return true;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }
    public static void main(String[] args) {
//        System.out.println(getNewestFilms(4).get(0).getMovieName());
//        System.out.println(getNewestFilms(4).get(2).getMovieName());
//        System.out.println(getNewestFilms(4).get(3).getMovieName());
//        System.out.println(getNewestFilms(4).get(0).getLinkMovieTrailer());
//        System.out.println(getNewestFilms(4).get(1).getLinkMovieTrailer());
//        System.out.println(getNewestFilms(4).get(2).getLinkMovieTrailer());
//        System.out.println(getNewestFilms(4).get(3).getLinkMovieTrailer());
    }
}