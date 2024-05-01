package constant;

import dal.DAO;
import dal.MySaleOrder.MySaleOrderDAO;
import entity.User;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BalanceHandler implements Runnable {

    private BlockingQueue<DeductionRequest> requestQueue;
    private DAO dao;
    private MySaleOrderDAO mySaleOrderDao;

    public BalanceHandler() {
        requestQueue = new LinkedBlockingQueue<>();
        dao = new DAO();
        mySaleOrderDao = new MySaleOrderDAO();
        // Start the handler as a separate thread
        new Thread(this).start();
    }

    public void addToQueue(int userId, int amountToDeduct) {
        DeductionRequest request = new DeductionRequest(userId, amountToDeduct);
        requestQueue.offer(request);
    }

    @Override
    public void run() {
        try {
            while (true) {
                DeductionRequest request = requestQueue.take();
                int userId = request.getUserId();
                int amountToDeduct = request.getAmountToDeduct();

                User inforUserById = dao.findInforUserBuyId(userId);

                int newBalance = inforUserById.getBalance() + amountToDeduct;
                mySaleOrderDao.updateBalanceUser(userId, newBalance);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class DeductionRequest {

    private int userId;
    private int amountToDeduct;

    public DeductionRequest(int userId, int amountToDeduct) {
        this.userId = userId;
        this.amountToDeduct = amountToDeduct;
    }

    public int getUserId() {
        return userId;
    }

    public int getAmountToDeduct() {
        return amountToDeduct;
    }
}
