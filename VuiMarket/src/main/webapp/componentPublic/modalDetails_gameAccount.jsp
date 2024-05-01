<%-- 
    Document   : modalDetails_gameAccount
    Created on : Jan 27, 2024, 7:42:45 PM
    Author     : TNO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Modal Thông tin -->
<div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="infoModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="infoModalLabel">Thông tin sản phẩm</h5>
                <div class="pull-right">
                    <button type="button" data-target="#buyModal" data-toggle="modal" class="mr-1 btn-white-space btn btn-success">
                        <i class="fa fa-shopping-cart"></i> Mua
                    </button>
                </div>
            </div>
            <div class="modal-body">
                <div class="card-body">
                    <div autocomplete="off">

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3">
                                <label class=""><b>Hình ảnh sản phẩm</b></label>
                            </div>
                            <div class="col-md-9">
                                <div>
                                    <div>
                                        <div class="row">
                                            <div class="col-md-4 mb-2">
                                                <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Image_manquante_2.svg/400px-Image_manquante_2.svg.png" alt="No image" class="img-fluid img-hover-opacity">
                                            </div>
                                        </div>
                                    </div>
                                    <input disabled="" type="file" class="form-control-file">
                                </div>
                            </div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3">
                                <label class=""><b>Người cung cấp sản phẩm (*)</b></label>
                            </div>
                            <div class="col-md-9">
                                <div class="input-group">
                                    <input disabled="" type="text" class="form-control" value="system_admin">
                                    <div class="input-group-append">
                                        <button type="button" class="btn btn-primary"><i class="fa fa-plus"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Danh mục sản phẩm (*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="category"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Mã sản phẩm (*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="productId"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Tên sản phẩm (*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="productName"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Đơn giá (VNĐ) (*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="productPrices"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Số lượng còn lại (*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="quantity"></div>
                        </div>

                        <div class="position-relative row form-group">
                            <div class="form-label-horizontal col-md-3"><label class=""><b>Mô tả sản phẩm (*)</b></label></div>
                            <div class="col-md-9"><input disabled="" placeholder="" type="text" class="form-control" name="productDetails"></div>
                        </div>

                    </div>
                    <div class="pull-right text-center">
                        <button type="button" data-target="#buyModal" data-toggle="modal" class="mr-1 btn-white-space btn btn-success">
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
