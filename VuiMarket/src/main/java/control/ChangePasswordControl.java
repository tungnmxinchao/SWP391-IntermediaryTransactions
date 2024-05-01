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
import utils.Encode;

/**
 *
 * @author pc
 */
public class ChangePasswordControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("acc");

        // Kiểm tra xem account có tồn tại trong session không
        if (account == null) {
            response.sendRedirect("login"); // Chuyển hướng người dùng nếu không có thông tin account
            return;
        }

        int accountId = account.getUid();
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");
        
        oldPassword = Encode.toSHA1(oldPassword);

        if (!newPassword.equals(confirmNewPassword)) {
            request.setAttribute("message", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
            request.getRequestDispatcher("ChangePass.jsp").forward(request, response);
            return;
        } else {
            newPassword = Encode.toSHA1(newPassword);
        }

        DAO dao = new DAO();
        boolean passwordChanged = dao.changePassword(accountId, oldPassword, newPassword);

        if (passwordChanged) {
            request.setAttribute("message", "Thay đổi mật khẩu thành công");
        } else {
            request.setAttribute("message", "Mật khẩu cũ không đúng hoặc không thể cập nhật mật khẩu");
        }

        request.getRequestDispatcher("ChangePass.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
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
