/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.PublicOrders;

import dal.DAO;
import dal.MySaleOrder.MySaleOrderDAO;
import dal.PublicOrderDAO.PublicOrderDAO;
import entity.MySaleOrder;
import entity.PageControl;
import entity.User;
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
 * @author TNO
 */
public class PublicOrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("acc");

        if (user == null) {
            response.sendRedirect("login");
        } else {
            PageControl pageControl = new PageControl();
            int userAccess = user.getUid();

            User userDB = dao.findInforUserBuyId(user.getUid());
            int moneyUser = userDB.getBalance();
            String moneyString = decimalFormat.format(Double.parseDouble(String.valueOf(moneyUser)));
            request.setAttribute("balanceUser", moneyString);
            
            List<MySaleOrder> listMyOrder = pagination(request, pageControl);
            session.setAttribute("listMyOrder", listMyOrder);
            request.setAttribute("pageControl", pageControl);
            request.setAttribute("userAccess", userAccess);
            request.getRequestDispatcher("PublicOrder.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private List<MySaleOrder> pagination(HttpServletRequest request, PageControl pageControl) {
        //get page
        String pageRaw = request.getParameter("page");
        MySaleOrderDAO myOrderDAO = new MySaleOrderDAO();
        PublicOrderDAO publicOrders = new PublicOrderDAO();
        HttpSession session = request.getSession();
        //valid page
        int page;
        try {
            page = Integer.parseInt(pageRaw);
        } catch (Exception e) {
            page = 1;
        }
        int totalRecord = 0;
        List<MySaleOrder> listMySaleOrder = null;
        //get action hiện tại muốn làm gì
        //tìm kiếm xem có bao nhiêu record và listBook By page
        String action = request.getParameter("action") == null
                ? "defaultFindAll"
                : request.getParameter("action");
        switch (action) {
            case "search":
//                User user1 = (User) session.getAttribute("acc");
//                int IdUser = user1.getUid();
//
//                String idOrder = request.getParameter("idOrder");
//                String status = request.getParameter("status");
//                String buyer = request.getParameter("buyer");
//                String title = request.getParameter("title");
//                String isPublic = request.getParameter("isPublic");
//                String moneyValueFrom = request.getParameter("moneyValueFrom");
//                String moneyValueTo = request.getParameter("moneyValueTo");
//                String isSellerChargeFee = request.getParameter("isSellerChargeFee");
//                String timeCreatedFrom = request.getParameter("timeCreatedFrom");
//                String timeCreatedTo = request.getParameter("timeCreatedTo");
//                String timeUpdateFrom = request.getParameter("timeUpdateFrom");
//                String timeUpdateTo = request.getParameter("timeUpdateTo");
//
//                totalRecord = myOrderDAO.findTotalRecordBySearch(IdUser, idOrder, status, buyer,
//                        title, isPublic, moneyValueFrom, moneyValueTo, isSellerChargeFee, timeCreatedFrom,
//                        timeCreatedTo, timeUpdateFrom, timeUpdateTo);
//                listMySaleOrder = myOrderDAO.findByPageAndSearch(IdUser, idOrder, status, buyer,
//                        title, isPublic, moneyValueFrom, moneyValueTo, isSellerChargeFee, timeCreatedFrom,
//                        timeCreatedTo, timeUpdateFrom, timeUpdateTo, page);
//                pageControl.setUrlPattern("mySaleOrder?action=search&idOrder=" + idOrder + "&status=" + status
//                        + "&buyer=" + buyer + "&title=" + title + "&isPublic=" + isPublic + "&moneyValueFrom="
//                        + moneyValueFrom + "&moneyValueTo=" + moneyValueTo + "&isSellerChargeFee=" + isSellerChargeFee
//                        + "&timeCreatedFrom=" + timeCreatedFrom + "&timeCreatedTo=" + timeCreatedTo + "&timeUpdateFrom="
//                        + timeUpdateFrom + "&timeUpdateTo=" + timeUpdateTo + "&");
                break;
            case "category":
                break;
            default:
                User user = (User) session.getAttribute("acc");
                String nameUser = user.getUsername();

                int userId = myOrderDAO.findUserIdByUserName(nameUser);

                totalRecord = publicOrders.findTotalRecord(userId);

                listMySaleOrder = publicOrders.findByPage(page, userId);
                pageControl.setUrlPattern("publicOrders?");

        }

        //tìm kiếm xem tổng có bao nhiêu page
        int totalPage = (totalRecord % constant.constant.RECORD_PER_PAGE) == 0
                ? (totalRecord / constant.constant.RECORD_PER_PAGE)
                : (totalRecord / constant.constant.RECORD_PER_PAGE) + 1;
        //set những giá trị vào pageControl
        pageControl.setPage(page);
        pageControl.setTotalPage(totalPage);
        pageControl.setTotalRecord(totalRecord);

        return listMySaleOrder;
    }

}
