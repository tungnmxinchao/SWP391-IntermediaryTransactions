/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.AdminChargeUsers;

import static constant.constant.RECORD_PER_PAGE;
import context.DBContext;
import dal.DAO;
import entity.RechargeUsers;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author TNO
 */
public class AdminChargeUsersDAO {

    private List<RechargeUsers> listReChargeUsers;
    private DBContext dbContext;
    private DAO dao;
    PreparedStatement ps;
    ResultSet rs;

    public AdminChargeUsersDAO() {
        listReChargeUsers = new LinkedList<>();
        dao = new DAO();
        dbContext = DBContext.getInstance();
    }

    public int findTotalRecord() {
        String sql = "select count(d.id) from depositrequest d";
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
    public List<RechargeUsers> findByPage(int page) {
        String sql = "select d.id, d.uid, d.username, d.status, d.amount, d.createdAt, d.updateAt, d.rechargeCode from depositrequest d\n"
                + "Order by d.id\n"
                + "LIMIT ?, ?";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, (page - 1) * RECORD_PER_PAGE);
            ps.setInt(2, RECORD_PER_PAGE);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                int id_User = rs.getInt(2);
                String username = rs.getString(3);
                int status = rs.getInt(4);
                int amountMoney = rs.getInt(5);
                Timestamp date_created = rs.getTimestamp(6);
                Timestamp date_updated = rs.getTimestamp(7);
                String code = rs.getString(8);
                
                RechargeUsers rechargeUsers = new RechargeUsers();
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                
                rechargeUsers.setId(id);
                rechargeUsers.setUid(id_User);
                rechargeUsers.setFullname(username);

                rechargeUsers.setStatus(rechargeUsers.getStatus(status));

                String moneyValue = String.valueOf(amountMoney);
                rechargeUsers.setMoneyValue(decimalFormat.format(Double.parseDouble(moneyValue)));

                rechargeUsers.setCreatedAt(date_created);
                rechargeUsers.setUpdatedAt(date_updated);

                if (id_User != 0) {
                    User idUser = dao.findInforUserBuyId(id_User);
                    String userName = idUser.getFullname();
                    rechargeUsers.setUid(id_User);
                    rechargeUsers.setFullname(userName);
                }
                rechargeUsers.setCode(code);
                listReChargeUsers.add(rechargeUsers);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return listReChargeUsers;
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

    public void updateBalanceUser(int uid, int newBalance) {
        String sql = "UPDATE User\n"
                + "SET balance = ?\n"
                + "WHERE id = ?;";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, newBalance);
            ps.setInt(2, uid);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
    }

    public void updateStatusRequest(int idRequest, int status) {
        String sql = "UPDATE depositrequest d\n"
                + "SET d.status = ?\n"
                + "where d.id = ?";
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

    public static void main(String[] args) {
        AdminChargeUsersDAO dao = new AdminChargeUsersDAO();
        RechargeUsers a = dao.findStatusById(5);
        System.out.println(a);
    }

    public RechargeUsers findStatusById(int idRequest) {
        String sql = "select d.status from depositrequest d\n"
                + "where d.id = ?";
        RechargeUsers rechargeUser = new RechargeUsers();
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, idRequest);

            rs = ps.executeQuery();

            if (rs.next()) {
                int status = rs.getInt("status");
                rechargeUser.setStatus(String.valueOf(status));
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return rechargeUser;
    }

}
