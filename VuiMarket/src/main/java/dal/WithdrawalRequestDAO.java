/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WithdrawalRequestDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    private DBContext dbContext;

    public WithdrawalRequestDAO() {
        dbContext = DBContext.getInstance();
    }

    public boolean addWithdrawalRequest(int uid, int amount, String accountNum, String accountOwer, String bankName, String withdrawCode) {
        String query = "INSERT INTO WithdrawalRequest (status, uid, amount, accountNumber, accountOwner, bankName, withdrawCode) \n"
                + "VALUES (1, ?, ?, ?, ?, ?, ?)";
        try {
            ps = dbContext.getConnection().prepareStatement(query);
            ps.setInt(1, uid);
            ps.setInt(2, amount);
            ps.setString(3, accountNum);
            ps.setString(4, accountOwer);
            ps.setString(5, bankName);
            ps.setString(6, withdrawCode);

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
