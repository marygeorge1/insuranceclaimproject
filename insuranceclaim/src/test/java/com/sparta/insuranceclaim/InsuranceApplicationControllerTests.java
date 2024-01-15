package com.sparta.insuranceclaim;

import com.sparta.insuranceclaim.controller.ClaimSubmissionController;
import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.repository.CustomerDetailRepository;
import com.sparta.insuranceclaim.repository.UserRepository;
import com.sparta.insuranceclaim.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;

@WebMvcTest
public class InsuranceApplicationControllerTests {
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
    @MockBean
    private InsurancePremiumService insurancePremiumService;

    @InjectMocks
    private ClaimSubmissionController claimSubmissionController;

    //Admin View New Claims Controller
    @Test
    @WithMockUser
    public void testShowClaimDetailsAdmin() throws Exception {
        // Prepare a dummy claim for testing
        Claim dummyClaim = new Claim();
        dummyClaim.setId(1); // Set the claim ID as needed

        // Mock the behavior of userClaimStatusService.getClaimById
        Mockito.when(userClaimStatusService.getClaimById(1)).thenReturn(dummyClaim);

        // Perform a GET request to your endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/view/claims/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("claim"))
                .andExpect(MockMvcResultMatchers.view().name("admin-view-claim-details"));
    }

    @Test
    @WithMockUser
    public void testApproveClaim() throws Exception {
        // Perform a GET request to your endpoint with a claimId
        mockMvc.perform(MockMvcRequestBuilders.get("/approve/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin-new-claims"));

        // Verify that the updateClaimStatus method was called with the correct arguments
        Mockito.verify(adminViewNewClaimsService, times(1)).updateClaimStatus("approved", 1);
    }

    @Test
    @WithMockUser
    public void testDenyClaim() throws Exception {
        // Perform a GET request to your endpoint with a claimId
        mockMvc.perform(MockMvcRequestBuilders.get("/deny/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin-new-claims"));

        // Verify that the updateClaimStatus method was called with the correct arguments
        Mockito.verify(adminViewNewClaimsService, times(1)).updateClaimStatus("denied", 1);
    }

    @Test
    @WithMockUser
    public void testFlagClaim() throws Exception {
        // Perform a GET request to your endpoint with a claimId
        mockMvc.perform(MockMvcRequestBuilders.get("/flag/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin-new-claims"));

        // Verify that the updateClaimStatus method was called with the correct arguments
        Mockito.verify(adminViewNewClaimsService, times(1)).updateClaimStatus("flagged", 1);
    }

    //Claim Submission Controller
    @Test
    @WithMockUser
    public void testGetCreateForm() throws Exception {
        // Perform a GET request to your endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/claim/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("claim"))
                .andExpect(MockMvcResultMatchers.view().name("claimform"));
    }

    @Test
    @WithMockUser
    public void testCreateClaim() throws Exception {
        //test post of creating claim
    }

    //Login Controller
    @Test
    @WithMockUser
    public void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //.andExpect(MockMvcResultMatchers.view().name("login"))
        //.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("logoutMessage", "incorrectDetails"));
    }

    @Test
    @WithMockUser
    public void testLoginWithLogout() throws Exception {
        // Perform a GET request to your endpoint with logout parameter
        mockMvc.perform(MockMvcRequestBuilders.get("/login?logout=true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attribute("logoutMessage", true));
    }

    @Test
    @WithMockUser
    public void testLoginWithFailedLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login?error=true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attribute("incorrectDetails", true));
    }

    //Register Controller
    @Test
    @WithMockUser
    public void testSignUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user-registration-form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
    }

    //@Test
    //@WithMockUser
    //public void testSignUpPost() throws Exception {
    //
    //}

    //User Claim Status Controller
    @Test
    @WithMockUser
    public void testShowClaimDetailsUser() throws Exception {
        // Prepare a dummy claim for testing
        Claim dummyClaim = new Claim();
        dummyClaim.setId(1); // Set the claim ID as needed

        // Mock the behavior of userClaimStatusService.getClaimById
        Mockito.when(userClaimStatusService.getClaimById(1)).thenReturn(dummyClaim);

        // Perform a GET request to your endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/claims/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("claim"))
                .andExpect(MockMvcResultMatchers.view().name("user-claim-details"));
    }
}