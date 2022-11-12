package de.buw.se4de;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Component extends JPanel {
    List<Line> lines = new ArrayList<>();

    public void add(MyPoint a, MyPoint b) {
        this.lines.add(new Line(a, b));
    }


    public void paintComponent(Graphics g) {
        for(final Line l : lines) {
            super.paintComponent(g);
            l.paint(g);
        }
    }
}