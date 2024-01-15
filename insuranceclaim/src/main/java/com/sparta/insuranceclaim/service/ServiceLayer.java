package com.sparta.insuranceclaim.service;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.repository.CustomerDetailRepository;
import com.sparta.insuranceclaim.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceLayer implements IServiceLayer {
    private final ClaimRepository claimRepository;
    private final CustomerDetailRepository customerDetailRepository;
    private final UserRepository userRepository;

    public ServiceLayer(ClaimRepository claimRepository, CustomerDetailRepository customerDetailRepository, UserRepository userRepository) {
        this.claimRepository = claimRepository;
        this.customerDetailRepository = customerDetailRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Claim> getAllClaimsWithUser(User user) {
        return claimRepository.findByUser(user);
    }

    @Override
    public Optional<User> getUserByID(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<Claim> getClaimByID(Integer id) {
        return claimRepository.findById(id);
    }
}
