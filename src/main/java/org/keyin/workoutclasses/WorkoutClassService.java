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
    /**
     * Constructor to initialize the WorkoutClassService with a specific DAO implementation.
     * @param workoutDAO The DAO to interact with the database.
     */
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
     * This method allows trainers to modify the details of a class they have created.
     *
     * @param wc the updated workout class object
     * @throws SQLException if there is a database error
     */
    public void updateWorkoutClass(WorkoutClass wc) throws SQLException {
        workoutDAO.updateWorkoutClass(wc);
    }

    /**
     * Delete a workout class by its ID.
     * This method allows trainers to delete a class they have created.
     *
     * @param classId the ID of the class to delete
     * @throws SQLException if there is a database error
     */
    public void deleteWorkoutClass(int classId) throws SQLException {
        workoutDAO.deleteWorkoutClass(classId);
    }
}
