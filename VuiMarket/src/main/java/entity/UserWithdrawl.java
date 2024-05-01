/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserWithdrawl{

    private int id;
    private String status;
    private int uid;
    private String fullName;
    private String amount;
    private String accountNumber;
    private String accountOwner;
    private String banhName;
    private Date createdAt;
    private Date updateAt;
    private String withdrawCode;
    
    public String getStatus(int status) {
        switch (status) {
            case 1:
                return "Chờ xử lý";
            case 2:
                return "Hoàn thành";
            case 3: 
                return "Thất bại";
            default:
                throw new AssertionError();
        }
    }
}
