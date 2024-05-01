/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control.MySaleOrder;

/**
 *
 * @author pc
 */
import dal.DAO;
import dal.MySaleOrder.MySaleOrderDAO;
import entity.User;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DeductBalanceHandler implements Runnable {

    private BlockingQueue<Integer> userIdQueue;
    private DAO dao;
    private MySaleOrderDAO mySaleOrderDao;

    public DeductBalanceHandler() {
        userIdQueue = new LinkedBlockingQueue<>();
        dao = new DAO();
        mySaleOrderDao = new MySaleOrderDAO();
        // Start the handler as a separate thread
        new Thread(this).start();
    }

    public void addToQueue(int userId) {
        userIdQueue.offer(userId);
    }

    @Override
    public void run() {
        try {
            while (true) {
                int userId = userIdQueue.take();

                // Lấy thông tin người dùng để cập nhật số dư
                User inforUserById = dao.findInforUserBuyId(userId);

                // Cập nhật số dư
                int newBalance = inforUserById.getBalance() - 5000;
                mySaleOrderDao.updateBalanceUser(userId, newBalance);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
