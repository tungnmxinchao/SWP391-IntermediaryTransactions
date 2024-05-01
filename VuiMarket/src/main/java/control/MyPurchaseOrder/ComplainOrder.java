/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.MyPurchaseOrder;

import dal.MyPurchaseOrder.MyPurchaseOrderDAO;
import entity.MyPurchaseOrder;
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
public class ComplainOrder extends HttpServlet {

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

        if (recordOrder.getStatus().equalsIgnoreCase("Bên mua khiếu nại sản phẩm") || recordOrder.getStatus().equalsIgnoreCase("Hoàn thành")) {
            jsonResponse.put("message", "Không thể thực hiện!");
        } else {

            //update status for mysaleorder
            myPurchaseOrderDAO.updatestatusOrderId(recordOrder.getId(), recordOrder.getIdUser(), recordOrder.getIdBuyer(), 5, 1);
            //update status for mypurchaseorder
            myPurchaseOrderDAO.updatestatusOrderId(recordOrder.getId(), recordOrder.getIdUser(), recordOrder.getIdBuyer(), 5, 2);

            jsonResponse.put("message", "Người mua Khiếu nại sản phẩm không đúng mô tả !");

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
