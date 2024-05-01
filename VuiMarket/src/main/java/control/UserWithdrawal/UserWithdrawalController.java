/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.UserWithdrawal;

import dal.DAO;
import dal.UserWithdrawal.UserWithdrawalDAO;
import entity.PageControl;
import entity.RechargeUsers;
import entity.User;
import entity.UserWithdrawl;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pc
 */
public class UserWithdrawalController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PageControl pageControl = new PageControl();
        HttpSession session = request.getSession();

        DAO dao = new DAO();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        User user = (User) session.getAttribute("acc");
        if (user == null) {
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        User userDB = dao.findInforUserBuyId(user.getUid());
        int moneyUser = userDB.getBalance();
        String moneyString = decimalFormat.format(Double.parseDouble(String.valueOf(moneyUser)));

        List<UserWithdrawl> listWithdrawal = pagination(request, pageControl);

        request.setAttribute("balanceUser", moneyString);
        session.setAttribute("listWithdrawal", listWithdrawal);
        request.setAttribute("pageControl", pageControl);

        request.getRequestDispatcher("UserWithdrawal.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private List<UserWithdrawl> pagination(HttpServletRequest request, PageControl pageControl) {
        //get page
        String pageRaw = request.getParameter("page");
        UserWithdrawalDAO userWithdrawalDAO = new UserWithdrawalDAO();
        //valid page
        int page;
        try {
            page = Integer.parseInt(pageRaw);
        } catch (Exception e) {
            page = 1;
        }
        int totalRecord = 0;
        List<UserWithdrawl> listWithdrawl = null;
        //get action hiện tại muốn làm gì
        //tìm kiếm xem có bao nhiêu record và listBook By page
        String action = request.getParameter("action") == null
                ? "defaultFindAll"
                : request.getParameter("action");
        switch (action) {
            case "search":
                break;
            case "category":
                break;
            default:
                totalRecord = userWithdrawalDAO.findTotalRecord();
                listWithdrawl = userWithdrawalDAO.findByPage(page);
                pageControl.setUrlPattern("userWithdrawal?");
        }

        //tìm kiếm xem tổng có bao nhiêu page
        int totalPage = (totalRecord % constant.constant.RECORD_PER_PAGE) == 0
                ? (totalRecord / constant.constant.RECORD_PER_PAGE)
                : (totalRecord / constant.constant.RECORD_PER_PAGE) + 1;
        //set những giá trị vào pageControl
        pageControl.setPage(page);
        pageControl.setTotalPage(totalPage);
        pageControl.setTotalRecord(totalRecord);

        return listWithdrawl;
    }

}
