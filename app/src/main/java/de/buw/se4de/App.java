package de.buw.se4de;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.io.File;
import javax.sound.sampled.*;

public class App {
	//Spielt eine vorhandene .wav Datei ab.
	public static void main(String[] args) throws IOException{

		// Creating the main window of the app and specifying its properties
		JFrame mainFrame = new JFrame("Thanidria");
		mainFrame.setSize(900, 600);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton play = new JButton("Play");

		JButton save = new JButton("Save");

		//JButton name = new JButton("Name");

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
		// dieser Typ hat die IDEA vorgeschlagen, um die Variablen später durch die lambda funktion zu überschreiben
		AtomicReference<String> nameWAV = new AtomicReference<>("test");
		// AtomicReference<String> playWAV = new AtomicReference<>("test");

		play.addActionListener(e -> {
			JFrame frame = new JFrame("Play");
			frame.setSize(400, 300);
			frame.setLocation(100, 150);

			JLabel text = new JLabel("Gebe den Namen der abzuspielenden Datei ein.", SwingConstants.CENTER);
			text.setBounds(50,50,300,30);

			JTextField textfield = new JTextField();
			textfield.setBounds(50, 100, 300, 30);

			JButton ok = new JButton("OK");
			ok.setBounds(150, 150, 100, 30);

			frame.getRootPane().setDefaultButton(ok);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

			ok.addActionListener(f ->{
				nameWAV.set(textfield.getText());
				play("app/src/audios/"+nameWAV+".wav");
				frame.dispose();
			});
			frame.add(text);
			frame.add(textfield);
			frame.add(ok);
			frame.setLayout(null);
			frame.setVisible(true);
		});
		/*
		name.addActionListener(e -> {
			JFrame frame = new JFrame("Name");
			frame.setSize(400, 300);
			frame.setLocation(100, 150);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JLabel text = new JLabel("Gebe deiner Datei einen Namen.", SwingConstants.CENTER);
			text.setBounds(50,50,300,30);

			JTextField textfield = new JTextField();
			textfield.setBounds(50, 100, 300, 30);

			JButton ok = new JButton("OK");
			ok.setBounds(150, 150, 100, 30);

			ok.addActionListener(f ->{
				nameWAV.set(textfield.getText());
				frame.dispose();
			});
			frame.add(text);
			frame.add(textfield);
			frame.add(ok);
			frame.setLayout(null);
			frame.setVisible(true);
		});
		*/

		save.addActionListener(e -> {
			JFrame frame = new JFrame("Save");
			frame.setSize(400, 300);
			frame.setLocation(100, 150);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JLabel text = new JLabel("Gebe deiner Datei einen Namen.", SwingConstants.CENTER);
			text.setBounds(50,50,300,30);

			JTextField textfield = new JTextField();
			textfield.setBounds(50, 100, 300, 30);

			JButton ok = new JButton("OK");
			ok.setBounds(150, 150, 100, 30);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frame.getRootPane().setDefaultButton(ok);


			ok.addActionListener(f ->{
				nameWAV.set(textfield.getText());

				String name = textfield.getText();
				String specialCharacters = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";

				if (name.length() == 0){
					JOptionPane.showMessageDialog(frame, "Ein leerer Name ist nicht erlaubt!");
				}

				for (int i = 0; i < name.length(); i++) {
					char ch = name.charAt(i);
					if (specialCharacters.contains(Character.toString(ch))) {
						JOptionPane.showMessageDialog(frame, "Der Name soll nur aus Buchstaben und Ziffern bestehen!");
						break;
					}
					else if (i == name.length() - 1){
						gui.exportMusic(nameWAV.get(), 4.0);
						frame.dispose();
					}
				}

			});
			frame.add(text);
			frame.add(textfield);
			frame.add(ok);
			frame.setLayout(null);
			frame.setVisible(true);
		});

		// hier wurde ein Panel erstellt, mit Knöpfen versehen und daraufhin mit dem main frame verbunden
		JPanel panel = new JPanel();
		panel.add(play, BorderLayout.NORTH);
		panel.add(save, BorderLayout.NORTH);
		mainFrame.getContentPane().add(panel, BorderLayout.NORTH);
		mainFrame.getContentPane().add(gui);
		mainFrame.validate();
		mainFrame.setVisible(true);
	}
	public static void play(String filename) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));
			clip.start();
		}
		catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}
}


