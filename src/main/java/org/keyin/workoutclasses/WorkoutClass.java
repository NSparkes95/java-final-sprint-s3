package org.keyin.workoutclasses;

import java.time.LocalDate;
import java.time.LocalTime;

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

    public String getClassName() {
        return className;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public LocalDate getClassDate() {
        return classDate;
    }

    public LocalTime getClassTime() {
        return classTime;
    }

    public int getClassDuration() {
        return classDuration;
    }

    public int getClassCapacity() {
        return classCapacity;
    }

    public String getClassLocation() {
        return classLocation;
    }

    public String getClassLevel() {
        return classLevel;
    }

    public String getClassEquipment() {
        return classEquipment;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public int getClassId() {
        return id;
    }

    public int getTrainerId() {
        return trainerId;
    }

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