package com.sparta.insuranceclaim.service;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;

   @Autowired public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    private String generateRefferenceNumber(Claim claim){
       String refferenceNo = "";
       refferenceNo=refferenceNo.concat(claim.getFirstName().substring(0,1));
       refferenceNo=refferenceNo.concat(claim.getLastName().substring(0,1));
       refferenceNo+= claim.getDateOfIncident().getYear();
       refferenceNo+= claim.getDateOfIncident().getMonthValue();
       refferenceNo+= claim.getDateOfIncident().getDayOfMonth();
       refferenceNo+= claim.getDateOfSubmission().getYear();
       refferenceNo+= claim.getDateOfSubmission().getMonthValue();
       refferenceNo+= claim.getDateOfSubmission().getDayOfMonth();
refferenceNo+= claim.getCarRegistration().substring(0,3);


       return refferenceNo;
    }
    public void  addClaim (Claim claim) {
        claim.setDateOfSubmission(LocalDate.now());
        claim.setReferenceId(generateRefferenceNumber(claim));
        claimRepository.save(claim);
    }
}
