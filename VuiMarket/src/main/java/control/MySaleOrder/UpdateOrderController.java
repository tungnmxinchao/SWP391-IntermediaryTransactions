/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.MySaleOrder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dal.MySaleOrder.MySaleOrderDAO;
import entity.MySaleOrder;
import entity.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TNO
 */
public class UpdateOrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String json = sb.toString();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        String idOrder = jsonObject.get("idOrder").getAsString();
        String title = jsonObject.get("chude").getAsString();
        String isSeller = jsonObject.get("isSellerFee").getAsString();
        String contact = jsonObject.get("contact").getAsString();
        String moneyValue = jsonObject.get("moneyValue").getAsString();
        String isPublic = jsonObject.get("isPublic").getAsString();
        String description = jsonObject.get("description").getAsString();
        String hideenValue = jsonObject.get("hiddenValue").getAsString();

        MySaleOrderDAO myOderDAO = new MySaleOrderDAO();
        JsonObject responseData = new JsonObject();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");

        MySaleOrder recordOrder = myOderDAO.findRecordByOrderIdAndInforUser(idOrder, String.valueOf(user.getUid()), "");
        if (!recordOrder.getStatus().equalsIgnoreCase("Sẵn sàng giao dịch ")) {
            responseData.addProperty("message", "Không thể cập nhật!");
        } else {
            myOderDAO.updateOrder(idOrder, title, isSeller, contact, moneyValue, isPublic, description, hideenValue);
            responseData.addProperty("message", "Cập nhật thành công!");
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
