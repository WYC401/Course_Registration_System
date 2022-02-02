package model;
/*
The student class identifies student with id, including the following information: major, name
courses she/he have already taken and the corresponding grades
 */

import java.util.ArrayList;

public class Student {

    // EFFECT: Create a student object with student id, name, major and gradeList
    public Student() {
        //stub
    }

    // EFFECT: return the ID of a student
    public int getId() {
        return 0;
    }

    // EFFECT: return the name of a student
    public String getName() {
        return "Student";
    }

    // EFFECT: get the major the student are in
    public String getMajor() {
        return "CPSC";
    }

    // EFFECT: get the list of Grades
    public ArrayList<Grade> getGradeList() {
        return null;
    }

    // REQUIRE: the grade input should be an integer from 0 to 100.
    // MODIFIES: this
    // EFFECT: add a course into the gradeList
    public void addGrade() {
      //stub
    }

    // MODIFIES: this
    // EFFECT: drop the course registered
    public void dropCourse() {

    }

    // MODIFIES: this
    // EFFECT: register one course
    public void registerCourse() {

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
