<%-- 
    Document   : modalDetails_interMarket
    Created on : Feb 17, 2024, 11:18:44 AM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Modal Thông tin -->
<div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="infoModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="infoModalLabel">Thông tin đơn trung gian</h5>
            </div>
            <div class="modal-body">
                <div class="card-body">
                    <div autocomplete="off">
                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Mã sản phẩm (*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="mysaleorderId"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Người bán</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="createdBy"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Chủ đề trung gian (*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="title"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Giá tiền (VNĐ)(*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="moneyValue"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Bên Chịu Phí Trung Gian (*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="isSellerChargeFee"></div>
                        </div>
                       
                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Phí trung gian</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="feeOnSuccess"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Tổng tiền bên mua cần thanh toán</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="totalMoneyForBuyer"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Mô tả (*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="description"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Phương thức liên hệ</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="contact"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Thời gian tạo</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="createdAt"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Cập nhật cuối</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="updatedAt"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Link chia sẻ</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="shareLink"></div>
                        </div>
                    </div>
                    <div class="pull-right text-center">
                        <button type="button" data-target="#modalBuy" data-toggle="modal" class="mr-1 btn-white-space btn btn-success">
                            <i class="fa fa-shopping-cart"></i> Mua
                        </button>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
