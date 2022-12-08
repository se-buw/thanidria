package de.buw.se4de;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {
    //constructor test
    @Test
    void constructorTest(){
        MyPoint a= new MyPoint(0,0);
        MyPoint b = new MyPoint(-1,32676);
        Line l = new Line(a, b);

        assertEquals(a,l.a);
        assertEquals(b,l.b);
        double absX = Math.abs(a.xCoordinate-b.xCoordinate);
        double absY = Math.abs(a.yCoordinate-b.yCoordinate);
        double len = Math.sqrt(absX*absX+absY*absY);
        assertEquals(len,l.length);
    }


}