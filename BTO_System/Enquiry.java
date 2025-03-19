package BTO_System;

public class Enquiry {
    private static int idCounter = 1; // For auto-generating enquiry IDs
    private int enquiryID;
    private String content;
    private User submittedBy;
    private Project relatedProject;
    private String reply;

    public Enquiry(String content, User submittedBy, Project relatedProject) {
        this.enquiryID = idCounter++;
        this.content = content;
        this.submittedBy = submittedBy;
        this.relatedProject = relatedProject;
        this.reply = null; // No reply yet when enquiry is submitted
    }

    // Getters and Setters
    public int getEnquiryID() {
        return enquiryID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSubmittedBy() {
        return submittedBy;
    }

    public Project getRelatedProject() {
        return relatedProject;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    // Display Enquiry Details
    public void displayEnquiry() {
        System.out.println("Enquiry ID: " + enquiryID);
        System.out.println("Submitted By: " + submittedBy.getNric());
        System.out.println("Project: " + relatedProject.getProjectName());
        System.out.println("Content: " + content);
        if (reply != null) {
            System.out.println("Reply: " + reply);
        } else {
            System.out.println("Reply: [No reply yet]");
        }
    }
}

