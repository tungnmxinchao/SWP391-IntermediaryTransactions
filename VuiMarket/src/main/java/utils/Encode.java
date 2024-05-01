/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class Encode {

    public static String toSHA1(String str) {
        String salt = "tug11man29kha@mnhvg";
        String result = null;

        str = str + salt;
        try {
            byte[] dataBytes = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(dataBytes);
            result = Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String rechargeCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);

        // Thêm ngẫu nhiên 8 ký tự từ chuỗi characters
        for (int i = 0; i < 8; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        // In ra chuỗi mã hóa
        System.out.println(rechargeCode());
    }
}
