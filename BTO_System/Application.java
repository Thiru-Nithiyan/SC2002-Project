package BTO_System;

/**
 * Represents a BTO housing application in the system.
 * Each Application connects an Applicant to a specific Project and FlatType.
 * The application tracks its status through the application lifecycle.
 */
class Application {
    /** Counter for generating unique application IDs */
    private static int idCounter = 1;
    
    /** Unique identifier for this application */
    private int applicationID;
    
    /** The applicant who submitted this application */
    private Applicant applicant;
    
    /** The project this application is for */
    private Project project;
    
    /** The current status of this application */
    private ApplicationStatus status;
    
    /** The type of flat chosen by the applicant */
    private FlatType flatTypeChosen;

    /**
     * Constructs a new Application with the specified details.
     * Automatically assigns a unique application ID.
     *
     * @param applicant The applicant submitting the application
     * @param project The project being applied for
     * @param flatTypeChosen The type of flat chosen by the applicant
     */
    public Application(Applicant applicant, Project project, FlatType flatTypeChosen) {
        this.applicationID = idCounter++;
        this.applicant = applicant;
        this.project = project;
        this.status = ApplicationStatus.PENDING;
        this.flatTypeChosen = flatTypeChosen;
    }

    /**
     * Updates the status of this application.
     *
     * @param newStatus The new status to set for this application
     */
    public void updateStatus(ApplicationStatus newStatus) {
        this.status = newStatus;
    }

    /**
     * Gets the current status of this application.
     *
     * @return The current application status
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Gets the flat type chosen by the applicant.
     *
     * @return The chosen flat type
     */
    public FlatType getFlatTypeChosen() {
        return flatTypeChosen;
    }

    /**
     * Gets the applicant who submitted this application.
     *
     * @return The applicant
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     * Gets the project this application is for.
     *
     * @return The project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Gets the unique identifier for this application.
     *
     * @return The application ID
     */
    public int getApplicationID() {
        return applicationID;
    }

    /**
     * Sets the flat type chosen for this application.
     *
     * @param flatTypeChosen The new flat type to set
     */
    public void setFlatTypeChosen(FlatType flatTypeChosen) {
        this.flatTypeChosen = flatTypeChosen;
    }
}