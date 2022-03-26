package ui;

import model.*;
import model.Event;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import java.awt.*;
import java.util.Objects;

/*
    This is a GUI registrationApp. This class mainly serves as the login page and pop into menu window if
    login is successful
 */
public class RegistrationApp2 extends JFrame implements ActionListener {
    private RegistrationSystem registrationSystemCore;
    JLabel welcome = new JLabel("Welcome to Registration System!");
    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JPasswordField passwordField = new JPasswordField();
    JTextField usernameField = new JTextField();
    JButton loginButton = new JButton("Login");
    JCheckBox ifShowPassword = new JCheckBox("Show Password");
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;



    // EFFECT: create a new registration system app
    public RegistrationApp2() {
        registrationSystemCore = new RegistrationSystem();
        initialCore();
        initialLoginPage();

    }

    // MODIFIES: this
    // EFFECT: initialize a login page with button and texfield for password and username input
    private void initialLoginPage() {

        setLayout(null);
        setTitle("Login Page");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        welcome.setBounds(50, 50, 400, 50);
        welcome.setFont(new Font("Courier", Font.BOLD, 20));
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        usernameField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        ifShowPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        add(welcome);
        add(userLabel);
        add(passwordField);
        add(passwordLabel);
        add(usernameField);
        add(loginButton);
        add(ifShowPassword);
        loginButton.addActionListener(this);
        ifShowPassword.addActionListener(this);


    }

    //MODIFIERS: this
    //EFFECT: initialize the registration system
    private void initialCore() {
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

    // EFFECT: return the reference of registrationSystemCore
    public RegistrationSystem getRegistrationSystemCore() {
        return registrationSystemCore;
    }

    // MODIFIES: this
    // EFFECT: if the checkbox of "show password" is checked, the password input will no longer be encoded.
    //         if the button is clicked, password and username will be verified to match or not
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            Student student = registrationSystemCore.searchStudent(Arrays.asList(usernameField.getText(),
                    passwordField.getText()));
            if (Objects.isNull(student)) {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            } else {
                JFrame secondframe = new MenueUI(registrationSystemCore, student, Arrays.asList(usernameField.getText(),
                        passwordField.getText()));
                secondframe.setVisible(true);
                dispose();
            }
        } else if (e.getSource() == ifShowPassword) {
            if (ifShowPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }

    }
}
