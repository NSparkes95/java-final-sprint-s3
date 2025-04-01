package org.keyin.workoutclasses;

import org.keyin.database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the WorkoutClassDAO interface using JDBC to manage workout class data.
 */
public class WorkoutClassDAOImpl implements WorkoutClassDAO {

    /**
     * Fetches all workout classes assigned to a specific trainer.
     *
     * @param trainerId The trainer's ID.
     * @return A list of WorkoutClass objects.
     * @throws SQLException If there’s an issue accessing the database.
     */
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

    /**
     * Retrieves upcoming classes for a trainer (today or in the future).
     *
     * @param trainerId The trainer’s ID.
     * @return A list of upcoming WorkoutClass objects.
     * @throws SQLException If a database error occurs.
     */
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

    /**
     * Retrieves every workout class in the system.
     *
     * @return A list of all WorkoutClass records.
     * @throws SQLException If something goes wrong with the query.
     */
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

    /**
     * Adds a new workout class to the database.
     *
     * @param wc The workout class to insert.
     * @throws SQLException If the insert fails.
     */
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

    /**
     * Marks a class as completed in the database.
     * (This is currently optional but can be used later.)
     *
     * @param classId ID of the class to mark complete.
     * @throws SQLException If the update fails.
     */
    public void markClassAsCompleted(int classId) throws SQLException {
        String sql = "UPDATE workout_classes SET is_completed = TRUE WHERE class_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, classId);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Maps the current row of a ResultSet to a WorkoutClass object.
     *
     * @param rs The ResultSet to map.
     * @return A new WorkoutClass object.
     * @throws SQLException If column access fails.
     */
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
