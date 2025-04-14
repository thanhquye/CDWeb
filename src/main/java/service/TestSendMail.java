package service;

public class TestSendMail {
    public static void main(String[] args) {
        String email = "shikitea2000@gmail.com";
        String code = generateRandomCode();

        boolean success = SendMail.sendNewPasswordEmail(email, code);
        System.out.println("Mã OTP vừa tạo: " + code);


        if (success) {
            System.out.println("Gửi thành công tới: " + email);
        } else {
            System.out.println("Gửi thất bại. Vui lòng kiểm tra cấu hình hoặc kết nối mạng.");
        }
    }

    public static String generateRandomCode() {
        int code = (int) (Math.random() * 900000) + 100000; // Tạo mã OTP 6 chữ số
        return String.valueOf(code);
    }
}