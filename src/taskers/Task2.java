/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import java.util.ArrayList;
import javafx.application.Platform;

/**
 *
 * @author dalemusser
 * 
 * This example uses a Notification functional interface.
 * This allows the use of anonymous inner classes or
 * lambda expressions to define the method that gets called
 * when a notification is to be made.
 */
public class Task2 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;
    
    private ArrayList<Notification> notifications = new ArrayList<>();
    
    enum status {
        STARTED,
        RUNNING,
        COMPLETE
    }
    
    private status progress;
    private String message;
    
    public Task2(int maxValue, int notifyEvery)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
    }
    
    @Override
    public void run() {
        doNotify(Task2.status.STARTED, 0);
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify(Task2.status.RUNNING, i);
            }
            
            if (exit) {
                //return;
                break;
            }
        }
        doNotify(Task2.status.COMPLETE, 0);
    }
    
    public void end() {
        exit = true;
    }
    
    // this method allows a notification handler to be registered to receive notifications
    public void setOnNotification(Notification notification) {
        this.notifications.add(notification);
    }
    
    private void doNotify(status progress, int num) {
        // this provides the notification through the registered notification handler
        for (Notification notification : notifications) {
            Platform.runLater(() -> {
                switch(progress){
                    case STARTED:
                        message = "Task2 start.";
                        break;
                    case RUNNING:
                        message = "It happened in Task2: " + num;
                        break;
                    case COMPLETE:
                        message = "Task2 done.";
                        break;
                    default:
                        break;
                }
                notification.handle(message);
            });
        }
    }
}