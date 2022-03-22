package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DropPane extends PresentInformationPane implements ActionListener {

    private JButton dropButton = new JButton("Drop");

    public DropPane(RegistrationSystem registrationSystemCore, Student student) {
        super(registrationSystemCore,student);
        JPanel jpanel = new JPanel();
        jpanel.add(dropButton, BorderLayout.EAST);
        add(jpanel);
        dropButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Integer> temp = new ArrayList<Integer>();
        for(int i =0; i < data.length; i++) {
            Boolean checked = Boolean.valueOf(viewTable.getValueAt(i, 0).toString());
            int id = Integer.valueOf(viewTable.getValueAt(i,1).toString());
            if(checked) {
                temp.add(id);
            }
        }

        for (Integer i: temp) {
            registrationSystemCore.drop(registrationSystemCore.getCourseFromID(i), student);
        }
        update();
    }
}
