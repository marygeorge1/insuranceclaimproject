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

	//TESTING CLAIMS SERVICE
	@Test
	public void testGetAllClaimsService() {
		assertEquals(claimService.findAllClaims(), new ArrayList<Claim>());
	}

	@Test
	public void testGetClaimByIdService() {
		Claim dummyClaim = createTestClaim();
		Mockito.when(claimRepository.findById(1)).thenReturn(Optional.of(dummyClaim));
		Claim testClaim = claimService.findClaimById(1).get();
		assertEquals(dummyClaim, testClaim);}


	//TESTING ADMIN DATA SERVICE TESTS
	@Test
	public void testGetCustomerDetailsByClaimId() {
		CustomerDetail dummyCD = createTestCustomerDetail();
		Claim dummyClaim = createTestClaim();
		User dummyUser = dummyClaim.getUser();

		Mockito.when(adminDataService.getCustomerDetailByClaimId(dummyClaim.getId())).thenReturn(dummyCD);
		CustomerDetail testCD = dummyUser.getCustomerDetails();
		System.out.println("Expected: " + dummyCD);
		System.out.println("Actual: " + testCD);
		assertEquals(dummyCD, testCD);
	}

	//TESTING ADMIN VIEW NEW CLAIMS SERVICE
	@Test
	@DisplayName("all new claims with the claim status being submitted")
	public void testGetAllNewClaims() {
		Claim dummyClaim = createTestClaim();
		ArrayList<Claim> claims = new ArrayList<Claim>();
		Mockito.when(claimRepository.findAllByClaimStatus("submitted")).thenReturn(claims);
		List<Claim> testClaims = adminViewNewClaimsService.getAllNewClaims();
		assertEquals(testClaims,claims);
	}

	//TESTING AUTHENTICATION SERVICE
	//@Test
	//@DisplayName("test loading user by username")
	//public void testLoadUserByUsername() {
	//	User dummyUser = createTestUser();
//
	//	Mockito.when(userRepository.findByUsername("JohnDoe")).thenReturn(Optional.of(dummyUser));
	//
	//}

	//TESTING REGISTER SERVICE
	@Test
	public void testCreateNewUser() {
		User newUser = new User("JaneSmith","password","ROLE_USER");
		Mockito.when(userRepository.save(newUser)).thenReturn(newUser);

		UserDTO newUserDTO = new UserDTO();
		newUserDTO.setUsername("JaneSmith");
		newUserDTO.setPassword("password");
		User createdUser = registerService.createNewUser(newUserDTO);

		System.out.println("Expected: " + newUser);
		System.out.println("Actual: " + createdUser);

		assertEquals(newUser, createdUser);
	}

	//TESTING USER CLAIM STATUS SERVICE
	@Test
	public void testGetClaimByIdUserClaimStatusService() {
		Claim dummyClaim = createTestClaim();
		Mockito.when(claimRepository.findById(1)).thenReturn(Optional.of(dummyClaim));
		Claim testClaim = userClaimStatusService.getClaimById(1);
		assertEquals(dummyClaim,testClaim);
	}
}
