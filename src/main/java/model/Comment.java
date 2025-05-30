package model;

public class Comment {
    int stt;
    String fullName;
    String movieName;
    String commentID;
    String movieID;
    String customerID;
    String commentText;

    public Comment(){

    }

    public Comment(String commentID, String movieID, String customerID, String commentText) {
        this.commentID = commentID;
        this.movieID = movieID;
        this.customerID = customerID;
        this.commentText = commentText;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentID='" + commentID + '\'' +
                ", movieID='" + movieID + '\'' +
                ", customerID='" + customerID + '\'' +
                ", commentText='" + commentText + '\'' +
                '}';
    }
}
