package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PresentInformationPane extends JPanel  {
    private RegistrationSystem registrationSystemCore;
    private Student student;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JTable viewTable;
    private JLabel messageLabel;
    private JButton updateButton;
    private JPanel tablePane;
    private Object[][] data;

    public PresentInformationPane(RegistrationSystem registrationSystemCore, Student student)  {
        this.registrationSystemCore = registrationSystemCore;
        this.student = student;
        messageLabel = new JLabel();
        //updateButton = new JButton("Update");
        tablePane = new JPanel();
        String[] dataColumn = {"", "ID", "Name", "Semester", "Instructor"};
        data = initialTable();
        createInformation();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(messageLabel);
        add(tablePane);


        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        //updateButton.addActionListener(this);
        tablePane.setVisible(true);



    }

    public void updateData() {
        String[] dataColumn = {"", "ID", "Name", "Semester", "Instructor"};
        data = initialTable();

    }

    public void createInformation() {
        String[] dataColumn = {"", "ID", "Name", "Semester", "Instructor"};
        if(data.length == 0) {
            messageLabel.setVisible(true);
            messageLabel.setText("No course registered!");
            viewTable = new JTable();
        } else {
            messageLabel.setVisible(false);
            viewTable = new JTable(data, dataColumn);
            viewTable.setFillsViewportHeight(true);

        }
        //scrollPane.setVisible(true);
        viewTable.setVisible(true);
    }

    private Object[][] initialTable() {
        Object[] temp = student.getAlreadyRegisteredID().toArray();
        Object[][] tempData = new Object[temp.length][5];
        Object[] tempDataRow = new Object[4];
        for (int i = 0; i < temp.length; i++) {
            tempDataRow = registrationSystemCore.getCourseFromID((int) temp[i]).partiallyStringlized();
            tempData[i][0] = new Boolean(false);
            for (int j = 1; j < 5; j++) {
                tempData[i][j] = tempDataRow[j-1];
            }

        }
        return tempData;
    }


    public void update() {

        if (tablePane.getComponentCount() > 0) {
            tablePane.remove(0);
        }
        updateData();
        createInformation();
        tablePane.add(new JScrollPane(viewTable));
    }
}
