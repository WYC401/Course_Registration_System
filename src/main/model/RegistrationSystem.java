package model;
/*
This is a class for course registration system. It should have such functionalities:
(1) a course can be enrolled if it does not conflict with other courses and satisfies the prerequisites
(2) a course registered can be dropped
(3) all the information of a course can be searched and displayed
 */
public class RegistrationSystem {

    // MODIFIERS : this
    // EFFECT: initialize course management system under the hood
    private void loadCourseManagement() {

    }

    // MODIFIES: this
    // EFFECT: If the course has not full and student satisfies all the prerequisites
    //          (1)student gets into the list of enrollment list of input course
    //          (2)the number of course vacancy is deducted by 1
    //          (3)the courses will be added into student's registered list
    //          then return true
    //          else, false;
    public boolean register(CourseOfferedBySemester course, Student student) {
        return false;
    }
    // MODIFIES: this
    // EFFECT: if the student has registered this course,
    //          (1)student gets out the list of enrollment list of input course
    //          (2)the number of course vacancy is increased by 1
    //          (3)the courses will be removed into student's registered list
    public boolean drop(CourseOfferedBySemester course, Student student) {

    }

    // EFFECT: return/display the information of the course
    public void searchCourseInformation(CourseOfferedBySemester course) {

    }

    // MODIFIERS: this
    // EFFECT: add one course available for this semester into the system
    public void addCourseAvailable(CourseOfferedBySemester course) {

    }

    // MODIFIERS: this
    // EFFECT: add one Student into this system
    public void addCourseAvailable(Student student) {

    }




}
