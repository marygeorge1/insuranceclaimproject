package com.sparta.insuranceclaim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/homepage")
    public String goHome() {
        return "homepage";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
