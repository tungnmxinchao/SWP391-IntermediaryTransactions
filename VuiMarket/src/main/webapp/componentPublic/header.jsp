<%-- 
    Document   : header
    Created on : Jan 27, 2024, 7:30:50 PM
    Author     : TNO
--%>

<%@page import="entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" integrity="sha384-oRmgxG3EL6AqMl4P96PviVSD5M3N1VlSkDJaWPPjlGZaZV3OnzWFA9L7yVLfojZw" crossorigin="anonymous">

<style>
    .narbar {
        background-color: #87CEEB;
    }
</style>
<!-- Topbar Start -->
<div class="container-fluid">
    <div class="row align-items-center py-3 px-xl-5">
        <div class="col-lg-3 d-none d-lg-block">
            <a href="home" class="text-decoration-none">
                <div class="group_logo_left">
                    <div class="logo_header">
                        <img src="img/logo.png" alt="img">
                    </div>
                    <div class="text_header_left">
                        <h2>VuiMarket</h2>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-lg-6 col-6 text-left">

        </div>
        <div class="col-lg-3 col-6 text-center">
            <c:if test="${empty sessionScope.acc}">
                <a href="login" class="btn btn-primary"><i class="fas fa-sign-in-alt"></i> Đăng nhập</a>
            </c:if>
            <c:if test="${not empty sessionScope.acc}">
                <span class="indicator__area" id="homeUserBalance">${balanceUser} VNÐ</span>
                <div class="btn-group">
                    <button type="button" class="btn border dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-user"></i>
                        <span class="badge">0</span>
                    </button>
                    <div class="dropdown-menu dropdown-menu-right">
                        <c:if test="${sessionScope.acc.rolename eq 'admin'}">
                            <a class="dropdown-item" href="rechargeUsers"><i class="fas fa-money-check-alt"></i>Thanh toán cho người dùng</a>
                        </c:if>
                        <c:if test="${sessionScope.acc.rolename eq 'admin'}">
                            <a class="dropdown-item" href="userWithdrawal"><i class="fas fa-hand-holding-usd"></i>Xem yêu cầu rút tiền</a>
                        </c:if>
                        <c:if test="${sessionScope.acc.rolename eq 'admin'}">
                            <a class="dropdown-item" href="adminHandling"><i class="fas fa-user-cog"></i>Xử lý khiếu nại</a>
                        </c:if>

                        <a class="dropdown-item" href="userprofile"><i class="fas fa-user"></i> Thông tin người dùng</a>
                        <a class="dropdown-item" href="ChangePass.jsp"><i class="fas fa-key"></i> Đổi mật khẩu</a>
                        <a class="dropdown-item" href="logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
<!-- Topbar End -->


<!-- Navbar Start -->
<div class="container-fluid mb-5">
    <div class="row border-top px-xl-5">
        <div class="col-lg-12">
            <nav class="navbar navbar-expand-lg bg-light navbar-light py-3 py-lg-0 px-0">
                <a href="" class="text-decoration-none d-block d-lg-none"></a>
                <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-between narbar" id="navbarCollapse">
                    <div class="navbar-nav mr-auto py-0">
                        <a href="home" class="nav-item nav-link "><i class="fas fa-home"></i> Home</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown"><i class="fas fa-handshake"></i> Trung gian</a>
                            <div class="dropdown-menu rounded-0 m-0">
                                <a href="publicOrders" class="dropdown-item"><i class="fas fa-store-alt"></i> Chợ trung gian</a>
                                <a href="myPurchaseOrder" class="dropdown-item"><i class="fas fa-shopping-basket"></i> Đơn mua của tôi</a>
                                <a href="mySaleOrder" class="dropdown-item"><i class="fas fa-dollar-sign"></i> Đơn bán của tôi</a>
                            </div>
                        </div>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown"><i class="fas fa-credit-card"></i> Quản lý thanh toán</a>
                            <div class="dropdown-menu rounded-0 m-0">
                                <a href="loadingMoney" class="dropdown-item"><i class="fas fa-money-bill-wave"></i> Nạp tiền</a>
                                <a href="transactionHistory" class="dropdown-item"><i class="fas fa-history"></i> Lịch sử giao dịch</a>
                                <a href="withdrawalRequest" class="dropdown-item"><i class="fas fa-hand-holding-usd"></i> Yêu cầu rút tiền</a>
                            </div>
                        </div>
                        <c:if test="${sessionScope.acc.rolename eq 'admin'}">
                            <a class="nav-item nav-link " href="rechargeUsers"><i class="fas fa-money-check-alt"></i>Thanh toán cho người dùng</a>
                        </c:if>
                        <c:if test="${sessionScope.acc.rolename eq 'admin'}">
                            <a class="nav-item nav-link " href="userWithdrawal"><i class="fas fa-hand-holding-usd"></i>Xem yêu cầu rút tiền</a>
                        </c:if>
                        <c:if test="${sessionScope.acc.rolename eq 'admin'}">
                            <a class="nav-item nav-link " href="adminHandling"><i class="fas fa-user-cog"></i>Xử lý khiếu nại</a>
                        </c:if>
                    </div>
                </div>
            </nav>
        </div>
    </div>
</div>
<!-- Navbar End -->
