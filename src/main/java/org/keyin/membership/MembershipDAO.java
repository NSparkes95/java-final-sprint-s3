package org.keyin.membership;

import java.sql.SQLException;
import java.util.List;

/**
 * MembershipDAO defines the contract for interacting with the memberships table.
 * Provides methods to perform CRUD operations and revenue calculations.
 */
public interface MembershipDAO {

    /**
     * Inserts a new membership record into the database.
     *
     * @param membership The membership object to insert.
     * @throws SQLException If a database error occurs.
     */
    void insertMembership(Membership membership) throws SQLException;

    /**
     * Fetches a membership by its unique ID.
     *
     * @param membershipId The ID of the membership to retrieve.
     * @return The Membership object, or null if not found.
     * @throws SQLException If a database error occurs.
     */
    Membership getMembershipById(int membershipId) throws SQLException;

    /**
     * Retrieves all memberships from the database.
     *
     * @return A list of all Membership objects.
     * @throws SQLException If a database error occurs.
     */
    List<Membership> getAllMemberships() throws SQLException;

    /**
     * Updates an existing membership record in the database.
     *
     * @param membership The membership object with updated values.
     * @throws SQLException If a database error occurs.
     */
    void updateMembership(Membership membership) throws SQLException;

    /**
     * Deletes a membership by its ID.
     *
     * @param membershipId The ID of the membership to delete.
     * @throws SQLException If a database error occurs.
     */
    void deleteMembership(int membershipId) throws SQLException;

    /**
     * Retrieves all memberships linked to a specific member.
     *
     * @param memberId The ID of the member.
     * @return A list of Memberships for that user.
     * @throws SQLException If a database error occurs.
     */
    List<Membership> getMembershipsByMemberId(int memberId) throws SQLException;

    /**
     * Calculates the total revenue from all active (non-on-hold) memberships.
     *
     * @return The total revenue as a double.
     * @throws SQLException If a database access error occurs.
     */
    double getTotalMembershipRevenue() throws SQLException;
}
