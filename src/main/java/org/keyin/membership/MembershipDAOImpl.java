package org.keyin.membership;

import org.keyin.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements MembershipDAO and handles database operations using JDBC.
 */
public class MembershipDAOImpl implements MembershipDAO {

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

    @Override
    public void deleteMembership(int membershipId) throws SQLException {
        String sql = "DELETE FROM memberships WHERE membershipid = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, membershipId);
            pstmt.executeUpdate();
        }
    }

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

    @Override
    public double getTotalMembershipRevenue() throws SQLException {
        String sql = "SELECT (SUM(membership_cost), 0) AS total FROM memberships WHERE is_on_hold = FALSE";

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
     * Helper method to convert ResultSet row to Membership object.
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
