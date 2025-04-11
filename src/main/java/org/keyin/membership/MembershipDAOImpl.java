// MembershipDAOImpl.java
package org.keyin.membership;

import org.keyin.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the MembershipDAO interface.
 * Handles membership-related operations such as inserting, retrieving,
 * and calculating revenue from the database.
 */
public class MembershipDAOImpl implements MembershipDAO {

    /**
     * Adds a new membership to the database.
     * @param membership the Membership object to insert
     */
    @Override
    public void addMembership(Membership membership) {
        String sql = "INSERT INTO memberships (membership_type, membership_description, membership_cost, member_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, membership.getMembershipType());
            pstmt.setString(2, membership.getMembershipDescription());
            pstmt.setDouble(3, membership.getMembershipCost());
            pstmt.setInt(4, membership.getMemberId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all memberships in the database.
     * @return a list of all Membership objects
     */
    @Override
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                memberships.add(new Membership(
                        rs.getInt("membership_id"),
                        rs.getString("membership_type"),
                        rs.getString("membership_description"),
                        rs.getDouble("membership_cost"),
                        rs.getInt("member_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberships;
    }

    /**
     * Retrieves memberships associated with a specific member.
     * @param memberId the ID of the member
     * @return a list of memberships belonging to the member
     */
    @Override
    public List<Membership> getMembershipsByMemberId(int memberId) {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships WHERE member_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                memberships.add(new Membership(
                        rs.getInt("membership_id"),
                        rs.getString("membership_type"),
                        rs.getString("membership_description"),
                        rs.getDouble("membership_cost"),
                        rs.getInt("member_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberships;
    }

    /**
     * Calculates the total revenue earned from all memberships.
     * @return the sum of membership costs as a double
     */
    @Override
    public double getTotalRevenue() {
        double totalRevenue = 0;
        String sql = "SELECT SUM(membership_cost) AS total FROM memberships";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                totalRevenue = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRevenue;
    }
} 
