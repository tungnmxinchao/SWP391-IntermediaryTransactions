/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;
import utils.Encode;

/**
 *
 * @author pc
 */
public class PasswordRecoveryControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();
        String code = request.getParameter("code");
        String username = dao.getUsernameByConfirmationCode(code);
        request.setAttribute("codeConfirm", code);
        request.setAttribute("username", username);
        request.getRequestDispatcher("PasswordRecovery.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String code = request.getParameter("code");
        String username = dao.getUsernameByConfirmationCode(code);

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
            doGet(request, response);
        } else {

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("mess", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
                doGet(request, response);
            } else {
                newPassword = Encode.toSHA1(newPassword);
            }

            boolean success = dao.passwordRecovery(username, newPassword);
            if (success) {
                request.setAttribute("message", "Đổi mật khẩu thành công!");
            }

            response.sendRedirect("login");
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
