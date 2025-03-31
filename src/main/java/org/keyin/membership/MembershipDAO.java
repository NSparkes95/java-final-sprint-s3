package org.keyin.membership;

import java.sql.SQLException;
import java.util.List;

/**
 * MembershipDAO defines the contract for interacting with the memberships table.
 */
public interface MembershipDAO {

    /**
     * Inserts a new membership into the database.
     */
    void insertMembership(Membership membership) throws SQLException;

    /**
     * Fetches a membership by its ID.
     */
    Membership getMembershipById(int membershipId) throws SQLException;

    /**
     * Returns a list of all memberships.
     */
    List<Membership> getAllMemberships() throws SQLException;

    /**
     * Updates an existing membership.
     */
    void updateMembership(Membership membership) throws SQLException;

    /**
     * Deletes a membership by its ID.
     */
    void deleteMembership(int membershipId) throws SQLException;

    /**
     * Returns all memberships for a specific member.
     */
    List<Membership> getMembershipsByMemberId(int memberId) throws SQLException;

    /**
     * Calculates the total revenue from all memberships.
     */
    double getTotalMembershipRevenue() throws SQLException;
}
