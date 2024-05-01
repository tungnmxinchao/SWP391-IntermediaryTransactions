<%-- 
    Document   : AdminHandling
    Created on : Mar 18, 2024, 9:11:30 PM
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

        <!-- header -->
        <jsp:include page="componentPublic/header.jsp"></jsp:include>
            <!-- header -->
            <div id="notificationArea"></div>
            <div class="table_mysaleorder">

                <main class="table" id="customers_table">
                    <section class="table__header">
                        <h1>Xử lý khiếu nại</h1>
                    </section>
                    <section class="table__body">
                        <table>
                            <thead>
                                <tr>
                                    <th> Mã Trung Gian <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Trạng Thái <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Người Mua  <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Chủ Đề <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Công Khai / Riêng Tư  <span class="icon-arrow">&UpArrow;</span></th>
                                    <th>  Giá Tiền <span class="icon-arrow">&UpArrow;</span></th>
                                    <th>  Bên Chịu Phí  <span class="icon-arrow">&UpArrow;</span></th>
                                    <th>  Thời Gian Tạo  <span class="icon-arrow">&UpArrow;</span></th>
                                    <th>   Cập Nhật Cuối  <span class="icon-arrow">&UpArrow;</span></th>
                                    <th>   Hành Động  <span class="icon-arrow">&UpArrow;</span></th>
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
                                            <select id="" name="status">
                                                <option value="-1">Tất cả</option>
                                                <option value="1">Sẵn sàng giao dịch</option>
                                                <!--                                                <option value="2">Bên mua đang kiểm tra hàng</option>
                                                                                                <option value="3">Hủy</option>
                                                                                                <option value="4">Hoàn thành</option>
                                                                                                <option value="5">Bên mua khiếu nại sản phẩm</option>
                                                                                                <option value="6">Chờ bên mua xác nhận khiếu nại không đúng</option>
                                                                                                <option value="6">Yêu cầu quản trị viên trung gian</option>-->
                                            </select>
                                        </th>
                                        <th><input class="form-control mr-sm-2" aria-label="Search" placeholder="Tìm người mua" type="text" name="buyer" value="" /></th>
                                        <th><input class="form-control mr-sm-2" aria-label="Search" placeholder="Tìm chủ đề" type="text" name="title" value="" /></th>
                                        <th>
                                            <select id="" name="isPublic">
                                                <option value="-1">Tất cả</option>
                                                <option value="1">Công khai</option>
                                                <option value="0"> Riêng tư</option>
                                            </select>
                                        </th>
                                        <th>
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Từ" type="text" name="moneyValueFrom" value="" />
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Đến" type="text" name="moneyValueTo" value="" />
                                        </th>
                                        <th>
                                            <select id="" name="isSellerChargeFee">
                                                <option value="-1">Tất cả</option>
                                                <option value="1">Bên bán</option>
                                                <option value="0">  Bên mua</option>

                                            </select>
                                        </th>
                                        <th>
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Từ" type="date" name="timeCreatedFrom" value="" />
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Đến" type="date" name="timeCreatedTo" value="" />
                                        </th>
                                        <th>
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Từ" type="date" name="timeUpdateFrom" value="" />
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Đến" type="date" name="timeUpdateTo" value="" />
                                        </th>
                                        <th>
                                            <div class="action_form">
                                                <div><input onclick=""  type="submit" value="Search" /></div>
                                                <div ><button onclick="">Clear</button></div>                               
                                            </div>
                                        </th>
                                    </tr>
                                </form>
                            </thead2>
                            <tbody>
                            <c:forEach items="${listMyOrder}" var="listMyOrder">
                                <tr>
                                    <td> ${listMyOrder.id} </td>
                                    <td>${listMyOrder.status}</td>
                                    <td>${listMyOrder.buyer}</td>
                                    <td> ${listMyOrder.title} </td>
                                    <td>
                                        <p class="status delivered">${listMyOrder.isPublic}</p>
                                    </td>
                                    <td> <strong> ${listMyOrder.moneyValue} </strong></td>
                                    <td> ${listMyOrder.isSellerChargeFee} </td>
                                    <td> ${listMyOrder.createdAt} </td>
                                    <td> ${listMyOrder.updatedAt} </td>
                                    <td> 
                                        <a href="http://localhost:8080/VuiMarket/formInfor?orderId=${listMyOrder.id}&userId=${listMyOrder.idUser}" class="details-button">
                                            <i class="fa fa-eye"></i>
                                        </a>
                                    </td>
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

        <jsp:include page="componentUser/modalViewDetailsMyOrder.jsp"></jsp:include>





        <script src="js/scriptmysaleorder.js"></script>
        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>


        <script src="https://cdn.tiny.cloud/1/3dnf4g9cgv4sfog9pgz0n3f33fqrskhx8tz3ejpcmhn480gh/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>



        <script>

        </script>
    </body>

</html>

