<%-- 
    Document   : modalAddNew
    Created on : Feb 5, 2024, 2:38:57 PM
    Author     : TNO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Modal -->
<div class="modal fade" id="addNewOrder" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" data-backdrop="static" aria-hidden="true">
    <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel" data-backdrop="static" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmModalLabel">Xác nhận</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Hệ thống sẽ thu phí đăng bài là 5000đ
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary cancel-button" data-dismiss="modal">Hủy</button>
                    <button type="button" class="btn btn-primary" id="confirmBtn">Xác nhận</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Thông tin đơn trung gian</h5>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group row">
                        <label for="chude" class="col-sm-3 col-form-label">Chủ đề trung gian (*)</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="chude" placeholder="Nhập chủ đề">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="giatien" class="col-sm-3 col-form-label">Giá tiền (*)</label>
                        <div class="col-sm-9">
                            <input oninput="formatNumber()" type="text" class="form-control" id="giatien" placeholder="Nhập giá tiền">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label">Bên Chịu Phí Trung Gian (*)</label>
                        <div class="col-sm-9">
                            <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                <label class="btn btn-secondary active">
                                    <input type="radio" name="isSellerFee" id="benchiuphi1" value="1" autocomplete="off" checked> Bên Bán
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="isSellerFee" id="benchiuphi2" value="0" autocomplete="off"> Bên Mua
                                </label> 
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="mota" class="col-sm-3 col-form-label">Mô tả (*)</label>
                        <div class="col-sm-9">
                            <!-- Thay thế textarea bằng một div cho việc hiển thị TinyMCE -->
                            <div id="mota-editor"></div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="lienhe" class="col-sm-3 col-form-label">Phương thức liên hệ</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" id="lienhe" rows="2" placeholder="Số điện thoại / Zalo / Link Facebook / Telegram / discord ..."></textarea>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="noidungan" class="col-sm-3 col-form-label">Nội dung ẩn (*)</label>
                        <div class="col-sm-9">
                            <!-- Thay thế textarea bằng một div cho việc hiển thị TinyMCE -->
                            <div id="noidungan-editor"></div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label">Hiện công khai (*)</label>
                        <div class="col-sm-9">
                            <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                <label class="btn btn-secondary active">
                                    <input type="radio" name="hiencongkhai" id="hiencongkhai1" value="1" autocomplete="off" checked> Hiện công khai ai cũng tìm được
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="hiencongkhai" id="hiencongkhai2" value="0" autocomplete="off"> Ẩn chỉ ai có link mới xem được
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button id="btnThemMoiSP" type="button" class="btn btn-success">Thêm mới</button>
            </div>
        </div>
    </div>
</div>