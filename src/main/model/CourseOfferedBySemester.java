package model;

public class CourseOfferedBySemester extends Course{
    private String semester;
    private int seatsRemaining;
    private int seatsTotal;
    CourseOfferedBySemester(String courseName, int courseID, String syllabus, String instructor,
                            String semester, int seatsRemaining, int seatsTotal) {

        super(courseName, courseID, syllabus, instructor);
        this.seatsRemaining = seatsRemaining;
        this.semester = semester;
        this.seatsTotal = seatsTotal;
    }

    // MODIFIERS: this
    // EFFECT: If the input number is greater than people already enrolled, the input is set to be total seats and seats
    // remaining change accordingly. If not, return false.
    public boolean setSeatsTotal(int numSeats){
        int temp = this.seatsRemaining;
        if((numSeats>this.seatsTotal-this.seatsRemaining)||(numSeats==this.seatsTotal-this.seatsRemaining)) {
            this.seatsTotal = numSeats;
            this.seatsRemaining = this.seatsTotal-(temp - this.seatsRemaining);
            return true;
        }
        return false;
    }

    public boolean isFull() {
        if(seatsRemaining<seatsTotal) {
            return false;
        }
        return true;
    }

    public boolean addOneStudent() {
        if(!isFull()) {
            seatsRemaining ++;
            return true;
        }
        return false;
    }




}
