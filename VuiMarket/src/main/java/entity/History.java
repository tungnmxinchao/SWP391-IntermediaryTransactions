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
public class History {

    private int id;
    private String moneyValue;
    private String typeTransaction;
    private String status;
    private String description;
    private int uId;
    private String fullName;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public String getStatus(int status) {
        switch (status) {
            case 1:
                return "Đã xử lý";
            case 2:
                return "Chưa xử lý";
            default:
                throw new AssertionError();
        }
    }

}
