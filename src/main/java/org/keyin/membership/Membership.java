// Membership.java
package org.keyin.membership;

import java.time.LocalDate;

/**
 * Represents a gym membership associated with a specific member.
 * Includes membership details such as type, description, cost,
 * membership period, and on-hold status.
 */
public class Membership {
    private int membershipId;
    private String membershipType;
    private String membershipDescription;
    private double membershipCost;
    private int memberId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isOnHold;

    /**
     * Constructor with ID, used when retrieving membership records from the database.
     */
    public Membership(int membershipId, String membershipType, String membershipDescription, double membershipCost, int memberId) {
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
    }

    /**
     * Constructor for creating new memberships without an ID.
     */
    public Membership(String membershipType, String membershipDescription, double membershipCost, int memberId) {
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
    }

    /**
     * Constructor with all fields except ID. Useful when inserting a new membership.
     */
    public Membership(String membershipType, String membershipDescription, double membershipCost, int memberId, LocalDate startDate, LocalDate endDate, boolean isOnHold) {
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOnHold = isOnHold;
    }

    /**
     * Full constructor including ID and all membership details.
     */
    public Membership(int membershipId, String membershipType, String membershipDescription, double membershipCost, int memberId, LocalDate startDate, LocalDate endDate, boolean isOnHold) {
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOnHold = isOnHold;
    }

    /**
     * @return membership's unique ID
     */
    public int getMembershipId() {
        return membershipId;
    }

    /**
     * @param membershipId set a new membership ID
     */
    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    /**
     * @return the type of membership (e.g., Basic, Premium)
     */
    public String getMembershipType() {
        return membershipType;
    }

    /**
     * @param membershipType update the membership type
     */
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    /**
     * @return description or notes about the membership
     */
    public String getMembershipDescription() {
        return membershipDescription;
    }

    /**
     * @param membershipDescription set a new description for the membership
     */
    public void setMembershipDescription(String membershipDescription) {
        this.membershipDescription = membershipDescription;
    }

    /**
     * @return cost associated with the membership
     */
    public double getMembershipCost() {
        return membershipCost;
    }

    /**
     * @param membershipCost update the cost of the membership
     */
    public void setMembershipCost(double membershipCost) {
        this.membershipCost = membershipCost;
    }

    /**
     * @return ID of the user who owns this membership
     */
    public int getMemberId() {
        return memberId;
    }

    /**
     * @param memberId set the user ID associated with this membership
     */
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    /**
     * @return start date of the membership
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate set when the membership becomes active
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return end date of the membership
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate define when the membership expires
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * @return true if the membership is currently on hold
     */
    public boolean isOnHold() {
        return isOnHold;
    }

    /**
     * @param onHold set whether the membership is paused
     */
    public void setOnHold(boolean onHold) {
        isOnHold = onHold;
    }

    /**
     * Returns a readable string describing the membership.
     *
     * @return string with key membership info
     */
    @Override
    public String toString() {
        return "Membership ID: " + membershipId +
               ", Type: " + membershipType +
               ", Description: " + membershipDescription +
               ", Cost: $" + membershipCost +
               ", Member ID: " + memberId +
               ", Start Date: " + (startDate != null ? startDate : "N/A") +
               ", End Date: " + (endDate != null ? endDate : "N/A") +
               ", On Hold: " + (isOnHold ? "Yes" : "No");
    }
}
