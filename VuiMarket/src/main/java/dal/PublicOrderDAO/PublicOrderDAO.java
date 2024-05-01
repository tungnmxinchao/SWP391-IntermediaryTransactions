/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.PublicOrderDAO;

import static constant.constant.RECORD_PER_PAGE;
import context.DBContext;
import dal.DAO;
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
public class PublicOrderDAO {

    private User user;
    private List<MySaleOrder> listMyOrder;
    private DBContext dbContext;
    private DAO dao;
    PreparedStatement ps;
    ResultSet rs;

    public PublicOrderDAO() {
        user = new User();
        listMyOrder = new LinkedList<>();
        dbContext = DBContext.getInstance();
        dao = new DAO();
    }

    public int findTotalRecord(int userId) {
        String sql = "select count(m.id) from MySaleOrder m \n"
                + "WHERE m.isPublic = 1 and m.updatedable = 1";
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

    public List<MySaleOrder> findByPage(int page, int userId) {

        String sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, "
                + "m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, "
                + "m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink, m.buyerId, m.updatedable, m.customerCanComplain from MySaleOrder m\n"
                + "join User u\n"
                + "on m.createdBy = u.id\n"
                + "where m.isPublic = 1 and m.updatedable = 1\n"
                + "Order by m.id\n"
                + "LIMIT ?, ?";

        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, (page - 1) * RECORD_PER_PAGE);
            ps.setInt(2, RECORD_PER_PAGE);
            rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt(1);
                int idUser = rs.getInt(2);
                String createdBy = rs.getString(3);
                int status = rs.getInt(4);
                String title = rs.getString(5);
                String isSellerFee = rs.getString(6);
                int feeOnSuccess = rs.getInt(7);
                int totalMoneyForBuyer = rs.getInt(8);
                int moneyValue = rs.getInt(9);
                int sellerReciveSuccess = rs.getInt(10);
                String description = rs.getString(11);
                String hiddentValue = rs.getString(12);
                String contact = rs.getString(13);
                String isPublic = rs.getString(14);
                Timestamp date_created = rs.getTimestamp(15);
                Timestamp date_updated = rs.getTimestamp(16);
                String linkShare = rs.getString(17);
                int buyerId = rs.getInt(18);
                String updatedable = rs.getString(19);
                String customerComplain = rs.getString(20);

                MySaleOrder mySaleOrder = new MySaleOrder();
                DecimalFormat decimalFormat = new DecimalFormat("#,###");

                mySaleOrder.setId(id);
                mySaleOrder.setIdUser(idUser);
                mySaleOrder.setCreatedBy(createdBy);

                mySaleOrder.setStatus(mySaleOrder.getStatus(status));

                mySaleOrder.setTitle(title);
                mySaleOrder.setIsSellerChargeFee(mySaleOrder.getIsSellerFee(isSellerFee));

                String feeOnSuccessString = String.valueOf(feeOnSuccess);
                mySaleOrder.setFeeOnSuccess(decimalFormat.format(Double.parseDouble(feeOnSuccessString)));

                String moneyForBuyerString = String.valueOf(totalMoneyForBuyer);
                mySaleOrder.setTotalMoneyForBuyer(decimalFormat.format(Double.parseDouble(moneyForBuyerString)));

                String moneyString = String.valueOf(moneyValue);
                mySaleOrder.setMoneyValue(decimalFormat.format(Double.parseDouble(moneyString)));

                String sellerRecivedString = String.valueOf(sellerReciveSuccess);
                mySaleOrder.setSellerReceivedOnSuccess(decimalFormat.format(Double.parseDouble(sellerRecivedString)));

                mySaleOrder.setDescription(description);
                mySaleOrder.setHiddenValue(hiddentValue);
                mySaleOrder.setContact(contact);

                mySaleOrder.setIsPublic(mySaleOrder.getIsPublic(isPublic));

                mySaleOrder.setCreatedAt(date_created);
                mySaleOrder.setUpdatedAt(date_updated);
                mySaleOrder.setShareLink(linkShare);

                if (buyerId != 0) {
                    User Buyer_Id = dao.findInforUserBuyId(buyerId);
                    String buyerName = Buyer_Id.getFullname();
                    mySaleOrder.setIdBuyer(buyerId);
                    mySaleOrder.setBuyer(buyerName);
                }

                mySaleOrder.setUpdatedable(updatedable);
                mySaleOrder.setCustomerCanComplain(customerComplain);

                listMyOrder.add(mySaleOrder);

            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return listMyOrder;
    }

    public MySaleOrder findRecordByOrderIdAndInforUser(String orderId, String userId, String userName) {
        String sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, "
                + "m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, "
                + "m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink, m.buyerId, m.updatedable, m.customerCanComplain from MySaleOrder m\n"
                + "join User u \n"
                + "on u.id = m.createdBy\n"
                + "where m.id = ? and m.createdBy = ?";
        MySaleOrder mySaleOrder = new MySaleOrder();
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(orderId));
            ps.setInt(2, Integer.parseInt(userId));
            rs = ps.executeQuery();
            DecimalFormat decimalFormat = new DecimalFormat("#,###");

            rs.next();
            int id = rs.getInt(1);
            int idUser = rs.getInt(2);
            String createdBy = rs.getString(3);
            int status = rs.getInt(4);
            String title = rs.getString(5);
            String isSellerFee = rs.getString(6);
            int feeOnSuccess = rs.getInt(7);
            int totalMoneyForBuyer = rs.getInt(8);
            int moneyValue = rs.getInt(9);
            int sellerReciveSuccess = rs.getInt(10);
            String description = rs.getString(11);
            String hiddentValue = rs.getString(12);
            String contact = rs.getString(13);
            String isPublic = rs.getString(14);
            Timestamp date_created = rs.getTimestamp(15);
            Timestamp date_updated = rs.getTimestamp(16);
            String linkShare = rs.getString(17);

            int buyerId = rs.getInt(18);
            String updatedable = rs.getString(19);
            String customerComplain = rs.getString(20);

            mySaleOrder.setId(id);
            mySaleOrder.setIdUser(idUser);
            mySaleOrder.setCreatedBy(createdBy);

            mySaleOrder.setStatus(mySaleOrder.getStatus(status));

            mySaleOrder.setTitle(title);
            mySaleOrder.setIsSellerChargeFee(mySaleOrder.getIsSellerFee(isSellerFee));

            String feeOnSuccessString = String.valueOf(feeOnSuccess);
            mySaleOrder.setFeeOnSuccess(decimalFormat.format(Double.parseDouble(feeOnSuccessString)));

            String moneyForBuyerString = String.valueOf(totalMoneyForBuyer);
            mySaleOrder.setTotalMoneyForBuyer(decimalFormat.format(Double.parseDouble(moneyForBuyerString)));

            String moneyString = String.valueOf(moneyValue); // Chuyển đổi từ int sang chuỗi
            mySaleOrder.setMoneyValue(decimalFormat.format(Double.parseDouble(moneyString)));

            String sellerRecivedString = String.valueOf(sellerReciveSuccess);
            mySaleOrder.setSellerReceivedOnSuccess(decimalFormat.format(Double.parseDouble(sellerRecivedString)));

            mySaleOrder.setDescription(description);

            mySaleOrder.setContact(contact);

            mySaleOrder.setIsPublic(mySaleOrder.getIsPublic(isPublic));

            mySaleOrder.setCreatedAt(date_created);
            mySaleOrder.setUpdatedAt(date_updated);
            mySaleOrder.setShareLink("http://localhost:8080/VuiMarket/formInfor?orderId=" + id + "&userId=" + idUser);

            if (buyerId != 0) {
                User Buyer_Id = dao.findInforUserBuyId(buyerId);
                String buyerName = Buyer_Id.getFullname();
                mySaleOrder.setIdBuyer(buyerId);
                mySaleOrder.setBuyer(buyerName);
            }
            mySaleOrder.setUpdatedable(updatedable);
            mySaleOrder.setCustomerCanComplain(customerComplain);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return mySaleOrder;
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
