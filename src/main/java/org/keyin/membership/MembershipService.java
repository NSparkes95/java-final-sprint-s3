// MembershipService.java
package org.keyin.membership;

import java.util.List;

/**
 * Service class for handling high-level membership-related operations.
 * Acts as a layer between the application logic and MembershipDAO implementation.
 */
public class MembershipService {

    private MembershipDAO membershipDAO;

    /**
     * Constructs a MembershipService with the provided DAO implementation.
     * @param membershipDAO the DAO to use for membership data access
     */
    public MembershipService(MembershipDAO membershipDAO) {
        this.membershipDAO = membershipDAO;
    }

    /**
     * Adds a new membership to the system. Typically used by trainers or members.
     * @param membership the Membership object containing membership details
     */
    public void buyMembership(Membership membership) {
        membershipDAO.addMembership(membership);
    }

    /**
     * Retrieves all memberships in the system. Typically used by admin users.
     * @return a list of all Membership objects
     */
    public List<Membership> getAllMemberships() {
        return membershipDAO.getAllMemberships();
    }

    /**
     * Retrieves memberships belonging to a specific member.
     * Used by trainers or members to view a user's memberships.
     * @param memberId the ID of the user
     * @return a list of Memberships for that user
     */
    public List<Membership> getMembershipsByMemberId(int memberId) {
        return membershipDAO.getMembershipsByMemberId(memberId);
    }

    /**
     * Calculates the total revenue from all memberships in the system.
     * Typically used by admin users.
     * @return the total revenue as a double
     */
    public double getTotalRevenue() {
        return membershipDAO.getTotalRevenue();
    }
} 
