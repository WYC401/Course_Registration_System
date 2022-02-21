package model;
/*
The student class identifies student with id, including the following information: major, name
courses she/he have already taken and course already registered
 */


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

public class Student implements Writable {
    private String name;
    private Integer id;
    private String major;
    private HashMap<Integer, CourseOfferedBySemester> courseMapAlreadyTaken;
    private HashMap<Integer, CourseOfferedBySemester> courseMapToTake;

    // EFFECT: Create a student object with student id, name, major and gradeList
    public Student(String name, Integer id, String major) {
        this.id = id;
        this.name = name;
        this.major = major;
        courseMapAlreadyTaken = new HashMap<>();
        courseMapToTake = new HashMap<>();
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
        if (!isAlreadyTaken(course)) {
            courseMapAlreadyTaken.put(course.getCourseID(), course);
            return true;
        }
        return false;
    }

    //EFFECT: verify student's access to register the input by their courses taken in previous semesters and courses
    //        registered in this semester. return true if they did not take the course and
    //        have not registered this course
    public boolean canBeRegistered(CourseOfferedBySemester course) {
        if ((!isAlreadyRegistered(course)) && (!isAlreadyTaken(course))) {
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECT: register one course if they did not take the course and have not registered this course
    public void registerCourse(CourseOfferedBySemester course) {
        if ((!isAlreadyRegistered(course)) && (!isAlreadyTaken(course))) {
            courseMapToTake.put(course.getCourseID(), course);
        }

    }

    //EFFECT: return true if student have already taken this course in previous semesters
    public boolean isAlreadyTaken(Course course) {
        if (courseMapAlreadyTaken.containsKey(course.getCourseID())) {
            return true;
        } else {
            return false;
        }
    }

    //EFFECT: return true if students have registered this course in this semester
    public boolean isAlreadyRegistered(Course course) {
        if (courseMapToTake.containsKey(course.getCourseID())) {
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECT: drop the course registered and return true if the course is not registered
    public boolean dropCourse(CourseOfferedBySemester course) {
        if (isAlreadyRegistered(course)) {
            courseMapToTake.remove(course);
            return true;
        }
        return false;
    }


    //EFFECT: return all the courses in the format of set which were taken in previous semester
    public Set<CourseOfferedBySemester> getTakenCourses() {
        Collection<CourseOfferedBySemester> tempCollection = courseMapAlreadyTaken.values();
        HashSet<CourseOfferedBySemester> tempSet = new HashSet<>();
        for (CourseOfferedBySemester coursethissemester : tempCollection) {
            tempSet.add(coursethissemester);
        }
        return tempSet;
    }

    //EFFECT: return all the courses ID which are registered this semester
    public Set<Integer> getAlreadyRegisteredID() {
        return courseMapToTake.keySet();
    }

    //EFFECT: return all the courses ID which were taken before
    public Set<Integer> getAlreadyTakenCourseID() {
        return courseMapAlreadyTaken.keySet();
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", getName());
        jsonObject.put("id", getId());
        jsonObject.put("major", getMajor());
        jsonObject.put("courseMapAlreadyTaken", courseMapAlreadyTakenToJson());
        jsonObject.put("courseMapToTake", courseMapToTakeToJson());
        return jsonObject;
    }

    private JSONObject courseMapAlreadyTakenToJson() {
        JSONObject jsonObject = new JSONObject();
        for(Integer i: courseMapAlreadyTaken.keySet()) {
            jsonObject.put(String.valueOf(i), courseMapAlreadyTaken.get(i).toJson());
        }
        return jsonObject;
    }

    private JSONObject courseMapToTakeToJson() {
        JSONObject jsonObject = new JSONObject();
        for(Integer i: courseMapToTake.keySet()) {
            jsonObject.put(String.valueOf(i), courseMapToTake.get(i).toJson());
        }
        return jsonObject;
    }

}
