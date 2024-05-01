<%-- 
    Document   : RechargeUsers
    Created on : Mar 19, 2024, 12:25:01 AM
    Author     : TNO
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>VuiMarket</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="Free HTML Templates" name="keywords">
        <meta content="Free HTML Templates" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet"> 

        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">       

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">

        <!-- css table -->
        <link rel="stylesheet" type="text/css" href="css/stylemysaleorder.css">

        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>

    </head>

    <body>
        <!-- Modal -->
        <div class="modal fade" id="confirmationModal" tabindex="-1" aria-labelledby="confirmationModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmationModalLabel">Xác Nhận</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" id="modalBodyContent">
                        Xác nhận chuyển tiền cho người dùng?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                        <button type="button" class="btn btn-primary" id="confirmButton">Đồng Ý</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- header -->
        <jsp:include page="componentPublic/header.jsp"></jsp:include>
            <!-- header -->
            <div id="notificationArea"></div>
            <div class="table_mysaleorder">

                <main class="table" id="customers_table">
                    <section class="table__header">
                        <h1>Lịch sử giao dịch</h1>  
                    </section>
                    <section class="table__body">
                        <table>
                            <thead>
                                <tr>
                                    <th> Mã  <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Số tiền <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Loại giao dịch <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Trạng thái  <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Mô tả  <span class="icon-arrow">&UpArrow;</span></th>
                                    <th>  Người dùng  <span class="icon-arrow">&UpArrow;</span></th>
                                    <th>   Thời gian tạo  <span class="icon-arrow">&UpArrow;</span></th>
                                    <th>  Cập Nhật Cuối  <span class="icon-arrow">&UpArrow;</span></th>
                                </tr>
                            </thead>
                            <thead2>
                                <form action="mySaleOrder" method="GET">
                                    <tr>                              
                                        <th>
                                            <input type="text" name="action" style="display:none" value="search"/>
                                            <input class="form-control mr-sm-2" placeholder="ID" aria-label="Search" type="text" name="idOrder" value="" />
                                        </th>
                                        <th>
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Số tiền" type="text" name="moneyValue" value="" />
                                        </th>
                                        <th>
                                            <select id="" name="typeTrans">
                                                <option value="-1">Tất cả</option>
                                                <option value="1">Trừ tiền ( - )</option>
                                                <option value="2">Cộng tiền ( + )</option>
                                            </select>
                                        </th>
                                        <th>
                                            <select id="" name="status">
                                                <option value="-1">Tất cả</option>
                                                <option value="1">Đã xử lý</option>
                                                <option value="2">Chưa xử lý</option>
                                            </select>
                                        </th>
                                        <th>
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Mô tả" type="text" name="mota" value="" />

                                        </th>
                                        <th><input class="form-control mr-sm-2" aria-label="Search" placeholder="Người dùng" type="text" name="userAccess" value="" /> </th>
                                        <th>
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Từ" type="date" name="timeUpdateFrom" value="" />
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Đến" type="date" name="timeUpdateTo" value="" />
                                        </th>
                                        <th>
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Từ" type="date" name="timeUpdateFrom" value="" />
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Đến" type="date" name="timeUpdateTo" value="" />
                                        </th>
                                    </tr>
                                </form>
                            </thead2>
                            <tbody>
                            <c:forEach items="${listHistory}" var="listRecharge">
                                <tr>
                                    <td>${listRecharge.id}</td>
                                    <td>${listRecharge.moneyValue}</td>
                                    <td>${listRecharge.typeTransaction}</td>
                                    <td>${listRecharge.status}</td>
                                    <td> <strong> ${listRecharge.description} </strong></td>
                                    <td> ${listRecharge.fullName} </td>
                                    <td> ${listRecharge.createdAt} </td>
                                    <td> ${listRecharge.updatedAt} </td>


                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>
                </section>              
            </main>

        </div>
        <%@include file="componentPublic/pagination.jsp" %>
        <%@include file="componentPublic/footer.jsp" %>
        <%@include file="componentUser/modalAddNew.jsp" %>

        <jsp:include page="componentUser/modalViewDetailsPublicOrders.jsp"></jsp:include>





        <script src="js/scriptmysaleorder.js"></script>
        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>


        <script src="https://cdn.tiny.cloud/1/3dnf4g9cgv4sfog9pgz0n3f33fqrskhx8tz3ejpcmhn480gh/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>



        <script>
        </script>
    </body>

</html>
