package ui;

import model.*;

import java.util.Arrays;

public class RegistrationApp {
    private final RegistrationSystem registrationSystemCore;

    public RegistrationApp() {
        registrationSystemCore = new RegistrationSystem();
        initi();
        displayMenu();

    }

    private void displayMenu() {
        System.out.println("Welcome to course registration system");
        System.out.println("Please enter your username:");

    }

    private void initi() {
        CourseManagement CM = new CourseManagement();
        Course cpsc213 = new Course("Computer System", 213, "This is a syllabus", "Meghan");
        Course cpsc210 = new Course("Software Construction", 210, "This is a syllabus", "Meghan");
        Course cpsc313 = new Course("Operating System", 313, "This is a syllabus", "Meghan");
        Course cpsc110 = new Course("Programming", 110, "This is a syllabus", "Meghan");
        Course cpsc221 = new Course("Basic Data Structure And Algorithm", 221, "This is a syllabus", "Meghan");
        Course cpsc310 = new Course("Introduction to Software Engineer", 310, "This is a syllabus", "Meghan");
        CM.addCourses(cpsc213);
        CM.addCourses(cpsc210);
        CM.addCourses(cpsc313);
        CM.addCourses(cpsc110);
        CM.addCourses(cpsc221);
        CM.setPrerequisites(cpsc210, cpsc110);
        CM.setPrerequisites(cpsc213, cpsc210);
        CM.setPrerequisites(cpsc313, cpsc213);
        CM.setPrerequisites(cpsc313, cpsc221);
        CM.setPrerequisites(cpsc310, cpsc221);
        CM.setPrerequisites(cpsc310, cpsc213);
        registrationSystemCore.loadCourseManagement(CM);
        addStudent(registrationSystemCore);
        addCourseThisSemester(registrationSystemCore);

    }
    private void addStudent(RegistrationSystem registrationSystemCore) {
        Student yicheng = new Student("Yicheng Wang",1,"Statistics");
        Student chenyang = new Student("Chenyang Li", 2, "History");
        Student richard = new Student("Richard Yang", 3, "Computer Science");
        registrationSystemCore.addStudent(Arrays.asList("yicheng2021","wycwyc"),yicheng);
        registrationSystemCore.addStudent(Arrays.asList("chenyang2018","lcylcy"),chenyang);
        registrationSystemCore.addStudent(Arrays.asList("richard2019","yzhyzh"),richard);
        CourseOfferedBySemester cpsc213PreviousSemester = new CourseOfferedBySemester("Computer System",213,
                "This is a syllabus","Meghan","2021W1",2,60);
        CourseOfferedBySemester cpsc210PreviousSemester = new CourseOfferedBySemester("Software Construction",210,
                "This is a syllabus","Meghan","2021W1",2,70);
        CourseOfferedBySemester cpsc110PreviousSemester = new CourseOfferedBySemester("Programming",110,
                "This is a syllabus","Meghan","2021W1",2,60);
        CourseOfferedBySemester cpsc221PreviousSemester = new CourseOfferedBySemester("Basic Data Structure And Algorithm",221,
                "This is a syllabus","Meghan","2021W1",2,60);
        yicheng.addTakenCourse(cpsc210PreviousSemester);
        richard.addTakenCourse(cpsc110PreviousSemester);
        richard.addTakenCourse(cpsc213PreviousSemester);
        richard.addTakenCourse(cpsc221PreviousSemester);
    }

    private void addCourseThisSemester(RegistrationSystem registrationSystemCore) {
        CourseOfferedBySemester cpsc213ThisSemester = new CourseOfferedBySemester("ComputerSystem",213,
                "This is a syllabus","Meghan","2021W1",2,-1);
        CourseOfferedBySemester cpsc313ThisSemester = new CourseOfferedBySemester("ComputerSystem",313,
                "This is a syllabus","Meghan","2021W1",2,-1);
        CourseOfferedBySemester cpsc110ThisSemester = new CourseOfferedBySemester("ComputerSystem",110,
                "This is a syllabus","Meghan","2021W1",2,-1);
        CourseOfferedBySemester cpsc210ThisSemester = new CourseOfferedBySemester("ComputerSystem",210,
                "This is a syllabus","Meghan","2021W1",2,-1);
    }
}

