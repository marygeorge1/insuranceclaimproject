package com.sparta.insuranceclaim;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.repository.CustomerDetailRepository;
import com.sparta.insuranceclaim.repository.UserRepository;
import com.sparta.insuranceclaim.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class InsuranceApplicationMVCTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ClaimRepository claimRepository;
    @MockBean
    private CustomerDetailRepository customerDetailRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ClaimService claimService;
    @MockBean
    private AdminDataService adminDataService;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private RegisterService registerService;
    @MockBean
    private AdminViewNewClaimsService adminViewNewClaimsService;
    @MockBean
    private UserClaimStatusService userClaimStatusService;

    @Test
    @DisplayName("Test login page")
    void testLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Test register page")
    void testRegisterPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register")).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Test view claims page")
    void testFilmsPage() throws Exception {
        Claim claim = new Claim();
        Mockito.when(claimRepository.findAll()).thenReturn(new ArrayList<>(List.of(claim)));

        mockMvc.perform(MockMvcRequestBuilders.get("/viewClaimsData?")).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Test home page gets 200")
    void testHomePage200() throws Exception {
        int status = mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andReturn()
                .getResponse()
                .getStatus();
        Assertions.assertEquals(200,status);
    }

    @Test
    @DisplayName("Test view claims page gets 200")
    void testViewClaimsPage200() throws Exception {
        int status = mockMvc.perform(MockMvcRequestBuilders.get("/viewClaimsData?"))
                .andReturn()
                .getResponse()
                .getStatus();
        Assertions.assertEquals(200,status);
    }

    @Test
    @DisplayName("Test that login status code is 200")
    void testThatLoginStatusCodeIs200() throws Exception {
        this.mockMvc.perform(get("/login")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test that register status code is 200")
    void testThatRegisterStatusCodeIs200() throws Exception {
        this.mockMvc.perform(get("/register")).andDo(print())
                .andExpect(status().isOk());
    }
}
