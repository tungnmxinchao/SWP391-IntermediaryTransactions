/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.MyPurchaseOrder;

import com.google.gson.Gson;
import dal.DAO;
import dal.MyPurchaseOrder.MyPurchaseOrderDAO;
import dal.TransactionHistory.TransactionHistoryDAO;
import entity.MyPurchaseOrder;
import entity.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author TNO
 */
public class ConFirmOrder extends HttpServlet {

    private TransactionHistoryDAO transactionHistoryDAO;

    public ConFirmOrder() {
        transactionHistoryDAO = new TransactionHistoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        JSONObject jsonObject = new JSONObject(sb.toString());
        String orderId = jsonObject.getString("orderId");
        String userId = jsonObject.getString("userId");
        String buyerId = jsonObject.getString("buyerId");

        MyPurchaseOrderDAO myPurchaseOrderDAO = new MyPurchaseOrderDAO();
        MyPurchaseOrder recordOrder = myPurchaseOrderDAO.findRecordByOrderIdAndInforUser(orderId, userId, Integer.parseInt(buyerId));

        JSONObject jsonResponse = new JSONObject();

        if (recordOrder.getStatus().equalsIgnoreCase("Hoàn thành") || recordOrder.getStatus().equalsIgnoreCase("Bên mua khiếu nại sản phẩm")) {
            jsonResponse.put("message", "Không thể khiếu nại do đơn đã hoàn thành!");
        } else {

            //update status for mysaleorder
            myPurchaseOrderDAO.updatestatusOrderId(recordOrder.getId(), recordOrder.getIdUser(), recordOrder.getIdBuyer(), 4, 1);
            //update status for mypurchaseorder
            myPurchaseOrderDAO.updatestatusOrderId(recordOrder.getId(), recordOrder.getIdUser(), recordOrder.getIdBuyer(), 4, 2);

            DAO dao = new DAO();

            User inforSeller = dao.findInforUserBuyId(recordOrder.getIdUser());
            User inforBuyer = dao.findInforUserBuyId(recordOrder.getIdBuyer());

            int sellerReceivedOnSuccess = myPurchaseOrderDAO.convertToInteger(recordOrder.getSellerReceivedOnSuccess());
            int totalMoneyForBuyer = myPurchaseOrderDAO.convertToInteger(recordOrder.getTotalMoneyForBuyer());

            int sellerReceive = inforSeller.getBalance() + sellerReceivedOnSuccess;
            int moneyForBuyer = inforBuyer.getBalance() - totalMoneyForBuyer;

            //update balance for seller
            myPurchaseOrderDAO.updateBalanceUser(moneyForBuyer, sellerReceive, inforBuyer, inforSeller, 2);
            String description = "Hệ thống chuyển tiền đơn hoàn thành với mã:" + orderId;
            transactionHistoryDAO.insertTransactionHistory(sellerReceivedOnSuccess, "+", 1, description, inforSeller.getUid());

            //update filed updatedable of order
            myPurchaseOrderDAO.updatestFieldUpdatedableOrder(recordOrder.getId(), "0", 2);

            jsonResponse.put("message", "Người mua xác nhận đơn hàng đúng mô tả !");
        }
        jsonResponse.put("IdUser", Integer.parseInt(userId));
        jsonResponse.put("buyerId", Integer.parseInt(buyerId));
        jsonResponse.put("confirmOrder", new JSONObject(recordOrder)); // Chuyển đối tượng MyPurchaseOrder thành JSONObject

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
