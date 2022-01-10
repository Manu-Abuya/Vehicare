package com.example.vehicare;

public class VehicleDetails {
    private String car, license, insurance, identification;

    public VehicleDetails(){
    }

    public VehicleDetails(String car, String license, String insurance, String identification){
        this.car = car;
        this.license = license;
        this.insurance = insurance;
        this.identification = identification;
    }

    public String getCar() {
        return car;
    }

    public String getLicense() {
        return license;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getIdentification() {
        return identification;
    }
}
