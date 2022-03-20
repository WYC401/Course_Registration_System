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
    JPanel tablePanel;

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
        searchCombo.add(idInput);
        searchCombo.add(searchButton);
        searchCombo.setVisible(true);
        add(searchCombo);
        tablePanel = new JPanel();
        tablePanel.setVisible(false);
        add(tablePanel);
        searchButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == searchButton)  {
            try {
                int courseId = Integer.parseInt(idInput.getText());
                if(registrationSystemCore.containCourses(courseId)) {
                    String data[][]={ {"101","Amit","670000"},
                            {"102","Jai","780000"},
                            {"101","Sachin","700000"}};
                    String column[]={"ID","NAME","SALARY"};
                    JTable jt = new JTable(data,column);
                    jt.setBounds(30,40,200,300);
                    tablePanel.add(jt);
                    tablePanel.setVisible(true);
                    setVisible(true);


                } else {
                    tablePanel.setVisible(false);
                    add(new JLabel("Invalid course ID or Not offered this semester!" ));
                }
            } catch( Exception exception) {
                // do nothing
            }

        }
    }
}
