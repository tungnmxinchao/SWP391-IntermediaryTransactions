/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control;

import dal.DAO;
import entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

/**
 *
 * @author pc
 */
public class ConfirmAccountControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();
        String code = request.getParameter("code");
        String username = dao.getUsernameByConfirmationCode(code);
        request.setAttribute("codeConfirm", code);
        request.setAttribute("username", username);
        request.getRequestDispatcher("confirmAccount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        String capcha = request.getParameter("capcha");
        String capchaId_user = request.getParameter("capchaId");

        //connect to cache jedis
        Jedis jedis = new Jedis("localhost", 6379);
        //get key capcha
        String getIdCapcha = jedis.get(capchaId_user);
        //get expiration time of capcha
        String expirationTimeString = jedis.get("expirationTime");
        //parsing string capcha to Long type
        Long expirationTime = Long.parseLong(expirationTimeString);

        if (!(capcha.equalsIgnoreCase(getIdCapcha)) || System.currentTimeMillis() > expirationTime) {
            request.setAttribute("mess", "Captcha chưa chính xác!");
            jedis.del(capchaId_user);
            request.getRequestDispatcher("confirmAccount.jsp").forward(request, response);
        } else {
            DAO dao = new DAO();
            // Kiểm tra mã xác nhận và kích hoạt tài khoản
            if (dao.confirmAccount(code)) {
                response.sendRedirect("Login.jsp");
            }
        }
        //delete capcha if user login successfully
        jedis.del(capchaId_user);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
