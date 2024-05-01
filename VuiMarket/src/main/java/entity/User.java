/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

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

public class User {
    private int uid;
    private String rolename;
    private String username;
    private String pass;
    private String fullname;
    private String avatar;
    private String email;
    private String phone;
    private String detail;
    private int balance;
    private String confirmationCode; 
    private boolean isConfirmed;
}
