package com.sparta.insuranceclaim;

import com.sparta.insuranceclaim.dto.UserDTO;
import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.CustomerDetail;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@SpringBootTest
class InsuranceclaimApplicationTests {
	@Mock
	private static ClaimRepository claimRepository;
	@Mock
	private static CustomerDetailRepository customerDetailRepository;
	@Mock
	private static UserRepository userRepository;
	@InjectMocks
	private ClaimService claimService;
	@InjectMocks
	private AdminDataService adminDataService;
	@InjectMocks
	private AuthenticationService authenticationService;
	@InjectMocks
	private RegisterService registerService;
	@InjectMocks
	private AdminViewNewClaimsService adminViewNewClaimsService;
	@InjectMocks
	private UserClaimStatusService userClaimStatusService;

	@InjectMocks
	private ServiceLayer serviceLayer;

	@BeforeEach
	void setUp() {
		serviceLayer = new ServiceLayer(claimRepository, customerDetailRepository, userRepository);
	}

	private static User createTestUser() {
		User dummyUser = new User();
		CustomerDetail dummyCD = createTestCustomerDetail();
		dummyUser.setUsername("JohnDoe");
		dummyUser.setId(1);
		dummyUser.setCustomerDetails(dummyCD);
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
		dummyClaim.setClaimStatus("submitted");
		return dummyClaim;
	}
	private static CustomerDetail createTestCustomerDetail() {
		CustomerDetail dummyCustomerDetail = new CustomerDetail();
		dummyCustomerDetail.setAge(33);
		dummyCustomerDetail.setId(1);
		dummyCustomerDetail.setGender("Male");
		dummyCustomerDetail.setDrivingYoe(2);
		dummyCustomerDetail.setCarValue(2000);
		dummyCustomerDetail.setPreviousAccidents(2);
		return dummyCustomerDetail;
	}

	// TESTING THE TESTS WITH A SERVICE LAYER AND BECAUSE OF ISSUES WITH CLAIM SERVICE GETTING ID NEEDED TO CHECK GET CLAIM BY ID WORKED
	@Test
	public void testGetClaimById(){
		Claim dummyClaim = createTestClaim();
		Mockito.when(claimRepository.findById(1)).thenReturn(Optional.of(dummyClaim));
		Claim testClaimToRetrieve = serviceLayer.getClaimByID(1).get();
		assertEquals(dummyClaim, testClaimToRetrieve);
	}

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

	// TESTING REPOSITORIES
	@Test
	public void testGetAllCustomerDetails() {
		assertEquals(customerDetailRepository.findAll(), new ArrayList<CustomerDetail>());
	}

	@Test
	public void testGetAllUsers() {
		assertEquals(userRepository.findAll(), new ArrayList<User>());
	}

	@Test
	public void testGetAllClaimsRepo() {
		assertEquals(claimRepository.findAll(), new ArrayList<Claim>());
	}

	//TESTING ADMIN VIEW NEW CLAIMS SERVICE
	@Test
	public void testGetAllNewClaims() {
		Claim dummyClaim = createTestClaim();
		ArrayList<Claim> claims = new ArrayList<Claim>();
		Mockito.when(claimRepository.findAllByClaimStatus("submitted")).thenReturn(claims);
		List<Claim> testClaims = adminViewNewClaimsService.getAllNewClaims();
		assertEquals(testClaims,claims);
	}

	//TESTING CLAIMS SERVICE
	@Test
	public void testGetAllClaimsService() {
		assertEquals(claimService.findAllClaims(), new ArrayList<Claim>());
	}

	@Test
	public void testGetClaimByIdService() {
		Claim dummyClaim = createTestClaim();
		Mockito.when(claimRepository.findById(1)).thenReturn(Optional.of(dummyClaim));
		Claim testClaim = claimService.findClaimById(dummyClaim.getId()).get();
		Mockito.when(claimRepository.findById(1)).thenReturn(Optional.of(dummyClaim));
		assertEquals(dummyClaim, testClaim);
	}

	@Test
	public void testGetClaimsWithinAYear() {
		Claim claim = new Claim();
		claim.setDateOfSubmission(LocalDate.now());

		List<Claim> previousClaims = new ArrayList<>();
		previousClaims.add(createClaimWithSubmissionDate(LocalDate.now().minusDays(180)));  // within a year
		previousClaims.add(createClaimWithSubmissionDate(LocalDate.now().minusYears(2))); // outside a year
		previousClaims.add(createClaimWithSubmissionDate(LocalDate.now().minusDays(30)));  // within a year

		List<Claim> claimsWithinAYear = claimService.getClaimsWithinAYear(claim, previousClaims);

		assertEquals(2, claimsWithinAYear.size());
	}

	private Claim createClaimWithSubmissionDate(LocalDate date) {
		Claim claim = new Claim();
		claim.setDateOfSubmission(date);
		return claim;
	}


	//TESTING USER CLAIM STATUS SERVICE
	@Test
	public void testGetClaimByIdUserClaimStatusService() {
		Claim dummyClaim = createTestClaim();
		Mockito.when(claimRepository.findById(1)).thenReturn(Optional.of(dummyClaim));
		Claim testClaim = userClaimStatusService.getClaimById(dummyClaim.getId());
		assertEquals(dummyClaim,testClaim);
	}

	@Test
	public void testGetAllClaimsUserClaimStatusService() {
		assertEquals(userClaimStatusService.getAllClaims(),new ArrayList<Claim>());
	}

	@Test
	public void testGetAllClaimByLoggedInUser_ClaimsFound() {
		User loggedInUser = new User();
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();

		Mockito.when(claimRepository.findByUser(loggedInUser)).thenReturn(List.of(claim1, claim2));
		List<Claim> claims = userClaimStatusService.getAllClaimByLoggedInUser(loggedInUser);
		assertEquals(2, claims.size());
	}
	@Test
	public void testGetAllClaimByLoggedInUser_NoClaimsFound() {
		User loggedInUser = new User();
		Mockito.when(claimRepository.findByUser(loggedInUser)).thenReturn(Collections.emptyList());
		List<Claim> claims = userClaimStatusService.getAllClaimByLoggedInUser(loggedInUser);
		assertEquals(0, claims.size());
	}
	@Test
	public void testSaveClaimUserClaimStatusService() {
		Claim dummyClaim = createTestClaim();
		Mockito.when(claimRepository.save(dummyClaim)).thenReturn(dummyClaim);
		userClaimStatusService.saveClaim(dummyClaim);
		Mockito.verify(claimRepository, Mockito.times(1)).save(dummyClaim);
	}

//
//
	////TESTING ADMIN DATA SERVICE TESTS
	//@Test
	//public void testGetCustomerDetailsByClaimId() {
	//	CustomerDetail dummyCD = createTestCustomerDetail();
	//	Claim dummyClaim = createTestClaim();
	//	User dummyUser = dummyClaim.getUser();
//
	//	Mockito.when(adminDataService.getCustomerDetailByClaimId(1)).thenReturn(dummyCD);
	//	CustomerDetail testCD = dummyUser.getCustomerDetails();
	//	Mockito.verify(adminDataService, Mockito.times(1)).getCustomerDetailByClaimId(1);
//
	//	System.out.println("Expected: " + dummyCD);
	//	System.out.println("Actual: " + testCD);
	//	assertEquals(dummyCD, testCD);
	//}

	//TESTING AUTHENTICATION SERVICE
	//@Test
	//@DisplayName("test loading user by username")
	//public void testLoadUserByUsername() {
	//	User dummyUser = createTestUser();
//
	//	Mockito.when(userRepository.findByUsername("JohnDoe")).thenReturn(Optional.of(dummyUser));
	//	User testUser = (User)authenticationService.loadUserByUsername(dummyUser.getUsername());
//
	//	assertEquals(dummyUser, testUser);
	//}

	//TESTING REGISTER SERVICE


}
