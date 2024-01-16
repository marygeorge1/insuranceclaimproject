package com.sparta.insuranceclaim.controller;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.repository.UserRepository;

import jakarta.validation.Valid;

import com.sparta.insuranceclaim.service.ClaimService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClaimSubmissionController {

    Logger log = LoggerFactory.getLogger(getClass());

private final ClaimService claimService;

    public ClaimSubmissionController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping("/claim/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getCreateForm(@ModelAttribute("claim") Claim dummyClaim){
        return "claimform";
    }

    @PostMapping("/claim/create")
    public String createClaim(@Valid Claim claim, BindingResult result, Authentication authentication){
        if(result.hasErrors()){
            return "claimform";
        }
        User loggedInUser=(User)authentication.getPrincipal();
        claim = claimService.addClaim(claim,loggedInUser);
        claimService.detectFraud(claim);

        return "homepage";
    }
}
