package com.sparta.insuranceclaim.service;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.CustomerDetail;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public void addClaim (Claim claim, User loggedInUser) {
        claim.setDateOfSubmission(LocalDate.now());
        claim.setReferenceId(generateRefferenceNumber(claim));
        claim.setClaimStatus("submitted");
        claim.setUser(loggedInUser);
        claimRepository.save(claim);
    }

    public List<Claim> findAllClaims() {
       return claimRepository.findAll();
    }

    public Optional<Claim> findClaimById(int id) {
       return claimRepository.findById(id);
    }

    public void detectFraud(Claim claim) {

       User user = claim.getUser();
       CustomerDetail userDetails = user.getCustomerDetails();
       int numberOfClaimsFromUser = claimRepository.findByUser(user).size();
       String flagInformation = claim.getFraudFlagInformation();

       // Suspicious if number of claims > 3
       if(numberOfClaimsFromUser > 3) {
           claim.setFraudFlag(true);
           claim.setFraudFlagInformation(appendToFlagInformation(flagInformation, "Number of previous claims exceeds 3"));
           claimRepository.save(claim);
       }

       // Suspicious if previously submitted claim was found suspicious
        List<Claim> previousClaims = claimRepository.findByUser(user);
        for(Claim previousClaim : previousClaims) {
            if(previousClaim.getFraudFlag()) {
                claim.setFraudFlag(true);
                claim.setFraudFlagInformation(appendToFlagInformation(flagInformation, "Claimant has a previously submitted claim that was found to be suspicious"));
                claimRepository.save(claim);
            }
        }

        // Suspicious if claim submitted within few days of joining insurance
        LocalDate dateOfClaimSubmission = claim.getDateOfSubmission();
        LocalDate dateOfJoining = userDetails.getDateJoining();
        if (dateOfJoining.isAfter(dateOfClaimSubmission.minusDays(2))) {
            claim.setFraudFlag(true);
            claim.setFraudFlagInformation(appendToFlagInformation(flagInformation, "Claim was made within 2 days of joining insurance plan"));
            claimRepository.save(claim);
        }
    }

    private String appendToFlagInformation(String flagInformation, String message) {
        if (!flagInformation.isEmpty()) {
            flagInformation += "\n";
        }
        return flagInformation += message;
    }

}
