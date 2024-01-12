package com.sparta.insuranceclaim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

 public class AdminDataVisualisation {
    private String name;
    private int age;
    private int numberOfClaims;
    private String gender;
    private String carMake;
    private String carModel;

    // Constructor, getters, and setters omitted for brevity

    // toString method for easy printing
    @Override
    public String toString() {
        return "CarAccidentClaim{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", numberOfClaims=" + numberOfClaims +
                ", gender='" + gender + '\'' +
                ", carMake='" + carMake + '\'' +
                ", carModel='" + carModel + '\'' +
                '}';
    }
}

class CarAccidentClaimManager {
    private List<CarAccidentClaim> claims;

    public CarAccidentClaimManager() {
        this.claims = new ArrayList<>();
    }

    // Method to add a claim to the list
    public void addClaim(CarAccidentClaim claim) {
        claims.add(claim);
    }

    // Method to search for claims based on a specific attribute
    public List<CarAccidentClaim> searchClaims(String attribute, String value) {
        List<CarAccidentClaim> result = new ArrayList<>();

        for (CarAccidentClaim claim : claims) {
            switch (attribute.toLowerCase()) {
                case "name":
                    if (claim.getName().equalsIgnoreCase(value)) {
                        result.add(claim);
                    }
                    break;
                case "age":
                    if (Integer.toString(claim.getAge()).equals(value)) {
                        result.add(claim);
                    }
                    break;
                // Add cases for other attributes (numberOfClaims, gender, carMake, carModel) as needed
                // ...

                default:
                    System.out.println("Invalid attribute");
            }
        }

        return result;
    }


    public void sortClaims(String attribute) {
        switch (attribute.toLowerCase()) {
            case "age":
                Collections.sort(claims, Comparator.comparingInt(CarAccidentClaim::getAge));
                break;
            case "numberofclaims":
                Collections.sort(claims, Comparator.comparingInt(CarAccidentClaim::getNumberOfClaims));
                break;
            case "gender":
                Collections.sort(claims, Comparator.comparing(CarAccidentClaim::getGender));
                break;
            case "carmake":
                Collections.sort(claims, Comparator.comparing(CarAccidentClaim::getCarMake));
                break;
            default:
                System.out.println("Invalid attribute");
        }
        //filter by date
    }

    // Method to print all claims
    public void printAllClaims() {
        for (CarAccidentClaim claim : claims) {
            System.out.println(claim);
        }
    }


    public void CarAccidentClaimApp(){
        CarAccidentClaimManager claimManager = new CarAccidentClaimManager();

        // Searching and printing claims with age = 35
        System.out.println("Claims with age = 35:");
        List<CarAccidentClaim> age35Claims = claimManager.searchClaims("age", "35");
        for (CarAccidentClaim claim : age35Claims) {
            System.out.println(claim);
        }

        // Sorting and printing claims by car make
        System.out.println("\nClaims sorted by car make:");
        claimManager.sortClaims("carmake");
        claimManager.printAllClaims();

    }
}