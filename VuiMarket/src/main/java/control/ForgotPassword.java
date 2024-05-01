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

/**
 *
 * @author pc
 */
public class ForgotPassword extends HttpServlet {

    private DAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new DAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        User user = dao.getUserByUsername(username);

        if (user != null) {
            String email = user.getEmail();
            String confirmationCode = user.getConfirmationCode();
            dao.sendEmailForgotPass(username, email, confirmationCode);
            response.sendRedirect("Login.jsp");
        } else {
            response.sendRedirect("forgotPassword.jsp?error=notfound");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        dao.shutdownExecutorService();
    }
}
