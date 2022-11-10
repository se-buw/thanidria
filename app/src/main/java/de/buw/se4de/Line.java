package de.buw.se4de;

public class Line {
    Point a;
    Point b;
    double length;

    public Line(Point PtA, Point PtB) {
        this.a = PtA;
        this.b = PtB;
        this.length = PtA.getDistance(PtB);
    }


}