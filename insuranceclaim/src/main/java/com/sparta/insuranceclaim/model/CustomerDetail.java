package com.sparta.insuranceclaim.model;

import com.sparta.insuranceclaim.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "customer_details")
public class CustomerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_details_id", nullable = false)
    private Integer id;

    @Column(name = "policy_id")
    private Integer policyId;

    @Column(name = "age")
    private Integer age;

    @Size(max = 50)
    @Column(name = "gender", length = 50)
    private String gender;

    @Column(name = "driving_yoe")
    private Integer drivingYoe;

    @Column(name = "annual_income")
    private Integer annualIncome;

    @Column(name = "vehicle_ownership")
    private Boolean vehicleOwnership;

    @Column(name = "vehicle_year")
    private Integer vehicleYear;

    @Column(name = "annual_mileage")
    private Integer annualMileage;

    @Column(name = "speeding_violations")
    private Integer speedingViolations;

    @Column(name = "duis")
    private Integer duis;

    @Column(name = "previous_accidents")
    private Integer previousAccidents;

    @Column(name = "previous_insurances")
    private Integer previousInsurances;

    @Column(name = "car_value")
    private Integer carValue;

    @Column(name = "car_mileage")
    private Integer carMileage;

    @Column(name = "date_joining")
    private LocalDate dateJoining;

    @OneToMany(mappedBy = "customerDetails")
    private Set<User> users = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getDrivingYoe() {
        return drivingYoe;
    }

    public void setDrivingYoe(Integer drivingYoe) {
        this.drivingYoe = drivingYoe;
    }

    public Integer getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Integer annualIncome) {
        this.annualIncome = annualIncome;
    }

    public Boolean getVehicleOwnership() {
        return vehicleOwnership;
    }

    public void setVehicleOwnership(Boolean vehicleOwnership) {
        this.vehicleOwnership = vehicleOwnership;
    }

    public Integer getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(Integer vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public Integer getAnnualMileage() {
        return annualMileage;
    }

    public void setAnnualMileage(Integer annualMileage) {
        this.annualMileage = annualMileage;
    }

    public Integer getSpeedingViolations() {
        return speedingViolations;
    }

    public void setSpeedingViolations(Integer speedingViolations) {
        this.speedingViolations = speedingViolations;
    }

    public Integer getDuis() {
        return duis;
    }

    public void setDuis(Integer duis) {
        this.duis = duis;
    }

    public Integer getPreviousAccidents() {
        return previousAccidents;
    }

    public void setPreviousAccidents(Integer previousAccidents) {
        this.previousAccidents = previousAccidents;
    }

    public Integer getPreviousInsurances() {
        return previousInsurances;
    }

    public void setPreviousInsurances(Integer previousInsurances) {
        this.previousInsurances = previousInsurances;
    }

    public Integer getCarValue() {
        return carValue;
    }

    public void setCarValue(Integer carValue) {
        this.carValue = carValue;
    }

    public Integer getCarMileage() {
        return carMileage;
    }

    public void setCarMileage(Integer carMileage) {
        this.carMileage = carMileage;
    }

    public LocalDate getDateJoining() {
        return dateJoining;
    }

    public void setDateJoining(LocalDate dateJoining) {
        this.dateJoining = dateJoining;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}