/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.TransactionHistory;

import dal.DAO;
import dal.TransactionHistory.TransactionHistoryDAO;
import entity.History;
import entity.PageControl;
import entity.User;
import java.io.IOException;
import java.text.DecimalFormat;
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
public class TransactionHistoryController extends HttpServlet {

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

        List<History> listHistory = pagination(request, pageControl,userDB.getUid() );

        request.setAttribute("balanceUser", moneyString);
        session.setAttribute("listHistory", listHistory);
        request.setAttribute("pageControl", pageControl);

        request.getRequestDispatcher("History.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private List<History> pagination(HttpServletRequest request, PageControl pageControl, int userAccess) {
        //get page
        String pageRaw = request.getParameter("page");

        TransactionHistoryDAO historyDAO = new TransactionHistoryDAO();

        //valid page
        int page;
        try {
            page = Integer.parseInt(pageRaw);
        } catch (Exception e) {
            page = 1;
        }
        int totalRecord = 0;
        List<History> listHistory = null;
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
                totalRecord = historyDAO.findTotalRecord(userAccess);
                listHistory = historyDAO.findByPage(page, userAccess);
                pageControl.setUrlPattern("transactionHistory?");
        }

        //tìm kiếm xem tổng có bao nhiêu page
        int totalPage = (totalRecord % constant.constant.RECORD_PER_PAGE) == 0
                ? (totalRecord / constant.constant.RECORD_PER_PAGE)
                : (totalRecord / constant.constant.RECORD_PER_PAGE) + 1;
        //set những giá trị vào pageControl
        pageControl.setPage(page);
        pageControl.setTotalPage(totalPage);
        pageControl.setTotalRecord(totalRecord);

        return listHistory;
    }

}
