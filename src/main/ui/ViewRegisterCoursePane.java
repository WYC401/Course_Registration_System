package ui;

import model.RegistrationSystem;
import model.Student;

/*
    This class is to represent the courses registered
 */
public class ViewRegisterCoursePane extends PresentInformationPane {

    // EFFECT: create a new view panel
    public ViewRegisterCoursePane(RegistrationSystem registrationSystemCore, Student student) {
        super(registrationSystemCore, student);
    }
}
