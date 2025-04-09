package org.keyin.workoutclasses;

import java.sql.SQLException;
import java.util.List;

public interface WorkoutClassDAO {
    void addWorkoutClass(WorkoutClass workoutClass) throws SQLException;
    List<WorkoutClass> getAllWorkoutClasses() throws SQLException;
    List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) throws SQLException;
    void deleteWorkoutClass(int classId) throws SQLException;
}
