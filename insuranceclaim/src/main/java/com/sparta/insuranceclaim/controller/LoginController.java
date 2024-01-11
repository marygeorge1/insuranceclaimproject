package com.sparta.insuranceclaim.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/homepage/user")
    public String userHomepage() {
        return "homepage";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/homepage/admin")
    public String adminHomepage() {
        return "admin-homepage";
    }

    @PreAuthorize("hasRole('ROLE_AGENT')")
    @GetMapping("/homepage/agent")
    public String agentHomepage() {
        return "agent-homepage";
    }




}
