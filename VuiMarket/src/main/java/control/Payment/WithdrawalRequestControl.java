package control.Payment;

import constant.BalanceHandler;
import dal.DAO;
import dal.WithdrawalRequestDAO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.Encode;

public class WithdrawalRequestControl extends HttpServlet {

    private BalanceHandler balanceHandler;

    public WithdrawalRequestControl() {
        balanceHandler = new BalanceHandler();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet WithdrawalRequestControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WithdrawalRequestControl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DAO dao = new DAO();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");

        if (user != null) {
            User userDB = dao.findInforUserBuyId(user.getUid());
            int moneyUser = userDB.getBalance();
            String moneyString = decimalFormat.format(Double.parseDouble(String.valueOf(moneyUser)));
            String withdrawCode = Encode.rechargeCode();

            request.setAttribute("withdrawCode", withdrawCode);
            request.setAttribute("balanceUser", moneyString);
            request.getRequestDispatcher("WithdrawalRequest.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");
        WithdrawalRequestDAO dao = new WithdrawalRequestDAO();
        String withdrawCode = request.getParameter("withdrawCode");

        if (user != null) {
            int uid = user.getUid();
            String amountStr = request.getParameter("amount");
            amountStr = amountStr.replaceAll("[,.]", "");
            int amount = Integer.parseInt(amountStr);
            String accountNum = request.getParameter("accountNumber");
            String accountOwner = request.getParameter("accountOwner");
            String bankName = request.getParameter("bankName");

            int amountToDeduct = - amount; // Số tiền muốn trừ

            balanceHandler.addToQueue(uid, amountToDeduct);

            boolean success = dao.addWithdrawalRequest(uid, amount, accountNum, accountOwner, bankName, withdrawCode);

            if (success) {
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Bạn đã gửi yêu cầu thành công. Hệ thống sẽ tạm trừ tiền trên hệ thống của bạn!');");
                out.println("window.location.href = 'home';");
                out.println("</script>");
            } else {
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Có lỗi xảy ra. Vui lòng thử lại sau.');");
                out.println("window.location.href = 'home';");
                out.println("</script>");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
