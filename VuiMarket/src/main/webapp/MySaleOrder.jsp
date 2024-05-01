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
                        <h1>Đơn Bán Của Tôi</h1>
                        <div class="d-flex">
                            <li class="breadcrumb-item ml-auto">
                                <button class="btn btn-success" type="button" data-toggle="modal" data-target="#addNewOrder">
                                    <i class="fas fa-plus"></i> Thêm Mới
                                </button>
                            </li>
                        </div>

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
                                        <button class="details-button" data-toggle="modal" data-target="#viewDetailOrder" data-buyerId="${listMyOrder.buyer}" data-updatedable="${listMyOrder.updatedable}" data-id="${listMyOrder.id}" data-username="${listMyOrder.createdBy}" data-userId="${listMyOrder.idUser}" >
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

        <%@include file="componentUser/modalAddNew.jsp" %>

        <jsp:include page="componentUser/modalViewDetailsMyOrder.jsp"></jsp:include>





        <script src="js/scriptmysaleorder.js"></script>
        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>


        <script src="https://cdn.tiny.cloud/1/3dnf4g9cgv4sfog9pgz0n3f33fqrskhx8tz3ejpcmhn480gh/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>



        <script>
            tinymce.init({
                selector: '#mota-editor, #noidungan-editor, #mota-editortg, #noidungantg-editor',
                height: 300,
                plugins: 'anchor autolink charmap codesample emoticons image link lists media searchreplace table visualblocks wordcount linkchecker',
                toolbar: 'undo redo | formatselect | bold italic backcolor | \
                alignleft aligncenter alignright alignjustify | \
                bullist numlist outdent indent | removeformat | help'
            });

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
                const userAccessUid = document.getElementById('idnguoiban').value;
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

            document.addEventListener("DOMContentLoaded", function () {

                if (localStorage.getItem("notificationMessage")) {

                    Toastify({
                        text: localStorage.getItem("notificationMessage"),
                        duration: 3000,
                        position: "right",
                        backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)"
                    }).showToast();

                    localStorage.removeItem("notificationMessage");
                }
            });

            document.addEventListener("DOMContentLoaded", function () {
                document.getElementById("btnThemMoiSP").addEventListener("click", function () {
                    // Hiển thị modal xác nhận
                    $('#confirmModal').modal('show');

                    // Xử lý sự kiện khi người dùng xác nhận
                    document.getElementById("confirmBtn").addEventListener("click", function () {
                        // Lấy giá trị của các trường input
                        var chudeValue = document.getElementById("chude").value;
                        var giatienValue = document.getElementById("giatien").value.replace(/[,.]/g, '');
                        var lienheValue = document.getElementById("lienhe").value;
                        var motaValue = tinymce.get('mota-editor').getContent();
                        var noidunganValue = tinymce.get('noidungan-editor').getContent();
                        var hiencongkhaiValue = document.querySelector('input[name="hiencongkhai"]:checked').value;
                        var isSellerFeeValue = document.querySelector('input[name="isSellerFee"]:checked').value;

                        // Kiểm tra nếu bất kỳ trường nào rỗng
                        if (!chudeValue || !giatienValue || !lienheValue || !motaValue || !noidunganValue) {
                            alert("Vui lòng điền đầy đủ thông tin.");
                            return; // Ngăn chặn gửi form nếu có trường rỗng
                        }

                        // Nếu không có trường nào rỗng, tiếp tục gửi form
                        var data = {
                            "chude": chudeValue,
                            "giatien": giatienValue,
                            "lienhe": lienheValue,
                            "mota": motaValue,
                            "noidungan": noidunganValue,
                            "hiencongkhai": hiencongkhaiValue,
                            "isSellerFee": isSellerFeeValue
                        };

                        // Gửi dữ liệu đi
                        fetch("inforAddNew", {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify(data)
                        })
                                .then(response => response.json())
                                .then(data => {
                                    console.log("Data received:", data);
                                    localStorage.setItem("notificationMessage", data.message);

                                    window.location.href = "http://localhost:8080/VuiMarket/mySaleOrder";
                                })
                                .catch(error => {
                                    console.error("Error:", error);
                                });
                    });
                });
                document.querySelector("#confirmModal .modal-footer .cancel-button").addEventListener("click", function (event) {
                    event.stopPropagation(); // Ngăn chặn sự kiện lan truyền lên modal addNewOrder
                });

                // Đóng modal confirmModal khi click vào nút "Hủy"
                document.querySelector("#confirmModal .modal-footer .cancel-button").addEventListener("click", function () {
                    $('#confirmModal').modal('hide');
                });

                // Khi modal confirmModal được đóng, focus trở lại modal addNewOrder
                $('#confirmModal').on('hidden.bs.modal', function () {
                    $('#addNewOrder').css('overflow-y', 'auto');
                });
            });

            document.querySelectorAll('.details-button').forEach(button => {
                button.addEventListener('click', function () {
                    const orderId = this.getAttribute('data-id');
                    const user_name = this.getAttribute('data-username');
                    const user_id = this.getAttribute('data-userId');
                    const updatedableValue = this.getAttribute('data-updatedable');

                    if (updatedableValue === "0") {
                        document.getElementById('updateOrder').style.display = 'none';

                        document.getElementById('chudetg').disabled = true;
                        document.getElementById('giatientg').disabled = true;
                        document.getElementById('benchiuphi1tg').disabled = true;
                        document.getElementById('benchiuphi2tg').disabled = true;
                        tinymce.get('mota-editortg').getBody().setAttribute('contenteditable', false); // Vô hiệu hóa mô tả của TinyMCE
                        document.getElementById('lienhetg').disabled = true;
                        tinymce.get('noidungantg-editor').getBody().setAttribute('contenteditable', false); // Vô hiệu hóa nội dung của TinyMCE
                        document.getElementById('hiencongkhai1tg').disabled = true;
                        document.getElementById('hiencongkhai2tg').disabled = true;
                    } else {
                        document.getElementById('updateOrder').style.display = 'block';
                        document.getElementById('chudetg').disabled = false;
                        document.getElementById('giatientg').disabled = false;
                        document.getElementById('benchiuphi1tg').disabled = false;
                        document.getElementById('benchiuphi2tg').disabled = false;
                        tinymce.get('mota-editortg').getBody().setAttribute('contenteditable', true); // Kích hoạt mô tả của TinyMCE
                        document.getElementById('lienhetg').disabled = false;
                        tinymce.get('noidungantg-editor').getBody().setAttribute('contenteditable', true); // Kích hoạt nội dung của TinyMCE
                        document.getElementById('hiencongkhai1tg').disabled = false;
                        document.getElementById('hiencongkhai2tg').disabled = false;
                    }
                    fetch('viewDetailsMyOrder', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({id: orderId, userCreated: user_name, userId: user_id})
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
                tinymce.get('mota-editortg').setContent(data.description);
                tinymce.get('noidungantg-editor').setContent(data.hiddenValue);


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
                if (statusComplain === "Bên mua khiếu nại sản phẩm") {
                    document.getElementById('sellerConfirmOrderFalse').style.display = 'inline-block';
                    document.getElementById('sellerConfirmOrderCorrect').style.display = 'inline-block';
                    document.getElementById('callAdmin').style.display = 'inline-block';
                } else {
                    document.getElementById('sellerConfirmOrderFalse').style.display = 'none';
                    document.getElementById('sellerConfirmOrderCorrect').style.display = 'none';
                    document.getElementById('callAdmin').style.display = 'none';
                }
            }

            document.addEventListener('DOMContentLoaded', function () {
                // Add event listener to the "Cập nhật" button
                document.getElementById('updateOrder').addEventListener('click', function () {
                    // Gather data from input fields and radio buttons
                    const matrunggianValue = document.getElementById('matrunggian').value;
                    const chudeValue = document.getElementById('chudetg').value;
                    const isSellerFeeValue = document.querySelector('input[name="isSellerFeeTg"]:checked').value;
                    const giatienValue = document.getElementById('giatientg').value;
                    const lienheValue = document.getElementById('lienhetg').value;
                    const hiencongkhaiValue = document.querySelector('input[name="hiencongkhaiTg"]:checked').value;
                    const motaValue = tinymce.get('mota-editortg').getContent();
                    const noidunganValue = tinymce.get('noidungantg-editor').getContent();

                    // Prepare data to send
                    const data = {
                        idOrder: matrunggianValue,
                        chude: chudeValue,
                        isSellerFee: isSellerFeeValue,
                        contact: lienheValue,
                        moneyValue: giatienValue,
                        isPublic: hiencongkhaiValue,
                        description: motaValue,
                        hiddenValue: noidunganValue
                    };

                    // Send data using fetch API
                    fetch('updateOrder', {
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
                                // Handle response if needed
                                console.log('Response:', data);
                                displaySuccessModal(data.message);
                            })
                            .catch(error => {
                                // Handle errors
                                console.error('Error:', error);
                            });
                });
                // Add event listener to the close button of success modal
                document.getElementById('successModalClose').addEventListener('click', function (event) {
                    event.stopPropagation(); // Prevent event propagation to parent modal
                    $('#successModal').modal('hide');
                });
            });

            function displaySuccessModal(message) {
                // Tìm phần body của modal
                var modalBody = document.querySelector('#successModal .modal-body');
                // Nếu có thông điệp được truyền vào, thêm vào phần body của modal
                if (message) {
                    modalBody.textContent = message;
                } else {
                    // Nếu không có thông điệp, sử dụng nội dung mặc định
                    modalBody.textContent = '';
                }
                // Hiển thị modal
                $('#successModal').modal('show');
            }

            // seller complain product correct
            document.addEventListener('DOMContentLoaded', function () {
                const sellerConfirmOrderTrue = document.getElementById('sellerConfirmOrderCorrect');
                if (sellerConfirmOrderTrue) {
                    sellerConfirmOrderTrue.addEventListener('click', function () {
                        displayModalConfirm('Xác nhận đơn hàng đúng?');
                        document.getElementById('confirmButton').addEventListener('click', function () {

                            const orderId_SellerComplain = document.getElementById('matrunggian').value;
                            const userId__SellerComplain = document.getElementById('idnguoiban').value;
                            const buyerId__SellerComplain = document.getElementById('idnguoimua').value;

                            sendDataSellerConfirmOrderTrue(orderId_SellerComplain, userId__SellerComplain, buyerId__SellerComplain);
                            $('#confirmationModal').modal('hide');
                        });
                    });
                }
            });

            function sendDataSellerConfirmOrderTrue(orderId, userId, buyerId) {
                // Tạo đối tượng chứa dữ liệu cần gửi
                const data = {
                    orderId: orderId,
                    userId: userId,
                    buyerId: buyerId
                };

                // Gửi request fetch với method POST và body là dữ liệu đã chuẩn bị
                fetch('sellerConfirmOrderTrue', {
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

            // seller confirm order false
            document.addEventListener('DOMContentLoaded', function () {
                const sellerConfirmOrderFalse = document.getElementById('sellerConfirmOrderFalse');
                if (sellerConfirmOrderFalse) {
                    sellerConfirmOrderFalse.addEventListener('click', function () {
                        displayModalConfirm('Xác nhận đơn hàng sai, hủy đơn?');

                        document.getElementById('confirmButton').addEventListener('click', function () {

                            const orderId_SellerComplainFalse = document.getElementById('matrunggian').value;
                            const userId__SellerComplainFalse = document.getElementById('idnguoiban').value;
                            const buyerId__SellerComplainFalse = document.getElementById('idnguoimua').value;

                            sendDataSellerConfirmOrderFalse(orderId_SellerComplainFalse, userId__SellerComplainFalse, buyerId__SellerComplainFalse);
                            $('#confirmationModal').modal('hide');
                        });

                    });
                }
            });

            function sendDataSellerConfirmOrderFalse(orderId, userId, buyerId) {
                // Tạo đối tượng chứa dữ liệu cần gửi
                const data = {
                    orderId: orderId,
                    userId: userId,
                    buyerId: buyerId
                };

                // Gửi request fetch với method POST và body là dữ liệu đã chuẩn bị
                fetch('sellerConfirmOrderFalse', {
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

            // seller call admin
            document.addEventListener('DOMContentLoaded', function () {
                const callAdminOrder = document.getElementById('callAdmin');
                if (callAdminOrder) {
                    callAdminOrder.addEventListener('click', function () {
                        displayModalConfirm('Admin sẽ thu phí bên bán và mua 50k là phí xử lý, vui lòng đọc lại kĩ thông tin sản phẩm !');

                        document.getElementById('confirmButton').addEventListener('click', function () {

                            const orderId_SellerCallAdmin = document.getElementById('matrunggian').value;
                            const userId_SellerCallAdmin = document.getElementById('idnguoiban').value;
                            const buyerId_SellerCallAdmin = document.getElementById('idnguoimua').value;

                            sendDataCallAdmin(orderId_SellerCallAdmin, userId_SellerCallAdmin, buyerId_SellerCallAdmin);
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

            function displayModalConfirm(message) {
                // Set the message content in the modal body
                const modalBody = document.querySelector('#confirmationModal .modal-body');
                modalBody.innerHTML = message;

                // Show the modal
                $('#confirmationModal').modal('show');
            }

            function formatNumber() {
                // Lấy giá trị từ input
                let input = document.getElementById("giatien").value;
                let input2 = document.getElementById("giatientg").value;
                // Loại bỏ tất cả các ký tự không phải là số
                let number = input.replace(/\D/g, "");
                let number2 = input2.replace(/\D/g, "");
                // Định dạng số với dấu phẩy
                let formattedNumber = new Intl.NumberFormat().format(number);
                let formattedNumber2 = new Intl.NumberFormat().format(number2);
                // Gán giá trị đã định dạng trở lại vào input
                document.getElementById("giatien").value = formattedNumber;
                document.getElementById("giatientg").value = formattedNumber2;
            }
        </script>
    </body>

</html>
