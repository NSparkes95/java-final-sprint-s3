// Membership.java
package org.keyin.membership;

import java.time.LocalDate;

public class Membership {
    private int membershipId;
    private String membershipType;
    private String membershipDescription;
    private double membershipCost;
    private int memberId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isOnHold;

    public Membership(int membershipId, String membershipType, String membershipDescription, double membershipCost, int memberId) {
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
    }

    public Membership(String membershipType, String membershipDescription, double membershipCost, int memberId) {
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
    }

    public Membership(String membershipType, String membershipDescription, double membershipCost, int memberId, LocalDate startDate, LocalDate endDate, boolean isOnHold) {
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOnHold = isOnHold;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMembershipDescription() {
        return membershipDescription;
    }

    public void setMembershipDescription(String membershipDescription) {
        this.membershipDescription = membershipDescription;
    }

    public double getMembershipCost() {
        return membershipCost;
    }

    public void setMembershipCost(double membershipCost) {
        this.membershipCost = membershipCost;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isOnHold() {
        return isOnHold;
    }

    public void setOnHold(boolean onHold) {
        isOnHold = onHold;
    }

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
