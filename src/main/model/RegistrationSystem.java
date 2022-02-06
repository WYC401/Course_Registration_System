package model;

import java.util.HashSet;
import java.util.Set;

/*
This is a class for course registration system. It should have such functionalities:
(1) a course can be enrolled if it does not conflict with other courses and satisfies the prerequisites
(2) a course registered can be dropped
(3) all the information of a course can be searched and displayed
 */
public class RegistrationSystem {
    private Set<CourseOfferedBySemester> courseSetThisSemester;
    private CourseManagement courseManagementSystem;

    public RegistrationSystem() {
        courseSetThisSemester = new HashSet<CourseOfferedBySemester>();
        courseManagementSystem = new CourseManagement();
    }
    // MODIFIERS : this
    // EFFECT: initialize course management system under the hood
    public void loadCourseManagement(CourseManagement CM) {
        courseManagementSystem = CM;
    }

    // MODIFIES: this
    // EFFECT: If the course has not full and student satisfies all the prerequisites
    //          (1)student gets into the list of enrollment list of input course
    //          (2)the number of course vacancy is deducted by 1
    //          (3)the courses will be added into student's registered list
    //          then return true
    //          else, false;
    public boolean register(CourseOfferedBySemester course, Student student) {
        if(hasPrerequisites(course,student) && (student.canBeRegistered(course)) && (!course.isFull())) {
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
        if( course.containsStudent(student) && student.isAlreadyRegistered(course)) {
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
        return courseSetThisSemester.add(course);
    }

    // MODIFIERS: this
    // EFFECT: add one Student into this system
    public void addStudent(Student student) {

    }



    private boolean hasPrerequisites(Course course, Student student) {
        Set<Integer> temp1 = student.getAlreadyTakenCourseID();
        Set<Integer> temp2 = courseManagementSystem.returnPrerequisitesID(course.getCourseID());

        if(student.getAlreadyTakenCourseID().containsAll(courseManagementSystem.returnPrerequisitesID(course.getCourseID()))) {

            return true;
        }
        return false;
    }


}
