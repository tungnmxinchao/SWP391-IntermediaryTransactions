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
import javax.servlet.http.HttpSession;
import redis.clients.jedis.Jedis;
import utils.Encode;

/**
 *
 * @author W10.TQ
 */
public class LoginControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String capcha = request.getParameter("capcha");
        String capchaId_user = request.getParameter("capchaId");

        password = Encode.toSHA1(password);

        DAO dao = new DAO();
        //connect to cache jedis
        Jedis jedis = new Jedis("localhost", 6379);
        User a = dao.login(username, password);
        //get key capcha
//        String capchaId = jedis.get("capchaVuiMarket");
        String getIdCapcha = jedis.get(capchaId_user);
        //get expiration time of capcha
        String expirationTimeString = jedis.get("expirationTime");
        //parsing string capcha to Long type
        Long expirationTime = Long.parseLong(expirationTimeString);
        //check if username,password or capcha is not match or Expired captcha
        if (a == null || !(capcha.equalsIgnoreCase(getIdCapcha)) || System.currentTimeMillis() > expirationTime) {

            request.setAttribute("mess", "Username, mật khẩu hoặc captcha chưa chính xác!");
            jedis.del(capchaId_user);
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else {
            // Check if the account is confirmed
            if (a.isConfirmed() == false) {
                request.setAttribute("mess", "Tài khoản chưa được kích hoạt. Vui lòng truy cập Email để kích hoạt tài khoản của bạn!");
                jedis.del(capchaId_user);
                request.getRequestDispatcher("Login.jsp").forward(request, response);
                return;  // Stop further processing
            }

            HttpSession session = request.getSession();
            session.setAttribute("acc", a);
//             delete capcha if user login successfully
            jedis.del(capchaId_user);
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
