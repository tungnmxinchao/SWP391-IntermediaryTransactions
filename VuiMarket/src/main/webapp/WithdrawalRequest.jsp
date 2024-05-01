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
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
              rel="stylesheet">

        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
        <link href="css/withdrawalrequest.css" rel="stylesheet">
        <!-- SweetAlert2 CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@10/dist/sweetalert2.min.css">


    </head>

    <body>
        <!-- header -->
        <jsp:include page="componentPublic/header.jsp"></jsp:include>
            <!-- header -->

            <!--main content-->
            <div class="container">
                <h1>Yêu Cầu Rút Tiền</h1>
                <form action="withdrawalRequest" method="post" class="withdrawal-form" onsubmit="return validateForm();">
                    <span class="indicator__area" id="homeUserBalance" hidden>${acc.balance}</span>
                <div class="form-group">
                    <label for="amount">Mã giao dịch</label>
                    <input type="text" id="withdrawCode" name="withdrawCode" value="${withdrawCode}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="amount">Số tiền rút (*)</label>
                    <input type="text" id="amount" name="amount" required oninput="formatNumber();">
                </div>
                <div class="form-group">
                    <label for="accountNumber">Số tài khoản (*)</label>
                    <input type="text" id="accountNumber" name="accountNumber" required>
                </div>
                <div class="form-group">
                    <label for="accountOwner">Chủ tài khoản (*)</label>
                    <input type="text" id="accountOwner" name="accountOwner" required>
                </div>
                <div class="form-group">
                    <label for="bankName">Tên ngân hàng (*)</label>
                    <input type="text" id="bankName" name="bankName" placeholder="VD: Tiên Phong Bank (TPB), Vietcombank (VCB)" required>
                </div>
                <div class="form-group">
                    <button type="submit" class="submit-btn">Gửi Yêu Cầu</button>
                    <a href="home" class="cancel-btn">Hủy</a>
                </div>
            </form>
        </div>
        <!--main content end-->

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
        <!-- SweetAlert2 JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>


        <script>
                    function formatNumber() {
                        // Lấy giá trị từ input
                        let input = document.getElementById("amount").value;
                        // Loại bỏ tất cả các ký tự không phải là số
                        let number = input.replace(/\D/g, "");
                        // Định dạng số với dấu phẩy
                        let formattedNumber = new Intl.NumberFormat().format(number);
                        // Gán giá trị đã định dạng trở lại vào input
                        document.getElementById("amount").value = formattedNumber;
                    }

                    function validateForm() {
                        let inputAmount = document.getElementById("amount").value;
                        let balance = document.getElementById("homeUserBalance").textContent;
                        balance = parseInt(balance.replace(/\D/g, ""));
                        let amount = parseInt(inputAmount.replace(/\D/g, ""));

                        // Kiểm tra nếu số tiền nhập vào lớn hơn số dư hiện có
                        if (amount > balance) {
                            // Hiển thị cửa sổ thông báo với SweetAlert2
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: 'Không đủ tiền để rút!',
                            });
                            return false;
                        }

                        // Kiểm tra nếu số tiền nhập vào nhỏ hơn 50,000
                        if (amount < 50000) {
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: 'Số tiền rút ít nhất phải là 50,000 VND.',
                            });
                            return false;
                        }

                        Swal.fire({
                            icon: 'info',
                            title: 'Xác nhận',
                            text: 'Xác nhận đúng thông tin TK Ngân hàng? Nếu thông tin bị sai thì bạn sẽ mất 10% khoản tiền yêu cầu rút.',
                            showCancelButton: true,
                            confirmButtonText: 'OK',
                            cancelButtonText: 'Cancel'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                document.querySelector('.withdrawal-form').submit();
                            } else {
                                return false;
                            }
                        });

                        return false; // Trả về false để ngăn chặn biểu mẫu được gửi tự động
                    }
        </script>

    </body>

</html>
