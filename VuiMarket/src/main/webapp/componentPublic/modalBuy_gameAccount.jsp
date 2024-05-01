<%-- 
    Document   : modalBuy_gameAccount
    Created on : Jan 27, 2024, 7:44:29 PM
    Author     : TNO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="buyModal" tabindex="-1" role="dialog" aria-labelledby="buyModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="buyModalLabel">Mua sản phẩm</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Nội dung của modal Mua -->
                <div class="card-body">
                    <div autocomplete="off">
                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Tên sản phẩm (*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="productNameBy"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Đơn giá (VNĐ) (*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="productPricesBy"></div>
                        </div>
                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Số lượng (*)</b></label></div>
                            <div class="col-md-9"><input placeholder="" type="text" class="form-control" value=""></div>
                        </div>

                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>