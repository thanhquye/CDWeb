package database;


import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketDAO {
    public ArrayList<Ticket> getAllTicket(){
        Connection connection = null;
        ArrayList<Ticket> list = new ArrayList<>();
        int i =1;
        try {
            connection = JDBCUtil.getConnection();
            String query = "select * from ticket tk JOIN cinema cnm ON tk.cinemaID = cnm.cinemaID JOIN booking bk ON tk.ticketID = bk.ticketID JOIN customer cus ON bk.userID = cus.userID JOIN showtime st ON tk.showtimeID = st.showtimeID JOIN movie mv ON st.movieID = mv.movieID";
            PreparedStatement pr = connection.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                Ticket ticket = new Ticket();
                ticket.setStt(i++);
                ticket.setFullName(rs.getString("fullName"));
                ticket.setSdt(rs.getString("phoneNumber"));
                ticket.setMovieName(rs.getString("movieName"));
                ticket.setCinemaName(rs.getString("cinemaName"));
                ticket.setTicketID(rs.getString("ticketID"));
                ticket.setCinemaID(rs.getString("cinemaID"));
                ticket.setShowtimeID(rs.getString("showtimeID"));
                list.add(ticket);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public ArrayList<Ticket> getAllTicketBySTT(){
        Connection connection = null;
        ArrayList<Ticket> list = new ArrayList<>();
        int i =0;
        try {
            connection = JDBCUtil.getConnection();
            String query = "select * from ticket";
            PreparedStatement pr = connection.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                Ticket ticket = new Ticket();
                ticket.setTicketID(rs.getString("ticketID"));
                ticket.setCinemaID(rs.getString("cinemaID"));
                ticket.setShowtimeID(rs.getString("showtimeID"));
                list.add(ticket);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public ArrayList<Ticket> getAllTicketByMovieID(String id){
        Connection connection = null;
        ArrayList<Ticket> list = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String query = "select * from ticket where cinemaID = ?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                Ticket ticket = new Ticket();
                ticket.setTicketID(rs.getString("ticketID"));
                ticket.setCinemaID(rs.getString("cinemaID"));
                ticket.setShowtimeID(rs.getString("showtimeID"));
                list.add(ticket);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }
}
