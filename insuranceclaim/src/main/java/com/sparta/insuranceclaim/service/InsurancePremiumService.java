package com.sparta.insuranceclaim.service;

import com.sparta.insuranceclaim.model.CustomerDetail;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.CustomerDetailRepository;
import com.sparta.insuranceclaim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InsurancePremiumService {


    @Autowired
    private CustomerDetailRepository customerDetailRepository;

    public String getInsurancePremiumAmount(Integer customerDetailId){
         double baseAmount=500;
        //getting the customer details
        CustomerDetail customerDetail=getCustomerDetails(customerDetailId);
        // for age
        baseAmount += customerDetail.getCarValue()*0.0025;
        double ageFactorAmount=ageFactor(baseAmount,customerDetail.getAge());
        double drivingYearsOfExperienceFactorAmount=drivingYearsOfExperienceFactor(baseAmount,customerDetail.getDrivingYoe());
        double carAgeFactorAmount=carAgeFactor(baseAmount,customerDetail.getVehicleYear());
        double speedingFactorAmount=speedingFactor(baseAmount,customerDetail.getSpeedingViolations());
        double duiFactorAmount=duiFactor(baseAmount,customerDetail.getDuis());
        double accidentFactorAmount=accidentFactor(baseAmount,customerDetail.getPreviousAccidents());
        double loyaltyFactorAmount=loyaltyFactor(baseAmount,customerDetail.getDateJoining());

        double predictedPremium = baseAmount
                +ageFactorAmount
                +drivingYearsOfExperienceFactorAmount
                +carAgeFactorAmount
                +speedingFactorAmount
                +duiFactorAmount
                +accidentFactorAmount
                -loyaltyFactorAmount;

        return String.format("%,.2f", predictedPremium);
    }
    public double getInsurancePremiumAmount(CustomerDetail customerDetail){
        double baseAmount=500;

        baseAmount += customerDetail.getCarValue()*0.0025;
        double ageFactorAmount=ageFactor(baseAmount,customerDetail.getAge());
        double drivingYearsOfExperienceFactorAmount=drivingYearsOfExperienceFactor(baseAmount,customerDetail.getDrivingYoe());
        double carAgeFactorAmount=carAgeFactor(baseAmount,customerDetail.getVehicleYear());
        double speedingFactorAmount=speedingFactor(baseAmount,customerDetail.getSpeedingViolations());
        double duiFactorAmount=duiFactor(baseAmount,customerDetail.getDuis());
        double accidentFactorAmount=accidentFactor(baseAmount,customerDetail.getPreviousAccidents());
        double loyaltyFactorAmount=loyaltyFactor(baseAmount,customerDetail.getDateJoining());

      baseAmount  +=ageFactorAmount;
        baseAmount        +=drivingYearsOfExperienceFactorAmount;
        baseAmount        +=carAgeFactorAmount;
        baseAmount        +=speedingFactorAmount;
        baseAmount        +=duiFactorAmount;
        baseAmount        +=accidentFactorAmount;
        baseAmount        -=loyaltyFactorAmount;
        //

        return baseAmount;

    }



    public  CustomerDetail getCustomerDetails(Integer customerDetailId){
        return customerDetailRepository.findById(customerDetailId).get();
    }

    public double ageFactor(double baseAmount,int age){
        double ageFactor=0.0;
        if(age<25 || age>50){
            ageFactor=baseAmount*0.15;
        }
        return ageFactor;
    }

    public double drivingYearsOfExperienceFactor(double baseAmount,int drivingYearsOfExperience){

       if(drivingYearsOfExperience<=10){
           return baseAmount*((10.0-drivingYearsOfExperience)/100);
       }
       if( drivingYearsOfExperience<20){
           return -baseAmount*((drivingYearsOfExperience%10)*0.5/100);
       }
       return(-baseAmount*0.05);
    }
    public double carAgeFactor (double baseAmount,int vehicleYear){
        double carAge =  LocalDate.now().getYear()- vehicleYear;
        if(carAge>20)
            return baseAmount*0.1;
        if(carAge>10)
            return baseAmount*0.05;
        if(carAge>5)
            return baseAmount*0.02;
        return 0;
    }
    public double speedingFactor(double baseAmount, int speedingViolations){
        if(speedingViolations>5)
            return baseAmount*0.05;
        if(speedingViolations>=2)
            return baseAmount*0.02;
        if(speedingViolations==1)
            return baseAmount*0.005;
        return 0;
    }
    public double duiFactor(double baseAmount, int duiAmount){
        if(duiAmount>1)
            return baseAmount*0.4;
        if(duiAmount==1)
            return baseAmount*0.2;
        return 0;
    }
    public double accidentFactor(double baseAmount, int numberOfAccidents){
        return baseAmount*numberOfAccidents*0.01;
    }
public double loyaltyFactor(double baseAmount, LocalDate dateOfJoining)
{
    int yearsOfService = dateOfJoining.until(LocalDate.now()).getYears();
    if(yearsOfService<=10)
        return baseAmount*yearsOfService*0.01;
    return baseAmount*0.1;
}




}
