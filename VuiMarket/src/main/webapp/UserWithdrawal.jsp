
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
        <!-- Modal -->
        <div class="modal fade" id="confirmationModal" tabindex="-1" aria-labelledby="confirmationModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmationModalLabel">Xác Nhận</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" id="modalBodyContent">
                        Xác nhận chuyển tiền cho người dùng?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                        <button type="button" class="btn btn-primary" id="confirmButton">Đồng Ý</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- header -->
        <jsp:include page="componentPublic/header.jsp"></jsp:include>
            <!-- header -->
            <div id="notificationArea"></div>
            <div class="table_mysaleorder">

                <main class="table" id="customers_table">
                    <section class="table__header">
                        <h1>Nạp tiền cho người dùng</h1>  
                    </section>
                    <section class="table__body">
                        <table>
                            <thead>
                                <tr>
                                    <th> Mã đơn hàng  <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Người yêu cầu<span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Trạng Thái <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Mã giao dịch <span class="icon-arrow">&UpArrow;</span></th>
                                    <th> Số tiền<span class="icon-arrow">&UpArrow;</span></th>
                                    <th>Số tài khoản<span class="icon-arrow">&UpArrow;</span></th>
                                    <th>Chủ tài khoản<span class="icon-arrow">&UpArrow;</span></th>
                                    <th>Ngân hàng<span class="icon-arrow">&UpArrow;</span></th>
                                    <th>Thời Gian Tạo<span class="icon-arrow">&UpArrow;</span></th>
                                    <th>Cập Nhật Cuối<span class="icon-arrow">&UpArrow;</span></th>
                                    <th>Hành Động<span class="icon-arrow">&UpArrow;</span></th>
                                </tr>

                            </thead>
                            <thead2>
                                <form action="mySaleOrder" method="GET">
                                    <tr>                              
                                        <th>
                                            <input type="text" name="action" style="display:none" value="search"/>
                                            <input class="form-control mr-sm-2" placeholder="ID" aria-label="Search" type="text" name="idOrder" value="" />
                                        </th>
                                        <th></th>
                                        <th>
                                            <select id="" name="status">
                                                <option value="-1">Tất cả</option>
                                                <option value="1">Chờ xử lý</option>
                                            </select>
                                        </th>
                                        <th><input class="form-control mr-sm-2" aria-label="Search" placeholder="Mã giao dịch" type="text" name="withdrawCode" value="" /></th>
                                        <th>
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Từ" type="text" name="moneyValueFrom" value="" />
                                            <input class="form-control mr-sm-2" aria-label="Search" placeholder="Đến" type="text" name="moneyValueTo" value="" />
                                        </th>
                                        <th><input class="form-control mr-sm-2" aria-label="Search" placeholder="Số tài khoản" type="text" name="accountNumber" value="" /></th>
                                        <th><input class="form-control mr-sm-2" aria-label="Search" placeholder="Chủ tài khoản" type="text" name="accountOwner" value="" /></th>
                                        <th><input class="form-control mr-sm-2" aria-label="Search" placeholder="Ngân hàng" type="text" name="banhName" value="" /></th>
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
                            <c:forEach items="${listWithdrawal}" var="listWithdrawal">
                                <tr>
                                    <td>${listWithdrawal.id}</td>
                                    <td>${listWithdrawal.fullName}</td>
                                    <td>${listWithdrawal.status}</td>
                                    <td>${listWithdrawal.withdrawCode}</td>
                                    <td> <strong> ${listWithdrawal.amount} </strong></td>
                                    <td>${listWithdrawal.accountNumber}</td>
                                    <td>${listWithdrawal.accountOwner}</td>
                                    <td>${listWithdrawal.banhName}</td>
                                    <td> ${listWithdrawal.createdAt} </td>
                                    <td> ${listWithdrawal.updateAt} </td>
                                    <td> 
                                        <button class="true-button" data-toggle="modal" data-target="#" data-orderId="${listWithdrawal.id}" data-userId="${listWithdrawal.uid}" data-moneyValue="${listWithdrawal.amount}">
                                            <i class="fas fa-check"></i> 
                                        </button>

                                        <button class="false-button" data-toggle="modal" data-target="#" data-orderId="${listWithdrawal.id}" data-userId="${listWithdrawal.uid}" data-moneyValue="${listWithdrawal.amount}">
                                            <i class="fas fa-times"></i>
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
            function displayModalConfirm(message) {
                // Set the message content in the modal body
                const modalBody = document.querySelector('#confirmationModal .modal-body');
                modalBody.innerHTML = message;

                // Show the modal
                $('#confirmationModal').modal('show');
            }

            document.querySelectorAll('.true-button').forEach(button => {
                button.addEventListener('click', function () {
                    // Lấy thông tin từ các thuộc tính dữ liệu của nút
                    const orderId = this.getAttribute('data-orderId');
                    const userId = this.getAttribute('data-userId');
                    const moneyValue = this.getAttribute('data-moneyValue');


                    // Tạo đối tượng chứa dữ liệu để gửi đi
                    const dataToSend = {
                        orderId: orderId,
                        userId: userId,
                        moneyValue: moneyValue,

                    };

                    // Gửi dữ liệu đến servlet sử dụng fetch và JSON
                    fetch('adminTransferSuccess', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(dataToSend)
                    })
                            .then(response => {
                                if (response.ok) {
                                    console.log('Dữ liệu đã được gửi thành công.');
                                    return response.json(); // Trả về dữ liệu JSON từ phản hồi
                                } else {
                                    throw new Error('Đã xảy ra lỗi khi gửi dữ liệu.');
                                }
                            })
                            .then(data => {
                                // Xử lý dữ liệu nhận được từ máy chủ (nếu cần)
                                console.log('Dữ liệu từ máy chủ:', data);
                                Toastify({
                                    text: data.message,
                                    duration: 3000,
                                    position: "right",
                                    backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)"
                                }).showToast();
                            })
                            .catch(error => {
                                console.error('Lỗi:', error);
                            });
                });
            });

            document.querySelectorAll('.false-button').forEach(button => {
                button.addEventListener('click', function () {
                    // Lấy thông tin từ các thuộc tính dữ liệu của nút
                    const orderId = this.getAttribute('data-orderId');
                    const userId = this.getAttribute('data-userId');
                    const moneyValue = this.getAttribute('data-moneyValue');


                    // Tạo đối tượng chứa dữ liệu để gửi đi
                    const dataToSend = {
                        orderId: orderId,
                        userId: userId,
                        moneyValue: moneyValue,

                    };

                    // Gửi dữ liệu đến servlet sử dụng fetch và JSON
                    fetch('adminTransferFail', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(dataToSend)
                    })
                            .then(response => {
                                if (response.ok) {
                                    console.log('Dữ liệu đã được gửi thành công.');
                                    return response.json(); // Trả về dữ liệu JSON từ phản hồi
                                } else {
                                    throw new Error('Đã xảy ra lỗi khi gửi dữ liệu.');
                                }
                            })
                            .then(data => {
                                // Xử lý dữ liệu nhận được từ máy chủ (nếu cần)
                                console.log('Dữ liệu từ máy chủ:', data);
                                Toastify({
                                    text: data.message,
                                    duration: 3000,
                                    position: "right",
                                    backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)"
                                }).showToast();
                            })
                            .catch(error => {
                                console.error('Lỗi:', error);
                            });
                });
            });
        </script>
    </body>

</html>
