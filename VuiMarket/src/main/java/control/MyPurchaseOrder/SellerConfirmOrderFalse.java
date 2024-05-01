/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.MyPurchaseOrder;

import dal.DAO;
import dal.MyPurchaseOrder.MyPurchaseOrderDAO;
import dal.MySaleOrder.MySaleOrderDAO;
import entity.MyPurchaseOrder;
import entity.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author TNO
 */
public class SellerConfirmOrderFalse extends HttpServlet {

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
        
        DAO dao = new DAO();
        MySaleOrderDAO mySaleOrderDAO = new MySaleOrderDAO();
        MyPurchaseOrderDAO myPurchaseOrderDAO = new MyPurchaseOrderDAO();
        MyPurchaseOrder recordOrder = myPurchaseOrderDAO.findRecordByOrderIdAndInforUser(orderId, userId, Integer.parseInt(buyerId));
        
        User inforBuyerInDB = dao.findInforUserBuyId(Integer.parseInt(buyerId));
 
        String cleanedInputMoney = recordOrder.getTotalMoneyForBuyer().replaceAll("[,.]", "");
        int moneyRefundToBuyer = Integer.parseInt(cleanedInputMoney);
        int newBalanceForBuyer = inforBuyerInDB.getBalance() + moneyRefundToBuyer;

        JSONObject jsonResponse = new JSONObject();

        if (recordOrder.getStatus().equalsIgnoreCase("Hủy")) {
            jsonResponse.put("message", "Đơn hàng đã hủy!");
        } else {

            //update status for mysaleorder
            myPurchaseOrderDAO.updatestatusOrderId(recordOrder.getId(), recordOrder.getIdUser(), recordOrder.getIdBuyer(), 3, 1);
            //update status for mypurchaseorder
            myPurchaseOrderDAO.updatestatusOrderId(recordOrder.getId(), recordOrder.getIdUser(), recordOrder.getIdBuyer(), 3, 2);
            //update filed updatedable of order
            myPurchaseOrderDAO.updatestFieldUpdatedableOrder(recordOrder.getId(), "0", 2);
            
            mySaleOrderDAO.updateBalanceUser(Integer.parseInt(buyerId), newBalanceForBuyer);

            jsonResponse.put("message", "Người bán xác nhận đơn hàng sai!");
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
