@startuml

' Abstract User class
+----------------------------------------------+
|                    User                      |
+----------------------------------------------+
| - nric: String                               |
| - password: String                           |
| - age: int                                   |
| - maritalStatus: MaritalStatus               |
+----------------------------------------------+
| + User(nric: String, password: String,       |
|        age: int, maritalStatus: MaritalStatus)|
| + login(nric: String, password: String): boolean |
| + changePassword(newPassword: String): void  |
| + viewProjects(projects: List<Project>): void|
| + submitEnquiry(enquiry: Enquiry,            |
|                 enquiries: List<Enquiry>): void |
| + editEnquiry(enquiryID: int, newContent:    |
|               String, enquiries: List<Enquiry>): void |
| + deleteEnquiry(enquiryID: int,              |
|                 enquiries: List<Enquiry>): void |
| + viewEnquiries(enquiries: List<Enquiry>): void |
| + getNric(): String                          |
| + getAge(): int                              |
| + getMaritalStatus(): MaritalStatus          |
+----------------------------------------------+

+------------------------------------------+
|                Applicant                 |
+------------------------------------------+
| - application: Application               |
+------------------------------------------+
| + Applicant(nric: String, password:      |
|   String, age: int, maritalStatus:       |
|   MaritalStatus)                         |
| + applyForProject(project: Project,      |
|   flatType: FlatType): void              |
| + withdrawApplication(): void            |
| + viewApplicationStatus(): void          |
| + getApplication(): Application          |
| + setApplication(application:            |
|   Application): void                     |
+------------------------------------------+

+---------------------------------------------------------------------+
|                           HDBOfficer                                |
+---------------------------------------------------------------------+
| - registeredProjects: List<Project>                                 |
+---------------------------------------------------------------------+
| + HDBOfficer(nric: String, password: String,                        |
|   age: int, maritalStatus: MaritalStatus)                           |
| + registerForProject(project: Project): void                        |
| + viewProjectDetails(project: Project): void                        |
| + updateFlatAvailability(project: Project, flatType: FlatType,      |
|   count: int): void                                                 |
| + bookFlat(application: Application): void                          |
| + replyEnquiry(enquiry: Enquiry, reply: String): void               |
| + generateReceipt(application: Application): Receipt                |
| + getRegisteredProjects(): List<Project>                            |
+---------------------------------------------------------------------+


+---------------------------------------------------------------+
|                         HDBManager                            |
+---------------------------------------------------------------+
| - projectsCreated: List<Project>                              |
+---------------------------------------------------------------+
| + HDBManager(nric: String, password: String,                  |
|   age: int, maritalStatus: MaritalStatus)                     |
| + createProject(projectName: String, neighborhood: String,    |
|   flatTypes: List<FlatType>, unitsAvailable:                 |
|   Map<FlatType, Integer>, openingDate: Date,                  |
|   closingDate: Date): void                                    |
| + toggleVisibility(project: Project): void                    |
| + approveOfficerRegistration(officer: HDBOfficer,             |
|   project: Project): void                                     |
| + approveApplication(application: Application): void          |
| + approveWithdrawal(application: Application): void           |
+---------------------------------------------------------------+


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


+---------------------------------------------------+
|                    Application                    |
+---------------------------------------------------+
| - idCounter: int (static)                         |
| - applicationID: int                              |
| - applicant: Applicant                            |
| - project: Project                                |
| - status: ApplicationStatus                       |
| - flatTypeChosen: FlatType                        |
+---------------------------------------------------+
| + Application(applicant: Applicant,              |
|   project: Project, flatTypeChosen: FlatType)     |
| + updateStatus(newStatus: ApplicationStatus): void|
| + getStatus(): ApplicationStatus                  |
| + getFlatTypeChosen(): FlatType                   |
| + getApplicant(): Applicant                       |
| + getProject(): Project                           |
| + getApplicationID(): int                         |
+---------------------------------------------------+


+-----------------------------------------------------+
|                      Enquiry                        |
+-----------------------------------------------------+
| - idCounter: int (static)                           |
| - enquiryID: int                                    |
| - content: String                                   |
| - submittedBy: User                                 |
| - relatedProject: Project                           |
| - reply: String                                     |
+-----------------------------------------------------+
| + Enquiry(content: String, submittedBy: User,       |
|   relatedProject: Project)                          |
| + getEnquiryID(): int                               |
| + getContent(): String                              |
| + setContent(content: String): void                 |
| + getSubmittedBy(): User                            |
| + getRelatedProject(): Project                      |
| + getReply(): String                                |
| + setReply(reply: String): void                     |
| + displayEnquiry(): void                            |
+-----------------------------------------------------+

+---------------------------------------------+
|                  Receipt                    |
+---------------------------------------------+
| - nric: String                              |
| - age: int                                  |
| - maritalStatus: MaritalStatus              |
| - flatType: FlatType                        |
| - projectName: String                       |
| - issueDate: Date                           |
+---------------------------------------------+
| + Receipt(nric: String, age: int,           |
|           maritalStatus: MaritalStatus,     |
|           flatType: FlatType,               |
|           projectName: String,              |
|           issueDate: Date)                  |
| + displayReceipt(): void                    |
+---------------------------------------------+


' Enums
+-------------------------+
|   <<enum>> FlatType     |
+-------------------------+
| TWOROOM                 |
| THREEROOM               |
+-------------------------+

+------------------------------+
| <<enum>> MaritalStatus       |
+------------------------------+
| SINGLE                       |
| MARRIED                      |
+------------------------------+

+-------------------------------+
| <<enum>> ApplicationStatus    |
+-------------------------------+
| PENDING                       |
| SUCCESSFUL                    |
| UNSUCCESSFUL                  |
| BOOKED                        |
+-------------------------------+

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
