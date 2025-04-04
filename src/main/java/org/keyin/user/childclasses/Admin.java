package org.keyin.user.child_classes;

import org.keyin.membership.Membership;
import org.keyin.membership.MembershipService;
import org.keyin.user.User;
import org.keyin.user.UserService;
import org.keyin.workoutclasses.WorkoutClass;
import org.keyin.workoutclasses.WorkoutClassService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {
    public Admin(String username, String password, String email, String phone, String address) {
        super(username, password, email, phone, address, "admin");
    }

    public void showAdminMenu(Scanner scanner, User user, UserService userService, MembershipService membershipService, WorkoutClassService workoutService) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== 🛠️Admin Menu ===");
            System.out.println("1. 👤User Management");
            System.out.println("2. 💳Membership Management");
            System.out.println("3. 🏋️Workout Class Management");
            System.out.println("4. 🧑‍🏫Trainer Management");
            System.out.println("5. 💰View Total Revenue");
            System.out.println("0.🔙Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case"1":
                    GymApp.showAdminUserMenu(scanner, userService);
                    break;
                case "2":
                    GymApp.showAdminMembershipMenu(scanner, membershipService);
                    break;
                case "3":
                    showAdminWorkoutClassMenu(scanner, workoutService);
                    break;
                case "4":
                    GymApp.showAdminTrainerMenu(scanner, userService);
                    break;
                case "5":
                    try {
                        double totalRevenue = membershipService.getTotalRevenue();
                        System.out.println("💰 Total Revenue from Memberships: $" + total);
                    }   catch (SQLException e) {
                        System.out.println("❌ Error retrieving total revenue: " + e.getMessage());
                    }
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("❌Invalid option. Try again.");
            }
        }
    }

    /**
    * Displays the Admin > User Management submenu, allowing the admin
    * to add, view, update, or delete users.
    *
    * @param scanner Scanner object for user input
    * @param userService Service used to handle user-related database actions
    */

    public void showAdminUserMenu (Scanner scanner, UserService userService) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== 👤Admin User Management Menu ===");
            System.out.println("1. Add New User");
            System.out.println("2. View All Users");
            System.out.println("3. Delete User");
            System.out.println("4. Update User");
            System.out.println("5. View User Details");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": 
                    addNewUser(scanner, userService);
                    break;
                case "2":
                    try {
                        List<User> users = userService.getAllUsers();
                        System.out.println("\n📋 All Users:");
                        for (User u : users) {
                            System.out.println(u);
                        }
                    } catch (SQLException e) {
                        System.out.println("❌ Error retrieving users: " + e.getMessage());
                    }
                    break;
                case "3":
                    deleteUser(scanner, userService);
                    break;
                case "4":
                    updateUser(scanner, userService);
                    break;
                case "5":
                    System.out.print("Enter user ID to view details: ");
                    int userIdToView = Integer.parseInt(scanner.nextLine());
                    try {
                        User user = userService.getUserById(userIdToView);
                        if (user != null) {
                            System.out.println("\n📋 User Details:");
                            System.out.println(user);
                        } else {
                            System.out.println("❌ User not found.");
                        }
                    } catch (SQLException e) {
                        System.out.println("❌ Error retrieving user details: " + e.getMessage());
                    }
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    /**
    * Displays the Admin > Membership Management submenu,
    * allowing the admin to view all memberships and check total revenue.
    *
    * @param scanner Scanner for admin input
    * @param membershipService Service for accessing membership records and revenue
    */
    public void showAdminMembershipMenu(Scanner scanner, MembershipService membershipService) {
        boolean managingMemberships = true;

        while (running) {
            System.out.println("\n=== 💳Admin Membership Management Menu ===");
            System.out.println("1. View All Memberships");
            System.out.println("2. Check Total Revenue");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewAllMemberships(scanner, membershipService);
                    break;
                case "2":
                    try {
                        double totalRevenue = membershipService.getTotalRevenue();
                        System.out.println("💰 Total Revenue from Memberships: $" + totalRevenue);
                    } catch (SQLException e) {
                        System.out.println("❌ Error retrieving total revenue: " + e.getMessage());
                    }
                    break;
                case "0":
                    managingMemberships = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    /**
    * Displays the Admin > Trainer Management submenu.
    * Allows the admin to view, add, update, or delete trainers.
    *
    * @param scanner Scanner for admin input
    * @param userService Service for accessing user data (trainer accounts)
    */
    public void showAdminTrainerMenu(Scanner scanner, UserService userService) {
        boolean managingTrainers = true;

        while (managingTrainers) {
            System.out.println("\n=== 🧑‍🏫Admin Trainer Management Menu ===");
            System.out.println("1. View All Trainers");
            System.out.println("2. Add New Trainer");
            System.out.println("3. Update Trainer Information");
            System.out.println("4. Delete Trainer");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine(); 

            switch (choice) {
                case "1":
                    viewAllTrainers(userService);
                    break;
                case "2":
                    addNewTrainer(scanner, userService);
                    break;
                case "3":
                    updateTrainer(scanner, userService);
                    break;
                case "4":
                    deleteTrainer(scanner, userService);
                    break;
                case "0":
                    managingTrainers = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    /**
    * Updates an existing user by prompting the admin for user ID and new values.
    * The admin can update the username, email, password, and role.
    *
    * @param scanner Scanner for capturing admin input
    * @param userService Service for handling user-related database operations
    */
    public void updateUser(Scanner scanner, UserService userService) {
        System.out.print("Enter user ID to update: ");
        int userId = Integer.parseInt(scanner.nextLine());

        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                System.out.println("❌ User not found.");
                return;
            }

            System.out.print("Enter new username (or press Enter to keep current): ");
            String newUsername = scanner.nextLine();
            if (!newUsername.isEmpty() && !userService.isUsernameTaken(newUsername)) {
                user.setUserName(newUsername);
            }

            System.out.print("Enter new email (or press Enter to keep current): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty() && !userService.isEmailTaken(newEmail)) {
                user.setEmail(newEmail);
            }

            System.out.print("Enter new password (or press Enter to keep current): ");
            String newPassword = scanner.nextLine();
            if (!newPassword.isEmpty()) {
                user.setPassword(newPassword);
            }

            System.out.print("Enter new role (or press Enter to keep current): ");
            String newRole = scanner.nextLine();
            if (!newRole.isEmpty()) {
                user.setUserRole(newRole);
            }

            userService.updateUser(user);
            System.out.println("✅ User updated successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Error updating user: " + e.getMessage());
        }
    }/**
    * Prompts the admin to delete a user by entering their ID.
    * Confirms existence before deletion to prevent accidental removal.
    *
    * @param scanner Scanner for capturing admin input
    * @param userService Service used to access and modify user data
    */
    public void deleteUser(Scanner scanner, UserService userService) {
        System.out.print("Enter user ID to delete: ");
        int userId = Integer.parseInt(scanner.nextLine());

        try{
            User user = userService.getUserById(userId);
            if (user == null) {
                System.out.println("❌ User not found.");
                return;
            }

            System.out.print("Are you sure you want to delete this user? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                userService.deleteUser(userId);
                System.out.println("✅ User deleted successfully.");
            } else {
                System.out.println("❌ User deletion cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting user: " + e.getMessage());
        }
    }
    

    /**
    * Retrieves and displays all memberships stored in the system.
    *
    * @param membershipService Service used to fetch membership data
    */
    public void viewAllMemberships(MembershipService membershipService) {
        try {
            List<Membership> memberships = membershipService.getAllMemberships();
            if (memberships.isEmpty()) {
                System.out.println("No memberships found.");
            } else {
                System.out.println("\n📋 All Memberships:");
                for (Membership m : memberships) {
                    System.out.println(m);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving memberships: " + e.getMessage());
        }
    }

    /**
    * Displays the Admin > Workout Class Management submenu,
    * allowing the admin to view and add workout classes.
    *
    * @param scanner Scanner for admin input
    * @param workoutService Service for accessing workout class data
    */
    public void showAdminWorkoutClassMenu(Scanner scanner, WorkoutClassService workoutService) {
        boolean managingClasses = true;

        while (managingClasses) {
            System.out.println("\n=== 🏋️Admin Workout Class Management Menu ===");
            System.out.println("1. View All Classes");
            System.out.println("2. Add New Class");
            System.out.println("3. Update Class");
            System.out.println("4. Delete Class");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewAllWorkoutClasses(workoutService);
                    break;
                case "2":
                    addNewWorkoutClass(scanner, workoutService);
                    break;
                case "3":
                    updateWorkoutClass(scanner, workoutService);
                    break;
                case "4":
                    deleteWorkoutClass(scanner, workoutService);
                    break;
                case "0":
                    managingClasses = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    /**
    * Displays a list of all workout classes currently in the system.
    *
    * @param workoutService Service used to fetch workout class data
    */
    public void viewAllWorkoutClasses(WorkoutClassService workoutService) {
        try {
            List<WorkoutClass> classes = workoutService.getAllWorkoutClasses();
            if (classes.isEmpty()) {
                System.out.println("No workout classes found.");
            } else {
                System.out.println("\n📋 All Workout Classes:");
                for (WorkoutClass wc : classes) {
                    System.out.println(wc);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving workout classes: " + e.getMessage());
        }
    }

    /**
    * Displays all users with the role of 'Trainer'.
    *
    * @param userService Service used to fetch user data
    */
    public void viewAllTrainers(UserService userService) {
        try {
            List<User> trainers = userService.getAllTrainers();
            if (trainers.isEmpty()) {
                System.out.println("No trainers found.");
            } else {
                System.out.println("\n📋 All Trainers:");
                for (User t : trainers) {
                    System.out.println(t);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving trainers: " + e.getMessage());
        }
    }

    /**
    * Prompts the admin to create a new trainer account.
    * Validates for duplicate username/email and password strength.
    *
    * @param scanner Scanner for input
    * @param userService Service used to create the trainer
    */
    public void addNewTrainer(Scanner scanner, UserService userService) {
        System.out.print("Enter trainer username: ");
        String username = scanner.nextLine();
        // Check if username already exists
        if (userService.isUsernameTaken(username)) {
            System.out.println("❌ Username already exists. Please choose a different one.");
            return;
        }
        System.out.print("Enter trainer email: ");
        String email = scanner.nextLine();
        // Check if email already exists
        if (userService.isEmailTaken(email)) {
            System.out.println("❌ Email already exists. Please choose a different one.");
            return;
        }
        System.out.print("Enter trainer password: ");
        String password = scanner.nextLine();
        // Check password strength
        if (password.length() < 8 || !password.matches(".*\\d.*") ||
        !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
        System.out.println("❌ Password must be at least 8 characters, include a digit and a special character.");
        return;
        }

        User trainer = new User(username, password, "Trainer");
        trainer.setEmail(email);
        try {
            userService.registerUser(trainer);
            System.out.println("Trainer added successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error adding trainer: " + e.getMessage());
        }
    }

    /**
    * Allows the admin to update trainer information by user ID.
    *
    * @param scanner Scanner for input
    * @param userService Service used to update trainer data
    */
    public void updateTrainer(Scanner scanner, UserService, userService) {
        System.out.print("Enter trainer ID to update: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        try {
            User trainer = userService.getUserById(trainerId);
            if (trainer == null) {
                System.out.println("❌ Trainer not found.");
                return;
            }

            System.out.print("Enter new username (or press Enter to keep '" + trainer.getUserName() + "'): ");
            String newUsername = scanner.nextLine();
            if (!newUsername.isEmpty()) {
                trainer.setUserName(newUsername);
            }

            System.out.print("Enter new email (or press Enter to keep '" + trainer.getEmail() + "'): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) {
                trainer.setEmail(newEmail);
            }

            System.out.print("Enter new password (or press Enter to keep current): ");
            String newPassword = scanner.nextLine();
            if (!newPassword.isEmpty()) {
                trainer.setPassword(newPassword);
            }

            userService.updateUser(trainer);
            System.out.println("✅ Trainer updated successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Error updating trainer: " + e.getMessage());
        }
    }
    /**
    * Deletes a trainer account by ID after confirmation.
    *
    * @param scanner Scanner to capture input
    * @param userService Service used to access/delete trainer account
    */
    public void deleteTrainer(Scanner scanner, UserService userService) {
        System.out.print("Enter trainer ID to delete: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        try {
            User trainer = userService.getUserById(trainerId);
            if (trainer == null) || !trainer.getUserRole().equalsIgnoreCase("Trainer") {
                System.out.println("❌ Trainer not found.");
                return;
            }

            System.out.print("Are you sure you want to delete this trainer? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                userService.deleteUser(trainerId);
                System.out.println("✅ Trainer deleted successfully.");
            } else {
                System.out.println("❌ Trainer deletion cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting trainer: " + e.getMessage());
        }
    }
}
