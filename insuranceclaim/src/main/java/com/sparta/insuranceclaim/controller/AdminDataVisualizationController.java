package com.sparta.insuranceclaim.controller;


import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.CustomerDetail;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.service.AdminDataService;
import com.sparta.insuranceclaim.service.ClaimService;
import com.sparta.insuranceclaim.service.InsurancePremiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminDataVisualizationController {

    private final ClaimService claimService;
    private final AdminDataService adminDataService;
    private final ClaimRepository claimRepository;

    @Autowired
    private InsurancePremiumService insurancePremiumService;


    public AdminDataVisualizationController(ClaimService claimService, AdminDataService adminDataService,
                                            ClaimRepository claimRepository) {
        this.claimService = claimService;
        this.adminDataService = adminDataService;
        this.claimRepository = claimRepository;
    }

    @GetMapping("/viewClaimsData")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminViewClaimsData(Model model,
                                      @RequestParam(defaultValue = "First Name") String searchField,
                                      @RequestParam(defaultValue = "") String searchTerm,
                                      @RequestParam(defaultValue = "id") String sortField,
                                      @RequestParam(defaultValue = "asc") String sortOrder) {
        List<Claim> submittedClaims= claimService.searchClaims(searchField, searchTerm);
        submittedClaims = claimService.findClaimsSorted(submittedClaims, sortField, sortOrder);
        model.addAttribute("claims", submittedClaims);
        model.addAttribute("sortOrder", sortOrder.equals("asc") ? "desc" : "asc"); // toggle sorting order for next click
        return "admin-view-claims-data";
    }

    @GetMapping("/viewClaimData/{claimId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String viewClaimDetails(@PathVariable Integer claimId, Model model) {
        CustomerDetail customerDetail = adminDataService.getCustomerDetailByClaimId(claimId);
        Claim claim = claimRepository.findById(claimId).get();
        model.addAttribute("claim", claim);
        model.addAttribute("customerDetail", customerDetail);

        // generating insurance premium data
        String premiumAmountForTheUser=insurancePremiumService.getInsurancePremiumAmount(customerDetail.getId());
        model.addAttribute("premium", premiumAmountForTheUser);
        return "admin-view-all-claim-details";
    }

}
