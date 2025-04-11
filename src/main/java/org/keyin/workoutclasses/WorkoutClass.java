// WorkoutClass.java
package org.keyin.workoutclasses;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a workout class within the gym management system.
 * Stores details such as trainer, location, schedule, and class characteristics.
 */
public class WorkoutClass {
    private int id;
    private String className;
    private int trainerId;
    private String classDescription;
    private String classLevel;
    private int classDuration;
    private int classCapacity;
    private LocalDate classDate;
    private LocalTime classTime;
    private String classLocation;
    private String classEquipment;
    private boolean isCompleted;

    /**
     * Constructs a WorkoutClass with full scheduling and configuration details.
     *
     * @param id               The unique identifier for the workout class.
     * @param className        The name of the workout class.
     * @param trainerId        The trainer assigned to the class (user ID).
     * @param classDescription A description of the workout class.
     * @param classLevel       The difficulty level (e.g., Beginner, Intermediate).
     * @param classDuration    Duration in minutes.
     * @param classCapacity    Maximum number of participants allowed.
     * @param classDate        Date the class takes place.
     * @param classTime        Time the class starts.
     * @param classLocation    Physical location or room.
     * @param classEquipment   Equipment required for the session.
     */

    public WorkoutClass(int id, String className, int trainerId, String classDescription, String classLevel,
                        int classDuration, int classCapacity, LocalDate classDate, LocalTime classTime,
                        String classLocation, String classEquipment) {
        this.id = id;
        this.className = className;
        this.trainerId = trainerId;
        this.classDescription = classDescription;
        this.classLevel = classLevel;
        this.classDuration = classDuration;
        this.classCapacity = classCapacity;
        this.classDate = classDate;
        this.classTime = classTime;
        this.classLocation = classLocation;
        this.classEquipment = classEquipment;
        this.isCompleted = false;
    }

    /**
     * Gets the name of the workout class.
     *
     * @return class name.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Gets the description of the class.
     *
     * @return class description.
     */
    public String getClassDescription() {
        return classDescription;
    }

    /**
     * Gets the scheduled date of the class.
     *
     * @return class date.
     */

    public LocalDate getClassDate() {
        return classDate;
    }

    /**
     * Gets the scheduled time of the class.
     *
     * @return class time.
     */
    public LocalTime getClassTime() {
        return classTime;
    }

    /**
     * Gets the duration of the class in minutes.
     *
     * @return class duration.
     */
    public int getClassDuration() {
        return classDuration;
    }

    /**
     * Gets the participant capacity for the class.
     *
     * @return class capacity.
     */
    public int getClassCapacity() {
        return classCapacity;
    }

    /**
     * Gets the location of the class.
     *
     * @return class location.
     */
    public String getClassLocation() {
        return classLocation;
    }

    /**
     * Gets the difficulty level of the class.
     *
     * @return class level.
     */
    public String getClassLevel() {
        return classLevel;
    }

    /**
     * Gets the equipment required for the class.
     *
     * @return equipment list or label.
     */
    public String getClassEquipment() {
        return classEquipment;
    }

    /**
     * Indicates whether the class is marked as completed.
     *
     * @return true if completed, false otherwise.
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Gets the class ID.
     *
     * @return the workout class ID.
     */
    public int getClassId() {
        return id;
    }

    /**
     * Gets the ID of the assigned trainer.
     *
     * @return trainer user ID.
     */
    public int getTrainerId() {
        return trainerId;
    }

    /**
     * Returns a formatted string representation of the workout class.
     *
     * @return class details in text format.
     */
    @Override
    public String toString() {
        return "WorkoutClass{" +
                "ID=" + id +
                ", Name='" + className + '\'' +
                ", Description='" + classDescription + '\'' +
                ", Level='" + classLevel + '\'' +
                ", Duration=" + classDuration +
                ", Capacity=" + classCapacity +
                ", Date=" + classDate +
                ", Time=" + classTime +
                ", Location='" + classLocation + '\'' +
                ", Equipment='" + classEquipment + '\'' +
                ", TrainerID=" + trainerId +
                ", Completed=" + isCompleted +
                '}';
    }
}