package ui;

import model.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

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

    public void updateData() {
        data = initialTable();

    }

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


    public void update() {

        tablePane.removeAll();
        tablePane.revalidate();
        updateData();
        createInformation();
        tablePane.add(new JScrollPane(viewTable));
        tablePane.setVisible(true);
        tablePane.repaint();
    }

    class MyTableModel extends AbstractTableModel {


        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return dataColumn.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public String getColumnName(int col) {
            return dataColumn[col];
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 1) {
                return true;
            }
            return false;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);

        }

    }
}
