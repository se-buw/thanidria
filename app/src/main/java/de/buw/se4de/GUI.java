package de.buw.se4de;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame{

    public JPanel panel = new JPanel();
    public int canvasHeight = 880;
    //public List<Point> points = new ArrayList<>();

    public GUI() throws IOException {
        super("Thanidira");
        BufferedImage background = ImageIO.read(new File("./background.jpg"));
        JButton export = new JButton("Export to .wav");
        JLabel pic_label = new JLabel(new ImageIcon(background));
        setSize(1200, canvasHeight);
        //panel.add(export);
        panel.add(pic_label);
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //default closing action
        setLocationRelativeTo(null);
        pack();
        setVisible(true); //show frame

    }
    /*drawing lines test
    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //draw horizontal line
        //g2d.drawLine(x1, y1, x2, y1);
        g2d.drawLine(100, 100, 300, 100);
    }*/
    //paint function
    public void paint(Graphics g, MyPoint a, MyPoint b) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(a.xCoordinate , a.yCoordinate+30, b.xCoordinate+35, a.yCoordinate+30);
    }


/*
    public static void main(String[] args) throws IOException {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GUI thanid = new GUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }*/
}
