package BTO_System;

import java.util.*;

public class HDBOfficer extends Applicant {
    private List<Project> registeredProjects;

    public HDBOfficer(String nric, String password, int age, MaritalStatus maritalStatus) {
        super(nric, password, age, maritalStatus);
        this.registeredProjects = new ArrayList<>();
    }

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

    public void viewProjectDetails(Project project) {
        project.displayProjectDetails();
    }

    public void updateFlatAvailability(Project project, FlatType flatType, int count) {
        project.updateFlatUnits(flatType, count);
        System.out.println("Updated flat availability for " + flatType + " to " + count);
    }

    public void bookFlat(Application application) {
        if (application.getStatus() != ApplicationStatus.SUCCESSFUL) {
            System.out.println("Cannot book flat. Application status is not SUCCESSFUL.");
            return;
        }
        application.updateStatus(ApplicationStatus.BOOKED);
        System.out.println("Flat booked for applicant: " + application.getApplicant().getNric());
    }

    public void replyEnquiry(Enquiry enquiry, String reply) {
        enquiry.setReply(reply);
        System.out.println("Replied to enquiry: " + enquiry.getContent());
    }

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

    public List<Project> getRegisteredProjects() {
        return registeredProjects;
    }

    public void addRegisteredProject(Project project) {
        registeredProjects.add(project);
    }
    
}
