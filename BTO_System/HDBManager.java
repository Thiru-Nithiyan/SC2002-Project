package BTO_System;
import java.util.*;

public class HDBManager extends User {
    private List<Project> projectsCreated;

    public HDBManager(String nric, String password, int age, MaritalStatus maritalStatus) {
        super(nric, password, age, maritalStatus);
        this.projectsCreated = new ArrayList<>();
    }

    // Create a new BTO project
    public void createProject(String projectName, String neighborhood, List<FlatType> flatTypes, Map<FlatType, Integer> unitsAvailable, Date openingDate, Date closingDate) {
        Project newProject = new Project(projectName, neighborhood, flatTypes, unitsAvailable, openingDate, closingDate, this);
        projectsCreated.add(newProject);
        System.out.println("Project " + projectName + " created successfully.");
    }

    // Toggle project visibility
    public void toggleVisibility(Project project) {
        project.setVisibility(!project.isVisible());
        System.out.println("Project visibility toggled.");
    }

    // Approve an officer registration
    public void approveOfficerRegistration(HDBOfficer officer, Project project) {
        project.addOfficer(officer);
        System.out.println("Officer " + officer.getNric() + " approved for project " + project.getProjectName());
    }

    // Approve an application
    public void approveApplication(Application application) {
        application.updateStatus(ApplicationStatus.SUCCESSFUL);
        System.out.println("Application approved.");
    }

    // Approve withdrawal of an application
    public void approveWithdrawal(Application application) {
        application.updateStatus(ApplicationStatus.UNSUCCESSFUL);
        System.out.println("Withdrawal approved.");
    }
}
