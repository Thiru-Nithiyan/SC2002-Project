# SC2002-Project

class User:
    - nric: String
    - password: String
    - age: int
    - maritalStatus: MaritalStatus
    + login(): void
    + changePassword(newPassword: String): void
    + viewProjects(): void
    + submitEnquiry(enquiry: Enquiry): void
    + editEnquiry(enquiryID: int): void
    + deleteEnquiry(enquiryID: int): void
    + viewEnquiries(): void

class Applicant(User):
    - application: Application
    + applyForProject(project: Project): void
    + withdrawApplication(): void
    + viewApplicationStatus(): void

class HDBOfficer(Applicant):
    - registeredProjects: List<Project>
    + registerForProject(project: Project): void
    + viewProjectDetails(project: Project): void
    + updateFlatAvailability(project: Project, flatType: FlatType, count: int): void
    + bookFlat(application: Application): void
    + replyEnquiry(enquiryID: int, reply: String): void
    + generateReceipt(application: Application): Receipt

class HDBManager(User):
    - projectsCreated: List<Project>
    + createProject(projectDetails): void
    + editProject(projectID: int, newDetails): void
    + deleteProject(projectID: int): void
    + toggleVisibility(projectID: int): void
    + approveOfficerRegistration(officerID: int, projectID: int): void
    + approveApplication(applicationID: int): void
    + approveWithdrawal(applicationID: int): void
    + generateReport(filterCriteria): Report
    + replyEnquiry(enquiryID: int, reply: String): void

class Project:
    - projectName: String
    - neighborhood: String
    - flatTypes: List<FlatType>
    - unitsAvailable: Dict<FlatType, int>
    - openingDate: Date
    - closingDate: Date
    - visibility: Boolean
    - officerSlots: int
    - applicantsList: List<Applicant>
    - officersList: List<HDBOfficer>
    - managerInCharge: HDBManager
    + filterProjectList(criteria): List<Project>

class Application:
    - applicant: Applicant
    - project: Project
    - status: ApplicationStatus
    - flatTypeChosen: FlatType
    + updateStatus(newStatus: ApplicationStatus): void

class Enquiry:
    - content: String
    - applicant: Applicant
    - project: Project
    - reply: String

enum ApplicationStatus:
    PENDING
    SUCCESSFUL
    UNSUCCESSFUL
    BOOKED

enum MaritalStatus:
    SINGLE
    MARRIED

enum FlatType:
    TWOROOM
    THREEROOM

class Receipt:
    - applicantName: String
    - nric: String
    - age: int
    - maritalStatus: MaritalStatus
    - flatType: FlatType
    - projectName: String