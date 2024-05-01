<%-- 
    Document   : IntermediateMarket
    Created on : Feb 15, 2024, 11:03:01 AM
    Author     : Acer
--%>
<%@page import="entity.User"%>
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
        <link rel="stylesheet" href="css/font-awesome.min.css">

        <!-- Libraries Stylesheet -->
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">

        <!-- Custom datatable -->
        <%@include file="componentPublic/customTable_GameAccount.jsp" %>
        <!-- Custom datatable -->

    </head>

    <body>
        <!-- header -->
        <jsp:include page="componentPublic/header.jsp"></jsp:include>
            <!-- header -->

            <!-- search -->
            <nav class="navbar navbar-light bg-light pl-sm-0">
                <form class="form-inline" action="intermediateMarket" method="GET" style="display:none" >
                    <input type="text" name="action" style="display:none" value="search"/>
                    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="keyword">
                    <button class="btn btn-outline-success my-2 my-sm-0 ml-sm-0"  style="visibility: hidden" type="submit">Search</button>
                </form>
            </nav>
            <!-- search -->             

            <div class="data_table" style="width:100%" >
                <table id="example" class="table2 table-striped">
                    <thead>

                        <tr>
                            <th>Mã Trung gian</th>
                            <th>Chủ đề trung gian</th>
                            <th>Phương thức liên hệ</th>                  
                            <th>Giá tiền</th>
                            <th>Bên chịu phí trung gian</th>
                            <th>Phí trung gian(5% giá trị đơn hàng)</th>
                            <th>Tổng phí cần thanh toán</th>
                            <th>Người bán</th>
                            <th>Thời gian tạo</th>
                            <th>Cập nhật cuối</th>
                            <th>Hành động</th>
                        </tr>
                    <form action="intermediateMarket" method="GET" >
                        <tr>
                            <th>
                                <input class="form-control mr-sm-2" type="search" placeholder="" aria-label="Search" name="mysaleorder_id" oninput="saveSearchValue(this)">
                            </th>
                            <th>                          
                                <input class="form-control mr-sm-2" type="search" placeholder="" aria-label="Search" name="title" oninput="saveSearchValue(this)">
                            </th>
                            <th>
                                <input class="form-control mr-sm-2" type="search" placeholder="" aria-label="Search" name="contact" oninput="saveSearchValue(this)">
                            </th>
                            <th>
                                <div class = "moneyValue_search">
                                    <input class="form-control mr-sm-2" type="search" placeholder="Từ" aria-label="Search" name="moneyValue_from" oninput="saveSearchValue(this)" >
                                    <input class="form-control mr-sm-2" type="search" placeholder="Đến" aria-label="Search" name="moneyValue_to" oninput="saveSearchValue(this)" >
                                </div>
                            </th>
                            <th>
                                <input type="text" name="action" style="display:none" value="search"/>
                            </th> 
                            <th>

                            </th> 
                            <th>

                            </th> 
                            <th>

                            </th> 
                            <th>
                                <!--                                <div class = "createdAt_search">
                                                                    <input class="form-control mr-sm-2" type="search" placeholder="Từ" aria-label="Search" name="createdAt_from" oninput="saveSearchValue(this)" >
                                                                    <input class="form-control mr-sm-2" type="search" placeholder="Đến" aria-label="Search" name="createdAt_to" oninput="saveSearchValue(this)" >
                                                                </div>-->
                            </th> 
                            <th>
                                <!--                                <div class = "updatedAt_search">
                                                                    <input class="form-control mr-sm-2" type="search" placeholder="Từ" aria-label="Search" name="updatedAt_from" oninput="saveSearchValue(this)" >
                                                                    <input class="form-control mr-sm-2" type="search" placeholder="Đến" aria-label="Search" name="updatedAt_to" oninput="saveSearchValue(this)" >
                                                                </div>-->
                            </th>
                            <th>
                                <div class="action_form">
                                    <div><input onclick="performSearch()"  type="submit" value="Search" /></div>
                                    <div ><button onclick="clearSearchValues()">Clear</button></div>                               
                                </div>
                            </th>
                        </tr>

                    </form>
                    </thead>
                    <tbody>
                    <c:forEach items="${listMySaleOrder}" var="list">  
                        <tr>
                            <td>${list.id}</td>
                            <td>${list.title}</td>
                            <td>${list.contact}</td>
                            <td class="price">${list.moneyValue}</td>
                            <td>${list.isSellerChargeFee}</td>
                            <td>${list.feeOnSuccess}</td>
                            <td>${list.totalMoneyForBuyer}</td>
                            <td>${list.createdBy}</td>
                            <td>${list.createdAt} </td>
                            <td>${list.updatedAt}</td>
                            <td>    
                                <div class="action_user" style="text-align: center;">
                                    <button type="button" class="mr-1 btn btn-warning view-details-btn" data-toggle="modal" data-target="#infoModal" 
                                            data-mysaleorder-Id="${list.id}" 
                                            data-title="${list.title}" 
                                            data-contact="${list.contact}" 
                                            data-money-Value="${list.moneyValue}" 
                                            data-is-Seller-Charge-Fee="${list.isSellerChargeFee}" 
                                            data-fee-On-Success="${list.feeOnSuccess}" 
                                            data-total-Money-For-Buyer="${list.totalMoneyForBuyer}" 
                                            data-description="${list.description}"
                                            data-created-By="${list.createdBy}" 
                                            data-created-At="${list.createdAt}" 
                                            data-updated-At="${list.updatedAt}"
                                            data-shareLink="${list.shareLink}">
                                        <i class="fa fas fa-eye"></i> 
                                    </button>

                                    <button type="button" class="mr-1 btn btn-success buy-btn" data-toggle="modal" data-target="#modalBuy" 
                                            data-mysaleorder-name-buy="${list.title}" 
                                            data-mysaleorder-prices-buy="${list.moneyValue}">
                                        <i class="fa fa-shopping-cart"></i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
            </table>
        </div>
        <!-- View product End -->

        <!-- modal details -->
        <%@include file="componentPublic/modalDetail_interMarket.jsp" %>
        <!-- modal details -->

        <!-- Modal buy -->
        <%@include file="componentPublic/modalBuy_interMarket.jsp" %>
        <!-- Modal buy -->

        <!-- pagination -->
        <%@include file="componentPublic/pagination.jsp" %>
        <!-- pagination -->

        <!-- footer -->
        <%@include file="componentPublic/footer.jsp" %>
        <!-- footer -->

        <!-- Back to Top -->
        <a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>

        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>

        <!-- Contact Javascript File -->
        <script src="mail/jqBootstrapValidation.min.js"></script>
        <script src="mail/contact.js"></script>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
        <script>
                                        // open modal when click buttion 
                                        $(document).ready(function () {
                                            // Khi nhấp vào biểu tượng fa-eye, hiển thị modal
                                            $('.btn-warning').on('click', function () {
                                                $('#infoModal').modal('show');
                                            });

                                            // Khi nhấp vào biểu tượng fa-shopping-cart, hiển thị modal
                                            $('.btn-success').on('click', function () {
                                                $('#buyModal').modal('show');
                                            });
                                        });



                                        // auto submit when slect option tag
                                        function submitForm() {
                                            document.getElementById('yourForm').submit();
                                        }


                                        // store data search input after searching
                                        function saveSearchValue(input) {
                                            sessionStorage.setItem(input.name, input.value);
                                        }

                                        // Thực hiện tìm kiếm
                                        function performSearch() {
                                            // Lấy giá trị tìm kiếm từ Session Storage
                                            var mysaleorderId = sessionStorage.getItem('mysaleorder_id') || "";
                                            var title = sessionStorage.getItem('title') || "";
                                            var contact = sessionStorage.getItem('contact') || "";
                                            var moneyValuefrom = sessionStorage.getItem('moneyValue_from') || "";
                                            var moneyValueto = sessionStorage.getItem('moneyValue_to') || "";
                                        }

                                        // Load giá trị tìm kiếm từ Session Storage khi trang được tải
                                        window.onload = function () {
                                            var inputs = document.querySelectorAll('input[type="search"]');
                                            inputs.forEach(function (input) {
                                                var savedValue = sessionStorage.getItem(input.name);
                                                if (savedValue) {
                                                    input.value = savedValue;
                                                }
                                            });
                                        };


                                        // clear data input when click into button
                                        function clearSearchValues() {
                                            var inputs = document.querySelectorAll('input[type="search"]');
                                            inputs.forEach(function (input) {
                                                input.value = "";
                                                sessionStorage.removeItem(input.name);
                                            });
                                        }


//                                       fill data into modal view details - modal detals
                                        $(document).ready(function () {
                                            $('.view-details-btn').click(function () {
                                                var mysaleorderId = $(this).data('mysaleorderId');
                                                var title = $(this).data('title');
                                                var contact = $(this).data('contact');
                                                var moneyValue = $(this).data('moneyValue');
                                                var createdBy = $(this).data('createdBy');
                                                var isSellerChargeFee = $(this).data('isSellerChargeFee');
                                                var feeOnSuccess = $(this).data('feeOnSuccess');
                                                var totalMoneyForBuyer = $(this).data('totalMoneyForBuyer');
                                                var description = $(this).data('description');
                                                var createdAt = $(this).data('createdAt');
                                                var updatedAt = $(this).data('updatedAt');
                                                var shareLink = $(this).data('shareLink');


                                                // Cập nhật nội dung modal dựa trên dữ liệu từ hàng được chọn
                                                $('#infoModal .modal-body input[name="mysaleorderId"]').val(mysaleorderId);
                                                $('#infoModal .modal-body input[name="createdBy"]').val(createdBy);
                                                $('#infoModal .modal-body input[name="title"]').val(title);
                                                $('#infoModal .modal-body input[name="moneyValue"]').val(moneyValue);
                                                $('#infoModal .modal-body input[name="isSellerChargeFee"]').val(isSellerChargeFee);
                                                $('#infoModal .modal-body input[name="feeOnSuccess"]').val(feeOnSuccess);
                                                $('#infoModal .modal-body input[name="totalMoneyForBuyer"]').val(totalMoneyForBuyer);
                                                $('#infoModal .modal-body input[name="description"]').val(description);
                                                $('#infoModal .modal-body input[name="contact"]').val(contact);

                                                $('#infoModal .modal-body input[name="createdAt"]').val(createdAt);
                                                $('#infoModal .modal-body input[name="updatedAt"]').val(updatedAt);
                                                $('#infoModal .modal-body input[name="shareLink"]').val(shareLink);


                                            });


                                        });

//                                        // modal buy
//                                        $(document).ready(function () {
//                                            $('.buy-btn').click(function () {
//                                                // Show the confirmation modal
//                                                $('#confirmIntermediaryMoneyModal').modal('show');
//
//                                                // Attach a click event listener to the "OK" button to handle the temporary hold of intermediary money
//                                                $('#confirmIntermediaryMoneyButton').click(function () {
//                                                    // Implement the logic for handling the temporary hold of intermediary money here
//                                                });
//                                            });
//                                        });

                                        // Bắt sự kiện click cho nút "Mua" trong modal thông tin
                                        $("#infoModal .btn-success").on("click", function () {
                                            // Mở modal mua
                                            $("#buyModal").modal("show");
                                        });

                                        document.addEventListener('DOMContentLoaded', function () {
                                            var priceElements = document.querySelectorAll('.price');

                                            priceElements.forEach(function (element) {
                                                var price = parseFloat(element.textContent.replace(/[^\d.-]/g, '')); // Lấy giá và loại bỏ dấu và ký tự không phải số
                                                var formattedPrice = price.toLocaleString('vi-VN'); // Định dạng giá với dấu phân cách hàng nghìn

                                                element.textContent = formattedPrice;
                                            });
                                        });
        </script>

    </body>

</html>