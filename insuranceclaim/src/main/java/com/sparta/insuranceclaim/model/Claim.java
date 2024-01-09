package com.sparta.insuranceclaim.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

@Entity
@Table(name = "claim")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "reference_id", length = 50)
    private String referenceId;

    @Size(max = 250)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 250)
    private String firstName;

    @Size(max = 250)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 250)
    private String lastName;

    @Size(max = 250)
    @NotNull
    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Size(max = 50)
    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Size(max = 50)
    @NotNull
    @Column(name = "car_registration", nullable = false, length = 50)
    private String carRegistration;

    @Column(name = "date_of_incident")
    private Instant dateOfIncident;

    @Column(name = "previous_claims")
    private Integer previousClaims;

    @Column(name = "injuries")
    private Boolean injuries;

    @Size(max = 50)
    @Column(name = "police_report_reference", length = 50)
    private String policeReportReference;

    @Size(max = 250)
    @Column(name = "fault", length = 250)
    private String fault;

    @Size(max = 500)
    @Column(name = "other_parties_details", length = 500)
    private String otherPartiesDetails;

    @Size(max = 500)
    @Column(name = "incident_description", length = 500)
    private String incidentDescription;

    @Size(max = 500)
    @Column(name = "image_link", length = 500)
    private String imageLink;

    @Column(name = "date_of_submission")
    private Instant dateOfSubmission;

    @Size(max = 50)
    @Column(name = "claim_status", length = 50)
    private String claimStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarRegistration() {
        return carRegistration;
    }

    public void setCarRegistration(String carRegistration) {
        this.carRegistration = carRegistration;
    }

    public Instant getDateOfIncident() {
        return dateOfIncident;
    }

    public void setDateOfIncident(Instant dateOfIncident) {
        this.dateOfIncident = dateOfIncident;
    }

    public Integer getPreviousClaims() {
        return previousClaims;
    }

    public void setPreviousClaims(Integer previousClaims) {
        this.previousClaims = previousClaims;
    }

    public Boolean getInjuries() {
        return injuries;
    }

    public void setInjuries(Boolean injuries) {
        this.injuries = injuries;
    }

    public String getPoliceReportReference() {
        return policeReportReference;
    }

    public void setPoliceReportReference(String policeReportReference) {
        this.policeReportReference = policeReportReference;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public String getOtherPartiesDetails() {
        return otherPartiesDetails;
    }

    public void setOtherPartiesDetails(String otherPartiesDetails) {
        this.otherPartiesDetails = otherPartiesDetails;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Instant getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void setDateOfSubmission(Instant dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

}