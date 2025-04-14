// Membership.java
package org.keyin.membership;

/**
 * Represents a gym membership assigned to a user.
 * Contains information such as type, description, cost, and the ID of the member.
 */
public class Membership {
    private int membershipId;
    private String membershipType;
    private String membershipDescription;
    private double membershipCost;
    private int memberId;

    /**
     * Constructor used when fetching memberships from the database.
     */
    public Membership(int membershipId, String membershipType, String membershipDescription, double membershipCost, int memberId) {
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
    }

    /**
     * Constructor used when creating a new membership (ID is auto-generated).
     */
    public Membership(String membershipType, String membershipDescription, double membershipCost, int memberId) {
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
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

    @Override
public String toString() {
    return "Membership ID: " + membershipId +
           ", Type: " + membershipType +
           ", Description: " + membershipDescription +
           ", Cost: $" + membershipCost +
           ", Member ID: " + memberId;
}

} 
