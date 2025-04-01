package org.keyin.workout_classes;

import org.keyin.database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of WorkoutClassDAO using JDBC
 */
public class WorkoutClassDAOImpl implements WorkoutClassDAO {

    @Override
    public List<WorkoutClass> getClassesByTrainerId(int trainerId) throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes WHERE trainer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, trainerId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                classes.add(mapResultSetToWorkoutClass(rs));
            }
        }

        return classes;
    }

    @Override
    public List<WorkoutClass> getUpcomingClasses(int trainerId) throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes WHERE trainer_id = ? AND class_date >= CURRENT_DATE ORDER BY class_date, class_time";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, trainerId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                classes.add(mapResultSetToWorkoutClass(rs));
            }
        }

        return classes;
    }

    @Override
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                classes.add(mapResultSetToWorkoutClass(rs));
            }
        }

        return classes;
    }

    @Override
    public void addWorkoutClass(WorkoutClass wc) throws SQLException {
        String sql = "INSERT INTO workout_classes (class_name, class_description, trainer_id, class_date, class_time, class_duration, class_capacity, class_location, class_level, class_equipment, is_completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, wc.getClassName());
            stmt.setString(2, wc.getClassDescription());
            stmt.setInt(3, wc.getTrainerId());
            stmt.setDate(4, Date.valueOf(wc.getClassDate()));
            stmt.setTime(5, Time.valueOf(wc.getClassTime()));
            stmt.setInt(6, wc.getClassDuration());
            stmt.setInt(7, wc.getClassCapacity());
            stmt.setString(8, wc.getClassLocation());
            stmt.setString(9, wc.getClassLevel());
            stmt.setString(10, wc.getClassEquipment());
            stmt.setBoolean(11, wc.isCompleted());

            stmt.executeUpdate();
        }
    }

    // Optional (future)
    public void markClassAsCompleted(int classId) throws SQLException {
        String sql = "UPDATE workout_classes SET is_completed = TRUE WHERE class_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, classId);
            preparedStatement.executeUpdate();
        }
    }

    private WorkoutClass mapResultSetToWorkoutClass(ResultSet rs) throws SQLException {
        return new WorkoutClass(
                rs.getInt("class_id"),
                rs.getString("class_name"),
                rs.getInt("trainer_id"),
                rs.getString("class_level"),
                rs.getString("class_description"),
                rs.getInt("class_duration"),
                rs.getInt("class_capacity"),
                rs.getDate("class_date").toLocalDate(),
                rs.getTime("class_time").toLocalTime(),
                rs.getString("class_location"),
                rs.getString("class_equipment")
        );
    }
}
