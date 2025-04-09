package org.keyin.workoutclasses;

import org.keyin.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutClassDAOImpl implements WorkoutClassDAO {

    @Override
    public void addWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        String sql = "INSERT INTO workout_classes (class_name, trainer_id, class_description, class_level, class_duration, class_capacity, class_date, class_time, class_location, class_equipment, is_completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, workoutClass.getClassName());
            stmt.setInt(2, workoutClass.getTrainerId());
            stmt.setString(3, workoutClass.getClassDescription());
            stmt.setString(4, workoutClass.getClassLevel());
            stmt.setInt(5, workoutClass.getClassDuration());
            stmt.setInt(6, workoutClass.getClassCapacity());
            stmt.setDate(7, Date.valueOf(workoutClass.getClassDate()));
            stmt.setTime(8, Time.valueOf(workoutClass.getClassTime()));
            stmt.setString(9, workoutClass.getClassLocation());
            stmt.setString(10, workoutClass.getClassEquipment());
            stmt.setBoolean(11, workoutClass.isCompleted());

            stmt.executeUpdate();
        }
    }

    @Override
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                classes.add(buildWorkoutClassFromResultSet(rs));
            }
        }
        return classes;
    }

    @Override
    public List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes WHERE trainer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trainerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                classes.add(buildWorkoutClassFromResultSet(rs));
            }
        }
        return classes;
    }

    @Override
    public void deleteWorkoutClass(int classId) throws SQLException {
        String sql = "DELETE FROM workout_classes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            stmt.executeUpdate();
        }
    }

    private WorkoutClass buildWorkoutClassFromResultSet(ResultSet rs) throws SQLException {
        return new WorkoutClass(
                rs.getInt("id"),
                rs.getString("class_name"),
                rs.getInt("trainer_id"),
                rs.getString("class_description"),
                rs.getString("class_level"),
                rs.getInt("class_duration"),
                rs.getInt("class_capacity"),
                rs.getDate("class_date").toLocalDate(),
                rs.getTime("class_time").toLocalTime(),
                rs.getString("class_location"),
                rs.getString("class_equipment")
        );
    }
}
