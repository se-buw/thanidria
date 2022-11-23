package de.buw.se4de;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class App {
	public static void main(String[] args) throws IOException{
		JFrame mainFrame = new JFrame("Thanidria");
		mainFrame.setSize(900, 600);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton export = new JButton("Export");

		JButton name = new JButton("Name");

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

		AtomicReference<String> NAME = new AtomicReference<>("none");

		name.addActionListener(e -> {
			JFrame frame = new JFrame("Name");
			frame.setSize(400, 300);
			frame.setLocation(100, 150);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JLabel text = new JLabel("Gebe deiner Datei einen Namen.");
			text.setBounds(50,50,200,30);

			JTextField textfield = new JTextField();
			textfield.setBounds(50, 100, 200, 30);

			JButton ok = new JButton("OK");
			ok.setBounds(50, 150, 200, 30);

			ok.addActionListener(f ->{
					NAME.set(textfield.getText());
					frame.dispose();
				});


			frame.add(text);
			frame.add(textfield);
			frame.add(ok);
			frame.setLayout(null);
			frame.setVisible(true);
		});

		export.addActionListener(e -> {
			gui.exportMusic(NAME.get(), 4.0);
		});

		JPanel panel = new JPanel();
		panel.add(export, BorderLayout.NORTH);
		panel.add(name, BorderLayout.NORTH);
		mainFrame.getContentPane().add(panel, BorderLayout.NORTH);
		mainFrame.getContentPane().add(gui);

		mainFrame.validate();
		mainFrame.setVisible(true);
	}
}


