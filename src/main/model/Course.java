package model;
/*
This is representation of a course, including name of course, courseID, syllabus, instructor and section
 */
public class Course {
    // EFFECT: create a new course object
    private String courseName;
    private Integer courseID;
    private String syllabus;
    private String instructor;

    Course(String courseName, int courseID, String syllabus, String instructor) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.syllabus = syllabus;
        this.instructor = instructor;
    }
    public String getCourseName() {
        return courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public void setInstructor() {
        this.instructor = instructor;
    }


}
