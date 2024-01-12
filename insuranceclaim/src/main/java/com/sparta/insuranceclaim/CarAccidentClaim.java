package com.sparta.insuranceclaim;

import com.sparta.insuranceclaim.model.CustomerDetail;
import com.sparta.insuranceclaim.model.Claim;

import java.time.LocalDate;

public class CarAccidentClaim {
    public String getName() {
        return new Claim().getFirstName();
    }

    public int getAge() {
       return new CustomerDetail().getAge();
    }

    public int getCarValue() {
        return new CustomerDetail().getCarValue();
    }

    public String getGender(){
        return new CustomerDetail().getGender();
    }
    public LocalDate getDateJoined(){
    return new CustomerDetail().getDateJoining();
    }




}
