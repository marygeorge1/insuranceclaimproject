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

    private final UserClaimStatusService userClaimStatusService;

    private final ClaimRepository claimRepository;

    @Autowired
    public LoginController(UserClaimStatusService userClaimStatusService, ClaimRepository claimRepository) {
        this.userClaimStatusService = userClaimStatusService;
        this.claimRepository = claimRepository;
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/homepage/user")
    public String userHomepage(Model model, Authentication authentication) {
        User loggedInUser = (User) authentication.getPrincipal();
        List<Claim> submittedClaims = userClaimStatusService.getAllClaimByLoggedInUser(loggedInUser);
        boolean showNotification = processClaimsForNotification(submittedClaims);
        model.addAttribute("showNotification", showNotification);
        return "homepage";
    }

    private boolean processClaimsForNotification(List<Claim> submittedClaims) {
        for (Claim claim : submittedClaims) {
            if (claim != null
                    && claim.getDisplayNotificationCustomer() != null
                    && claim.getDisplayNotificationCustomer()) {
                claim.setDisplayNotificationCustomer(false);
                userClaimStatusService.saveClaim(claim);
                return true;
            }
        }
        return false;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/homepage/admin")
    public String adminHomepage(Model model, Authentication authentication) {
        User loggedInUser = (User) authentication.getPrincipal();
        // Need to get all claims, not just by user, all claims
        // Also, go through all claims that are new and set admin flag to false in this case
        List<Claim> allClaims = userClaimStatusService.getAllClaims();
        boolean showNotification = processClaimsForAdminNotification(allClaims);
        model.addAttribute("showNotification", showNotification);
        return "admin-homepage";
    }

    private boolean processClaimsForAdminNotification(List<Claim> allClaims) {
        for (Claim claim : allClaims) {
            if (claim != null
                    && claim.getDisplayNotificationAdmin() != null
                    && claim.getDisplayNotificationAdmin()) {
                claim.setDisplayNotificationAdmin(false);
                userClaimStatusService.saveClaim(claim);
                return true;
            }
        }
        return false;
    }

    @PreAuthorize("hasRole('ROLE_AGENT')")
    @GetMapping("/homepage/agent")
    public String agentHomepage() {
        return "agent-homepage";
    }

}

