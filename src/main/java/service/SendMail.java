package service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import utils.ConfigReader;

import java.util.Properties;

public class SendMail {
    private static Session createEmailSession() {
        final String senderEmail = ConfigReader.getProperty("senderEmail");
        final String senderPassword = ConfigReader.getProperty("senderPassword");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }

    // Hàm gửi email chứa mật khẩu mới
    public static boolean sendNewPasswordEmail(String toEmail, String newPassword) {

        try {
            Session session = createEmailSession();
            final String senderEmail = ConfigReader.getProperty("senderEmail");
            Message message = new MimeMessage(session);

            // Đặt tên người gửi, "Movie Theater"
            message.setFrom(new InternetAddress(senderEmail, "Movie Theater"));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mật khẩu mới");

            // Nội dung email
            String htmlContent = "<!DOCTYPE html>" +
                    "<html><body>" +
                    "<h2 style='color:#2c3e50;'>Xin chào,</h2>" +
                    "<p>Yêu cầu đặt lại mật khẩu của bạn đã được chấp nhận.</p>" +
                    "<p>Mật khẩu mới của bạn là: <b style='color:#e74c3c;'>" + newPassword + "</b></p>" +
                    "<p>Vui lòng đăng nhập và thay đổi mật khẩu sau khi đăng nhập thành công.</p>" +
                    "<br>" +
                    "<p>Trân trọng,<br>Movie Theater Team</p>" +
                    "</body></html>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Email mật khẩu mới đã được gửi tới: " + toEmail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Email ko gửi được");
            return false;
        }
    }

    public static boolean sendVerificationEmail(String toEmail, String code, String username) {
        try {
            Session session = createEmailSession();
            final String senderEmail = ConfigReader.getProperty("senderEmail");

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail, "Movie Theater Team"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Xác minh tài khoản của bạn");

            String verificationLink = "http://localhost:8080/Movie_Ticket_Website_war/verify?email=" + toEmail + "&code=" + code;

            String htmlContent = "<!DOCTYPE html><html><body>" +
                    "<h2 style='color:#2c3e50;'>Xin chào, " + username + "!</h2>" +
                    "<p>Vui lòng xác minh tài khoản bằng cách nhấn vào liên kết dưới đây:</p>" +
                    "<p><a href='" + verificationLink + "' style='color: #1abc9c;'>Xác minh tài khoản</a></p>" +
                    "<br><p>Trân trọng,<br>Movie Theater Team</p>" +
                    "</body></html>";

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);

            System.out.println("Email xác minh đã gửi tới: " + toEmail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gửi email xác minh thất bại");
            return false;
        }
    }

}