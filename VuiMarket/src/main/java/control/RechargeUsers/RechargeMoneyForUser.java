/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.RechargeUsers;

import dal.AdminChargeUsers.AdminChargeUsersDAO;
import dal.DAO;
import dal.TransactionHistory.TransactionHistoryDAO;
import entity.RechargeUsers;
import entity.User;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author TNO
 */
public class RechargeMoneyForUser extends HttpServlet {

    private TransactionHistoryDAO transactionHistoryDAO;

    public RechargeMoneyForUser() {
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
        String moneyValue = jsonObject.getString("moneyValue");

        String cleanedStr = moneyValue.replaceAll("[,.]", "");
        int moneyInt = Integer.parseInt(cleanedStr);

        DAO dao = new DAO();
        AdminChargeUsersDAO adminDAO = new AdminChargeUsersDAO();
        JSONObject jsonResponse = new JSONObject();

        RechargeUsers rechargeRecord = adminDAO.findStatusById(Integer.parseInt(orderId));
        String statusOfOrderId = rechargeRecord.getStatus();

        if (statusOfOrderId.equalsIgnoreCase("1")) {
            User inforUserById = dao.findInforUserBuyId(Integer.parseInt(userId));
            int newBalance = inforUserById.getBalance() + moneyInt;

            adminDAO.updateBalanceUser(inforUserById.getUid(), newBalance);
            adminDAO.updateStatusRequest(Integer.parseInt(orderId), 2);

            String description = "Hệ thống chuyển tiền cho người dùng";
            transactionHistoryDAO.insertTransactionHistory(moneyInt, "+", 1, description, inforUserById.getUid());

            jsonResponse.put("message", "Xác nhận đã chuyển tiền cho người dùng!");
        } else {
            jsonResponse.put("message", "Không thể cộng tiền do đơn đã hoành thành!");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
