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
        insurancePremiumService.ageFactor(20);
        Assertions.assertEquals(575,insurancePremiumService.getBaseAmount());
    }

    @ParameterizedTest
    @CsvSource({"5,525","15,487.5"})
    public void drivingYearsOfExperienceFactorTest(int yearsOExperience,double expectedValue){
        insurancePremiumService.drivingYearsOfExperienceFactor(yearsOExperience);
        Assertions.assertEquals(expectedValue,insurancePremiumService.getBaseAmount());
    }
}
