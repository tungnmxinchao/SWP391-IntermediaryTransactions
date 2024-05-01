<%-- 
    Document   : MySaleOrder
    Created on : Feb 4, 2024, 6:51:02 PM
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
                        <h1>Đơn Mua Của Tôi</h1>
                    </section>
                    <section class="table__body">
                        <table>
                            <thead>
                                <tr>
                                    <th> Mã Trung Gian <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Trạng Thái <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Người Bán  <span class="icon-arrow">&UpArrow;</span></th>
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
                            <c:forEach items="${listMyPurchase}" var="listMyPurchase">
                                <tr>
                                    <td> ${listMyPurchase.id} </td>
                                    <td>${listMyPurchase.status}</td>
                                    <td>${listMyPurchase.createdBy}</td>
                                    <td> ${listMyPurchase.title} </td>
                                    <td>
                                        <p class="status delivered">${listMyPurchase.isPublic}</p>
                                    </td>
                                    <td> <strong> ${listMyPurchase.moneyValue} </strong></td>
                                    <td> ${listMyPurchase.isSellerChargeFee} </td>
                                    <td> ${listMyPurchase.createdAt} </td>
                                    <td> ${listMyPurchase.updatedAt} </td>
                                    <td> 
                                        <button class="details-button" data-toggle="modal" data-target="#viewPurchaseOrder" data-buyerId="${listMyPurchase.idBuyer}" data-id="${listMyPurchase.id}" data-sellerId="${listMyPurchase.idUser}">
                                            <i class="fa fa-eye"></i>
                                        </button>
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

        <jsp:include page="componentUser/modalViewDetailsMyPurchaseOrder.jsp"></jsp:include>

        <script src="js/scriptmysaleorder.js"></script>
        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>

        <script>
            var wsUrl;
            if (window.location.protocol === 'http:') {
                wsUrl = 'ws://';
            } else {
                wsUrl = 'wss://';
            }
            var ws = new WebSocket(wsUrl + window.location.host + "/VuiMarket/notification");

            ws.onopen = function () {
                console.log("WebSocket connected");
            };

            ws.onmessage = function (event) {
                const eventData = JSON.parse(event.data);
                const userAccessUid = document.getElementById('nguoiban').value;
                const buyerId = document.getElementById('idnguoimua').value;
                if (String(userAccessUid) === String(eventData.idUser) || String(buyerId) === String(eventData.buyerId)) {
                    Toastify({
                        text: eventData.message,
                        duration: 3000,
                        position: "right",
                        backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)"
                    }).showToast();
                }
            };

            document.querySelectorAll('.details-button').forEach(button => {
                button.addEventListener('click', function () {
                    const orderId = this.getAttribute('data-id');
                    const sellerId = this.getAttribute('data-sellerId');
                    const buyerId = this.getAttribute('data-buyerId');

                    fetch('viewPurchaseOrder', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({id: orderId, sellerId: sellerId, buyerId: buyerId})
                    }).then(response => response.json()) // Parse response as JSON
                            .then(data => {
                                console.log("Data received:", data);
                                // Call fetchInfor with the received data
                                fetchInfor(data);
                            }).catch(error => {
                        console.error('Error:', error);
                    });
                });
            });

            function fetchInfor(data) {

                document.getElementById('matrunggian').value = data.id;
                document.getElementById('idnguoiban').value = data.idUser;
                document.getElementById('nguoiban').value = data.createdBy;
                document.getElementById('idnguoimua').value = data.idBuyer;
                document.getElementById('nguoimua').value = data.buyer;
                document.getElementById('trangthai').value = data.status;
                document.getElementById('chudetg').value = data.title;
                document.getElementById('giatientg').value = data.moneyValue;
                document.getElementById('phitrunggian').value = data.feeOnSuccess;
                document.getElementById('totalFeeOfBuyer').value = data.totalMoneyForBuyer;
                document.getElementById('moneyforSeller').value = data.sellerReceivedOnSuccess;
                document.getElementById('createdDate').value = data.createdAt;
                document.getElementById('updateDate').value = data.updatedAt;
                document.getElementById('lienhetg').value = data.contact;
                document.getElementById('mota-editortg').innerHTML = data.description;
                document.getElementById('noidungantg-editor').innerHTML = data.hiddenValue;

                const benchiuphi1tg = document.getElementById('benchiuphi1tg');
                const benchiuphi2tg = document.getElementById('benchiuphi2tg');

                if (data.isSellerChargeFee === "Bên Bán") {
                    benchiuphi1tg.checked = true;
                    benchiuphi1tg.parentNode.classList.add('active');
                    benchiuphi2tg.parentNode.classList.remove('active');
                } else if (data.isSellerChargeFee === "Bên Mua") {
                    benchiuphi2tg.checked = true;
                    benchiuphi2tg.parentNode.classList.add('active');
                    benchiuphi1tg.parentNode.classList.remove('active');
                }

                const hiencongkhai1tg = document.getElementById('hiencongkhai1tg');
                const hiencongkhai2tg = document.getElementById('hiencongkhai2tg');

                if (data.isPublic === "Công Khai") {
                    hiencongkhai1tg.checked = true;
                    hiencongkhai1tg.parentNode.classList.add('active');
                    hiencongkhai2tg.parentNode.classList.remove('active');
                } else if (data.isPublic === "Riêng Tư") {
                    hiencongkhai2tg.checked = true;
                    hiencongkhai2tg.parentNode.classList.add('active');
                    hiencongkhai1tg.parentNode.classList.remove('active');
                }

                var linkElement = document.getElementById('linkSharetg');
                linkElement.setAttribute('href', data.shareLink);
                linkElement.textContent = data.shareLink;

                const statusComplain = document.getElementById('trangthai').value;
                if (statusComplain === "Bên mua đang kiểm tra hàng") {
                    document.getElementById('confirmReceiveButton').style.display = 'inline-block';
                    document.getElementById('complaintButton').style.display = 'inline-block';
                    document.getElementById('callAdmin').style.display = 'none';
                }
                if (statusComplain === "Bên mua khiếu nại sản phẩm") {
                    document.getElementById('callAdmin').style.display = 'inline-block';
                    document.getElementById('confirmReceiveButton').style.display = 'none';
                    document.getElementById('complaintButton').style.display = 'none';
                }
                if (statusComplain === "Chờ bên mua xác nhận khiếu nại không đúng") {
                    document.getElementById('confirmReceiveButton').style.display = 'inline-block';
                    document.getElementById('callAdmin').style.display = 'inline-block';
                    document.getElementById('complaintButton').style.display = 'none';
                }
                if (statusComplain === "Yêu cầu quản trị viên trung gian") {
                    document.getElementById('confirmReceiveButton').style.display = 'none';
                    document.getElementById('callAdmin').style.display = 'none';
                    document.getElementById('complaintButton').style.display = 'none';
                }
                if (statusComplain === "Hoàn thành") {
                    document.getElementById('confirmReceiveButton').style.display = 'none';
                    document.getElementById('callAdmin').style.display = 'none';
                    document.getElementById('complaintButton').style.display = 'none';
                }
                if (statusComplain === "Hủy") {
                    document.getElementById('confirmReceiveButton').style.display = 'none';
                    document.getElementById('callAdmin').style.display = 'none';
                    document.getElementById('complaintButton').style.display = 'none';
                }
                
            }

            function displayModalConfirm(message) {
                // Set the message content in the modal body
                const modalBody = document.querySelector('#confirmationModal .modal-body');
                modalBody.innerHTML = message;

                // Show the modal
                $('#confirmationModal').modal('show');
            }

            document.addEventListener('DOMContentLoaded', function () {
                const confirmReceiveButton = document.getElementById('confirmReceiveButton');
                if (confirmReceiveButton) {
                    confirmReceiveButton.addEventListener('click', function () {
                        displayModalConfirm('Xác nhận đơn hàng đúng mô tả?');
                        document.getElementById('confirmButton').addEventListener('click', function () {
                            // Lấy giá trị từ RecordMyOrder và gọi hàm để gửi dữ liệu đến servlet
                            const orderId_buyerConfirmOrderTrue = document.getElementById('matrunggian').value;
                            const userId_buyerConfirmOrderTrue = document.getElementById('idnguoiban').value;
                            const buyerId_buyerConfirmOrderTrue = document.getElementById('idnguoimua').value;
                            sendDataToServlet(orderId_buyerConfirmOrderTrue, userId_buyerConfirmOrderTrue, buyerId_buyerConfirmOrderTrue);
                            $('#confirmationModal').modal('hide');
                        });

                    });
                }
            });

            function sendDataToServlet(orderId, userId, buyerId) {
                // Tạo đối tượng chứa dữ liệu cần gửi
                const data = {
                    orderId: orderId,
                    userId: userId,
                    buyerId: buyerId
                };

                // Gửi request fetch với method POST và body là dữ liệu đã chuẩn bị
                fetch('conFirmOrder', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Network response was not ok');
                            }
                            return response.json();
                        })
                        .then(data => {
                            // Xử lý dữ liệu trả về từ servlet nếu cần
                            console.log('Response from servlet:', data);
                            ws.send(JSON.stringify({idUser: data.idUser, buyerId: data.buyerId, message: data.message}));
                        })
                        .catch(error => {
                            console.error('There was a problem with your fetch operation:', error);
                        });
            }

            document.addEventListener('DOMContentLoaded', function () {
                const complainButton = document.getElementById('complaintButton');
                if (complainButton) {
                    complainButton.addEventListener('click', function () {
                        displayModalConfirm('Sản phẩm không đúng mô tả, bạn muốn khiếu nại ?');

                        document.getElementById('confirmButton').addEventListener('click', function () {
                            // Lấy giá trị từ RecordMyOrder và gọi hàm để gửi dữ liệu đến servlet
                            const orderId_buyerComlain = document.getElementById('matrunggian').value;
                            const userId_buyerComplain = document.getElementById('idnguoiban').value;
                            const buyerId_buyerComplain = document.getElementById('idnguoimua').value;
                            sendDataComplain(orderId_buyerComlain, userId_buyerComplain, buyerId_buyerComplain);
                            $('#confirmationModal').modal('hide');
                        });
                    });
                }
            });
            function sendDataComplain(orderId, userId, buyerId) {
                // Tạo đối tượng chứa dữ liệu cần gửi
                const data = {
                    orderId: orderId,
                    userId: userId,
                    buyerId: buyerId
                };

                // Gửi request fetch với method POST và body là dữ liệu đã chuẩn bị
                fetch('complainOrder', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Network response was not ok');
                            }
                            return response.json();
                        })
                        .then(data => {
                            // Xử lý dữ liệu trả về từ servlet nếu cần
                            console.log('Response from servlet:', data);
                            ws.send(JSON.stringify({idUser: data.idUser, buyerId: data.buyerId, message: data.message}));
                        })
                        .catch(error => {
                            console.error('There was a problem with your fetch operation:', error);
                        });
            }

            document.addEventListener('DOMContentLoaded', function () {
                const callAdminOrder = document.getElementById('callAdmin');
                if (callAdminOrder) {
                    callAdminOrder.addEventListener('click', function () {
                        displayModalConfirm('Admin sẽ thu phí bên bán và mua 50k là phí xử lý, vui lòng đọc lại kĩ thông tin sản phẩm !');

                        document.getElementById('confirmButton').addEventListener('click', function () {
                            // Lấy giá trị từ RecordMyOrder và gọi hàm để gửi dữ liệu đến servlet
                            const orderId_BuyerCallAdmin = document.getElementById('matrunggian').value;
                            const userId_BuyerCallADmin = document.getElementById('idnguoiban').value;
                            const buyerId_BuyerCallAdmin = document.getElementById('idnguoimua').value;
                            sendDataCallAdmin(orderId_BuyerCallAdmin, userId_BuyerCallADmin, buyerId_BuyerCallAdmin);
                            $('#confirmationModal').modal('hide');
                        });
                    });
                }
            });

            function sendDataCallAdmin(orderId, userId, buyerId) {
                // Tạo đối tượng chứa dữ liệu cần gửi
                const data = {
                    orderId: orderId,
                    userId: userId,
                    buyerId: buyerId
                };

                // Gửi request fetch với method POST và body là dữ liệu đã chuẩn bị
                fetch('callAdmin', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Network response was not ok');
                            }
                            return response.json();
                        })
                        .then(data => {
                            // Xử lý dữ liệu trả về từ servlet nếu cần
                            console.log('Response from servlet:', data);
                            ws.send(JSON.stringify({idUser: data.idUser, buyerId: data.buyerId, message: data.message}));
                        })
                        .catch(error => {
                            console.error('There was a problem with your fetch operation:', error);
                        });
            }
        </script>
    </body>

</html>
