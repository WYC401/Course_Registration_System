package model;

import java.util.*;

/*
This is a class for course registration system. It should have such functionalities:
(1) a course can be enrolled if it does not conflict with other courses and satisfies the prerequisites
(2) a course registered can be dropped
(3) all the information of a course can be searched and displayed
 */
public class RegistrationSystem {
    private HashMap<Integer, CourseOfferedBySemester> courseSetThisSemester;
    private CourseManagement courseManagementSystem;
    private HashMap<List<String>, Student> studentMapByUsername;

    public RegistrationSystem() {
        courseSetThisSemester = new HashMap<Integer, CourseOfferedBySemester>();
        courseManagementSystem = new CourseManagement();
        studentMapByUsername = new HashMap<>();
    }

    // MODIFIERS : this
    // EFFECT: initialize course management system under the hood
    public void loadCourseManagement(CourseManagement courseManagementSystem) {
        this.courseManagementSystem = courseManagementSystem;
    }

    // MODIFIES: this
    // EFFECT: If the course has not full and student satisfies all the prerequisites
    //          (1)student gets into the list of enrollment list of input course
    //          (2)the number of course vacancy is deducted by 1
    //          (3)the courses will be added into student's registered list
    //          then return true
    //          else, false;
    public boolean register(CourseOfferedBySemester course, Student student) {
        if (hasPrerequisites(course, student) && (canRegisterThisCourse(course, student))
                && (!isCourseFull(course, student))) {
            student.registerCourse(course);
            course.addOneStudent(student);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECT: if the student has registered this course,
    //          (1)student gets out the list of enrollment list of input course
    //          (2)the number of course vacancy is increased by 1
    //          (3)the courses will be removed into student's registered list
    public boolean drop(CourseOfferedBySemester course, Student student) {
        if (course.containsStudent(student) && student.isAlreadyRegistered(course)) {
            course.removeStudent(student);
            student.dropCourse(course);
            return true;
        }
        return false;
    }

    // EFFECT: return/display the information of the course
    public void searchCourseInformation(CourseOfferedBySemester course) {

    }

    // MODIFIERS: this
    // EFFECT: add one course available for this semester into the system
    public boolean addCourseAvailable(CourseOfferedBySemester course) {
        return Objects.isNull(courseSetThisSemester.put(course.getCourseID(), course));
    }

    // MODIFIERS: this
    // EFFECT: add one Student into this system
    public void addStudent(List<String> usernameAndPassword, Student student) {
        studentMapByUsername.put(usernameAndPassword, student);
    }

    public Student searchStudent(List<String> usernameAndPassword) {
        if (studentMapByUsername.containsKey(usernameAndPassword)) {
            return studentMapByUsername.get(usernameAndPassword);
        }
        return null;
    }

    public boolean containCourses(Integer courseID) {
        return courseSetThisSemester.keySet().contains(courseID);
    }

    public CourseOfferedBySemester getCourseFromID(Integer courseID) {
        if (containCourses(courseID)) {
            return courseSetThisSemester.get(courseID);
        }
        return null;

    }


    private boolean hasPrerequisites(Course course, Student student) {
        if (student.getAlreadyTakenCourseID()
                        .containsAll(courseManagementSystem.returnPrerequisitesID(course.getCourseID()))) {
            return true;
        }
        return false;
    }

    public boolean isCourseFull(CourseOfferedBySemester course, Student student) {
        return course.isFull();
    }

    public boolean canRegisterThisCourse(CourseOfferedBySemester course, Student student) {
        return student.canBeRegistered(course);
    }

}
