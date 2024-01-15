package com.sparta.insuranceclaim.controller;


import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.CustomerDetail;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.service.AdminDataService;
import com.sparta.insuranceclaim.service.ClaimService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminDataVisualizationController {

    private final ClaimService claimService;
    private final AdminDataService adminDataService;
    private final ClaimRepository claimRepository;

    public AdminDataVisualizationController(ClaimService claimService, AdminDataService adminDataService,
                                            ClaimRepository claimRepository) {
        this.claimService = claimService;
        this.adminDataService = adminDataService;
        this.claimRepository = claimRepository;
    }

    @GetMapping("/viewClaimsData")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminViewClaimsData(Model model) {
        List<Claim> submittedClaims = claimService.findAllClaims();
        model.addAttribute("claims", submittedClaims);
        return "admin-view-claims-data";
    }

    //@GetMapping("/viewClaimData/{claimId}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    //public String viewClaimDetails(@PathVariable Integer claimId, Model model) {
    //    CustomerDetail customerDetail = adminDataService.getCustomerDetailByClaimId(claimId);
    //    Claim claim = claimService.findClaimById(claimId).get();
    //    model.addAttribute("claim", claim);
    //    model.addAttribute("customerDetail", customerDetail);
    //    return "admin-view-all-claim-details";
    //}

    @GetMapping("/viewClaimData/{claimId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String viewClaimDetails(@PathVariable Integer claimId, Model model) {
        CustomerDetail customerDetail = adminDataService.getCustomerDetailByClaimId(claimId);
        Claim claim = claimRepository.findById(claimId).get();
        model.addAttribute("claim", claim);
        model.addAttribute("customerDetail", customerDetail);
        return "admin-view-all-claim-details";
    }

}
