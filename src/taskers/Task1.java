/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import javafx.application.Platform;

/**
 *
 * @author dalemusser
 * 
 * This example uses an object passed in with a notify()
 * method that gets called when a notification is to occur.
 * To accomplish this the Notifiable interface is needed.
 * 
 */
public class Task1 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;
    
    private Notifiable notificationTarget;
    
    enum status {
        STARTED,
        RUNNING,
        COMPLETE
    }
    
    private status progress;
    private String message;
    
    public Task1(int maxValue, int notifyEvery)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
    }
    
    @Override
    public void run() {
        doNotify(status.STARTED, 0);
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify(status.RUNNING, i);
            }
            
            if (exit) {
                //return;
                break;
            }
        }
        doNotify(status.COMPLETE, 0);
    }
    
    
    public void end() {
        exit = true;
    }
    
    public void setNotificationTarget(Notifiable notificationTarget) {
        this.notificationTarget = notificationTarget;
    }
    
    private void doNotify(status progress, int num) {
        // this provides the notification through a method on a passed in object (notificationTarget)
        if (notificationTarget != null) {
            Platform.runLater(() -> {
                switch (progress) {
                    case STARTED:
                        message = "Task1 start.";
                        break;
                    case RUNNING:
                        message = "It happened in Task1: " + num;
                        break;
                    case COMPLETE:
                        message = "Task1 done.";
                        break;
                    default: 
                        break;
                }
                notificationTarget.notify(message);
            });
        }
    }
}
