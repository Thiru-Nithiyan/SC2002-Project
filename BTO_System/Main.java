package BTO_System;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

/**
 * The main class for the BTO Management System.
 * This class serves as the entry point and controller for the application,
 * managing user interactions, authentication, and delegating to appropriate menus.
 * 
 * @author Your Name
 * @version 1.0
 */
public class Main {
    private static List<User> users = new ArrayList<>();
    private static List<Project> projects = new ArrayList<>();
    private static List<Enquiry> enquiries = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Checks if two projects have overlapping dates.
     * 
     * @param p1 The first project to compare
     * @param p2 The second project to compare
     * @return true if the projects' dates overlap, false otherwise
     */
    private static boolean isDateOverlap(Project p1, Project p2) {
        return !(p1.getClosingDate().before(p2.getOpeningDate()) || p1.getOpeningDate().after(p2.getClosingDate()));
    }
    
    /**
     * The main method serving as the entry point for the BTO Management System.
     * Initializes the system, loads data, and presents the login interface.
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        loadInitialData();
        System.out.println("Welcome to the BTO Management System");

        while (true) {
            System.out.print("Enter NRIC: ");
            String nric = scanner.nextLine().toUpperCase();

            if (!nric.matches("[ST]\\d{7}[A-Z]")) {
                System.out.println("Invalid NRIC format.");
                continue;
            }

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            User user = login(nric, password);
            if (user == null) {
                System.out.println("Login failed. Please try again.");
                continue;
            }

            System.out.println("Login successful. Welcome, " + user.getNric());
            if (user instanceof HDBManager) {
                managerMenu((HDBManager) user);
            } else if (user instanceof HDBOfficer) {
                officerMenu((HDBOfficer) user);
            } else if (user instanceof Applicant) {
                applicantMenu((Applicant) user);
            }
        }
    }

    /**
     * Loads initial test data into the system.
     * Creates sample users, managers, officers, and projects for testing purposes.
     */
    private static void loadInitialData() {
        HDBManager jessica = new HDBManager("S5678901G", "password", 26, MaritalStatus.MARRIED);
        users.add(jessica);
        HDBManager jeff = new HDBManager("T0172419Z", "password", 26, MaritalStatus.MARRIED);
        users.add(jeff);

        HDBOfficer daniel = new HDBOfficer("T2109876H", "password", 36, MaritalStatus.SINGLE);
        HDBOfficer emily = new HDBOfficer("S6543210I", "password", 28, MaritalStatus.SINGLE);
        users.add(daniel);
        users.add(emily);

        users.add(new Applicant("S1234567A", "password", 35, MaritalStatus.SINGLE));
        users.add(new Applicant("T7654321B", "password", 40, MaritalStatus.MARRIED));
        users.add(new Applicant("S9876543C", "password", 37, MaritalStatus.MARRIED));
        users.add(new Applicant("T2345678D", "password", 30, MaritalStatus.MARRIED));
        users.add(new Applicant("S3456789E", "password", 25, MaritalStatus.SINGLE));

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<FlatType> flatTypes = Arrays.asList(FlatType.TWOROOM, FlatType.THREEROOM);
            Map<FlatType, Integer> units = new HashMap<>();
            units.put(FlatType.TWOROOM, 2);
            units.put(FlatType.THREEROOM, 3);

            Project acacia = new Project("Acacia Breeze", "Yishun", flatTypes, units,
                    sdf.parse("2025-02-15"), sdf.parse("2025-03-20"), null);
            acacia.addOfficer(daniel);
            // acacia.addOfficer(emily);
            projects.add(acacia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Authenticates a user based on NRIC and password.
     * 
     * @param nric The NRIC identifier of the user
     * @param password The password for authentication
     * @return The authenticated User object if successful, null otherwise
     */
    private static User login(String nric, String password) {
        for (User user : users) {
            if (user.login(nric, password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Displays and handles the menu options for Applicant users.
     * Provides functionality for viewing projects, applying, and managing applications.
     * 
     * @param applicant The Applicant user accessing the menu
     */
    private static void applicantMenu(Applicant applicant) {
        while (true) {
            System.out.println("\n[Applicant Menu]");
            System.out.println("1. View Available Projects");
            System.out.println("2. Apply for Project");
            System.out.println("3. View Application Status");
            System.out.println("4. Withdraw Application");
            System.out.println("5. Submit Enquiry");
            System.out.println("6. View/Edit/Delete My Enquiries");
            System.out.println("7. Change Password");
            System.out.println("8. Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    for (Project p : projects) {
                        if (p.isVisible()) {
                            boolean eligible = applicant.getMaritalStatus() == MaritalStatus.MARRIED && applicant.getAge() >= 21
                                    || applicant.getMaritalStatus() == MaritalStatus.SINGLE && applicant.getAge() >= 35;
                            if (eligible) p.displayProjectDetails();
                        }
                    }
                    break;
                case 2:
                    if (applicant.getApplication() != null &&
                            (applicant.getApplication().getStatus() == ApplicationStatus.PENDING ||
                             applicant.getApplication().getStatus() == ApplicationStatus.SUCCESSFUL ||
                             applicant.getApplication().getStatus() == ApplicationStatus.BOOKED)) {
                        System.out.println("You already have an ongoing application.");
                        break;
                    }
                    System.out.print("Enter project name: ");
                    String pname = scanner.nextLine();
                    Project selected = projects.stream()
                        .filter(p -> p.getProjectName().equalsIgnoreCase(pname) && p.isVisible())
                        .findFirst().orElse(null);

                    if (selected == null) {
                        System.out.println("Project not found or not visible.");
                        break;
                    }
                    if (applicant.getMaritalStatus() == MaritalStatus.SINGLE) {
                        if (applicant.getAge() < 35) {
                            System.out.println("You must be at least 35 and single to apply.");
                        } else {
                            applicant.applyForProject(selected, FlatType.TWOROOM);
                        }
                    } else {
                        if (applicant.getAge() < 21) {
                            System.out.println("You must be at least 21 and married to apply.");
                        } else {
                            System.out.print("Enter flat type (TWOROOM/THREEROOM): ");
                            try {
                                FlatType type = FlatType.valueOf(scanner.nextLine().toUpperCase());
                                applicant.applyForProject(selected, type);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid flat type.");
                            }
                        }
                    }
                    //added this
                    if (applicant instanceof HDBOfficer) {
                        HDBOfficer officer = (HDBOfficer) applicant;
                        for (Project registered : officer.getRegisteredProjects()) {
                            if (registered == selected || isDateOverlap(registered, selected)) {
                                System.out.println("You cannot apply for this project because you are registered as an officer for a conflicting project.");
                                return;
                            }
                        }
                    }
                    break;
                case 3:
                    applicant.viewApplicationStatus();
                    break;
                case 4:
                    applicant.withdrawApplication();
                    break;
                case 5:
                    System.out.print("Enter project name for enquiry: ");
                    String proj = scanner.nextLine();
                    Project p = projects.stream().filter(pr -> pr.getProjectName().equalsIgnoreCase(proj)).findFirst().orElse(null);
                    if (p != null) {
                        System.out.print("Enter enquiry: ");
                        String content = scanner.nextLine();
                        Enquiry e = new Enquiry(content, applicant, p);
                        p.addEnquiry(e);
                        System.out.println("Enquiry submitted.");
                    } else {
                        System.out.println("Project not found.");
                    }
                    break;
                case 6:
                    List<Enquiry> myEnquiries = new ArrayList<>();
                    for (Project projx : projects) {
                        for (Enquiry e : projx.getEnquiries()) {
                            if (e.getSubmittedBy().equals(applicant)) {
                                myEnquiries.add(e);
                                e.displayEnquiry();
                            }
                        }
                    }
                    System.out.print("Enter enquiry ID to edit/delete (blank to skip): ");
                    String input = scanner.nextLine();
                    if (!input.isBlank()) {
                        int id = Integer.parseInt(input);
                        for (Enquiry e : myEnquiries) {
                            if (e.getEnquiryID() == id) {
                                System.out.print("Edit (e) or Delete (d)? ");
                                String action = scanner.nextLine();
                                if (action.equalsIgnoreCase("e")) {
                                    System.out.print("Enter new content: ");
                                    e.setContent(scanner.nextLine());
                                    System.out.println("Updated.");
                                } else if (action.equalsIgnoreCase("d")) {
                                    e.getRelatedProject().getEnquiries().remove(e);
                                    System.out.println("Deleted.");
                                }
                                break;
                            }
                        }
                    }
                    break;
                case 7:
                    System.out.print("Enter your new password: ");
                    String newPassword = scanner.nextLine();
                    applicant.changePassword(newPassword);
                    System.out.println("Password changed successfully.");
                    break;
                
                case 8:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Displays and handles the menu options for HDB Officer users.
     * Provides functionality for project registration, enquiry management, and applicant approvals.
     * 
     * @param officer The HDBOfficer user accessing the menu
     */
    private static void officerMenu(HDBOfficer officer) {
        while (true) {
            System.out.println("\n[HDB Officer Menu]");
            System.out.println("1. View Registered Projects");
            System.out.println("2. Register for Project");
            System.out.println("3. View Project Details");
            System.out.println("4. View/Reply Enquiries");
            System.out.println("5. Book Flat for Applicant");
            System.out.println("6. Generate Receipt");
            System.out.println("7. Use Applicant Features");
            System.out.println("8. Change Password");
            System.out.println("9. Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                System.out.println("[Registered Projects]");
                boolean hasRegistered = false;
                for (Project p : projects) {
                    if (p.getOfficersList().contains(officer)) {
                        System.out.println("✔ " + p.getProjectName() + " (Approved)");
                        hasRegistered = true;
                    } else if (p.getPendingOfficerRequests().contains(officer)) {
                        System.out.println("🕓 " + p.getProjectName() + " (Pending Approval)");
                        hasRegistered = true;
                    }
                }
                if (!hasRegistered) {
                    System.out.println("You are not registered or pending for any projects.");
                }
                break;
            
                case 2:
                    System.out.print("Enter project name to register: ");
                    String name = scanner.nextLine();
                    Project regProj = projects.stream()
                            .filter(p -> p.getProjectName().equalsIgnoreCase(name)).findFirst().orElse(null);
                    if (regProj == null) {
                        System.out.println("Project not found.");
                        break;
                    }
                    if (officer.getApplication() != null) {
                        Project appliedProj = officer.getApplication().getProject();
                        if (regProj == appliedProj || isDateOverlap(regProj, appliedProj)) {
                            System.out.println("Cannot register as officer due to conflict with your application project.");
                            break;
                        }
                    }
                    boolean conflict = officer.getRegisteredProjects().stream().anyMatch(
                        p -> !(regProj.getClosingDate().before(p.getOpeningDate()) || regProj.getOpeningDate().after(p.getClosingDate()))
                    );
                    if (conflict) {
                        System.out.println("Conflict with another registered project.");
                        break;
                    }

                    if (regProj.getOfficersList().contains(officer)) {
                        System.out.println("You are already registered for this project.");
                        break;
                    }
                    
                    if (regProj.getPendingOfficerRequests().contains(officer)) {
                        System.out.println("Your registration request for this project is still pending approval.");
                        break;
                    }
                    
                    // Proceed with checks and then add request
                    regProj.addPendingOfficerRequest(officer);
                    System.out.println("Registration request submitted. Awaiting manager approval.");
                
                    break;
                    
                case 3:
                    System.out.print("Enter project name: ");
                    String projName = scanner.nextLine();
                    projects.stream()
                        .filter(p -> p.getProjectName().equalsIgnoreCase(projName))
                        .findFirst().ifPresent(Project::displayProjectDetails);
                    break;
                case 4:
                    for (Project pr : officer.getRegisteredProjects()) {
                        for (Enquiry e : pr.getEnquiries()) {
                            e.displayEnquiry();
                            System.out.print("Reply to enquiry? (y/n): ");
                            if (scanner.nextLine().equalsIgnoreCase("y")) {
                                System.out.print("Reply: ");
                                officer.replyEnquiry(e, scanner.nextLine());
                            }
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter NRIC of applicant to book: ");
                    String nric = scanner.nextLine();
                    users.stream().filter(u -> u instanceof Applicant && u.getNric().equalsIgnoreCase(nric))
                        .map(u -> (Applicant) u)
                        .findFirst().ifPresent(app -> {
                            if (app.getApplication() != null &&
                                app.getApplication().getStatus() == ApplicationStatus.SUCCESSFUL) {
                                officer.bookFlat(app.getApplication());
                            } else {
                                System.out.println("Not eligible or not found.");
                            }
                        });
                    break;
                case 6:
                System.out.print("Enter NRIC of applicant: ");
                String receiptNric = scanner.nextLine();
                users.stream().filter(u -> u instanceof Applicant && u.getNric().equalsIgnoreCase(receiptNric))
                    .map(u -> (Applicant) u)
                    .findFirst().ifPresentOrElse(app -> {
                        if (app.getApplication() == null) {
                            System.out.println("This applicant has not applied for any projects yet.");
                        } else {
                            Receipt r = officer.generateReceipt(app.getApplication());
                            if (r != null) r.displayReceipt();
                        }
                    }, () -> System.out.println("Applicant not found."));
                case 7:
                    applicantMenu(officer);
                    break;
                case 8:
                    System.out.print("Enter your new password: ");
                    String newPassword = scanner.nextLine();
                    officer.changePassword(newPassword);
                    System.out.println("Password changed successfully.");
                    break;
                
                case 9:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Displays and handles the menu options for HDB Manager users.
     * Provides functionality for creating, editing, and managing projects, as well as
     * approving applications and officer registrations.
     * 
     * @param manager The HDBManager user accessing the menu
     */
    private static void managerMenu(HDBManager manager) {
        Project activeProject = manager.getActiveProject(); // Load remembered project
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
        while (true) {
            System.out.println("\n[HDB Manager Menu]");
            System.out.println("0. View All Projects");
            System.out.println("1. Select Project to Manage");
            System.out.println("2. View My Projects");
            System.out.println("3. Create New Project");
            System.out.println("4. Edit Project Details");
            System.out.println("5. Delete Project");
            System.out.println("6. Toggle Project Visibility");
            System.out.println("7. Approve Officer Registrations");
            System.out.println("8. Approve/Reject Applications");
            System.out.println("9. Approve Withdrawals");
            System.out.println("10. Generate Application Report");
            System.out.println("11. View All Enquiries");
            System.out.println("12. Reply to Enquiries (My Projects)");
            System.out.println("13. Change Password");
            System.out.println("14. Filter Projects");
            System.out.println("15. Logout");
            System.out.print("Choose an option: ");
    
            int choice = Integer.parseInt(scanner.nextLine());
    
            switch (choice) {
                case 0:
                    for (Project p : projects) {
                        p.displayProjectDetails();
                    }
                    break;
    
                case 1:
                    List<Project> unassignedOrOwned = projects.stream()
                        .filter(p -> p.getManagerInCharge() == null || p.getManagerInCharge().equals(manager))
                        .collect(Collectors.toList());
    
                    if (unassignedOrOwned.isEmpty()) {
                        System.out.println("No projects available to manage.");
                        break;
                    }
    
                    System.out.println("Select a project to manage:");
                    for (int i = 0; i < unassignedOrOwned.size(); i++) {
                        Project p = unassignedOrOwned.get(i);
                        System.out.println((i + 1) + ". " + p.getProjectName() +
                            " (From " + sdf.format(p.getOpeningDate()) + " to " + sdf.format(p.getClosingDate()) + ")" +
                            (p.getManagerInCharge() == null ? " [Unassigned]" : ""));
                    }
    
                    System.out.print("Enter your choice: ");
                    int projChoice = Integer.parseInt(scanner.nextLine());
    
                    if (projChoice < 1 || projChoice > unassignedOrOwned.size()) {
                        System.out.println("❌ Invalid selection.");
                        break;
                    }
    
                    Project selected = unassignedOrOwned.get(projChoice - 1);
    
                    boolean conflict = projects.stream()
                        .filter(p -> p.getManagerInCharge() != null && p.getManagerInCharge().equals(manager))
                        .anyMatch(p -> isDateOverlap(p, selected) && !p.equals(selected));
    
                    if (conflict) {
                        System.out.println("❌ You cannot manage multiple projects that overlap in time.");
                        break;
                    }
    
                    if (selected.getManagerInCharge() == null) {
                        selected.setManagerInCharge(manager);
                        System.out.println("✅ You are now assigned as the manager of: " + selected.getProjectName());
                    } else if (selected.getManagerInCharge().equals(manager)) {
                        System.out.println("✅ You are already managing this project.");
                    }

                    if (activeProject != null && activeProject.equals(selected)) {
                        System.out.println("✅ This project is already your active project.");
                        break;
                    }
                    
                    manager.setActiveProject(selected);
                    activeProject = selected;
                    System.out.println("✅ Active project set: " + activeProject.getProjectName());
                    
                    break;
    
                case 2:
                    for (Project p : projects) {
                        if (p.getManagerInCharge() != null && p.getManagerInCharge().equals(manager)) {
                            p.displayProjectDetails();
                        }
                    }
                    break;
    
                case 3:
                    try {
                        System.out.print("Enter project name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter neighborhood: ");
                        String hood = scanner.nextLine();
                        List<FlatType> types = Arrays.asList(FlatType.TWOROOM, FlatType.THREEROOM);
                        Map<FlatType, Integer> units = new HashMap<>();
                        for (FlatType type : types) {
                            System.out.print("Units for " + type + ": ");
                            units.put(type, Integer.parseInt(scanner.nextLine()));
                        }
                        System.out.print("Opening date (yyyy-MM-dd): ");
                        Date open = sdf.parse(scanner.nextLine());
                        System.out.print("Closing date (yyyy-MM-dd): ");
                        Date close = sdf.parse(scanner.nextLine());
    
                        Project newProj = new Project(name, hood, types, units, open, close, null);
                        projects.add(newProj);
                        System.out.println("✅ Project created successfully.");
                    } catch (Exception e) {
                        System.out.println("Error creating project: " + e.getMessage());
                    }
                    break;
    
                case 4:
                    if (activeProject == null) {
                        System.out.println("❗ Please select a project to manage first (Option 1).");
                        break;
                    }
                    for (FlatType type : activeProject.getFlatTypes()) {
                        System.out.print("New unit count for " + type + ": ");
                        int count = Integer.parseInt(scanner.nextLine());
                        activeProject.updateFlatUnits(type, count);
                    }
                    System.out.println("✅ Project units updated.");
                    break;
    
                case 5:
                    if (activeProject == null) {
                        System.out.println("❗ Please select a project to manage first (Option 1).");
                        break;
                    }
                    projects.remove(activeProject);
                    System.out.println("✅ Project deleted: " + activeProject.getProjectName());
                    activeProject = null;
                    break;
    
                case 6:
                    if (activeProject == null) {
                        System.out.println("❗ Please select a project to manage first (Option 1).");
                        break;
                    }
                    manager.toggleVisibility(activeProject);
                    break;
    
                case 7:
                    if (activeProject == null) {
                        System.out.println("❗ Please select a project to manage first (Option 1).");
                        break;
                    }
                    Iterator<HDBOfficer> iterator = activeProject.getPendingOfficerRequests().iterator();
                    while (iterator.hasNext()) {
                        HDBOfficer officer = iterator.next();
                        if (officer.getApplication() != null) {
                            Project appliedProj = officer.getApplication().getProject();
                            if (activeProject == appliedProj || isDateOverlap(activeProject, appliedProj)) {
                                System.out.println("Officer " + officer.getNric() + " cannot be approved due to conflict.");
                                continue;
                            }
                        }
                        System.out.println("Approve officer " + officer.getNric() + "? (y/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("y")) {
                            manager.approveOfficerRegistration(officer, activeProject);
                            iterator.remove();
                            System.out.println("✅ Approved.");
                        }
                    }
                    break;
    
                case 8:
                    if (activeProject == null) {
                        System.out.println("❗ Please select a project to manage first (Option 1).");
                        break;
                    }
                    boolean foundPending = false;
                    for (User u : users) {
                        if (u instanceof Applicant) {
                            Applicant a = (Applicant) u;
                            Application app = a.getApplication();
                            if (app != null && app.getStatus() == ApplicationStatus.PENDING && app.getProject().equals(activeProject)) {
                                foundPending = true;
                                System.out.println("Application ID: " + app.getApplicationID());
                                System.out.println("Applicant NRIC: " + a.getNric());
                                System.out.print("Approve (a) / Reject (r) / Skip (s): ");
                                String decision = scanner.nextLine();
    
                                if (decision.equalsIgnoreCase("a")) {
                                    if (activeProject.getUnitsAvailable().get(app.getFlatTypeChosen()) > 0) {
                                        manager.approveApplication(app);
                                        activeProject.updateFlatUnits(app.getFlatTypeChosen(),
                                                activeProject.getUnitsAvailable().get(app.getFlatTypeChosen()) - 1);
                                        System.out.println("✅ Application approved.");
                                    } else {
                                        System.out.println("❌ No units left.");
                                    }
                                } else if (decision.equalsIgnoreCase("r")) {
                                    app.updateStatus(ApplicationStatus.UNSUCCESSFUL);
                                    System.out.println("❌ Application rejected.");
                                }
                            }
                        }
                    }
                    if (!foundPending) System.out.println("No pending applications found.");
                    break;
    
                case 9:
                    if (activeProject == null) {
                        System.out.println("❗ Please select a project to manage first (Option 1).");
                        break;
                    }
                    for (Applicant a : activeProject.getApplicantsList()) {
                        Application app = a.getApplication();
                        if (app != null && app.getStatus() == ApplicationStatus.UNSUCCESSFUL) {
                            System.out.println("Withdrawal from " + a.getNric());
                            System.out.print("Approve withdrawal? (y/n): ");
                            if (scanner.nextLine().equalsIgnoreCase("y")) {
                                manager.approveWithdrawal(app);
                                System.out.println("✅ Approved.");
                            }
                        }
                    }
                    break;
    
                case 10:
                    if (activeProject == null) {
                        System.out.println("❗ Please select a project to manage first (Option 1).");
                        break;
                    }
    
                    System.out.print("Filter by flat type (optional, press enter to skip): ");
                    String typeFilter = scanner.nextLine();
                    System.out.print("Filter by marital status (optional, press enter to skip): ");
                    String maritalFilter = scanner.nextLine();
    
                    for (User u : users) {
                        if (u instanceof Applicant) {
                            Applicant a = (Applicant) u;
                            if (a.getApplication() != null && a.getApplication().getProject().equals(activeProject)) {
                                Application app = a.getApplication();
                                boolean matchesType = typeFilter.isEmpty() ||
                                        app.getFlatTypeChosen().toString().equalsIgnoreCase(typeFilter);
                                boolean matchesMarital = maritalFilter.isEmpty() ||
                                        a.getMaritalStatus().toString().equalsIgnoreCase(maritalFilter);
    
                                if (matchesType && matchesMarital) {
                                    System.out.println("NRIC: " + a.getNric() +
                                            ", Flat: " + app.getFlatTypeChosen() +
                                            ", Marital: " + a.getMaritalStatus() +
                                            ", Project: " + app.getProject().getProjectName());
                                }
                            }
                        }
                    }
                    break;
    
                case 11:
                    for (Project p : projects) {
                        for (Enquiry e : p.getEnquiries()) {
                            e.displayEnquiry();
                        }
                    }
                    break;
    
                case 12:
                    if (activeProject == null) {
                        System.out.println("❗ Please select a project to manage first (Option 1).");
                        break;
                    }
                    for (Enquiry e : activeProject.getEnquiries()) {
                        e.displayEnquiry();
                        System.out.print("Reply? (y/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("y")) {
                            System.out.print("Reply: ");
                            e.setReply(scanner.nextLine());
                        }
                    }
                    break;
    
                case 13:
                    System.out.print("Enter your new password: ");
                    String newPassword = scanner.nextLine();
                    manager.changePassword(newPassword);
                    System.out.println("Password changed successfully.");
                    break;
    
                case 14:
                    System.out.print("Enter neighborhood to filter (or press Enter to skip): ");
                    String neighborhood = scanner.nextLine();
                    if (neighborhood.isBlank()) neighborhood = null;
    
                    System.out.print("Enter flat type (TWOROOM/THREEROOM or press Enter to skip): ");
                    String ftInput = scanner.nextLine();
                    FlatType flatType = null;
                    if (!ftInput.isBlank()) {
                        try {
                            flatType = FlatType.valueOf(ftInput.toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid flat type.");
                        }
                    }
    
                    System.out.print("Only show visible projects? (y/n): ");
                    String visibleInput = scanner.nextLine();
                    Boolean visibleOnly = null;
                    if (visibleInput.equalsIgnoreCase("y")) visibleOnly = true;
                    else if (visibleInput.equalsIgnoreCase("n")) visibleOnly = false;
    
                    List<Project> filtered = Project.filterProjectList(projects, neighborhood, flatType, visibleOnly);
                    if (filtered.isEmpty()) {
                        System.out.println("No matching projects found.");
                    } else {
                        for (Project p : filtered) {
                            p.displayProjectDetails();
                        }
                    }
                    break;
    
                case 15:
                    return;
    
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    
}
