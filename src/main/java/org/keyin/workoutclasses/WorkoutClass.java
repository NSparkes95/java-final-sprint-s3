// WorkoutClass.java
package org.keyin.workoutclasses;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a workout class within the gym management system.
 * Stores details such as trainer, location, schedule, and class characteristics.
 */
public class WorkoutClass {

    private int         id;
    private String      className;
    private int         trainerId;
    private String      classDescription;
    private String      classLevel;
    private int         classDuration;
    private int         classCapacity;
    private LocalDate   classDate;
    private LocalTime   classTime;
    private String      classLocation;
    private String      classEquipment;
    private boolean     isCompleted;

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

        this.id               = id;
        this.className        = className;
        this.trainerId        = trainerId;
        this.classDescription = classDescription;
        this.classLevel       = classLevel;
        this.classDuration    = classDuration;
        this.classCapacity    = classCapacity;
        this.classDate        = classDate;
        this.classTime        = classTime;
        this.classLocation    = classLocation;
        this.classEquipment   = classEquipment;
        this.isCompleted      = false;
    }

    //        GETTERS
    /** @return class name. */
    public String getClassName() {
        return className;
    }

    /** @return class description. */
    public String getClassDescription() {
        return classDescription;
    }

    /** @return class date. */
    public LocalDate getClassDate() {
        return classDate;
    }

    /** @return class time. */
    public LocalTime getClassTime() {
        return classTime;
    }

    /** @return class duration. */
    public int getClassDuration() {
        return classDuration;
    }

    /** @return class capacity. */
    public int getClassCapacity() {
        return classCapacity;
    }

    /** @return class location. */
    public String getClassLocation() {
        return classLocation;
    }

    /** @return class level. */
    public String getClassLevel() {
        return classLevel;
    }

    /** @return equipment list or label. */
    public String getClassEquipment() {
        return classEquipment;
    }

    /** @return true if completed, false otherwise. */
    public boolean isCompleted() {
        return isCompleted;
    }

    /** @return the workout class ID. */
    public int getClassId() {
        return id;
    }

    /** @return trainer user ID. */
    public int getTrainerId() {
        return trainerId;
    }

    //        SETTERS
    /**
     * Sets the name of the workout class.
     * @param className the new class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Sets the description of the workout class.
     * @param classDescription the new description
     */
    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    /**
     * Sets the difficulty level of the class (e.g., Beginner, Intermediate).
     * @param classLevel the new class level
     */
    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }

    /**
     * Sets the duration of the class in minutes.
     * @param classDuration the new duration
     */
    public void setClassDuration(int classDuration) {
        this.classDuration = classDuration;
    }

    /**
     * Sets the maximum capacity for the class.
     * @param classCapacity the new capacity
     */
    public void setClassCapacity(int classCapacity) {
        this.classCapacity = classCapacity;
    }

    /**
     * Sets the date the class will take place.
     * @param classDate the new date
     */
    public void setClassDate(LocalDate classDate) {
        this.classDate = classDate;
    }

    /**
     * Sets the time the class will begin.
     * @param classTime the new time
     */
    public void setClassTime(LocalTime classTime) {
        this.classTime = classTime;
    }

    /**
     * Sets the physical location of the class.
     * @param classLocation the new location
     */
    public void setClassLocation(String classLocation) {
        this.classLocation = classLocation;
    }

    /**
     * Sets the equipment needed for the class.
     * @param classEquipment the new equipment list
     */
    public void setClassEquipment(String classEquipment) {
        this.classEquipment = classEquipment;
    }

    /**
     * Sets whether the class is marked as completed.
     * @param isCompleted true if the class is completed, false otherwise
     */
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * Returns a formatted string representation of the workout class.
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
