/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control.MySaleOrder;

/**
 *
 * @author TNO
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class UserRequestHandler {
    private final ExecutorService executorService;
    private final LinkedBlockingQueue<Runnable> queue;

    public UserRequestHandler(int maxConcurrentRequests) {
        this.executorService = Executors.newFixedThreadPool(maxConcurrentRequests);
        this.queue = new LinkedBlockingQueue<>();
        startProcessing();
    }

    public void submitRequest(Runnable request) {
        try {
            queue.put(request);
            System.out.println(queue);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void startProcessing() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Runnable request = queue.take();
                        request.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
