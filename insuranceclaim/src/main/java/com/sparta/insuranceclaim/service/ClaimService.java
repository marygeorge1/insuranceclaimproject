package com.sparta.insuranceclaim.service;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.CustomerDetail;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
    public Claim addClaim (Claim claim, User loggedInUser) {
        claim.setDateOfSubmission(LocalDate.now());
        claim.setReferenceId(generateRefferenceNumber(claim));
        claim.setClaimStatus("submitted");
        claim.setUser(loggedInUser);
        claim.setFraudFlag(false);
        claim.setFraudFlagInformation("");
        claimRepository.save(claim);
        return claim;
    }

    public List<Claim> findAllClaims() {
       return claimRepository.findAll();
    }

    public Optional<Claim> findClaimById(Integer id) {
       return claimRepository.findById(id);
    }

    public void detectFraud(Claim claim) {
        User user = claim.getUser();
        CustomerDetail userDetails = user.getCustomerDetails();
        List<Claim> previousClaims = claimRepository.findByUser(user);
        String flagInformation = claim.getFraudFlagInformation();

        detectFraudBasedOnPreviousSuspiciousClaim(claim, previousClaims, flagInformation);
        flagInformation = claim.getFraudFlagInformation();
        detectFraudBasedOnNumberOfClaims(claim, previousClaims, flagInformation);
        flagInformation = claim.getFraudFlagInformation();
        detectFraudBasedOnClaimSubmissionDate(claim, userDetails, flagInformation);
    }

    private void detectFraudBasedOnNumberOfClaims(Claim claim, List<Claim> previousClaims, String flagInformation) {
        List<Claim> claimsWithinAYear = getClaimsWithinAYear(claim, previousClaims);
        int numberOfClaimsWithin1Year = claimsWithinAYear.size();

        if (numberOfClaimsWithin1Year > 3) {
            processFraudBasedOnNumberOfClaims(claim, claimsWithinAYear, flagInformation);
        }
    }

    private List<Claim> getClaimsWithinAYear(Claim claim, List<Claim> previousClaims) {
        List<Claim> claimsWithinAYear = new ArrayList<>();
        for (Claim previousClaim : previousClaims) {
            if (previousClaim.getDateOfSubmission().isAfter(claim.getDateOfSubmission().minusYears(1))) {
                claimsWithinAYear.add(previousClaim);
            }
        }
        return claimsWithinAYear;
    }

    private void processFraudBasedOnNumberOfClaims(Claim claim, List<Claim> claimsWithinAYear, String flagInformation) {
        claim.setFraudFlag(true);
        flagInformation = appendToFlagInformation(flagInformation, "Number of claims within 1 year exceeds 3.");
        claim.setFraudFlagInformation(flagInformation);
        claimRepository.save(claim);

        for (Claim claimWithinAYear : claimsWithinAYear) {
            processFraudForClaimWithinAYear(claimWithinAYear, flagInformation);
        }
    }

    private void processFraudForClaimWithinAYear(Claim claimWithinAYear, String flagInformation) {
        claimWithinAYear.setFraudFlag(true);
        String previousClaimFlagInformation = claimWithinAYear.getFraudFlagInformation();
        if (!previousClaimFlagInformation.contains("Number of claims within 1 year exceeds 3.")) {
            claimWithinAYear.setFraudFlagInformation(
                    appendToFlagInformation(previousClaimFlagInformation, "Number of claims within 1 year exceeds 3."));
        }
        claimRepository.save(claimWithinAYear);
    }

    private void detectFraudBasedOnPreviousSuspiciousClaim(Claim claim, List<Claim> previousClaims, String flagInformation) {
        if (hasPreviousSuspiciousClaim(previousClaims)) {
            claim.setFraudFlag(true);
            flagInformation = appendToFlagInformation(flagInformation,
                    "Claimant has a previously submitted claim that was found to be suspicious.");
            claim.setFraudFlagInformation(flagInformation);
            claimRepository.save(claim);
        }
    }

    private boolean hasPreviousSuspiciousClaim(List<Claim> previousClaims) {
        return previousClaims.stream().anyMatch(Claim::getFraudFlag);
    }

    private void detectFraudBasedOnClaimSubmissionDate(Claim claim, CustomerDetail userDetails, String flagInformation) {
        LocalDate dateOfClaimSubmission = claim.getDateOfSubmission();
        LocalDate dateOfJoining = userDetails.getDateJoining();

        if (dateOfJoining.isAfter(dateOfClaimSubmission.minusDays(2))) {
            processFraudBasedOnClaimSubmissionDate(claim, flagInformation);
        }
    }

    private void processFraudBasedOnClaimSubmissionDate(Claim claim, String flagInformation) {
        claim.setFraudFlag(true);
        flagInformation = appendToFlagInformation(flagInformation,
                "Claim was made within 2 days of joining insurance plan.");
        claim.setFraudFlagInformation(flagInformation);
        claimRepository.save(claim);
    }

    private String appendToFlagInformation(String flagInformation, String message) {
        if (!flagInformation.isEmpty()) {
            flagInformation += "\n";
        }
        return flagInformation += message;
    }

    public List<Claim> findAllClaimsSorted(String sortField, String sortOrder) {
        List<Claim> claims = claimRepository.findAll(); // Assuming you have a claimRepository

        Comparator<Claim> comparator;

        switch (sortField.toLowerCase()) {
            case "id":
                comparator = Comparator.comparing(Claim::getId);
                break;
            case "referenceid":
                comparator = Comparator.comparing(Claim::getReferenceId);
                break;
            case "firstname":
                comparator = Comparator.comparing(Claim::getFirstName);
                break;
            case "lastname":
                comparator = Comparator.comparing(Claim::getLastName);
                break;
            case "email":
                comparator = Comparator.comparing(Claim::getEmail);
                break;
            case "carregistration":
                comparator = Comparator.comparing(Claim::getCarRegistration);
                break;
            case "dateofincident":
                comparator = Comparator.comparing(Claim::getDateOfIncident);
                break;
            case "claimstatus":
                comparator = Comparator.comparing(Claim::getClaimStatus);
                break;
            case "fraudflag":
                comparator = Comparator.comparing(Claim::getFraudFlag);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }

        if ("desc".equalsIgnoreCase(sortOrder)) {
            claims.sort(comparator.reversed());
        } else {
            claims.sort(comparator);
        }

        return claims;
    }
}
