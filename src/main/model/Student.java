package model;
/*
The student class identifies student with id, including the following information: major, name
courses she/he have already taken and course already registered
 */


import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

public class Student implements Writable {
    private String name;
    private Integer id;
    private String major;
    private HashSet<Integer> courseMapAlreadyTaken;
    private HashSet<Integer> courseMapToTake;

    // EFFECT: Create a student object with student id, name, major and gradeList
    public Student(String name, Integer id, String major) {
        this.id = id;
        this.name = name;
        this.major = major;
        courseMapAlreadyTaken = new HashSet<>();
        courseMapToTake = new HashSet<>();
    }

    // EFFECT: return the ID of a student
    public int getId() {
        return id;
    }

    // EFFECT: return the name of a student
    public String getName() {
        return name;
    }

    // EFFECT: get the major the student are in
    public String getMajor() {
        return major;
    }


    // REQUIRE: the grade input should be an integer from 0 to 100.
    // MODIFIES: this
    // EFFECT: add a course into the gradeList
    public boolean addTakenCourse(Integer courseID) {
        if (!isAlreadyTaken(courseID)) {
            courseMapAlreadyTaken.add(courseID);
            return true;
        }
        return false;
    }

    //EFFECT: verify student's access to register the input by their courses taken in previous semesters and courses
    //        registered in this semester. return true if they did not take the course and
    //        have not registered this course
    public boolean canBeRegistered(Integer courseID) {
        if ((!isAlreadyRegistered(courseID)) && (!isAlreadyTaken(courseID))) {
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECT: register one course if they did not take the course and have not registered this course
    public void registerCourse(Integer courseID) {
        if ((!isAlreadyRegistered(courseID)) && (!isAlreadyTaken(courseID))) {
            courseMapToTake.add(courseID);
        }

    }

    //EFFECT: return true if student have already taken this course in previous semesters
    public boolean isAlreadyTaken(Integer courseID) {
        if (courseMapAlreadyTaken.contains(courseID)) {
            return true;
        } else {
            return false;
        }
    }

    //EFFECT: return true if students have registered this course in this semester
    public boolean isAlreadyRegistered(Integer courseID) {
        if (courseMapToTake.contains(courseID)) {
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECT: drop the course registered and return true if the course is not registered
    public boolean dropCourse(Integer courseID) {
        if (isAlreadyRegistered(courseID)) {
            courseMapToTake.remove(courseID);
            return true;
        }
        return false;
    }


    //EFFECT: return all the courses ID which are registered this semester
    public Set<Integer> getAlreadyRegisteredID() {
        return courseMapToTake;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //EFFECT: return all the courses ID which were taken before
    public Set<Integer> getAlreadyTakenCourseID() {
        return courseMapAlreadyTaken;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", getName());
        jsonObject.put("id", getId());
        jsonObject.put("major", getMajor());
        jsonObject.put("courseMapAlreadyTaken", courseMapAlreadyTaken.toString());
        jsonObject.put("courseMapToTake", courseMapToTake.toString());
        return jsonObject;
    }


}
