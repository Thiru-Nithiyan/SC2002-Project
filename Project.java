package BTO_System;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Represents a BTO (Build-To-Order) housing project managed by the Housing Development Board.
 * This class encapsulates all information about a BTO project including its location,
 * available flat types, unit counts, application dates, and associated users.
 */
public class Project {
    private String projectName;
    private String neighborhood;
    private List<FlatType> flatTypes;
    private Map<FlatType, Integer> unitsAvailable;
    private Date openingDate;
    private Date closingDate;
    private boolean visibility;
    private int officerSlots;
    private List<Applicant> applicantsList;
    private List<HDBOfficer> officersList;
    private HDBManager managerInCharge;
    private List<Enquiry> enquiries;
    private List<HDBOfficer> pendingOfficerRequests = new ArrayList<>();

    /**
     * Constructs a new BTO Project with the specified details.
     *
     * @param projectName     The name of the BTO project
     * @param neighborhood    The neighborhood where the project is located
     * @param flatTypes       The types of flats available in this project
     * @param unitsAvailable  Map containing the number of units available for each flat type
     * @param openingDate     The date when applications for this project open
     * @param closingDate     The date when applications for this project close
     * @param managerInCharge The HDB manager assigned to oversee this project
     */
    public Project(String projectName, String neighborhood, List<FlatType> flatTypes, 
                   Map<FlatType, Integer> unitsAvailable, Date openingDate, 
                   Date closingDate, HDBManager managerInCharge) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.flatTypes = flatTypes;
        this.unitsAvailable = new HashMap<>(unitsAvailable);
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.visibility = true;
        this.officerSlots = 10;
        this.applicantsList = new ArrayList<>();
        this.officersList = new ArrayList<>();
        this.managerInCharge = managerInCharge;
        this.enquiries = new ArrayList<>();
    }

    /**
     * Gets the name of this BTO project.
     *
     * @return The project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Gets the neighborhood where this project is located.
     *
     * @return The neighborhood name
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Gets the list of flat types available in this project.
     *
     * @return List of available flat types
     */
    public List<FlatType> getFlatTypes() {
        return flatTypes;
    }

    /**
     * Gets the number of units available for each flat type.
     *
     * @return Map of flat types to number of available units
     */
    public Map<FlatType, Integer> getUnitsAvailable() {
        return unitsAvailable;
    }

    /**
     * Gets the date when applications for this project open.
     *
     * @return The opening date
     */
    public Date getOpeningDate() {
        return openingDate;
    }

    /**
     * Gets the date when applications for this project close.
     *
     * @return The closing date
     */
    public Date getClosingDate() {
        return closingDate;
    }

    /**
     * Checks whether this project is visible to applicants.
     *
     * @return true if the project is visible, false otherwise
     */
    public boolean isVisible() {
        return visibility;
    }

    /**
     * Sets the visibility of this project to applicants.
     *
     * @param visibility true to make the project visible, false to hide it
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Gets the total number of officer slots available for this project.
     *
     * @return The number of officer slots
     */
    public int getOfficerSlots() {
        return officerSlots;
    }

    /**
     * Gets the list of applicants who have applied for this project.
     *
     * @return List of applicants
     */
    public List<Applicant> getApplicantsList() {
        return applicantsList;
    }

    /**
     * Gets the list of HDB officers assigned to this project.
     *
     * @return List of officers
     */
    public List<HDBOfficer> getOfficersList() {
        return officersList;
    }

    /**
     * Gets the HDB manager in charge of this project.
     *
     * @return The manager in charge, or null if no manager is assigned
     */
    public HDBManager getManagerInCharge() {
        return managerInCharge;
    }

    /**
     * Gets the list of enquiries submitted for this project.
     *
     * @return List of enquiries
     */
    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    /**
     * Adds an applicant to this project's list of applicants.
     *
     * @param applicant The applicant to add
     */
    public void addApplicant(Applicant applicant) {
        applicantsList.add(applicant);
    }

    /**
     * Adds an HDB officer to this project if there are slots available.
     *
     * @param officer The officer to add
     */
    public void addOfficer(HDBOfficer officer) {
        if (officersList.size() < officerSlots) {
            officersList.add(officer);
        } else {
            System.out.println("Officer slots full. Cannot register more officers.");
        }
    }

    /**
     * Adds an enquiry to this project's list of enquiries.
     *
     * @param enquiry The enquiry to add
     */
    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    /**
     * Updates the number of available units for a specific flat type.
     *
     * @param flatType The flat type to update
     * @param count    The new number of available units
     */
    public void updateFlatUnits(FlatType flatType, int count) {
        if (flatTypes.contains(flatType)) {
            unitsAvailable.put(flatType, count);
        } else {
            System.out.println("Invalid flat type for this project.");
        }
    }

    /**
     * Displays detailed information about this project to the console.
     */
    public void displayProjectDetails() {
        System.out.println("Project Name: " + projectName);
        System.out.println("Neighborhood: " + neighborhood);
        System.out.println("Flat Types: " + flatTypes);
        System.out.println("Units Available: " + unitsAvailable);
        System.out.println("Opening Date: " + openingDate);
        System.out.println("Closing Date: " + closingDate);
        System.out.println("Visibility: " + (visibility ? "Visible" : "Hidden"));
        System.out.println("Remaining Officer Slots: " + (officerSlots - officersList.size()));
    }

    /**
     * Gets the list of pending officer registration requests for this project.
     *
     * @return List of officers with pending registration requests
     */
    public List<HDBOfficer> getPendingOfficerRequests() {
        return pendingOfficerRequests;
    }
    
    /**
     * Adds an officer's registration request to the pending list if not already present.
     *
     * @param officer The officer making the registration request
     */
    public void addPendingOfficerRequest(HDBOfficer officer) {
        if (!pendingOfficerRequests.contains(officer)) {
            pendingOfficerRequests.add(officer);
        }
    }
    
    /**
     * Removes an officer's registration request from the pending list.
     *
     * @param officer The officer whose request should be removed
     */
    public void removePendingOfficerRequest(HDBOfficer officer) {
        pendingOfficerRequests.remove(officer);
    }

    /**
     * Filters a list of projects based on specified criteria.
     *
     * @param projects          The list of projects to filter
     * @param neighborhoodFilter The neighborhood to filter by, or null for any neighborhood
     * @param flatTypeFilter    The flat type to filter by, or null for any flat type
     * @param visibleOnly       Whether to include only visible projects, or null for all projects
     * @return A filtered list of projects matching the criteria
     */
    public static List<Project> filterProjectList(List<Project> projects, String neighborhoodFilter, FlatType flatTypeFilter, Boolean visibleOnly) {
        List<Project> filtered = new ArrayList<>();
        for (Project p : projects) {
            boolean matchNeighborhood = (neighborhoodFilter == null || p.getNeighborhood().equalsIgnoreCase(neighborhoodFilter));
            boolean matchFlatType = (flatTypeFilter == null || p.getFlatTypes().contains(flatTypeFilter));
            boolean matchVisibility = (visibleOnly == null || p.isVisible() == visibleOnly);

            if (matchNeighborhood && matchFlatType && matchVisibility) {
                filtered.add(p);
            }
        }
        return filtered;
    }

    /**
     * Finds a project by name and manager from a list of projects.
     *
     * @param name        The name of the project to find
     * @param manager     The manager of the project to find
     * @param allProjects The list of projects to search
     * @return The matching project, or null if no match is found
     */
    public static Project findProjectByNameAndManager(String name, HDBManager manager, List<Project> allProjects) {
         return allProjects.stream()
        .filter(p -> p.getProjectName().equalsIgnoreCase(name) && p.getManagerInCharge().equals(manager))
        .findFirst().orElse(null);
    }

    /**
     * Helps a manager select an active project from those they are assigned to.
     *
     * @param manager     The manager selecting a project
     * @param allProjects The list of all projects
     * @param scanner     Scanner for reading user input
     * @return The selected project, or null if selection failed
     */
    public static Project selectActiveProject(HDBManager manager, List<Project> allProjects, Scanner scanner) {
        List<Project> ownedProjects = new ArrayList<>();
        for (Project p : allProjects) {
            if (p.getManagerInCharge().equals(manager)) {
                ownedProjects.add(p);
            }
        }

        if (ownedProjects.isEmpty()) {
            System.out.println("You are not assigned to any projects.");
            return null;
        }

        if (ownedProjects.size() == 1) {
            return ownedProjects.get(0); // only one to choose from
        }

        // Show projects to select from
        System.out.println("\nYou are assigned to multiple projects. Select one to manage:");
        for (int i = 0; i < ownedProjects.size(); i++) {
            System.out.println((i + 1) + ". " + ownedProjects.get(i).getProjectName() +
                " (From " + new SimpleDateFormat("yyyy-MM-dd").format(ownedProjects.get(i).getOpeningDate()) +
                " to " + new SimpleDateFormat("yyyy-MM-dd").format(ownedProjects.get(i).getClosingDate()) + ")");
        }

        System.out.print("Enter number: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice >= 1 && choice <= ownedProjects.size()) {
                return ownedProjects.get(choice - 1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
     }

        System.out.println("Invalid selection.");
        return null;
    }

    /**
     * Sets the manager in charge of this project.
     *
     * @param manager The manager to assign to this project
     */
    public void setManagerInCharge(HDBManager manager) {
        this.managerInCharge = manager;
    }
}