package de.buw.se4de;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyPointTest {
    //not so sure why MyPoint gets 2 int if they later use double distance, but who am I to judge

    //constructor Test -> testing if input = output
    @Test
    void constructorTest(){
        int x=0;
        int y=-1;
        MyPoint p = new MyPoint(x,y);

        assertEquals(x,p.xCoordinate);
        assertEquals(y,p.yCoordinate);
    }

    //to string test -> testing if input = output
    @Test
    void toStringTest(){
        int x = -1;
        int y = 1;
        MyPoint p = new MyPoint(x,y);
        assertEquals("(-1,1)",p.toString());
    }
    //measuring if distance functions for --,++,+-,-+,00
    @Test
    void getDistanceTestNegNeg() {
        int x = 1;
        int y = 1;
        MyPoint p = new MyPoint(x,y);
        int x2 = -1;
        int y2 = -1;
        MyPoint p2 = new MyPoint(x2,y2);

        assertEquals(Math.sqrt(8),p.getDistance(p2));
    }

    @Test
    void getDistanceTestPosPos() {
        int x = 1;
        int y = 1;
        MyPoint p = new MyPoint(x,y);
        int x2 = 3;
        int y2 = 3;
        MyPoint p2 = new MyPoint(x2,y2);

        assertEquals(Math.sqrt(8),p.getDistance(p2));
    }
    @Test
    void getDistanceTestNegPos() {
        int x = 1;
        int y = 1;
        MyPoint p = new MyPoint(x,y);
        int x2 = -1;
        int y2 = 3;
        MyPoint p2 = new MyPoint(x2,y2);

        assertEquals(Math.sqrt(8),p.getDistance(p2));
    }
    @Test
    void getDistanceTestPosNeg() {
        int x = 1;
        int y = 1;
        MyPoint p = new MyPoint(x,y);
        int x2 = 3;
        int y2 = -1;
        MyPoint p2 = new MyPoint(x2,y2);

        assertEquals(Math.sqrt(8),p.getDistance(p2));
    }
    @Test
    void getDistanceTestZeroZero() {
        int x = 0;
        int y = 0;
        MyPoint p = new MyPoint(x,y);
        int x2 = 0;
        int y2 = 0;
        MyPoint p2 = new MyPoint(x2,y2);

        assertEquals(0,p.getDistance(p2));
    }

    @Test
    void roundPointTest() {
        int x = 2;
        int y = 3;
        MyPoint p = new MyPoint(x,y);

        int x2= 0;
        int y2= 1;
        MyPoint p2 = new MyPoint(x2,y2);
        p.roundPoint(p2,5);
        assertEquals(p.xCoordinate,p2.xCoordinate);
        assertEquals(p.yCoordinate,p2.yCoordinate);
    }
    @Test
    void roundPointTestFail() {
        int x = 2;
        int y = 3;
        MyPoint p = new MyPoint(x,y);

        int x2= 0;
        int y2= 1;
        MyPoint p2 = new MyPoint(x2,y2);
        p.roundPoint(p2,0);
        assertNotEquals(p.xCoordinate,p2.xCoordinate);
        assertNotEquals(p.yCoordinate,p2.yCoordinate);
    }

    @Test
    void setYTest() {
        int x=0;
        int y=1;
        MyPoint p=new MyPoint(x,y);
        int newY = 2;
        int newX = 3;
        MyPoint p2 = new MyPoint(newX,newY);
        p.setY(p2);
        assertEquals(3,p2.xCoordinate);
        assertEquals(1,p2.yCoordinate);
    }
}