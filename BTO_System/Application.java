package BTO_System;

class Application {
    private Applicant applicant;
    private Project project;
    private ApplicationStatus status;
    private FlatType flatTypeChosen;

    public Application(Applicant applicant, Project project, FlatType flatTypeChosen) {
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
}
