package com.sparta.insuranceclaim.service;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserClaimStatusService {

    @Autowired
    private ClaimRepository claimRepository;

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public List<Claim> getAllClaimByLoggedInUser(User loggedInUser) {
        return claimRepository.findByUser(loggedInUser);
    }

    public Claim getClaimById(Integer id){
        return claimRepository.findById(id).get();
    }

    public void saveClaim(Claim claim) {
        claimRepository.save(claim);
    }
}
