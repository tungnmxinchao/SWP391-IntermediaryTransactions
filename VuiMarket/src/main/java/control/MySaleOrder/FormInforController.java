/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.MySaleOrder;

import dal.DAO;
import dal.MySaleOrder.MySaleOrderDAO;
import entity.MySaleOrder;
import entity.User;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TNO
 */
public class FormInforController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        String userId = request.getParameter("userId");

        DAO dao = new DAO();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("acc");

        MySaleOrderDAO myOrderDAO = new MySaleOrderDAO();
        MySaleOrder RecordMyOrder = myOrderDAO.findRecordByOrderIdAndInforUser(orderId, userId, "");

        if (RecordMyOrder.getUpdatedable().equalsIgnoreCase("0") && user == null) {
            request.getRequestDispatcher("Home.jsp").forward(request, response);
            return;
        }
        if (user == null) {
            session.setAttribute("RecordMyOrder", RecordMyOrder);
            request.getRequestDispatcher("FormInfor.jsp").forward(request, response);
            return;
        }
        User userDB = dao.findInforUserBuyId(user.getUid());
        int moneyUser = userDB.getBalance();
        String moneyString = decimalFormat.format(Double.parseDouble(String.valueOf(moneyUser)));

        request.setAttribute("balanceUser", moneyString);
        request.setAttribute("UserAcess", user);
        session.setAttribute("RecordMyOrder", RecordMyOrder);
        request.getRequestDispatcher("FormInfor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
