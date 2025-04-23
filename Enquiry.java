package BTO_System;

/**
 * Represents an enquiry submitted by a user regarding a specific BTO project.
 * Enquiries can be submitted by any user and may be replied to by HDB officers or managers.
 */
public class Enquiry {
    /** Counter for generating unique enquiry IDs */
    private static int idCounter = 1;
    
    /** Unique identifier for this enquiry */
    private int enquiryID;
    
    /** The content/question of the enquiry */
    private String content;
    
    /** The user who submitted this enquiry */
    private User submittedBy;
    
    /** The project this enquiry is related to */
    private Project relatedProject;
    
    /** The reply to this enquiry, null if not yet replied */
    private String reply;

    /**
     * Constructs a new Enquiry with the specified details.
     * Automatically assigns a unique enquiry ID.
     *
     * @param content The content/question of the enquiry
     * @param submittedBy The user who submitted the enquiry
     * @param relatedProject The project this enquiry is related to
     */
    public Enquiry(String content, User submittedBy, Project relatedProject) {
        this.enquiryID = idCounter++;
        this.content = content;
        this.submittedBy = submittedBy;
        this.relatedProject = relatedProject;
        this.reply = null;
    }

    /**
     * Gets the unique identifier for this enquiry.
     *
     * @return The enquiry ID
     */
    public int getEnquiryID() {
        return enquiryID;
    }

    /**
     * Gets the content/question of this enquiry.
     *
     * @return The enquiry content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets/updates the content of this enquiry.
     *
     * @param content The new content for this enquiry
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the user who submitted this enquiry.
     *
     * @return The user who submitted the enquiry
     */
    public User getSubmittedBy() {
        return submittedBy;
    }

    /**
     * Gets the project related to this enquiry.
     *
     * @return The related project
     */
    public Project getRelatedProject() {
        return relatedProject;
    }

    /**
     * Gets the reply to this enquiry.
     *
     * @return The reply, or null if not yet replied
     */
    public String getReply() {
        return reply;
    }

    /**
     * Sets a reply to this enquiry.
     *
     * @param reply The reply text
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * Displays the details of this enquiry to the console.
     * Includes the enquiry ID, submitter, project, content, and reply (if any).
     */
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