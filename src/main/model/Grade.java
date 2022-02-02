package model;
/*
Single grade of one course which is taken
 */
public class Grade {

    //EFFECT: generate a new grade of one course
    Grade() {

    }
    // EFFECT: return mark of a course
    public double getMark() {
        return 0.0;
    }


    // MODIFIES: this
    // EFFECT: set the mark of the course
    public void setMark() {
        //stub
    }

    // EFFECT: return the time when the course was taken or is now been taken
    public String getTime() {
        return "This Semester";
    }

    // MODIFIES
    // EFFECT: set the time/semester in which the course was taken
    public void setTime() {
        //stub
    }
}
