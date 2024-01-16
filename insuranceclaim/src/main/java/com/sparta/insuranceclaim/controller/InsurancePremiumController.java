package com.sparta.insuranceclaim.controller;

import com.sparta.insuranceclaim.service.InsurancePremiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class InsurancePremiumController {

    @Autowired
    private InsurancePremiumService insurancePremiumService;

    @GetMapping("/insurance-premium/{customerDetailId}")
    public String getInsurancePremiumAmount(@PathVariable Integer customerDetailId, Model model){

        return "";
    }
}
