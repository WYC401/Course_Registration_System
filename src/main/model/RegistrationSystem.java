package model;

import org.json.JSONObject;
import persistence.Writable;

import java.io.IOException;
import java.util.*;

/*
This is a class for course registration system. It should have such functionalities:
(1) a course can be enrolled if it does not conflict with other courses and satisfies the prerequisites
(2) a course registered can be dropped
(3) all the information of a course can be searched and displayed
 */
public class RegistrationSystem implements Writable {
    private HashMap<Integer, CourseOfferedBySemester> courseMapThisSemester;
    private CourseManagement courseManagementSystem;
    private HashMap<List<String>, Student> studentMapByUsername;

    //EFFECT: create registration system that configures students, course set and course management system
    public RegistrationSystem() {
        courseMapThisSemester = new HashMap<Integer, CourseOfferedBySemester>();
        courseManagementSystem = new CourseManagement();
        studentMapByUsername = new HashMap<>();
    }

    public RegistrationSystem(HashMap<Integer, CourseOfferedBySemester> courseMapThisSemester,
                              HashMap<List<String>, Student> studentMapByUsername,
                              CourseManagement courseManagementSystem) {
        this.courseManagementSystem = courseManagementSystem;
        this.courseMapThisSemester = courseMapThisSemester;
        this.studentMapByUsername = studentMapByUsername;
    }

    // MODIFIERS : this
    // EFFECT: initialize course management system under the hood
    public void loadCourseManagement(CourseManagement courseManagementSystem) {
        this.courseManagementSystem = courseManagementSystem;
    }

    // MODIFIES: this
    // EFFECT: If the course has not full, student satisfies all the prerequisites and student did not take or have not
    //          registered this course:
    //          (1)student gets into the list of enrollment list of input course
    //          (2)the courses will be added into student's registered list
    //          then return true
    //          else, false;
    public boolean register(CourseOfferedBySemester course, Student student) {
        if (hasPrerequisites(course, student) && (canRegisterThisCourse(course, student))
                && (!isCourseFull(course, student))) {
            student.registerCourse(course.getCourseID());
            course.addOneStudent(student);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECT: if the student has registered this course and courses contains this student,
    //          (1)student gets out the list of enrollment list of input course
    //          (2)the courses will be removed into student's registered list
    //          else return false
    public boolean drop(CourseOfferedBySemester course, Student student) {
        if (course.containsStudent(student) && student.isAlreadyRegistered(course.getCourseID())) {
            course.removeStudent(student);
            student.dropCourse(course.getCourseID());
            return true;
        }
        return false;
    }


    // MODIFIERS: this
    // EFFECT: add one course available for this semester into the system
    public boolean addCourseAvailable(CourseOfferedBySemester course) {
        return Objects.isNull(courseMapThisSemester.put(course.getCourseID(), course));
    }

    // MODIFIERS: this
    // EFFECT: add one Student into this system
    public void addStudent(List<String> usernameAndPassword, Student student) {
        studentMapByUsername.put(usernameAndPassword, student);
    }

    //EFFECT: search student by their username and password in this system. if the two matches, a student object will
    //          be returned
    public Student searchStudent(List<String> usernameAndPassword) {
        if (studentMapByUsername.containsKey(usernameAndPassword)) {
            return studentMapByUsername.get(usernameAndPassword);
        }
        return null;
    }

    //EFFECT: return true if the courseSet in this registration system contains this course(by ID)
    public boolean containCourses(Integer courseID) {
        return courseMapThisSemester.keySet().contains(courseID);
    }

    //EFFECT: return the course by its ID if it is in the system
    public CourseOfferedBySemester getCourseFromID(Integer courseID) {
        if (containCourses(courseID)) {
            return courseMapThisSemester.get(courseID);
        }
        return null;

    }

    //EFFECT: return true if the student satisfies the prerequisites of course else return false
    private boolean hasPrerequisites(Course course, Student student) {
        if (student.getAlreadyTakenCourseID()
                .containsAll(courseManagementSystem.returnPrerequisitesID(course.getCourseID()))) {
            return true;
        }
        return false;
    }

    //EFFECT: return true if the class is full
    public boolean isCourseFull(CourseOfferedBySemester course, Student student) {
        return course.isFull();
    }

    //EFFECT: return true if the student did not register previously or have not registered this course in the current
    //          semester
    public boolean canRegisterThisCourse(CourseOfferedBySemester course, Student student) {
        return student.canBeRegistered(course.getCourseID());
    }

    public Set<Integer> getCourseSetInDatabase() {
        return courseManagementSystem.getAllCoursesID();
    }

    public Set<List<Integer>> getAllPrerequisitesRelation() {
        Set<Integer> courseSet = getCourseSetInDatabase();
        Set<List<Integer>> temp = new HashSet<>();
        List<Integer> temptemp;
        for (Integer i : courseSet) {
            for (Integer j : courseManagementSystem.returnPrerequisitesID(i)) {
                temptemp = new ArrayList<>();
                temptemp.add(i);
                temptemp.add(j);
                temp.add(temptemp);
            }
        }
        return temp;
    }

    @Override
    public JSONObject toJson() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courseMapThisSemester", courseMapThisSemesterToJson());
        jsonObject.put("studentMapByUsername", studentMapByUsernameToJson());
        jsonObject.put("courseManagementSystem", courseManagementSystem.toJson());
        return jsonObject;
    }

    public int numberOfCourseThisSemester() {
        return courseMapThisSemester.keySet().size();
    }

    public int numberOfStudent() {
        return studentMapByUsername.size();
    }

    public int numberOfCourseInDatabase() {
        return courseManagementSystem.getAllCoursesID().size();
    }

    private JSONObject studentMapByUsernameToJson() {
        JSONObject jsonObject = new JSONObject();
        for (List<String> ls : studentMapByUsername.keySet()) {
            jsonObject.put(ls.get(0) + " " + ls.get(1), studentMapByUsername.get(ls).toJson());
        }
        return jsonObject;
    }

    private JSONObject courseMapThisSemesterToJson() {
        JSONObject jsonObject = new JSONObject();
        for (Integer i : courseMapThisSemester.keySet()) {
            jsonObject.put(String.valueOf(i), courseMapThisSemester.get(i).toJson());
        }
        return jsonObject;
    }

}
