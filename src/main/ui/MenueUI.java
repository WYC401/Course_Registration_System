package ui;

//import model.RegistrationSystem;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenueUI extends JFrame  implements ActionListener {
    private RegistrationSystem registrationSystemCore;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JMenuBar menuBar = new JMenuBar();
    private Student user;
    private JPanel cards;

    public MenueUI(RegistrationSystem registrationSystemCore, Student user){
        this.registrationSystemCore = registrationSystemCore;
        this.user = user;
        initialMenuBar();
        setJMenuBar(menuBar);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        cards = new JPanel(new CardLayout());
        initialCards();
        getContentPane().add(cards, BorderLayout.CENTER);
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
        viewMenu.add(new JMenuItem("Courses Registered"),0);
        viewMenu.getItem(0).addActionListener(this);
        viewMenu.add(new JMenuItem("Learning Roadmap"), 1);
        viewMenu.getItem(1).addActionListener(this);
        operationMenu.add(new JMenuItem("Search"), 0);
        operationMenu.getItem(0).addActionListener(this);
        operationMenu.add(new JMenuItem("Drop"), 1);
        operationMenu.getItem(1).addActionListener(this);
        operationMenu.add(new JMenuItem("Register"));
        operationMenu.getItem(2).addActionListener(this);
        fileMenu.add(new JMenuItem("Save"), 0);
        fileMenu.getItem(0).addActionListener(this);
        fileMenu.add(new JMenuItem("Load"),1);
        fileMenu.getItem(1).addActionListener(this);
        fileMenu.add(new JMenuItem("Exit"),2);
        fileMenu.getItem(2).addActionListener(this);
    }

    private void initialCards() {
        SearchPane searchPane = new SearchPane(this.registrationSystemCore);
        cards.add( searchPane, "Search");

        RegisterPane registerPane = new RegisterPane(this.registrationSystemCore, this.user);
        cards.add(registerPane, "Register");

        ViewRegisterCoursePane viewPane = new ViewRegisterCoursePane(this.registrationSystemCore, this.user);
        cards.add(viewPane, "Courses Registered");

        DropPane dropPane = new DropPane(this.registrationSystemCore, this.user);
        cards.add(dropPane, "Drop");

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        if (e.getSource() == menuBar.getMenu(0).getItem(0)) {

            menuBar.getMenu(0).getItem(0).getText();

        }
        */
        JMenuItem temp = (JMenuItem) e.getSource();
        CardLayout cl = (CardLayout)(cards.getLayout());
        if(((String)temp.getText()).equals("Courses Registered")) {
            PresentInformationPane tempViewPane = (PresentInformationPane) cards.getComponent(2);
            tempViewPane.update();
        }
        if(((String)temp.getText()).equals("Drop")) {
            PresentInformationPane tempViewPane = (PresentInformationPane) cards.getComponent(3);
            tempViewPane.update();
        }
        cl.show(cards, (String)temp.getText());



    }
}
