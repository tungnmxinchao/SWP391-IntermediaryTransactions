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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author TNO
 */
public class InforBuyerController extends HttpServlet {

    private TransactionHistoryDAO transactionHistoryDAO;

    public InforBuyerController() {
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
        String orderId = jsonObject.getString("idOrder");
        String userId = jsonObject.getString("idUser");

        DAO dao = new DAO();
        HttpSession session = request.getSession();
        User userBuyer = (User) session.getAttribute("acc");
        if (userBuyer == null) {
            response.sendRedirect("Login.jsp");
            return;
        }
        int idBuyer = userBuyer.getUid();

        User idBuyerInDB = dao.findInforUserBuyId(idBuyer);
        int moneyBuyer = idBuyerInDB.getBalance();

        MyPurchaseOrderDAO myPurchaseOrderDAO = new MyPurchaseOrderDAO();
        MyPurchaseOrder myPurchaseOrder = myPurchaseOrderDAO.findRecordByOrderIdAndInforUser(orderId, userId, idBuyer);

        JSONObject jsonResponse = new JSONObject();

        if (myPurchaseOrder.getStatus().equalsIgnoreCase("Bên mua đang kiểm tra hàng")) {
            jsonResponse.put("message", "Không thể mua do đơn hàng đã được mua trước đó!");
        } else {
            String moneyForBuyer = myPurchaseOrder.getTotalMoneyForBuyer();
            String cleanedStr = moneyForBuyer.replaceAll("[,.]", "");
            int moneyIntForBuyer = moneyBuyer - Integer.parseInt(cleanedStr);

            myPurchaseOrderDAO.updateBalanceUser(moneyIntForBuyer, 0, userBuyer, userBuyer, 1);

            myPurchaseOrderDAO.insertNewOrder(myPurchaseOrder);
            myPurchaseOrderDAO.updateStatusAndBuyer(2, idBuyer, orderId, "0", 2);
            myPurchaseOrderDAO.updateStatusAndBuyer(2, idBuyer, orderId, "0", 1);

            String description = "Thanh toán đơn hàng mã số:" + orderId;

            transactionHistoryDAO.insertTransactionHistory(Integer.parseInt(cleanedStr), "-", 1, description, idBuyer);

            jsonResponse.put("message", "Hoàn thành xử lý giao dịch");
        }

        jsonResponse.put("myPurchaseOrder", new JSONObject(myPurchaseOrder));
        jsonResponse.put("IdUser", Integer.parseInt(userId));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
