package org.keyin.user.child_classes;

import org.keyin.membership.MembershipService;
import org.keyin.user.User;
import org.keyin.workoutclasses.WorkoutClass;
import org.keyin.workoutclasses.WorkoutClassService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * Trainer class extends User and encapsulates trainer-specific menu functionality.
 */
public class Trainer extends User {
    public Trainer(String username, String password, String email, String phone, String address) {
        super(username, password, email, phone, address, "trainer");
    }

    /**
    * Displays the trainer-specific menu, allowing trainers to manage their assigned classes.
    *
    * @param scanner Scanner for user input
    * @param user The currently logged-in trainer
    * @param membershipService Not used by trainers, but included for consistency
    * @param workoutService Service for managing workout classes
    */
    public void showTrainerMenu(Scanner scanner, User user, MembershipService membershipService, WorkoutClassService workoutService) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Trainer Menu ===");
            System.out.println("1. View My Upcoming Classes");
            System.out.println("2. Add New Class");
            System.out.println("3. Update Class");
            System.out.println("4. Delete Class");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewTrainerClasses(this.getUserId(), workoutService);
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
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
    * Displays all workout classes created by the logged-in trainer.
    *
    * @param trainerId The ID of the logged-in trainer
    * @param workoutService Service to retrieve the trainer's classes
    */
    public void viewTrainerClasses(int trainerId, WorkoutClassService workoutService) {
        try {
            List<WorkoutClass> classes = workoutService.getClassesByTrainerId(trainerId);
            if (classes.isEmpty()) {
                System.out.println("No classes found for this trainer.");
            } else {
                System.out.println("\n📋 Your Upcoming Classes:");
                for (WorkoutClass wc : classes) {
                    System.out.println(wc);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving classes: " + e.getMessage());
        }
    }

    /**
    * Prompts the user to enter workout class details and adds the new class.
    *
    * @param scanner Scanner for user input
    * @param service WorkoutClassService for DAO access
    */
    public void addNewWorkoutClass(Scanner scanner, WorkoutClassService workoutService) {
        try {
            System.out.println("\n=== Add New Workout Class ===");

            System.out.print("Enter class name: ");
            String name = scanner.nextLine();

            System.out.print("Enter class description: ");
            String description = scanner.nextLine();

            System.out.print("Trainer ID: ");
            int trainerId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter class level (Beginner/Intermediate/Advanced): ");
            String level = scanner.nextLine();

            System.out.print("Enter class duration (in minutes): ");
            int duration = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter class capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter class date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter class time (HH:MM): ");
            LocalTime time = LocalTime.parse(scanner.nextLine());

            System.out.print("Enter class location: ");
            String location = scanner.nextLine();

            System.out.print("Enter equipment needed (or 'None'): ");
            String equipment = scanner.nextLine();

            // Create a new WorkoutClass object
            WorkoutClass workoutClass = new WorkoutClass(
                0, name, trainerId, level, description, 
                duration, capacity, date, time, location, equipment
            );

            workoutService.addWorkoutClass(workoutClass);
            System.out.println("Workout class added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding workout class: " + e.getMessage());
        }
    }

    /**
    * Allows the admin to update details of an existing workout class by ID.
    * Admin can skip any field by pressing Enter to retain the current value.
    *
    * @param scanner Scanner for capturing admin input
    * @param workoutService Service used to access and update class data
    */
    public void updateWorkoutClass(Scanner scanner, WorkoutClassService workoutService){
        try {
            System.out.print("Enter class ID to update: ");
            int classId = Integer.parseInt(scanner.nextLine());

            WorkoutClass existingClass = workoutService.getWorkoutClassById(classId);
            if (existingClass == null) {
                System.out.println("❌ Class not found.");
                return;
            }

            System.out.print("Enter new class name (or press Enter to keep '" + existingClass.getClassName() + "'): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                existingClass.setClassName(newName);
            }

            System.out.print("Enter new description (or press Enter to keep '" + existingClass.getDescription() + "'): ");
            String newDescription = scanner.nextLine();
            if (!newDescription.isEmpty()) {
                existingClass.setDescription(newDescription);
            }

            System.out.print("New Trainer ID (or press Enter to keep '" + existingClass.getTrainerId() + "'): ");
            String newTrainerIdInput = scanner.nextLine();
            if (!newTrainerIdInput.isEmpty()) {
                int newTrainerId = Integer.parseInt(newTrainerIdInput);
                existingClass.setTrainerId(newTrainerId);
            }

            System.out.print("New class level (or press Enter to keep '" + existingClass.getLevel() + "'): ");
            String newLevel = scanner.nextLine();
            if (!newLevel.isEmpty()) {
                existingClass.setLevel(newLevel);
            }

            System.out.print("New duration (or press Enter to keep '" + existingClass.getDuration() + "'): ");
            String newDuration = scanner.nextLine();
            if (!newDuration,isEmpty()) {
                int newDuration = Integer.parseInt(duration);
                existingClass.setDuration(newDuration);
            }

            System.out.print("New capacity (or press Enter to keep '" + existingClass.getCapacity() + "'): ");
            String newCapacity = scanner.nextLine();
            if (!newCapacity.isEmpty()) {
                int newCapacity = Integer.parseInt(capacity);
                existingClass.setCapacity(newCapacity);
            }

            System.out.print("New date (YYYY-MM-DD) (or press Enter to keep '" + existingClass.getDate() + "'): ");
            String newDate = scanner.nextLine();
            if (!newDate.isEmpty()) {
                LocalDate newDate = LocalDate.parse(date);
                existingClass.setDate(newDate);
            }

            System.out.print("New time (HH:MM) (or press Enter to keep '" + existingClass.getTime() + "'): ");
            String newTime = scanner.nextLine();
            if (!newTime.isEmpty()) {
                LocalTime newTime = LocalTime.parse(time);
                existingClass.setTime(newTime);
            }

            System.out.print("New location (or press Enter to keep '" + existingClass.getLocation() + "'): ");
            String newLocation = scanner.nextLine();
            if (!newLocation.isEmpty()) {
                existingClass.setLocation(location);
            }

            System.out.print("New equipment needed (or press Enter to keep '" + existingClass.getEquipment() + "'): ");
            String newEquipment = scanner.nextLine();
            if (!newEquipment.isEmpty()) {
                existingClass.setEquipment(equipment);
            }

            workoutService.updateWorkoutClass(existingClass);
            System.out.println("Workout class updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating workout class: " + e.getMessage());
        }
    }

    /**
    * Allows an admin to delete a workout class by entering its ID.
    * Confirms existence and prompts for deletion confirmation.
    *
    * @param scanner Scanner used to read admin input
    * @param workoutService Service used to fetch and delete class data
    */
    public void deleteWorkoutClass(Scanner scanner, WorkoutClassService workoutService) {
        System.out.print("Enter class ID to delete: ");
        int classId = Integer.parseInt(scanner.nextLine());

        try {
            WorkoutClass workoutClass = workoutService.getWorkoutClassById(classId);
            if (workoutClass == null) {
                System.out.println("❌ Class not found.");
                return;
            }

            System.out.print("Are you sure you want to delete this class? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                workoutService.deleteWorkoutClass(classId);
                System.out.println("✅ Class deleted successfully.");
            } else {
                System.out.println("❌ Class deletion cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting class: " + e.getMessage());
        }
    }
}
