package BTO_System;

import java.util.Date;

/**
 * Represents a receipt issued when an applicant successfully books a flat.
 * The receipt contains information about the applicant, the flat type, and the project.
 */
public class Receipt {
    /** The NRIC (National Registration Identity Card) number of the applicant */
    private String nric;
    
    /** The age of the applicant */
    private int age;
    
    /** The marital status of the applicant */
    private MaritalStatus maritalStatus;
    
    /** The type of flat booked */
    private FlatType flatType;
    
    /** The name of the project for which the flat is booked */
    private String projectName;
    
    /** The date when the receipt was issued */
    private Date issueDate;

    /**
     * Constructs a new Receipt with the specified details.
     *
     * @param nric The NRIC number of the applicant
     * @param age The age of the applicant
     * @param maritalStatus The marital status of the applicant
     * @param flatType The type of flat booked
     * @param projectName The name of the project
     * @param issueDate The date when the receipt is issued
     */
    public Receipt(String nric, int age, MaritalStatus maritalStatus,
                   FlatType flatType, String projectName, Date issueDate) {
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.flatType = flatType;
        this.projectName = projectName;
        this.issueDate = issueDate;
    }

    /**
     * Displays the details of this receipt to the console.
     * Includes the applicant's information, flat type, project, and issue date.
     */
    public void displayReceipt() {
        System.out.println("----- Receipt -----");
        System.out.println("NRIC: " + nric);
        System.out.println("Age: " + age);
        System.out.println("Marital Status: " + maritalStatus);
        System.out.println("Flat Type: " + flatType);
        System.out.println("Project: " + projectName);
        System.out.println("Issue Date: " + issueDate);
        System.out.println("-------------------");
    }
}