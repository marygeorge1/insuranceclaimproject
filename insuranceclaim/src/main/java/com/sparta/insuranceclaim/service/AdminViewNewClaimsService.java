package com.sparta.insuranceclaim.service;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminViewNewClaimsService {
    @Autowired
    private ClaimRepository claimRepository;

    public List<Claim> getAllNewClaims() {

        return claimRepository.findAllByClaimStatus("submitted");
    }

    public void updateClaimStatus(String status, int claimId) {
        Claim claim = claimRepository.findById(claimId).get();

        claim.setClaimStatus(status);

        claimRepository.save(claim);
    }

}
