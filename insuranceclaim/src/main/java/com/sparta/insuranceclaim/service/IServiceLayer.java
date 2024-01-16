package com.sparta.insuranceclaim.service;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.User;

import java.util.List;
import java.util.Optional;

public interface IServiceLayer {

    public List<Claim> getAllClaimsWithUser(User user);

    public Optional<User> getUserByID(Integer id);
    public Optional<Claim> getClaimByID(Integer id);

}
