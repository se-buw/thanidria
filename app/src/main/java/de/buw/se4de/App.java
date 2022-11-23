package de.buw.se4de;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

public class App {
	public static void main(String[] args) throws IOException{

		JFrame mainFrame = new JFrame("Thanidria");
		mainFrame.setSize(1220, 945);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton export = new JButton("Export");
		export.setBounds(10,10,100,25);

		JButton name = new JButton("Name");
		name.setBounds(10,50,100,25);

		//JButton name = new JButton("Name");
		//name.setBounds(10,10,100,25);

		GUI gui = new GUI();

		gui.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {}
			@Override
			public void mouseMoved(MouseEvent e) {
				gui.updateActiveLine(e.getX(), e.getY(), true);
				gui.repaint();
			}
		});

		gui.addMouseListener(new MouseListener() {
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				gui.updateActiveLine(e.getX(), e.getY(), false);
				gui.repaint();
			}
		});

		String NAME = "none";

		export.addActionListener(e -> {
			gui.exportMusic("test", 4.0);
		});

		mainFrame.getContentPane().add(name);
		mainFrame.getContentPane().add(export);
		mainFrame.getContentPane().add(gui);

		mainFrame.validate();
		mainFrame.setVisible(true);

	}
}


