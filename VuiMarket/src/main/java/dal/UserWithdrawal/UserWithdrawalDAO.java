/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.UserWithdrawal;

import static constant.constant.RECORD_PER_PAGE;
import context.DBContext;
import dal.DAO;
import entity.RechargeUsers;
import entity.User;
import entity.UserWithdrawl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author pc
 */
public class UserWithdrawalDAO {

    private List<UserWithdrawl> listUserWithdrawl;
    private DBContext dbContext;
    private DAO dao;
    PreparedStatement ps;
    ResultSet rs;

    public UserWithdrawalDAO() {
        listUserWithdrawl = new LinkedList<>();
        dao = new DAO();
        dbContext = DBContext.getInstance();
    }

    public int findTotalRecord() {
        String sql = "select count(w.id) from withdrawalrequest w";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return -1;
    }
//    public static void main(String[] args) {
//        AdminChargeUsersDAO dao = new AdminChargeUsersDAO();
//        int a = dao.findTotalRecord();
//        System.out.println(a);
//    }

//    public static void main(String[] args) {
//        AdminChargeUsersDAO dao = new AdminChargeUsersDAO();
//        List<RechargeUsers> list = dao.findByPage(1);
//        for (RechargeUsers o : list) {
//            System.out.println(o);
//        }
//    }
    public List<UserWithdrawl> findByPage(int page) {
        String sql = "select w.id, w.status, w.uid, w.amount, w.accountNumber, w.accountOwner, w.bankName, w.createdAt, w.updatedAt, w.withdrawCode from withdrawalrequest w \n"
                + "order by w.id LIMIT ?, ?";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, (page - 1) * RECORD_PER_PAGE);
            ps.setInt(2, RECORD_PER_PAGE);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                int status = rs.getInt(2);
                int uid = rs.getInt(3);
                int amount = rs.getInt(4);
                String accountNumber = rs.getString(5);
                String accountOwner = rs.getString(6);
                String bankName = rs.getString(7);
                Timestamp date_created = rs.getTimestamp(8);
                Timestamp date_updated = rs.getTimestamp(9);
                String code = rs.getString(10);

                UserWithdrawl userWithdrawl = new UserWithdrawl();
                DecimalFormat decimalFormat = new DecimalFormat("#,###");

                userWithdrawl.setId(id);
                userWithdrawl.setStatus(userWithdrawl.getStatus(status));
                userWithdrawl.setUid(uid);

                String moneyValue = String.valueOf(amount);
                userWithdrawl.setAmount(decimalFormat.format(Double.parseDouble(moneyValue)));

                userWithdrawl.setAccountNumber(accountNumber);
                userWithdrawl.setAccountOwner(accountOwner);
                userWithdrawl.setBanhName(bankName);
                userWithdrawl.setCreatedAt(date_created);
                userWithdrawl.setUpdateAt(date_updated);
                userWithdrawl.setWithdrawCode(code);

                if (uid != 0) {
                    User idUser = dao.findInforUserBuyId(uid);
                    String userName = idUser.getFullname();
                    userWithdrawl.setFullName(userName);
                }

                listUserWithdrawl.add(userWithdrawl);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return listUserWithdrawl;
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
                System.out.println(e + "ham mysaleorder");
            }
        }

    }

    public UserWithdrawl findStatusById(int idRequest) {
        String sql = "select w.status from withdrawalrequest w\n"
                + "where w.id = ?";
        UserWithdrawl userWithdrawl = new UserWithdrawl();
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, idRequest);

            rs = ps.executeQuery();

            if (rs.next()) {
                int status = rs.getInt("status");
                userWithdrawl.setStatus(String.valueOf(status));
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return userWithdrawl;
    }

    public void updateStatusRequest(int idRequest, int status) {
        String sql = "UPDATE withdrawalrequest w\n"
                + "SET w.status = ?\n"
                + "where w.id = ?";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, idRequest);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
    }

}
