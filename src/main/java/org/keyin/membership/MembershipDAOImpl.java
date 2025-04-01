package org.keyin.membership;

import org.keyin.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MembershipDAOImpl implements the MembershipDAO interface.
 * Handles database operations for the Membership table using JDBC.
 */
public class MembershipDAOImpl implements MembershipDAO {

    /**
     * Inserts a new membership into the database.
     *
     * @param membership The membership object to insert.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void insertMembership(Membership membership) throws SQLException {
        String sql = "INSERT INTO memberships (membershiptype, membershipdescription, membershipcost, memberid, startdate, enddate, isonhold) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, membership.getMembershipType());
            pstmt.setString(2, membership.getMembershipDescription());
            pstmt.setDouble(3, membership.getMembershipCost());
            pstmt.setInt(4, membership.getMemberId());
            pstmt.setDate(5, Date.valueOf(membership.getStartDate()));
            pstmt.setDate(6, Date.valueOf(membership.getEndDate()));
            pstmt.setBoolean(7, membership.isOnHold());

            pstmt.executeUpdate();
        }
    }

    /**
     * Retrieves a membership by its ID.
     *
     * @param membershipId The ID of the membership to retrieve.
     * @return The matching Membership object, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public Membership getMembershipById(int membershipId) throws SQLException {
        String sql = "SELECT * FROM memberships WHERE membershipid = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, membershipId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToMembership(rs);
            }
        }

        return null;
    }

    /**
     * Returns a list of all memberships in the database.
     *
     * @return A list of Membership objects.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<Membership> getAllMemberships() throws SQLException {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                memberships.add(mapResultSetToMembership(rs));
            }
        }

        return memberships;
    }

    /**
     * Updates an existing membership record.
     *
     * @param membership The membership object with updated fields.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void updateMembership(Membership membership) throws SQLException {
        String sql = "UPDATE memberships SET membershiptype=?, membershipdescription=?, membershipcost=?, memberid=?, startdate=?, enddate=?, isonhold=? WHERE membershipid=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, membership.getMembershipType());
            pstmt.setString(2, membership.getMembershipDescription());
            pstmt.setDouble(3, membership.getMembershipCost());
            pstmt.setInt(4, membership.getMemberId());
            pstmt.setDate(5, Date.valueOf(membership.getStartDate()));
            pstmt.setDate(6, Date.valueOf(membership.getEndDate()));
            pstmt.setBoolean(7, membership.isOnHold());
            pstmt.setInt(8, membership.getMembershipId());

            pstmt.executeUpdate();
        }
    }

    /**
     * Deletes a membership by its ID.
     *
     * @param membershipId The ID of the membership to delete.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void deleteMembership(int membershipId) throws SQLException {
        String sql = "DELETE FROM memberships WHERE membershipid = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, membershipId);
            pstmt.executeUpdate();
        }
    }

    /**
     * Returns all memberships assigned to a specific member.
     *
     * @param memberId The ID of the user.
     * @return A list of that user's memberships.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<Membership> getMembershipsByMemberId(int memberId) throws SQLException {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships WHERE memberid = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                memberships.add(mapResultSetToMembership(rs));
            }
        }

        return memberships;
    }

    /**
     * Calculates the total revenue from all memberships that are not on hold.
     *
     * @return Total revenue as a double.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public double getTotalMembershipRevenue() throws SQLException {
        String sql = "SELECT COALESCE(SUM(membershipcost), 0) AS total FROM memberships WHERE isonhold = FALSE";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("total");
            }
        }

        return 0.0;
    }

    /**
     * Helper method to convert a ResultSet row to a Membership object.
     *
     * @param rs The result set from the query.
     * @return A populated Membership object.
     * @throws SQLException If column mapping fails.
     */
    private Membership mapResultSetToMembership(ResultSet rs) throws SQLException {
        return new Membership(
                rs.getInt("membershipid"),
                rs.getString("membershiptype"),
                rs.getString("membershipdescription"),
                rs.getDouble("membershipcost"),
                rs.getInt("memberid"),
                rs.getDate("startdate").toLocalDate(),
                rs.getDate("enddate").toLocalDate(),
                rs.getBoolean("isonhold")
        );
    }
}
