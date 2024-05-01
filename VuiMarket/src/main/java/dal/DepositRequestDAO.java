/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author pc
 */
public class DepositRequestDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    private DBContext dbContext;

    public DepositRequestDAO() {
        dbContext = DBContext.getInstance();
    }

    public boolean addDepositRequest(int uid, String username, int amount, String code) {
    String query = "INSERT INTO depositRequest (uid, username, status, amount, createdAt, updateAT, rechargeCode) VALUES (?, ?, 1, ?, NOW(), NOW(), ?)";
    try {
        ps = dbContext.getConnection().prepareStatement(query);
        ps.setInt(1, uid);
        ps.setString(2, username);
        ps.setInt(3, amount);
        ps.setString(4, code);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        System.out.println(e);
    } finally {
        closeResultSetAndStatement(rs, ps);
    }
    return false;
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
}

