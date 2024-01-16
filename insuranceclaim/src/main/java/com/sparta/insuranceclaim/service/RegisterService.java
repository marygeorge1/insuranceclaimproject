package com.sparta.insuranceclaim.service;

import com.sparta.insuranceclaim.dto.UserDTO;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.CustomerDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CustomerDetailRepository customerDetailRepository;

    public User createNewUser(UserDTO userDetails) {
        User newUser = new User();
        newUser.setUsername(userDetails.getUsername());
        newUser.setPassword(encoder.encode(userDetails.getPassword()));
        newUser.setRole("ROLE_USER");
        newUser.setCustomerDetails(customerDetailRepository.findById(1).get());

        return newUser;
    }

}
