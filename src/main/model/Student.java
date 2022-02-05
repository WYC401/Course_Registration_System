package model;
/*
The student class identifies student with id, including the following information: major, name
courses she/he have already taken and the corresponding grades
 */

import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Student {
    private String name;
    private Integer id;
    private String major;
    private HashMap<Integer, CourseOfferedBySemester> courseSetAlreadyTaken;
    private HashMap<Integer, CourseOfferedBySemester> courseSetToTake;
    // EFFECT: Create a student object with student id, name, major and gradeList
    public Student(String name, Integer id, String major) {
        this.id = id;
        this.name = name;
        this.major = major;
        courseSetAlreadyTaken = new HashMap<>();
        courseSetToTake = new HashMap<>();
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
    public boolean addTakenCourse(CourseOfferedBySemester course) {
        if(!isAlreadyTaken(course)) {
            courseSetAlreadyTaken.put(course.getCourseID(),course);
            return true;
        }
        return false;
    }

    public boolean canBeRegistered(CourseOfferedBySemester course) {
        if((!isAlreadyRegistered(course))&&(!isAlreadyTaken(course))) {
            return true;
        }
        return false;
    }
    // MODIFIES: this
    // EFFECT: register one course
    public void registerCourse(CourseOfferedBySemester course) {
        if((!isAlreadyRegistered(course))&&(!isAlreadyTaken(course))) {
            courseSetToTake.put(course.getCourseID(), course);
        }

    }

    public boolean isAlreadyTaken(Course course) {
        if(courseSetAlreadyTaken.containsKey(course.getCourseID())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAlreadyRegistered(Course course) {
        if(courseSetToTake.containsKey(course.getCourseID())) {
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECT: drop the course registered
    public boolean dropCourse(CourseOfferedBySemester course) {
        if(isAlreadyRegistered(course)) {
            courseSetToTake.remove(course);
            return true;
        }
        return false;
    }




    //EFFECT: return all the courses which were taken in previous semester
    private ArrayList<Integer> getTakenCourses() {
        return null;
    }

    //EFFECT: return all the courses registered in this semester
    private ArrayList<Integer> getRegisteredCourses() {
        return null;
    }



}
