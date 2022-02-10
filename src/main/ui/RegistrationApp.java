package ui;

import model.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class RegistrationApp {
    private final RegistrationSystem registrationSystemCore;
    private Scanner scanner;
    public RegistrationApp() {
        registrationSystemCore = new RegistrationSystem();
        initi();
        Student studentUsingSystem = login();
        if(Objects.isNull(studentUsingSystem)) {
            System. exit(0);
        }
        while (true) {
            displayMenu();
            scanner = new Scanner(System.in);
            String choice = dealWithChoice();
            if(choice.equals("q")) {
                System. exit(0);
            } else if(choice.equals("r")) {
                goToRegisterPage(studentUsingSystem);
            } else if(choice.equals("d")) {
                goToDropPage(studentUsingSystem);
            } else {
                goToSearchPage(studentUsingSystem);
            }
        }

    }

    private void goToRegisterPage(Student student) {
        String temp = "";
        do {
            System.out.println("Enter the course you want register: ");
            temp = scanner.next();
            int courseID = Integer.parseInt(temp);//it may not be a parsable integer
            if (registrationSystemCore.containCourses(courseID)) {
                CourseOfferedBySemester courseIntendedTaken = registrationSystemCore.getCourseFromID(courseID);
                if(registrationSystemCore.register(courseIntendedTaken, student)) {
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
            do{
                System.out.println("Continue to register or Back to Main Menu");
                System.out.println("\tc-->continue to register");
                System.out.println("\tb-->back to menu");
                temp = scanner.next().toLowerCase();
            } while((!temp.equals("c")) && (!temp.equals("b")));
        } while(temp.equals("c"));
    }

    private void goToSearchPage(Student student) {
        String temp = "";
        do {
            System.out.println("Type the courseID you want to search: ");
            temp = scanner.next();
            Integer courseID = Integer.parseInt(temp);
            if(!registrationSystemCore.containCourses(courseID)) {
                System.out.println("This course does not exist!\n");
            } else {
                CourseOfferedBySemester tempCourse = registrationSystemCore.getCourseFromID(courseID);
                System.out.println(String.format("%-30s %s", "Course Name", tempCourse.getCourseName()));
                System.out.println(String.format("%-30s %s", "CourseID", tempCourse.getCourseID()));
                System.out.println(String.format("%-30s %s", "Instructor", tempCourse.getInstructor()));
                System.out.println(String.format("%-30s %s", "Seats Total", tempCourse.getSeatsTotal()));
                System.out.println(String.format("%-30s %s", "Seats Remaining", tempCourse.getSeatsRemaining()));
                System.out.println("Syllabus:");
                System.out.println(tempCourse.getSyllabus()+"\n");
            }
            do{
                System.out.println("Continue to register or Back to Main Menu");
                System.out.println("\tc-->continue to register");
                System.out.println("\tb-->back to menu");
                temp = scanner.next().toLowerCase();
            } while((!temp.equals("c")) && (!temp.equals("b")));
        } while(temp.equals("c"));
    }

    private void goToDropPage(Student student) {
        String temp;
        do {
            System.out.println("Type in courseID you want to drop: ");
            temp = scanner.next();//TODO:why .nextline() does not work
            Integer courseID = Integer.parseInt(temp);
            if(!registrationSystemCore.containCourses(courseID)) {
                System.out.println("This course does not exist!\n");
            } else {
                CourseOfferedBySemester tempCourse = registrationSystemCore.getCourseFromID(courseID);
                if(registrationSystemCore.drop(tempCourse, student)) {
                    System.out.println("Successfully drop this course!\n");
                } else {
                    System.out.println("You have not registered this course!\n");
                }
            }
            do{
                System.out.println("Continue to drop or Back to Main Menu");
                System.out.println("\tc-->continue to drop");
                System.out.println("\tb-->back to menu");
                temp = scanner.next().toLowerCase();
            } while((!temp.equals("c")) && (!temp.equals("b")));
        } while(temp.equals("c"));
    }
    private String dealWithChoice() {
        String choice = "";
        while((!choice.equals("q")) && (!choice.equals("d")) && (!choice.equals("r")) && (!choice.equals("s")))  {
            choice = scanner.next();
            choice = choice.toLowerCase();
        }
        return choice;
    }
    private Student login() {
        System.out.println("Welcome to course registration system");
        String usernameGot = "";
        String passwordGot = "";
        Student student;

        while(true) {
            System.out.println("Please enter your username:");
            Scanner sc = new Scanner(System.in);
            usernameGot = sc.nextLine();
            System.out.println("Please enter your password:");
            //sc.nextLine();
            passwordGot= sc.nextLine();
            student = registrationSystemCore.searchStudent(Arrays.asList(usernameGot,passwordGot));
            if(Objects.isNull(student)) {
                System.out.print("Press q to quit, otherwise try again");
                String choice = sc.next();
                if(choice.equals("q")) {
                    return null;
                }
            } else {
                return student;
            }
        }
    }

    private void displayMenu() {
        System.out.println("Welcome back!");
        System.out.println("Select From: ");
        System.out.println("\td-->drop registered courses");
        System.out.println("\ts-->search for certain course");
        System.out.println("\tr-->register courses");
        System.out.println("\tq-->quit the system");
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
        registrationSystemCore.addCourseAvailable(cpsc213ThisSemester);
        registrationSystemCore.addCourseAvailable(cpsc313ThisSemester);
        registrationSystemCore.addCourseAvailable(cpsc110ThisSemester);
        registrationSystemCore.addCourseAvailable(cpsc210ThisSemester);

    }
}

