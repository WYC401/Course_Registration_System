package ui;

//import model.RegistrationSystem;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MenueUI extends JFrame  implements ActionListener {
    private RegistrationSystem registrationSystemCore;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JMenuBar menuBar = new JMenuBar();
    private Student user;

    public MenueUI(RegistrationSystem registrationSystemCore, Student user){
        this.registrationSystemCore = registrationSystemCore;
        this.user = user;
        initialMenuBar();
        setJMenuBar(menuBar);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        //menuBar.addMouseListener(this);
    }

    private void initialMenuBar() {
        JMenu viewMenu = new JMenu("View");
        JMenu operationMenu = new JMenu("Operate");
        JMenu fileMenu = new JMenu("File");

        JMenuItem menuItem = new JMenuItem("MenuItem");
        menuBar.add(viewMenu, 0);
        menuBar.add(fileMenu, 1);
        menuBar.add(operationMenu, 2);
        viewMenu.add(new JMenuItem("Registered Courses"),0);
        viewMenu.getItem(0).addActionListener(this);

        viewMenu.add(new JMenuItem("Learning Roadmap"), 1);
        operationMenu.add(new JMenuItem("Search"));
        operationMenu.add(new JMenuItem("Drop"));
        operationMenu.add(new JMenuItem("Register"));
        fileMenu.add(new JMenuItem("Save"));
        fileMenu.add(new JMenuItem("Load"));
        fileMenu.add(new JMenuItem("Exit"));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuBar.getMenu(0).getItem(0)) {

            menuBar.getMenu(0).getItem(0).getText();
            dispose();
        }
    }
}
