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
public class RechargeUsers {
    private int id;
    private int uid;
    private String fullname;
    private String status;
    private String moneyValue;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String code;

    public String getStatus(int status) {
        switch (status) {
            case 1:
                return "Chờ xử lý";
            case 2:
                return "Hoàn thành";
            default:
                throw new AssertionError();
        }
    }
}
