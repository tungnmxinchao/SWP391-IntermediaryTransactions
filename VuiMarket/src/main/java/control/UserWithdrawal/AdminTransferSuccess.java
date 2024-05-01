/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.UserWithdrawal;

import dal.DAO;
import dal.UserWithdrawal.UserWithdrawalDAO;
import entity.UserWithdrawl;
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
 * @author pc
 */
public class AdminTransferSuccess extends HttpServlet {

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

        UserWithdrawalDAO userWithdrawalDAO = new UserWithdrawalDAO();
        JSONObject jsonResponse = new JSONObject();

        UserWithdrawl withdrawlRecord = userWithdrawalDAO.findStatusById(Integer.parseInt(orderId));
        String statusOfOrderId = withdrawlRecord.getStatus();

        if (statusOfOrderId.equalsIgnoreCase("1")) {
            userWithdrawalDAO.updateStatusRequest(Integer.parseInt(orderId), 2);

            jsonResponse.put("message", "Chuyển tiền thành công!");
        } else {
            jsonResponse.put("message", "Không thể chuyển tiền do đơn đã hoành thành!");
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
