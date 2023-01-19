package de.buw.se4de;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class DrawingSurface extends JPanel {
    private boolean isLineActive = false;
    private Line activeLine = new Line(new MyPoint(0, 0), new MyPoint(0, 0));
    private ArrayList<Line> lines = new ArrayList<>();

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Get the current size of this component
        Dimension d = this.getSize();

        g.setColor(Color.lightGray);

        // divide the DrawingSurface with horizontal lines and their corresponding frequency
        for (float i = 1; i < 8; i++){
            float horizontal_scale = i/8;
            g.drawLine(0, (int) (d.height*horizontal_scale),d.width, (int) (d.height*horizontal_scale));
            int frequency = (int) (880 - 880 * horizontal_scale);
            g.drawString(frequency + " Hz", 7, (int) (d.height * horizontal_scale));
        }

        // divide the DrawingSurface with vertical lines and their corresponding timestamps in seconds
        for (float i = 1; i < 12; i++){
            float vertical_scale = i/12;
            g.drawLine((int) (d.width*vertical_scale), 0,(int) (d.width*vertical_scale), d.height);
            float time = 4 * vertical_scale;
            time = Math.round(time);
            g.drawString(time + " s", (int) (d.width * vertical_scale), d.height - 7);
        }


        // blue color for the lines
        g.setColor(Color.blue);

        for (Line line : lines) {
            line.draw(g);
        }

        if (isLineActive) {
            activeLine.draw(g);
        }
    }
    // die Funktion sorgt dafür, dass die gezogenen Linien sichtbar sind
    public void updateActiveLine(int x, int y, boolean mov) {
        if (isLineActive && mov) {
            activeLine.b.xCoordinate = x;
            activeLine.b.yCoordinate = y;
        } else if (!isLineActive && !mov) {
            activeLine.a.xCoordinate = x;
            activeLine.a.yCoordinate = y;
            activeLine.b.xCoordinate = x;
            activeLine.b.yCoordinate = y;
            isLineActive = true;
        } else if (isLineActive && !mov) {
            activeLine.b.xCoordinate = x;
            activeLine.b.yCoordinate = y;
            isLineActive = false;
            if (activeLine.a.xCoordinate > activeLine.b.xCoordinate) {
                int temp = activeLine.a.xCoordinate;
                activeLine.a.xCoordinate = activeLine.b.xCoordinate;
                activeLine.b.xCoordinate = temp;
                temp = activeLine.a.yCoordinate;
                activeLine.a.yCoordinate = activeLine.b.yCoordinate;
                activeLine.b.yCoordinate = temp;
            }
            lines.add(activeLine);
            activeLine = new Line(new MyPoint(0, 0), new MyPoint(0, 0));
        }
    }

    public void exportMusic(String name, double time) {
        double sampleRate = 44100.0;
        float[] buffer = new float[(int)(sampleRate*time)];
        Arrays.fill(buffer, 0.0f);

        double[] freqs = new double[buffer.length];

        for (Line line : lines) {
            Arrays.fill(freqs, 0.0);
            int i_start = (int)(freqs.length * (float)(line.a.xCoordinate / (float)getSize().getWidth()));
            int i_end = (int)(freqs.length * (float)(line.b.xCoordinate / (float)getSize().getWidth()));
            double f_start = (1.0 - (double)(line.a.yCoordinate / (double)getSize().getHeight())) * 880.0;
            double f_end = (1.0 - (double)(line.b.yCoordinate / (double)getSize().getHeight())) * 880.0;
            double f_delta = (f_end - f_start) / (double)(i_end - i_start);
            for (int i = i_start; i < i_end; ++i) {
                freqs[i] = f_start + f_delta * (i - i_start);
            }
            buffer = nco(freqs, buffer, sampleRate);
        }

        // normalisieren und runterscalen (um Ohren und Ausgabegeräte zu schonen)
        float max = 1.0f;
        for (float samp : buffer) {
            if (Math.abs(samp) > max) max = Math.abs(samp);
        }
        for (int i = 0; i < buffer.length; ++i) {
            buffer[i] /= max * 10.0f;
        }

        try {
            WaveHandler.writeFile(name, buffer, sampleRate);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // man kann Wellen nicht einfach so verknüpfen, also geht man schrittweise durch die Phase
    private float[] nco(double[] freqs, float[] buffer, double sampleRate) {
        double phase =  0.0;
        for (int i = 0; i < freqs.length; ++i) {
            double ph_step = 2.0 * Math.PI * freqs[i] * (1.0/sampleRate);
            phase += ph_step;
            if (Math.abs(freqs[i]) < 0.001) phase = 0.0;
            buffer[i] += (float)Math.sin(phase);
        }

        return buffer;
    }

}
