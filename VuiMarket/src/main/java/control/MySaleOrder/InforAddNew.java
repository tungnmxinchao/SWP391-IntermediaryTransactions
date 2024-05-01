/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.MySaleOrder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dal.DAO;
import dal.MySaleOrder.MySaleOrderDAO;
import entity.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TNO
 */
public class InforAddNew extends HttpServlet {

    private MySaleOrderDAO mySaleOrderDAO;
    private List listAdd;
    private DeductBalanceHandler deductBalanceHandler;

    public InforAddNew() {
        listAdd = new ArrayList();
        mySaleOrderDAO = new MySaleOrderDAO();
        deductBalanceHandler = new DeductBalanceHandler();
    }

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

        String chude = jsonObject.get("chude").getAsString();
        String giatien = jsonObject.get("giatien").getAsString();
        String lienhe = jsonObject.get("lienhe").getAsString();
        String mota = jsonObject.get("mota").getAsString();
        String noidungan = jsonObject.get("noidungan").getAsString();
        String noidungcongkhai = jsonObject.get("hiencongkhai").getAsString();
        String isSellerFee = jsonObject.get("isSellerFee").getAsString();
        
        DAO dao = new DAO();
        JsonObject responseData = new JsonObject();
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("acc");
        User findUser = dao.findInforUserBuyId(user.getUid());
        if (findUser.getBalance() < 5000) {
            responseData.addProperty("message", "Số dư không đủ để thực hiện!");
        } else {
            String nameUser = user.getUsername();
            int userId = mySaleOrderDAO.findUserIdByUserName(nameUser);

            //tru tien thong qua queue
            deductBalanceHandler.addToQueue(userId);

            mySaleOrderDAO.insertNewOrder(chude, giatien, lienhe, mota, noidungan, noidungcongkhai, isSellerFee, userId);

            responseData.addProperty("message", "Hệ thống ghi nhận đơn hàng!");
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
