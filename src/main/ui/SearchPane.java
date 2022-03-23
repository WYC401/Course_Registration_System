package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

public class SearchPane extends JPanel implements ActionListener {

    private RegistrationSystem registrationSystemCore;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JTextField idInput;
    private JButton searchButton;
    private JPanel tablePanel;
    private JTable jt;
    private JPanel messagePanel = new JPanel();

    public SearchPane(RegistrationSystem registrationSystemCore) {
        this.registrationSystemCore = registrationSystemCore;


        //setVisible(true);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        idInput = new JTextField();
        idInput.setPreferredSize(new Dimension(100, 20));
        searchButton = new JButton("Search");
        JPanel searchCombo = new JPanel();
        searchCombo.setLayout(new FlowLayout());
        searchCombo.add(new JLabel("courseID"));
        searchCombo.add(idInput);
        searchCombo.add(searchButton);
        searchCombo.setVisible(true);
        add(searchCombo);

        messagePanel.add(new JLabel("Invalid courseID or the course is not offered this semester!"));
        messagePanel.setVisible(false);
        add(messagePanel);

        tablePanel = new JPanel();

        //jt = new JTable();

        //jt.setVisible(false);
        tablePanel.setVisible(false);
        add(tablePanel);

        searchButton.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == searchButton) {
            try {
                int courseId = Integer.parseInt(idInput.getText());
                tablePanel.setVisible(false);
                if (registrationSystemCore.containCourses(courseId)) {
                    if (tablePanel.getComponentCount() > 0) {
                        tablePanel.remove(0);
                    }
                    showTable(courseId);

                } else {
                    tablePanel.setVisible(false);
                    messagePanel.setVisible(true);
                }
            } catch (Exception exception) {
                tablePanel.setVisible(false);
                messagePanel.setVisible(true);
            }

        }
    }

    private void showTable(int courseId) {
        messagePanel.setVisible(false);
        Object[][] data = new Object[1][4];
        data[0] = registrationSystemCore.getCourseFromID(courseId).partiallyStringlized();
        String[] column = {"ID", "NAME", "Semester", "Instructor"};
        jt = new JTable(data, column);
        jt.setFillsViewportHeight(true);
        //jt.setBounds(30,40,200,300);
        tablePanel.add(new JScrollPane(jt));
        jt.setVisible(true);
        //tablePanel.add(new JLabel("hhhhhhhhhh"));
        tablePanel.setVisible(true);
        setVisible(true);
    }

}
