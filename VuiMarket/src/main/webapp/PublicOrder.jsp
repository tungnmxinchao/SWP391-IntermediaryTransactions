<%-- 
    Document   : PublicOrder
    Created on : Mar 18, 2024, 4:39:43 PM
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
                        <h1>Chợ Công Khai</h1>  
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
                                    <td>${listMyOrder.createdBy}</td>
                                    <td> ${listMyOrder.title} </td>
                                    <td>
                                        <p class="status delivered">${listMyOrder.isPublic}</p>
                                    </td>
                                    <td> <strong> ${listMyOrder.moneyValue} </strong></td>
                                    <td> ${listMyOrder.isSellerChargeFee} </td>
                                    <td> ${listMyOrder.createdAt} </td>
                                    <td> ${listMyOrder.updatedAt} </td>
                                    <td> 
                                        <button class="details-button" data-toggle="modal" data-target="#viewDetailOrder" data-userAccess="${userAccess}" data-buyerId="${listMyOrder.buyer}" data-updatedable="${listMyOrder.updatedable}" data-id="${listMyOrder.id}" data-username="${listMyOrder.createdBy}" data-userId="${listMyOrder.idUser}" >
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

        <jsp:include page="componentUser/modalViewDetailsPublicOrders.jsp"></jsp:include>





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

                document.querySelectorAll('.details-button').forEach(button => {
                    button.addEventListener('click', function () {
                        const orderId = this.getAttribute('data-id');
                        const user_name = this.getAttribute('data-username');
                        const user_id = this.getAttribute('data-userId');
                        const updatedableValue = this.getAttribute('data-updatedable');
                        const userAccess = this.getAttribute('data-userAccess');

                        if (userAccess !== user_id) {
                            document.getElementById('buyOrder').style.display = 'block';
                            document.getElementById('updateOrder').style.display = 'none';

                            fetch('viewPublicOrder', {
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
                        } else {
                            document.getElementById('buyOrder').style.display = 'none';
                            document.getElementById('updateOrder').style.display = 'block';

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
                        }

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

                    if (data.idUser === ${userAccess}) {
                        tinymce.get('mota-editortg').setContent(data.description);
                        tinymce.get('noidungantg-editor').setContent(data.hiddenValue);
                    } else {
                        tinymce.get('mota-editortg').setContent(data.description);
                        tinymce.get('noidungantg-editor').setContent("");
                    }

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
                        modalBody.textContent = 'Dữ liệu đã được cập nhật thành công.';
                    }
                    // Hiển thị modal
                    $('#successModal').modal('show');
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

                document.addEventListener('DOMContentLoaded', function () {
                    const buyOrderForm = document.getElementById('buyOrder');

                    if (buyOrderForm) { // Check if the buyOrderForm element exists
                        buyOrderForm.addEventListener('click', function () {
                            // Display modal with custom message
                            displayModalConfirm('Hệ thống sẽ tiến hành tạm giữ tiền trung gian, xác nhận đồng ý?');
                        });

                        // Add event listener for the confirm button in the modal
                        document.getElementById('confirmButton').addEventListener('click', function () {
                            // Gather data from input fields and radio buttons
                            const matrunggianValue = document.getElementById('matrunggian').value;
                            const IdUser = document.getElementById('idnguoiban').value;

                            // Prepare data to send
                            const data = {
                                idOrder: matrunggianValue,
                                idUser: IdUser
                            };

                            // Send data using fetch API
                            fetch('inforBuyer', {
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
                                        Toastify({
                                            text: data.message,
                                            duration: 3000,
                                            position: "right",
                                            backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)"
                                        }).showToast();
                                    })
                                    .catch(error => {
                                        // Handle errors
                                        console.error('Error:', error);
                                    });

                            // Hide the modal after processing
                            $('#confirmationModal').modal('hide');
                        });
                    }
                });

                function displayModalConfirm(message) {
                    // Set the message content in the modal body
                    const modalBody = document.querySelector('#confirmationModal .modal-body');
                    modalBody.innerHTML = message;

                    // Show the modal
                    $('#confirmationModal').modal('show');
                }


        </script>
    </body>

</html>
