/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.TransactionHistory;

import static constant.constant.RECORD_PER_PAGE;
import context.DBContext;
import dal.DAO;
import entity.History;
import entity.MySaleOrder;
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
public class TransactionHistoryDAO {

    private User user;
    private List<History> listHistory;
    private DBContext dbContext;
    private DAO dao;
    PreparedStatement ps;
    ResultSet rs;

    public TransactionHistoryDAO() {
        user = new User();
        listHistory = new LinkedList<>();
        dbContext = DBContext.getInstance();
        dao = new DAO();
    }

//    public static void main(String[] args) {
//        TransactionHistoryDAO dao = new TransactionHistoryDAO();
//        dao.insertTransactionHistory(10000, "-", 1, "test", 1);
//    }
    public void insertTransactionHistory(int moneyValue, String typeTracsaction, int status, String description, int uid) {
        String sql = "INSERT INTO TransactionHistory (moneyValue, typeTransaction, status, description, uId) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, moneyValue);
            ps.setString(2, typeTracsaction);
            ps.setInt(3, status);
            ps.setString(4, description);
            ps.setInt(5, uid);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
    }

    public int findTotalRecord( int idUserAccess) {
        String sql = "select count(t.id) from transactionhistory t\n"
                + "where t.uId = ?";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, idUserAccess);
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

    public List<History> findByPage(int page, int idUserAccess) {
        String sql = "select t.id, t.moneyValue, t.typeTransaction, t.status, t.description, t.uId, t.createdAt, t.updatedAt from transactionhistory t\n"
                + "where t.uId = ?\n"
                + "order by t.id \n"
                + "LIMIT ?, ?";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, idUserAccess);
            ps.setInt(2, (page - 1) * RECORD_PER_PAGE);
            ps.setInt(3, RECORD_PER_PAGE);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                int money = rs.getInt(2);
                String typeTrans = rs.getString(3);
                int status = rs.getInt(4);
                String description = rs.getString(5);
                int uid = rs.getInt(6);
                Timestamp date_created = rs.getTimestamp(7);
                Timestamp date_updated = rs.getTimestamp(8);

                History history = new History();
                DecimalFormat decimalFormat = new DecimalFormat("#,###");

                history.setId(id);

                String moneyValue = String.valueOf(money);
                history.setMoneyValue(decimalFormat.format(Double.parseDouble(moneyValue)));

                history.setTypeTransaction(typeTrans);
                history.setStatus(history.getStatus(status));

                history.setDescription(description);
                history.setUId(uid);

                if (uid != 0) {
                    User idUser = dao.findInforUserBuyId(uid);
                    String userName = idUser.getFullname();
                    history.setFullName(userName);
                }
                history.setCreatedAt(date_created);
                history.setUpdatedAt(date_updated);

                listHistory.add(history);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return listHistory;
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

}
