package org.keyin.workoutclasses;

import java.sql.SQLException;
import java.util.List;

/**
 * WorkoutClassDAO is the interface that defines the methods 
 * used to interact with workout class data stored in the database.
 */
public interface WorkoutClassDAO {

    /**
     * Gets all classes assigned to a specific trainer.
     * This includes any classes they are teaching.
     *
     * @param trainerId The ID of the trainer.
     * @return A list of WorkoutClass objects.
     * @throws SQLException If a database error occurs.
     */
    List<WorkoutClass> getClassesByTrainerId(int trainerId) throws SQLException;

    /**
     * Gets all upcoming (future-dated) classes for a specific trainer.
     * This can be used to show them what’s scheduled.
     *
     * @param trainerId The ID of the trainer.
     * @return A list of WorkoutClass objects.
     * @throws SQLException If a database access error occurs.
     */
    List<WorkoutClass> getUpcomingClasses(int trainerId) throws SQLException;

    /**
     * Returns all workout classes in the system.
     * This is typically used by admins to see everything.
     *
     * @return A list of all WorkoutClass objects.
     * @throws SQLException If a database access error occurs.
     */
    List<WorkoutClass> getAllWorkoutClasses() throws SQLException;

    /**
     * Adds a new workout class to the system.
     * This will insert the class details into the database.
     *
     * @param workoutClass The workout class to be added.
     * @throws SQLException If a database access error occurs.
     */
    void addWorkoutClass(WorkoutClass workoutClass) throws SQLException;

    /**
     * Updates an existing workout class.
     * This method is used to modify the details of an existing class.
     *
     * @param workoutClass The updated workout class object.
     * @throws SQLException If a database access error occurs.
     */
    void updateWorkoutClass(WorkoutClass workoutClass) throws SQLException;

    /**
     * Deletes a workout class from the system by its ID.
     * This will remove the specified class from the database.
     *
     * @param classId The ID of the class to be deleted.
     * @throws SQLException If a database access error occurs.
     */
    void deleteWorkoutClass(int classId) throws SQLException;
}
