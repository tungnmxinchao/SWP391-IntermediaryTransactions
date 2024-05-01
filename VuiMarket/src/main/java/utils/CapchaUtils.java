/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author TNO
 */
public class CapchaUtils {

    public static Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static String generateRandomCaptchaText(int captchaLength) {

        Random random = new Random();
        StringBuilder captchaText = new StringBuilder();

        for (int i = 0; i < captchaLength; i++) {
            // Thêm các ký tự ngẫu nhiên vào chuỗi CAPTCHA
            char randomChar = (char) (random.nextInt(26) + 'A');
            captchaText.append(randomChar);
        }

        return captchaText.toString();
    }

    public static BufferedImage generateImgCaptchFromText(String captchaText) {
        int frameWidth = 300;
        int frameHeight = 150;
        int captchaWidth = 200;
        int captchaHeight = 80;

        BufferedImage bufferedImage = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Fill the background of the frame
        g2d.setColor(Color.pink);
        g2d.fillRect(0, 0, frameWidth, frameHeight);

        // Add random lines for complexity in the frame
        Random random = new Random();

        // Calculate the position to center the CAPTCHA within the frame
        int captchaX = (frameWidth - captchaWidth) / 2;
        int captchaY = (frameHeight - captchaHeight) / 2;

        // Set Font and Color
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.setColor(getRandomColor());

        // Calculate the starting x-position for the CAPTCHA
        int startX = (frameWidth - captchaText.length() * 30) / 2;

        // Draw each character of the CAPTCHA in a straight line
        for (int i = 0; i < captchaText.length(); i++) {
            g2d.drawString(String.valueOf(captchaText.charAt(i)), startX + i * 30, captchaY + 50);
        }

        for (int i = 0; i < 20; i++) {
            int x1 = random.nextInt(frameWidth);
            int y1 = random.nextInt(frameHeight);
            int x2 = random.nextInt(frameWidth);
            int y2 = random.nextInt(frameHeight);
            g2d.setColor(getRandomColor());
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(x1, y1, x2, y2);
        }

        g2d.dispose();  // Release resources

        return bufferedImage;
    }

    public static String ConvertImgToBase64(BufferedImage bufferedImage) throws IOException {
        // convert BufferedImage to string Base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        return base64Image;
    }
}
