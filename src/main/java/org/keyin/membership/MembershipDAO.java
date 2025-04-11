// MembershipDAO.java
package org.keyin.membership;

import java.util.List;

/**
 * Interface defining data access methods for Membership operations.
 * Includes methods for adding memberships, retrieving by member ID,
 * and calculating total revenue.
 */
public interface MembershipDAO {

    /**
     * Adds a new membership to the database.
     * @param membership the Membership object to add
     */
    void addMembership(Membership membership);

    /**
     * Retrieves all memberships in the system.
     * @return a list of all Membership objects
     */
    List<Membership> getAllMemberships();

    /**
     * Retrieves all memberships associated with a specific member.
     * @param memberId the ID of the member
     * @return a list of memberships tied to the given member ID
     */
    List<Membership> getMembershipsByMemberId(int memberId);

    /**
     * Calculates total revenue generated from all memberships.
     * @return total revenue as a double
     */
    double getTotalRevenue();
} 
