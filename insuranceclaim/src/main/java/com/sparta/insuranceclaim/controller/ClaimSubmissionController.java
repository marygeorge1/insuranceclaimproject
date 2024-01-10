package com.sparta.insuranceclaim.controller;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.repository.UserRepository;

import jakarta.validation.Valid;

import com.sparta.insuranceclaim.service.ClaimService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClaimSubmissionController {
    private  ClaimRepository claimRepository;
    private  UserRepository userRepository;
private ClaimService claimService;

    public ClaimSubmissionController(ClaimRepository claimRepository, UserRepository userRepository, ClaimService claimService) {
        this.claimRepository = claimRepository;
        this.userRepository = userRepository;
        this.claimService = claimService;
    }

    @GetMapping("/claim/create")
    public String getCreateForm(@ModelAttribute("claim") Claim dummyClaim){
        return "claimform";
    }

    @PostMapping("/claim/create")
    public String createClaim(@Valid Claim claim, BindingResult result){
        if(result.hasErrors()){
            return "claimform";
        }
        claimService.addClaim(claim);
        return "claimform";
        
    }
}
