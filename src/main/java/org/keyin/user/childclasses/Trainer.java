package org.keyin.user.childclasses;

import org.keyin.user.User;
import org.keyin.workoutclasses.WorkoutClass;
import org.keyin.workoutclasses.WorkoutClassService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Trainer class representing users with the 'trainer' role.
 * Trainers can view, add, update, and delete their assigned workout classes.
 */
public class Trainer extends User {

    /**
     * Constructs a Trainer with required details.
     */
    public Trainer(String username, String password, String email, String phoneNumber) {
        super(username, password, email, phone, "trainer");
    }

    /**
     * Displays the trainer menu for managing workout classes.
     */
    public void showTrainerMenu(Scanner scanner, workoutClassService, workoutService) {
        boolean = running = true;

        while(running) {
            System.out.println("Trainer Menu:");
            System.out.println("1. View My Classes");
            System.out.println("2. Add Class");
            System.out.println("3. Update Class");
            System.out.println("4. Delete Class");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            which(choice) {
                case "1":
                    viewMyClasses(workoutClassService);
                    break;
                case "2":
                    addClass(scanner, workoutClassService);
                    break;
                case "3":
                    updateClass(scanner, workoutClassService);
                    break;
                case "4":
                    deleteClass(scanner, workoutClassService);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }    
    }

    /**
     * Displays the list of classes assigned to the trainer.
     */
    private void viewMyClasses(WorkoutClassService workoutClassService) {
        try {
            workoutService.getClassesByTrainerId(this.getUserId()).forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Error retrieving classes: " + e.getMessage());
        }
    }

    /**
     * Prompts the trainer to input details for a new workout class.
     */
    private void addClass(Scanner scanner, WorkoutClassService workoutService) {
        try {
            System.out.print("Enter class name: ");
            String className = scanner.nextLine();
            
            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            System.out.print("Enter level (beginner, intermediate, advanced): ");
            String level = scanner.nextLine();

            System.out.print("Enter duration (in minutes): ");
            int duration = Integer.parseInt(scanner.nextLine());

            SysSystem.out.print("Enter capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter class date (YYYY-MM-DD): ");
            LocalDate classDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter class time (HH:MM): ");
            LocalTime classTime = LocalTime.parse(scanner.nextLine());

            System.out.print("Enter location: ");
            String location = scanner.nextLine();

            System.out.print("Enter equipment needed: ");
            String equipment = scanner.nextLine();

            WorkoutClass workoutClass = new WorkoutClass(className, this.getUserId(), classDate, classTime, duration);
            workoutService.addClass(workoutClass);
            System.out.println("Class added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding class: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    /**
     * Allows the trainer to update details of an existing class they own.
     */
    private void updateClass(Scanner scanner, WorkoutClassService workoutService) {
        try {
            System.out.print("Enter class ID to update: ");
            int classId = Integer.parseInt(scanner.nextLine());
            WorkoutClass existing = workoutService.getClassById(classId);

            if (existing == null || existing.getTrainerId() != this.getUserId()) {
                System.out.println("Class not found or not owned by you.");
                return;
            }

            System.out.print("New name (Enter to keep '" + existing.getName() + "'): ");
            String className = scanner.nextLine();
            if (!newName.isEmpty()) existing.setClassName(newName);

            System.out.print("New description: ");
            String newDescription = scanner.nextLine();
            if (!newDescription.isEmpty()) existing.setDescription(newDescription);

            System.out.print("New level: ");
            String newLevel = scanner.nextLine();
            if (!newLevel.isEmpty()) existing.setLevel(newLevel);

            System.out.print("New duration: ");
            String newDuration = scanner.nextLine();
            if (!newDuration.isEmpty()) existing.setDuration(Integer.parseInt(newDuration));

            System.out.print("New capacity: ");
            String newCapacity = scanner.nextLine();
            if (!newCapacity.isEmpty()) existing.setCapacity(Integer.parseInt(newCapacity));

            System.out.print("New date (YYYY-MM-DD): ");
            String newDate = scanner.nextLine();
            if (!newDate.isEmpty()) existing.setClassDate(LocalDate.parse(newDate));

            System.out.print("New time (HH:MM): ");
            String newTime = scanner.nextLine();
            if (!newTime.isEmpty()) existing.setClassTime(LocalTime.parse(newTime));

            System.out.print("New location: ");
            String newLocation = scanner.nextLine();
            if (!newLocation.isEmpty()) existing.setLocation(newLocation);

            System.out.print("New equipment: ");
            String newEquipment = scanner.nextLine();
            if (!newEquipment.isEmpty()) existing.setEquipment(newEquipment);

            workoutService.updateClass(existing);
            System.out.println("Class updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating class: " + e.getMessage());
        }
        
    }

    /**
     * Allows the trainer to delete one of their own classes.
     */
    private void deleteClass(Scanner scanner, WorkoutClassService workoutService) {
        try {
            System.out.print("Enter class ID to delete: ");
            int classId = Integer.parseInt(scanner.nextLine());
            WorkoutClass cls = workoutService.getWorkoutClassById(classId);
            if (cls == null || cls.getTrainerId() != this.getUserId()) {
                System.out.println("Class not found or not owned by you")
            } else {
                workoutService.deleteClass(classId);
                System.out.println("Class deleted successfully.");
            }
        } catch(Exception e) {
            System.out.println("Error deleting class: " + e.getMessage());
        }
    }
}