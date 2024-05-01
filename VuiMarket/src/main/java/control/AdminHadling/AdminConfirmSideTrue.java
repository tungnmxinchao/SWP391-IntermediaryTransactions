/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.AdminHadling;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dal.DAO;
import dal.MyPurchaseOrder.MyPurchaseOrderDAO;
import dal.MySaleOrder.MySaleOrderDAO;
import entity.MyPurchaseOrder;
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
public class AdminConfirmSideTrue extends HttpServlet {

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

        int moneyAfterHandling;

        JSONObject jsonObject = new JSONObject(sb.toString());
        String idOrderHandling = jsonObject.getString("idOrderHandling");
        String idHandlingSeller = jsonObject.getString("idHandlingSeller");
        String idHandlingBuyer = jsonObject.getString("idHandlingBuyer");
        String correctSide = jsonObject.getString("correctSide");

        DAO dao = new DAO();
        MySaleOrderDAO myOrderDAO = new MySaleOrderDAO();
        MyPurchaseOrderDAO myPurchaseOrderDAO = new MyPurchaseOrderDAO();

        MyPurchaseOrder recordOrder = myPurchaseOrderDAO.findRecordByOrderIdAndInforUser(idOrderHandling, idHandlingSeller, Integer.parseInt(idHandlingBuyer));

        String cleanedInputMoney = recordOrder.getTotalMoneyForBuyer().replaceAll("[,.]", "");
        int moneyRefundToBuyer = Integer.parseInt(cleanedInputMoney);

        User inforBuyerById = dao.findInforUserBuyId(Integer.parseInt(idHandlingBuyer));
        int balanceBuyerAfterRefund = inforBuyerById.getBalance() + moneyRefundToBuyer;

        if (correctSide.equalsIgnoreCase("1")) {

            int balanceOfBuyer = inforBuyerById.getBalance();
            moneyAfterHandling = balanceOfBuyer - 50000;
            myOrderDAO.updateBalanceUser(Integer.parseInt(idHandlingBuyer), moneyAfterHandling);
        } else {
            User inforSellerById = dao.findInforUserBuyId(Integer.parseInt(idHandlingSeller));
            int balanceOfSeller = inforSellerById.getBalance();
            moneyAfterHandling = balanceOfSeller - 50000;
            myOrderDAO.updateBalanceUser(Integer.parseInt(idHandlingSeller), moneyAfterHandling);
            myOrderDAO.updateBalanceUser(Integer.parseInt(idHandlingBuyer), balanceBuyerAfterRefund);
        }
        JsonObject responseData = new JsonObject();

        if (recordOrder.getStatus().equalsIgnoreCase("Hoàn thành")) {
            responseData.addProperty("message", "Đơn đã được xử lý trước đó!");
        } else {
            //update status for mysaleorder
            myPurchaseOrderDAO.updatestatusOrderId(recordOrder.getId(), recordOrder.getIdUser(), recordOrder.getIdBuyer(), 4, 1);
            //update status for mypurchaseorder
            myPurchaseOrderDAO.updatestatusOrderId(recordOrder.getId(), recordOrder.getIdUser(), recordOrder.getIdBuyer(), 4, 2);
            responseData.addProperty("message", "Xử lý đơn hàng thành công!");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(responseData));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
