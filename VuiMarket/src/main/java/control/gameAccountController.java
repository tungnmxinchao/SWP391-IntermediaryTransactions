/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control;

import dal.ProductDAO;
import entity.Category;
import entity.PageControl;
import entity.Product;
import java.io.IOException;
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
public class gameAccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        //TAO SESSION
        HttpSession session = request.getSession();
        //tao DAO
        ProductDAO producDAO = new ProductDAO();
        //tao doi tuong pageControl
        PageControl pageControl = new PageControl();
        // list category
        List<Category> listCategory = producDAO.findAllCategory();
        //phân trang
        List<Product> listProduct = pagination(request, pageControl);

        session.setAttribute("listCategory", listCategory);
        session.setAttribute("listProduct", listProduct);
        request.setAttribute("pageControl", pageControl);
        request.getRequestDispatcher("GameAccount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private List<Product> pagination(HttpServletRequest request, PageControl pageControl) {
        //get page
        String pageRaw = request.getParameter("page");
        ProductDAO productDAO = new ProductDAO();
        //valid page
        int page;
        try {
            page = Integer.parseInt(pageRaw);
        } catch (Exception e) {
            page = 1;
        }
        int totalRecord = 0;
        List<Product> listProduct = null;
        //get action hiện tại muốn làm gì
        //tìm kiếm xem có bao nhiêu record và listBook By page
        String action = request.getParameter("action") == null
                ? "defaultFindAll"
                : request.getParameter("action");
        switch (action) {
            case "search":
//                //tìm kiếm bao nhiêu record
                String product_id = request.getParameter("product_id");
                String product_name = request.getParameter("product_name");
                String price_from = request.getParameter("price_from");
                String price_to = request.getParameter("price_to");
                totalRecord = productDAO.findTotalRecordByProperty(product_id, product_name, price_from, price_to);
//                ///tìm về list product
                listProduct = productDAO.findByPropertyAndPage(product_id, product_name, price_from, price_to, page);
                pageControl.setUrlPattern("gameAccount?action=search&product_id=" + product_id + "&product_name=" + product_name
                        + "&price_from=" + price_from + "&price_to=" + price_to + "&");
                break;
            case "category":
                int caterogy_Id = Integer.parseInt(request.getParameter("categoryId"));
                if (caterogy_Id == -1) {
                    //tìm về totalRecord 
                    totalRecord = productDAO.findTotalRecord();
                    //tìm về danh sách các quyển sách ở trang chỉ định
                    listProduct = productDAO.findByPage(page);
                    pageControl.setUrlPattern("gameAccount?");
                } else {
                    // Continue with the existing logic for category handling
                    totalRecord = productDAO.findTotalRecordByCategoryId(caterogy_Id);
                    listProduct = productDAO.findByCategoryIdAndPage(caterogy_Id, page);
                    pageControl.setUrlPattern("gameAccount?action=category&categoryId=" + caterogy_Id + "&");
                    request.setAttribute("caterogyId", caterogy_Id);
                }
                break;
            default:
                //phân trang ở trang home
                //tìm về totalRecord 
                totalRecord = productDAO.findTotalRecord();
                //tìm về danh sách các quyển sách ở trang chỉ định
                listProduct = productDAO.findByPage(page);
                pageControl.setUrlPattern("gameAccount?");

        }

        //tìm kiếm xem tổng có bao nhiêu page
        int totalPage = (totalRecord % constant.constant.RECORD_PER_PAGE) == 0
                ? (totalRecord / constant.constant.RECORD_PER_PAGE)
                : (totalRecord / constant.constant.RECORD_PER_PAGE) + 1;
        //set những giá trị vào pageControl
        pageControl.setPage(page);
        pageControl.setTotalPage(totalPage);
        pageControl.setTotalRecord(totalRecord);

        return listProduct;

    }
}
