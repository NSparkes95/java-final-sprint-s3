package org.keyin.memberships;
import java.time.LocalDate;

/**
 * Represents a gym membership purchased by a Member or Trainer.
 * Stores membership type, description, cost, and linked member ID.
 */
public class Membership {
    private int membershipId;
    private String membershipType;
    private String membershipDescription;
    private double membershipCost;
    private int memberId; // FK for user_id in the User class
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isOnHold;

    
    /**
     * Full constructor used when reading from the database.
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
     * Constructor used when creating a new membership (inserts).
     */
    public Membership(String membershipType, String membershipDescription, double membershipCost, int memberId, LocalDate startDate, LocalDate endDate) {
        this(-1, membershipType, membershipDescription, membershipCost, memberId, startDate, endDate, false);
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
                '}';
    }
}
