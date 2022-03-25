package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/*
this class is one of panel in the card layout of menuUI, which represent the information when dropping class
 */
public class DropPane extends PresentInformationPane implements ActionListener {

    private JButton dropButton = new JButton("Drop");

    //EFFECT: construct a drop panel
    public DropPane(RegistrationSystem registrationSystemCore, Student student) {
        super(registrationSystemCore, student);
        JPanel jpanel = new JPanel();
        jpanel.add(dropButton, BorderLayout.EAST);
        add(jpanel);
        dropButton.addActionListener(this);

    }

    // MODIFIES: this
    // EFFECT: drop the courses selected in the JTable each the drop button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        List<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i < data.length; i++) {
            Boolean checked = Boolean.valueOf(viewTable.getValueAt(i, 0).toString());
            int id = Integer.valueOf(viewTable.getValueAt(i, 1).toString());
            if (checked) {
                temp.add(id);
            }
        }

        for (Integer i : temp) {
            registrationSystemCore.drop(registrationSystemCore.getCourseFromID(i), student);
        }
        update();
    }
}
