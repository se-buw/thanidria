package de.buw.se4de;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static boolean comparePoints(Point a, Point b) { // Checks if point a is "further" along x-axis than point b
        if (a.xCoordinate < b.xCoordinate)
            return false;
        return true;
    }

    public static void main(String[] args) {
        List<Point> points = new ArrayList<>();
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.add(panel);
        Point origin = new Point(0,0); // add point to ensure that list is not empty
        points.add(origin);
        int roundingLimit = 10;

        panel.addMouseListener(new MouseAdapter() { //allows us to override mouseListener functions
            @Override
            public void mousePressed(MouseEvent e) {
                if (points.size() >= 1) {   // Make sure that list contains at least one value
                    if (e.getX() >= points.get(points.size() - 1).xCoordinate) {   // Makes sure to only include points past last point's y-coordinate

                        points.add(new Point(e.getX(), e.getY()));  // add point to list
                        points.get(points.size() - 2).roundPoint(points.get(points.size() - 1), roundingLimit); // Checks if last two points are potentially the same point, if so it rounds them to match

                        if (points.size() % 2 != 0) {   // every two points get combined to create a line
                            points.get(points.size() - 2).setY(points.get(points.size() - 1)); // lines up last two points in array, if array size is odd
                        }

                        System.out.println(points);
                    }
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 880);
        frame.setVisible(true);
    }
}