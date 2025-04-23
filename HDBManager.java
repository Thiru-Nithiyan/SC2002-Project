package BTO_System;
import java.util.*;

/**
 * Represents a Housing Development Board (HDB) Manager who oversees BTO projects.
 * HDB Managers are responsible for creating and managing projects, approving applications,
 * managing officers, and handling various administrative tasks related to BTO projects.
 */
public class HDBManager extends User {
    /** List of projects created or managed by this manager */
    private List<Project> projectsCreated;
    
    /** The currently active project being managed */
    private Project activeProject;

    /**
     * Constructs a new HDB Manager with the specified user information.
     *
     * @param nric The National Registration Identity Card number of the manager
     * @param password The password for the manager's account
     * @param age The age of the manager
     * @param maritalStatus The marital status of the manager
     */
    public HDBManager(String nric, String password, int age, MaritalStatus maritalStatus) {
        super(nric, password, age, maritalStatus);
        this.projectsCreated = new ArrayList<>();
    }

    /**
     * Creates a new BTO project with the specified details.
     *
     * @param projectName The name of the new project
     * @param neighborhood The neighborhood where the project is located
     * @param flatTypes The types of flats available in this project
     * @param unitsAvailable Map containing the number of units available for each flat type
     * @param openingDate The date when applications for this project open
     * @param closingDate The date when applications for this project close
     */
    public void createProject(String projectName, String neighborhood, List<FlatType> flatTypes, Map<FlatType, Integer> unitsAvailable, Date openingDate, Date closingDate) {
        Project newProject = new Project(projectName, neighborhood, flatTypes, unitsAvailable, openingDate, closingDate, this);
        projectsCreated.add(newProject);
        System.out.println("Project " + projectName + " created successfully.");
    }

    /**
     * Toggles the visibility of a project.
     * If the project is currently visible, it will be hidden.
     * If the project is currently hidden, it will be made visible.
     *
     * @param project The project whose visibility is to be toggled
     */
    public void toggleVisibility(Project project) {
        project.setVisibility(!project.isVisible());
        System.out.println("Project visibility toggled.");
    }

    /**
     * Approves an officer's registration for a specific project.
     * Adds the officer to the project and updates the officer's registered projects.
     *
     * @param officer The officer to be approved
     * @param project The project for which the officer is being approved
     */
    public void approveOfficerRegistration(HDBOfficer officer, Project project) {
        project.addOfficer(officer);
        officer.addRegisteredProject(project);
        System.out.println("Officer " + officer.getNric() + " approved for project " + project.getProjectName());
    }

    /**
     * Approves an application, updating its status to SUCCESSFUL.
     *
     * @param application The application to be approved
     */
    public void approveApplication(Application application) {
        application.updateStatus(ApplicationStatus.SUCCESSFUL);
        System.out.println("Application approved.");
    }

    /**
     * Approves the withdrawal of an application, updating its status to UNSUCCESSFUL.
     *
     * @param application The application for which withdrawal is being approved
     */
    public void approveWithdrawal(Application application) {
        application.updateStatus(ApplicationStatus.UNSUCCESSFUL);
        System.out.println("Withdrawal approved.");
    }

    /**
     * Gets the currently active project being managed.
     *
     * @return The active project, or null if no project is currently active
     */
    public Project getActiveProject() {
        return activeProject;
    }
    
    /**
     * Sets the active project for this manager.
     *
     * @param project The project to set as active
     */
    public void setActiveProject(Project project) {
        this.activeProject = project;
    }
}