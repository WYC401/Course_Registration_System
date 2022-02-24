package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
This class extends course to give specific information about courses in each semester, which will include interaction
with students who take or took this courses.
 */
public class CourseOfferedBySemester extends Course {
    private String semester;
    private int seatsRemaining;
    private int seatsTotal;
    private int grade;
    private ArrayList<Student> studentsRegistered;

    //EFFECT: construct a course object in specific semester
    public CourseOfferedBySemester(String courseName, int courseID, String syllabus, String instructor,
                                   String semester, int seatsTotal, int grade) {

        super(courseName, courseID, syllabus, instructor);
        this.seatsRemaining = seatsTotal;
        this.semester = semester;
        this.seatsTotal = seatsTotal;
        this.grade = grade;
        studentsRegistered = new ArrayList<Student>();
    }


    // MODIFIERS: this
    // EFFECT: If the input number is greater than people already enrolled, the input is set to be total seats and seats
    //          remaining change accordingly. If not, return false.
    public boolean setSeatsTotal(int numSeats) {
        int numRegistered = getSeatsTotal() - getSeatsRemaining();
        if ((numSeats > getSeatsTotal() - getSeatsRemaining()) || (numSeats == getSeatsTotal() - getSeatsRemaining())) {
            this.seatsTotal = numSeats;
            this.seatsRemaining = this.seatsTotal - numRegistered;
            return true;
        }
        return false;
    }

    //EFFECT: return true if there are remaining seats
    public boolean isFull() {
        if (seatsRemaining == 0) {
            return true;
        }
        return false;
    }

    //MODIFIERS:this
    //EFFECT: add one student to course and the remaining seats decrease by 1
    public void addOneStudent(Student student) {
        seatsRemaining--;
        studentsRegistered.add(student);
    }

    //EFFECT: return true if the student has been added into course
    public boolean containsStudent(Student student) {
        if (studentsRegistered.contains(student)) {
            return true;
        }
        return false;
    }

    //MODIFIERS: this
    //EFFECT: remove students from registration set of the course and the remaining seats increment by 1
    public void removeStudent(Student student) {
        if (containsStudent(student)) {
            studentsRegistered.remove(student);
        }
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getSemester() {
        return semester;
    }

    public int getSeatsTotal() {
        return seatsTotal;
    }

    public int getSeatsRemaining() {
        return seatsRemaining;
    }

    public void setSeatsRemaining(int num) {
        this.seatsRemaining = num;
    }

    //EFFECT: return the list of students who registered this course
    public List<Student> getStudentRegistered() {
        return studentsRegistered;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("semester", getSemester());
        jsonObject.put("seatsRemaining", getSeatsRemaining());
        jsonObject.put("seatsTotal", getSeatsTotal());
        jsonObject.put("grade", getGrade());
        jsonObject.put("studentsRegistered", studentArrayToJson());
        return jsonObject;

    }

    private JSONArray studentArrayToJson() {
       JSONArray jsonArray = new JSONArray();
       for(Student s: studentsRegistered) {
           jsonArray.put(s.toJson());
       }
       return jsonArray;
    }


}
