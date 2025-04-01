package org.keyin.membership;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class to handle membership-related logic and use MembershipDAO to interact with the database.
 */
public class MembershipService {

    private final MembershipDAO membershipDAO;

    /**
     * Constructor to inject the DAO (implementation).
     *
     * @param membershipDAO The DAO implementation to use.
     */
    public MembershipService(MembershipDAO membershipDAO) {
        this.membershipDAO = membershipDAO;
    }

    /**
     * Purchase a membership (insert into the database).
     *
     * @param membership The membership to be added.
     */
    public void buyMembership(Membership membership) throws SQLException {
        membershipDAO.insertMembership(membership);
    }

   /**
     * Get all memberships linked to a specific user.
     *
     * @param userId The user's ID (member or trainer).
     * @return List of memberships.
     */
    public List<Membership> getMembershipsByUserId(int userId) throws SQLException {
        return membershipDAO.getMembershipsByMemberId(userId);
    }

    /**
    * Get all memberships in the system (for admin use).
    *
    * @return List of all memberships.
    */
    public List<Membership> getAllMemberships() throws SQLException {
       return membershipDAO.getAllMemberships();
    }

    /**
     * Get the total revenue generated from all memberships.
     *
     * @return The total revenue value.
     */
    public double getTotalRevenue() throws SQLException {
        return membershipDAO.getTotalMembershipRevenue();
    }
}
