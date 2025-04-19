package BTO_System;

class Application {
    private static int idCounter = 1;
    private int applicationID;
    private Applicant applicant;
    private Project project;
    private ApplicationStatus status;
    private FlatType flatTypeChosen;

    public Application(Applicant applicant, Project project, FlatType flatTypeChosen) {
        this.applicationID = idCounter++;
        this.applicant = applicant;
        this.project = project;
        this.status = ApplicationStatus.PENDING;
        this.flatTypeChosen = flatTypeChosen;
    }

    public void updateStatus(ApplicationStatus newStatus) {
        this.status = newStatus;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public FlatType getFlatTypeChosen() {
        return flatTypeChosen;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Project getProject() {
        return project;
    }

    public int getApplicationID() {
        return applicationID;
    }

    public void setFlatTypeChosen(FlatType flatTypeChosen) {
        this.flatTypeChosen = flatTypeChosen;
    }
    
}
