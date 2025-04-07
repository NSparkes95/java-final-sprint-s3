package org.keyin.membership;

import java.time.LocalDate;

/**
 * Represents a gym membership purchased by a Member or Trainer.
 * Stores membership details including type, description, cost, and associated user ID.
 */
public class Membership {
    private int membershipId;
    private String membershipType;
    private String membershipDescription;
    private double membershipCost;
    private int memberId; // Foreign key referencing a user
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isOnHold;
    private String paymentMethod; 
    private String status;        

    /**
     * Full constructor for reading a membership from the database.
     *
     * @param membershipId ID of the membership
     * @param membershipType Type of the membership (e.g., Monthly, Annual)
     * @param membershipDescription Description of the membership
     * @param membershipCost Cost of the membership
     * @param memberId ID of the user who owns the membership
     * @param startDate Start date of the membership
     * @param endDate End date of the membership
     * @param isOnHold Whether the membership is currently on hold
     * @param paymentMethod Payment method used for the membership
     * @param status Status of the membership (e.g., "active", "inactive")
     */
    public Membership(int membershipId, String membershipType, String membershipDescription, double membershipCost, int memberId, LocalDate startDate, LocalDate endDate, boolean isOnHold, String paymentMethod, String status) {
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOnHold = isOnHold;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    /**
     * Constructor used when creating a new membership (typically for inserts).
     *
     * @param membershipType Type of the membership
     * @param membershipDescription Description of the membership
     * @param membershipCost Cost of the membership
     * @param memberId ID of the user who is buying the membership
     * @param startDate Start date of the membership
     * @param endDate End date of the membership
     */
    public Membership(String membershipType, String membershipDescription, double membershipCost, int memberId, LocalDate startDate, LocalDate endDate, String paymentMethod, String status) {
        this(-1, membershipType, membershipDescription, membershipCost, memberId, startDate, endDate, false, paymentMethod, status);
    }

    // Getters and Setters

    public int getMembershipId() { return membershipId; }
    public void setMembershipId(int membershipId) { this.membershipId = membershipId; }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }

    public String getMembershipDescription() { return membershipDescription; }
    public void setMembershipDescription(String membershipDescription) { this.membershipDescription = membershipDescription; }

    public double getMembershipCost() { return membershipCost; }
    public void setMembershipCost(double membershipCost) { this.membershipCost = membershipCost; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public boolean isOnHold() { return isOnHold; }
    public void setOnHold(boolean onHold) { isOnHold = onHold; }

    public String getPaymentMethod() { return paymentMethod; }  // New getter
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }  // New setter

    public String getStatus() { return status; }  // New getter
    public void setStatus(String status) { this.status = status; }  // New setter

    /**
     * Returns a string representation of the membership.
     *
     * @return A string containing all membership fields.
     */
    @Override
    public String toString() {
        return "Membership{" +
                "membershipId=" + membershipId +
                ", membershipType='" + membershipType + '\'' +
                ", membershipDescription='" + membershipDescription + '\'' +
                ", membershipCost=" + membershipCost +
                ", memberId=" + memberId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isOnHold=" + isOnHold +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
