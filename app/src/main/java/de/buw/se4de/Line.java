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
    public void draw(Graphics g) {
        g.drawLine(a.xCoordinate, a.yCoordinate, b.xCoordinate, b.yCoordinate);
    }

}