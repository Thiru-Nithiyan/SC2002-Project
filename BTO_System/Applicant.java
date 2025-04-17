package BTO_System;

class Applicant extends User {
    private Application application;

    public Applicant(String nric, String password, int age, MaritalStatus maritalStatus) {
        super(nric, password, age, maritalStatus);
        this.application = null;
    }

    public void applyForProject(Project project, FlatType flatType) {
        if (application == null || application.getStatus() == ApplicationStatus.UNSUCCESSFUL) {
            this.application = new Application(this, project, flatType);
            project.addApplicant(this);
            System.out.println("Applied for project: " + project.getProjectName());
        } else {
            System.out.println("You have already applied for a project.");
        }
    }
    

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
    

    public void viewApplicationStatus() {
        if (application != null) {
            System.out.println("Application Status: " + application.getStatus());
        } else {
            System.out.println("No application found.");
        }
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
