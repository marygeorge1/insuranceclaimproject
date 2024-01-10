package com.sparta.insuranceclaim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/homepage")
    public String goHome() {
        return "homepage";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name="logout", required = false) String loggedOut
            , @RequestParam(name="error", required = false) String loginFailed, Model model) {
        if (loggedOut != null) {
            model.addAttribute("logoutMessage", true);
        }
        if (loginFailed != null) {
            model.addAttribute("incorrectDetails", true);
        }

        return "login";
    }
}
