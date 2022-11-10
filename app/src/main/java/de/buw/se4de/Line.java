package de.buw.se4de;

import java.awt.*;

public class Line {
    MyPoint a;
    MyPoint b;
    double length;

    public Line(MyPoint PtA, MyPoint PtB) {
        this.a = PtA;
        this.b = PtB;
        this.length = PtA.getDistance(PtB);
    }
    public void paint(Graphics g) {
        //super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(a.xCoordinate, a.yCoordinate, b.xCoordinate, a.yCoordinate);
    }

}