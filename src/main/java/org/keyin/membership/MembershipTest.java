package org.keyin.membership;

import java.sql.SQLException;
import java.util.List;

public class MembershipTest {
    public static void main(String[] args) {
        // Connect your service to your DAO
        MembershipService membershipService = new MembershipService(new MembershipDAOImpl());

        try {
            System.out.println("📋 All memberships in the system:");
            List<Membership> memberships = membershipService.getAllMemberships();
            for (Membership m : memberships) {
                System.out.println(m);
            }

            System.out.println("\n💰 Total Revenue:");
            double revenue = membershipService.getTotalRevenue();
            System.out.println("$" + revenue);

        } catch (SQLException e) {
            System.err.println("❌ Error while testing: " + e.getMessage());
        }
    }
}
