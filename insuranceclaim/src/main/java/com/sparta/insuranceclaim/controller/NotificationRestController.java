package com.sparta.insuranceclaim.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationRestController {

    @PostMapping("/updateNotification")
    public void updateNotification(@RequestBody String notificationUpdate) {
        System.out.println("The notification status has been updated" + notificationUpdate);
    }

}
