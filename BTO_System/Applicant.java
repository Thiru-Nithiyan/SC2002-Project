package BTO_System;

class Applicant extends User {
    private Application application;

    public Applicant(String nric, String password, int age, MaritalStatus maritalStatus) {
        super(nric, password, age, maritalStatus);
    }

    public void applyForProject(Project project, FlatType flatType) {
        if (application == null || application.getStatus() == ApplicationStatus.UNSUCCESSFUL) {
            this.application = new Application(this, project, flatType);
            System.out.println("Applied for project: " + project.getProjectName());
        } else {
            System.out.println("You have already applied for a project.");
        }
    }

    public void withdrawApplication() {
        if (application != null) {
            application.updateStatus(ApplicationStatus.UNSUCCESSFUL);
            System.out.println("Application withdrawn.");
        } else {
            System.out.println("No application to withdraw.");
        }
    }

    public void viewApplicationStatus() {
        if (application != null) {
            System.out.println("Application Status: " + application.getStatus());
        } else {
            System.out.println("No application found.");
        }
    }
}
