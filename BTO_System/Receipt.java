package BTO_System;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;


public class Receipt {
    private String nric;
    private int age;
    private MaritalStatus maritalStatus;
    private FlatType flatType;
    private String projectName;
    private Date issueDate;

    public Receipt(String nric, int age, MaritalStatus maritalStatus,
                   FlatType flatType, String projectName, Date issueDate) {
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.flatType = flatType;
        this.projectName = projectName;
        this.issueDate = issueDate;
    }

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
