package BTO_System;

/**
 * Represents an applicant for BTO (Build-To-Order) housing projects.
 * The Applicant class extends User and manages application-related activities
 * such as applying for projects, withdrawing applications, and checking application status.
 */
class Applicant extends User {
    /** The current application associated with this applicant. */
    private Application application;

    /**
     * Constructs a new Applicant with the specified user information.
     *
     * @param nric The National Registration Identity Card number of the applicant
     * @param password The password for the applicant's account
     * @param age The age of the applicant
     * @param maritalStatus The marital status of the applicant
     */
    public Applicant(String nric, String password, int age, MaritalStatus maritalStatus) {
        super(nric, password, age, maritalStatus);
        this.application = null;
    }

    /**
     * Applies for a BTO project with a specified flat type.
     * If the applicant already has an active application, they cannot apply again.
     *
     * @param project The project to apply for
     * @param flatType The type of flat to apply for
     */
    public void applyForProject(Project project, FlatType flatType) {
        if (application == null || application.getStatus() == ApplicationStatus.UNSUCCESSFUL) {
            this.application = new Application(this, project, flatType);
            project.addApplicant(this);
            System.out.println("Applied for project: " + project.getProjectName());
        } else {
            System.out.println("You have already applied for a project.");
        }
    }
    
    /**
     * Withdraws the current application if possible.
     * Applications that are already booked or approved cannot be withdrawn.
     */
    public void withdrawApplication() {
        if (application != null) {
            if (application.getStatus() == ApplicationStatus.BOOKED || application.getStatus() == ApplicationStatus.SUCCESSFUL) {
                System.out.println("You cannot withdraw an already booked or approved application.");
            } else if (application.getStatus() == ApplicationStatus.UNSUCCESSFUL) {
                System.out.println("You have already withdrawn.");
            } else {
                application.updateStatus(ApplicationStatus.UNSUCCESSFUL);
                System.out.println("Withdrawal request submitted. Waiting for manager approval.");
            }
        } else {
            System.out.println("No application found.");
        }
    }
    
    /**
     * Displays the current status of the applicant's application.
     */
    public void viewApplicationStatus() {
        if (application != null) {
            System.out.println("Application Status: " + application.getStatus());
        } else {
            System.out.println("No application found.");
        }
    }

    /**
     * Gets the current application associated with this applicant.
     *
     * @return The current application, or null if no application exists
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Sets the application for this applicant.
     *
     * @param application The application to be set
     */
    public void setApplication(Application application) {
        this.application = application;
       }
}