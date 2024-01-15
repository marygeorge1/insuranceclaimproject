package com.sparta.insuranceclaim.service;

import com.sparta.insuranceclaim.model.CustomerDetail;
import com.sparta.insuranceclaim.model.User;
import com.sparta.insuranceclaim.repository.CustomerDetailRepository;
import com.sparta.insuranceclaim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsurancePremiumService {


    @Autowired
    private CustomerDetailRepository customerDetailRepository;

    @Autowired
    private UserRepository userRepository;

    public double getInsurancePremiumAmount(Integer userId){
         double baseAmount=500;
        //getting the customer details
        CustomerDetail customerDetail=getCustomerDetails(userId);
        // for age
        double ageFactorAmount=ageFactor(baseAmount,customerDetail.getAge());
        //



        return 0.0;
    }

    public  CustomerDetail getCustomerDetails(Integer userId){
        return userRepository.findById(userId).get().getCustomerDetails();
    }

    public double ageFactor(double baseAmount,int age){
        double ageFactor=0.0;
        if(age<25 || age>50){
            ageFactor=baseAmount*0.15;
        }
        return ageFactor;
    }

    public double drivingYearsOfExperienceFactor(double baseAmount,int drivingYearsOfExperience){

        double drivingYearsOfExperienceFactor=0.0;
       if(drivingYearsOfExperience<=10){
           //System.out.println(baseAmount+baseAmount*((10-drivingYearsOfExperience)/100));
           //baseAmount=baseAmount+baseAmount*((10.0-drivingYearsOfExperience)/100);
           drivingYearsOfExperienceFactor=baseAmount*((10.0-drivingYearsOfExperience)/100);

       }
       else if(drivingYearsOfExperience>10 && drivingYearsOfExperience<20){

            //baseAmount=baseAmount-baseAmount*((drivingYearsOfExperience%10)*0.5/100);
           drivingYearsOfExperienceFactor=baseAmount*((drivingYearsOfExperience%10)*0.5/100);
       }
       else if(drivingYearsOfExperience>=20){
           //baseAmount=baseAmount-(baseAmount*0.05);
           drivingYearsOfExperienceFactor=(baseAmount*0.05);
       }
        return drivingYearsOfExperienceFactor;
    }


}
