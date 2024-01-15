package com.sparta.insuranceclaim;

import com.sparta.insuranceclaim.service.InsurancePremiumService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InsurancePremiumServiceTests {

    @Autowired
    private InsurancePremiumService insurancePremiumService;

    @Test
    public void ageFactorTest(){
        ;
        Assertions.assertEquals(75,insurancePremiumService.ageFactor(500,20));
    }

    @ParameterizedTest
    @CsvSource({"5,25","15,12.5"})
    public void drivingYearsOfExperienceFactorTest(int yearsOExperience,double expectedValue){
        ;
        Assertions.assertEquals(expectedValue,insurancePremiumService.drivingYearsOfExperienceFactor(500,yearsOExperience));
    }
}
