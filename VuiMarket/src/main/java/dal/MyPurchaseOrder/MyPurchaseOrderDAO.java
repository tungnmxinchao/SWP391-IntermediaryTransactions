/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.MyPurchaseOrder;

import static constant.constant.RECORD_PER_PAGE;
import context.DBContext;
import dal.DAO;
import entity.MyPurchaseOrder;
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
public class MyPurchaseOrderDAO {

    PreparedStatement ps;
    ResultSet rs;
    private User user;
    private List<MyPurchaseOrder> listMyPurchaseOrder;
    private DAO dao;
    private DBContext dbContext;

    public MyPurchaseOrderDAO() {
        user = new User();
        listMyPurchaseOrder = new LinkedList<>();
        dbContext = DBContext.getInstance();
        dao = new DAO();
    }

    public int findTotalRecord(int userId) {
        String sql = "select count(m.id) from MyPurchaseOrder m\n"
                + "WHERE m.buyerId = ?";
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

//    public static void main(String[] args) {
//        MyPurchaseOrderDAO dao = new MyPurchaseOrderDAO();
//        List<MyPurchaseOrder> list = dao.findByPage(1, 3);
//        for (MyPurchaseOrder o : list) {
//            System.out.println(o);
//        }
//    }
    public List<MyPurchaseOrder> findByPage(int page, int userId) {

        String sql = "select m.id, m.createdBy, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, \n"
                + "                m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, \n"
                + "                m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink, m.buyerId from MyPurchaseOrder m\n"
                + "                where m.buyerId = ?\n"
                + "                Order by m.id\n"
                + "                LIMIT ?, ?";

        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, (page - 1) * RECORD_PER_PAGE);
            ps.setInt(3, RECORD_PER_PAGE);
            rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt(1);
                int idSeller = rs.getInt(2);
                int status = rs.getInt(3);
                String title = rs.getString(4);
                String isSellerFee = rs.getString(5);
                int feeOnSuccess = rs.getInt(6);
                int totalMoneyForBuyer = rs.getInt(7);
                int moneyValue = rs.getInt(8);
                int sellerReciveSuccess = rs.getInt(9);
                String description = rs.getString(10);
                String hiddentValue = rs.getString(11);
                String contact = rs.getString(12);
                String isPublic = rs.getString(13);
                Timestamp date_created = rs.getTimestamp(14);
                Timestamp date_updated = rs.getTimestamp(15);
                String linkShare = rs.getString(16);
                int buyerId = rs.getInt(17);
//                String updatedable = rs.getString(19);

                MyPurchaseOrder myPurchaseOrder = new MyPurchaseOrder();
                DecimalFormat decimalFormat = new DecimalFormat("#,###");

                myPurchaseOrder.setId(id);

                if (idSeller != 0) {
                    User seller_Id = dao.findInforUserBuyId(idSeller);
                    String sellerName = seller_Id.getFullname();
                    myPurchaseOrder.setIdUser(seller_Id.getUid());
                    myPurchaseOrder.setCreatedBy(sellerName);
                }
//                mySaleOrder.setIdUser(idUser);
//                mySaleOrder.setCreatedBy(createdBy);

                myPurchaseOrder.setStatus(myPurchaseOrder.getStatus(status));

                myPurchaseOrder.setTitle(title);
                myPurchaseOrder.setIsSellerChargeFee(myPurchaseOrder.getIsSellerFee(isSellerFee));

                String feeOnSuccessString = String.valueOf(feeOnSuccess);
                myPurchaseOrder.setFeeOnSuccess(decimalFormat.format(Double.parseDouble(feeOnSuccessString)));

                String moneyForBuyerString = String.valueOf(totalMoneyForBuyer);
                myPurchaseOrder.setTotalMoneyForBuyer(decimalFormat.format(Double.parseDouble(moneyForBuyerString)));

                String moneyString = String.valueOf(moneyValue);
                myPurchaseOrder.setMoneyValue(decimalFormat.format(Double.parseDouble(moneyString)));

                String sellerRecivedString = String.valueOf(sellerReciveSuccess);
                myPurchaseOrder.setSellerReceivedOnSuccess(decimalFormat.format(Double.parseDouble(sellerRecivedString)));

                myPurchaseOrder.setDescription(description);
                myPurchaseOrder.setHiddenValue(hiddentValue);
                myPurchaseOrder.setContact(contact);

                myPurchaseOrder.setIsPublic(myPurchaseOrder.getIsPublic(isPublic));

                myPurchaseOrder.setCreatedAt(date_created);
                myPurchaseOrder.setUpdatedAt(date_updated);
                myPurchaseOrder.setShareLink(linkShare);

                if (buyerId != 0) {
                    User Buyer_Id = dao.findInforUserBuyId(userId);
                    String buyerName = Buyer_Id.getFullname();
                    myPurchaseOrder.setIdBuyer(buyerId);
                    myPurchaseOrder.setBuyer(buyerName);
                }

//                mySaleOrder.setUpdatedable(updatedable);
                listMyPurchaseOrder.add(myPurchaseOrder);

            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return listMyPurchaseOrder;
    }

    public MyPurchaseOrder findRecordByOrderIdAndInforUser(String orderId, String userId, int idBuyer) {
        String sql = "select m.id, u.id, u.fullname, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, "
                + "m.totalMoneyForBuyer, m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, "
                + "m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink, m.customerCanComplain from MySaleOrder m\n"
                + "join User u \n"
                + "on u.id = m.createdBy\n"
                + "where m.id = ? and m.createdBy = ?";
        MyPurchaseOrder myPurchaseOrder = new MyPurchaseOrder();
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
            String customerComlain = rs.getString(18);

            myPurchaseOrder.setId(id);
            myPurchaseOrder.setIdUser(idUser);
            myPurchaseOrder.setCreatedBy(createdBy);
            myPurchaseOrder.setIdBuyer(idBuyer);

            myPurchaseOrder.setStatus(myPurchaseOrder.getStatus(status));

            myPurchaseOrder.setTitle(title);
            myPurchaseOrder.setIsSellerChargeFee(myPurchaseOrder.getIsSellerFee(isSellerFee));

            String feeOnSuccessString = String.valueOf(feeOnSuccess);
            myPurchaseOrder.setFeeOnSuccess(decimalFormat.format(Double.parseDouble(feeOnSuccessString)));

            String moneyForBuyerString = String.valueOf(totalMoneyForBuyer);
            myPurchaseOrder.setTotalMoneyForBuyer(decimalFormat.format(Double.parseDouble(moneyForBuyerString)));

            String moneyString = String.valueOf(moneyValue); // Chuyển đổi từ int sang chuỗi
            myPurchaseOrder.setMoneyValue(decimalFormat.format(Double.parseDouble(moneyString)));

            String sellerRecivedString = String.valueOf(sellerReciveSuccess);
            myPurchaseOrder.setSellerReceivedOnSuccess(decimalFormat.format(Double.parseDouble(sellerRecivedString)));

            myPurchaseOrder.setDescription(description);
            myPurchaseOrder.setHiddenValue(hiddentValue);
            myPurchaseOrder.setContact(contact);

            myPurchaseOrder.setIsPublic(myPurchaseOrder.getIsPublic(isPublic));

            myPurchaseOrder.setCreatedAt(date_created);
            myPurchaseOrder.setUpdatedAt(date_updated);
            myPurchaseOrder.setShareLink("http://localhost:8080/VuiMarket/formInfor?orderId=" + id + "&userId=" + idUser);
            myPurchaseOrder.setCustomerCanComplain(customerComlain);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return myPurchaseOrder;
    }

    public MyPurchaseOrder findRecordByOrderIdAndInforUsers(String orderId, String sellerId, int idBuyer) {
        String sql = "select m.id, m.createdBy, m.buyerId, m.status, m.title, m.isSellerChargeFee, m.feeOnSuccess, m.totalMoneyForBuyer\n"
                + ", m.moneyValue, m.sellerReceivedOnSuccess, m.description, m.hiddenValue, m.contact, m.isPublic, m.createdAt, m.updatedAt, m.shareLink,\n"
                + "m.customerCanComplain \n"
                + "from MyPurchaseOrder m\n"
                + "where m.buyerId = ? and m.id = ?";
        MyPurchaseOrder myPurchaseOrder = new MyPurchaseOrder();
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, idBuyer);
            ps.setInt(2, Integer.parseInt(orderId));
            rs = ps.executeQuery();
            DecimalFormat decimalFormat = new DecimalFormat("#,###");

            rs.next();
            int id = rs.getInt(1);
            int id_seller = rs.getInt(2);
            int id_Buyer = rs.getInt(3);
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
            String customerComlain = rs.getString(18);

            myPurchaseOrder.setId(id);
            myPurchaseOrder.setIdUser(id_seller);

            if (id_seller != 0) {
                User idSeller = dao.findInforUserBuyId(id_seller);
                String sellerName = idSeller.getFullname();
                myPurchaseOrder.setIdUser(id_seller);
                myPurchaseOrder.setCreatedBy(sellerName);
            }

            if (id_Buyer != 0) {
                User inforBuyer = dao.findInforUserBuyId(id_Buyer);
                String buyerName = inforBuyer.getFullname();
                myPurchaseOrder.setIdBuyer(id_Buyer);
                myPurchaseOrder.setBuyer(buyerName);
            }

            myPurchaseOrder.setStatus(myPurchaseOrder.getStatus(status));

            myPurchaseOrder.setTitle(title);
            myPurchaseOrder.setIsSellerChargeFee(myPurchaseOrder.getIsSellerFee(isSellerFee));

            String feeOnSuccessString = String.valueOf(feeOnSuccess);
            myPurchaseOrder.setFeeOnSuccess(decimalFormat.format(Double.parseDouble(feeOnSuccessString)));

            String moneyForBuyerString = String.valueOf(totalMoneyForBuyer);
            myPurchaseOrder.setTotalMoneyForBuyer(decimalFormat.format(Double.parseDouble(moneyForBuyerString)));

            String moneyString = String.valueOf(moneyValue); // Chuyển đổi từ int sang chuỗi
            myPurchaseOrder.setMoneyValue(decimalFormat.format(Double.parseDouble(moneyString)));

            String sellerRecivedString = String.valueOf(sellerReciveSuccess);
            myPurchaseOrder.setSellerReceivedOnSuccess(decimalFormat.format(Double.parseDouble(sellerRecivedString)));

            myPurchaseOrder.setDescription(description);
            myPurchaseOrder.setHiddenValue(hiddentValue);
            myPurchaseOrder.setContact(contact);

            myPurchaseOrder.setIsPublic(myPurchaseOrder.getIsPublic(isPublic));

            myPurchaseOrder.setCreatedAt(date_created);
            myPurchaseOrder.setUpdatedAt(date_updated);
            myPurchaseOrder.setShareLink("http://localhost:8080/VuiMarket/formInfor?orderId=" + id + "&userId=" + id_seller);
            myPurchaseOrder.setCustomerCanComplain(customerComlain);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return myPurchaseOrder;
    }

    public void insertNewOrder(MyPurchaseOrder myPurchaseOrder) {

        String sql = "INSERT INTO MyPurchaseOrder\n"
                + "           (id\n"
                + "           ,title\n"
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
                + "           ,buyerId\n"
                + "           ,customerCanComplain)\n"
                + "     VALUES\n"
                + "           (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, myPurchaseOrder.getId());
            ps.setString(2, myPurchaseOrder.getTitle());
            ps.setInt(3, myPurchaseOrder.getIdUser());
            ps.setInt(4, getStatusReverse(myPurchaseOrder.getStatus()));
            ps.setString(5, getIsSellerFeeReverse(myPurchaseOrder.getIsSellerChargeFee()));
            ps.setInt(6, convertToInteger(myPurchaseOrder.getFeeOnSuccess()));
            ps.setInt(7, convertToInteger(myPurchaseOrder.getTotalMoneyForBuyer()));
            ps.setInt(8, convertToInteger(myPurchaseOrder.getMoneyValue()));
            ps.setInt(9, convertToInteger(myPurchaseOrder.getSellerReceivedOnSuccess()));
            ps.setString(10, myPurchaseOrder.getDescription());
            ps.setString(11, myPurchaseOrder.getHiddenValue());
            ps.setString(12, myPurchaseOrder.getContact());
            ps.setString(13, getIsPublic(myPurchaseOrder.getIsPublic()));
            ps.setInt(14, myPurchaseOrder.getIdBuyer());
            ps.setString(15, "1");
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
    }

    public void updateStatusAndBuyer(int status, int idBuyer, String orderId, String updatedable, int mode) {
        String sql = null;
        switch (mode) {
            case 1:
                sql = "UPDATE MyPurchaseOrder m\n"
                        + "SET \n"
                        + "   m.status = ?\n"
                        + "WHERE m.id = ?;";
                try {
                    ps = dbContext.getConnection().prepareStatement(sql);
                    ps.setInt(1, status);
                    ps.setInt(2, Integer.parseInt(orderId));

                    ps.executeUpdate();

                } catch (SQLException e) {
                    System.out.println(e);
                } finally {
                    closeResultSetAndStatement(rs, ps);
                }
                break;
            case 2:
                sql = "UPDATE MySaleOrder m\n"
                        + "SET \n"
                        + "   m.status = ?,\n"
                        + "   m.buyerId = ?,\n"
                        + "   m.updatedable = ?\n"
                        + "WHERE m.id = ?;";

                try {
                    ps = dbContext.getConnection().prepareStatement(sql);
                    ps.setInt(1, status);
                    ps.setInt(2, idBuyer);
                    ps.setString(3, updatedable);
                    ps.setInt(4, Integer.parseInt(orderId));

                    ps.executeUpdate();

                } catch (SQLException e) {
                    System.out.println(e);
                } finally {
                    closeResultSetAndStatement(rs, ps);
                }
                break;
            default:
                throw new AssertionError();
        }
    }

    public int convertToInteger(String moneyValue) {
        String moneyValueWithoutSymbols = moneyValue.replaceAll("[,.]", "");

        int moneyValueInt = Integer.parseInt(moneyValueWithoutSymbols);

        return moneyValueInt;
    }

    public int getStatusReverse(String statusString) {
        switch (statusString) {
            case "Mới Tạo":
                return 0;
            case "Sẵn sàng giao dịch ":
                return 1;
            case "Hủy":
                return 2;
            case "Hoàn thành":
                return 3;
            default:
                throw new IllegalArgumentException("Invalid status: " + statusString);
        }
    }

    public String getIsSellerFeeReverse(String isSellerFeeString) {
        if (isSellerFeeString.equalsIgnoreCase("Bên Mua")) {
            return "0";
        } else if (isSellerFeeString.equalsIgnoreCase("Bên Bán")) {
            return "1";
        } else {
            throw new IllegalArgumentException("Invalid isSellerFee: " + isSellerFeeString);
        }
    }

    public String getIsPublic(String isPublic) {
        if (isPublic.equalsIgnoreCase("Công Khai")) {
            return "1";
        }
        return "0";

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
                System.out.println(e + "ham mypurchase");
            }
        }

    }

    public void updatestatusOrderId(int id, int idUser, int idBuyer, int status, int mode) {
        String sql = null;
        switch (mode) {
            case 1:
                sql = "UPDATE MyPurchaseOrder m\n"
                        + "SET \n"
                        + "   m.status = ?\n"
                        + "WHERE m.id = ?;";
                break;
            case 2:
                sql = "UPDATE MySaleOrder m\n"
                        + "SET \n"
                        + "   m.status = ?\n"
                        + "WHERE m.id = ?;";
                break;
            default:
                throw new AssertionError();
        }
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }

    }

    public void updateBalanceUser(User inforUserById) {
        String sql = "UPDATE User\n"
                + "SET balance = ?\n"
                + "WHERE id = ?;";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, (inforUserById.getBalance() - 5000));
            ps.setInt(2, inforUserById.getUid());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
    }

    public void updateBalanceUser(int moneyForBuyer, int sellerReceive, User inforBuyer, User inforSeller, int mode) {
        String sql = "UPDATE User\n"
                + "SET balance = ?\n"
                + "WHERE id = ?;";
        switch (mode) {
            case 1:
                try {
                ps = dbContext.getConnection().prepareStatement(sql);
                ps.setInt(1, moneyForBuyer);
                ps.setInt(2, inforBuyer.getUid());

                ps.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                closeResultSetAndStatement(rs, ps);
            }
            break;
            case 2:
                try {
                ps = dbContext.getConnection().prepareStatement(sql);
                ps.setInt(1, sellerReceive);
                ps.setInt(2, inforSeller.getUid());

                ps.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                closeResultSetAndStatement(rs, ps);
            }
            break;
            default:
                throw new AssertionError();
        }
    }

    public void updatestFieldUpdatedableOrder(int idOrder, String updatedable, int mode) {
        String sql = null;
        switch (mode) {
            case 1:
                sql = "UPDATE MyPurchaseOrder m\n"
                        + "SET \n"
                        + "   m.updatedable = ?\n"
                        + "WHERE m.id = ?;";
                break;
            case 2:
                sql = "UPDATE MySaleOrder m\n"
                        + "SET \n"
                        + "   m.updatedable = ?\n"
                        + "WHERE m.id = ?;";
                break;
            default:
                throw new AssertionError();
        }
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, idOrder);
            ps.setString(2, updatedable);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
    }

}
