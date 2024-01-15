package com.sparta.insuranceclaim;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.ClaimRepository;
import com.sparta.insuranceclaim.repository.CustomerDetailRepository;
import com.sparta.insuranceclaim.repository.UserRepository;
import com.sparta.insuranceclaim.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class InsuranceclaimApplicationTests {
	@Mock
	private static ClaimRepository claimRepository;
	@Mock
	private static CustomerDetailRepository customerDetailRepository;
	@Mock
	private static UserRepository userRepository;
	@Mock
	private static ClaimService claimService;
	@Mock
	private static AdminDataService adminDataService;
	@Mock
	private static AuthenticationService authenticationService;
	@Mock
	private static RegisterService registerService;
	@Mock
	private static AdminViewNewClaimsService adminViewNewClaimsService;
	@Mock
	private static UserClaimStatusService userClaimStatusService;

	@InjectMocks
	private ServiceLayer serviceLayer;

	@BeforeEach
	void setUp() {
		serviceLayer = new ServiceLayer(claimRepository, customerDetailRepository, userRepository);
	}

	private static User createTestUser() {
		User dummyUser = new User();
		dummyUser.setUsername("JohnDoe");
		dummyUser.setId(1);
		return dummyUser;
	}
	private static Claim createTestClaim() {
		User dummyUser = createTestUser();
		Claim dummyClaim = new Claim();
		dummyClaim.setClaimStatus("Approved");
		dummyClaim.setId(1);
		dummyClaim.setCarRegistration("1234");
		dummyClaim.setFault("broken car");
		dummyClaim.setUser(dummyUser);
		dummyClaim.setEmail("johndoe@mail.com");
		return dummyClaim;
	}

	@Test
	public void testGetClaimById(){
		Claim dummyClaim = createTestClaim();
		Mockito.when(claimRepository.findById(1)).thenReturn(Optional.of(dummyClaim));
		Claim testClaimToRetrieve = serviceLayer.getClaimByID(1).get();
		assertEquals(dummyClaim, testClaimToRetrieve);
	}

	//@Test
	//public void testGetClaimByIdFail(){
	//	Claim dummyClaim = createTestClaim();
	//	Mockito.when(claimRepository.findById(1)).thenReturn(Optional.of(dummyClaim));
	//	Claim testClaimToRetrieve = claimService.findClaimById(1).get();
	//	assertEquals(dummyClaim, testClaimToRetrieve);
	//}

	@Test
	public void testGetUserById() {
		User dummyUser = createTestUser();
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(dummyUser));
		User testUserToRetrieve = serviceLayer.getUserByID(1).get();
		assertEquals(dummyUser, testUserToRetrieve);
	}

	@Test
	public void testGetClaimsByUser(){
		User dummyUser = createTestUser();
		ArrayList<Claim> claims = new ArrayList<Claim>();
		Mockito.when(claimRepository.findByUser(dummyUser)).thenReturn(claims);
		List<Claim> testClaimList = serviceLayer.getAllClaimsWithUser(dummyUser);
		assertEquals(claims, testClaimList);
	}

	@Test
	public void testGetAllClaims() {
		assertEquals(claimRepository.findAll(), new ArrayList<Claim>());
	}


}
