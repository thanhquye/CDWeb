package database;

import model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentDAO {
    public ArrayList<Comment> getAllComment(){
        Connection connection = null;
        ArrayList<Comment> list = new ArrayList<>();
        int i =1;
        try {
            connection = JDBCUtil.getConnection();
            String query = "select * from usercomment ucm join customer cus ON ucm.customerID = cus.customerID JOIN movie mv ON ucm.movieID = mv.movieID";
            PreparedStatement pr = connection.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                Comment comment = new Comment();
                comment.setStt(i++);
                comment.setFullName(rs.getString("fullName"));
                comment.setMovieName(rs.getString("movieName"));
                comment.setCommentID(rs.getString("commentID"));
                comment.setCustomerID(rs.getString("customerID"));
                comment.setMovieID(rs.getString("movieID"));
                comment.setCommentText(rs.getString("commentText"));

                list.add(comment);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.closeConnection(connection);
        }

        return list;
    }
    public Comment getCommentById(String id){
        Connection connection = null;
        Comment comment = null;
        try {
            connection = JDBCUtil.getConnection();
            String query = "select * from usercomment where commentID = ?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                comment = new Comment();
                comment.setCommentID(rs.getString("commentID"));
                comment.setMovieID(rs.getString("movieID"));
                comment.setCustomerID(rs.getString("customerID"));
                comment.setCommentText(rs.getString("commentText"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return comment;
    }

    public boolean updateFilm(String commentID, String text){
        Connection connection = null;

        try {
            connection = JDBCUtil.getConnection();
            String query = "update usercomment set commentText = ?  where commentID = ? ";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, text);
            pr.setString(2, commentID);
            int rs = pr.executeUpdate();
            if (rs >0){
                return true;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }
    public ArrayList<Comment> getAllCommentByMovieID(String movieID) {
        Connection connection = null;
        ArrayList<Comment> list = new ArrayList<>();

        try {
            connection = JDBCUtil.getConnection();
            String query = "select * from usercomment where movieID = ?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, movieID);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentID(rs.getString("commentID"));
                comment.setMovieID(rs.getString("movieID"));
                comment.setCustomerID(rs.getString("customerID"));
                comment.setCommentText(rs.getString("commentText"));
                list.add(comment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
        return list;
    }
    public static void main(String[] args) {
        CommentDAO commentDAO = new CommentDAO();
        for(Comment comment : commentDAO.getAllComment()){
            System.out.println(comment.toString());
        }
        System.out.println(commentDAO.updateFilm("cmt1", "dỡ ẹt"));
    }
}
