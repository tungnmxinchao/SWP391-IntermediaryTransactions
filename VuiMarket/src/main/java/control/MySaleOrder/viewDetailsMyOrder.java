/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.MySaleOrder;

import com.google.gson.Gson;
import dal.MySaleOrder.MySaleOrderDAO;
import entity.MySaleOrder;
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
public class viewDetailsMyOrder extends HttpServlet {

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
        String userName = jsonObject.getString("userCreated");
        String userId = jsonObject.getString("userId");

        MySaleOrderDAO mySaleOrderDAO = new MySaleOrderDAO();
        MySaleOrder mySaleOrder = mySaleOrderDAO.findRecordByOrderIdAndInforUser(orderId, userId, userName);

        // Chuyển đối tượng MySaleOrder thành JSON
        Gson gson = new Gson();
        String jsonMySaleOrder = gson.toJson(mySaleOrder);
        

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonMySaleOrder);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
