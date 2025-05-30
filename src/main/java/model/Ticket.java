package model;

public class Ticket {

    int stt;
    String fullName;
    String sdt;
    String movieName;
    String cinemaName;
    String ticketID;
    String cinemaID;
    String showtimeID;

    public Ticket(){

    }
    public Ticket(String ticketID, String cinemaID, String showtimeID) {
        this.ticketID = ticketID;
        this.cinemaID = cinemaID;
        this.showtimeID = showtimeID;
    }



    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getShowtimeID() {
        return showtimeID;
    }

    public void setShowtimeID(String showtimeID) {
        this.showtimeID = showtimeID;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketID='" + ticketID + '\'' +
                ", cinemaID='" + cinemaID + '\'' +
                ", showtimeID='" + showtimeID + '\'' +
                '}';

    }
}
