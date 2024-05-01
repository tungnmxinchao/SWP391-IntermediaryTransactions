/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.AuthenWithCapcha;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import utils.CapchaUtils;

/**
 *
 * @author TNO
 */
public class GenerateCapcha extends HttpServlet {
    
    private int lastID;
    private String base64Image;
    
    public GenerateCapcha() {
        lastID = 1;
    }
       
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // generate random capchatext based on length of text
        String captchaText = CapchaUtils.generateRandomCaptchaText(5); 

        // generrate img capcha from text of captcha
        BufferedImage bufferedImage = CapchaUtils.generateImgCaptchFromText(captchaText);
         
        // get string id capcha
        String idCapcha = String.valueOf(lastID++);
        // Get the current timestamp
        long createTime = System.currentTimeMillis();
        // Calculate expiration time (2 minutes)
        long expirationTime = createTime + (200000);
        
        // convert time to string
        String createTimeString = String.valueOf(createTime);
        // convert expiration time to string
        String expirationTimeString = String.valueOf(expirationTime);
         
        Jedis jedis = new Jedis("localhost", 6379);
        // set into jedis cache
        jedis.set(idCapcha, captchaText);
        jedis.set("createTime", createTimeString);
        jedis.set("expirationTime", expirationTimeString);
        
        // close connect with cache
        jedis.close();
                   
        base64Image = CapchaUtils.ConvertImgToBase64(bufferedImage);

        // Create a JSON object
        JSONObject captchaJson = new JSONObject();
        captchaJson.put("bufferedImage", base64Image);
        captchaJson.put("idCapcha", idCapcha);

        // Convert JSON object to JSON string
        String captchaJsonString = captchaJson.toString();
        
        HttpSession session = request.getSession();
        
        // Set the JSON string as a session attribute (or use it as needed)
        session.setAttribute("captchaJson", captchaJsonString);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(captchaJsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
