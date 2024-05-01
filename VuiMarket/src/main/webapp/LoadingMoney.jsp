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
        <link href="css/loadingmoney.css" rel="stylesheet">


    </head>

    <body>
        <!-- header -->
        <jsp:include page="componentPublic/header.jsp"></jsp:include>
            <!-- header -->

            <!-- Main Content -->
            <div class="top-up-form">
                <h2>Yêu cầu nạp tiền</h2>
                <form id="topUpForm" action="processTopUp" method="post" onsubmit="return checkPaymentMethod()">
                    <label for="paymentMethod">Chọn Phương thức (*):</label>
                    <select id="paymentMethod" name="paymentMethod">
                        <option value="bankTransfer">Chuyển khoản ngân hàng</option>
                        <option value="gateway">Cổng thanh toán</option>
                    </select>

                    <label for="amount">Số tiền (VND) (*):</label>
                    <input type="text" id="amount" name="amount" required oninput="formatNumber(); updateQRCode();">

                    <label for="description">Mô tả khoản nạp:</label>
                    <textarea rows="5" id="description" name="description" placeholder="Ghi chú khoản nạp khi cần thiết" class="form-control"  required oninput="updateQRCode();"></textarea>
                    <input type="hidden" id="rechargeCode" name="rechargeCode" value="${rechargeCode}">

                <button type="submit">Nạp tiền</button>
            </form>
        </div>
        <!-- End Main Content -->
        <!-- Thông tin chuyển khoản ngân hàng-->
        <form action="requestDeposit" method="post">
            <div class="modal fade" id="bankTransferModal" tabindex="-1" role="dialog" aria-labelledby="bankTransferModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="bankTransferModalLabel">Thông tin chuyển khoản ngân hàng</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <h4>Vui lòng chuyển khoản đúng theo nội dung bên dưới đây:</h4>

                            <img id="qrcode" src="" alt="QR Code" style="display: block; margin: auto; width: 50%">

                            <p>Số tiền: <span id="modalTransferAmount" class="red-text"></span>₫</p>
                            <input id="moneyHidden" type="type" name="money" hidden>
                            <p>
                                Nội dung chuyển khoản:
                                <span class="red-text">${rechargeCode}</span>
                                <input type="hidden" id="rechargeCode" name="rechargeCode" value="${rechargeCode}">
                            </p>
                            <p>Tên chủ tài khoản:<span class="red-text">TRẦN QUANG MẠNH</span></p>
                            <p>Số tài khoản:<span class="red-text">0333319530</span></p>
                            <p class="red-text">Ngân hàng Thương mại Cổ phần Quân đội (MB bank)</p>
                            <h4 class="red-text">Chú ý mỗi mã QRCode chỉ chuyển 1 lần duy nhất</h4>
                            <p>Nếu chuyển thủ công điền sai thông tin chuyển khoản hoặc chuyển nhiều lần cùng 1 mã giao dịch, hệ thống sẽ:</p>
                            <ul>
                                <li>Không cộng tiền vào tài khoản của quý khách</li>
                                <li>Không hoàn trả tiền</li>
                                <li>Không chịu trách nhiệm về khoản tiền chuyển nhầm hoặc chuyển thừa</li>
                            </ul>
                            <p>Vui lòng chờ đợi 1 vài phút để hệ thống cập nhật số dư sau khi đã chuyển khoản.</p>
                            <p>Nếu đợi 20 phút sau khi chuyển khoản không thấy cập nhật thông tin giao dịch, vui lòng liên hệ QTV:</p>
                            <p>Zalo/Phone <span class="red-text">(Message Only)</span>: 033.331.9530</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-success">Xác nhận đã chuyển khoản</button>
                        </div>


                    </div>
                </div>
            </div>
        </form>

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

        <!-- JavaScript for Number Formatting -->
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

                    function checkPaymentMethod() {
                        let amount = document.getElementById("amount").value;
                        amount = amount.replace(/\D/g, "");
                        if (amount < 10000) {
                            alert("Số tiền nạp tối thiểu là 10,000 VND.");
                            return false;
                        }

                        let paymentMethod = document.getElementById("paymentMethod").value;
                        if (paymentMethod === "gateway") {
                            window.location.href = "#";
                            return false;
                        } else if (paymentMethod === "bankTransfer") {
                            document.getElementById("modalTransferAmount").textContent = amount;
                            document.getElementById("moneyHidden").value = amount;
//                            document.getElementById("modalTransferDescription").textContent = document.getElementById("description").value;
                            $('#bankTransferModal').modal('show');
                            return false;
                        }
                        return true;
                    }

                    function updateQRCode() {
                        let amount = document.getElementById("amount").value;
                        let content = document.getElementById("rechargeCode").value;

                        amount = amount.replace(/\./g, "");

                        let qrCodeUrl = "https://img.vietqr.io/image/MBbank-0333319530-compact.png?amount=" + encodeURIComponent(amount) + "&addInfo=" + encodeURIComponent(content);
                        document.getElementById("qrcode").src = qrCodeUrl;
                    }

                    // Call updateQRCode on page load
                    updateQRCode();
        </script>
    </body>

</html>
