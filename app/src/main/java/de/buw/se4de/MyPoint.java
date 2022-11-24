package de.buw.se4de;

// simple custom point class, to track mouse clicks on canvas
public class MyPoint {
    int xCoordinate = 0;       // the difference between the x-coordinates of two points determines the length of the note (currently we simply divide the x coordinate's distance by 100)
    int yCoordinate = 0;       // the y coordinate determines the frequency of the pitch, directly correlating to its position on the canvas (ex: {120;440} will generate a note at 440hz)

    public MyPoint(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    // Gruppe 3: Funktion der vorherigen Gruppe, haben wir allerdings nicht gebraucht
    public String toString() { // allows easy System.out.print() of MyPoint objects
        return "(" + this.xCoordinate + "," + this.yCoordinate + ")";
    }

    // Gruppe 3: Funktion der vorherigen Gruppe, haben wir allerdings nicht gebraucht
    public double getDistance(MyPoint b) { // Measures distance between points, used to generate note length, although in practice it only compares x values, since notes do not change in pitch
        double distX = (b.xCoordinate - this.xCoordinate);
        double distY = (b.yCoordinate - this.yCoordinate);
        double distance = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
        return distance;
    }

    // Gruppe 3: Funktion der vorherigen Gruppe, haben wir allerdings nicht gebraucht
    public void roundPoint(MyPoint b, int roundingLimit) { // Rounds similar points within a given rounding limit to be the same point (this is basically so that points/notes can be connected to each other, without the user needing pixel perfect accuracy to do so)
        if (((b.xCoordinate <= this.xCoordinate + roundingLimit) && (b.xCoordinate >= this.xCoordinate - roundingLimit)) &&
                ((b.yCoordinate <= this.yCoordinate + roundingLimit) && (b.yCoordinate >= this.yCoordinate - roundingLimit))) {
            System.out.println("Point " + b + "was rounded to " + this);

            b.xCoordinate = this.xCoordinate;
            b.yCoordinate = this.yCoordinate;
        } else
            System.out.println("This point does not need to be rounded.");
    }
    // Gruppe 3: Funktion der vorherigen Gruppe, haben wir allerdings nicht gebraucht
    public void setY(MyPoint b) {   // rounds only y coordinate, is used to flip y values in sound generation
        b.yCoordinate = this.yCoordinate;
    }
}

