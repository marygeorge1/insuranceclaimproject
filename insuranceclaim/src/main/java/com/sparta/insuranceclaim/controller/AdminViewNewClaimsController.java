package com.sparta.insuranceclaim.controller;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.service.AdminViewNewClaimsService;
import com.sparta.insuranceclaim.service.UserClaimStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminViewNewClaimsController {

    @Autowired
    AdminViewNewClaimsService adminViewNewClaimsService;

    @Autowired
    UserClaimStatusService userClaimStatusService;

    @GetMapping("/new-claims")
    public String getAllNewClaims(Model model) {
        model.addAttribute("newClaims", adminViewNewClaimsService.getAllNewClaims());
        return "admin-new-claims";
    }

    @GetMapping("/view/claims/{claimId}")
    public String showClaimDetails(@PathVariable Integer claimId, Model model){
        Claim claim=userClaimStatusService.getClaimById(claimId);
        model.addAttribute("claim",claim);
        return "admin-view-claim-details";
    }

    @GetMapping("/approve/{claimId}")
    public String approveClaim(@PathVariable int claimId) {
        adminViewNewClaimsService.updateClaimStatus("approved", claimId);
        return "redirect:/new-claims";
    }

    @GetMapping("/deny/{claimId}")
    public String denyClaim(@PathVariable int claimId) {
        adminViewNewClaimsService.updateClaimStatus("denied", claimId);
        return "redirect:/new-claims";

    }

    @GetMapping("/flag/{claimId}")
    public String flagClaim(@PathVariable int claimId) {
        adminViewNewClaimsService.updateClaimStatus("flagged", claimId);
        return "redirect:/new-claims";

    }
}
