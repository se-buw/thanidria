package de.buw.se4de;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static boolean comparePoints(MyPoint a, MyPoint b) { // Checks if point a is "further" along x-axis than point b
        if (a.xCoordinate < b.xCoordinate)
            return false;
        return true;
    }

    public static double calculatePitch(double difference) {  //this might be unnecessary, but I'll keep it in here just in case, can calculate the frequency of a note, given the difference between a starting note (in hz) in semi-tone steps to target note
        double exponent = (difference / 12);
        double frequency = 440.00d * Math.pow(2, exponent);
        return frequency;
    }

    public static void writeWAV(final double frequency, final double amplitude, final double seconds, String fileName) throws IOException {  // Creates WAV file from given parameters, god help me I don't understand how this bullshit works, but it does, so don't touch it if you don't have to

        final double sampleRate = 44100.0;
        final double piF = Math.PI * frequency;

        float[] buffer = new float[(int) (seconds * sampleRate)];

        for (int sample = 0; sample < buffer.length; sample++) {
            double time = sample / sampleRate;
            buffer[sample] = (float) (amplitude * Math.cos(piF * time) * Math.sin(piF * time));
        }

        final byte[] byteBuffer = new byte[buffer.length * 2];

        int bufferIndex = 0;
        for (int i = 0; i < byteBuffer.length; i++) {
            final int x = (int) (buffer[bufferIndex++] * 32767.0);

            byteBuffer[i++] = (byte) x;
            byteBuffer[i] = (byte) (x >>> 8);
        }

        File out = new File(fileName + ".wav");

        final boolean bigEndian = false;
        final boolean signed = true;

        final int bits = 16;
        final int channels = 1;

        AudioFormat format = new AudioFormat((float) sampleRate, bits, channels, signed, bigEndian);
        ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, buffer.length);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
        audioInputStream.close();
    }
    public static void invertY(List<MyPoint> myPointList, int canvasHeight){  // inverts y-coordinate of all points so that clicking lower on screen produces lower notes
        for (MyPoint pt: myPointList) {
            pt.yCoordinate = canvasHeight - pt.yCoordinate;
        }
    }
    public static void combineAudio(String fileName1, String fileName2, int size, int i) throws UnsupportedAudioFileException, IOException { // this abomination concatenates all the audio files, in theory you could simply overwrite the old file, but for testing purposes it currently retains all intermediary files as well
        // creates individual concatenated files, always concatenates previous concatenation with next part file
        String wavFile1 = fileName1;
        String wavFile2 = fileName2;
        AudioInputStream clip1 = AudioSystem.getAudioInputStream(new File(wavFile1));
        AudioInputStream clip2 = AudioSystem.getAudioInputStream(new File(wavFile2));

        AudioInputStream appendedFiles =
                new AudioInputStream(
                        new SequenceInputStream(clip1, clip2),
                        clip1.getFormat(),
                        clip1.getFrameLength() + clip2.getFrameLength());

        AudioSystem.write(appendedFiles,
                AudioFileFormat.Type.WAVE,
                new File("wavAppended" + i +".wav"));

        while (i < size) {
            // calls recursively until all part files are concatenated
            i++;
            combineAudio("wavAppended" + (i-1) + ".wav", "part" + (i+1) + ".wav", size, i);
        }
    }

    public static void cleanup(List<MyPoint> myPointList, int canvasHeight) throws IOException, UnsupportedAudioFileException {
        myPointList.remove(0);  // removes forced (0,0) coordinate

        invertY(myPointList, canvasHeight); // Subtracts x value from canvas height for each point, in order to have visual clarity (higher points = higher notes, not vice-versa)

        for (int i = 0; i < myPointList.size()-2; i+=2) { // Creates one .wav part file from every 2 points
            double length = myPointList.get(i).getDistance(myPointList.get(i+1))/100;
            String fileName = "part" + i/2;
            writeWAV(myPointList.get(i).yCoordinate,1.0d, length, fileName);
        }
        // concatenates all created .wav files
        combineAudio("part0.wav", "part1.wav",((myPointList.size()/2)-2), 0);
    }


    //////////////////////////////////////////////////// MAIN /////////////////////////////////////////////////////////
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.setVisible(true);
            }
        });
        List<MyPoint> myPoints = new ArrayList<>(); // stores mouse click locations
        MyPoint origin = new MyPoint(0,0); // add point to ensure that list is not empty, basically obsolete at this point, was useful for testing purposes
        myPoints.add(origin);
        int roundingLimit = 10; // the maximum pixel difference between 2 coordinates, at which they will be assumed to be the same point (see roundPoint() function)
        Graphics g = gui.getGraphics();
        g.setColor(Color.red); // red
        gui.panel.addMouseListener(new MouseAdapter() { //allows us to override mouseListener functions
            @Override
            public void mousePressed(MouseEvent e) {  // Records position of mouse, creates lines from said positions that can later be turned into audio segments
                if (myPoints.size() >= 1 && myPoints.size() <= 11) {   // Make sure that list contains at least one value (and no more than 10 for current testing purposes)
                    if (e.getX() >= myPoints.get(myPoints.size() - 1).xCoordinate) {   // Makes sure to only include points past last point's y-coordinate
                        myPoints.add(new MyPoint(e.getX(), e.getY()));  // add point to list
                        myPoints.get(myPoints.size() - 2).roundPoint(myPoints.get(myPoints.size() - 1), roundingLimit); // Checks if last two points are potentially the same point, if so it rounds them to match

                        if (myPoints.size() % 2 != 0) {   // every two points get combined to create a line
                            myPoints.get(myPoints.size() - 2).setY(myPoints.get(myPoints.size() - 1)); // lines up last two points in array, if array size is odd (assuming forced 0,0 coord is still in, change this if you remove origin from list)
                            System.out.println(myPoints);

                            //painting lines
                            gui.paint(g, myPoints.get(myPoints.size() - 1), myPoints.get(myPoints.size() - 2));
                            //gui.getContentPane().repaint();

                        }

                    }
                }
                else {
                    System.out.println("Ten points have been created!");
                    try {
                        cleanup(myPoints, gui.canvasHeight); // leads into sound creation step
                    } catch (IOException | UnsupportedAudioFileException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

    }


}
