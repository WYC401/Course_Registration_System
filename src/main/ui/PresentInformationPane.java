package ui;

import model.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

/*
    This abstract class represents the panels for indicating the current registered courses
 */
public abstract class PresentInformationPane extends JPanel {
    protected RegistrationSystem registrationSystemCore;
    protected Student student;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final String[] dataColumn = {"", "ID", "Name", "Semester", "Instructor"};
    protected JTable viewTable;
    protected JLabel messageLabel;
    protected JButton updateButton;
    protected JPanel tablePane;
    protected Object[][] data;

    // EFFECT: create a jpanel to present all the courses registered
    public PresentInformationPane(RegistrationSystem registrationSystemCore, Student student) {
        this.registrationSystemCore = registrationSystemCore;
        this.student = student;
        messageLabel = new JLabel();
        //updateButton = new JButton("Update");
        tablePane = new JPanel();

        data = initialTable();
        createInformation();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(messageLabel);
        add(tablePane);


        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        //updateButton.addActionListener(this);
        tablePane.setVisible(true);


    }

    // MODIFIES: this
    // EFFECT: update registration information by extracting data from current registration System
    public void updateData() {
        data = initialTable();

    }

    // MODIFIES: this
    // EFFECT: update the panel to incooperate data and message
    public void createInformation() {

        if (data.length == 0) {
            messageLabel.setVisible(true);
            messageLabel.setText("No course registered!");
            viewTable = new JTable();
        } else {
            messageLabel.setVisible(false);
            viewTable = new JTable(new MyTableModel());
            viewTable.setFillsViewportHeight(true);

        }
        //scrollPane.setVisible(true);
        viewTable.setVisible(true);


    }

    // MODIFIES: this
    // EFFECT: return the registration data which is extracted from the object registrationSystem and Student
    private Object[][] initialTable() {
        Object[] temp = student.getAlreadyRegisteredID().toArray();
        Object[][] tempData = new Object[temp.length][5];
        Object[] tempDataRow = new Object[4];
        for (int i = 0; i < temp.length; i++) {
            tempDataRow = registrationSystemCore.getCourseFromID((int) temp[i]).partiallyStringlized();
            tempData[i][0] = new Boolean(false);
            for (int j = 1; j < 5; j++) {
                tempData[i][j] = tempDataRow[j - 1];
            }

        }
        return tempData;
    }


    //EFFECT: update data table and all the information
    public void update() {

        tablePane.removeAll();
        tablePane.revalidate();
        updateData();
        createInformation();
        tablePane.add(new JScrollPane(viewTable));
        tablePane.setVisible(true);
        tablePane.repaint();
    }


    /*
      This is a class inside class to define the data model in Jtable
     */
    class MyTableModel extends AbstractTableModel {

        // EFFECT: return number of rows
        @Override
        public int getRowCount() {
            return data.length;
        }

        // EFFECT: return the number of columns
        @Override
        public int getColumnCount() {
            return dataColumn.length;
        }

        // EFFECT: return the value at rowIndex and columnIndex of table
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        // EFFECT: return the Class for each column
        @Override
        public Class<?> getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        // EFFECT: return Column Name for each column
        @Override
        public String getColumnName(int col) {
            return dataColumn[col];
        }

        // EFFECT: return true if the cell is editable( modifiable)
        @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 1) {
                return true;
            }
            return false;
        }

        // EFFECT: set value at the row, col
        @Override
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);

        }

    }
}
