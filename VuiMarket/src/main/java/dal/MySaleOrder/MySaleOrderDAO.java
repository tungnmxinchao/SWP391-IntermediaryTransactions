/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.MySaleOrder;

import static constant.constant.RECORD_PER_PAGE;
import context.DBContext;
import dal.DAO;
import entity.MySaleOrder;
import entity.User;
import java.sql.Date;
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
public class MySaleOrderDAO {

    private User user;
    private List<MySaleOrder> listMyOrder;
    private DBContext dbContext;
    private DAO dao;
    PreparedStatement ps;
    ResultSet rs;

    public MySaleOrderDAO() {
        user = new User();
        listMyOrder = new LinkedList<>();
        dbContext = DBContext.getInstance();
        dao = new DAO();
    }

    public void insertNewOrder(String chude, String giatien, String lienhe, String mota, String noidungan, String hiencongkhai, String isSellerFee, int userId) {

        int moneyValue = Integer.parseInt(giatien);

        int totalMoneyForBuyer;
        int feeOnSuccess = moneyValue * 5 / 100;
        int sellerReceivedOnSuccess;

        if (isSellerFee.equalsIgnoreCase("1")) {
            sellerReceivedOnSuccess = moneyValue - feeOnSuccess;
            totalMoneyForBuyer = moneyValue;
        } else {
            totalMoneyForBuyer = moneyValue + feeOnSuccess;
            sellerReceivedOnSuccess = moneyValue;
        }

        String sql = "INSERT INTO MySaleOrder\n"
                + "           (title\n"
                + "           ,createdBy\n"
                + "           ,status\n"
                + "           ,isSellerChargeFee\n"
                + "           ,feeOnSuccess\n"
                + "           ,totalMoneyForBuyer\n"
                + "           ,moneyValue\n "
                + "           ,sellerReceivedOnSuccess\n "
                + "           ,description\n"
                + "           ,hiddenValue\n"
                + "           ,contact\n"
                + "           ,isPublic\n"
                + "           ,customerCanComplain\n"
                + "           ,updatedable)\n"
                + "     VALUES\n"
                + "           (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setString(1, chude);
            ps.setInt(2, userId);
            ps.setInt(3, 1);
            ps.setString(4, isSellerFee);
            ps.setInt(5, feeOnSuccess);
            ps.setInt(6, totalMoneyForBuyer);
            ps.setInt(7, moneyValue);
            ps.setInt(8, sellerReceivedOnSuccess);
            ps.setString(9, mota);
            ps.setString(10, noidungan);
            ps.setString(11, lienhe);
            ps.setString(12, hiencongkhai);
            ps.setString(13, "1");
            ps.setString(14, "1");
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
    }

    public boolean convertToBoolean(String value) {
        return value != null && value.equalsIgnoreCase("on");
    }

    public int findUserIdByUserName(String nameUser) {
        String sql = "select * from User u\n"
                + "where u.username like ?";

        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setString(1, nameUser);
            rs = ps.executeQuery();

            rs.next();
            int UserId = rs.getInt(1);
            String roleName = rs.getString(2);
            String username = rs.getString(3);
            String password = rs.getString(4);
            String fullName = rs.getString(5);
            String avatar = rs.getString(6);
            String email = rs.getString(7);
            String phone = rs.getString(8);
            String detail = rs.getString(9);
            Timestamp createdDate = rs.getTimestamp(10);
            Timestamp updateDate = rs.getTimestamp(11);

            user.setUid(UserId);
            user.setRolename(roleName);
            user.setUsername(username);
            user.setPass(password);
            user.setFullname(fullName);
            user.setAvatar(avatar);
            user.setEmail(email);
            user.setPhone(phone);
            user.setDetail(detail);

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }

        return user.getUid();
    }

    public int findTotalRecord(int userId) {
        String sql = "select count(m.id) from MySaleOrder m\n"
                + "WHERE m.createdBy = ?";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, userId);
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

    public static void main(String[] args) throws Exception {
        MySaleOrderDAO mdao = new MySaleOrderDAO();
        List<MySaleOrder> list = mdao.findByPage(1, 1);
        for (MySaleOrder o : list) {
            System.out.println(o);
        }

    }

    public List<MySaleOrder> findByPage(int page, int userId) {

        String sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, "
                + "m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, "
                + "m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink, m.buyerId, m.updatedable, m.customerCanComplain from MySaleOrder m\n"
                + "join User u\n"
                + "on m.createdBy = u.id\n"
                + "where u.id = ?\n"
                + "Order by m.id\n"
                + "LIMIT ?, ?";

        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, (page - 1) * RECORD_PER_PAGE);
            ps.setInt(3, RECORD_PER_PAGE);
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
            mySaleOrder.setHiddenValue(hiddentValue);
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

    public void updateOrder(String idOrder, String title, String isSeller, String contact, String moneyValue, String isPublic, String description, String hiddenValue) {
        int money = parseMoneyValueToInt(moneyValue);
        int feeOnSuccess = money * 5 / 100;
        int totalMoneyForBuyer;
        int sellerReceivedOnSuccess;

        if (isSeller.equalsIgnoreCase("1")) {
            sellerReceivedOnSuccess = money - feeOnSuccess;
            totalMoneyForBuyer = money;
        } else {
            totalMoneyForBuyer = money + feeOnSuccess;
            sellerReceivedOnSuccess = money;
        }

        String sql = "UPDATE MySaleOrder "
                + "SET title = ?, "
                + "contact = ?, "
                + "moneyValue = ?, "
                + "isPublic = ?, "
                + "description = ?, "
                + "hiddenValue = ?, "
                + "isSellerChargeFee = ?, "
                + "totalMoneyForBuyer = ?, "
                + "feeOnSuccess = ?, "
                + "sellerReceivedOnSuccess = ?, "
                + "updatedAt = NOW() " // Thêm thời gian cập nhật
                + "WHERE id = ?";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, contact);
            ps.setInt(3, money);
            ps.setString(4, isPublic);
            ps.setString(5, description);
            ps.setString(6, hiddenValue);
            ps.setString(7, isSeller);
            ps.setInt(8, totalMoneyForBuyer);
            ps.setInt(9, feeOnSuccess);
            ps.setInt(10, sellerReceivedOnSuccess);
            ps.setInt(11, Integer.parseInt(idOrder));

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
    }

    public static int parseMoneyValueToInt(String moneyValueString) {

        String cleanMoneyValueString = moneyValueString.replaceAll(",", "");

        int moneyValue = Integer.parseInt(cleanMoneyValueString);

        return moneyValue;
    }

//    public static void main(String[] args) {
//        MySaleOrderDAO dao = new MySaleOrderDAO();
//        List<MySaleOrder> list = dao.findByPageAndSearch(
//                1, "73", "-1", "hello", "", "-1", "2", "5000000", "-1",
//                "2024-01-01", "2025-01-01",
//                "2024-01-01", "2025-01-01", 1);
//        for (MySaleOrder o : list) {
//            System.out.println(o);
//        }
//    }
    public List<MySaleOrder> findByPageAndSearch(int IdUser, String idOrder, String status, String buyer, String title, String isPublic, String moneyValueFrom, String moneyValueTo, String sellerChargeFee, String timeCreatedFrom, String timeCreatedTo, String timeUpdateFrom, String timeUpdateTo, int page) {
        String sql = null;
//        boolean isPublicBoolean = convertToBoolean(isPublic);
//        boolean isSellerFeeBoolean = convertToBoolean(sellerChargeFee);

        if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                && !idOrder.isEmpty() && !buyer.isEmpty() && !moneyValueFrom.isEmpty()
                && !moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
            sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                    + "join User u\n"
                    + "on u.id = m.createdBy\n"
                    + "where m.id = ? and m.title like ? and m.moneyValue BETWEEN ? and ? and m.createdAt >= ? and m.createdAt <= ? and m.updatedAt >= ? and m.updatedAt <= ? and u.id = ?\n"
                    + "Order by m.id\n"
                    + "LIMIT ?, ?";
        } else {
            if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.id = ? and m.title like ? and u.id = ?\n"
                        + "Order by m.id\n"
                        + "LIMIT ?, ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                    && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and m.moneyValue BETWEEN ? and ? and u.id = ?\n"
                        + "Order by m.id\n"
                        + "LIMIT ?, ?";

            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and m.createdAt >= ? and m.createdAt <= ? and u.id = ?\n"
                        + "Order by m.id\n"
                        + "LIMIT ?, ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and m.updatedAt >= ? and m.updatedAt <= ? and u.id = ?\n"
                        + "Order by m.id\n"
                        + "LIMIT ?, ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && !idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                    && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.id = ? and m.title like ? and m.moneyValue BETWEEN ? and ? and u.id = ?\n"
                        + "Order by m.id\n"
                        + "LIMIT ?, ?";

            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.id = ? and m.title like ? and m.createdAt >= ? and m.createdAt <= ? and u.id = ?\n"
                        + "Order by m.id\n"
                        + "LIMIT ?, ?";

            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.id = ? and m.title like ? and m.updatedAt >= ? and m.updatedAt <= ? and u.id = ?\n"
                        + "Order by m.id\n"
                        + "LIMIT ?, ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                    && !moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and m.moneyValue BETWEEN ? and ? and m.createdAt >= ? and m.createdAt <= ? and u.id = ?\n"
                        + "Order by m.id\n"
                        + "LIMIT ?, ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                    && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and m.moneyValue BETWEEN ? and ? and m.updatedAt >= ? and m.updatedAt <= ? and u.id = ?\n"
                        + "Order by m.id\n"
                        + "LIMIT ?, ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                    && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and m.createdAt >= ? and m.createdAt <= ? and m.updatedAt >= ? and m.updatedAt <= ? and u.id = ?\n"
                        + "Order by m.id\n"
                        + "LIMIT ?, ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and u.id = ?\n"
                        + "Order by m.id\n"
                        + "LIMIT ?, ?";
            } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "SELECT m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink "
                            + "FROM MySaleOrder m "
                            + "JOIN User u ON u.id = m.createdBy "
                            + "WHERE m.status like ? AND m.title LIKE ? AND u.id = ? "
                            + "ORDER BY m.id "
                            + "LIMIT ?, ?";
                } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.status like ? and m.isPublic like ? and m.title like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.status like ? and m.isSellerChargeFee like ? and m.title like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.isSellerChargeFee like ? and m.isPublic like ? and m.title like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.isPublic like ? and m.title like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.isSellerChargeFee like ? and m.title like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                }

            } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                    && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {

                if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.id = ? and m.title like ? and m.status like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.id = ? and m.title like ? and m.status like ? and m.isPublic like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.id = ? and m.title like ? and m.status like ? and m.isSellerChargeFee like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.id = ? and m.title like ? and m.isSellerChargeFee like ? and m.isPublic like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.id = ? and m.title like ? and m.isPublic like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.id = ? and m.title like ? and m.isSellerChargeFee like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                }

            } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                    && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                    && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.moneyValue BETWEEN ? and ? and m.title like ? and m.status like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.moneyValue BETWEEN ? and ? and m.title like ? and m.status like ? and m.isPublic like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.moneyValue BETWEEN ? and ? and m.title like ? and m.status like ? and m.isSellerChargeFee like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.moneyValue BETWEEN ? and ? and m.title like ? and m.isSellerChargeFee like ? and m.isPublic like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.moneyValue BETWEEN ? and ? and m.title like ? and m.isPublic like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.moneyValue BETWEEN ? and ? and m.title like ? and m.isSellerChargeFee like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                }

            } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {

                if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.createdAt >= ? and m.createdAt <= ? and m.title like ? and m.status like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.createdAt >= ? and m.createdAt <= ? and m.title like ? and m.status like ? and m.isPublic like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.createdAt >= ? and m.createdAt <= ? and m.title like ? and m.status like ? and m.isSellerChargeFee like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.createdAt >= ? and m.createdAt <= ? and m.title like ? and m.isSellerChargeFee like ? and m.isPublic like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.createdAt >= ? and m.createdAt <= ? and m.title like ? and m.isPublic like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.createdAt >= ? and m.createdAt <= ? and m.title like ? and m.isSellerChargeFee like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                }

            } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.updatedAt >= ? and m.updatedAt <= ? and m.title like ? and m.status like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.updatedAt >= ? and m.updatedAt <= ? and m.title like ? and m.status like ? and m.isPublic like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.updatedAt >= ? and m.updatedAt <= ? and m.title like ? and m.status like ? and m.isSellerChargeFee like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.updatedAt >= ? and m.updatedAt <= ? and m.title like ? and m.isSellerChargeFee like ? and m.isPublic like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.updatedAt >= ? and m.updatedAt <= ? and m.title like ? and m.isPublic like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.updatedAt >= ? and m.updatedAt <= ? and m.title like ? and m.isSellerChargeFee like ? and u.id = ?\n"
                            + "Order by m.id\n"
                            + "LIMIT ?, ?";
                }

            }
        }

        try {
            ps = dbContext.getConnection().prepareStatement(sql);

            if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && !idOrder.isEmpty() && !buyer.isEmpty() && !moneyValueFrom.isEmpty()
                    && !moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                    && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                ps.setInt(1, Integer.parseInt(idOrder));
                ps.setString(2, "%" + title + "%");
                ps.setInt(3, Integer.parseInt(moneyValueFrom));
                ps.setInt(4, Integer.parseInt(moneyValueTo));
                ps.setDate(5, Date.valueOf(timeCreatedFrom));
                ps.setDate(6, Date.valueOf(timeCreatedTo));
                ps.setDate(7, Date.valueOf(timeUpdateFrom));
                ps.setDate(8, Date.valueOf(timeUpdateTo));
                ps.setInt(9, IdUser);
                ps.setInt(10, (page - 1) * RECORD_PER_PAGE);
                ps.setInt(11, RECORD_PER_PAGE);
            } else {
                if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setInt(1, Integer.parseInt(idOrder));
                    ps.setString(2, "%" + title + "%");
                    ps.setInt(3, IdUser);
                    ps.setInt(4, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(5, RECORD_PER_PAGE);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                        && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setInt(2, Integer.parseInt(moneyValueFrom));
                    ps.setInt(3, Integer.parseInt(moneyValueTo));
                    ps.setInt(4, IdUser);
                    ps.setInt(5, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(6, RECORD_PER_PAGE);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setDate(2, Date.valueOf(timeCreatedFrom));
                    ps.setDate(3, Date.valueOf(timeCreatedTo));
                    ps.setInt(4, IdUser);
                    ps.setInt(5, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(6, RECORD_PER_PAGE);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setDate(2, Date.valueOf(timeUpdateFrom));
                    ps.setDate(3, Date.valueOf(timeUpdateTo));
                    ps.setInt(4, IdUser);
                    ps.setInt(5, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(6, RECORD_PER_PAGE);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && !idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                        && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setInt(1, Integer.parseInt(idOrder));
                    ps.setString(2, "%" + title + "%");
                    ps.setInt(3, Integer.parseInt(moneyValueFrom));
                    ps.setInt(4, Integer.parseInt(moneyValueTo));
                    ps.setInt(5, IdUser);
                    ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(7, RECORD_PER_PAGE);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setInt(1, Integer.parseInt(idOrder));
                    ps.setString(2, "%" + title + "%");
                    ps.setDate(3, Date.valueOf(timeCreatedFrom));
                    ps.setDate(4, Date.valueOf(timeCreatedTo));
                    ps.setInt(5, IdUser);
                    ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(7, RECORD_PER_PAGE);
                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                    ps.setInt(1, Integer.parseInt(idOrder));
                    ps.setString(2, "%" + title + "%");
                    ps.setDate(3, Date.valueOf(timeUpdateFrom));
                    ps.setDate(4, Date.valueOf(timeUpdateTo));
                    ps.setInt(5, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(6, RECORD_PER_PAGE);
                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                        && !moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setInt(2, Integer.parseInt(moneyValueFrom));
                    ps.setInt(3, Integer.parseInt(moneyValueTo));
                    ps.setDate(4, Date.valueOf(timeCreatedFrom));
                    ps.setDate(5, Date.valueOf(timeCreatedTo));
                    ps.setInt(6, IdUser);
                    ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(8, RECORD_PER_PAGE);
                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                        && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setInt(2, Integer.parseInt(moneyValueFrom));
                    ps.setInt(3, Integer.parseInt(moneyValueTo));
                    ps.setDate(4, Date.valueOf(timeUpdateFrom));
                    ps.setDate(5, Date.valueOf(timeUpdateTo));
                    ps.setInt(6, IdUser);
                    ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(8, RECORD_PER_PAGE);
                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                        && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setDate(2, Date.valueOf(timeCreatedFrom));
                    ps.setDate(3, Date.valueOf(timeCreatedTo));
                    ps.setDate(4, Date.valueOf(timeUpdateFrom));
                    ps.setDate(5, Date.valueOf(timeUpdateTo));
                    ps.setInt(6, IdUser);
                    ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(8, RECORD_PER_PAGE);
                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setInt(2, IdUser);
                    ps.setInt(3, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(4, RECORD_PER_PAGE);

                } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setString(1, "%" + status + "%");
                        ps.setString(2, "%" + title + "%");
                        ps.setInt(3, IdUser);
                        ps.setInt(4, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(5, RECORD_PER_PAGE);

                    } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(status));
                        ps.setString(2, "%" + isPublic + "%");
                        ps.setString(3, "%" + title + "%");
                        ps.setInt(4, IdUser);
                        ps.setInt(5, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(6, RECORD_PER_PAGE);
                    } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(status));
                        ps.setString(2, "%" + sellerChargeFee + "%");
                        ps.setString(3, "%" + title + "%");
                        ps.setInt(4, IdUser);
                        ps.setInt(5, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(6, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setString(1, "%" + sellerChargeFee + "%");
                        ps.setString(2, "%" + isPublic + "%");
                        ps.setString(3, "%" + title + "%");
                        ps.setInt(4, IdUser);
                        ps.setInt(5, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(6, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setString(1, "%" + isPublic + "%");
                        ps.setString(2, "%" + title + "%");
                        ps.setInt(3, IdUser);
                        ps.setInt(4, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(5, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setString(1, "%" + sellerChargeFee + "%");
                        ps.setString(2, "%" + title + "%");
                        ps.setInt(3, IdUser);
                        ps.setInt(4, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(5, RECORD_PER_PAGE);

                    }

                } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                        && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {

                    if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(idOrder));
                        ps.setString(2, "%" + title + "%");
                        ps.setInt(3, Integer.parseInt(status));
                        ps.setInt(4, IdUser);
                        ps.setInt(5, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(6, RECORD_PER_PAGE);
                    } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(idOrder));
                        ps.setString(2, "%" + title + "%");
                        ps.setInt(3, Integer.parseInt(status));
                        ps.setString(4, "%" + isPublic + "%");
                        ps.setInt(5, IdUser);
                        ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(7, RECORD_PER_PAGE);
                    } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(idOrder));
                        ps.setString(2, "%" + title + "%");
                        ps.setString(3, "%" + status + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setInt(5, IdUser);
                        ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(7, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(idOrder));
                        ps.setString(2, "%" + title + "%");
                        ps.setString(3, "%" + sellerChargeFee + "%");
                        ps.setString(4, "%" + isPublic + "%");
                        ps.setInt(5, IdUser);
                        ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(7, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(idOrder));
                        ps.setString(2, "%" + title + "%");
                        ps.setString(3, "%" + isPublic + "%");
                        ps.setInt(4, IdUser);
                        ps.setInt(5, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(6, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(idOrder));
                        ps.setString(2, "%" + title + "%");
                        ps.setString(3, "%" + sellerChargeFee + "%");
                        ps.setInt(4, IdUser);
                        ps.setInt(5, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(6, RECORD_PER_PAGE);
                    }

                } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                        && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                        && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(moneyValueFrom));
                        ps.setInt(2, Integer.parseInt(moneyValueTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setInt(5, IdUser);
                        ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(7, RECORD_PER_PAGE);
                    } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(moneyValueFrom));
                        ps.setInt(2, Integer.parseInt(moneyValueTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setString(5, "%" + isPublic + "%");
                        ps.setInt(6, IdUser);
                        ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(8, RECORD_PER_PAGE);
                    } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(moneyValueFrom));
                        ps.setInt(2, Integer.parseInt(moneyValueTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setString(5, "%" + sellerChargeFee + "%");
                        ps.setInt(6, IdUser);
                        ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(8, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(moneyValueFrom));
                        ps.setInt(2, Integer.parseInt(moneyValueTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setString(5, "%" + isPublic + "%");
                        ps.setInt(6, IdUser);
                        ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(8, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(moneyValueFrom));
                        ps.setInt(2, Integer.parseInt(moneyValueTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + isPublic + "%");
                        ps.setInt(5, IdUser);
                        ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(7, RECORD_PER_PAGE);

                    } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(moneyValueFrom));
                        ps.setInt(2, Integer.parseInt(moneyValueTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setInt(5, IdUser);
                        ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(7, RECORD_PER_PAGE);
                    }

                } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {

                    if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeCreatedTo));
                        ps.setDate(2, Date.valueOf(timeUpdateFrom));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setInt(5, IdUser);
                        ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(7, RECORD_PER_PAGE);
                    } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeCreatedTo));
                        ps.setDate(2, Date.valueOf(timeUpdateFrom));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setString(5, "%" + isPublic + "%");
                        ps.setInt(6, IdUser);
                        ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(8, RECORD_PER_PAGE);
                    } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeCreatedTo));
                        ps.setDate(2, Date.valueOf(timeUpdateFrom));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setString(5, "%" + sellerChargeFee + "%");
                        ps.setInt(6, IdUser);
                        ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(8, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeCreatedTo));
                        ps.setDate(2, Date.valueOf(timeUpdateFrom));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setString(5, "%" + isPublic + "%");
                        ps.setInt(6, IdUser);
                        ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(8, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeCreatedTo));
                        ps.setDate(2, Date.valueOf(timeUpdateFrom));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + isPublic + "%");
                        ps.setInt(5, IdUser);
                        ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(7, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeCreatedFrom));
                        ps.setDate(2, Date.valueOf(timeCreatedTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setInt(5, IdUser);
                        ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(7, RECORD_PER_PAGE);
                    }

                } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                    if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeUpdateFrom));
                        ps.setDate(2, Date.valueOf(timeUpdateTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setInt(5, IdUser);
                        ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(7, RECORD_PER_PAGE);
                    } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeUpdateFrom));
                        ps.setDate(2, Date.valueOf(timeUpdateTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setString(5, "%" + isPublic + "%");
                        ps.setInt(6, IdUser);
                        ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(8, RECORD_PER_PAGE);
                    } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeUpdateFrom));
                        ps.setDate(2, Date.valueOf(timeUpdateTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setString(5, "%" + sellerChargeFee + "%");
                        ps.setInt(6, IdUser);
                        ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(8, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeUpdateFrom));
                        ps.setDate(2, Date.valueOf(timeUpdateTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setString(5, "%" + isPublic + "%");
                        ps.setInt(6, IdUser);
                        ps.setInt(7, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(8, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeUpdateFrom));
                        ps.setDate(2, Date.valueOf(timeUpdateTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + isPublic + "%");
                        ps.setInt(5, IdUser);
                        ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(7, RECORD_PER_PAGE);
                    } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeUpdateFrom));
                        ps.setDate(2, Date.valueOf(timeUpdateTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setInt(5, IdUser);
                        ps.setInt(6, (page - 1) * RECORD_PER_PAGE);
                        ps.setInt(7, RECORD_PER_PAGE);
                    }
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                int idUser = rs.getInt(2);
                String createdBy = rs.getString(3);
                int orderStatus = rs.getInt(4);
                String orderTitle = rs.getString(5);
                String isSellerFee = rs.getString(6);
                int feeOnSuccess = rs.getInt(7);
                int totalMoneyForBuyer = rs.getInt(8);
                int moneyValue = rs.getInt(9);
                int sellerReceiveSuccess = rs.getInt(10);
                String description = rs.getString(11);
                String hiddenValue = rs.getString(12);
                String contact = rs.getString(13);
                String isPublicOrder = rs.getString(14);
                Timestamp dateCreated = rs.getTimestamp(15);
                Timestamp dateUpdated = rs.getTimestamp(16);
                String linkShare = rs.getString(17);

                MySaleOrder mySaleOrder = new MySaleOrder();
                DecimalFormat decimalFormat = new DecimalFormat("#,###");

                mySaleOrder.setId(id);
                mySaleOrder.setIdUser(idUser);
                mySaleOrder.setCreatedBy(createdBy);
                mySaleOrder.setStatus(mySaleOrder.getStatus(orderStatus));
                mySaleOrder.setTitle(orderTitle);
                mySaleOrder.setIsSellerChargeFee(mySaleOrder.getIsSellerFee(isSellerFee));

                String feeOnSuccessString = String.valueOf(feeOnSuccess);
                mySaleOrder.setFeeOnSuccess(decimalFormat.format(Double.parseDouble(feeOnSuccessString)));

                String moneyForBuyerString = String.valueOf(totalMoneyForBuyer);
                mySaleOrder.setTotalMoneyForBuyer(decimalFormat.format(Double.parseDouble(moneyForBuyerString)));

                String moneyString = String.valueOf(moneyValue);
                mySaleOrder.setMoneyValue(decimalFormat.format(Double.parseDouble(moneyString)));

                String sellerReceivedString = String.valueOf(sellerReceiveSuccess);
                mySaleOrder.setSellerReceivedOnSuccess(decimalFormat.format(Double.parseDouble(sellerReceivedString)));

                mySaleOrder.setDescription(description);
                mySaleOrder.setHiddenValue(hiddenValue);
                mySaleOrder.setContact(contact);
                mySaleOrder.setIsPublic(mySaleOrder.getIsPublic(isPublicOrder));

                mySaleOrder.setCreatedAt(dateCreated);
                mySaleOrder.setUpdatedAt(dateUpdated);
                mySaleOrder.setShareLink(linkShare);

                listMyOrder.add(mySaleOrder);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }

        return listMyOrder;
    }

    public int findTotalRecordBySearch(int IdUser, String idOrder, String status, String buyer, String title, String isPublic, String moneyValueFrom, String moneyValueTo, String sellerChargeFee, String timeCreatedFrom, String timeCreatedTo, String timeUpdateFrom, String timeUpdateTo) {
        String sql = null;
//        boolean isPublicBoolean = convertToBoolean(isPublic);
//        boolean isSellerFeeBoolean = convertToBoolean(sellerChargeFee);

        if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                && !idOrder.isEmpty() && !buyer.isEmpty() && !moneyValueFrom.isEmpty()
                && !moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
            sql = "select count(m.id) from MySaleOrder m\n"
                    + "join User u\n"
                    + "on u.id = m.createdBy\n"
                    + "where m.id = ? and m.title like ? and m.moneyValue BETWEEN ? and ? and m.createdAt >= ? and m.createdAt <= ? and m.updatedAt >= ? and m.updatedAt <= ? and u.id = ?";
        } else {
            if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select count(m.id) from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.id = ? and m.title like ? and u.id = ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                    && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select count(m.id) from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and m.moneyValue BETWEEN ? and ? and u.id = ?";

            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select count(m.id) from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and m.createdAt >= ? and m.createdAt <= ? and u.id = ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                sql = "select count(m.id) from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and m.updatedAt >= ? and m.updatedAt <= ? and u.id = ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && !idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                    && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select count(m.id) from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.id = ? and m.title like ? and m.moneyValue BETWEEN ? and ? and u.id = ?";

            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select count(m.id) from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.id = ? and m.title like ? and m.createdAt >= ? and m.createdAt <= ? and u.id = ?";

            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                sql = "select count(m.id) from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.id = ? and m.title like ? and m.updatedAt >= ? and m.updatedAt <= ? and u.id = ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                    && !moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select count(m.id) from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and m.moneyValue BETWEEN ? and ? and m.createdAt >= ? and m.createdAt <= ? and u.id = ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                    && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                sql = "select count(m.id) from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and m.moneyValue BETWEEN ? and ? and m.updatedAt >= ? and m.updatedAt <= ? and u.id = ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                    && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                sql = "select count(m.id) from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and m.createdAt >= ? and m.createdAt <= ? and m.updatedAt >= ? and m.updatedAt <= ? and u.id = ?";
            } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                sql = "select count(m.id) from MySaleOrder m\n"
                        + "join User u\n"
                        + "on u.id = m.createdBy\n"
                        + "where m.title like ? and u.id = ?";
            } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.status like ? and m.title like ? and u.id = ?";
                } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.status like ? and m.isPublic like ? and m.title like ? and u.id = ?";
                } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.status like ? and m.isSellerChargeFee like ? and m.title like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.isSellerChargeFee like ? and m.isPublic like ? and m.title like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.isPublic like ? and m.title like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.isSellerChargeFee like ? and m.title like ? and u.id = ?";
                }

            } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                    && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {

                if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.id = ? and m.title like ? and m.status like ? and u.id = ?";
                } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.id = ? and m.title like ? and m.status like ? and m.isPublic like ? and u.id = ?";
                } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.id = ? and m.title like ? and m.status like ? and m.isSellerChargeFee like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.id = ? and m.title like ? and m.isSellerChargeFee like ? and m.isPublic like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.id = ? and m.title like ? and m.isPublic like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.id = ? and m.title like ? and m.isSellerChargeFee like ? and u.id = ?";
                }

            } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                    && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                    && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.moneyValue BETWEEN ? and ? and m.title like ? and m.status like ? and u.id = ?";
                } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.moneyValue BETWEEN ? and ? and m.title like ? and m.status like ? and m.isPublic like ? and u.id = ?";
                } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.moneyValue BETWEEN ? and ? and m.title like ? and m.status like ? and m.isSellerChargeFee like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.moneyValue BETWEEN ? and ? and m.title like ? and m.isSellerChargeFee like ? and m.isPublic like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.moneyValue BETWEEN ? and ? and m.title like ? and m.isPublic like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.moneyValue BETWEEN ? and ? and m.title like ? and m.isSellerChargeFee like ? and u.id = ?";
                }

            } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                    && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {

                if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.createdAt >= ? and m.createdAt <= ? and m.title like ? and m.status like ? and u.id = ?";
                } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.createdAt >= ? and m.createdAt <= ? and m.title like ? and m.status like ? and m.isPublic like ? and u.id = ?";
                } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.createdAt >= ? and m.createdAt <= ? and m.title like ? and m.status like ? and m.isSellerChargeFee like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.createdAt >= ? and m.createdAt <= ? and m.title like ? and m.isSellerChargeFee like ? and m.isPublic like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.createdAt >= ? and m.createdAt <= ? and m.title like ? and m.isPublic like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.createdAt >= ? and m.createdAt <= ? and m.title like ? and m.isSellerChargeFee like ? and u.id = ?";
                }

            } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                    && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                    && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                    && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.updatedAt >= ? and m.updatedAt <= ? and m.title like ? and m.status like ? and u.id = ?";
                } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.updatedAt >= ? and m.updatedAt <= ? and m.title like ? and m.status like ? and m.isPublic like ? and u.id = ?";
                } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.updatedAt >= ? and m.updatedAt <= ? and m.title like ? and m.status like ? and m.isSellerChargeFee like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.updatedAt >= ? and m.updatedAt <= ? and m.title like ? and m.isSellerChargeFee like ? and m.isPublic like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.updatedAt >= ? and m.updatedAt <= ? and m.title like ? and m.isPublic like ? and u.id = ?";
                } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                    sql = "select count(m.id) from MySaleOrder m\n"
                            + "join User u\n"
                            + "on u.id = m.createdBy\n"
                            + "where m.updatedAt >= ? and m.updatedAt <= ? and m.title like ? and m.isSellerChargeFee like ? and u.id = ?";
                }

            }
        }

        try {
            ps = dbContext.getConnection().prepareStatement(sql);

            if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                    && !idOrder.isEmpty() && !buyer.isEmpty() && !moneyValueFrom.isEmpty()
                    && !moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                    && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                ps.setInt(1, Integer.parseInt(idOrder));
                ps.setString(2, "%" + title + "%");
                ps.setInt(3, Integer.parseInt(moneyValueFrom));
                ps.setInt(4, Integer.parseInt(moneyValueTo));
                ps.setDate(5, Date.valueOf(timeCreatedFrom));
                ps.setDate(6, Date.valueOf(timeCreatedTo));
                ps.setDate(7, Date.valueOf(timeUpdateFrom));
                ps.setDate(8, Date.valueOf(timeUpdateTo));
                ps.setInt(9, IdUser);

            } else {
                if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setInt(1, Integer.parseInt(idOrder));
                    ps.setString(2, "%" + title + "%");
                    ps.setInt(3, IdUser);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                        && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setInt(2, Integer.parseInt(moneyValueFrom));
                    ps.setInt(3, Integer.parseInt(moneyValueTo));
                    ps.setInt(4, IdUser);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setDate(2, Date.valueOf(timeCreatedFrom));
                    ps.setDate(3, Date.valueOf(timeCreatedTo));
                    ps.setInt(4, IdUser);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setDate(2, Date.valueOf(timeUpdateFrom));
                    ps.setDate(3, Date.valueOf(timeUpdateTo));
                    ps.setInt(4, IdUser);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && !idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                        && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setInt(1, Integer.parseInt(idOrder));
                    ps.setString(2, "%" + title + "%");
                    ps.setInt(3, Integer.parseInt(moneyValueFrom));
                    ps.setInt(4, Integer.parseInt(moneyValueTo));
                    ps.setInt(5, IdUser);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setInt(1, Integer.parseInt(idOrder));
                    ps.setString(2, "%" + title + "%");
                    ps.setDate(3, Date.valueOf(timeCreatedFrom));
                    ps.setDate(4, Date.valueOf(timeCreatedTo));
                    ps.setInt(5, IdUser);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                    ps.setInt(1, Integer.parseInt(idOrder));
                    ps.setString(2, "%" + title + "%");
                    ps.setDate(3, Date.valueOf(timeUpdateFrom));
                    ps.setDate(4, Date.valueOf(timeUpdateTo));
                    ps.setInt(5, IdUser);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                        && !moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setInt(2, Integer.parseInt(moneyValueFrom));
                    ps.setInt(3, Integer.parseInt(moneyValueTo));
                    ps.setDate(4, Date.valueOf(timeCreatedFrom));
                    ps.setDate(5, Date.valueOf(timeCreatedTo));
                    ps.setInt(6, IdUser);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                        && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setInt(2, Integer.parseInt(moneyValueFrom));
                    ps.setInt(3, Integer.parseInt(moneyValueTo));
                    ps.setDate(4, Date.valueOf(timeUpdateFrom));
                    ps.setDate(5, Date.valueOf(timeUpdateTo));
                    ps.setInt(6, IdUser);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                        && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setDate(2, Date.valueOf(timeCreatedFrom));
                    ps.setDate(3, Date.valueOf(timeCreatedTo));
                    ps.setDate(4, Date.valueOf(timeUpdateFrom));
                    ps.setDate(5, Date.valueOf(timeUpdateTo));
                    ps.setInt(6, IdUser);

                } else if (status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1")
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    ps.setString(1, "%" + title + "%");
                    ps.setInt(2, IdUser);

                } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setString(1, "%" + status + "%");
                        ps.setString(2, "%" + title + "%");
                        ps.setInt(3, IdUser);

                    } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(status));
                        ps.setString(2, "%" + isPublic + "%");
                        ps.setString(3, "%" + title + "%");
                        ps.setInt(4, IdUser);

                    } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(status));
                        ps.setString(2, "%" + sellerChargeFee + "%");
                        ps.setString(3, "%" + title + "%");
                        ps.setInt(4, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setString(1, "%" + sellerChargeFee + "%");
                        ps.setString(2, "%" + isPublic + "%");
                        ps.setString(3, "%" + title + "%");
                        ps.setInt(4, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setString(1, "%" + isPublic + "%");
                        ps.setString(2, "%" + title + "%");
                        ps.setInt(3, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setString(1, "%" + sellerChargeFee + "%");
                        ps.setString(2, "%" + title + "%");
                        ps.setInt(3, IdUser);

                    }

                } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                        && !idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {

                    if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(idOrder));
                        ps.setString(2, "%" + title + "%");
                        ps.setString(3, "%" + status + "%");
                        ps.setInt(4, IdUser);

                    } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(idOrder));
                        ps.setString(2, "%" + title + "%");
                        ps.setString(3, "%" + status + "%");
                        ps.setString(4, "%" + isPublic + "%");

                    } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(idOrder));
                        ps.setString(2, "%" + title + "%");
                        ps.setString(3, "%" + status + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setInt(5, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(idOrder));
                        ps.setString(2, "%" + title + "%");
                        ps.setString(3, "%" + sellerChargeFee + "%");
                        ps.setString(4, "%" + isPublic + "%");
                        ps.setInt(5, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(idOrder));
                        ps.setString(2, "%" + title + "%");
                        ps.setString(4, "%" + isPublic + "%");
                        ps.setInt(5, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(idOrder));
                        ps.setString(2, "%" + title + "%");
                        ps.setString(3, "%" + sellerChargeFee + "%");
                        ps.setInt(4, IdUser);

                    }

                } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                        && idOrder.isEmpty() && !moneyValueFrom.isEmpty()
                        && !moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {
                    if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(moneyValueFrom));
                        ps.setInt(2, Integer.parseInt(moneyValueTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setInt(5, IdUser);

                    } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(moneyValueFrom));
                        ps.setInt(2, Integer.parseInt(moneyValueTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setString(5, "%" + isPublic + "%");
                        ps.setInt(6, IdUser);

                    } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(moneyValueFrom));
                        ps.setInt(2, Integer.parseInt(moneyValueTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setString(5, "%" + sellerChargeFee + "%");
                        ps.setInt(6, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(moneyValueFrom));
                        ps.setInt(2, Integer.parseInt(moneyValueTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setString(5, "%" + isPublic + "%");
                        ps.setInt(6, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(moneyValueFrom));
                        ps.setInt(2, Integer.parseInt(moneyValueTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + isPublic + "%");
                        ps.setInt(5, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setInt(1, Integer.parseInt(moneyValueFrom));
                        ps.setInt(2, Integer.parseInt(moneyValueTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setInt(5, IdUser);

                    }

                } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && !timeCreatedTo.isEmpty() && !timeCreatedFrom.isEmpty()
                        && timeUpdateFrom.isEmpty() && timeUpdateTo.isEmpty()) {

                    if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeCreatedTo));
                        ps.setDate(2, Date.valueOf(timeUpdateFrom));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setInt(5, IdUser);

                    } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeCreatedTo));
                        ps.setDate(2, Date.valueOf(timeUpdateFrom));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setString(5, "%" + isPublic + "%");
                        ps.setInt(6, IdUser);

                    } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeCreatedTo));
                        ps.setDate(2, Date.valueOf(timeUpdateFrom));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setString(5, "%" + sellerChargeFee + "%");
                        ps.setInt(6, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeCreatedTo));
                        ps.setDate(2, Date.valueOf(timeUpdateFrom));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setString(5, "%" + isPublic + "%");
                        ps.setInt(6, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeCreatedTo));
                        ps.setDate(2, Date.valueOf(timeUpdateFrom));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + isPublic + "%");
                        ps.setInt(5, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeCreatedFrom));
                        ps.setDate(2, Date.valueOf(timeCreatedTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setInt(5, IdUser);

                    }

                } else if ((!status.equalsIgnoreCase("-1") || !isPublic.equalsIgnoreCase("-1") || !sellerChargeFee.equalsIgnoreCase("-1"))
                        && idOrder.isEmpty() && moneyValueFrom.isEmpty()
                        && moneyValueTo.isEmpty() && timeCreatedTo.isEmpty() && timeCreatedFrom.isEmpty()
                        && !timeUpdateFrom.isEmpty() && !timeUpdateTo.isEmpty()) {
                    if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeUpdateFrom));
                        ps.setDate(2, Date.valueOf(timeUpdateTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setInt(5, IdUser);

                    } else if ((!status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeUpdateFrom));
                        ps.setDate(2, Date.valueOf(timeUpdateTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setString(5, "%" + isPublic + "%");
                        ps.setInt(6, IdUser);

                    } else if ((!status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeUpdateFrom));
                        ps.setDate(2, Date.valueOf(timeUpdateTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + status + "%");
                        ps.setString(5, "%" + sellerChargeFee + "%");
                        ps.setInt(6, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeUpdateFrom));
                        ps.setDate(2, Date.valueOf(timeUpdateTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setString(5, "%" + isPublic + "%");
                        ps.setInt(6, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && !isPublic.equalsIgnoreCase("-1") && sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeUpdateFrom));
                        ps.setDate(2, Date.valueOf(timeUpdateTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + isPublic + "%");
                        ps.setInt(5, IdUser);

                    } else if ((status.equalsIgnoreCase("-1") && isPublic.equalsIgnoreCase("-1") && !sellerChargeFee.equalsIgnoreCase("-1"))) {
                        ps.setDate(1, Date.valueOf(timeUpdateFrom));
                        ps.setDate(2, Date.valueOf(timeUpdateTo));
                        ps.setString(3, "%" + title + "%");
                        ps.setString(4, "%" + sellerChargeFee + "%");
                        ps.setInt(5, IdUser);

                    }
                }
            }

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

    public void updateBalanceUser(int userId, int newBalance) {
        String sql = "UPDATE User\n"
                + "SET balance = ?\n"
                + "WHERE id = ?;";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, newBalance);
            ps.setInt(2, userId);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
    }

}
