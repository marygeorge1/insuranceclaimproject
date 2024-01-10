package com.sparta.insuranceclaim.controller;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.repository.UserRepository;
import com.sparta.insuranceclaim.service.UserClaimStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserClaimStatusController {


    @Autowired
    private UserClaimStatusService userClaimStatusService;

    @GetMapping("/all-claims")
    public String getAllClaims(Model model, Authentication authentication){

        System.out.println(((User)(authentication.getPrincipal())).getUsername());
        User loggedInUser=(User)authentication.getPrincipal();
        List<Claim> submittedClaims=userClaimStatusService.getAllClaimByLoggedInUser(loggedInUser);
        System.out.println(submittedClaims);
        model.addAttribute("claims",submittedClaims);
        return "user-claims";

    }

}
