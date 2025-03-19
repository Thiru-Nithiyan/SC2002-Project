package BTO_System;
import java.util.List;

enum ApplicationStatus {
    PENDING,
    SUCCESSFUL,
    UNSUCCESSFUL,
    BOOKED
}

enum MaritalStatus {
    SINGLE,
    MARRIED
}

enum FlatType {
    TWOROOM,
    THREEROOM
}

class User {
    private String nric;
    private String password;
    private int age;
    private MaritalStatus maritalStatus;

    public User(String nric, String password, int age, MaritalStatus maritalStatus) {
        this.nric = nric;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    public boolean login(String nric, String password) {
        return this.nric.equals(nric) && this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void viewProjects(List<Project> projects) {
        for (Project project : projects) {
            if (project.isVisible()) {
                project.displayProjectDetails();
            }
        }
    }

    public void submitEnquiry(Enquiry enquiry, List<Enquiry> enquiries) {
        enquiries.add(enquiry);
    }

    public void editEnquiry(int enquiryID, String newContent, List<Enquiry> enquiries) {
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getEnquiryID() == enquiryID) {
                enquiry.setContent(newContent);
                break;
            }
        }
    }

    public void deleteEnquiry(int enquiryID, List<Enquiry> enquiries) {
        enquiries.removeIf(enquiry -> enquiry.getEnquiryID() == enquiryID);
    }

    public void viewEnquiries(List<Enquiry> enquiries) {
        for (Enquiry enquiry : enquiries) {
            enquiry.displayEnquiry();
        }
    }

    public String getNric() {
        return nric;
    }

    public int getAge() {
        return age;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }
}
