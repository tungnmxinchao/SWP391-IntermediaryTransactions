<%-- 
    Document   : modalHandling
    Created on : Mar 18, 2024, 10:19:32 PM
    Author     : TNO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="handlingOrder" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Thông tin đơn trung gian</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group row">
                        <label for="handlingMatrunggian" class="col-sm-3 col-form-label">Mã trung gian</label>
                        <div class="col-sm-9">
                            <input disabled type="text" class="form-control" id="handlingMatrunggian" value="${RecordMyOrder.id}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="handlingnguoimua" class="col-sm-3 col-form-label">Người bán</label>
                        <div class="col-sm-9">
                            <input style="display: none" type="text" class="form-control" id="idHandlingSeller" value="${RecordMyOrder.idUser}">
                            <input disabled type="text" class="form-control" id="nguoiban" value="${RecordMyOrder.createdBy}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="handlingBuyer" class="col-sm-3 col-form-label">Người mua</label>
                        <div class="col-sm-9">
                            <input style="display: none" type="text" class="form-control" id="idHandlingBuyer" value="${RecordMyOrder.idBuyer}">
                            <input disabled type="text" class="form-control" id="handlingBuyer" value="${RecordMyOrder.buyer}">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label">Bên Đúng</label>
                        <div class="col-sm-9">
                            <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                <label class="btn btn-secondary ">
                                    <input type="radio" name="correctSide" id="bennguoiban" value="1" autocomplete="off"> Bên Bán
                                </label>
                                <label class="btn btn-secondary">
                                    <input type="radio" name="correctSide" id="bennguoimua" value="0" autocomplete="off"> Bên Mua
                                </label> 
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button id="adminConfirm" type="button" class="btn btn-success">Xác nhận</button>
            </div>
        </div>
    </div>
</div>