// WorkoutClassDAOImpl.java
package org.keyin.workoutclasses;

import org.keyin.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link WorkoutClassDAO} interface.
 * Provides database operations for managing workout classes.
 */
public class WorkoutClassDAOImpl implements WorkoutClassDAO {
    /**
     * Adds a new workout class to the database.
     *
     * @param workoutClass The {@link WorkoutClass} object containing class details.
     * @throws SQLException If a database access error occurs.
     */
    @Override
public void addWorkoutClass(WorkoutClass workoutClass) throws SQLException {
    String sql = "INSERT INTO workoutclasses (class_name, trainer_id, class_description, class_level, class_duration, class_capacity, class_date, class_time, class_location, class_equipment, is_completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

    /**
     * Retrieves all workout classes from the database.
     *
     * @return A list of all {@link WorkoutClass} records.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workoutclasses";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                classes.add(buildWorkoutClassFromResultSet(rs));
            }
        }
        return classes;
    }

    /**
     * Retrieves workout classes associated with a specific trainer.
     *
     * @param trainerId The ID of the trainer (foreign key from users table).
     * @return A list of {@link WorkoutClass} instances assigned to the trainer.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workoutClasses WHERE trainer_id = ?";

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
        String sql = "DELETE FROM workoutClasses WHERE class_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            stmt.executeUpdate();
        }
    }
    /**
     * Builds a {@link WorkoutClass} object from a database result set.
     *
     * @param rs The {@link ResultSet} containing workout class data.
     * @return A fully populated {@link WorkoutClass} instance.
     * @throws SQLException If reading from the ResultSet fails.
     */
    private WorkoutClass buildWorkoutClassFromResultSet(ResultSet rs) throws SQLException {
        return new WorkoutClass(
            rs.getInt("class_id"),
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
    
