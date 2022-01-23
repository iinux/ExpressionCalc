package com.mycompany.helloworld.mbean;

import javax.management.Notification;
import javax.management.NotificationListener;

public class HelloListener implements NotificationListener {

    public void handleNotification(Notification notification, Object handback) {
        if (handback instanceof Hello) {
            Hello hello = (Hello) handback;
            hello.helloWorld(notification.getMessage());
        }
    }

}