package org.keyin.workoutclasses;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a workout class that can be scheduled by a trainer
 * and attended by gym members. Stores basic class info like date,
 * time, duration, and equipment needed.
 */
public class WorkoutClass {
    private int classId;
    private String className;
    private int trainerId;
    private String classLevel;
    private String classDescription;
    private int classDuration;
    private int classCapacity;
    private LocalDate classDate;
    private LocalTime classTime;
    private String classLocation;
    private String classEquipment;
    private boolean isCompleted;

    /**
     * Constructor used for creating or reading a full workout class.
     */
    public WorkoutClass(int classId, String className, int trainerId, String classLevel, String classDescription,
                        int classDuration, int classCapacity, LocalDate classDate, LocalTime classTime,
                        String classLocation, String classEquipment) {
        this.classId = classId;
        this.className = className;
        this.trainerId = trainerId;
        this.classLevel = classLevel;
        this.classDescription = classDescription;
        this.classDuration = classDuration;
        this.classCapacity = classCapacity;
        this.classDate = classDate;
        this.classTime = classTime;
        this.classLocation = classLocation;
        this.classEquipment = classEquipment;
        this.isCompleted = false; // Default to not completed
    }

    // Getters and Setters

    /**
     * Gets the class ID.
     */
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    /**
     * Gets the name of the class.
     */
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets the ID of the trainer running the class.
     */
    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    /**
     * Gets the difficulty level (e.g. Beginner, Intermediate).
     */
    public String getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }

    /**
     * Gets the description of the class.
     */
    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    /**
     * Gets the duration of the class in minutes.
     */
    public int getClassDuration() {
        return classDuration;
    }

    public void setClassDuration(int classDuration) {
        this.classDuration = classDuration;
    }

    /**
     * Gets the maximum number of participants allowed.
     */
    public int getClassCapacity() {
        return classCapacity;
    }

    public void setClassCapacity(int classCapacity) {
        this.classCapacity = classCapacity;
    }

    /**
     * Gets the scheduled date of the class.
     */
    public LocalDate getClassDate() {
        return classDate;
    }

    public void setClassDate(LocalDate classDate) {
        this.classDate = classDate;
    }

    /**
     * Gets the scheduled time of the class.
     */
    public LocalTime getClassTime() {
        return classTime;
    }

    public void setClassTime(LocalTime classTime) {
        this.classTime = classTime;
    }

    /**
     * Gets the location where the class will be held.
     */
    public String getClassLocation() {
        return classLocation;
    }

    public void setClassLocation(String classLocation) {
        this.classLocation = classLocation;
    }

    /**
     * Gets the required equipment (if any).
     */
    public String getClassEquipment() {
        return classEquipment;
    }

    public void setClassEquipment(String classEquipment) {
        this.classEquipment = classEquipment;
    }

    /**
     * Checks whether the class has been marked as completed.
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    /**
     * String version of the workout class.
     */
    @Override
    public String toString() {
        return "WorkoutClass{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", trainerId=" + trainerId +
                ", classLevel='" + classLevel + '\'' +
                ", classDescription='" + classDescription + '\'' +
                ", classDuration=" + classDuration +
                ", classCapacity=" + classCapacity +
                ", classDate=" + classDate +
                ", classTime=" + classTime +
                ", classLocation='" + classLocation + '\'' +
                ", classEquipment='" + classEquipment + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
