package com.sparta.insuranceclaim.service;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.CustomerDetail;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.repository.CustomerDetailRepository;
import com.sparta.insuranceclaim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDataService {

    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;
    private final CustomerDetailRepository customerDetailRepository;

    @Autowired
    public AdminDataService(ClaimRepository claimRepository, UserRepository userRepository, CustomerDetailRepository customerDetailRepository) {
        this.claimRepository = claimRepository;
        this.userRepository = userRepository;
        this.customerDetailRepository = customerDetailRepository;
    }

    public CustomerDetail getCustomerDetailByClaimId(int claimId) {
        Claim claim = claimRepository.findById(claimId).get();
        User user = claim.getUser();
        if(user == null) {
            System.out.println("it all went wrong, no user");
        }
        CustomerDetail customerDetail = user.getCustomerDetails();
        if(customerDetail == null) {
            System.out.println("it all went wrong, no user customer detail");
        }
        return customerDetail;
    }


}
