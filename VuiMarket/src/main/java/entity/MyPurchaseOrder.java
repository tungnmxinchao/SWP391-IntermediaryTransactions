/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author TNO
 */
@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyPurchaseOrder {

    private int id;
    private int idBuyer;
    private String buyer;
    private int idUser;
    private String createdBy;
    private String status;
    private String title;
    private String isSellerChargeFee;
    private String feeOnSuccess;
    private String totalMoneyForBuyer;
    private String moneyValue;
    private String sellerReceivedOnSuccess;
    private String description;
    private String hiddenValue;
    private String contact;
    private String isPublic;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String shareLink;
    private String customerCanComplain;

    public String getIsPublic(String isPublic) {
        if (isPublic.equalsIgnoreCase("0")) {
            return "Riêng Tư";
        }
        return "Công Khai";
    }

    public String getIsSellerFee(String isSellerFee) {
        if (isSellerFee.equalsIgnoreCase("0")) {
            return "Bên Mua";
        }
        return "Bên Bán";
    }

    public String getStatus(int status) {
        switch (status) {
            case 0:
                return "Mới Tạo";
            case 1:
                return "Sẵn sàng giao dịch ";
            case 2:
                return "Bên mua đang kiểm tra hàng";
            case 3:
                return "Hủy";
            case 4:
                return "Hoàn thành";
            case 5:
                return "Bên mua khiếu nại sản phẩm";
            case 6:
                return "Chờ bên mua xác nhận khiếu nại không đúng";
            case 7:
                return "Yêu cầu quản trị viên trung gian";
            default:
                throw new AssertionError();
        }
    }
}
