package de.buw.se4de;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.atomic.AtomicReference;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class App {
	//Spielt eine vorhandene .wav Datei ab.
	public static void main(String[] args){

		// Creating the main window of the app and specifying its properties
		JFrame mainFrame = new JFrame("Thanidria");
		mainFrame.setSize(900, 600);
		mainFrame.getRootPane().setBorder(new EmptyBorder(10,10,0,10));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// The DrawingSurface element is the part of the window where the user will draw their song
		DrawingSurface gui = new DrawingSurface();
		gui.setPreferredSize(new Dimension(850, 600));
		gui.setBorder(new LineBorder(Color.lightGray));
		gui.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainFrame.getContentPane().add(gui);

		// The buttons are placed in a separate panel below the DrawingSurface element
		JPanel buttons = new JPanel();

		JButton play = new JButton("Play");
		JButton open = new JButton("Open");
		JButton save = new JButton("Save");

		buttons.add(play);
		buttons.add(open);
		buttons.add(save);

		buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainFrame.getContentPane().add(buttons);

		mainFrame.validate();
		mainFrame.setVisible(true);
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
			public void mouseClicked(MouseEvent e){}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				gui.updateActiveLine(e.getX(), e.getY(), false);
				gui.repaint();
			}
		});
		// dieser Typ hat die IDEA vorgeschlagen, um die Variablen später durch die lambda funktion zu überschreiben
		AtomicReference<String> nameWAV = new AtomicReference<>("test");
		// AtomicReference<String> playWAV = new AtomicReference<>("test");

		play.addActionListener(e -> {
			gui.exportMusic("", 4.0);
			play("app/src/audios/.wav");
		});

		open.addActionListener(e -> {
			JFrame frame = new JFrame("Open");
			frame.setSize(400, 300);
			frame.setLocation(100, 150);


			JLabel text = new JLabel("Enter the name of the file you want to listen to.", SwingConstants.CENTER);
			text.setBounds(50,50,300,30);

			JTextField textField = new JTextField();
			textField.setBounds(50, 100, 300, 30);

			JButton ok = new JButton("OK");
			ok.setBounds(150, 150, 100, 30);

			frame.getRootPane().setDefaultButton(ok);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

			ok.addActionListener(f ->{
				nameWAV.set(textField.getText());

				play("app/src/audios/"+nameWAV+".wav");
				frame.dispose();
			});
			frame.add(text);
			frame.add(textField);
			frame.add(ok);
			frame.setLayout(null);
			frame.setVisible(true);
		});

		save.addActionListener(e -> {
			JFrame frame = new JFrame("Save");
			frame.setSize(400, 300);
			frame.setLocation(100, 150);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JLabel text = new JLabel("Enter a file name.", SwingConstants.CENTER);
			text.setBounds(50,50,300,30);

			JTextField textField = new JTextField();
			textField.setBounds(50, 100, 300, 30);

			JButton ok = new JButton("OK");
			ok.setBounds(150, 150, 100, 30);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frame.getRootPane().setDefaultButton(ok);


			ok.addActionListener(f ->{
				nameWAV.set(textField.getText());

				String name = textField.getText();
				String specialCharacters = "!@#$%&*()'+,./:;<=>?[]^`{|}";

				if (name.length() == 0){
					JOptionPane.showMessageDialog(frame, "Please enter a name for the file.");
				}

				for (int i = 0; i < name.length(); i++) {
					char ch = name.charAt(i);
					if (specialCharacters.contains(Character.toString(ch))) {
						JOptionPane.showMessageDialog(frame, "The name should not contain any special characters.");
						break;
					}
					else if (i == name.length() - 1){
						gui.exportMusic(nameWAV.get(), 4.0);
						JOptionPane.showMessageDialog(frame, "File was saved.");
						frame.dispose();
					}
				}
			});

			frame.add(text);
			frame.add(textField);
			frame.add(ok);
			frame.setLayout(null);
			frame.setVisible(true);
		});
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


