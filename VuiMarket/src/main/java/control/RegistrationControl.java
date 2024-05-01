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
import utils.Encode;

/**
 *
 * @author W10.TQ
 */
public class RegistrationControl extends HttpServlet {

    private DAO signupDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        signupDAO = new DAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            
            String user = request.getParameter("name");
            String email = request.getParameter("email");
            String pass = request.getParameter("pass");
            String rePass = request.getParameter("re_pass");
            String phone = request.getParameter("contact");

            // Kiểm tra mật khẩu nhập lại
            if (!pass.equals(rePass)) {
                response.sendRedirect("registration.jsp?error=2");
                return;
            } else {
                pass = Encode.toSHA1(pass);
            }

            // Kiểm tra tài khoản đã tồn tại
            if (signupDAO.existedUser(user)) {
                response.sendRedirect("registration.jsp?error=1");
                return;
            }

            // Kiểm tra số điện thoại là số và có 10-11 chữ số
            if (!phone.matches("\\d{10,11}")) {
                response.sendRedirect("registration.jsp?error=3");
                return;
            }

            // Tiếp tục xử lý đăng ký nếu không có lỗi
            signupDAO.register(null, user, email, pass, phone);
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Bạn đã đăng kí thành công. Vui lòng truy cập email để kích hoạt tài khoản!');");
            out.println("window.location.href = 'login';");
            out.println("</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        // Tắt ExecutorService
        signupDAO.shutdownExecutorService();
    }
}
