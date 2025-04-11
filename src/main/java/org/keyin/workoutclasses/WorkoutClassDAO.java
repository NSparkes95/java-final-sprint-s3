// WorkoutClassDAO.java
package org.keyin.workoutclasses;

import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object (DAO) interface for handling workout class records in the database.
 * Defines methods to create, retrieve, and delete workout classes.
 */
public interface WorkoutClassDAO {
    /**
     * Inserts a new workout class into the database.
     *
     * @param workoutClass The {@link WorkoutClass} object to add.
     * @throws SQLException If a database access error occurs.
     */

    void addWorkoutClass(WorkoutClass workoutClass) throws SQLException;
    /**
     * Retrieves all workout classes from the database.
     *
     * @return A list of all {@link WorkoutClass} records.
     * @throws SQLException If a database access error occurs.
     */
    List<WorkoutClass> getAllWorkoutClasses() throws SQLException;
    /**
     * Retrieves workout classes associated with a specific trainer.
     *
     * @param trainerId The ID of the trainer (foreign key from users table).
     * @return A list of {@link WorkoutClass} instances taught by the given trainer.
     * @throws SQLException If a database access error occurs.
     */

    List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) throws SQLException;
    /**
     * Deletes a workout class from the database using its ID.
     *
     * @param classId The ID of the workout class to delete.
     * @throws SQLException If a database access error occurs or the ID does not exist.
     */
    void deleteWorkoutClass(int classId) throws SQLException;
}
