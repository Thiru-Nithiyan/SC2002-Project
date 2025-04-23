package BTO_System;

import java.util.*;

/**
 * Represents a Housing Development Board (HDB) Officer who can register for projects
 * and perform administrative tasks such as booking flats and generating receipts.
 * An HDB Officer extends Applicant, meaning they can also apply for BTO projects personally.
 */
public class HDBOfficer extends Applicant {
    /** List of projects this officer is registered to manage */
    private List<Project> registeredProjects;

    /**
     * Constructs a new HDB Officer with the specified user information.
     *
     * @param nric The National Registration Identity Card number of the officer
     * @param password The password for the officer's account
     * @param age The age of the officer
     * @param maritalStatus The marital status of the officer
     */
    public HDBOfficer(String nric, String password, int age, MaritalStatus maritalStatus) {
        super(nric, password, age, maritalStatus);
        this.registeredProjects = new ArrayList<>();
    }

    /**
     * Registers this officer for a project, pending manager approval.
     * Officers cannot register for more than one project at a time or
     * for a project they have applied to as an applicant.
     *
     * @param project The project to register for
     */
    public void registerForProject(Project project) {
        if (registeredProjects.size() >= 1) {
            System.out.println("Cannot register for more than one project at a time.");
            return;
        }
        if (this.getApplication() != null && this.getApplication().getProject() == project) {
            System.out.println("Cannot register as Officer for a project you applied as Applicant.");
            return;
        }
        registeredProjects.add(project);
        project.addOfficer(this);
        System.out.println("Registered for project: " + project.getProjectName());
    }

    /**
     * Displays detailed information about a project.
     *
     * @param project The project to view details for
     */
    public void viewProjectDetails(Project project) {
        project.displayProjectDetails();
    }

    /**
     * Updates the availability of a specific flat type in a project.
     *
     * @param project The project to update
     * @param flatType The type of flat to update
     * @param count The new count of available units
     */
    public void updateFlatAvailability(Project project, FlatType flatType, int count) {
        project.updateFlatUnits(flatType, count);
        System.out.println("Updated flat availability for " + flatType + " to " + count);
    }

    /**
     * Books a flat for an applicant with a successful application.
     * Changes the application status to BOOKED.
     *
     * @param application The application for which to book a flat
     */
    public void bookFlat(Application application) {
        if (application.getStatus() != ApplicationStatus.SUCCESSFUL) {
            System.out.println("Cannot book flat. Application status is not SUCCESSFUL.");
            return;
        }
        application.updateStatus(ApplicationStatus.BOOKED);
        System.out.println("Flat booked for applicant: " + application.getApplicant().getNric());
    }

    /**
     * Replies to an enquiry submitted by a user.
     *
     * @param enquiry The enquiry to reply to
     * @param reply The reply text
     */
    public void replyEnquiry(Enquiry enquiry, String reply) {
        enquiry.setReply(reply);
        System.out.println("Replied to enquiry: " + enquiry.getContent());
    }

    /**
     * Generates a receipt for an application with a booked flat.
     *
     * @param application The application for which to generate a receipt
     * @return A Receipt object if the flat is booked, null otherwise
     */
    public Receipt generateReceipt(Application application) {
        if (application.getStatus() != ApplicationStatus.BOOKED) {
            System.out.println("Receipt cannot be generated. Flat not booked yet.");
            return null;
        }
        Receipt receipt = new Receipt(
                application.getApplicant().getNric(),
                application.getApplicant().getAge(),
                application.getApplicant().getMaritalStatus(),
                application.getFlatTypeChosen(),
                application.getProject().getProjectName(),
                new Date()
        );
        System.out.println("Receipt generated for " + application.getApplicant().getNric());
        return receipt;
    }

    /**
     * Gets the list of projects this officer is registered for.
     *
     * @return List of registered projects
     */
    public List<Project> getRegisteredProjects() {
        return registeredProjects;
    }

    /**
     * Adds a project to this officer's list of registered projects.
     *
     * @param project The project to add to the registered projects list
     */
    public void addRegisteredProject(Project project) {
        registeredProjects.add(project);
    }
}
