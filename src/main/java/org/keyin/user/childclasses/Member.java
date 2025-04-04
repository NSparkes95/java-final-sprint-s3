package org.keyin.user.child_classes;

import org.keyin.membership.Membership;
import org.keyin.membership.MembershipService;
import org.keyin.user.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Member extends User {
    public Member(String username, String password, String email, String phone, String address) {
        super(username, password, email, phone, address, "member");
    }

    /**
     * Displays the member menu for purchasing and viewing memberships.
     *
     * @param scanner Scanner for user input
     * @param membershipService Service for managing memberships
     */
    public void showMemberMenu(Scanner scanner, User user, MembershipService membershipService) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Member Menu ===");
            System.out.println("1. Buy Membership");
            System.out.println("2. View My Memberships");
            System.out.println("3. Check My Expenses");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    try {
                        System.out.print("Enter membership type: ");
                        String type = scanner.nextLine();
                        System.out.print("Enter description: ");
                        String desc = scanner.nextLine();
                        System.out.print("Enter cost: ");
                        double cost = Double.parseDouble(scanner.nextLine());
                        LocalDate start = LocalDate.now();
                        LocalDate end = start.plusMonths(1); // default 1 month

                        Membership membership = new Membership(type, desc, cost, user.getUserId(), start, end);
                        membershipService.buyMembership(membership);
                        System.out.println("✅ Membership purchased successfully.");
                    } catch (Exception e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        List<Membership> memberships = membershipService.getMembershipsByUserId(user.getUserId());
                        System.out.println("\n📄 Your Memberships:");
                        for (Membership m : memberships) {
                            System.out.println(m);
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                    break;

                case "3":
                    try {
                        List<Membership> memberships = membershipService.getMembershipsByUserId(user.getUserId());
                        double total = memberships.stream().mapToDouble(Membership::getMembershipCost).sum();
                        System.out.println("💰 Total spent on memberships: $" + total);
                    } catch (Exception e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void buyMembership(Scanner scanner, MembershipService membershipService) {
        try {
            System.out.print("Enter membership type: ");
            String type = scanner.nextLine();

            System.out.print("Enter description: ");
            String desc = scanner.nextLine();

            System.out.print("Enter cost: ");
            double cost = Double.parseDouble(scanner.nextLine());

            LocalDate start = LocalDate.now();
            LocalDate end = start.plusMonths(1); // default 1 month

            Membership membership = new Membership(type, desc, cost, this.getUserId(), start, end);
            membershipService.buyMembership(membership);
            System.out.println("✅ Membership purchased successfully.");
        } catch (Exception e) {
            System.out.println("❌ Error purchasing membership: " + e.getMessage());
        }
    }

    private void viewMemberships(MembershipService membershipService) {
        try {
            List<Membership> memberships = membershipService.getMembershipsByUserId(this.getUserId());
            System.out.println("\n📄 Your Memberships:");
            for (Membership m : memberships) {
                System.out.println(m);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving memberships: " + e.getMessage());
        }
    }

    private void checkExpenses(MembershipService membershipService) {
        try {
            List<Membership> memberships = membershipService.getMembershipsByUserId(this.getUserId());
            double total = memberships.stream().mapToDouble(Membership::getMembershipCost).sum();
            System.out.println("💰 Total spent on memberships: $" + total);
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving expenses: " + e.getMessage());
        }
    }
}
