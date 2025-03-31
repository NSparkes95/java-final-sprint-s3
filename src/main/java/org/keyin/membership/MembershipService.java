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
     * Get all memberships for a specific user (member/trainer).
     *
     * @param userId The ID of the user.
     * @return List of memberships for the user.
     */
    public List<Membership> getMembershipsByUserId(int userId) throws SQLException {
        return membershipDAO.getMembershipsByMemberId(userId);
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
