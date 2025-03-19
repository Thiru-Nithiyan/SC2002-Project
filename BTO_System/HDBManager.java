package BTO_System;
import java.util.*;

public class HDBManager extends User {
    private List<Project> projectsCreated;

    public HDBManager(String nric, String password, int age, MaritalStatus maritalStatus) {
        super(nric, password, age, maritalStatus);
        this.projectsCreated = new ArrayList<>();
    }

    // Create a new BTO project
    public void createProject(String projectName, String neighborhood, Date openingDate, Date closingDate, int officerSlots) {
        Project newProject = new Project(projectName, neighborhood, openingDate, closingDate, officerSlots, this);
        projectsCreated.add(newProject);
        System.out.println("Project " + projectName + " created successfully.");
    }

    // Edit an existing project details
    public void editProject(int projectID, String newName, String newNeighborhood, Date newOpeningDate, Date newClosingDate) {
        for (Project project : projectsCreated) {
            if (project.getProjectID() == projectID) {
                project.setProjectDetails(newName, newNeighborhood, newOpeningDate, newClosingDate);
                System.out.println("Project details updated successfully.");
                return;
            }
        }
        System.out.println("Project not found.");
    }

    // Delete a project
    public void deleteProject(int projectID) {
        projectsCreated.removeIf(project -> project.getProjectID() == projectID);
        System.out.println("Project deleted successfully.");
    }

    // Toggle project visibility
    public void toggleVisibility(int projectID) {
        for (Project project : projectsCreated) {
            if (project.getProjectID() == projectID) {
                project.setVisibility(!project.isVisible());
                System.out.println("Project visibility toggled successfully.");
                return;
            }
        }
        System.out.println("Project not found.");
    }

    // Approve an officer registration
    public void approveOfficerRegistration(HDBOfficer officer, int projectID) {
        for (Project project : projectsCreated) {
            if (project.getProjectID() == projectID) {
                project.addOfficer(officer);
                System.out.println("Officer " + officer.getNric() + " approved for project " + project.getProjectName());
                return;
            }
        }
        System.out.println("Project not found.");
    }

    // Approve an application
    public void approveApplication(int applicationID, List<Application> applications) {
        for (Application application : applications) {
            if (application.getApplicationID() == applicationID) {
                application.updateStatus(ApplicationStatus.SUCCESSFUL);
                System.out.println("Application " + applicationID + " approved successfully.");
                return;
            }
        }
        System.out.println("Application not found.");
    }

    // Approve withdrawal of an application
    public void approveWithdrawal(int applicationID, List<Application> applications) {
        for (Application application : applications) {
            if (application.getApplicationID() == applicationID) {
                application.updateStatus(ApplicationStatus.UNSUCCESSFUL);
                System.out.println("Withdrawal for application " + applicationID + " approved.");
                return;
            }
        }
        System.out.println("Application not found.");
    }

    // Generate report based on filter criteria
    public void generateReport(String filterCriteria) {
        System.out.println("Generating report based on criteria: " + filterCriteria);
        // Implement logic to generate and display reports
    }

    // Reply to an enquiry
    public void replyEnquiry(int enquiryID, String reply, List<Enquiry> enquiries) {
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getEnquiryID() == enquiryID) {
                enquiry.setReply(reply);
                System.out.println("Replied to enquiry " + enquiryID + " successfully.");
                return;
            }
        }
        System.out.println("Enquiry not found.");
    }
}