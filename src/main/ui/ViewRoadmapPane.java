package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import model.*;

public class ViewRoadmapPane extends JPanel {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private BufferedImage image;
    private Student student;
    private RegistrationSystem registrationSystemCore;

    public ViewRoadmapPane(RegistrationSystem registrationSystem, Student student) {
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        try {
            image = ImageIO.read(new File("./pic/courseGraph.png"));
        } catch (IOException ex) {
            System.out.print("No such file");
        }

        setVisible(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, WIDTH / 2, 0, this); // see javadoc for more info on the parameters
    }

}
