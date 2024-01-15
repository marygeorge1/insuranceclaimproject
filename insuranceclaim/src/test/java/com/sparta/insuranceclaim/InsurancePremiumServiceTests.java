package com.sparta.insuranceclaim;

import com.sparta.insuranceclaim.model.CustomerDetail;
import com.sparta.insuranceclaim.service.InsurancePremiumService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class InsurancePremiumServiceTests {

    @Autowired
    private InsurancePremiumService insurancePremiumService;

    @Test
    public void ageFactorTest(){
        Assertions.assertEquals(75,insurancePremiumService.ageFactor(500,20));
    }

    @ParameterizedTest
    @CsvSource({"5,25","15,-12.5"})
    public void drivingYearsOfExperienceFactorTest(int yearsOExperience,double expectedValue){
        Assertions.assertEquals(expectedValue,insurancePremiumService.drivingYearsOfExperienceFactor(500,yearsOExperience));
    }
    @ParameterizedTest
    @CsvSource({"2018,10","1998,50"})
    public void carAgeExperienceFactorTest(int vehicleYear,double expectedValue){
        Assertions.assertEquals(expectedValue,insurancePremiumService.carAgeFactor(500,vehicleYear));
    }
    @ParameterizedTest
    @CsvSource({"1,2.5","3,10","6,25"})
    public void speedingFactorTest(int speedingViolations,double expectedValue){
        Assertions.assertEquals(expectedValue,insurancePremiumService.speedingFactor(500,speedingViolations));
    }
    @ParameterizedTest
    @CsvSource({"1,100","2,200"})
    public void duiFactorTest(int duiAmount,double expectedValue){
        Assertions.assertEquals(expectedValue,insurancePremiumService.duiFactor(500,duiAmount));
    }
    @ParameterizedTest
    @CsvSource({"1,5","2,10","7,35"})
    public void accidentFactorTest(int numberOfAccidents,double expectedValue){
        Assertions.assertEquals(expectedValue,insurancePremiumService.accidentFactor(500,numberOfAccidents));
    }
    @ParameterizedTest
    @CsvSource({"2014-01-02,50","2018-02-02,25","2008-02-02,50"})
    public void loyaltyFactorTest(LocalDate dateOfJoining, double expectedValue){
        Assertions.assertEquals(expectedValue,insurancePremiumService.loyaltyFactor(500,dateOfJoining));
    }
    @Test
    public void totalPremiumTest(){
        CustomerDetail testCustomer = new CustomerDetail();
        testCustomer.setAge(25);
        testCustomer.setDrivingYoe(6);
        testCustomer.setCarValue(50000);
        testCustomer.setVehicleYear(2017);
        testCustomer.setSpeedingViolations(2);
        testCustomer.setDuis(0);
        testCustomer.setPreviousAccidents(3);
        testCustomer.setDateJoining(LocalDate.parse("2015-07-12"));
        Assertions.assertEquals(643.75,insurancePremiumService.getInsurancePremiumAmount(testCustomer));
    }
}
