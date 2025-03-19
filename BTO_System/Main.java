package BTO_System;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Manager creation
        HDBManager manager = new HDBManager("M1234567A", "password", 45, MaritalStatus.MARRIED);

        // Flat type and unit availability setup
        List<FlatType> flatTypes = new ArrayList<>();
        flatTypes.add(FlatType.TWOROOM);
        flatTypes.add(FlatType.THREEROOM);

        Map<FlatType, Integer> unitsAvailable = new HashMap<>();
        unitsAvailable.put(FlatType.TWOROOM, 50);
        unitsAvailable.put(FlatType.THREEROOM, 30);

        // Manager creates Project
        Project project = new Project("Tampines GreenWeave", "Tampines", flatTypes, unitsAvailable,
                new Date(), new Date(System.currentTimeMillis() + 1000000000), manager);

        System.out.println("\n--- Project Created ---");
        project.displayProjectDetails();

        // Manager toggles visibility
        System.out.println("\n--- Manager toggles project visibility ---");
        manager.toggleVisibility(project);
        System.out.println("Visibility now: " + (project.isVisible() ? "Visible" : "Hidden"));

        // Applicant creation
        Applicant applicant = new Applicant("S1234567B", "password", 36, MaritalStatus.SINGLE);
        applicant.applyForProject(project, FlatType.TWOROOM);
        project.addApplicant(applicant); // Add to project list

        // Applicant withdraws
        System.out.println("\n--- Applicant withdraws application ---");
        applicant.withdrawApplication();
        applicant.viewApplicationStatus();

        // Applicant re-applies
        applicant.applyForProject(project, FlatType.TWOROOM);

        // Officer creation
        HDBOfficer officer = new HDBOfficer("O9876543X", "password", 30, MaritalStatus.SINGLE);
        officer.registerForProject(project);

        // Manager approves officer registration
        manager.approveOfficerRegistration(officer, project);

        // Officer views project details
        System.out.println("\n--- Officer views project details ---");
        officer.viewProjectDetails(project);

        // Officer updates flat availability
        officer.updateFlatAvailability(project, FlatType.TWOROOM, 45);

        // Enquiry testing
        System.out.println("\n--- Applicant submits enquiry ---");
        Enquiry enquiry = new Enquiry("When is booking date?", applicant, project);
        applicant.submitEnquiry(enquiry, project.getEnquiries());
        applicant.viewEnquiries(project.getEnquiries());

        System.out.println("\n--- Applicant edits enquiry ---");
        applicant.editEnquiry(enquiry.getEnquiryID(), "Updated: What is booking procedure?", project.getEnquiries());
        applicant.viewEnquiries(project.getEnquiries());

        // Officer replies enquiry
        officer.replyEnquiry(enquiry, "Booking starts next month.");
        enquiry.displayEnquiry();

        // Applicant deletes enquiry
        System.out.println("\n--- Applicant deletes enquiry ---");
        applicant.deleteEnquiry(enquiry.getEnquiryID(), project.getEnquiries());
        applicant.viewEnquiries(project.getEnquiries());

        // Manager approves application
        Application app = applicant.getApplication();
        manager.approveApplication(app);

        // Officer books flat
        officer.bookFlat(app);

        // Officer generates receipt
        Receipt receipt = officer.generateReceipt(app);
        receipt.displayReceipt();

        // Applicant views application status
        System.out.println("\n--- Applicant Views Application Status ---");
        applicant.viewApplicationStatus();
    }
}
