package ui;

//import model.RegistrationSystem;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MenueUI extends JFrame  implements ActionListener {
    private RegistrationSystem registrationSystemCore;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JMenuBar menuBar = new JMenuBar();
    private Student user;
    private JPanel cards;
    private static final String CORE_PATH = "./data/registrationSystemCore.json";
    private JsonWriter writer;
    private JsonReader reader;
    private List<String> usernameAndPassword;
    private SearchPane searchPane;
    private RegisterPane registerPane;
    private DropPane dropPane;
    private ViewRegisterCoursePane viewPane;

    public MenueUI(RegistrationSystem registrationSystemCore, Student user, List<String> usernameAndPassword){
        this.registrationSystemCore = registrationSystemCore;
        this.user = user;
        initialMenuBar();
        setJMenuBar(menuBar);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        cards = new JPanel(new CardLayout());
        initialCards();
        addIntoCards();
        getContentPane().add(cards, BorderLayout.CENTER);
        writer = new JsonWriter(CORE_PATH);
        reader = new JsonReader(CORE_PATH);
        this.usernameAndPassword = usernameAndPassword;
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
        searchPane = new SearchPane(this.registrationSystemCore);
        //cards.add( searchPane, "Search");

        registerPane = new RegisterPane(this.registrationSystemCore, this.user);
        //cards.add(registerPane, "Register");

        viewPane = new ViewRegisterCoursePane(this.registrationSystemCore, this.user);
        //cards.add(viewPane, "Courses Registered");

        dropPane = new DropPane(this.registrationSystemCore, this.user);
        //cards.add(dropPane, "Drop");

    }

    private void addIntoCards() {
        cards.add( searchPane, "Search");
        cards.add(registerPane, "Register");
        cards.add(viewPane, "Courses Registered");
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


        if(((String) temp.getText()).equals("Save")) {
            try {
                writer.open();
                writer.write(registrationSystemCore);
                writer.close();
            } catch (FileNotFoundException fne) {
                System.out.println("No such file: " + CORE_PATH);
            } catch (IOException ioe1) {
                System.out.println("No directory named \"data\"");
            }

        } else if (((String) temp.getText()).equals("Load")) {
            try {
                registrationSystemCore = reader.read();
                user = registrationSystemCore.searchStudent(usernameAndPassword);
                cards.removeAll();
                cards.repaint();
                initialCards();
                addIntoCards();
                System.out.println("File loaded from " + CORE_PATH);
            } catch (IOException ioe2) {
                System.out.println("Unable to read file: " + CORE_PATH);
            }
        }

        cl.show(cards, (String)temp.getText());

    }
}
