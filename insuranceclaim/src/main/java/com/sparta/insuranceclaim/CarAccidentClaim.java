package com.sparta.insuranceclaim;

public class CarAccidentClaim {
    public String getName() {
        return name;
    }
    public void setName(){
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static <T> int getNumberOfClaims(T t) {
    }

    public static <U extends Comparable<? super U>, T> U getGender(T t) {
    }

    public static <U extends Comparable<? super U>, T> U getCarMake(T t) {
    }
}
