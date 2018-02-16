/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.application.Platform;

/**
 *
 * @author dalemusser
 * 
 * This example uses PropertyChangeSupport to implement
 * property change listeners.
 * 
 */
public class Task3 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;
    
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    enum status {
        STARTED,
        RUNNING,
        COMPLETE
    }
    
    private status progress;
    private String message;
    
    public Task3(int maxValue, int notifyEvery)  {
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
                return;
            }
        }
        doNotify(status.COMPLETE, 0);
    }
    
    public void end() {
        exit = true;
    }
    
    // the following two methods allow property change listeners to be added
    // and removed
    public void addPropertyChangeListener(PropertyChangeListener listener) {
         pcs.addPropertyChangeListener(listener);
     }

     public void removePropertyChangeListener(PropertyChangeListener listener) {
         pcs.removePropertyChangeListener(listener);
     }
     
    private void doNotify(status progress, int num ) {
        // this provides the notification through the property change listener
        Platform.runLater(() -> {
            switch(progress){
                case STARTED:
                    message = "Task3 start.";
                    break;
                case RUNNING:
                    message = "It happened in Task3: " + num;
                    break;
                case COMPLETE:
                    message = "Task3 done.";
                    break;
                default:
                    break;
                }
            // I'm choosing not to send the old value (second param).  Sending "" instead.
            pcs.firePropertyChange("message", "", message);
        });
    }
}