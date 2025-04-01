package org.keyin.workout_classes;

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
     * @return list of classes
     * @throws SQLException if DB fails
     */
    public List<WorkoutClass> getClassesByTrainerId(int trainerId) throws SQLException {
        return workoutDAO.getClassesByTrainerId(trainerId);
    }

    /**
     * Get upcoming (future) classes for a trainer.
     *
     * @param trainerId trainer's ID
     * @return list of upcoming classes
     * @throws SQLException if DB fails
     */
    public List<WorkoutClass> getUpcomingClasses(int trainerId) throws SQLException {
        return workoutDAO.getUpcomingClasses(trainerId);
    }

    /**
     * Get all workout classes (admin use).
     *
     * @return list of all classes
     * @throws SQLException if DB fails
     */
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        return workoutDAO.getAllWorkoutClasses();
    }

    /**
     * Add a new workout class to the system.
     *
     * @param wc the class object to insert
     * @throws SQLException if DB fails
     */
    public void addWorkoutClass(WorkoutClass wc) throws SQLException {
        workoutDAO.addWorkoutClass(wc);
    }
}
