package org.keyin.workout_classes;

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
<<<<<<< HEAD
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                classes.add(mapResultSetToWorkoutClass(rs));
=======
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("class_id");
                String name = resultSet.getString("class_name");
                LocalDate date = rs.getDate("class_date").toLocalDate();
                LocalTime time = rs.getTime("class_time").toLocalTime();
                int duration = resultSet.getInt("class_duration");
                String location = resultSet.getString("class_location");
                int classCapacity = resultSet.getInt("class_capacity");

                classes.add(new WorkoutClass(id, name, date, time, duration, location, capacity, trainerId));
>>>>>>> 1cc488c90583d5f815b702df370e039a356bce26
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
            preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("class_id");
                String name = resultSet.getString("class_name");
                LocalDate date = rs.getDate("class_date").toLocalDate();
                LocalTime time = rs.getTime("class_time").toLocalTime();
                int duration = rs.getInt("class_duration");
                String location = rs.getString("class_location");

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

        try (Connection connection = DatabaseConnection.getConnection()) {
            var preparedStatement = connection.prepareStatement(sql);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = rs.getInt("class_id");
                String name = rs.getString("class_name");
                String description = resultSet.getString("class_description");
                int trainerId = rs.getInt("trainer_id");
                LocalDate date = rs.getDate("class_date").toLocalDate();
                LocalTime time = rs.getTime("class_time").toLocalTime();
                int duration = rs.getInt("class_duration");
                String location = rs.getString("class_location");
                String level = resultSet.getString("class_level");
                String equipment = resultSet.getString("class_equipment");
                boolean completed = resultSet.getBoolean("is_completed");


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
        preparedStatement stmt = conn.preparedStatement(sql)) {
            stmt.setString(1, workoutClass.getClassName());
            stmt.setString(2, workoutClass.getDescription());
            stmt.setInt(3, workoutClass.getTrainerId());
            stmt.setDate(4, java.sql.Date.valueOf(workoutClass.getDate()));
            stmt.setTime(5, java.sql.Time.valueOf(workoutClass.getTime()));
            stmt.setInt(6, workoutClass.getDuration());
            stmt.setInt(7, workoutClass.getClassCapacity());
            stmt.setString(8, workoutClass.getLocation());
            stmt.setString(9, workoutClass.getLevel());
            stmt.setString(10, workoutClass.getClassEquipment());
            stmt.setBoolean(11, workoutClass.isCompleted());

            stmt.executeUpdate();
        }
    }

    /** 
     * Helper to map result row to a WorkoutClass object.
    */
    private WorkoutClass map(ResultSet rs) throws SQLException {
        return new WorkoutClass(
            rs.getInt("class_id"),
            rs.getString("class_name"),
            rs.getString("class_description"),
            rs.getInt("trainer_id"),
            rs.getDate("class_date").toLocalDate(),
            rs.getTime("class_time").toLocalTime(),
            rs.getInt("class_duration"),
            rs.getInt("class_capacity"),
            rs.getString("class_location"),
            rs.getString("class_level"),
            rs.getString("class_equipment"),
            rs.getBoolean("is_completed")
        );
    }
}
