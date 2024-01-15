package com.sparta.insuranceclaim.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

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
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;

    @Size(max = 250)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 250)
    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    @Size(max = 250)
    @NotNull
    @Column(name = "email", nullable = false, length = 250)
    @Email(message = "*Email should be in the correct format")
    @NotBlank(message = "*Email cannot be blank")
    private String email;

    @Size(max = 50)
    @Column(name = "phone_number", length = 50)
    @Pattern(regexp = "^$|([0-9]{10,12})", message = "*Only numbers are allowed, and should have 10-12 digits")
    private String phoneNumber;

    @Size(max = 50)
    @NotNull
    @Column(name = "car_registration", nullable = false, length = 50)
    @Pattern(regexp = "^$|(^[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}$)|(^[A-Z][0-9]{1,3}[A-Z]{3}$)|(^[A-Z]{3}[0-9]{1,3}[A-Z]$)|(^[0-9]{1,4}[A-Z]{1,2}$)|(^[0-9]{1,3}[A-Z]{1,3}$)|(^[A-Z]{1,2}[0-9]{1,4}$)|(^[A-Z]{1,3}[0-9]{1,3}$)|(^[A-Z]{1,3}[0-9]{1,4}$)|(^[0-9]{3}[DX]{1}[0-9]{3}$)"
            ,message = "*Invalid Registration number")
    @NotBlank(message = "*Car registration number cannot be blank")
    private String carRegistration;

    @Column(name = "date_of_incident")
    @PastOrPresent(message = "*Cannot be a future date")
    @NotNull(message = "*Provide the date of incident")
    private LocalDate dateOfIncident;

    @Column(name = "previous_claims")
    @Min(0)
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
    @Pattern(regexp = "^$|([(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*))",message = "Enter a valid URL")
    private String imageLink;

    @Column(name = "date_of_submission")
    private LocalDate dateOfSubmission;

    @Size(max = 50)
    @Column(name = "claim_status", length = 50)
    private String claimStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_agent_id")
    private User assignedAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "display_notification")
    private Boolean displayNotification;

    @Column(name = "fraud_flag")
    private Boolean fraudFlag;

    @Size(max = 500)
    @Column(name = "fraud_flag_information", length = 500)
    private String fraudFlagInformation;

    public String getFraudFlagInformation() {
        return fraudFlagInformation;
    }

    public void setFraudFlagInformation(String fraudFlagInformation) {
        this.fraudFlagInformation = fraudFlagInformation;
    }

    public Boolean getFraudFlag() {
        return fraudFlag;
    }

    public void setFraudFlag(Boolean fraudFlag) {
        this.fraudFlag = fraudFlag;
    }

    public Boolean getDisplayNotification() {
        return displayNotification;
    }

    public void setDisplayNotification(Boolean displayNotification) {
        this.displayNotification = displayNotification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAssignedAgent() {
        return assignedAgent;
    }

    public void setAssignedAgent(User assignedAgent) {
        this.assignedAgent = assignedAgent;
    }

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

    public LocalDate getDateOfIncident() {
        return dateOfIncident;
    }

    public void setDateOfIncident(LocalDate dateOfIncident) {
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

    public LocalDate getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void setDateOfSubmission(LocalDate dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "id=" + id +
                ", referenceId='" + referenceId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", carRegistration='" + carRegistration + '\'' +
                ", dateOfIncident=" + dateOfIncident +
                ", previousClaims=" + previousClaims +
                ", injuries=" + injuries +
                ", policeReportReference='" + policeReportReference + '\'' +
                ", fault='" + fault + '\'' +
                ", otherPartiesDetails='" + otherPartiesDetails + '\'' +
                ", incidentDescription='" + incidentDescription + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", dateOfSubmission=" + dateOfSubmission +
                ", claimStatus='" + claimStatus + '\'' +
                ", assignedAgent=" + assignedAgent +
                ", user=" + user +
                '}';
    }
}