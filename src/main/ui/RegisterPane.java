package ui;

import model.RegistrationSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

/*
    This is register panel where user can type in number of courseID and then register it if he/she can register
 */
public class RegisterPane extends JPanel implements ActionListener {
    private RegistrationSystem registrationSystemCore;
    private Student student;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JTextField courseIDBox;
    private JButton button;
    private JLabel errorMessage;

    // EFFECT: create a register panel
    public RegisterPane(RegistrationSystem registrationSystemCore, Student student) {
        this.registrationSystemCore = registrationSystemCore;
        this.student = student;
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
        setLayout(null);
        JLabel title = new JLabel("Register!");
        title.setBounds(50, 50, 400, 50);
        title.setFont(new Font("Courier", Font.BOLD, 20));
        add(title);

        courseIDBox = new JTextField();
        courseIDBox.setBounds(100, 100, 100, 20);
        add(courseIDBox);

        button = new JButton("Register");
        button.setBounds(100, 120, 100, 20);
        button.addActionListener(this);
        add(button);

        errorMessage = new JLabel();
        errorMessage.setVisible(false);
        errorMessage.setBounds(100, 140, 600, 20);
        //errorMessage.setText();
        add(errorMessage);
    }

    // EFFECT: if the user click the register button, it will successfully register if the user satisfies
    //  with all the prerequisites and the course is not full. If the input courseID is not right, there will be message
    //  to inform them.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            try {
                int courseID = Integer.parseInt(courseIDBox.getText());
                if (registrationSystemCore.containCourses(courseID)) {
                    if (registrationSystemCore.register(registrationSystemCore.getCourseFromID(courseID), student)) {

                        errorMessage.setText("Successfully Registered!");
                        errorMessage.setVisible(true);
                    } else {
                        errorMessage.setText("You have registered or"
                                + " you do not satisfy prerequisites "
                                + "or you have taken this course! ");
                        errorMessage.setVisible(true);
                    }
                } else {
                    errorMessage.setText("No this courseID or course is not offered this semester!");
                    errorMessage.setVisible(true);
                }
            } catch (NumberFormatException nfe) {
                errorMessage.setText("Please enter a number");
                errorMessage.setVisible(true);
            }
        }
    }
}
