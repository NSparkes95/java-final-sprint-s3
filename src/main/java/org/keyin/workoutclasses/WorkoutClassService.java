// WorkoutClassService.java
package org.keyin.workoutclasses;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for managing workout class-related business logic.
 * Acts as an intermediary between the UI/controller layer and the DAO layer.
 */
public class WorkoutClassService {
    private WorkoutClassDAO workoutClassDAO;

    /**
     * Constructs the WorkoutClassService using a DAO implementation.
     * @param workoutClassDAO the DAO used to manage workout class data
     */
    public WorkoutClassService(WorkoutClassDAO workoutClassDAO) {
        this.workoutClassDAO = workoutClassDAO;
    }

    /**
     * Adds a new workout class to the database.
     * @param workoutClass the class to add
     * @throws SQLException if a database error occurs
     */
    public void addWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        workoutClassDAO.addWorkoutClass(workoutClass);
    }

    /**
     * Retrieves all workout classes in the system.
     * @return a list of all workout classes
     * @throws SQLException if a database error occurs
     */
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        return workoutClassDAO.getAllWorkoutClasses();
    }

    /**
     * Retrieves all workout classes assigned to a specific trainer.
     * @param trainerId the ID of the trainer
     * @return a list of classes taught by the trainer
     * @throws SQLException if a database error occurs
     */
    public List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) throws SQLException {
        return workoutClassDAO.getWorkoutClassesByTrainerId(trainerId);
    }

    /**
     * Deletes a workout class by its ID.
     * @param classId the ID of the class to delete
     * @throws SQLException if a database error occurs
     */
    public void deleteWorkoutClass(int classId) throws SQLException {
        workoutClassDAO.deleteWorkoutClass(classId);
    }

    /**
     * Updates an existing workout class in the database.
     * @param workoutClass the class with updated details
     * @return true if the update was successful, false otherwise
     */
    public boolean updateWorkoutClass(WorkoutClass workoutClass) {
        return workoutClassDAO.updateWorkoutClass(workoutClass);
    }
    
} 
