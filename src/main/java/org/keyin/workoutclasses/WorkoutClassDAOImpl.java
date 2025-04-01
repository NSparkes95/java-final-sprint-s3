package org.keyin.workout_classes;

import org.keyin.database.DatabaseConnection;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of WorkoutClassDAO using JDBC
 */
public class WorkoutClassDAOImpl implements WorkoutClassDao {
    @Override
    public List<WorkoutClass> getClassesByTrainerId(int trainerId) throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes WHERE trainer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, trainerId);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LocalTime time = resultSet.getTime("time").toLocalTime();
                int duration = resultSet.getInt("duration");
                String location = resultSet.getString("location");

                classes.add(new WorkoutClass(id, name, date, time, duration, location, trainerId));
            }
        }
        return classes;
    }

    @Override
    public List<WorkoutClass> getUpcomingClasses(int trainerId) throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes WHERE trainer_id = ? AND date >= ? ORDER BY date, time";

        try (Connection connection = DatabaseConnection.getConnection()) {
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, trainerId);
            preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LocalTime time = resultSet.getTime("time").toLocalTime();
                int duration = resultSet.getInt("duration");
                String location = resultSet.getString("location");

                classes.add(new WorkoutClass(id, name, date, time, duration, location, trainerId));
            }
        }
        return classes;
    }

    @Override
    public void markClassAsCompleted(int classId) throws SQLException {
        String sql = "UPDATE workout_classes SET completed = TRUE WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, classId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes";

        try (Connection connection = DatabaseConnection.getConnection()) {
            var preparedStatement = connection.prepareStatement(sql);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LocalTime time = resultSet.getTime("time").toLocalTime();
                int duration = resultSet.getInt("duration");
                String location = resultSet.getString("location");
                int trainerId = resultSet.getInt("trainer_id");

                classes.add(new WorkoutClass(id, name, date, time, duration, location, trainerId));
            }
        }
        return classes;
    }

    /**
     * Adds a new workout class to the database.
     * 
     * @param workoutClass The workout class to add.
     * @throws SQLException If there is an error adding the class.
     */
    @Override
    public void addWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        String sql = "INSERT INTO workout_classes (class_name, class_description, trainer_id, class_date, class_time, class_duration, class_location,class_level, class_equipment, is_completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
        preparedStatement stmt = conn.preparedStatement(sql)) {
            
        }
    }
}