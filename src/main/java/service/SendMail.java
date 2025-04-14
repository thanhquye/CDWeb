package service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import utils.ConfigReader;

import java.util.Properties;

public class SendMail {

    // Hàm gửi email chứa mật khẩu mới
    public static boolean sendNewPasswordEmail(String toEmail, String newPassword) {
        final String senderEmail = ConfigReader.getProperty("senderEmail");
        final String senderPassword = ConfigReader.getProperty("senderPassword");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.user", senderEmail);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
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
}