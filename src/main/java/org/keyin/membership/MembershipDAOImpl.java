package org.keyin.membership;

import org.keyin.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipDAOImpl implements MembershipDAO {

    @Override
    public void addMembership(Membership membership) {
        String sql = "INSERT INTO memberships (membership_type, membership_description, membership_cost, member_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, membership.getMembershipType());
            stmt.setString(2, membership.getMembershipDescription());
            stmt.setDouble(3, membership.getMembershipCost());
            stmt.setInt(4, membership.getMemberId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding membership: " + e.getMessage());
        }
    }

    @Override
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                memberships.add(buildMembershipFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving memberships: " + e.getMessage());
        }

        return memberships;
    }

    @Override
    public List<Membership> getMembershipsByMemberId(int memberId) {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships WHERE member_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                memberships.add(buildMembershipFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving memberships by member ID: " + e.getMessage());
        }

        return memberships;
    }

    @Override
    public double getTotalRevenue() {
        double total = 0;
        String sql = "SELECT SUM(membership_cost) AS total FROM memberships";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("Error calculating revenue: " + e.getMessage());
        }

        return total;
    }

    private Membership buildMembershipFromResultSet(ResultSet rs) throws SQLException {
        return new Membership(
                rs.getInt("membership_id"),
                rs.getString("membership_type"),
                rs.getString("membership_description"),
                rs.getDouble("membership_cost"),
                rs.getInt("member_id")
        );
    }
}
