package com.sparta.insuranceclaim.service;

import com.sparta.insuranceclaim.model.CustomerDetail;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.CustomerDetailRepository;
import com.sparta.insuranceclaim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsurancePremiumService {

    private double baseAmount=500;



    @Autowired
    private CustomerDetailRepository customerDetailRepository;

    @Autowired
    private UserRepository userRepository;

    public double getInsurancePremiumAmount(Integer userId){
        //getting the customer details
        CustomerDetail customerDetail=getCustomerDetails(userId);
        // for age
        ageFactor(customerDetail.getAge());
        //



        return 0.0;
    }

    public  CustomerDetail getCustomerDetails(Integer userId){
        return userRepository.findById(userId).get().getCustomerDetails();
    }

    public void ageFactor(int age){
        if(age<25 || age>50){
            baseAmount=baseAmount+(baseAmount*0.15);
        }
    }

    public void drivingYearsOfExperienceFactor(int drivingYearsOfExperience){

       if(drivingYearsOfExperience<=10){
           //System.out.println(baseAmount+baseAmount*((10-drivingYearsOfExperience)/100));
           baseAmount=baseAmount+baseAmount*((10.0-drivingYearsOfExperience)/100);
           //return;
       }
       else if(drivingYearsOfExperience>10 && drivingYearsOfExperience<20){

            baseAmount=baseAmount-baseAmount*((drivingYearsOfExperience%10)*0.5/100);
       }
       else if(drivingYearsOfExperience>=20){
           baseAmount=baseAmount-(baseAmount*0.05);
       }

    }

    public double getBaseAmount() {
        return baseAmount;
    }
}
