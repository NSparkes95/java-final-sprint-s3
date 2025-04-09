package org.keyin.workoutclasses;

import java.sql.SQLException;
import java.util.List;

public class WorkoutClassService {
    private WorkoutClassDAO workoutClassDAO;

    public WorkoutClassService(WorkoutClassDAO workoutClassDAO) {
        this.workoutClassDAO = workoutClassDAO;
    }

    public void addWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        workoutClassDAO.addWorkoutClass(workoutClass);
    }

    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        return workoutClassDAO.getAllWorkoutClasses();
    }

    public List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) throws SQLException {
        return workoutClassDAO.getWorkoutClassesByTrainerId(trainerId);
    }

    public void deleteWorkoutClass(int classId) throws SQLException {
        workoutClassDAO.deleteWorkoutClass(classId);
    }
}  
