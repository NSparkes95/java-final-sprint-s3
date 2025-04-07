package org.keyin.user.childclasses;

import org.keyin.membership.Membership;
import org.keyin.membership.MembershipService;
import org.keyin.user.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Member class representing users with the 'member' role.
 * Members can purchase memberships, view their memberships, and track expenses.
 */
public class Member extends User {

    /**
     * Constructs a Member with extended user information.
     */
    public Member(String username, String password, String firstName, String lastName, String phone, String role) {
        // Use the constructor from User that matches the parameters
        super(username, password, firstName, lastName, phone, role);
    }

    /**
     * Allows the member to buy a new membership.
     */
    public void buyMembership(Scanner scanner, MembershipService membershipService) {
        try {
            System.out.print("Enter membership type (e.g., 'basic', 'premium'): ");
            String membershipType = scanner.nextLine().trim().toLowerCase();

            System.out.print("Enter membership duration in months: ");
            int duration = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter membership start date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter membership expiry date (YYYY-MM-DD): ");
            LocalDate expiryDate = LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter membership price: ");
            double price = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter membership status (e.g., 'active', 'inactive'): ");
            String status = scanner.nextLine().trim().toLowerCase();

            System.out.print("Enter payment method (Credit/ Debit/ Cash): ");
            String paymentMethod = scanner.nextLine().trim().toLowerCase();

            Membership membership = new Membership(
                    membershipType,
                    "Duration: " + duration + " months",
                    price,
                    getUserId(),
                    startDate,
                    expiryDate,
                    paymentMethod,
                    status
            );

            membershipService.buyMembership(membership);
            System.out.println("✅ Membership successfully created and saved!");
        } catch (Exception e) {
            System.out.println("❌ Error during membership creation: " + e.getMessage());
        }
    }

    /**
     * Displays all memberships purchased by the member.
     */
    public void viewMyMemberships(MembershipService membershipService) {
        try {
            List<Membership> memberships = membershipService.getMembershipsByUserId(getUserId());
            if (memberships.isEmpty()) {
                System.out.println("No memberships found for this user.");
            } else {
                System.out.println("=== My Memberships ===");
                for (Membership membership : memberships) {
                    System.out.println(membership);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving memberships: " + e.getMessage());
        }
    }

    /**
     * Calculates and shows the total amount spent on memberships.
     */
    public void checkMyExpenses(MembershipService membershipService) {
        try {
            List<Membership> memberships = membershipService.getMembershipsByUserId(getUserId());
            double total = memberships.stream().mapToDouble(Membership::getMembershipCost).sum();
            System.out.println("💰 Total spent on memberships: $" + total);
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving expenses: " + e.getMessage());
        }
    }
}
