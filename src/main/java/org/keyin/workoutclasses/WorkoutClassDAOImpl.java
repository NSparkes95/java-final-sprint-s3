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

    // Existing methods...

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
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                classes.add(mapResultSetToWorkoutClass(resultSet));
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
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classes.add(mapResultSetToWorkoutClass(resultSet));
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
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                classes.add(mapResultSetToWorkoutClass(resultSet));
            }
        }

        return classes;
    }

    /**
     * Adds a new workout class to the database.
     *
     * @param workoutClass The workout class to insert.
     * @throws SQLException If the insert fails.
     */
    @Override
    public void addWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        String sql = "INSERT INTO workout_classes (class_name, class_description, trainer_id, class_date, class_time, class_duration, class_capacity, class_location, class_level, class_equipment, is_completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, workoutClass.getClassName());
            stmt.setString(2, workoutClass.getClassDescription());
            stmt.setInt(3, workoutClass.getTrainerId());
            stmt.setDate(4, java.sql.Date.valueOf(workoutClass.getClassDate()));
            stmt.setTime(5, java.sql.Time.valueOf(workoutClass.getClassTime()));
            stmt.setInt(6, workoutClass.getClassDuration());
            stmt.setInt(7, workoutClass.getClassCapacity());
            stmt.setString(8, workoutClass.getClassLocation());
            stmt.setString(9, workoutClass.getClassLevel());
            stmt.setString(10, workoutClass.getClassEquipment());
            stmt.setBoolean(11, workoutClass.isCompleted());

            stmt.executeUpdate();
        }
    }

    /**
     * Updates an existing workout class in the database.
     *
     * @param workoutClass The workout class to update.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void updateWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        String sql = "UPDATE workout_classes SET class_name = ?, class_description = ?, trainer_id = ?, class_date = ?, class_time = ?, class_duration = ?, class_capacity = ?, class_location = ?, class_level = ?, class_equipment = ?, is_completed = ? WHERE class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, workoutClass.getClassName());
            stmt.setString(2, workoutClass.getClassDescription());
            stmt.setInt(3, workoutClass.getTrainerId());
            stmt.setDate(4, java.sql.Date.valueOf(workoutClass.getClassDate()));
            stmt.setTime(5, java.sql.Time.valueOf(workoutClass.getClassTime()));
            stmt.setInt(6, workoutClass.getClassDuration());
            stmt.setInt(7, workoutClass.getClassCapacity());
            stmt.setString(8, workoutClass.getClassLocation());
            stmt.setString(9, workoutClass.getClassLevel());
            stmt.setString(10, workoutClass.getClassEquipment());
            stmt.setBoolean(11, workoutClass.isCompleted());
            stmt.setInt(12, workoutClass.getClassId());

            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a workout class from the database by its ID.
     *
     * @param classId The ID of the class to delete.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void deleteWorkoutClass(int classId) throws SQLException {
        String sql = "DELETE FROM workout_classes WHERE class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, classId);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all available workout classes for members.
     * This method filters out completed classes and returns only available ones.
     *
     * @return A list of available WorkoutClass objects.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<WorkoutClass> getAvailableClasses() throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes WHERE is_completed = FALSE";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                classes.add(mapResultSetToWorkoutClass(resultSet));
            }
        }

        return classes;
    }

    /**
     * Helper to map result row to a WorkoutClass object.
     */
    private WorkoutClass mapResultSetToWorkoutClass(ResultSet resultSet) throws SQLException {
        return new WorkoutClass(
                resultSet.getInt("class_id"),
                resultSet.getString("class_name"),
                resultSet.getInt("trainer_id"),
                resultSet.getString("class_level"),
                resultSet.getString("class_description"),
                resultSet.getInt("class_duration"),
                resultSet.getInt("class_capacity"),
                resultSet.getDate("class_date").toLocalDate(),
                resultSet.getTime("class_time").toLocalTime(),
                resultSet.getString("class_location"),
                resultSet.getString("class_equipment")
        );
    }
}
