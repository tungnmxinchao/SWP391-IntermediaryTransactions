<%-- 
    Document   : GameAccount
    Created on : Jan 22, 2024, 4:19:08 PM
    Author     : TNO
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
                <form class="form-inline" action="gameAccount" method="GET" style="display:none" >
                    <input type="text" name="action" style="display:none" value="search"/>
                    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="keyword">
                    <button class="btn btn-outline-success my-2 my-sm-0 ml-sm-0"  style="visibility: hidden" type="submit">Search</button>
                </form>
            </nav>
            <!-- search -->     

            <!-- select tag -->
            <div class="tag_select">
                <form action="gameAccount" method="get" id="yourForm">    
                    <input style="display:none" type="text" name="action" value="category" />
                    <select class="form-select" aria-label="Default select example" name="categoryId" onchange="submitForm()">
                        <option value="-1">DANH MỤC (ALL)</option>
                    <c:forEach items="${listCategory}" var="category">                      
                        <option value="${category.id}" ${caterogyId eq category.id ? "selected" : ""}>${category.category_name}</option>
                    </c:forEach>
                </select>               
            </form>
        </div>
        <!-- select tag -->

        <div class="data_table" style="width:100%" >
            <table id="example" class="table2 table-striped">
                <thead>

                    <tr>
                        <th>Image</th>
                        <th>ProductID</th>
                        <th>Product Name</th>                  
                        <th>Price</th>
                        <th>Supplier</th>
                        <th>Quantity</th>
                        <th>Action</th>
                    </tr>
                <form action="gameAccount" method="GET" >
                    <tr>
                        <th>
                            <input type="text" name="action" style="display:none" value="search"/>
                        </th>
                        <th>                          
                            <input class="form-control mr-sm-2" type="search" placeholder="Tìm theo ID" aria-label="Search" name="product_id" oninput="saveSearchValue(this)">
                        </th>
                        <th><input class="form-control mr-sm-2" type="search" placeholder="Tìm theo Name" aria-label="Search" name="product_name" oninput="saveSearchValue(this)" ></th>
                        <th>
                            <div class = "price_search">
                                <input class="form-control mr-sm-2" type="search" placeholder="Từ" aria-label="Search" name="price_from" oninput="saveSearchValue(this)" >
                                <input class="form-control mr-sm-2" type="search" placeholder="Đến" aria-label="Search" name="price_to" oninput="saveSearchValue(this)" >
                            </div>
                        </th>
                        <th>

                        </th>   
                        <th>

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
                    <c:forEach items="${listProduct}" var="list">  
                        <tr>
                            <td><img style="width:50%"src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Image_manquante_2.svg/400px-Image_manquante_2.svg.png" alt="alt"/></td>
                            <td>${list.id}</td>
                            <td>${list.product_name}</td>
                            <td class="price">${list.product_prices}</td>
                            <td>${list.created_by_userID} </td>
                            <td>${list.quantity}</td>
                            <td>
                                <div class="action_user" style="text-align: center;">
                                    <button type="button" class="mr-1 btn btn-warning view-details-btn" data-toggle="modal" data-target="#infoModal" data-product-id="${list.id}" data-product-name="${list.product_name}" data-product-prices="${list.product_prices}" data-created-by-user="${list.created_by_userID}" data-quantity="${list.quantity}" data-product-details="${list.product_details}" >
                                        <i class="fa fas fa-eye"></i> 
                                    </button>

                                    <button type="button" data-target="#buyModal" data-toggle="modal" title="Mua" class="mr-1 btn btn-success buy-btn" data-product-name-buy="${list.product_name}" data-product-prices-buy="${list.product_prices}" >
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
        <%@include file="componentPublic/modalDetails_gameAccount.jsp" %>
        <!-- modal details -->

        <!-- Modal buy -->
        <%@include file="componentPublic/modalBuy_gameAccount.jsp" %>
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
                                        var productId = sessionStorage.getItem('product_id') || "";
                                        var productName = sessionStorage.getItem('product_name') || "";
                                        var priceFrom = sessionStorage.getItem('price_from') || "";
                                        var priceTo = sessionStorage.getItem('price_to') || "";
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


                                    // fill date into modal view details - modal detals
                                    $(document).ready(function () {
                                        $('.view-details-btn').click(function () {
                                            var productId = $(this).data('product-id');
                                            var productName = $(this).data('product-name');
                                            var productPrices = $(this).data('product-prices');
                                            var createdByUser = $(this).data('created-by-user');
                                            var quantity = $(this).data('quantity');
                                            var productDetails = $(this).data('product-details');

                                            // Cập nhật nội dung modal dựa trên dữ liệu từ hàng được chọn
                                            $('#infoModal .modal-body input[name="productId"]').val(productId);
                                            $('#infoModal .modal-body input[name="productName"]').val(productName);
                                            $('#infoModal .modal-body input[name="productPrices"]').val(productPrices);
                                            $('#infoModal .modal-body input[name="createdByUser"]').val(createdByUser);
                                            $('#infoModal .modal-body input[name="quantity"]').val(quantity);
                                            $('#infoModal .modal-body input[name="productDetails"]').val(productDetails);


                                        });


                                    });

                                    // modal buy
                                    $(document).ready(function () {
                                        $('.buy-btn').click(function () {
                                            var productNameBy = $(this).data('product-name-buy');
                                            var productPricesBy = $(this).data('product-prices-buy');

                                            // Cập nhật nội dung modal dựa trên dữ liệu từ nút "Mua"
                                            $('#buyModal  .modal-body input[name="productNameBy"]').val(productNameBy);
                                            $('#buyModal  .modal-body input[name="productPricesBy"]').val(productPricesBy);

                                        });
                                    });

                                    // Bắt sự kiện click cho nút "Mua" trong modal thông tin
                                    $("#infoModal .btn-success").on("click", function () {
                                        // Lấy dữ liệu từ modal thông tin
                                        var productId = $("#infoModal input[name='productId']").val();
                                        var productName = $("#infoModal input[name='productName']").val();
                                        var createdByUser = $("#infoModal input[name='createdByUser']").val();
                                        var productPrices = $("#infoModal input[name='productPrices']").val();
                                        var quantity = $("#infoModal input[name='quantity']").val();
                                        var productDetails = $("#infoModal input[name='productDetails']").val();

                                        // Đặt dữ liệu vào modal mua
                                        // Thực hiện các bước cần thiết tại đây
                                        $('#buyModal  .modal-body input[name="productNameBy"]').val(productName);
                                        $('#buyModal  .modal-body input[name="productPricesBy"]').val(productPrices);

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