package utils;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    static final String from = "manhnek510@gmail.com";
    static final String password = "mgev gopn nwmk qihv";

    public static boolean sendEmail(String to, String subject, String content) {
        // Properties: khai báo các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Tạo Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        // Phiên làm việc
        Session session = Session.getInstance(props, auth);

        // Tạo một tin nhắn
        MimeMessage msg = new MimeMessage(session);

        try {
            // Kiểu nội dung
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

            // Người gửi
            msg.setFrom(new InternetAddress(from, "Support VuiMarket"));

            // Người nhận
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            // Tiêu đề email
            msg.setSubject(subject, "UTF-8");

            // Quy định ngày gửi
            msg.setSentDate(new Date());

            // Nội dung
            msg.setContent(content, "text/html; charset=UTF-8");

            // Gửi email
            Transport.send(msg);
            System.out.println("Gửi email thành công");
            return true;
        } catch (Exception e) {
            System.out.println("Gặp lỗi trong quá trình gửi email");
            e.printStackTrace();
            return false;
        }
    }

    public static void sendRegistrationEmail(String toEmail, String username, String confirmationLink) {
        String subject = "[Thông báo] - Kích hoạt tài khoản!";
        String content = "Xin chào <strong>" + username + "</strong>,<br><br>"
                + "Bạn đã sử dụng email để tạo tài khoản!<br><br>"
                + "Vui lòng truy cập đường dẫn sau để kích hoạt tài khoản:<br>"
                + "<a href='" + confirmationLink + "'>" + confirmationLink + "</a><br><br>";

        sendEmail(toEmail, subject, content);
    }
    
    public static void sendForgotPasswordEmail(String toEmail, String username, String confirmationLink) {
        String subject = "[Thông báo] - Đổi mật khẩu mới!";
        String content = "Xin chào <strong>" + username + "</strong>,<br><br>"
                + "Bạn đã yêu cầu đổi mật khẩu mới do quên mật khẩu cũ!<br><br>"
                + "Vui lòng truy cập đường dẫn sau để đổi mật khẩu mới:<br>"
                + "<a href='" + confirmationLink + "'>" + confirmationLink + "</a><br><br>";

        sendEmail(toEmail, subject, content);
    }
}
