package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    // Đặt một static biến instance để lưu trữ phiên bản duy nhất của lớp
    private static DBContext instance;
    // Kết nối cũng được là một static biến để đảm bảo rằng nó là duy nhất cho toàn bộ ứng dụng
    public Connection connection;

    // Phương thức private constructor để ngăn chặn việc tạo đối tượng từ bên ngoài
    private DBContext() {
        try {
            String user = "root";
            String pass = "tungnob510";
            String url = "jdbc:mysql://localhost:3306/vuimarket";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("dongketnoi");
        }
    }

    // Phương thức getInstance để truy cập đến phiên bản duy nhất của lớp
    public static DBContext getInstance() {
        if (instance == null) {
            instance = new DBContext();
        }
        return instance;
    }

    // Phương thức để lấy kết nối
    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        // Sử dụng phương thức getInstance để lấy thể hiện của lớp DBContext
        DBContext dbContext = DBContext.getInstance();

        if (dbContext.getConnection() != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}
