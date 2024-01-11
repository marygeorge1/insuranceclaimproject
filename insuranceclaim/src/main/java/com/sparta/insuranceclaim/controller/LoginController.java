package com.sparta.insuranceclaim.controller;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.service.UserClaimStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserClaimStatusService userClaimStatusService;

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
    public String userHomepage(Model model, Authentication authentication) {
        User loggedInUser = (User)authentication.getPrincipal();
        List<Claim> submittedClaims = userClaimStatusService.getAllClaimByLoggedInUser(loggedInUser);
        boolean showNotification = false;
        for(Claim claim : submittedClaims) {
            if(claim.getClaimStatus().equals("approved")) {
                showNotification = true;
                break;
            }
        }
        showNotification = true;
        model.addAttribute("showNotification",showNotification);
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
