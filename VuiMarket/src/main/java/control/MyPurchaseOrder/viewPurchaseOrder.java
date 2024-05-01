/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package control.MyPurchaseOrder;

import com.google.gson.Gson;
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
public class viewPurchaseOrder extends HttpServlet {

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
        String orderId = jsonObject.getString("id");
        String sellerId = jsonObject.getString("sellerId");
        String buyerId = jsonObject.getString("buyerId");
        
        MyPurchaseOrderDAO myPurchaseDAO = new MyPurchaseOrderDAO();
        MyPurchaseOrder myOrder = myPurchaseDAO.findRecordByOrderIdAndInforUsers(orderId, sellerId, Integer.parseInt(buyerId));
        
        Gson gson = new Gson();
        String jsonMyOrder = gson.toJson(myOrder);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonMyOrder);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
