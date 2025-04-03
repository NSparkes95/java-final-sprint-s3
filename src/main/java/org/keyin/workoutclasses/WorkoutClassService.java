package org.keyin.workoutclasses;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer to handle logic for workout classes.
 * This uses the DAO to interact with the database.
 */
public class WorkoutClassService {

    private final WorkoutClassDAO workoutDAO;

    // Constructor that takes a DAO (implementation)
    public WorkoutClassService(WorkoutClassDAO workoutDAO) {
        this.workoutDAO = workoutDAO;
    }

    /**
     * Get all classes created by a specific trainer.
     *
     * @param trainerId the trainer's user ID
     * @return list of classes created by the trainer
     * @throws SQLException if there is a database error
     */
    public List<WorkoutClass> getClassesByTrainerId(int trainerId) throws SQLException {
        return workoutDAO.getClassesByTrainerId(trainerId);
    }

    /**
     * Get upcoming (future) classes for a specific trainer.
     *
     * @param trainerId the trainer's user ID
     * @return list of upcoming classes for the trainer
     * @throws SQLException if there is a database error
     */
    public List<WorkoutClass> getUpcomingClasses(int trainerId) throws SQLException {
        return workoutDAO.getUpcomingClasses(trainerId);
    }

    /**
     * Get all workout classes from the system (for admin use).
     *
     * @return list of all workout classes in the system
     * @throws SQLException if there is a database error
     */
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        return workoutDAO.getAllWorkoutClasses();
    }

    /**
     * Add a new workout class to the system.
     *
     * @param wc the workout class object to insert
     * @throws SQLException if there is a database error
     */
    public void addWorkoutClass(WorkoutClass wc) throws SQLException {
        workoutDAO.addWorkoutClass(wc);
    }

    /**
     * Update an existing workout class.
     *
     * @param workoutClass The workout class object to update.
     * @throws SQLException If a database error occurs.
     */
    public void updateWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        workoutDAO.updateWorkoutClass(workoutClass);
    }

    /**
     * Delete a workout class.
     *
     * @param classId The ID of the class to delete.
     * @throws SQLException If a database error occurs.
     */
    public void deleteWorkoutClass(int classId) throws SQLException {
        workoutDAO.deleteWorkoutClass(classId);
    }

    /**
     * Get all available workout classes (for members).
     * This method is used to retrieve all classes available for members.
     *
     * @return list of all available workout classes
     * @throws SQLException if a database error occurs
     */
    public List<WorkoutClass> getAllAvailableClasses() throws SQLException {
        return workoutDAO.getAllWorkoutClasses(); // This method can be extended if you want to filter out completed classes or other conditions.
    }
}
