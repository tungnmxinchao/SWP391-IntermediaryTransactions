<%-- 
    Document   : FormInfor
    Created on : Feb 9, 2024, 1:44:00 PM
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
        <!-- Modal -->
        <div class="modal fade" id="successModal" tabindex="-1" role="dialog" aria-labelledby="successModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="successModalLabel">Xác nhận</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Dữ liệu đã được cập nhật thành công.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>
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
                        Hệ thống sẽ tiến hành tạm giữ tiền trung gian, xác nhận đồng ý?
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
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Thông tin đơn trung gian</h5>
                        <div class="pull-right">
                        <c:if test="${(RecordMyOrder.status eq 'Bên mua đang kiểm tra hàng' || RecordMyOrder.status eq 'Chờ bên mua xác nhận khiếu nại không đúng') && UserAcess.uid == RecordMyOrder.idBuyer}">
                            <button id="confirmReceiveButton" type="button" class="mr-1 btn-white-space btn btn-success">
                                <i class="fa fa-check"></i> Xác nhận đã nhận được hàng đúng mô tả
                            </button>
                        </c:if>
                        <c:if test="${RecordMyOrder.status eq 'Bên mua khiếu nại sản phẩm' && UserAcess.uid == RecordMyOrder.idUser}">
                            <button id="callAdmin" type="button" class="mr-1 btn-white-space btn btn-danger">
                                <i class="fa fa-check"></i> YÊU CẦU ADMIN XỬ LÝ DO BÊN MUA KHÔNG CHẤP NHẬT KẾT QUẢ
                            </button>
                        </c:if>

                        <c:if test="${RecordMyOrder.status eq 'Yêu cầu quản trị viên trung gian' && UserAcess.rolename eq 'admin'}">
                            <button id="adminHandling" type="button" class="mr-1 btn-white-space btn btn-danger">
                                <i class="fa fa-check"></i> XỬ LÝ KHIẾU NẠI
                            </button>
                        </c:if>

                    </div>
                </div>
                <div class="modal-body">
                    <div class="form-group row">
                        <label for="matrunggianForm" class="col-sm-3 col-form-label">Mã trung gian</label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="matrunggianForm" value="${RecordMyOrder.id}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="nguoibanForm" class="col-sm-3 col-form-label">Người bán</label>
                        <div class="col-sm-9">
                            <input style="display: none" type="text" class="form-control" id="idnguoibanForm" value="${RecordMyOrder.idUser}">
                            <input disabled type="text" class="form-control" id="nguoibanForm" value="${RecordMyOrder.createdBy}">
                        </div>
                    </div>
                    <c:if test="${RecordMyOrder.buyer != null}">
                        <div class="form-group row">
                            <label for="nguoibanForm" class="col-sm-3 col-form-label">Người mua</label>
                            <div class="col-sm-9">
                                <input style="display: none" type="text" class="form-control" id="idnguoimuaForm" value="${RecordMyOrder.idBuyer}">
                                <input disabled type="text" class="form-control" id="nguoibanForm" value="${RecordMyOrder.buyer}">
                            </div>
                        </div>
                    </c:if>
                    <div class="form-group row">
                        <label for="trangthaiForm" class="col-sm-3 col-form-label">Trạng thái</label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="trangthaiForm" value="${RecordMyOrder.status}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="chudeForm" class="col-sm-3 col-form-label">Chủ đề trung gian</label>
                        <div class="col-sm-9">
                            <input type="text" value="${RecordMyOrder.title}" class="form-control" id="chudeForm" ${UserAcess.uid != RecordMyOrder.idUser || RecordMyOrder.updatedable == "0" ? 'disabled' : ''}>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="giatienForm" class="col-sm-3 col-form-label">Giá tiền</label>
                        <div class="col-sm-9">
                            <input oninput="formatNumber()" type="text" class="form-control" id="giatienForm" value="${RecordMyOrder.moneyValue}" ${UserAcess.uid != RecordMyOrder.idUser || RecordMyOrder.updatedable == "0" ? 'disabled' : ''}>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label">Bên Chịu Phí Trung Gian</label>
                        <div class="col-sm-9">
                            <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                <label class="btn btn-secondary">
                                    <input ${UserAcess.uid != RecordMyOrder.idUser || RecordMyOrder.updatedable == "0" ? 'disabled' : ''} type="radio" name="isSellerFee" id="benchiuphi1Form" value="1" autocomplete="off"> Bên Bán
                                </label>
                                <label class="btn btn-secondary">
                                    <input ${UserAcess.uid != RecordMyOrder.idUser || RecordMyOrder.updatedable == "0" ? 'disabled' : ''} type="radio" name="isSellerFee" id="benchiuphi2Form" value="0" autocomplete="off"> Bên Mua
                                </label> 
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="phitrunggianForm" class="col-sm-3 col-form-label">Phí trung gian</label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="phitrunggianForm" value="${RecordMyOrder.feeOnSuccess}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="tongienthanhtoanForm" class="col-sm-3 col-form-label">Tổng tiền bên mua cần thanh toán</label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="totalFeeOfBuyerForm" value="${RecordMyOrder.totalMoneyForBuyer}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="benbanthucnhanForm" class="col-sm-3 col-form-label">Tiền bên bán thực nhận</label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="moneyforSellerForm" value="${RecordMyOrder.sellerReceivedOnSuccess}">
                        </div>
                    </div>
                    <c:if test="${UserAcess.uid == RecordMyOrder.idUser}">
                        <div class="form-group row">
                            <label for="motaForm" class="col-sm-3 col-form-label">Mô tả</label>
                            <div class="col-sm-9">
                                <div id="mota-editor"></div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${UserAcess.uid != RecordMyOrder.idUser}">
                        <div class="form-group row">
                            <label for="motaForm2" class="col-sm-3 col-form-label">Mô tả</label>
                            <div class="col-sm-9">
                                <div id="mota-editor2"></div>
                            </div>
                        </div>
                    </c:if>

                    <div class="form-group row">
                        <label for="lienheForm" class="col-sm-3 col-form-label">Phương thức liên hệ</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" id="lienheForm" rows="2" placeholder="Số điện thoại / Zalo / Link Facebook / Telegram / discord ..." ${UserAcess.uid != RecordMyOrder.idUser || RecordMyOrder.updatedable == "0" ? 'disabled' : ''}></textarea>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="noidunganForm" class="col-sm-3 col-form-label">Nội dung ẩn</label>
                        <div class="col-sm-9">
                            <div id="noidungan-editor"></div>
                        </div>
                    </div>
                    <c:if test="${UserAcess.uid == RecordMyOrder.idBuyer}">
                        <div class="form-group row">
                            <label for="motaForm2" class="col-sm-3 col-form-label">Nội dung ẩn</label>
                            <div class="col-sm-9">
                                <div id="noidungan-editor2"></div>
                            </div>
                        </div>
                    </c:if>

                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label">Hiện công khai</label>
                        <div class="col-sm-9">
                            <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                <label class="btn btn-secondary">
                                    <input ${UserAcess.uid != RecordMyOrder.idUser || RecordMyOrder.updatedable == "0" ? 'disabled' : ''} type="radio" name="hiencongkhai" id="hiencongkhai1Form" value="1" autocomplete="off"> Hiện công khai ai cũng tìm được
                                </label>
                                <label class="btn btn-secondary">
                                    <input ${UserAcess.uid != RecordMyOrder.idUser || RecordMyOrder.updatedable == "0" ? 'disabled' : ''} type="radio" name="hiencongkhai" id="hiencongkhai2Form" value="0" autocomplete="off"> Ẩn chỉ ai có link mới xem được
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="tgtaoForm" class="col-sm-3 col-form-label">Thời gian tạo </label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="createdDateForm" value="${RecordMyOrder.createdAt}">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="capnhatcuoiForm" class="col-sm-3 col-form-label">Cập nhật cuối </label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="updateDateForm" value="${RecordMyOrder.updatedAt}">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="linkShareForm" class="col-sm-3 col-form-label">Link chia sẻ </label>
                        <div class="col-sm-9">
                            <a href="${RecordMyOrder.shareLink}" id="linkShareForm" class="form-control">${RecordMyOrder.shareLink}</a>
                        </div>
                    </div>                          
                </div>
                <c:if test="${RecordMyOrder.status eq 'Bên mua đang kiểm tra hàng' && UserAcess.uid == RecordMyOrder.idBuyer}">
                    <div class="text-center">
                        <button id="complaintButton" type="button" class="mr-1 btn-white-space btn btn-danger">
                            <i class="fa fa-times-circle"></i> Khiếu nại sản phẩm không đúng mô tả
                        </button>
                    </div>
                </c:if>

                <c:if test="${(RecordMyOrder.status eq 'Bên mua khiếu nại sản phẩm' || RecordMyOrder.status eq 'Chờ bên mua xác nhận khiếu nại không đúng') && UserAcess.uid == RecordMyOrder.idBuyer}">
                    <div class="text-center">
                        <button id="callAdmin" type="button" class="mr-1 btn-white-space btn btn-danger">
                            <i class="fa fa-times-circle"></i> YÊU CẦU ADMIN TRUNG GIAN DO NGƯỜI BÁN KHÔNG CHỊU GIẢI QUYẾT KHIẾU NẠI
                        </button>
                    </div>
                </c:if>

                <c:if test="${RecordMyOrder.status eq 'Bên mua khiếu nại sản phẩm' && UserAcess.uid == RecordMyOrder.idUser}">
                    <div class="text-center">
                        <button id="sellerConfirmOrderFalse" type="button" class="mr-1 btn-white-space btn btn-danger">
                            <i class="fa fa-times-circle"></i> XÁC NHẬN ĐƠN HÀNG SAI, HỦY ĐƠN
                        </button>
                    </div>
                </c:if>

                <c:if test="${RecordMyOrder.status eq 'Bên mua khiếu nại sản phẩm' && UserAcess.uid == RecordMyOrder.idUser}">
                    <div class="text-center">
                        <button id="sellerConfirmOrderCorrect" type="button" class="mr-1 btn-white-space btn btn-success">
                            <i class="fa fa-times-circle"></i>ĐƠN HÀNG CHÍNH XÁC, Y/C KHÁCH KIỂM TRA LẠI
                        </button>
                    </div>
                </c:if>

                <c:if test="${UserAcess.uid == RecordMyOrder.idUser && RecordMyOrder.updatedable != '0'}">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button id="updateOrderForm" type="button" class="btn btn-success"> Cập nhật</button>
                    </div>
                </c:if>
                <c:if test="${UserAcess.uid != RecordMyOrder.idUser && RecordMyOrder.updatedable != '0'}">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button id="buyOrderForm" type="button" class="btn btn-success">Mua</button>
                    </div>
                </c:if>
            </div>
        </div>
        <jsp:include page="componentUser/modalHandling.jsp"></jsp:include>

            <!-- JavaScript Libraries -->
            <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>


            <script src="https://cdn.tiny.cloud/1/3dnf4g9cgv4sfog9pgz0n3f33fqrskhx8tz3ejpcmhn480gh/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>



            <script>


                document.addEventListener('DOMContentLoaded', function () {
                    tinymce.init({
                        selector: '#mota-editor, #noidungan-editor',
                        height: 300,
                        plugins: 'anchor autolink charmap codesample emoticons image link lists media searchreplace table visualblocks wordcount linkchecker',
                        toolbar: 'undo redo | formatselect | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | help',
                        setup: function (editor) {
                            editor.on('init', function () {
                                fetchInfor();
                            });
                        }
                    });
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
                    const userAccessUid = document.getElementById('idnguoibanForm').value;
                    const buyerId = document.getElementById('idnguoimuaForm').value;
                    if (String(userAccessUid) === String(eventData.idUser) || String(buyerId) === String(eventData.buyerId)) {
                        Toastify({
                            text: eventData.message,
                            duration: 3000,
                            position: "right",
                            backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)"
                        }).showToast();
                    }
                };

                function fetchInfor() {
                    // Lấy dữ liệu từ các trường cần gửi
                    const id = document.getElementById("matrunggianForm").value;
                    const createdBy = document.getElementById("nguoibanForm").value;
                    const idUser = document.getElementById("idnguoibanForm").value;

                    // Tạo một đối tượng chứa dữ liệu cần gửi
                    const data = {
                        id: id,
                        idUser: idUser,
                        createdBy: createdBy
                    };

                    // Gửi request fetch với method POST và body là dữ liệu đã chuẩn bị
                    fetch('fetchData', {
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
                                console.log('Response from servlet:', data);
                                fetchInforToForm(data);
                            })
                            .catch(error => {
                                console.error('There was a problem with your fetch operation:', error);
                            });
                }

                function fetchInforToForm(data) {
                    var motaEditor = document.getElementById('mota-editor');
                    if (motaEditor) {
                        tinymce.get('mota-editor').setContent(data.description);
                    }
                    var motaEditor2 = document.getElementById('mota-editor2');
                    if (motaEditor2) {
                        motaEditor2.innerHTML = data.description;
                    }
                    var noidungan = document.getElementById('noidungan-editor');
                    if (noidungan) {
                        // Check if the user has access to edit the hidden content
                        if ((${UserAcess.uid == RecordMyOrder.idUser}) || (${UserAcess.rolename == "admin"})) {
                            // If the user has access, set the content of the hidden field using TinyMCE
                            tinymce.get('noidungan-editor').setContent(data.hiddenValue);
                        } else {
                            // If the user doesn't have access, remove the form group for the hidden content
                            var noidunganFormGroup = document.querySelector('label[for="noidunganForm"]').closest('.form-group.row');
                            if (noidunganFormGroup) {
                                noidunganFormGroup.remove();
                            }
                        }
                    }
                    var noidungan2 = document.getElementById('noidungan-editor2');
                    if (noidungan2) {
                        noidungan2.innerHTML = data.hiddenValue;
                    }

                    document.getElementById('lienheForm').value = data.contact;

                    const benchiuphi1Form = document.getElementById('benchiuphi1Form');
                    const benchiuphi2Form = document.getElementById('benchiuphi2Form');

                    if (data.isSellerChargeFee === "Bên Bán") {
                        benchiuphi1Form.checked = true;
                        benchiuphi1Form.parentNode.classList.add('active');
                        benchiuphi2Form.parentNode.classList.remove('active');
                    } else if (data.isSellerChargeFee === "Bên Mua") {
                        benchiuphi2Form.checked = true;
                        benchiuphi2Form.parentNode.classList.add('active');
                        benchiuphi1Form.parentNode.classList.remove('active');
                    }


                    const hiencongkhai1Form = document.getElementById('hiencongkhai1Form');
                    const hiencongkhai2Form = document.getElementById('hiencongkhai2Form');

                    if (data.isPublic === "Công Khai") {
                        hiencongkhai1Form.checked = true;
                        hiencongkhai1Form.parentNode.classList.add('active');
                        hiencongkhai2Form.parentNode.classList.remove('active');
                    } else if (data.isPublic === "Riêng Tư") {
                        hiencongkhai2Form.checked = true;
                        hiencongkhai2Form.parentNode.classList.add('active');
                        hiencongkhai1Form.parentNode.classList.remove('active');
                    }

                    var updatedable = "${RecordMyOrder.updatedable}";
                    if (updatedable === '0') {
                        tinymce.get('mota-editor').getBody().setAttribute('contenteditable', false);
                        tinymce.get('noidungan-editor').getBody().setAttribute('contenteditable', false);
                    }

                }

                document.addEventListener('DOMContentLoaded', function () {
                    const updateOrderForm = document.getElementById('updateOrderForm');

                    if (updateOrderForm && "${RecordMyOrder.updatedable != '0'}") { // Check if the updateOrderForm element exists and if RecordMyOrder.updatedable is not equal to '0'
                        updateOrderForm.addEventListener('click', function () {
                            // Gather data from input fields and radio buttons
                            const matrunggianValue = document.getElementById('matrunggianForm').value;
                            const chudeValue = document.getElementById('chudeForm').value;
                            const isSellerFeeValue = document.querySelector('input[name="isSellerFee"]:checked').value;
                            const giatienValue = document.getElementById('giatienForm').value;
                            const lienheValue = document.getElementById('lienheForm').value;
                            const hiencongkhaiValue = document.querySelector('input[name="hiencongkhai"]:checked').value;
                            const motaValue = tinymce.get('mota-editor').getContent();
                            const noidunganValue = tinymce.get('noidungan-editor').getContent();

                            // Perform validation here if needed

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
                    }
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
                    let input = document.getElementById("giatienForm").value;

                    // Loại bỏ tất cả các ký tự không phải là số
                    let number = input.replace(/\D/g, "");

                    // Định dạng số với dấu phẩy
                    let formattedNumber = new Intl.NumberFormat().format(number);

                    // Gán giá trị đã định dạng trở lại vào input
                    document.getElementById("giatienForm").value = formattedNumber;
                }

                document.addEventListener('DOMContentLoaded', function () {
                    const buyOrderForm = document.getElementById('buyOrderForm');

                    if (buyOrderForm) { // Check if the buyOrderForm element exists
                        buyOrderForm.addEventListener('click', function () {
                            // Display modal with custom message
                            displayModalConfirm('Hệ thống sẽ tiến hành tạm giữ tiền trung gian, xác nhận đồng ý?');
                        });

                        // Add event listener for the confirm button in the modal
                        document.getElementById('confirmButton').addEventListener('click', function () {
                            // Gather data from input fields and radio buttons
                            const matrunggianValue = document.getElementById('matrunggianForm').value;
                            const IdUser = document.getElementById('idnguoibanForm').value;

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
                                        ws.send(JSON.stringify({idUser: data.idUser, message: data.message}));
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

                document.addEventListener('DOMContentLoaded', function () {
                    const confirmReceiveButton = document.getElementById('confirmReceiveButton');
                    if (confirmReceiveButton) {
                        confirmReceiveButton.addEventListener('click', function () {
                            displayModalConfirm('Xác nhận đơn hàng đúng mô tả?');
                            document.getElementById('confirmButton').addEventListener('click', function () {
                                // Lấy giá trị từ RecordMyOrder và gọi hàm để gửi dữ liệu đến servlet
                                const orderId = "${RecordMyOrder.id}";
                                const userId = "${RecordMyOrder.idUser}";
                                const buyerId = "${RecordMyOrder.idBuyer}";
                                sendDataToServlet(orderId, userId, buyerId);
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
                                //                    status: statusOrder
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
                                const orderId = "${RecordMyOrder.id}";
                                const userId = "${RecordMyOrder.idUser}";
                                const buyerId = "${RecordMyOrder.idBuyer}";
                                sendDataComplain(orderId, userId, buyerId);
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
                    const sellerConfirmOrderTrue = document.getElementById('sellerConfirmOrderCorrect');
                    if (sellerConfirmOrderTrue) {
                        sellerConfirmOrderTrue.addEventListener('click', function () {
                            displayModalConfirm('Xác nhận đơn hàng đúng?');

                            document.getElementById('confirmButton').addEventListener('click', function () {

                                const orderId = "${RecordMyOrder.id}";
                                const userId = "${RecordMyOrder.idUser}";
                                const buyerId = "${RecordMyOrder.idBuyer}";

                                sendDataSellerConfirmOrderTrue(orderId, userId, buyerId);
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

                document.addEventListener('DOMContentLoaded', function () {
                    const sellerConfirmOrderFalse = document.getElementById('sellerConfirmOrderFalse');
                    if (sellerConfirmOrderFalse) {
                        sellerConfirmOrderFalse.addEventListener('click', function () {
                            displayModalConfirm('Xác nhận đơn hàng sai?');
                            document.getElementById('confirmButton').addEventListener('click', function () {

                                const orderId = "${RecordMyOrder.id}";
                                const userId = "${RecordMyOrder.idUser}";
                                const buyerId = "${RecordMyOrder.idBuyer}";
                                sendDataSellerConfirmOrderFalse(orderId, userId, buyerId);
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

                document.addEventListener('DOMContentLoaded', function () {
                    const callAdminOrder = document.getElementById('callAdmin');
                    if (callAdminOrder) {
                        callAdminOrder.addEventListener('click', function () {
                            displayModalConfirm('Admin sẽ thu phí bên bán và mua 50k là phí xử lý, vui lòng đọc lại kĩ thông tin sản phẩm !');

                            document.getElementById('confirmButton').addEventListener('click', function () {
                                // Lấy giá trị từ RecordMyOrder và gọi hàm để gửi dữ liệu đến servlet
                                const orderId = "${RecordMyOrder.id}";
                                const userId = "${RecordMyOrder.idUser}";
                                const buyerId = "${RecordMyOrder.idBuyer}";
                                sendDataCallAdmin(orderId, userId, buyerId);
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

                // Thêm code xử lý sự kiện click cho nút "Xác nhận" vào trong cùng một hàm
                document.getElementById('adminHandling').addEventListener('click', function () {
                    $('#handlingOrder').modal('show');
                    document.getElementById('adminConfirm').addEventListener('click', function () {
                        // Lấy giá trị của các input
                        var idOrderHandling = document.getElementById('handlingMatrunggian').value;
                        var idHandlingSeller = document.getElementById('idHandlingSeller').value;
                        var idHandlingBuyer = document.getElementById('idHandlingBuyer').value;
                        var correctSide = document.querySelector('input[name="correctSide"]:checked').value;

                        // Tạo đối tượng chứa dữ liệu để gửi đi
                        var dataToSend = {
                            idOrderHandling: idOrderHandling,
                            idHandlingSeller: idHandlingSeller,
                            idHandlingBuyer: idHandlingBuyer,
                            correctSide: correctSide
                        };
                        fetch('adminConfirmSideTrue', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(dataToSend)
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
                                    Toastify({
                                        text: data.message,
                                        duration: 3000,
                                        position: "right",
                                        backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)"
                                    }).showToast();
                                    $('#handlingOrder').modal('hide');
                                })
                                .catch(error => {
                                    console.error('There was a problem with your fetch operation:', error);
                                });
                    });
                });
        </script>
    </body>

</html>
