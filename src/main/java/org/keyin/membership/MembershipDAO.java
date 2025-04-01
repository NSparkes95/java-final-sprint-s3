package org.keyin.membership;

import java.sql.SQLException;
import java.util.List;

/**
 * MembershipDAO defines the contract for interacting with the memberships table.
 */
public interface MembershipDAO {

    /**
     * Inserts a new membership record into the database.
     * @param membership The membership object to insert.
     * @throws SQLException if a database error occurs.
     */
    void insertMembership(Membership membership) throws SQLException;

    /**
     * Fetches a membership by its ID.
     */
    Membership getMembershipById(int membershipId) throws SQLException;

    /**
     * Retrieves a list of all memberships in the database.
     * @return List of Membership objects.
     * @throws SQLException if a database error occurs.
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
     * Retrieves all memberships assigned to a specific member.
     * @param memberId the ID of the member
     * @return list of Memberships
     */
    List<Membership> getMembershipsByMemberId(int memberId) throws SQLException;
    /**
     * Calculates the total revenue from all active (not on-hold) memberships.
     * @return Total revenue as a double.
     * @throws SQLException if database access fails.
     */
    double getTotalMembershipRevenue() throws SQLException;
}
