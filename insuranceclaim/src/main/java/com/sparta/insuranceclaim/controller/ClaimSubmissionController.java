package com.sparta.insuranceclaim.controller;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClaimSubmissionController {
    private  ClaimRepository claimRepository;
    private  UserRepository userRepository;

    public ClaimSubmissionController(ClaimRepository claimRepository, UserRepository userRepository) {
        this.claimRepository = claimRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/claim/create")
    public String getCreateForm(){
        return "claimform";
    }
    @PostMapping("/claim/create")
public String createClaim(@ModelAttribute() Claim claim){
        claimRepository.save(claim);
        return "claimform";
    }
}
