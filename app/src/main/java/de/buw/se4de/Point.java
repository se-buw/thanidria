package de.buw.se4de;

public class Point {
    int xCoordinate = 0;
    int yCoordinate = 0;

    public Point(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    public String toString() {
        return "(" + this.xCoordinate + "," + this.yCoordinate + ")";
    }

    public double getDistance(Point b) {
        double distX = (b.xCoordinate - this.xCoordinate);
        double distY = (b.yCoordinate - this.yCoordinate);
        double distance = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
        return distance;
    }

    public void roundPoint(Point b, int roundingLimit) {   // compares new point to former point in list to see if the same point is intended to be created
        if (((b.xCoordinate <= this.xCoordinate + roundingLimit) && (b.xCoordinate >= this.xCoordinate - roundingLimit)) &&
                ((b.yCoordinate <= this.yCoordinate + roundingLimit) && (b.yCoordinate >= this.yCoordinate - roundingLimit))) {
            System.out.println("Point " + b + "was rounded to " + this);

            b.xCoordinate = this.xCoordinate;
            b.yCoordinate = this.yCoordinate;
        } else
            System.out.println("This point does not need to be rounded.");
    }

    public void setY(Point b) {   // rounds only y coordinate
        b.yCoordinate = this.yCoordinate;
    }
}

