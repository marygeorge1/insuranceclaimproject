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

                case "gender":
                    if (claim.getGender().equalsIgnoreCase(value)) {
                        result.add(claim);
                    }
                    break;
                case "carvalue":
                    if (Integer.toString(claim.getCarValue()).equals(value)) {
                        result.add(claim);

                    }
                    break;

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
            case "gender":
                Collections.sort(claims, Comparator.comparing(CarAccidentClaim::getGender));
                break;
            case "carvalue":
                Collections.sort(claims, Comparator.comparingInt(CarAccidentClaim::getCarValue));
                break;
            case "datejoined":
                Collections.sort(claims, Comparator.comparing(CarAccidentClaim::getDateJoined));
                break;
            case "name":
                Collections.sort(claims, Comparator.comparing(CarAccidentClaim:: getName));
                break;
            default:
                System.out.println("Invalid attribute");
        }

    }


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


