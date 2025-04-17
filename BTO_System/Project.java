package BTO_System;

import java.util.*;

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

    public String getProjectName() {
        return projectName;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public List<FlatType> getFlatTypes() {
        return flatTypes;
    }

    public Map<FlatType, Integer> getUnitsAvailable() {
        return unitsAvailable;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public int getOfficerSlots() {
        return officerSlots;
    }

    public List<Applicant> getApplicantsList() {
        return applicantsList;
    }

    public List<HDBOfficer> getOfficersList() {
        return officersList;
    }

    public HDBManager getManagerInCharge() {
        return managerInCharge;
    }

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void addApplicant(Applicant applicant) {
        applicantsList.add(applicant);
    }

    public void addOfficer(HDBOfficer officer) {
        if (officersList.size() < officerSlots) {
            officersList.add(officer);
        } else {
            System.out.println("Officer slots full. Cannot register more officers.");
        }
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    public void updateFlatUnits(FlatType flatType, int count) {
        if (flatTypes.contains(flatType)) {
            unitsAvailable.put(flatType, count);
        } else {
            System.out.println("Invalid flat type for this project.");
        }
    }

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

    public List<HDBOfficer> getPendingOfficerRequests() {
        return pendingOfficerRequests;
    }
    
    public void addPendingOfficerRequest(HDBOfficer officer) {
        if (!pendingOfficerRequests.contains(officer)) {
            pendingOfficerRequests.add(officer);
        }
    }
    
    public void removePendingOfficerRequest(HDBOfficer officer) {
        pendingOfficerRequests.remove(officer);
    }

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

    
}

