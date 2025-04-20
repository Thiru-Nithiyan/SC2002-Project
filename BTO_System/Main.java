package BTO_System;


import java.util.*;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;


public class Main {
    private static List<User> users = new ArrayList<>();
    private static List<Project> projects = new ArrayList<>();
    private static List<Enquiry> enquiries = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

private static boolean isDateOverlap(Project p1, Project p2) {
    return !(p1.getClosingDate().before(p2.getOpeningDate()) || p1.getOpeningDate().after(p2.getClosingDate()));
}
    

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
                managerMenu((HDBManager) user); // Placeholder
            } else if (user instanceof HDBOfficer) {
                officerMenu((HDBOfficer) user);
            } else if (user instanceof Applicant) {
                applicantMenu((Applicant) user);
            }
        }
    }

    private static void loadInitialData() {
        HDBManager jessica = new HDBManager("S5678901G", "password", 26, MaritalStatus.MARRIED);
        users.add(jessica);

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
                    sdf.parse("2025-02-15"), sdf.parse("2025-03-20"), jessica);
            acacia.addOfficer(daniel);
            // acacia.addOfficer(emily);
            projects.add(acacia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static User login(String nric, String password) {
        for (User user : users) {
            if (user.login(nric, password)) {
                return user;
            }
        }
        return null;
    }

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

    private static void managerMenu(HDBManager manager) {
        Project activeProject = null;

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
                    // Let manager select a project to manage
                    List<Project> myProjects = projects.stream()
                        .filter(p -> p.getManagerInCharge().equals(manager))
                        .collect(Collectors.toList());
                
                    if (myProjects.isEmpty()) {
                        System.out.println("❌ You are not managing any projects.");
                        break;
                    }
                
                    System.out.println("Select a project to manage:");
                    for (int i = 0; i < myProjects.size(); i++) {
                        Project p = myProjects.get(i);
                        System.out.println((i + 1) + ". " + p.getProjectName() +
                                " (From " + new SimpleDateFormat("yyyy-MM-dd").format(p.getOpeningDate()) +
                                " to " + new SimpleDateFormat("yyyy-MM-dd").format(p.getClosingDate()) + ")");
                    }
                
                    try {
                        System.out.print("Enter your choice: ");
                        int projChoice = Integer.parseInt(scanner.nextLine());
                
                        if (projChoice < 1 || projChoice > myProjects.size()) {
                            System.out.println("❌ Invalid selection.");
                            break;
                        }
                
                        Project selectedProject = myProjects.get(projChoice - 1);
                
                        if (activeProject != null && !activeProject.equals(selectedProject)) {
                            if (isDateOverlap(activeProject, selectedProject)) {
                                System.out.println("❌ You can only manage one project in the same application period.");
                                break;
                            }
                        }
                
                        activeProject = selectedProject;
                        System.out.println("✅ You are now managing: " + activeProject.getProjectName());
                
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid input.");
                    }
                    break;
                
                
                
                case 2:
                    for (Project p : projects) {
                        if (p.getManagerInCharge().equals(manager)) {
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
                        Date open = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
                        System.out.print("Closing date (yyyy-MM-dd): ");
                        Date close = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
                
                        Project newProj = new Project(name, hood, types, units, open, close, manager);
                        projects.add(newProj);
                        System.out.println("✅ Project created successfully.");
                    } catch (Exception e) {
                        System.out.println("Error creating project: " + e.getMessage());
                    }
                    break;
                
                
                case 4:
                    System.out.print("Enter project name to edit: ");
                    String editName = scanner.nextLine();
                    Project editProj = findProjectByNameAndManager(editName, manager);
                    if (editProj != null) {
                        for (FlatType type : editProj.getFlatTypes()) {
                            System.out.print("New unit count for " + type + ": ");
                            int count = Integer.parseInt(scanner.nextLine());
                            editProj.updateFlatUnits(type, count);
                        }
                    } else {
                        System.out.println("Project not found or not yours.");
                    }
                    break;
                case 5:
                    System.out.print("Enter project name to delete: ");
                    String delName = scanner.nextLine();
                    Project delProj = findProjectByNameAndManager(delName, manager);
                    if (delProj != null) {
                        projects.remove(delProj);
                        System.out.println("Deleted.");
                    } else {
                        System.out.println("Not found or unauthorized.");
                    }
                    break;
                case 6:
                    System.out.print("Enter project name to toggle: ");
                    String tname = scanner.nextLine();
                    for (Project p : projects) {
                        if (p.getProjectName().equalsIgnoreCase(tname)) {
                            manager.toggleVisibility(p);
                            break;
                        }
                    }
                    break;
                case 7:
                    Project activeProject7 = selectActiveProject(manager);
                    if (activeProject7 == null) break;
                    Iterator<HDBOfficer> iterator = activeProject7.getPendingOfficerRequests().iterator();
                    while (iterator.hasNext()) {
                        HDBOfficer officer = iterator.next();
                        if (officer.getApplication() != null) {
                            Project appliedProj = officer.getApplication().getProject();
                            if (activeProject7 == appliedProj || isDateOverlap(activeProject7, appliedProj)) {
                                System.out.println("Officer " + officer.getNric() + " cannot be approved due to conflict.");
                                continue;
                            }
                        }
                        System.out.println("Approve officer " + officer.getNric() + "? (y/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("y")) {
                            manager.approveOfficerRegistration(officer, activeProject7);
                            iterator.remove();
                            System.out.println("Approved.");
                        }
                    }
                    break;
                
            
                case 8:
                    Project activeProject8 = selectActiveProject(manager);
                    if (activeProject8 == null) break;
                
                    boolean foundPending = false;
                    List<Applicant> applicants = users.stream()
                        .filter(u -> u instanceof Applicant)
                        .map(u -> (Applicant) u)
                        .collect(Collectors.toList()); // Fix for .toList()
                
                    for (Applicant a : applicants) {
                        Application app = a.getApplication();
                        if (app != null && app.getStatus() == ApplicationStatus.PENDING && app.getProject().equals(activeProject8)) {
                            foundPending = true;
                            System.out.println("Application ID: " + app.getApplicationID());
                            System.out.println("Applicant NRIC: " + a.getNric());
                            System.out.print("Approve (a) / Reject (r) / Skip (s): ");
                            String decision = scanner.nextLine();
                
                            if (decision.equalsIgnoreCase("a")) {
                                if (activeProject8.getUnitsAvailable().get(app.getFlatTypeChosen()) > 0) {
                                    manager.approveApplication(app);
                                    activeProject8.updateFlatUnits(app.getFlatTypeChosen(),
                                            activeProject8.getUnitsAvailable().get(app.getFlatTypeChosen()) - 1);
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
                    if (!foundPending) System.out.println("No pending applications found.");
                    break;
                
            
            
                case 9:
                    Project activeProject9 = selectActiveProject(manager);
                    if (activeProject9 == null) break;
                
                    for (Applicant a : activeProject9.getApplicantsList()) {
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
                System.out.print("Filter by flat type (optional, press enter to skip): ");
                String typeFilter = scanner.nextLine();
                System.out.print("Filter by marital status (optional, press enter to skip): ");
                String maritalFilter = scanner.nextLine();
                
                for (User u : users) {
                    if (u instanceof Applicant) {
                        Applicant a = (Applicant) u;
                        if (a.getApplication() != null) {
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
                    Project activeProject12 = selectActiveProject(manager);
                    if (activeProject12 == null) break;
                
                    for (Enquiry e : activeProject12.getEnquiries()) {
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
    
    private static Project findProjectByNameAndManager(String name, HDBManager manager) {
        return projects.stream()
            .filter(p -> p.getProjectName().equalsIgnoreCase(name) && p.getManagerInCharge().equals(manager))
            .findFirst().orElse(null);
    }

    private static Project selectActiveProject(HDBManager manager) {
        List<Project> ownedProjects = new ArrayList<>();
        for (Project p : projects) {
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
    
    
}
