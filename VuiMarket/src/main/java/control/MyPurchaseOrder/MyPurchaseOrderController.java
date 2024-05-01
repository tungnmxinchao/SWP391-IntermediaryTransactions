/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.MyPurchaseOrder;

import dal.DAO;
import dal.MyPurchaseOrder.MyPurchaseOrderDAO;
import entity.MyPurchaseOrder;
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
public class MyPurchaseOrderController extends HttpServlet {

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
            //tao doi tuong pageControl
            PageControl pageControl = new PageControl();
            List<MyPurchaseOrder> listMyPurchase = pagination(request, pageControl);

            User userDB = dao.findInforUserBuyId(user.getUid());
            int moneyUser = userDB.getBalance();
            String moneyString = decimalFormat.format(Double.parseDouble(String.valueOf(moneyUser)));
            request.setAttribute("balanceUser", moneyString);

            session.setAttribute("listMyPurchase", listMyPurchase);
            request.setAttribute("pageControl", pageControl);
            request.getRequestDispatcher("MyPurchaseOrder.jsp").forward(request, response);
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

    private List<MyPurchaseOrder> pagination(HttpServletRequest request, PageControl pageControl) {
        //get page
        String pageRaw = request.getParameter("page");
        MyPurchaseOrderDAO myPurchaseOrderDao = new MyPurchaseOrderDAO();
        HttpSession session = request.getSession();
        //valid page
        int page;
        try {
            page = Integer.parseInt(pageRaw);
        } catch (Exception e) {
            page = 1;
        }
        int totalRecord = 0;
        List<MyPurchaseOrder> listMyPurchaseOrder = null;
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
                User user = (User) session.getAttribute("acc");
                int userId = user.getUid();

                totalRecord = myPurchaseOrderDao.findTotalRecord(userId);
                listMyPurchaseOrder = myPurchaseOrderDao.findByPage(page, userId);

                pageControl.setUrlPattern("myPurchaseOrder?");

        }

        //tìm kiếm xem tổng có bao nhiêu page
        int totalPage = (totalRecord % constant.constant.RECORD_PER_PAGE) == 0
                ? (totalRecord / constant.constant.RECORD_PER_PAGE)
                : (totalRecord / constant.constant.RECORD_PER_PAGE) + 1;
        //set những giá trị vào pageControl
        pageControl.setPage(page);
        pageControl.setTotalPage(totalPage);
        pageControl.setTotalRecord(totalRecord);

        return listMyPurchaseOrder;
    }

}
