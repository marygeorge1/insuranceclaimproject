package com.sparta.insuranceclaim;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.repository.CustomerDetailRepository;
import com.sparta.insuranceclaim.repository.UserRepository;
import com.sparta.insuranceclaim.service.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class InsuranceApplicationMVCTests {
    @Autowired
    MockMvc mockMvc;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

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
    @DisplayName("Test login page gets 200")
    void testHomePage200() throws Exception {
        int status = mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertEquals(200,status);
    }

    @Test
    @WithMockUser
    @DisplayName("Test homepage for user page gets 200")
    void testHomePageUser200() throws Exception {
        int status = mockMvc.perform(MockMvcRequestBuilders.get("/homepage/user"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertEquals(200,status);
    }

    @Test
    @WithMockUser
    @DisplayName("Test homepage for admin page gets 200")
    void testHomePageAdmin200() throws Exception {
        int status = mockMvc.perform(MockMvcRequestBuilders.get("/homepage/admin"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertEquals(200,status);
    }

    @Test
    @WithMockUser
    @DisplayName("Test view claims page gets 200")
    void testViewClaimsPage200() throws Exception {
        int status = mockMvc.perform(MockMvcRequestBuilders.get("/viewClaimsData?"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertEquals(200,status);
    }

    @Test
    @WithMockUser
    @DisplayName("Test create claims page gets 200")
    void testCreateClaimsPage200() throws Exception {
        int status = mockMvc.perform(MockMvcRequestBuilders.get("/claim/create"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertEquals(200,status);
    }

    @Test
    @DisplayName("Test that login status code is 200")
    void testThatLoginStatusCodeIs200() throws Exception {
        this.mockMvc.perform(get("/login")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("Test that register status code is 200")
    void testThatRegisterStatusCodeIs200() throws Exception {
        this.mockMvc.perform(get("/register")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("test that new claim form created code 201")
    void testNewClaimFormCreated201() throws Exception {
        this.mockMvc.perform(get("/claim/create")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testNewClaimView200() throws Exception {
        this.mockMvc.perform(get("/new-claims?")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser // Use a mock user for an authenticated user
    public void testAuthenticatedUserAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/homepage/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("test succesful login user")
    public void testSuccessfulLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/perform_login")
                        .param("username","user")
                        .param("password","password"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFailedLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/perform_login")
                        .param("user", "invalidUsername")
                        .param("password", "invalidPassword"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}
