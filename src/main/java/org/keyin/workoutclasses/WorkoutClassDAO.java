package org.keyin.workout_classes;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for WorkoutClassDAO.
 * This interface defines the methods for interacting with the workout_classes table in the database.
*/
public interface WorkoutClassDAO {
    /**
     * Retrieve all future classes that haven't been completed.
     * 
     * @param trainerId The ID of the trainer.
     * @return List of WorkoutClass
     * @throws SQLException if a database access error occurs.
     */
    List<WorkoutClass> getClassesByTrainerId(int trainerId) throws SQLException;

    /**
     * Retrieve all classes that have been completed.
     * 
     * @param trainerId Trainer ID
     * @return List of UpcomingWorkoutClass
     * @throws SQLException if a database access error occurs.
     */
    List<WorkoutClass> getUpcomingClasses(int trainerId) throws SQLException;

    /**
     * Return all classes in the system (admin only).
     * 
     * @return List of WorkoutClass
     * @throws SQLException if a database access error occurs.
     */
    List<WorkoutClass> getAllWorkoutClasses() throws SQLException;

    /**
     * Add a new class to the database.
     * 
     * @param workoutClass The WorkoutClass object to be added.
     * @throws SQLException if a database access error occurs.
     */
    void addWorkoutClass(WorkoutClass workoutClass) throws SQLException;
}