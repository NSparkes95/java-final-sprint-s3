package org.keyin.user.childclasses;

import org.keyin.membership.MembershipService;
import org.keyin.user.User;
import org.keyin.user.UserService;
import org.keyin.workoutclasses.WorkoutClassService;

import java.util.Scanner;
import java.sql.SQLException;

public class Admin extends User {
    // Constructor should properly initialize the User fields
    public Admin(String username, String password, String email, String phone, String address) {
        super(username, password, email, phone, address, "Admin");
    }

    // Admin menu for showing different options
    public void showAdminMenu(Scanner scanner, UserService userService, MembershipService membershipService, WorkoutClassService workoutService) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== 🛠️Admin Menu ===");
            System.out.println("1. User Management");
            System.out.println("2. Membership Management");
            System.out.println("3. Workout Class Management");
            System.out.println("4. Trainer Management");
            System.out.println("5. View Revenue");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    userService.showUserManagementMenu(scanner);
                    break;
                case "2":
                    membershipService.showMembershipManagementMenu(scanner);
                    break;
                case "3":
                    workoutService.showWorkoutClassManagementMenu(scanner);
                    break;
                case "4":
                    userService.showTrainerManagementMenu(scanner);
                    break;
                case "5":
                    try {
                        System.out.println("Total Revenue: $" + membershipService.getTotalRevenue());
                    } catch (SQLException e) {
                        System.out.println("Error retrieving revenue: " + e.getMessage());
                    }
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
