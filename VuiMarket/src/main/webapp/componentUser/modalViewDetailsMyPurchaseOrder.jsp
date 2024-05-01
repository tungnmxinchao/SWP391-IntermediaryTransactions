<%-- 
    Document   : modalViewDetailsMyPurchaseOrder
    Created on : Mar 17, 2024, 9:40:42 PM
    Author     : TNO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Modal -->
<viewDetailOrderdiv class="modal fade" id="viewPurchaseOrder" tabindex="" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">

    <jsp:include page="../componentPublic/modalConfirm.jsp"></jsp:include>
    <jsp:include page="../componentPublic/modalSuccess.jsp"></jsp:include>


    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Thông tin đơn trung gian</h5>

                <div class="pull-right">
                    <button id="confirmReceiveButton" type="button" class="mr-1 btn-white-space btn btn-success">
                        <i class="fa fa-check"></i> Xác nhận đã nhận được hàng đúng mô tả
                    </button>
                </div>

                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group row">
                        <label for="matrunggian" class="col-sm-3 col-form-label">Mã trung gian</label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="matrunggian" value="">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="nguoiban" class="col-sm-3 col-form-label">Người bán</label>
                        <div class="col-sm-9">
                            <input style ="display: none" type="text" class="form-control" id="idnguoiban" value="">
                            <input disabled type="text" class="form-control" id="nguoiban" value="">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="nguoimua" class="col-sm-3 col-form-label">Người mua</label>
                        <div class="col-sm-9">
                            <input style ="display: none" type="text" class="form-control" id="idnguoimua" value="">
                            <input disabled type="text" class="form-control" id="nguoimua" value="">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="trangthai" class="col-sm-3 col-form-label">Trạng thái</label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="trangthai" value="">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="chude" class="col-sm-3 col-form-label">Chủ đề trung gian</label>
                        <div class="col-sm-9">
                            <input disabled type="text" value="" class="form-control" id="chudetg">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="giatien" class="col-sm-3 col-form-label">Giá tiền</label>
                        <div class="col-sm-9">
                            <input disabled oninput="formatNumber()" type="text" class="form-control" id="giatientg" value="">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label">Bên Chịu Phí Trung Gian</label>
                        <div class="col-sm-9">
                            <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                <label class="btn btn-secondary ">
                                    <input disabled type="radio" name="isSellerFeeTg" id="benchiuphi1tg" value="1" autocomplete="off"> Bên Bán
                                </label>
                                <label class="btn btn-secondary">
                                    <input disabled type="radio" name="isSellerFeeTg" id="benchiuphi2tg" value="0" autocomplete="off"> Bên Mua
                                </label> 
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="phitrunggian" class="col-sm-3 col-form-label">Phí trung gian</label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="phitrunggian" value="">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="tongienthanhtoan" class="col-sm-3 col-form-label">Tổng tiền bên mua cần thanh toán</label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="totalFeeOfBuyer" value="">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="benbanthucnhan" class="col-sm-3 col-form-label">Tiền bên bán thực nhận</label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="moneyforSeller" value="">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="mota" class="col-sm-3 col-form-label">Mô tả</label>
                        <div class="col-sm-9">
                            <div id="mota-editortg"></div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="lienhe" class="col-sm-3 col-form-label">Phương thức liên hệ</label>
                        <div class="col-sm-9">
                            <textarea disabled class="form-control" id="lienhetg" rows="2" placeholder="Số điện thoại / Zalo / Link Facebook / Telegram / discord ..."></textarea>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="noidungan" class="col-sm-3 col-form-label">Nội dung ẩn (*)</label>
                        <div class="col-sm-9">
                            <!-- Thay thế textarea bằng một div cho việc hiển thị TinyMCE -->
                            <div id="noidungantg-editor"></div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label">Hiện công khai</label>
                        <div class="col-sm-9">
                            <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                <label class="btn btn-secondary">
                                    <input disabled type="radio" name="hiencongkhaiTg" id="hiencongkhai1tg" value="1" autocomplete="off"> Hiện công khai ai cũng tìm được
                                </label>
                                <label class="btn btn-secondary">
                                    <input disabled type="radio" name="hiencongkhaiTg" id="hiencongkhai2tg" value="0" autocomplete="off"> Ẩn chỉ ai có link mới xem được
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="tgtao" class="col-sm-3 col-form-label">Thời gian tạo </label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="createdDate" value="">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="capnhatcuoi" class="col-sm-3 col-form-label">Cập nhật cuối </label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="updateDate" value="">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="linkShare" class="col-sm-3 col-form-label">Link chia sẻ </label>
                        <div class="col-sm-9">
                            <a href="" id="linkSharetg" class="form-control"></a>
                        </div>
                    </div>
                    <div class="text-center">
                        <button id="complaintButton" type="button" class="mr-1 btn-white-space btn btn-danger">
                            <i class="fa fa-times-circle"></i> Khiếu nại sản phẩm không đúng mô tả
                        </button>


                        <button id="callAdmin" type="button" class="mr-1 btn-white-space btn btn-danger">
                            <i class="fa fa-check"></i> YÊU CẦU ADMIN XỬ LÝ DO BÊN MUA KHÔNG CHẤP NHẬT KẾT QUẢ
                        </button>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
