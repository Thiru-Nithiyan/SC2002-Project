package BTO_System;
import java.util.List;

/**
 * Represents the application status for BTO applications.
 * Tracks the lifecycle of an application from submission to completion.
 */
enum ApplicationStatus {
    /** Application has been submitted but not yet reviewed */
    PENDING,
    
    /** Application has been reviewed and approved */
    SUCCESSFUL,
    
    /** Application has been rejected or withdrawn */
    UNSUCCESSFUL,
    
    /** Application has been successful and a flat has been booked */
    BOOKED
}

/**
 * Represents the marital status of a user.
 * This affects eligibility for certain flat types and projects.
 */
enum MaritalStatus {
    /** User is not married */
    SINGLE,
    
    /** User is legally married */
    MARRIED
}

/**
 * Represents the different types of flats available in BTO projects.
 * Each type has different eligibility requirements and pricing.
 */
enum FlatType {
    /** Two-room flat, typically for singles aged 35+ and smaller families */
    TWOROOM,
    
    /** Three-room flat, typically for families */
    THREEROOM
}

/**
 * Base class for all users in the BTO system.
 * Provides common functionality for authentication, profile management,
 * and basic interactions with projects and enquiries.
 */
class User {
    /** The National Registration Identity Card number of the user */
    private String nric;
    
    /** The password for the user's account */
    private String password;
    
    /** The age of the user */
    private int age;
    
    /** The marital status of the user */
    private MaritalStatus maritalStatus;

    /**
     * Constructs a new User with the specified information.
     *
     * @param nric The NRIC number of the user
     * @param password The password for the user's account
     * @param age The age of the user
     * @param maritalStatus The marital status of the user
     */
    public User(String nric, String password, int age, MaritalStatus maritalStatus) {
        this.nric = nric;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    /**
     * Authenticates a user by checking the provided credentials.
     *
     * @param nric The NRIC number to check
     * @param password The password to check
     * @return true if the credentials match, false otherwise
     */
    public boolean login(String nric, String password) {
        return this.nric.equals(nric) && this.password.equals(password);
    }

    /**
     * Changes the user's password to a new value.
     *
     * @param newPassword The new password to set
     */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * Displays all visible projects to the console.
     *
     * @param projects The list of projects to view
     */
    public void viewProjects(List<Project> projects) {
        for (Project project : projects) {
            if (project.isVisible()) {
                project.displayProjectDetails();
            }
        }
    }

    /**
     * Submits a new enquiry to the system.
     *
     * @param enquiry The enquiry to submit
     * @param enquiries The list of enquiries to add to
     */
    public void submitEnquiry(Enquiry enquiry, List<Enquiry> enquiries) {
        enquiries.add(enquiry);
    }

    /**
     * Edits the content of an existing enquiry.
     *
     * @param enquiryID The ID of the enquiry to edit
     * @param newContent The new content for the enquiry
     * @param enquiries The list of enquiries to search in
     */
    public void editEnquiry(int enquiryID, String newContent, List<Enquiry> enquiries) {
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getEnquiryID() == enquiryID) {
                enquiry.setContent(newContent);
                break;
            }
        }
    }

    /**
     * Deletes an existing enquiry from the system.
     *
     * @param enquiryID The ID of the enquiry to delete
     * @param enquiries The list of enquiries to remove from
     */
    public void deleteEnquiry(int enquiryID, List<Enquiry> enquiries) {
        enquiries.removeIf(enquiry -> enquiry.getEnquiryID() == enquiryID);
    }

    /**
     * Displays all enquiries in the provided list to the console.
     *
     * @param enquiries The list of enquiries to view
     */
    public void viewEnquiries(List<Enquiry> enquiries) {
        for (Enquiry enquiry : enquiries) {
            enquiry.displayEnquiry();
        }
    }

    /**
     * Gets the NRIC number of this user.
     *
     * @return The NRIC number
     */
    public String getNric() {
        return nric;
    }

    /**
     * Gets the age of this user.
     *
     * @return The age
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the marital status of this user.
     *
     * @return The marital status
     */
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }
}