package ui;

import model.*;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

/*
This is a registration APP you can play with, incorporating the functionalities like searching, adding, dropping
and viewing courses
 */
public class RegistrationApp {
    private RegistrationSystem registrationSystemCore;
    private Scanner scanner;
    private static final String CORE_PATH = "./data/registrationSystemCore.json";
    private JsonWriter writer;
    private JsonReader reader;

    //EFFECT: create a registration app
    public RegistrationApp() {
        registrationSystemCore = new RegistrationSystem();
        writer = new JsonWriter(CORE_PATH);
        reader = new JsonReader(CORE_PATH);
        initi();
        Student studentUsingSystem = login();
        if (Objects.isNull(studentUsingSystem)) {
            System.exit(0);
        }
        menuOperation(studentUsingSystem);

    }

    private void menuOperation(Student studentUsingSystem) {
        while (true) {
            displayMenu();
            scanner = new Scanner(System.in);
            String choice = dealWithChoice();
            if (choice.equals("q")) {
                System.exit(0);
            } else if (choice.equals("r")) {
                goToRegisterPage(studentUsingSystem);
            } else if (choice.equals("d")) {
                goToDropPage(studentUsingSystem);
            } else if (choice.equals("s")) {
                goToSearchPage(studentUsingSystem);
            } else if (choice.equals("p")) {
                saveFile();
            } else if (choice.equals("l")) {
                loadFile();
            } else {
                goToViewPage(studentUsingSystem);
            }
        }
    }


    private void saveFile() {
        try {
            writer.open();
            writer.write(registrationSystemCore);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("No such file: " + CORE_PATH);
        } catch (IOException e) {
            System.out.println("No directory named \"data\"");
        }
    }


    private void loadFile() {
        try {
            registrationSystemCore = reader.read();
            System.out.println("File loaded from " + CORE_PATH);
        } catch (IOException e) {
            System.out.println("Unable to read file: " + CORE_PATH);
        }
    }

    //MODIFIERS: this
    //EFFECT: display the registration page if user choose to go to
    private void goToRegisterPage(Student student) {
        String temp = "";
        do {
            System.out.println("Enter the course you want register: ");
            temp = scanner.next();
            int courseID = Integer.parseInt(temp);//it may not be a parsable integer
            if (registrationSystemCore.containCourses(courseID)) {
                CourseOfferedBySemester courseIntendedTaken = registrationSystemCore.getCourseFromID(courseID);
                if (registrationSystemCore.register(courseIntendedTaken, student)) {
                    System.out.println(" Successfully Registered!\n");
                } else if (registrationSystemCore.isCourseFull(courseIntendedTaken, student)) {
                    System.out.println(" The course is full!\n");
                } else if (!registrationSystemCore.canRegisterThisCourse(courseIntendedTaken, student)) {
                    System.out.println(" You have registered or taken this course!\n");
                } else {
                    System.out.println(" You do not satisfy prerequisites of the course!\n");
                }
            } else {
                System.out.println("The course does not exist!\n");
            }
            do {
                temp = displaySubPageInformation("register");
            } while ((!temp.equals("c")) && (!temp.equals("b")));
        } while (temp.equals("c"));
    }

    //EFFECT: display the menu page in search, view, drop, register mode and return the user's decision of that page
    private String displaySubPageInformation(String mode) {
        System.out.println("Continue to register or Back to Main Menu");
        System.out.println("\tc-->continue to " + mode);
        System.out.println("\tb-->back to menu");
        return scanner.next().toLowerCase();
    }

    //MODIFIERS: this
    //EFFECT: display the search page if user choose to go to
    private void goToSearchPage(Student student) {
        String temp = "";
        do {
            System.out.println("Type the courseID you want to search: ");
            temp = scanner.next();
            Integer courseID = Integer.parseInt(temp);
            if (!registrationSystemCore.containCourses(courseID)) {
                System.out.println("This course does not exist!\n");
            } else {
                CourseOfferedBySemester tempCourse = registrationSystemCore.getCourseFromID(courseID);
                System.out.println(String.format("%-30s %s", "Course Name", tempCourse.getCourseName()));
                System.out.println(String.format("%-30s %s", "CourseID", tempCourse.getCourseID()));
                System.out.println(String.format("%-30s %s", "Instructor", tempCourse.getInstructor()));
                System.out.println(String.format("%-30s %s", "Seats Total", tempCourse.getSeatsTotal()));
                System.out.println(String.format("%-30s %s", "Seats Remaining", tempCourse.getSeatsRemaining()));
                System.out.println("Syllabus:");
                System.out.println(tempCourse.getSyllabus() + "\n");
            }
            do {
                temp = displaySubPageInformation("search");
            } while ((!temp.equals("c")) && (!temp.equals("b")));
        } while (temp.equals("c"));
    }

    //MODIFIERS: this
    //EFFECT: display the drop page if user choose to go to
    private void goToDropPage(Student student) {
        String temp;
        do {
            System.out.println("Type in courseID you want to drop: ");
            temp = scanner.next();//TODO:why .nextline() does not work
            Integer courseID = Integer.parseInt(temp);
            if (!registrationSystemCore.containCourses(courseID)) {
                System.out.println("This course does not exist!\n");
            } else {
                CourseOfferedBySemester tempCourse = registrationSystemCore.getCourseFromID(courseID);
                if (registrationSystemCore.drop(tempCourse, student)) {
                    System.out.println("Successfully drop this course!\n");
                } else {
                    System.out.println("You have not registered this course!\n");
                }
            }
            do {
                temp = displaySubPageInformation("drop");
            } while ((!temp.equals("c")) && (!temp.equals("b")));
        } while (temp.equals("c"));
    }

    //MODIFIERS: this
    //EFFECT: display the view page if user choose to go to
    private void goToViewPage(Student student) {
        String temp;
        do {
            System.out.println("Here are all the courses you already registered:");
            System.out.println(student.getAlreadyRegisteredID());
            temp = displaySubPageInformation("view");

        } while (temp.equals("c"));
    }

    //EFFECT: deal with choice user pressed in. if it is not included in the menu, repeat the menu information again
    //          until user press one of the keys in menu
    private String dealWithChoice() {
        String choice = scanner.next().toLowerCase();
        while ((!choice.equals("q")) && (!choice.equals("d")) && (!choice.equals("r")) && (!choice.equals("s"))
                && (!choice.equals("v")) && (!choice.equals("p")) && (!choice.equals("l"))) {
            displayMenu();
            choice = scanner.next();
            choice = choice.toLowerCase();
        }
        return choice;
    }

    //EFFECT: check in users with right names
    private Student login() {
        System.out.println("Welcome to course registration system");
        String usernameGot = "";
        String passwordGot = "";
        Student student;

        while (true) {
            System.out.println("Please enter your username:");
            Scanner sc = new Scanner(System.in);
            usernameGot = sc.nextLine();
            System.out.println("Please enter your password:");
            //sc.nextLine();
            passwordGot = sc.nextLine();
            student = registrationSystemCore.searchStudent(Arrays.asList(usernameGot, passwordGot));
            if (Objects.isNull(student)) {
                System.out.print("Press q to quit, otherwise try again");
                String choice = sc.next();
                if (choice.equals("q")) {
                    return null;
                }
            } else {
                return student;
            }
        }
    }

    //EFFECT: print out menu information
    private void displayMenu() {
        System.out.println("Welcome back!");
        System.out.println("Select From: ");
        System.out.println("\td-->drop registered courses");
        System.out.println("\ts-->search for certain course");
        System.out.println("\tr-->register courses");
        System.out.println("\tq-->quit the system");
        System.out.println("\tv-->view all registered courses");
        System.out.println("\tp-->persist the data");
        System.out.println("\tl-->load the data");
    }

    //MODIFIERS: this
    //EFFECT: initialize the registration system
    private void initi() {
        CourseManagement courseManagement = new CourseManagement();
        Course cpsc213 = new Course("Computer System", 213, "This is a syllabus", "Meghan");
        Course cpsc210 = new Course("Software Construction", 210, "This is a syllabus", "Meghan");
        Course cpsc313 = new Course("Operating System", 313, "This is a syllabus", "Meghan");
        Course cpsc110 = new Course("Programming", 110, "This is a syllabus", "Meghan");
        Course cpsc221 = new Course("Basic Data Structure And Algorithm", 221, "This is a syllabus", "Meghan");
        Course cpsc310 = new Course("Introduction to Software Engineer", 310, "This is a syllabus", "Meghan");
        courseManagement.addCourses(cpsc213);
        courseManagement.addCourses(cpsc210);
        courseManagement.addCourses(cpsc313);
        courseManagement.addCourses(cpsc110);
        courseManagement.addCourses(cpsc221);
        courseManagement.setPrerequisites(cpsc210, cpsc110);
        courseManagement.setPrerequisites(cpsc213, cpsc210);
        courseManagement.setPrerequisites(cpsc313, cpsc213);
        courseManagement.setPrerequisites(cpsc313, cpsc221);
        courseManagement.setPrerequisites(cpsc310, cpsc221);
        courseManagement.setPrerequisites(cpsc310, cpsc213);
        registrationSystemCore.loadCourseManagement(courseManagement);
        addStudent(registrationSystemCore);
        addCourseThisSemester(registrationSystemCore);

    }

    //MODIFIERS: this
    //EFFECT: add students into registration system
    private void addStudent(RegistrationSystem registrationSystemCore) {
        Student yicheng = new Student("Yicheng Wang", 1, "Statistics");
        Student chenyang = new Student("Chenyang Li", 2, "History");
        Student richard = new Student("Richard Yang", 3, "Computer Science");
        registrationSystemCore.addStudent(Arrays.asList("yicheng2021", "wycwyc"), yicheng);
        registrationSystemCore.addStudent(Arrays.asList("chenyang2018", "lcylcy"), chenyang);
        registrationSystemCore.addStudent(Arrays.asList("richard2019", "yzhyzh"), richard);
        CourseOfferedBySemester cpsc213PreviousSemester = new CourseOfferedBySemester("Computer System", 213,
                "This is a syllabus", "Meghan", "2021W1", 2, 60);
        CourseOfferedBySemester cpsc210PreviousSemester = new CourseOfferedBySemester("Software Construction", 210,
                "This is a syllabus", "Meghan", "2021W1", 2, 70);
        CourseOfferedBySemester cpsc110PreviousSemester = new CourseOfferedBySemester("Programming", 110,
                "This is a syllabus", "Meghan", "2021W1", 2, 60);
        CourseOfferedBySemester cpsc221PreviousSemester = new CourseOfferedBySemester(
                "Basic Data Structure And Algorithm", 221,
                "This is a syllabus", "Meghan", "2021W1", 2, 60);
        yicheng.addTakenCourse(cpsc210PreviousSemester.getCourseID());
        richard.addTakenCourse(cpsc110PreviousSemester.getCourseID());
        richard.addTakenCourse(cpsc213PreviousSemester.getCourseID());
        richard.addTakenCourse(cpsc221PreviousSemester.getCourseID());
    }

    //MODIFIERS: this
    //EFFECT: add courses into the system
    private void addCourseThisSemester(RegistrationSystem registrationSystemCore) {
        CourseOfferedBySemester cpsc213ThisSemester = new CourseOfferedBySemester("ComputerSystem", 213,
                "This is a syllabus", "Meghan", "2021W1", 2, -1);
        CourseOfferedBySemester cpsc313ThisSemester = new CourseOfferedBySemester("ComputerSystem", 313,
                "This is a syllabus", "Meghan", "2021W1", 2, -1);
        CourseOfferedBySemester cpsc110ThisSemester = new CourseOfferedBySemester("ComputerSystem", 110,
                "This is a syllabus", "Meghan", "2021W1", 2, -1);
        CourseOfferedBySemester cpsc210ThisSemester = new CourseOfferedBySemester("ComputerSystem", 210,
                "This is a syllabus", "Meghan", "2021W1", 2, -1);
        registrationSystemCore.addCourseAvailable(cpsc213ThisSemester);
        registrationSystemCore.addCourseAvailable(cpsc313ThisSemester);
        registrationSystemCore.addCourseAvailable(cpsc110ThisSemester);
        registrationSystemCore.addCourseAvailable(cpsc210ThisSemester);

    }
}

