@startuml

' Abstract User class
abstract class User {
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
}

class Applicant extends User {
  - application: Application
  + applyForProject(project: Project): void
  + withdrawApplication(): void
  + viewApplicationStatus(): void
}

class HDBOfficer extends Applicant {
  - registeredProjects: List<Project>
  + registerForProject(project: Project): void
  + viewProjectDetails(project: Project): void
  + updateFlatAvailability(project: Project, flatType: FlatType, count: int): void
  + bookFlat(application: Application): void
  + replyEnquiry(enquiryID: int, reply: String): void
  + generateReceipt(application: Application): Receipt
}

class HDBManager extends User {
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
}

+--------------------------------------------------------+
|                      Project                           |
+--------------------------------------------------------+
| - projectName: String                                  |
| - neighborhood: String                                 |
| - flatTypes: List<FlatType>                            |
| - unitsAvailable: Map<FlatType, Integer>               |
| - openingDate: Date                                    |
| - closingDate: Date                                    |
| - visibility: boolean                                  |
| - officerSlots: int                                    |
| - applicantsList: List<Applicant>                      |
| - officersList: List<HDBOfficer>                       |
| - managerInCharge: HDBManager                          |
| - enquiries: List<Enquiry>                             |
+--------------------------------------------------------+
| + Project( need add  arguments )                       |
| + getProjectName(): String                             |
| + getNeighborhood(): String                            |
| + getFlatTypes(): List<FlatType>                       |
| + getUnitsAvailable(): Map<FlatType, Integer>          |
| + getOpeningDate(): Date                               |
| + getClosingDate(): Date                               |
| + isVisible(): boolean                                 |
| + setVisibility(boolean): void                         |
| + getOfficerSlots(): int                               |
| + getApplicantsList(): List<Applicant>                 |
| + getOfficersList(): List<HDBOfficer>                  |
| + getManagerInCharge(): HDBManager                     |
| + getEnquiries(): List<Enquiry>                        |
| + addApplicant(Applicant): void                        |
| + addOfficer(HDBOfficer): void                         |
| + addEnquiry(Enquiry): void                            |
| + updateFlatUnits(FlatType, int): void                 |
| + displayProjectDetails(): void                        |
+--------------------------------------------------------+


class Application {
  - applicant: Applicant
  - project: Project
  - status: ApplicationStatus
  - flatTypeChosen: FlatType
  + updateStatus(newStatus: ApplicationStatus): void
}

class Enquiry {
  - content: String
  - applicant: Applicant
  - project: Project
  - reply: String
}

class Receipt {
  - applicantName: String
  - nric: String
  - age: int
  - maritalStatus: MaritalStatus
  - flatType: FlatType
  - projectName: String
  - issueDate: Date
}

' Enums
enum ApplicationStatus {
  PENDING
  SUCCESSFUL
  UNSUCCESSFUL
  BOOKED
}

enum MaritalStatus {
  SINGLE
  MARRIED
}

enum FlatType {
  TWOROOM
  THREEROOM
}

' Relationships
User --> Enquiry : submit/view
Applicant --> Application : has
HDBOfficer --> Project : registers
HDBManager --> Project : creates/manages
Project --> Application : contains
Project --> Enquiry : contains
Project --> HDBOfficer : handled by
Application --> Project : applied to
Receipt --> Applicant : for
Receipt --> Project : relates to

@enduml
