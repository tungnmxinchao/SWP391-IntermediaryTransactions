
<%@page import="entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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


        <!-- Libraries Stylesheet -->

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
        <style>
            body {
                background-color: #f0f0f0; /* Màu xám nhạt */
            }
        </style>
    </head>

    <body>
        <!-- header -->
        <jsp:include page="componentPublic/header.jsp"></jsp:include>
            <!-- header -->

            <!-- Navbar Start -->
            <div class="container-fluid mb-5">
                <div class="row border-top px-xl-5">

                    <div class="col-lg-12">

                        <div id="header-carousel" class="carousel slide" data-ride="carousel">
                            <div class="carousel-inner">
                                <div class="carousel-item active" style="height: 410px;">
                                    <img class="img-fluid" src="https://btnmt.1cdn.vn/2022/08/30/blogtienao.zcc.com.vn-chuyen-doi-so-chia-khoa-cua-nganh-tai-nguyen-moi-truong-digital-transformation-solutions.jpg" alt="Image">
                                    <div class="carousel-caption d-flex flex-column align-items-center justify-content-center">
                                        <div class="p-3" style="max-width: 700px;">

                                            <h3 class="display-4 text-white font-weight-semi-bold mb-4">SÀN GIAO DỊCH TRUNG GIAN</h3>
                                            <a href="" class="btn btn-light py-2 px-3">Shop Now</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="carousel-item" style="height: 410px;">
                                    <img class="img-fluid" src="https://media.vneconomy.vn/w800/images/upload/2021/10/20/chuyen-doi-so-chi-so1.jpg" alt="Image">
                                    <div class="carousel-caption d-flex flex-column align-items-center justify-content-center">
                                        <div class="p-3" style="max-width: 700px;">

                                            <h3 class="display-4 text-white font-weight-semi-bold mb-4">BÁN TÀI NGUYÊN SỐ</h3>
                                            <a href="" class="btn btn-light py-2 px-3">Shop Now</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <a class="carousel-control-prev" href="#header-carousel" data-slide="prev">
                                <div class="btn btn-dark" style="width: 45px; height: 45px;">
                                    <span class="carousel-control-prev-icon mb-n2"></span>
                                </div>
                            </a>
                            <a class="carousel-control-next" href="#header-carousel" data-slide="next">
                                <div class="btn btn-dark" style="width: 45px; height: 45px;">
                                    <span class="carousel-control-next-icon mb-n2"></span>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Navbar End -->

            <!-- Featured Start -->
            <div class="heading_s2 text-center" style="margin-bottom: 5px; padding-bottom: 5px; position: relative;">
                <h2>ĐA DẠNG SẢN PHẨM</h2>
                <hr class="color-main" style="background-color: rgb(232, 65, 24); color: rgb(255, 255, 255); width: 10%; height: 2px; margin-top: 5px;">
            </div>

            <!--Featured End--> 
            <div class="container-fluid fix_3item">
                <div class="row">
                    <div class="col-md-4">
                        <!--<a rel="noopener noreferrer" href="gameAccount?action=category&categoryId=1" target="_self">-->
                        <div class="game-card">
                            <div class ="img_item">
                                <img src="https://zozila.com/wp-content/uploads/2023/06/fm-50_1-8_12_1-11.png" alt="img">
                            </div>
                            <div class="description">
                                <div style="align-items: flex-end;">
                                    <h1>Tài khoản game</h1>
                                    <!--<p>Truy cập &gt;&gt;</p>-->
                                </div>
                            </div>
                        </div>
                        </a>
                    </div>

                    <div class="col-md-4">
                        <!--<a rel="noopener noreferrer" href="gameAccount?action=category&categoryId=2" target="_self">-->
                        <div class="game-card">
                            <div class ="img_item">
                                <img src="https://avatars.githubusercontent.com/u/24659713?v=4" alt="img">
                            </div>
                            <div class="description">
                                <div style="align-items: flex-end;">
                                    <h1>Source code</h1>
                                    <!--<p>Truy cập &gt;&gt;</p>-->
                                </div>
                            </div>
                        </div>
                        </a>
                    </div>

                    <div class="col-md-4">
                        <!--<a rel="noopener noreferrer" href="gameAccount?action=category&categoryId=3" target="_self">-->
                        <div class="game-card">
                            <div class ="img_item">
                                <img src="https://d20ohkaloyme4g.cloudfront.net/img/document_thumbnails/4ffa9c419b1c5066d0739af7e29389ae/thumb_1200_1553.png" alt="img">
                            </div>
                            <div class="description">
                                <div style="align-items: flex-end;">
                                    <h1>Tài liệu học tập</h1>
                                    <!--<p>Truy cập &gt;&gt;</p>-->
                                </div>
                            </div>
                        </div>
                        </a>
                    </div>
                </div>
            </div>
            <!--Products Start--> 
            <div class="container-fluid pt-5">
                <div class="heading_s2 text-center" style="margin-bottom: 5px; padding-bottom: 40px; position: relative;">
                    <h2>SÀN TRUNG GIAN</h2>
                    <hr class="color-main" style="background-color: rgb(232, 65, 24); color: rgb(255, 255, 255); width: 10%; height: 2px; margin-top: 5px;">
                </div>

                <div class="row px-xl-5 pb-3 justify-content-center">
                    <div class="col-md-4">
                        <a rel="noopener noreferrer" href="publicOrders" target="_blank">
                            <div class="game-card">
                                <div class ="img_item">
                                    <img src="https://vieclam123.vn/ckfinder/userfiles/images/images/giao-dich-trung-gian.jpg" alt="img">
                                </div>
                                <div class="description">
                                    <div style="align-items: flex-end;">
                                        <h1>Trung gian</h1>
                                        <p>Truy cập &gt;&gt;</p>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                
                    <div class="col-md-4">
                        <a rel="noopener noreferrer" href="mySaleOrder" target="_blank">
                            <div class="game-card">
                                <div class ="img_item">
                                    <img src="img/banhang.png" alt="img">
                                </div>
                                <div class="description">
                                    <div style="align-items: flex-end;">
                                        <h1>Đơn bán của tôi</h1>
                                        <p>Truy cập &gt;&gt;</p>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
               
                    <div class="col-md-4">
                        <a rel="noopener noreferrer" href="myPurchaseOrder" target="_blank">
                            <div class="game-card">
                                <div class ="img_item">
                                    <img src="img/muahang.jpg" alt="img">
                                </div>
                                <div class="description">             
                                    <div style="align-items: flex-end;">
                                        <h1>Đơn mua của tôi</h1>
                                        <p>Truy cập &gt;&gt;</p>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
            </div>
        </div>
        <!--Products End-->

        <!--quan li thanh toan start-->
            <div class="container-fluid pt-5">
                <div class="heading_s2 text-center" style="margin-bottom: 5px; padding-bottom: 40px; position: relative;">
                    <h2>QUẢN LÝ THANH TOÁN</h2>
                    <hr class="color-main" style="background-color: rgb(232, 65, 24); color: rgb(255, 255, 255); width: 10%; height: 2px; margin-top: 5px;">
                </div>

                <div class="row px-xl-5 pb-3 justify-content-center">
                    <div class="col-md-4">
                        <a rel="noopener noreferrer" href="loadingMoney" target="_blank">
                            <div class="game-card">
                                <div class ="img_item">
                                    <img src="img/naptien.jpg" alt="img">
                                </div>
                                <div class="description">
                                    <div style="align-items: flex-end;">
                                        <h1>Nạp tiền</h1>
                                        <p>Truy cập &gt;&gt;</p>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>

                    <div class="col-md-4">
                        <a rel="noopener noreferrer" href="transactionHistory" target="_blank">
                            <div class="game-card">
                                <div class ="img_item">
                                    <img src="img/lsgiaodich.jpg" alt="img">
                                </div>
                                <div class="description">
                                    <div style="align-items: flex-end;">
                                        <h1>Lịch sử giao dịch</h1>
                                        <p>Truy cập &gt;&gt;</p>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>

                    <div class="col-md-4">
                        <a rel="noopener noreferrer" href="withdrawalRequest" target="_blank">
                            <div class="game-card">
                                <div class ="img_item">
                                    <img src="img/ruttien.png" alt="img">
                                </div>
                                <div class="description">             
                                    <div style="align-items: flex-end;">
                                        <h1>Yêu cầu rút tiền</h1>
                                        <p>Truy cập &gt;&gt;</p>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <!--quan li thanh toan end-->

            <!-- Footer Start -->
        <jsp:include page="componentPublic/footer.jsp"></jsp:include>
        <!-- Footer End -->


        <!-- Back to Top -->
        <a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>


        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
    </body>

</html>