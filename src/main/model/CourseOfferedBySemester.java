package model;

import java.util.ArrayList;
import java.util.List;

public class CourseOfferedBySemester extends Course {
    private String semester;
    private int seatsRemaining;
    private int seatsTotal;
    private int grade;
    private ArrayList<Student> studentsRegistered;

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
    // remaining change accordingly. If not, return false.
    public boolean setSeatsTotal(int numSeats) {
        int numRegistered = getSeatsTotal() - getSeatsRemaining();
        if ((numSeats > getSeatsTotal() - getSeatsRemaining()) || (numSeats == getSeatsTotal() - getSeatsRemaining())) {
            this.seatsTotal = numSeats;
            this.seatsRemaining = this.seatsTotal - numRegistered;
            return true;
        }
        return false;
    }

    public boolean isFull() {
        if (seatsRemaining == 0) {
            return true;
        }
        return false;
    }


    public void addOneStudent(Student student) {
        if (!isFull()) {
            seatsRemaining--;
            studentsRegistered.add(student);
        }
    }

    public boolean containsStudent(Student student) {
        if (studentsRegistered.contains(student)) {
            return true;
        }
        return false;
    }

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

    public List<Student> getStudentRegistered() {
        return studentsRegistered;
    }

}
