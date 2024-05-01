package dal;

import context.DBContext;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import utils.SendEmail;

public class DAO {

    private PreparedStatement ps;
    private ResultSet rs;
    private DBContext dbContext;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public DAO() {
        dbContext = DBContext.getInstance();
    }

    public User login(String user, String pass) {
        String query = "select u.id, u.role_name, u.username, u.password, u.fullName, u.avatar, u.email, u.phone, u.detail, u.balance, u.confirmation_code, u.is_confirmed from User u \n"
                + "WHERE u.username = ? AND u.password = ?";
        User userLogin = new User();
        try {
            ps = dbContext.getConnection().prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);

            rs = ps.executeQuery();

            rs.next();
            int id = rs.getInt(1);
            String role_name = rs.getString(2);
            String username = rs.getString(3);
            String password = rs.getString(4);
            String fullname = rs.getString(5);
            String avatar = rs.getString(6);
            String email = rs.getString(7);
            String phone = rs.getString(8);
            String detail = rs.getString(9);
            int balance = rs.getInt(10);
            String confirmCode = rs.getString(11);
            boolean isConfirm = rs.getBoolean(12);

            userLogin.setUid(id);
            userLogin.setRolename(role_name);
            userLogin.setUsername(username);
            userLogin.setPass(password);
            userLogin.setFullname(fullname);
            userLogin.setAvatar(avatar);
            userLogin.setEmail(email);
            userLogin.setPhone(phone);
            userLogin.setDetail(detail);
            userLogin.setBalance(balance);
            userLogin.setConfirmationCode(confirmCode);
            userLogin.setConfirmed(isConfirm);

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return userLogin;
    }

    public User getAccountByAid(String aid) {
        String query = "select u.id, u.role_name, u.username, u.password, u.fullName, u.avatar, u.email, u.phone, u.detail, u.createdby, u.updateby, u.balance from User u\n"
                + "where u.id = ?";
        User userFind = new User();
        try {
            ps = dbContext.getConnection().prepareStatement(query);
            ps.setInt(1, Integer.parseInt(aid));
            rs = ps.executeQuery();

            rs.next();
            int id = rs.getInt(1);
            String role_name = rs.getString(2);
            String username = rs.getString(3);
            String password = rs.getString(4);
            String fullname = rs.getString(5);
            String avatar = rs.getString(6);
            String email = rs.getString(7);
            String phone = rs.getString(8);
            String detail = rs.getString(9);
            Timestamp createdAt = rs.getTimestamp(10);
            Timestamp updateAt = rs.getTimestamp(11);
            int balance = rs.getInt(12);

            userFind.setUid(id);
            userFind.setRolename(role_name);
            userFind.setUsername(username);
            userFind.setPass(password);
            userFind.setFullname(fullname);
            userFind.setAvatar(avatar);
            userFind.setEmail(email);
            userFind.setPhone(phone);
            userFind.setDetail(detail);
            userFind.setBalance(balance);

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return userFind;
    }

    public boolean updateProfile(User account) {
        String query = "UPDATE User SET fullname = ?, phone = ?, detail = ? WHERE id = ?";
        try {
            ps = dbContext.getConnection().prepareStatement(query);
            ps.setString(1, account.getFullname());
            ps.setString(2, account.getPhone());
            ps.setString(3, account.getDetail());
            ps.setInt(4, account.getUid());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return false;
    }

    public boolean changePassword(int accountId, String oldPassword, String newPassword) {
        String query = "SELECT * FROM User WHERE id = ? AND password = ?";

        try {
            // Check old password
            ps = dbContext.getConnection().prepareStatement(query);
            ps.setInt(1, accountId);
            ps.setString(2, oldPassword);
            rs = ps.executeQuery();

            if (rs.next()) {
                // If old password is correct, update the new password
                String updateQuery = "UPDATE User SET password = ? WHERE id = ?";
                ps = dbContext.getConnection().prepareStatement(updateQuery);
                ps.setString(1, newPassword);
                ps.setInt(2, accountId);

                int rowsUpdated = ps.executeUpdate();

                return rowsUpdated > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndStatement(rs, ps);
        }

        return false;
    }

    public boolean existedUser(String user) throws Exception {
        try {
            String query = "SELECT * FROM User WHERE username=?";
            ps = dbContext.getConnection().prepareStatement(query);
            ps.setString(1, user);
            rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndStatement(rs, ps);
        }

        return false;
    }

    public void register(User a, String user, String email, String pass, String mobile) {
        String query = "INSERT INTO User (username, password, email, phone, confirmation_code, is_confirmed, role_name) VALUES (?,?,?,?,?,?,?)";
        try {
            // Generate confirmation code
            String confirmationCode = generateConfirmationCode();

            ps = dbContext.getConnection().prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.setString(3, email);
            ps.setString(4, mobile);
            ps.setString(5, confirmationCode);
            ps.setBoolean(6, false);
            ps.setString(7, "user");

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Đăng ký tài khoản thành công!");
                // Send confirmation email asynchronously

                String confirmationLink = buildConfirmationLink(confirmationCode);
                EmailSender emailSender = new EmailSender(email, user, confirmationLink);
                executorService.submit(emailSender);
            } else {
                System.out.println("Đăng ký tài khoản thất bại.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
    }

    private class EmailSender implements Runnable {

        private String email;
        private String username;
        private String confirmationLink;

        public EmailSender(String email, String username, String confirmationLink) {
            this.email = email;
            this.username = username;
            this.confirmationLink = confirmationLink;
        }

        @Override
        public void run() {
            // Send registration email
            SendEmail.sendRegistrationEmail(email, username, confirmationLink);
        }
    }

    public void shutdownExecutorService() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    private String generateConfirmationCode() {
        // Tạo một mã xác nhận ngẫu nhiên sử dụng SecureRandom và mã hóa Base64
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);

        return Base64.getEncoder().encodeToString(randomBytes).replaceAll("\\s", "").replaceAll("\\+", "");
    }

    private String buildConfirmationLink(String confirmationCode) {
        return "http://localhost:8080/VuiMarket/confirm?code=" + confirmationCode;
    }

    public boolean confirmAccount(String confirmationCode) {
        String query = "UPDATE User SET is_confirmed = true WHERE confirmation_code = ?";
        try {
            ps = dbContext.getConnection().prepareStatement(query);
            ps.setString(1, confirmationCode);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return false;
    }

    public String getUsernameByConfirmationCode(String confirmationCode) {
        String query = "SELECT username FROM User WHERE confirmation_code = ?";
        String username = null;
        try {
            ps = dbContext.getConnection().prepareStatement(query);
            ps.setString(1, confirmationCode);
            rs = ps.executeQuery();

            if (rs.next()) {
                username = rs.getString("username");
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return username;
    }

    public User findInforUserBuyId(int Iduser) {
        String sql = "select u.id, u.role_name, u.username , u.password, u.fullName, u.avatar, u.email, u.phone, u.detail, u.balance from User u\n"
                + "where u.id = ?";
        User userFind = new User();
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, Iduser);
            rs = ps.executeQuery();

            rs.next();
            int id = rs.getInt(1);
            String role_name = rs.getString(2);
            String username = rs.getString(3);
            String password = rs.getString(4);
            String fullname = rs.getString(5);
            String avatar = rs.getString(6);
            String email = rs.getString(7);
            String phone = rs.getString(8);
            String detail = rs.getString(9);
            int balance = rs.getInt(10);

            userFind.setUid(id);
            userFind.setRolename(role_name);
            userFind.setUsername(username);
            userFind.setPass(password);
            userFind.setFullname(fullname);
            userFind.setAvatar(avatar);
            userFind.setEmail(email);
            userFind.setPhone(phone);
            userFind.setDetail(detail);
            userFind.setBalance(balance);

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return userFind;
    }

    public String getEmailByUsername(String username) {
        String email = null;
        String confirmationCode = null;
        String query = "SELECT email, confirmation_code FROM User WHERE username = ?";
        try {
            ps = dbContext.getConnection().prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                email = rs.getString("email");
                confirmationCode = rs.getString("confirmationCode");
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return email;
    }

    public User getUserByUsername(String username) {
        User user = null;
        String query = "SELECT * FROM User WHERE username = ?";
        try {
            ps = dbContext.getConnection().prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getInt(14),
                        rs.getString(12),
                        rs.getBoolean(13)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return user;
    }

    private void closeResultSetAndStatement(ResultSet rs, PreparedStatement ps) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.out.println(e + "ham dao");
            }
        }

    }

    public void sendEmailForgotPass(String user, String email, String confirmationCode) {
        try {
            String confirmationLink = buildPasswordRecoveryLink(confirmationCode);
            EmailForgotPass emailForgotPass = new EmailForgotPass(email, user, confirmationLink);
            executorService.submit(emailForgotPass);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
    }

    private String buildPasswordRecoveryLink(String confirmationCode) {
        return "http://localhost:8080/VuiMarket/passwordRecovery?code=" + confirmationCode;
    }

    private class EmailForgotPass implements Runnable {

        private String email;
        private String username;
        private String confirmationLink;

        public EmailForgotPass(String email, String username, String confirmationLink) {
            this.email = email;
            this.username = username;
            this.confirmationLink = confirmationLink;
        }

        @Override
        public void run() {
            // Send registration email
            SendEmail.sendForgotPasswordEmail(email, username, confirmationLink);
        }
    }

    public boolean passwordRecovery(String username, String newPassword) {
        String updateQuery = "UPDATE User SET password = ? WHERE username = ?";
        try {
            ps = dbContext.getConnection().prepareStatement(updateQuery);
            ps.setString(1, newPassword);
            ps.setString(2, username);

            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return false;
    }

    public static void main(String[] args) {
        DAO dao = new DAO();
        User user = dao.getUserByUsername("mrd");

        if (user != null) {
            System.out.println("Thông tin người dùng:");
        } else {
            System.out.println("Không tìm thấy người dùng");
        }
    }
}
