package com.sparta.insuranceclaim.controller;

import com.sparta.insuranceclaim.dto.UserDTO;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.UserRepository;
import com.sparta.insuranceclaim.service.RegisterService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class RegisterController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    RegisterService registerService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String signUp(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user-registration-form";
    }

    @Transactional
    @PostMapping("/registration/form")
    public String registrationFormData(@ModelAttribute("user")UserDTO userDetails, Model model) {
        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
        if (userOptional.isPresent()) {
            model.addAttribute("userExists", true);
            return "user-registration-form";
        }

        log.info("New user is being created");
        User user = registerService.createNewUser(userDetails);

        userRepository.save(user);

        return "redirect:/login";
    }
}
